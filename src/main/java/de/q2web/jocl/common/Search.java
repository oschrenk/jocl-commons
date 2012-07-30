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

public class Search {

	private static final String MINIMUM_FLOAT = "minimumFloat";
	private static final String SEARCH = Resources
			.convertStreamToString(Search.class
					.getResourceAsStream("search.cl"));

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
	public static float minimum(cl_context context, cl_command_queue queue,
			float[] floats) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			int length = floats.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SEARCH }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, MINIMUM_FLOAT, null);

			Pointer floatsPointer = Pointer.to(floats);
			memObject = new cl_mem[1];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_float * length,
					floatsPointer, null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { length }));

			final long[] localWorkSize = new long[] { 1 };

			int nn = Integers.binaryLog(length);
			for (int j = 0; j < nn; j++) {
				long[] globalWorkSize = new long[] { (1 << (nn - j - 1)) };
				clSetKernelArg(kernel, 2, Sizeof.cl_uint,
						Pointer.to(new int[] { j }));
				clEnqueueNDRangeKernel(queue, kernel, 1, null, globalWorkSize,
						localWorkSize, 0, null, null);
				clEnqueueBarrier(queue);
			}

			// Read only the first float
			float[] result = new float[1];
			Pointer resultPointer = Pointer.to(result);
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
}
