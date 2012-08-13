package de.q2web.jocl.util;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 */
//@formatter:off
public class Duration {

	public static String of(final long nanoseconds) {

		return String.format(
				"%ds, %dms, %dum, %dns",
				// seconds
				TimeUnit.NANOSECONDS.toSeconds(nanoseconds),
				// milliseconds
				TimeUnit.NANOSECONDS.toMillis(nanoseconds) 	-
				TimeUnit.SECONDS.toMillis(TimeUnit.NANOSECONDS.toSeconds(nanoseconds)),
				//microseconds
				TimeUnit.NANOSECONDS.toMicros(nanoseconds)
				- TimeUnit.MILLISECONDS.toMicros(TimeUnit.NANOSECONDS.toMillis(nanoseconds)),
				//nanoseconds
				nanoseconds - TimeUnit.MICROSECONDS.toNanos(TimeUnit.NANOSECONDS.toMicros(nanoseconds)));
	}
}
//@formatter:on