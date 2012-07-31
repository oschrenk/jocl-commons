/**
 * Compute the minimum float in an array.
 *
 * <p><b>Warning</b>Overwrites the input array!
 *
 * @param inputOutput read/write float array
 * @param length length of input array
 * @param pass counts the passes through theouter loop in the host
 */
__kernel void minimumFloat(
	__global float* inputOutput,
	const uint length,
	const uint pass
) {
	// HostCode
	//	int nn = Integers.nearestBinary(length) / 2;
	//	for (int j = 0; j < nn; j++) {
	//		long[] globalWorkSize = new long[] { (1 << (nn - j - 1)) };
	//		clSetKernelArg(kernel, 2, Sizeof.cl_uint,
	//				Pointer.to(new int[] { j }));
	//		clEnqueueNDRangeKernel(queue, kernel, 1, null, globalWorkSize,
	//				localWorkSize, 0, null, null);
	//		clEnqueueBarrier(queue);
	//	}

	// Example:

	// Input:
	//		float expectedMinimum = 10.0f;
	//		float[] floats = new float[] { 784.5f, 45.6f, expectedMinimum, 56.7f, 67.8f, 78.9f };

	// length = 6
	// nn = 4
	// j = 0
	//	pass = 0, gws = 2
	//		positionLeft  = (1 << (pass + 1)) * i;
	//		              = (1 << (0 + 1)) * i;
	// 		              = 2 * i; with i in {0,1}
	//		=> positionLeft is {0, 2}
	//		positionRight = positionLeft + (1 << pass);
	//		              = {0,2} + (1 << 0);
	//		              = {0,2} + 1;
	//		=> positionRight is {1, 3}
	//
	//		positionRight < length IS true
	//		positionLeft < length IS true
	//
	//		=> io[positionLeft] = min(io[positionLeft], io[positionRight])
	// j = 1
	//	pass = 1, gws = 2


	uint i = get_global_id(0);

	uint positionLeft = (1 << (pass + 1)) * i;
	uint positionRight = positionLeft + (1 << pass);
	if (positionRight < length)
	{
		if (positionLeft < length)
		{
			((__global float*)inputOutput)[positionLeft] =
					min(((__global float*)inputOutput)[positionLeft], ((__global float*)inputOutput)[positionRight]);
		}
	}
}