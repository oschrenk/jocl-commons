package de.q2web.jocl.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntegersTest {

	@Test
	public void testBinaryLog() {
		assertEquals(3, Integers.binaryLog(8));
		assertEquals(3, Integers.binaryLog(9));
		assertEquals(3, Integers.binaryLog(15));
		assertEquals(4, Integers.binaryLog(16));
	}

	@Test
	public void testNearestBinary() {
		assertEquals(2, Integers.nearestBinary(2));
		assertEquals(4, Integers.nearestBinary(3));
		assertEquals(4, Integers.nearestBinary(4));
		assertEquals(8, Integers.nearestBinary(5));
		assertEquals(16, Integers.nearestBinary(10));
		assertEquals(16, Integers.nearestBinary(16));
	}
}
