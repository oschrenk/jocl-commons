/**
 * Compute the minimum float in an array.
 *
 * <p><b>Warning</b>Overwrites the input array!
 *
 * @param io
 			read/write float array
 * @param length
 			length of input array
 * @param pass
 			counts the passes through theouter loop in the host
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
	//		float expectedMinimum = 10.0f;
	//		float[] floats = new float[] { 784.5f, 45.6f, expectedMinimum, 56.7f, 67.8f, 78.9f };

	// length = 6
	// globalWorkSize = 4
	// j = 0
	//	pass = 0, gws = 2
	//		left  = (1 << (pass + 1)) * i;
	//		              = (1 << (0 + 1)) * i;
	// 		              = 2 * i; with i in {0..8}
	//		=> left is {0, 2}
	//		right = left + (1 << pass);
	//		              = {0,2} + (1 << 0);
	//		              = {0,2} + 1;
	//		=> right is {1, 3}
	//
	//		right < length IS true
	//		left < length IS true
	//
	//		=> io[left] = min(io[left], io[right])
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
 * minimum in the original array
 *
 * <p><b>Warning</b>Overwrites the input array!
 *
 * @param io
 			read/write float array
 * @param length
 			length of input array
 * @param pass
 			counts the passes through theouter loop in the host
 */
__kernel void minimumThresholdFloat(
	__global float* io,
	__global uint* iterator,
	const uint length,
	const uint pass
) {
	uint left = (1 << (pass + 1)) * get_global_id(0);
	uint right = left + (1 << pass);
	if (right < length && left < length) {
		if ( ((__global float*)io)[right] < ((__global float*)io)[left] ) {
			((__global float*)io)[left] = ((__global float*)io)[right];
			((__global uint*)iterator)[left] = ((__global uint*)iterator)[right];
		}
	}
}

/**
 * Fill an array of uints with the globalId; creating an iterator array starting
 * with <code>0</code>
 *
 * @param io
 			read/write uint array
 */
__kernel void iterator(
	__global uint* io
) {
	uint i = get_global_id(0);
	((__global uint*)io)[i] = i;
}
