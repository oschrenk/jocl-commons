package de.q2web.jocl.common;

import static org.jocl.CL.CL_MEM_COPY_HOST_PTR;
import static org.jocl.CL.CL_MEM_READ_ONLY;
import static org.jocl.CL.CL_MEM_READ_WRITE;
import static org.jocl.CL.CL_TRUE;
import static org.jocl.CL.clBuildProgram;
import static org.jocl.CL.clCreateBuffer;
import static org.jocl.CL.clCreateKernel;
import static org.jocl.CL.clCreateProgramWithSource;
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

import de.q2web.jocl.util.Resources;

/**
 *
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 */
public class Timing {

	private static final String SOURCE = Resources
			.convertStreamToString(Kernels.class
					.getResourceAsStream("timingKernels.cl"));

	private static final String KERNEL_NOOP = "noop";
	private static final String KERNEL_REWRITE = "rewrite";
	private static final String KERNEL_WRITE_PLUS_ONE = "writePlusOne";
	private static final String KERNEL_COMPARE_NOOP = "compareNoOp";
	private static final String KERNEL_COMPARE_REWRITE = "compareRewrite";
	private static final String KERNEL_COMPARE_PLUS_ONE = "comparePlusOne";

	private static final long[] DEFAULT_LOCAL_WORKSIZE = new long[] { 1 };

	public static void createBuffer(final cl_context context, final int[] ints) {
		final cl_program program = null;
		final cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			memObject = new cl_mem[1];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_ONLY
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void createAndReadBuffer(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength) {
		final cl_program program = null;
		final cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_ONLY
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);
		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void noop(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_NOOP, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_ONLY
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void rewrite(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_REWRITE, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_ONLY
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void writePlusOne(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_WRITE_PLUS_ONE, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void compareNoop(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength, final int number) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_COMPARE_NOOP, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { number }));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void compareRewrite(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength, final int number) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_COMPARE_REWRITE, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { number }));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}

	public static void comparePlusOne(final cl_context context,
			final cl_command_queue queue, final int[] ints,
			final int resultLength, final int number) {
		cl_program program = null;
		cl_kernel kernel = null;
		cl_mem[] memObject = null;
		try {
			final int length = ints.length;
			program = clCreateProgramWithSource(context, 1,
					new String[] { SOURCE }, null, null);
			clBuildProgram(program, 0, null, null, null, null);
			kernel = clCreateKernel(program, KERNEL_COMPARE_PLUS_ONE, null);

			memObject = new cl_mem[2];
			memObject[0] = clCreateBuffer(context, CL_MEM_READ_WRITE
					| CL_MEM_COPY_HOST_PTR, Sizeof.cl_int * length,
					Pointer.to(ints), null);
			memObject[1] = clCreateBuffer(context, CL_MEM_READ_ONLY,
					Sizeof.cl_int * resultLength, Pointer.to(ints), null);

			clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(memObject[0]));
			clSetKernelArg(kernel, 1, Sizeof.cl_uint,
					Pointer.to(new int[] { number }));
			clEnqueueNDRangeKernel(queue, kernel, 1, null,
					new long[] { length }, DEFAULT_LOCAL_WORKSIZE, 0, null,
					null);

			final int[] resultArray = new int[resultLength];
			clEnqueueReadBuffer(queue, memObject[1], CL_TRUE, 0, Sizeof.cl_int
					* resultLength, Pointer.to(resultArray), 0, null, null);

		} finally {
			// Release memory objects, kernel and program
			clReleaseMemObject(memObject[0]);
			clReleaseKernel(kernel);
			clReleaseProgram(program);
		}
	}
}
