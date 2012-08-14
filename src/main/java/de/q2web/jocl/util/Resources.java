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
	 * @see <a
	 *      href="http://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner_1.html">Stupid
	 *      Scanner tricks...</a>
	 * @see <a
	 *      href="http://docs.oracle.com/javase/tutorial/essential/regex/bounds.html">Boundary
	 *      Matchers</a>
	 */
	private static final String BOUNDARY_CONSTRUCT_BEGINNING_OF_INPUT = "\\A";
	private static final String UTF_8 = "UTF-8";

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
			return new java.util.Scanner(is, UTF_8).useDelimiter(
					BOUNDARY_CONSTRUCT_BEGINNING_OF_INPUT).next();
		} catch (final java.util.NoSuchElementException e) {
			return "";
		}
	}

}