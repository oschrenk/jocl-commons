package de.q2web.jocl.util;

import java.io.InputStream;

public class Resources {

	public static String convertStreamToString(InputStream is) {
		try {
			return new java.util.Scanner(is, "UTF-8").useDelimiter("\\A")
					.next();
		} catch (java.util.NoSuchElementException e) {
			return "";
		}
	}

}