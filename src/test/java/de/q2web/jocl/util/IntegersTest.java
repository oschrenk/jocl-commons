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
		assertEquals(2, Integers.nextBinary(2));
		assertEquals(4, Integers.nextBinary(3));
		assertEquals(4, Integers.nextBinary(4));
		assertEquals(8, Integers.nextBinary(5));
		assertEquals(16, Integers.nextBinary(10));
		assertEquals(16, Integers.nextBinary(16));
	}
}
