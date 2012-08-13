package de.q2web.jocl.util;

import java.util.concurrent.TimeUnit;

/**
 * Handle durations.
 *
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 */
public class Duration {

	/**
	 * Returns the elapsed time formatted as <code>%ds, %dms, %dum, %dns</code>
	 *
	 * @param nanoseconds
	 * @return the elapsed time formatted as <code>%ds, %dms, %dum, %dns</code>
	 */
	public static String of(final long nanoseconds) {
//@formatter:off
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