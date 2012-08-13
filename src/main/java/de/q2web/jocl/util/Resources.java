package de.q2web.jocl.util;

import java.io.InputStream;

/**
 * Static helpers to handle I/O on host device.
 *
 * @author Oliver Schrenk <oliver.schrenk@q2web.de>
 *
 */
public class Resources {

	/**
	 * Convert <code>UTF-8</code> character based {@link InputStream} to
	 * {@link String}.
	 *
	 * @param is
	 *            <code>UTF-8</code> character based {@link InputStream}
	 * @return the string
	 */
	public static String convertStreamToString(final InputStream is) {
		try {
			return new java.util.Scanner(is, "UTF-8").useDelimiter("\\A")
					.next();
		} catch (final java.util.NoSuchElementException e) {
			return "";
		}
	}

}