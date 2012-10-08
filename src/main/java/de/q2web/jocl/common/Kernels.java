package de.q2web.jocl.common;

import static org.jocl.CL.CL_MEM_COPY_HOST_PTR;
import static org.jocl.CL.CL_MEM_READ_ONLY;
import static org.jocl.CL.CL_MEM_READ_WRITE;
import static org.jocl.CL.CL_TRUE;
import static org.jocl.CL.clBuildProgram;
import static org.jocl.CL.clCreateBuffer;
import static org.jocl.CL.clCreateKernel;
import static org.jocl.CL.clCreateProgramWithSource;
import static org.jocl.CL.clEnqueueBarrier;
import static org.jocl.CL.clEnqueueNDRangeKernel;
import static org.jocl.CL.clEnqueueReadBuffer;
import static org.jocl.CL.clReleaseKernel;
import static org.jocl.CL.clReleaseMemObject;
import static org.jocl.CL.clReleaseProgram;
import static org.jocl.CL.clSetKernelArg;

import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_kernel;
import org.jocl.cl_mem;
import org.jocl.cl_program;

import de.q2web.jocl.util.Integers;
import de.q2web.jocl.util.Resources;

/**
 * 
 * OpenCl Kernels for common operatipons.
 * 
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 * 
 */
public class Kernels {

	/** Source code of all kernels. */
	private static final String SOURCE = Resources
			.convertStreamToString(Kernels.class
					.getResourceAsStream("commonKernels.cl"));

	/** The Constant DEFAULT_LOCAL_WORKSIZE. */
	private static final long[] DEFAULT_LOCAL_WORKSIZE = new long[] { 1 };

	private static final int NOT_FOUND = -1;

	/** The Constant KERNEL_MINIMUM_FLOAT. */
	private static final String KERNEL_MINIMUM_FLOAT = "minimumFloat";

	/** The Constant KERNEL_MINIMUM_FLOAT_WITH_POSITION. */
	private static final String KERNEL_MINIMUM_FLOAT_WITH_POSITION = "minimumWithPositionFloat";

	private static final String KERNEL_MAXIMUM_FLOAT_WITH_POSITION = "maximumWithPositionFloat";
	
	/** The Constant KERNEL_EMPTY_INTS. */
	private static final String KERNEL_EMPTY_INTS = "emptyInts";

	/**
	 * Returns the the minimum value and the position of that minimum of a float
	 * array
	 * 
	 * <p>
	 * If there are multiple minima (identical values), then the position of the
	 * first minimum is returned.
	 * 
	 * @param context
	 *            the context
	 * @param queue
	 *            the queue
	 * @param floats
	 *            the floats
	 * @return the position of the minimum
	 */
	@SuppressWarnings("deprecation")
	public static float minimum(final cl_context context,
			final cl_command_queue queue, final float[] floats) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = floats.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);

			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_MINIMUM_FLOAT, null);

			final Pointer floatsPointer = Pointer.to(floats);
			memObject = new cl_mem[1];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_float * length,
					floatsPointer, null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { length }));

			long globalWorkSize = Integers.nextBinary(length);
			for (int pass = 0; globalWorkSize > 1; pass++) {
				clSetKernelArg(kernel, 2, Sizeof.cl_uint,
						Pointer.to(new int[] { pass }));
				clEnqueueNDRangeKernel(queue, kernel, 1, null,
						new long[] { globalWorkSize >>= 1 },
						DEFAULT_LOCAL_WORKSIZE, 0, null, null);
				clEnqueueBarrier(queue);
			}

			// Read only the first float
			final float[] result = new float[1];
			final Pointer resultPointer = Pointer.to(result);
			clEnqueueReadBuffer(queue, memObject[0], CL_TRUE, 0,
					Sizeof.cl_float, resultPointer, 0, null, null);
			return result[0];
		} finally {
			// Release kernel, program, and memory objects
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}

	}

	/**
	 * Returns the the minimum value and the position of that minimum of a float
	 * array
	 * 
	 * <p>
	 * If there are multiple minima (identical values), then the position of the
	 * first minimum is returned.
	 * 
	 * @param context
	 *            the context
	 * @param queue
	 *            the queue
	 * @param floats
	 *            the floats
	 * @return the position of the minimum
	 */
	@SuppressWarnings("deprecation")
	public static ValuePosition maximumWithPositionAndOffset(final cl_context context,
			final cl_command_queue queue, final float[] floats, int leftOffset,
			int rightOffset) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = floats.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program,
					KERNEL_MAXIMUM_FLOAT_WITH_POSITION, null);

			memObject = new cl_mem[1];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_float * length,
					Pointer.to(floats), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { leftOffset }));
			clSetKernelArg(kernel, 2, Sizeof.cl_uint,
					Pointer.to(new int[] { rightOffset }));

			final int intervalLength = rightOffset - leftOffset + 1;
			long globalWorkSize = Integers.nextBinary(intervalLength);
			for (int pass = 0; globalWorkSize > 1; pass++) {
				clSetKernelArg(kernel, 3, Sizeof.cl_uint,
						Pointer.to(new int[] { pass }));
				clEnqueueNDRangeKernel(queue, kernel, 1, null,
						new long[] { globalWorkSize >>= 1 },
						DEFAULT_LOCAL_WORKSIZE, 0, null, null);
				clEnqueueBarrier(queue);
			}

			final float[] values = new float[2];
			clEnqueueReadBuffer(queue, memObject[0], CL_TRUE, Sizeof.cl_float
					* leftOffset, Sizeof.cl_float * 2, Pointer.to(values), 0,
					null, null);

			return new ValuePosition(values[0], (int) values[1]);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static ValuePosition minimumWithPosition(final cl_context context,
			final cl_command_queue queue, final float[] floats) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = floats.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program,
					KERNEL_MINIMUM_FLOAT_WITH_POSITION, null);

			memObject = new cl_mem[1];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_float * length,
					Pointer.to(floats), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { length }));

			long globalWorkSize = Integers.nextBinary(length);
			for (int pass = 0; globalWorkSize > 1; pass++) {
				clSetKernelArg(kernel, 2, Sizeof.cl_uint,
						Pointer.to(new int[] { pass }));
				clEnqueueNDRangeKernel(queue, kernel, 1, null,
						new long[] { globalWorkSize >>= 1 },
						DEFAULT_LOCAL_WORKSIZE, 0, null, null);
				clEnqueueBarrier(queue);
			}

			final float[] values = new float[2];
			clEnqueueReadBuffer(queue, memObject[0], CL_TRUE, 0,
					Sizeof.cl_float * 2, Pointer.to(values), 0, null, null);

			return new ValuePosition(values[0], (int) values[1]);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	/**
	 * Returns the position of the minimum value of a
	 * <code>float<code> array if it is below a
	 * certain threshold otherwise <code>-1</code>
	 * 
	 * <p>
	 * If there are multiple minima (identical values), then the position of the
	 * first minimum is returned.
	 * 
	 * @param context
	 *            the context
	 * @param queue
	 *            the queue
	 * @param floats
	 *            the floats
	 * @param threshold
	 *            the threshold
	 * @return the position if the found minimum is below the given threshold,
	 *         otherwise <code>-1</code>
	 */
	public static int positionOfMinimum(final cl_context context,
			final cl_command_queue queue, final float[] floats,
			final float threshold) {
		final ValuePosition minimumPosition = minimumWithPosition(context,
				queue, floats);
		if (minimumPosition.getValue() > threshold) {
			return NOT_FOUND;
		}

		return minimumPosition.getPosition();
	}

	/**
	 * Returns the position of the minimum value in an array
	 * 
	 * <p>
	 * If there are multiple minima (identical values), then the position of the
	 * first minimum is returned.
	 * 
	 * @param context
	 *            the context
	 * @param queue
	 *            the queue
	 * @param floats
	 *            the floats
	 * @return the position of the minimum
	 */
	public static int positionOfMinimum(final cl_context context,
			final cl_command_queue queue, final float[] floats) {
		return minimumWithPosition(context, queue, floats).getPosition();
	}

	/**
	 * Returns <code>true</code> if array is empty, <code>false</code>
	 * otherwise.
	 * 
	 * @param context
	 *            the context
	 * @param queue
	 *            the queues
	 * @param ints
	 *            the ints
	 * @return <code>true</code> if successful
	 */
	public static boolean isEmpty(final cl_context context,
			final cl_command_queue queue, final int[] ints) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_EMPTY_INTS, null);
			final int[] resultArray = new int[] { 0 };

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_ONLY
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int,
					Pointer.to(resultArray), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(memObject[1]));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int,
					Pointer.to(resultArray), 0, null, null);

			return resultArray[0] == 0;

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	/**
	 * Container for minimum and position of the minumum of an array.
	 * 
	 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
	 * 
	 * @see Kernels#minimumWithPosition(cl_context, cl_command_queue, float[])
	 */
	static class ValuePosition {

		private final float value;
		private final int position;

		/**
		 * Instantiates a new minimum position.
		 * 
		 * @param value
		 *            the value
		 * @param position
		 *            the position
		 */
		ValuePosition(final float value, final int position) {
			super();
			this.value = value;
			this.position = position;
		}

		/**
		 * Gets the value.
		 * 
		 * @return the value
		 */
		public float getValue() {
			return value;
		}

		/**
		 * Gets the position.
		 * 
		 * @return the position
		 */
		public int getPosition() {
			return position;
		}
	}

}
