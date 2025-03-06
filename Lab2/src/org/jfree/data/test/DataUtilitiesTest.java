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
	        testArray = new double[] {1.1, 2.0, 3.3, 4.0};
	        testArray2D = new double[][] {
	            {1.1, 2.2, 3.3},
	            {4.4, 5.5, 6.6}
	        };

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

	// TC1
	@Test
    public void testCalculateColumnTotal_shouldBeCorrect_OnValidColumn() {
        assertEquals("Wrong sum returned. Expected 4.0", 4.0, DataUtilities.calculateColumnTotal(values2D, 0), 0.0000001);
    }

	//Should fail due to out of bounds column
	
    @Test(expected = IndexOutOfBoundsException.class)
    public void testCalculateColumnTotal_shouldThrowException_OnOutOfBoundsColumn() {
        DataUtilities.calculateColumnTotal(values2D, 3);
    }
	
    @Test
	public void testCalculateColumnTotal_shouldBeZero_OnColumnWithZeros() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(0, 0, 0);
		testValues.addValue(0, 1, 0);
		values2D = testValues;
		assertEquals("Expected sum of zero", 0, DataUtilities.calculateColumnTotal(values2D, 0), 0.0000001);
	}
    
	@Test
	public void testCalculateColumnTotal_shouldBeCorrect_OnNegativeValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(-1, 0, 0);
		testValues.addValue(-3, 1, 0);
		testValues.addValue(-5, 2, 1);
		values2D = testValues;
		assertEquals("Wrong sum returned", 7, DataUtilities.calculateColumnTotal(values2D, 0), 0.000001);
	}
	
	@Test
	public void testCalculateColumnTotal_shouldBeZero_OnColumnWithMixedValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(5, 0, 0);
		testValues.addValue(-5, 1, 0);
		testValues.addValue(6, 2, 1);
		values2D = testValues;
		assertEquals("Wrong sum returned", 0, DataUtilities.calculateColumnTotal(values2D, 0), 0.000001);
	}
	
	@Test
	public void testCalculateColumnTotal_shouldBeZero_OnEmptyValues2D() {
		DefaultKeyedValues2D emptyValues = new DefaultKeyedValues2D();
		values2D = emptyValues;
		assertEquals("Expected sum of sero for empty dataset", 0, DataUtilities.calculateColumnTotal(values2D, 0), 0.0000001);
	}
	
	//Should fail 
	
	@Test
	public void testCalculateColumnTotal_shouldThrowException_OnNullData() {
		DataUtilities.calculateColumnTotal(null, 0);
	}

	
	//TC2
	
	@Test
    public void testCalculateRowTotal_shouldBeCorrect_OnValidRow() {
        assertEquals("Wrong sum returned. It should be: 3.0", 3.0, DataUtilities.calculateRowTotal(values2D, 0), 0.0000001);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testCalculateRowTotal_shouldThrowException_OnOutOfBoundsRow() {
        DataUtilities.calculateRowTotal(values2D, 5);
    }
    
    @Test
	public void testCalculateRowTotal_shouldBeZero_OnRowWithZeros() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(0, 0, 0);
		testValues.addValue(0, 0, 1);
		values2D = testValues;
		assertEquals("Expected sum of zero", 0, DataUtilities.calculateRowTotal(values2D, 0), 0.0000001);
	}
    
	@Test
	public void testCalculateRowTotal_shouldBeCorrect_OnNegativeValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(-4, 0, 0);
		testValues.addValue(-2, 2, 0);
		testValues.addValue(-1, 2, 1);
		values2D = testValues;
		assertEquals("Wrong sum returned", 7, DataUtilities.calculateRowTotal(values2D, 0), 0.000001);
	}
	
	@Test
	public void testCalculateRowTotal_shouldBeZero_OnRowWithMixedValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(5, 0, 1);
		testValues.addValue(-5, 1, 0);
		values2D = testValues;
		assertEquals("Wrong sum returned", 0, DataUtilities.calculateRowTotal(values2D, 0), 0.000001);
	}
	
	@Test
	public void testCalculateRowTotal_shouldBeZero_OnEmptyValues2D() {
		DefaultKeyedValues2D emptyValues = new DefaultKeyedValues2D();
		values2D = emptyValues;
		assertEquals("Expected sum of zero for empty dataset", 0, DataUtilities.calculateRowTotal(values2D, 0), 0.0000001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCalculateRowTotal_shouldThrowException_OnNullData() {
		DataUtilities.calculateRowTotal(null, 0);
	}
	
	@Test
	public void testCalculateRowTotal_shouldReturnNaN_OnRowWithNaNValues() {
		DefaultKeyedValues2D testValues = new DefaultKeyedValues2D();
		testValues.addValue(Double.NaN, 0, 1);
		testValues.addValue(-5, 1, 0);
		values2D = testValues;
		assertEquals("Expected sum to be NaN", Double.isNaN(DataUtilities.calculateRowTotal(values2D, 0)));
	}

	//TC3
	
	 @Test
	 public void testCreateNumberArray_shouldBeCorrect_OnValidInput() {
		 Number[] result = DataUtilities.createNumberArray(testArray);
	     assertArrayEquals("Conversion to Number[] Failed", new Number[] {1.1, 2.0, 3.3, 4.0}, result);
	}

	 @Test(expected = IllegalArgumentException.class)
	 public void testCreateNumberArray_shouldThrowException_OnNullInput() {
	     DataUtilities.createNumberArray(null);
    }
	
	 @Test
	public void testCreateNumberArray_shouldReturnEmptyArray_OnEmptyInput() {
		double[] emptyArray = new double [] {};
		Number[] result = DataUtilities.createNumberArray(emptyArray);
		assertEquals("Expected empty Number[] array", 0, result.length);	
	}
	 
     @Test
	public void testCreateNumberArray_shouldBeCorrect_OnArrayWithZeros() {
		double[] zeroArray = new double[] {0.0,0.0,0.0,0.0};
		Number[] result = DataUtilities.createNumberArray(zeroArray);
		assertArrayEquals("Expected all zero values", new Number[] {0.0,0.0,0.0,0.0}, result);
	}
     
	@Test
	public void testCreateNumberArray_shouldBeCorrect_OnArrayWithNegativeValues() {
		double [] negativeArray = new double[] {-1,-3,-5,-7};
		Number[] result = DataUtilities.createNumberArray(negativeArray);
		assertArrayEquals("Negative numbers not correctly converted", new Number[] {-1,-3,-5,-7}, result);
	}

	@Test
	public void testCreateNumberArray_shouldBeCorrect_OnArrayWithMixedValues() {
		double[] mixedArray = new double[] {-1, 0, 4,-6};
		Number[] result = DataUtilities.createNumberArray(mixedArray);
		assertArrayEquals("Mixed numbers not converted properly", new Number[] {-1,0,4,-6}, result);
	}
		
	@Test
	public void testCreateNumberArray_shouldBeCorrect_OnArrayWithNaNValues()  {
		double[] nanArray = new double[] {Double.NaN, 1, 2};
		Number[] result = DataUtilities.createNumberArray(nanArray);
		assertTrue("Expected NaN in array", Double.isNaN(result[0].doubleValue()));
	}
	
	@Test
	public void testCreateNumberArray_shouldBeCorrect_OnArrayWithLargeNumbers() {
		double[] largenumbers = new double[] {1010, 2020, 3030};
		Number[] result = DataUtilities.createNumberArray(largenumbers);
		assertArrayEquals("Large numbers incorrectly converted", new Number[] {1010, 2020, 3030}, result);
	}
	
	//TC4
	
	@Test
    public void testCreateNumberArray2D_shouldBeCorrect_OnValidInput() {
        Number[][] result = DataUtilities.createNumberArray2D(testArray2D);
        assertArrayEquals("2D array conversion failed", new Number[][] {
            {1.1, 2.2, 3.3},
            {4.4, 5.5, 6.6}
        }, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberArray2D_shouldThrowException_OnNullInput()  {
        DataUtilities.createNumberArray2D(null);
    }
	
    @Test
	public void testCreateNumberArray2D_shouldReturnEmptyArray_OnEmptyInput() {
		double[][] emptyArray = new double [][] {};
		Number[][] result = DataUtilities.createNumberArray2D(emptyArray);
		assertEquals("Expected empty Number[][] array", 0, result.length);
	}
    
	@Test
	public void testCreateNumberArray2D_shouldBeCorrect_On2DArrayWithZeros() {
		double[][] zeroArray = new double[][] {{0.0,0.0}, {0.0,0.0}};
		Number[][] result = DataUtilities.createNumberArray2D(zeroArray);
		assertArrayEquals("Expected all zero values", new Number[][] {{0.0,0.0}, {0.0,0.0}}, result);
	}
	
	@Test
	public void testCreateNumberArray2D_shouldBeCorrect_On2DArrayWithNegativeValues() {
		double [][] negativeArray = new double[][] {{-1.0,-3.0}, {-5.0,-7.0}};
		Number[][] result = DataUtilities.createNumberArray2D(negativeArray);
		assertArrayEquals("Negative numbers not correctly converted", new Number[][] {{-1.0,-3.0}, {-5.0,-7.0}}, result);
	}

	@Test
	public void testCreateNumberArray2D_shouldBeCorrect_On2DArrayWithMixedValues() {
		double[][] mixedArray = new double[][] {{-1.0, 2.2, -3.3},{4.0,-6.0, 8.0}};
		Number[][] result = DataUtilities.createNumberArray2D(mixedArray);
		assertArrayEquals("Mixed numbers not converted properly", new Number[][] {{-1.0, 2.2, -3.3},{4.0,-6.0, 8.0}}, result);
	}
	
	@Test
	public void testCreateNumberArray2D_shouldBeCorrect_On2DArrayWithNaNValues() {
		double[][] nanArray = new double[][] {{Double.NaN, 1.0}, {2.0, 4.0}};
		Number[][] result = DataUtilities.createNumberArray2D(nanArray);
		assertTrue("Array contains NaN which should be allowed", Double.isNaN(result[0][0].doubleValue()));
	}
	
	@Test
	public void testCreateNumberArray2D_shouldThrowException_OnJagged2DArray() {
		double[][] jaggedArray = new double[][] {{1.1,2.2,3.3}, {4.0,5.0}};
		DataUtilities.createNumberArray2D(jaggedArray);	
	}
	
	//TC5
	
	@Test
	public void testGetCumulativePercentages_shouldBeCorrect_OnValidData() {
		 KeyedValues result = DataUtilities.getCumulativePercentages(testKeyedValues);
		 assertEquals("Incorrect cumulative percentage for A", 0.3125, result.getValue("A"));
		 assertEquals("Incorrect cumulative percentage for B", 0.875, result.getValue("B"));
		 assertEquals("Incorrect cumulative percentage for C", 1.0, result.getValue("C"));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testGetCumulativePercentages_shouldThrowException_OnNullInput() {
		DataUtilities.getCumulativePercentages(null);
	}
	@Test
	public void testGetCumulativePercentages_shouldReturnEmpty_OnEmptyKeyedValues() {
		DefaultKeyedValues emptyValues = new DefaultKeyedValues();
		KeyedValues result = DataUtilities.getCumulativePercentages(emptyValues);
		assertEquals("Expected empty result", 0, result.getItemCount());
	
	}
	@Test
	public void testGetCumulativePercentages_shouldBeCorrect_OnSingleValue() {
		DefaultKeyedValues singlevalue = new DefaultKeyedValues();
		singlevalue.addValue("X",  10.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(singlevalue);
		assertEquals("Single Value should have 100% cumulative percentage", 1.0, result.getValue("X"));
		
	}
	@Test
	public void testGetCumulativePercentages_shouldHandleZeroValues_OnAllZeroInput() {
		DefaultKeyedValues zeroValues = new DefaultKeyedValues();
		zeroValues.addValue("A", 0.0);
		zeroValues.addValue("B", 0.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(zeroValues);
		
		double expectedA = Double.isNaN(result.getValue("A").doubleValue()) ? Double.NaN : 0.0;
		assertEquals("Cumulative percentage should be NaN or 0.0 due to division by zero", expectedA, result.getValue("A").doubleValue(), 0.0001);
	}

	@Test
	public void testGetCumulativePercentages_shouldBeCorrect_OnNegativeValues() {
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
	public void testGetCumulativePercentages_shouldHandleNaNValues_OnNaNInput() {
		DefaultKeyedValues nanValues = new DefaultKeyedValues();
		nanValues.addValue("A", Double.NaN);
		nanValues.addValue("B", 5.0);
		KeyedValues result = DataUtilities.getCumulativePercentages(nanValues);
		assertTrue("NaN should not be included in the cumulative percentages", Double.isNaN(result.getValue("A").doubleValue()));
		
	}
	@Test (expected = IllegalArgumentException.class)
	public void testGetCumulativePercentages_shouldThrowException_OnDuplicateKeys() {
		DefaultKeyedValues duplicateKeys = new DefaultKeyedValues();
		duplicateKeys.addValue("A", 5.0);
		duplicateKeys.addValue("A", 10.0);
		
		KeyedValues result = DataUtilities.getCumulativePercentages(duplicateKeys);	
		assertEquals("Cumulative percentage should be 1.0", 1.0, result.getValue("A").doubleValue(), 0.0001);
	}
	
	@Test
    public void testGetCumulativePercentages_shouldHandleInfinityValues_OnInfinityInput() {
        DefaultKeyedValues infinityValues = new DefaultKeyedValues();
        infinityValues.addValue("A", Double.POSITIVE_INFINITY);
        infinityValues.addValue("B", 5.0);
        KeyedValues result = DataUtilities.getCumulativePercentages(infinityValues);
        assertNotEquals("Infinity values should not be converted directly", Double.POSITIVE_INFINITY, result.getValue("A").doubleValue());
    }

}
