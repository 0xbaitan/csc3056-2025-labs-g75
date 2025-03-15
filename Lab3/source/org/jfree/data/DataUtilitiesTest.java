package org.jfree.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DataUtilitiesTest {
	private Values2D values2D;
	double[] testArray;
	double[][] testArray2D;
	private DefaultKeyedValues testKeyedValues;

	@Before
	public void setUp() throws Exception {
		// Values 2D for calcuateColumnTotal and calculateRowTotal tests
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		values2D = testValues;
		testValues.addValue(1.0, 0, 0);
		testValues.addValue(2.0, 0, 1);
		testValues.addValue(3.0, 1, 0);
		testValues.addValue(4.0, 1, 1);

		// Test arrays for createNumberArray and createNumberArray2D tests
		testArray = new double[] { 1.1, 2.0, 3.3, 4.0 };
		testArray2D = new double[][] { { 1.1, 2.2, 3.3 }, { 4.4, 5.5, 6.6 } };

		// KeyedValues for getCumulativePercentages test
		testKeyedValues = new DefaultKeyedValues();
		testKeyedValues.addValue("A", 5.0);
		testKeyedValues.addValue("B", 9.0);
		testKeyedValues.addValue("C", 2.0);
	}

	@After
	public void tearDown() throws Exception {
		values2D = null;
		testArray = null;
		testArray2D = null;
		testKeyedValues = null;
	}

	/**
	 * 
	 * TC1.1: A valid result should be returned on summing the columns.
	 * 
	 * Expects the sum of the second column.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldBeCorrect_OnValidColumn() {
		assertEquals("TC1.1 - Wrong sum returned. Expected 4.0", 4.0, DataUtilities.calculateColumnTotal(values2D, 0),
				0.0000001);
	}

	/**
	 * 
	 * TC1.2: Out of bounds column index.
	 * 
	 * Expects to throw an IndexOutOFBoundsException.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldThrowException_OnOutOfBoundsColumn() {
		assertThrows("TC1.2 - Expected an IndexOutOfBoundsException for out of bounds column index",
				IndexOutOfBoundsException.class, () -> DataUtilities.calculateColumnTotal(values2D, 3));
	}

	/**
	 * 
	 * TC1.3: Column contains all zeroes.
	 * 
	 * Expects 0 to be returned.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldBeZero_OnColumnWithZeros() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(0, 0, 0);
		testValues.addValue(0, 1, 0);
		values2D = testValues;
		assertEquals("TC1.3 - Expected sum of zero", 0, DataUtilities.calculateColumnTotal(values2D, 0), 0.0000001);
	}
	
	/**
	 * 
	 * TC1.4: Column contains negative values.
	 * 
	 * Expects a negative number to returned.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldBeCorrect_OnNegativeValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(-1, 0, 0);
		testValues.addValue(-3, 0, 1);
		testValues.addValue(-4, 1, 0);
		testValues.addValue(-6, 1, 1);
		values2D = testValues;
		assertEquals("TC1.4 - Wrong sum returned", -9, DataUtilities.calculateColumnTotal(values2D, 1), 0.000001);
	}

	/**
	 * 
	 * TC1.5: Column contains a mix of positive and negative values.
	 * 
	 * Expects a correct result to be returned.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldBeCorrect_OnColumnWithMixedValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(1, 0, 0);
		testValues.addValue(-2, 0, 1);
		testValues.addValue(-3, 1, 0);
		testValues.addValue(4, 1, 1);
		values2D = testValues;
		assertEquals("TC1.5 - Wrong sum returned", 2, DataUtilities.calculateColumnTotal(values2D, 1), 0.000001);
	}
	
	/**
	 * 
	 * TC1.6: Empty data.
	 * 
	 * Expects zero to be returned.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldBeZero_OnEmptyValues2D() {
		DefaultKeyedValues2D emptyValues = new DefaultKeyedValues2D();
		values2D = emptyValues;
		assertEquals("TC1.6 - Expected sum of zero for empty dataset", 0, DataUtilities.calculateColumnTotal(values2D, 0),
				0.0000001);
	}

	
	/**
	 * 
	 * TC1.7: Data is null.
	 * 
	 * Expects to throw an IllegalArgumentException.
	 */
	@Test
	public void testCalculateColumnTotal_ShouldThrowException_OnNullData() {
		assertThrows("TC1.7 - Expected an IllegalArgumentException", IllegalArgumentException.class, () -> {
			DataUtilities.calculateColumnTotal(null, 0);
		});

	}
	
	// -------------------------------------- calculateRowTotal functions -----------------------------------

	/**
	 * 
	 * TC2.1: Valid row index.
	 * 
	 */
	@Test
	public void testCalculateRowTotal_ShouldBeCorrect_OnValidRow() {
		assertEquals("TC2.1 - Wrong sum returned. It Should be: 3.0", 3.0, DataUtilities.calculateRowTotal(values2D, 0),
				0.0000001);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testCalculateRowTotal_ShouldThrowException_OnOutOfBoundsRow() {
		DataUtilities.calculateRowTotal(values2D, 5);
	}

	@Test
	public void testCalculateRowTotal_ShouldBeZero_OnRowWithZeros() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(0, 0, 0);
		testValues.addValue(0, 0, 1);
		values2D = testValues;
		assertEquals("Expected sum of zero", 0, DataUtilities.calculateRowTotal(values2D, 0), 0.0000001);
	}

	@Test
	public void testCalculateRowTotal_ShouldBeCorrect_OnNegativeValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(-4, 0, 0);
		testValues.addValue(-2, 2, 0);
		testValues.addValue(-1, 2, 1);
		values2D = testValues;
		assertEquals("Wrong sum returned", 7, DataUtilities.calculateRowTotal(values2D, 0), 0.000001);
	}

	@Test
	public void testCalculateRowTotal_ShouldBeZero_OnRowWithMixedValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(5, 0, 1);
		testValues.addValue(-5, 1, 0);
		values2D = testValues;
		assertEquals("Wrong sum returned", 0, DataUtilities.calculateRowTotal(values2D, 0), 0.000001);
	}

	@Test
	public void testCalculateRowTotal_ShouldBeZero_OnEmptyValues2D() {
		DefaultKeyedValues2D emptyValues = new DefaultKeyedValues2D();
		values2D = emptyValues;
		assertEquals("Expected sum of zero for empty dataset", 0, DataUtilities.calculateRowTotal(values2D, 0),
				0.0000001);
	}

	@Test()
	public void testCalculateRowTotal_ShouldThrowException_OnNullData() {
		assertThrows("Expected to throw IllegalArgumentException", IllegalArgumentException.class, () -> {
			DataUtilities.calculateRowTotal(null, 0);
		});
	}

	@Test
	public void testCalculateRowTotal_ShouldReturnNaN_OnRowWithNaNValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(Double.NaN, 0, 1);
		testValues.addValue(-5, 1, 0);
		values2D = testValues;
		assertEquals("Expected sum to be NaN", Double.isNaN(DataUtilities.calculateRowTotal(values2D, 0)));
	}

	// TC3

	@Test
	public void testCreateNumberArray_ShouldBeCorrect_OnValidInput() {
		Number[] result = DataUtilities.createNumberArray(testArray);
		assertArrayEquals("Conversion to Number[] Failed", new Number[] { 1.1, 2.0, 3.3, 4.0 }, result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateNumberArray_ShouldThrowException_OnNullInput() {
		DataUtilities.createNumberArray(null);
	}

	@Test
	public void testCreateNumberArray_ShouldReturnEmptyArray_OnEmptyInput() {
		double[] emptyArray = new double[] {};
		Number[] result = DataUtilities.createNumberArray(emptyArray);
		assertEquals("Expected empty Number[] array", 0, result.length);
	}

	@Test
	public void testCreateNumberArray_ShouldBeCorrect_OnArrayWithZeros() {
		double[] zeroArray = new double[] { 0.0, 0.0, 0.0, 0.0 };
		Number[] result = DataUtilities.createNumberArray(zeroArray);
		assertArrayEquals("Expected all zero values", new Number[] { 0.0, 0.0, 0.0, 0.0 }, result);
	}

	@Test
	public void testCreateNumberArray_ShouldBeCorrect_OnArrayWithNegativeValues() {
		double[] negativeArray = new double[] { -1, -3, -5, -7 };
		Number[] result = DataUtilities.createNumberArray(negativeArray);
		assertArrayEquals("Negative numbers not correctly converted", new Number[] { -1, -3, -5, -7 }, result);
	}

	@Test
	public void testCreateNumberArray_ShouldBeCorrect_OnArrayWithMixedValues() {
		double[] mixedArray = new double[] { -1, 0, 4, -6 };
		Number[] result = DataUtilities.createNumberArray(mixedArray);
		assertArrayEquals("Mixed numbers not converted properly", new Number[] { -1, 0, 4, -6 }, result);
	}

	@Test
	public void testCreateNumberArray_ShouldBeCorrect_OnArrayWithNaNValues() {
		double[] nanArray = new double[] { Double.NaN, 1, 2 };
		Number[] result = DataUtilities.createNumberArray(nanArray);
		assertTrue("Expected NaN in array", Double.isNaN(result[0].doubleValue()));
	}

	@Test
	public void testCreateNumberArray_ShouldBeCorrect_OnArrayWithLargeNumbers() {
		double[] largenumbers = new double[] { 1010, 2020, 3030 };
		Number[] result = DataUtilities.createNumberArray(largenumbers);
		assertArrayEquals("Large numbers incorrectly converted", new Number[] { 1010, 2020, 3030 }, result);
	}

	// TC4

	@Test
	public void testCreateNumberArray2D_ShouldBeCorrect_OnValidInput() {
		Number[][] result = DataUtilities.createNumberArray2D(testArray2D);
		assertArrayEquals("2D array conversion failed", new Number[][] { { 1.1, 2.2, 3.3 }, { 4.4, 5.5, 6.6 } },
				result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateNumberArray2D_ShouldThrowException_OnNullInput() {
		DataUtilities.createNumberArray2D(null);
	}

	@Test
	public void testCreateNumberArray2D_ShouldReturnEmptyArray_OnEmptyInput() {
		double[][] emptyArray = new double[][] {};
		Number[][] result = DataUtilities.createNumberArray2D(emptyArray);
		assertEquals("Expected empty Number[][] array", 0, result.length);
	}

	@Test
	public void testCreateNumberArray2D_ShouldBeCorrect_On2DArrayWithZeros() {
		double[][] zeroArray = new double[][] { { 0.0, 0.0 }, { 0.0, 0.0 } };
		Number[][] result = DataUtilities.createNumberArray2D(zeroArray);
		assertArrayEquals("Expected all zero values", new Number[][] { { 0.0, 0.0 }, { 0.0, 0.0 } }, result);
	}

	@Test
	public void testCreateNumberArray2D_ShouldBeCorrect_On2DArrayWithNegativeValues() {
		double[][] negativeArray = new double[][] { { -1.0, -3.0 }, { -5.0, -7.0 } };
		Number[][] result = DataUtilities.createNumberArray2D(negativeArray);
		assertArrayEquals("Negative numbers not correctly converted", new Number[][] { { -1.0, -3.0 }, { -5.0, -7.0 } },
				result);
	}

	@Ignore
	@Test
	public void testGetCumulativePercentages() {
		DefaultKeyedValues keyValues = new DefaultKeyedValues();
		keyValues.addValue((Comparable<Double>) 0.0, 6.0);
		keyValues.addValue((Comparable<Double>) 1.0, 11.0);
		keyValues.addValue((Comparable<Double>) 2.0, 3.0);
		KeyedValues objectUnderTest = DataUtilities.getCumulativePercentages(keyValues);
		assertEquals((double) objectUnderTest.getValue(2), 1.0, .000000001d);
	}

	@Test
	public void testCreateNumberArray2D_ShouldBeCorrect_On2DArrayWithMixedValues() {
		double[][] mixedArray = new double[][] { { -1.0, 2.2, -3.3 }, { 4.0, -6.0, 8.0 } };
		Number[][] result = DataUtilities.createNumberArray2D(mixedArray);
		assertArrayEquals("Mixed numbers not converted properly",
				new Number[][] { { -1.0, 2.2, -3.3 }, { 4.0, -6.0, 8.0 } }, result);
	}

	@Test
	public void testCreateNumberArray2D_ShouldBeCorrect_On2DArrayWithNaNValues() {
		double[][] nanArray = new double[][] { { Double.NaN, 1.0 }, { 2.0, 4.0 } };
		Number[][] result = DataUtilities.createNumberArray2D(nanArray);
		assertTrue("Array contains NaN which Should be allowed", Double.isNaN(result[0][0].doubleValue()));
	}

	@Test
	public void testCreateNumberArray2D_ShouldThrowException_OnJagged2DArray() {
		double[][] jaggedArray = new double[][] { { 1.1, 2.2, 3.3 }, { 4.0, 5.0 } };
		DataUtilities.createNumberArray2D(jaggedArray);
	}

	// TC5

	@Test
	public void testGetCumulativePercentages_ShouldBeCorrect_OnValidData() {
		KeyedValues result = DataUtilities.getCumulativePercentages(testKeyedValues);
		assertEquals("Incorrect cumulative percentage for A", 0.3125, result.getValue("A"));
		assertEquals("Incorrect cumulative percentage for B", 0.875, result.getValue("B"));
		assertEquals("Incorrect cumulative percentage for C", 1.0, result.getValue("C"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCumulativePercentages_ShouldThrowException_OnNullInput() {
		DataUtilities.getCumulativePercentages(null);
	}

	@Test
	public void testGetCumulativePercentages_ShouldReturnEmpty_OnEmptyKeyedValues() {
		DefaultKeyedValues emptyValues = new DefaultKeyedValues();
		KeyedValues result = DataUtilities.getCumulativePercentages(emptyValues);
		assertEquals("Expected empty result", 0, result.getItemCount());

	}

	@Test
	public void testGetCumulativePercentages_ShouldBeCorrect_OnSingleValue() {
		DefaultKeyedValues singlevalue = new DefaultKeyedValues();
		singlevalue.addValue("X", 10.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(singlevalue);
		assertEquals("Single Value Should have 100% cumulative percentage", 1.0, result.getValue("X"));

	}

	@Test
	public void testGetCumulativePercentages_ShouldHandleZeroValues_OnAllZeroInput() {
		DefaultKeyedValues zeroValues = new DefaultKeyedValues();
		zeroValues.addValue("A", 0.0);
		zeroValues.addValue("B", 0.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(zeroValues);

		double expectedA = Double.isNaN(result.getValue("A").doubleValue()) ? Double.NaN : 0.0;
		assertEquals("Cumulative percentage Should be NaN or 0.0 due to division by zero", expectedA,
				result.getValue("A").doubleValue(), 0.0001);
	}

	@Test
	public void testGetCumulativePercentages_ShouldBeCorrect_OnNegativeValues() {
		DefaultKeyedValues negativeValues = new DefaultKeyedValues();
		negativeValues.addValue("X", -5.0);
		negativeValues.addValue("Y", -10.0);
		negativeValues.addValue("Z", -15.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(negativeValues);
		assertEquals("Incorrect cumulative percentage for X", 0.1667, result.getValue("X").doubleValue(), 0.0001);
		assertEquals("Incorrect cumulative percentage for Y", 0.5, result.getValue("Y").doubleValue(), 0.0001);
		assertEquals("Incorrect cumulative percentage for Z", 1.0, result.getValue("Z").doubleValue(), 0.0001);
	}

	@Test
	public void testGetCumulativePercentages_ShouldHandleNaNValues_OnNaNInput() {
		DefaultKeyedValues nanValues = new DefaultKeyedValues();
		nanValues.addValue("A", Double.NaN);
		nanValues.addValue("B", 5.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(nanValues);
		assertTrue("NaN Should not be included in the cumulative percentages",
				Double.isNaN(result.getValue("A").doubleValue()));

	}

	@Test()
	public void testGetCumulativePercentages_ShouldThrowException_OnDuplicateKeys() {
		DefaultKeyedValues duplicateKeys = new DefaultKeyedValues();
		duplicateKeys.addValue("A", 5.0);
		duplicateKeys.addValue("A", 10.0);

		assertThrows(
				"Should throw IllegalArgumentException on trying to calculate cumulative percentages on duplicate keys",
				IllegalArgumentException.class, () -> {
					DataUtilities.getCumulativePercentages(duplicateKeys);
				});

	}

	@Test
	public void testGetCumulativePercentages_ShouldHandleInfinityValues_OnInfinityInput() {
		DefaultKeyedValues infinityValues = new DefaultKeyedValues();
		infinityValues.addValue("A", Double.POSITIVE_INFINITY);
		infinityValues.addValue("B", 5.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(infinityValues);
		assertNotEquals("Infinity values Should not be converted directly", Double.POSITIVE_INFINITY,
				result.getValue("A").doubleValue());
	}

}
