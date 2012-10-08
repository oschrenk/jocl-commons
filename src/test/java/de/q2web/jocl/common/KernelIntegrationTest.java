package de.q2web.jocl.common;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.clReleaseCommandQueue;
import static org.jocl.CL.clReleaseContext;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

import com.google.common.base.Stopwatch;
import com.google.common.primitives.Floats;

import de.q2web.jocl.util.Arrays;
import de.q2web.jocl.util.Duration;

/**
 * Test timings of common kernels.
 * 
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 * 
 */
public class KernelIntegrationTest {

	@BeforeClass
	public static void setUp() {
		// Enable exceptions and subsequently omit error checks in this sample
		CL.setExceptionsEnabled(true);
	}

	@SuppressWarnings("unused")
	@Test
	public void testMinimum() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final float[] floats = Arrays.random(10000, -180f, 180f);
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			final float minimumJava = Floats.min(floats);
			final long elapsedTimeJava = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			stopwatch.start();
			final float minimumJocl = Kernels.minimum(context, queue, floats);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Java: " + Duration.of(elapsedTimeJava));
			System.out.println("JOCL: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}

	@Test
	public void testEmpty() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = 10000000;
			final Random random = new Random();
			final int r = random.nextInt(length) + 1;

			final int[] ints = Arrays.prefilled(length, 0);
			ints[r] = 1;
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			for (final int i : ints) {
				if (i != 0) {
					break;
				}
			}
			final long elapsedTimeJava = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			stopwatch.start();
			Kernels.isEmpty(context, queue, ints);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Java: " + Duration.of(elapsedTimeJava));
			System.out.println("JOCL: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseCommandQueue(queue);
			clReleaseContext(context);
		}
	}
}
