/**
 * Compute the minimum float in an array.
 *
 * <p><b>Warning</b>Overwrites the input array!
 *
 * @param io
 *			read/write float array
 * @param length
 *			length of input array
 * @param pass
 *			counts the passes through theouter loop in the host
 */
__kernel void minimumFloat(
	__global float* io,
	const uint length,
	const uint pass
) {
	// HostCode
	// 	long globalWorkSize = Integers.nearestBinary(length);
	// 	for (int pass = 0; globalWorkSize > 1; pass++) {
	// 		clSetKernelArg(kernel, 2, Sizeof.cl_uint,
	// 				Pointer.to(new int[] { pass }));
	// 		clEnqueueNDRangeKernel(queue, kernel, 1, null, new long[] {globalWorkSize >>= 1},
	// 				localWorkSize, 0, null, null);
	// 		clEnqueueBarrier(queue);
	// 	}

	// Example:

	// Input:
	//	float min = 10.0f;
	//	float[] floats = { 784.5f, 45.6f, min, 56.7f, 67.8f, 78.9f };

	// length = 6
	// pass = 0, globalWorkSize = 4 > 1
	//	left  = (1 << (pass + 1)) * globalId;
	//	              = (1 << (0 + 1)) * globalId;
	// 	              = 2 * globalId; with globalId in {0..3}
	//	=> left is {0, 2, 4, 6}
	//	right = left + (1 << pass);
	//	              = {0, 2, 4, 6} + (1 << 0);
	//	              = {0, 2, 4, 6} + 1;
	//	=> right is {1, 3, 5, 7}
	//
	//	right < length IS true
	//	left < length IS true
	//
	//	=> io[left] = min(io[left], io[right])
	// j = 1
	//	pass = 1, gws = 2

	uint left = (1 << (pass + 1)) * get_global_id(0);
	uint right = left + (1 << pass);
	if (right < length && left < length) {
		((__global float*)io)[left] =
			min(((__global float*)io)[left], ((__global float*)io)[right]);
	}
}

/**
 * Compute the minimum float in an array and find the position of the
 * minimum in the original array. The minimum can be found in
 * <code>buffer[0]</code> and the position at
 * <code>buffer[ceil(log_2(buffer.length))]</code>
 *
 * <p><b>Warning</b>Overwrites the input array!
 *
 * @param io
 *			read/write float array
 * @param length
 *			length of input array
 * @param pass
 *			counts the passes through the outer loop in the host
 */
__kernel void minimumWithPositionFloat(
	__global float* io,
	const uint length,
	const uint pass
) {
	uint left = (1 << (pass + 1)) * get_global_id(0);
	uint right = left + (1 << pass);
	if (right < length && left < length) {

		// HostCode
		// 	long globalWorkSize = Integers.nearestBinary(length);
		// 	for (int pass = 0; globalWorkSize > 1; pass++) {
		// 		clSetKernelArg(kernel, 2, Sizeof.cl_uint,
		// 				Pointer.to(new int[] { pass }));
		// 		clEnqueueNDRangeKernel(queue, kernel, 1, null, new long[] {globalWorkSize >>= 1},
		// 				localWorkSize, 0, null, null);
		// 		clEnqueueBarrier(queue);
		// 	}

		// Example:

		// Input:
		//	float min = 10;
		//	float[] floats = { 70f, 60f, min, 40f, 50f, 80f };

		// pass = 0
		// globalWorkSize = 4
		// io = { 70, 60, 10, 40, 50, 80 }, length = 6
		// pass = 0, globalWorkSize = 4
		//
		//	left  = (1 << (pass + 1)) * globalId;
		//	              = (1 << (0 + 1)) * globalId;
		// 	              = 2 * globalId; with globalId in {0..3}
		//	=> left is {0, 2, 4, 6}
		//	right = left + (1 << pass);
		//	              = {0, 2, 4, 6} + (1 << 0);
		//	              = {0, 2, 4, 6} + 1;
		//	=> right is {1, 3, 5, 7}
		//
		// 	left:0, right:1
		//			=> 70 > 60
		//			=> io[0] = io[1] = 60
		//			   io[1] = right = 1
		// 	left:2, right:3
		//			=> 10 < 40
		//			=> io[3] = left = 2
		// 	left:4, right:5
		//			=> 50 < 80
		//			=> io[5] = left = 4
		// 	left:6, right:7 is out of bound => ignore
		//
		// pass = 1
		// globalWorkSize = 2
		// io = { 60, 1, 10, 2, 50, 4 };
		//
		// left  = (1 << (pass + 1)) * globalId;
		//       = 4 * globalId
		//       = {0, 4}
		// right = left + (2);
		//       = 4 * globalId + 2
		//       = {2, 6}
		// 	left:0, right:2
		//			=> 60 > 10
		//			=> io[0] = 10
		//			=> io[1]

		// 	left:0, right:1

		//
		// pass = 2
		// globalWorkSize = 1
		// io = { 60, 1, 10, 2, 50, 4 };

		//
		if ( ((__global float*)io)[right] < ((__global float*)io)[left] ) {
			((__global float*)io)[left] = ((__global float*)io)[right];
			((__global float*)io)[left+1] = (pass == 0 || right == length - 1) ? right : io[right+1];
		} else {
			((__global float*)io)[left+1] = (pass == 0) ? left : io[left+1];
		}

	}
}

/**
 * Fill an array of uints with the globalId; creating an iterator array starting
 * with <code>0</code>
 *
 * @param io
 *			read/write uint array
 */
__kernel void iterator(
	__global uint* io
) {
	uint i = get_global_id(0);
	((__global uint*)io)[i] = i;
}
