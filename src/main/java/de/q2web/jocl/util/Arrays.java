package de.q2web.jocl.util;

/**
 * Offers static helper methods for arrays
 */
public class Arrays {

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the boolean[]
	 */
	public static boolean[] prefilled(int length, boolean value) {
		boolean a[] = new boolean[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the byte[]
	 */
	public static byte[] prefilled(int length, byte value) {
		byte a[] = new byte[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the char[]
	 */
	public static char[] prefilled(int length, char value) {
		char a[] = new char[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the float[]
	 */
	public static float[] prefilled(int length, float value) {
		float a[] = new float[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the double[]
	 */
	public static double[] prefilled(int length, double value) {
		double a[] = new double[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the short[]
	 */
	public static short[] prefilled(int length, short value) {
		short a[] = new short[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the int[]
	 */
	public static int[] prefilled(int length, int value) {
		int a[] = new int[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the long[]
	 */
	public static long[] prefilled(int length, long value) {
		long a[] = new long[length];
		java.util.Arrays.fill(a, value);
		return a;
	}

	/**
	 * Position of minimum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMinimum(byte[] values) {
		byte min = Byte.MAX_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final byte v = values[i];
			if (v < min) {
				min = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of minimum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMinimum(short[] values) {
		short min = Short.MAX_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final short v = values[i];
			if (v < min) {
				min = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of minimum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMinimum(int[] values) {
		int min = Integer.MAX_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final int v = values[i];
			if (v < min) {
				min = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of minimum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMinimum(long[] values) {
		long min = Long.MAX_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final long v = values[i];
			if (v < min) {
				min = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of minimum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMinimum(float[] values) {
		float min = Float.MAX_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final float f = values[i];
			if (f < min) {
				min = f;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of minimum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMinimum(double[] values) {
		double min = Double.MAX_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final double v = values[i];
			if (v < min) {
				min = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of maximum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMaximum(byte[] values) {
		byte max = Byte.MIN_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final byte v = values[i];
			if (v > max) {
				max = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of maximum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMaximum(short[] values) {
		short max = Short.MIN_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final short v = values[i];
			if (v > max) {
				max = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of maximum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMaximum(int[] values) {
		int max = Integer.MIN_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final int v = values[i];
			if (v > max) {
				max = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of maximum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMaximum(long[] values) {
		long max = Long.MIN_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final long v = values[i];
			if (v > max) {
				max = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of maximum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMaximum(float[] values) {
		float max = Float.MIN_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final float f = values[i];
			if (f > max) {
				max = f;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Position of maximum.
	 * 
	 * @param values
	 *            the values
	 * @return the int
	 */
	public static final int positionOfMaximum(double[] values) {
		double max = Double.MIN_VALUE;
		int pos = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++) {
			final double v = values[i];
			if (v > max) {
				max = v;
				pos = i;
			}
		}
		return pos;
	}

}
