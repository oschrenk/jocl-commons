/**
 * <code>minimumFloat</code> computes the minimum float in the input array.
 *
 * <p><b>Warning</b>Overwrites the input array!
 *
 * @param inoutput read/write float array
 * @param size length of input array
 * @param pass counts the passes through theouter loop in the host
 */
__kernel void minimumFloat(__global float* inoutput,
					const uint size,
					const uint pass)
{
	uint i = get_global_id(0);

	uint sourcepos1 = (1 << (pass + 1)) * i;
	uint sourcepos2 = sourcepos1 + (1 << pass);
	if (sourcepos2 < size)
	{
		if (sourcepos1 < size)
		{
			((__global float*)inoutput)[sourcepos1] =
					min(((__global float*)inoutput)[sourcepos1], ((__global float*)inoutput)[sourcepos2]);
		}
	}
}