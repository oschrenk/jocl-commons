package de.q2web.jocl.common;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.clReleaseCommandQueue;
import static org.jocl.CL.clReleaseContext;
import static org.junit.Assert.assertEquals;

import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;
import org.jocl.utils.CommandQueues;
import org.jocl.utils.Contexts;
import org.jocl.utils.Devices;
import org.jocl.utils.Platforms;
import org.junit.Test;

public class KernelsTest {

	@Test
	public void testMinimum() {

		cl_platform_id platformId = Platforms.getPlatforms().get(0);
		cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		cl_context context = Contexts.create(platformId, deviceId);
		cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10.0f;
			final float[] floats = new float[] { 784.5f, 45.6f,
					expectedMinimum, 56.7f, 67.8f, 78.9f };
			final float actualMinimum = Kernels.minimum(context, queue, floats);
			assertEquals(expectedMinimum, actualMinimum, 0.0);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

}
