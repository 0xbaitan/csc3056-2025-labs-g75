package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.KeyedValues;
import org.jfree.data.Values2D;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DataUtilitiesTest {
	private Values2D values2D;

	@Before
	public void setUp() throws Exception {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		values2D = testValues;
		testValues.addValue(1, 0, 0);
		testValues.addValue(4, 1, 0);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidDataAndColumnTotal() {
		assertEquals("Wrong sum returned. It should be 5.0", 5.0, DataUtilities.calculateColumnTotal(values2D, 0),
				0.0000001d);
	}
	
	
	@Test
	public void testGetCumulativePercentages() {
		DefaultKeyedValues keyValues = new DefaultKeyedValues();
		keyValues.addValue((Comparable<Double>) 0.0, 6.0);
		keyValues.addValue((Comparable<Double>) 1.0, 11.0);
		keyValues.addValue((Comparable<Double>) 2.0, 3.0);
		KeyedValues objectUnderTest = DataUtilities.getCumulativePercentages(keyValues);
		assertEquals((double) objectUnderTest.getValue(2), 1.0,  .000000001d);
	}

}
