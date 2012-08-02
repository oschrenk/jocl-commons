package de.q2web.jocl.util;

import java.util.Random;

/**
 * Offers static helper methods for arrays.
 */
public class Arrays {

	/** The Constant NOT_FOUND. */
	private static final int NOT_FOUND = -1;

	/**
	 * Prefilled.
	 * 
	 * @param length
	 *            the length
	 * @param value
	 *            the value
	 * @return the boolean[]
	 */
	public static boolean[] prefilled(final int length, final boolean value) {
		final boolean a[] = new boolean[length];
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
	public static byte[] prefilled(final int length, final byte value) {
		final byte a[] = new byte[length];
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
	public static char[] prefilled(final int length, final char value) {
		final char a[] = new char[length];
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
	public static float[] prefilled(final int length, final float value) {
		final float a[] = new float[length];
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
	public static double[] prefilled(final int length, final double value) {
		final double a[] = new double[length];
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
	public static short[] prefilled(final int length, final short value) {
		final short a[] = new short[length];
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
	public static int[] prefilled(final int length, final int value) {
		final int a[] = new int[length];
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
	public static long[] prefilled(final int length, final long value) {
		final long a[] = new long[length];
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
	public static final int positionOfMinimum(final byte[] values) {
		byte min = Byte.MAX_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMinimum(final short[] values) {
		short min = Short.MAX_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMinimum(final int[] values) {
		int min = Integer.MAX_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMinimum(final long[] values) {
		long min = Long.MAX_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMinimum(final float[] values) {
		float min = Float.MAX_VALUE;
		int pos = -1;
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
	public static final int positionOfMinimum(final double[] values) {
		double min = Double.MAX_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMaximum(final byte[] values) {
		byte max = Byte.MIN_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMaximum(final short[] values) {
		short max = Short.MIN_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMaximum(final int[] values) {
		int max = Integer.MIN_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMaximum(final long[] values) {
		long max = Long.MIN_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMaximum(final float[] values) {
		float max = Float.MIN_VALUE;
		int pos = NOT_FOUND;
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
	public static final int positionOfMaximum(final double[] values) {
		double max = Double.MIN_VALUE;
		int pos = NOT_FOUND;
		for (int i = 0; i < values.length; i++) {
			final double v = values[i];
			if (v > max) {
				max = v;
				pos = i;
			}
		}
		return pos;
	}

	/**
	 * Fill an array with random floats between a lower and an upper bound.
	 * 
	 * @param length
	 *            the length
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 * @return the float[]
	 */
	public static float[] random(final int length, final float lowerBound,
			final float upperBound) {
		final Random random = new Random();
		final float values[] = new float[length];
		for (int i = 0; i < values.length; i++) {
			values[i] = random.nextFloat() * (upperBound - lowerBound)
					+ lowerBound;
		}
		return values;
	}

	/**
	 * Fill an array with random doubles between a lower and an upper bound..
	 * 
	 * @param length
	 *            the length
	 * @param lowerBound
	 *            the lower bound
	 * @param upperBound
	 *            the upper bound
	 * @return the double[]
	 */
	public static double[] random(final int length, final double lowerBound,
			final double upperBound) {
		final Random random = new Random();
		final double values[] = new double[length];
		for (int i = 0; i < values.length; i++) {
			values[i] = random.nextDouble() * (upperBound - lowerBound)
					+ lowerBound;
		}
		return values;
	}

	/**
	 * Fill an array with random floats that conform to gaussian distribution.
	 * 
	 * @param length
	 *            the length
	 * @param mean
	 *            the mean
	 * @param variance
	 *            the variance
	 * @return the float[]
	 */
	public static float[] randomGaussian(final int length, final float mean,
			final float variance) {
		final Random random = new Random();
		final float values[] = new float[length];
		for (int i = 0; i < values.length; i++) {
			values[i] = (float) (mean + random.nextGaussian() * variance);
		}
		return values;
	}

	/**
	 * Fill an array with random doubles that conform to gaussian distribution.
	 * 
	 * @param length
	 *            the length
	 * @param mean
	 *            the mean
	 * @param variance
	 *            the variance
	 * @return the double[]
	 */
	public static double[] randomGaussian(final int length, final double mean,
			final double variance) {
		final Random random = new Random();
		final double values[] = new double[length];
		for (int i = 0; i < values.length; i++) {
			values[i] = (mean + random.nextGaussian() * variance);
		}
		return values;
	}
}
