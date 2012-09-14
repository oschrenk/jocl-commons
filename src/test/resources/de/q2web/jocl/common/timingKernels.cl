/**
 * This kernel does nothing.
 *
 * @param io
 *			read/write uint array
 */
__kernel void noop(
	__global uint* io
) {
	;
}

/**
 * This kernel accesses the global id
 *
 * @param io
 *			read/write uint array
 */
__kernel void getGlobalId(
	__global uint* io
) {
	uint i = get_global_id(0);
}

/**
 * This kernel reads <code>io[globalId</code>
 *
 * @param io
 *			read/write uint array
 */
__kernel void readIo(
	__global uint* io
) {
	uint i = get_global_id(0);
	((__global uint*)io)[i];
}

/**
 * This kernel writes <code>io[globalId]</code> to <code>io[globalId]</code>
 *
 * @param io
 *			read/write uint array
 */
__kernel void rewrite(
	__global uint* io
) {
	uint i = get_global_id(0);
	((__global uint*)io)[i] = ((__global uint*)io)[i];
}

/**
 * This kernel writes <code>io[globalId]+1</code> to <code>io[globalId]</code>
 *
 * @param io
 *			read/write uint array
 */
__kernel void writePlusOne(
	__global uint* io
) {
	uint i = get_global_id(0);
	((__global uint*)io)[i] = ((__global uint*)io)[i];
}

/**
 * This kernel compares <code>io[globalId]</code> against 
 * <code>io[globalId]</code> but does nothing
 *
 * @param io
 *			read/write uint array
 */
__kernel void compareNoOp(
	__global uint* io,
	const uint compare
) {
	uint i = get_global_id(0);
	if (((__global uint*)io)[i] > compare) {
		;
	}
}

/**
 * This kernel compares <code>io[globalId]</code> against 
 * <code>io[globalId]</code> and writes <code>io[globalId]</code> back
 *
 * @param io
 *			read/write uint array
 */
__kernel void compareRewrite(
	__global uint* io,
	const uint compare
) {
	uint i = get_global_id(0);
	if (((__global uint*)io)[i] > compare) {
		((__global uint*)io)[i] = ((__global uint*)io)[i];
	}
}

/**
 * This kernel comapares <code>io[globalId]</code> against 
 * <code>io[globalId]</code> and writes <code>io[globalId] + 1</code> back
 *
 * @param io
 *			read/write uint array
 */
__kernel void comparePlusOne(
	__global uint* io,
	const uint compare
) {
	uint i = get_global_id(0);
	if (((__global uint*)io)[i] > compare) {
		((__global uint*)io)[i] = ((__global uint*)io)[i] + 1;
	}
}