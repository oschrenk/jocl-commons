package de.q2web.jocl.common;

import static org.jocl.CL.CL_MEM_COPY_HOST_PTR;
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

public class Kernels {

	private static final long[] DEFAULT_LOCAL_WORKSIZE = new long[] { 1 };

	private static final int NOT_FOUND = -1;

	private static final String KERNEL_ITERATOR = "iterator";
	private static final String KERNEL_MINIMUM_FLOAT = "minimumFloat";
	private static final String KERNEL_MINIMUM_THRESHOLD_FLOAT = "minimumThresholdFloat";

	private static final String SOURCE = Resources
			.convertStreamToString(Kernels.class
					.getResourceAsStream("commonKernels.cl"));

	/**
	 * <code>minimumFloat</code> computes the minimum float in the input array.
	 * 
	 * <p>
	 * <b>Warning</b>Overwrites the input array in the device!
	 * 
	 * <p>
	 * It's time complexity is <code>O(log n)</code>. It creates
	 * <code>ceil(log_2(length))</code> comparing two array entries. On the the
	 * first pass it compares <code>a[0] with <code>a[1]</code>,
	 * <code>...</code>, <code>a[n-1]</code> with a[n]</code>, on second pass
	 * <code>a[0] with <code>a[3]</code>, <code>...</code>, <code>a[n-3]</code>
	 * with a[n]</code> and so on.
	 * 
	 * 
	 * @param context
	 *            OpenCl Context
	 * @param queue
	 *            OpenCl CommandQuue
	 * @param floats
	 *            input array
	 * @return the minimum
	 */
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

			long globalWorkSize = Integers.nearestBinary(length);
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
	 * Returns the position of the minimum value in an array if it is below a
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
		cl_program program = null;
		cl_kernel kernelIterator = null;
		cl_kernel kernelMinimumThreshold = null;
		cl_mem[] memObject = null;
		try {
			final int length = floats.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernelIterator = clCreateKernel(program, KERNEL_ITERATOR, null);
			kernelMinimumThreshold = clCreateKernel(program,
					KERNEL_MINIMUM_THRESHOLD_FLOAT, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_float * length,
					Pointer.to(floats), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_WRITE,
					Sizeof.cl_uint * length, null, null);

			// first create an iterator array
			clSetKernelArg(kernelIterator, 0, Sizeof.cl_mem,
					Pointer.to(memObject[1]));
			clEnqueueNDRangeKernel(queue, kernelIterator, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);
			// make sure iterator array is filled
			clEnqueueBarrier(queue);

			clSetKernelArg(kernelMinimumThreshold, 0, Sizeof.cl_mem,
					Pointer.to(memObject[0]));
			clSetKernelArg(kernelMinimumThreshold, 1, Sizeof.cl_mem,
					Pointer.to(memObject[1]));
			clSetKernelArg(kernelMinimumThreshold, 2, Sizeof.cl_uint,
					Pointer.to(new int[] { length }));

			long globalWorkSize = Integers.nearestBinary(length);
			for (int pass = 0; globalWorkSize > 1; pass++) {
				clSetKernelArg(kernelMinimumThreshold, 3, Sizeof.cl_uint,
						Pointer.to(new int[] { pass }));
				clEnqueueNDRangeKernel(queue, kernelMinimumThreshold, 1, null,
						new long[] { globalWorkSize >>= 1 },
						DEFAULT_LOCAL_WORKSIZE, 0, null, null);
				clEnqueueBarrier(queue);
			}

			// Read only the first values
			final float[] resultFloat = new float[1];
			clEnqueueReadBuffer(queue, memObject[0], CL_TRUE, 0,
					Sizeof.cl_float, Pointer.to(resultFloat), 0, null, null);

			final int[] resultPosition = new int[1];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0,
					Sizeof.cl_uint, Pointer.to(resultPosition), 0, null, null);

			if (resultFloat[0] < threshold) {
				return resultPosition[0];
			}

			return NOT_FOUND;
		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseMemObject(memObject[1]);
			clReleaseKernel(kernelIterator);
			clReleaseKernel(kernelMinimumThreshold);
			clReleaseProgram(program);
		}
	}
}
