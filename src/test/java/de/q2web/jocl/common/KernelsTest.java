package de.q2web.jocl.common;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.clReleaseCommandQueue;
import static org.jocl.CL.clReleaseContext;
import static org.junit.Assert.assertEquals;

import org.jocl.CL;
import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;
import org.jocl.utils.CommandQueues;
import org.jocl.utils.Contexts;
import org.jocl.utils.Devices;
import org.jocl.utils.Platforms;
import org.junit.BeforeClass;
import org.junit.Test;

public class KernelsTest {

	@BeforeClass
	public static void setUp() {
		// Enable exceptions and subsequently omit error checks in this sample
		CL.setExceptionsEnabled(true);
	}

	@Test
	public void testMinimumFloat() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
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

	@Test
	public void testPositionOfMinimumWithThreshold() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10.0f;
			final float epsilon = expectedMinimum + 1;
			final float[] floats = new float[] { 70f, 60f, expectedMinimum,
					40f, 50f, 80f };
			final int minimumPosition = Kernels.positionOfMinimum(context,
					queue, floats, epsilon);
			assertEquals(2, minimumPosition);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testPositionOfMinimumWithThresholdMinimumAtEnd() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10.0f;
			final float threshold = expectedMinimum + 1;
			final float[] floats = new float[] { 70f, 60f, 90f, 40f, 50f, 80f,
					expectedMinimum };
			final int minimumPosition = Kernels.positionOfMinimum(context,
					queue, floats, threshold);
			assertEquals(floats.length - 1, minimumPosition);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testPositionOfMinimumWithThresholdMultipleIdenticalMinima() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10.0f;
			final float epsilon = expectedMinimum + 1;
			final float[] floats = new float[] { 70f, 60f, expectedMinimum,
					40f, 50f, expectedMinimum, 30f };
			final int minimumPosition = Kernels.positionOfMinimum(context,
					queue, floats, epsilon);
			assertEquals(2, minimumPosition);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

}
