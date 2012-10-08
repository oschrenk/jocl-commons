package de.q2web.jocl.common;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.clReleaseCommandQueue;
import static org.jocl.CL.clReleaseContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

import de.q2web.jocl.common.Kernels.ValuePosition;

/**
 * Test common kernels.
 * 
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 */
public class KernelsTest {

	@BeforeClass
	public static void setUp() {
		// Enable exceptions and subsequently omit error checks in this sample
		CL.setExceptionsEnabled(true);
	}

	@Test
	public void testMinimum() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10.0f;
			final float[] floats = { 784.5f, 45.6f, expectedMinimum, 56.7f,
					67.8f, 78.9f };
			final float actualMinimum = Kernels.minimum(context, queue, floats);
			assertEquals(expectedMinimum, actualMinimum, 0.0);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testMinimumWithPosition() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10;
			final float[] floats = { 70f, 60f, expectedMinimum, 40f, 50f, 80f };
			final ValuePosition minimumPosition = Kernels.minimumWithPosition(
					context, queue, floats);
			assertEquals(expectedMinimum, minimumPosition.getValue(), 0.0);
			assertEquals(2, minimumPosition.getPosition());

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testMaximumWithPosition() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMaximum = 90;
			final float[] floats = { 70f, 60f, expectedMaximum, 40f, 50f, 80f };
			final ValuePosition valuePosition = Kernels
					.maximumWithPositionAndOffset(context, queue, floats, 0,
							floats.length - 1);
			assertEquals(expectedMaximum, valuePosition.getValue(), 0.0);
			assertEquals(2, valuePosition.getPosition());

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testMaximumWithPositionAndInterval() {
		testMaximumWithPositionAndInterval(0, 5);
		testMaximumWithPositionAndInterval(1, 5);
		testMaximumWithPositionAndInterval(2, 5);
		testMaximumWithPositionAndInterval(2, 4);
		testMaximumWithPositionAndInterval(2, 3);
	}

	public void testMaximumWithPositionAndInterval(int leftOffset,
			int rightOffset) {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMaximum = 90;
			final float[] floats = { 70f, 60f, expectedMaximum, 40f, 50f, 80f };
			final ValuePosition valuePosition = Kernels
					.maximumWithPositionAndOffset(context, queue, floats,
							leftOffset, rightOffset);
			assertEquals(expectedMaximum, valuePosition.getValue(), 0.0);
			assertEquals(2, valuePosition.getPosition());

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testMaximumWithPositionAndInterval2() {
		// testMaximumWithPositionAndInterval2(0, 6);
		// testMaximumWithPositionAndInterval2(1, 6);
		// testMaximumWithPositionAndInterval2(2, 6);
		// testMaximumWithPositionAndInterval2(0, 5);
		// testMaximumWithPositionAndInterval2(1, 5);
		// testMaximumWithPositionAndInterval2(2, 5);
		// testMaximumWithPositionAndInterval2(2, 4);
		// // testMaximumWithPositionAndInterval2(2, 3);
		testMaximumWithPositionAndInterval2(2, 2);
	}

	public void testMaximumWithPositionAndInterval2(int leftOffset,
			int rightOffset) {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMaximum = 90;
			final float[] floats = { 70f, 60f, expectedMaximum, 40f, 50f, 80f,
					60f };
			final ValuePosition valuePosition = Kernels
					.maximumWithPositionAndOffset(context, queue, floats,
							leftOffset, rightOffset);
			assertEquals(expectedMaximum, valuePosition.getValue(), 0.0);
			assertEquals(2, valuePosition.getPosition());

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testMinimumWithPositionUnevenArraySizeMinimumAtEnd() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float minimum = 10;
			final float[] floats = { 70f, 60f, 90f, 40f, 50f, 80f, minimum };
			final ValuePosition minimumPosition = Kernels.minimumWithPosition(
					context, queue, floats);
			assertEquals(minimum, minimumPosition.getValue(), 0.0);
			assertEquals(6, minimumPosition.getPosition());

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testMinimumWithPositionAtEnd() {

		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float expectedMinimum = 10;
			final float[] floats = { 70f, 60f, 40f, 50f, 80f, expectedMinimum };
			final ValuePosition minimumPosition = Kernels.minimumWithPosition(
					context, queue, floats);
			assertEquals(expectedMinimum, minimumPosition.getValue(), 0.0);
			assertEquals(5, minimumPosition.getPosition());

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
			final float threshold = expectedMinimum + 1;
			final float[] floats = new float[] { 70f, 60f, expectedMinimum,
					40f, 50f, 80f };
			final int position = Kernels.positionOfMinimum(context, queue,
					floats, threshold);
			assertEquals(2, position);

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
			final float[] floats = { 70f, 60f, 90f, 40f, 50f, 80f,
					expectedMinimum };
			final int position = Kernels.positionOfMinimum(context, queue,
					floats, threshold);
			assertEquals(floats.length - 1, position);

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
			final int position = Kernels.positionOfMinimum(context, queue,
					floats, epsilon);
			assertEquals(2, position);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testEmptyIntsWithEmptyArray() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int[] ints = new int[] { 0, 0, 0, 0, 0, 0 };
			final boolean empty = Kernels.isEmpty(context, queue, ints);
			assertTrue(empty);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testEmptyIntsWithFilledArray() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int[] ints = new int[] { 0, 0, 42, 0, 0, 0 };
			final boolean empty = Kernels.isEmpty(context, queue, ints);
			assertFalse(empty);

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

}
