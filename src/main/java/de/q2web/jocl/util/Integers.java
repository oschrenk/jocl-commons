package de.q2web.jocl.util;

public class Integers {

	/**
	 * Fast computation of ceil(log_2(x)) for integer
	 * 
	 * @param number
	 * @return <code>0</code> for <code>0</code>, log_2(int)
	 */
	public static int binaryLog(int number) {
		int log = 0;
		if ((number & 0xffff0000) != 0) {
			number >>>= 16;
			log = 16;
		}
		if (number >= 256) {
			number >>>= 8;
			log += 8;
		}
		if (number >= 16) {
			number >>>= 4;
			log += 4;
		}
		if (number >= 4) {
			number >>>= 2;
			log += 2;
		}
		return log + (number >>> 1);
	}

	/**
	 * Fast computation of the nearest binary number equal or greater than x
	 * 
	 * <p>
	 * Not defined for <code>x <= 1</code>
	 * 
	 * @param x
	 * @return
	 */
	public static int nearestBinary(int x) {
		x--;
		x |= x >> 1;
		x |= x >> 2;
		x |= x >> 4;
		x |= x >> 8;
		x |= x >> 16;
		x++;
		return x;
	}

}
