/*
 *******************************************************************************
 * TransferTest.java
 * $Id: $
 *
 *******************************************************************************
 *
 * Copyright:   Q2WEB GmbH
 *              quality to the web
 *
 *              Tel  : +49 (0) 211 / 159694-00	Kronprinzenstr. 82-84
 *              Fax  : +49 (0) 211 / 159694-09	40217 Düsseldorf
 *              eMail: info@q2web.de						http://www.q2web.de
 *
 *
 * Author:      oliver.schrenk
 *
 * Created:     Aug 13, 2012
 *
 * Copyright (c) 2009 Q2WEB GmbH.
 * All rights reserved.
 *
 *******************************************************************************
 */
package de.q2web.jocl.common;

import static org.jocl.CL.CL_DEVICE_TYPE_GPU;
import static org.jocl.CL.clReleaseContext;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jocl.cl_command_queue;
import org.jocl.cl_context;
import org.jocl.cl_device_id;
import org.jocl.cl_platform_id;
import org.jocl.utils.CommandQueues;
import org.jocl.utils.Contexts;
import org.jocl.utils.Devices;
import org.jocl.utils.Platforms;
import org.junit.Test;

import com.google.common.base.Stopwatch;

import de.q2web.jocl.util.Arrays;
import de.q2web.jocl.util.Duration;

/**
 *
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 */
public class TimingIntegrationTest {

	private static final int LENGTH = 10000000;
	private static final int UPPER_BOUND = 100;
	private static final int LOWER_BOUND = 0;

	@Test
	public void testCreateBuffer() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.prefilled(length, 0);
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.createBuffer(context, ints);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testCreateBufferRandom() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.createBuffer(context, ints);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testCreateAndReadBuffer() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.prefilled(length, 0);
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.createAndReadBuffer(context, queue, ints, LENGTH);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testCreateAndReadBufferRandom() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.createAndReadBuffer(context, queue, ints, LENGTH);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testNoop() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);

			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.noop(context, queue, ints, LENGTH);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testRewrite() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);

			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.rewrite(context, queue, ints, LENGTH);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testWritePlusOne() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);

			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.writePlusOne(context, queue, ints, LENGTH);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testCompareNoop() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);
			final int random = new Random().nextInt(UPPER_BOUND - LOWER_BOUND
					+ 1)
					+ LOWER_BOUND;
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.compareNoop(context, queue, ints, LENGTH, random);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testCompareRewrite() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);
			final int random = new Random().nextInt(UPPER_BOUND - LOWER_BOUND
					+ 1)
					+ LOWER_BOUND;
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.compareRewrite(context, queue, ints, LENGTH, random);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

	@Test
	public void testComparePlusOne() {
		final cl_platform_id platformId = Platforms.getPlatforms().get(0);
		final cl_device_id deviceId = Devices.getDevices(platformId,
				CL_DEVICE_TYPE_GPU).get(0);
		final cl_context context = Contexts.create(platformId, deviceId);
		final cl_command_queue queue = CommandQueues.create(context, deviceId);
		try {
			final int length = LENGTH;
			final int[] ints = Arrays.random(length, LOWER_BOUND, UPPER_BOUND);
			final int random = new Random().nextInt(UPPER_BOUND - LOWER_BOUND
					+ 1)
					+ LOWER_BOUND;
			final Stopwatch stopwatch = new Stopwatch();

			stopwatch.start();
			Timing.compareRewrite(context, queue, ints, LENGTH, random);
			final long elapsedTimeJocl = stopwatch
					.elapsedTime(TimeUnit.NANOSECONDS);
			stopwatch.reset();

			System.out.println("Time: " + Duration.of(elapsedTimeJocl));

		} finally {
			clReleaseContext(context);
		}
	}

}
