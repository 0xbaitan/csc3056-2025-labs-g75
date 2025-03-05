package org.jfree.data.test;

import static org.junit.Assert.*;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RangeTest {

	private Range rangeObjectUnderTest;
	private Range rangeObjectToTestForIntersects;

	@Before
	public void setUp() throws Exception {
		rangeObjectUnderTest = new Range(-1, 1);
		rangeObjectToTestForIntersects = new Range(-5, 5);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test

	public void testCentralValueShouldBeZero() {

		assertEquals("The central value of -1 and 1 should be 0", 0, rangeObjectUnderTest.getCentralValue(),
				0.000000001d);

	}

	// ------------------- combine function tests ---------------------

	/**
	 * TC1: range1 and range2 are both null
	 * 
	 * Combined range should be null
	 */

	@Test
	public void testCombineTC1() {
		Range range1 = null;
		Range range2 = null;
		Range combinedRange = Range.combine(range1, range2);
		assertNull(combinedRange);
	}

	/**
	 * TC2: range1 is valid and range2 is null
	 * 
	 * Combined range should match the value of range1
	 */
	@Test
	public void testCombineTC2() {
		Range range1 = new Range(5, 8);
		Range range2 = null;
		Range combinedRange = Range.combine(range1, range2);
		assertSame("Combined range is not the same as range1 with upper bound 8 and lower bound 5", range1,
				combinedRange);

	}

	/**
	 * TC3: range1 is null and range2 is valid
	 * 
	 * Combined range should match the value of range1
	 */
	@Test
	public void testCombineTC3() {
		Range range1 = null;
		Range range2 = new Range(-4.4, -2.2);
		Range combinedRange = Range.combine(range1, range2);
		assertSame("Combined range is not the same as range2 with upper bound -4.4 and lower bound -2.2", range2,
				combinedRange);

	}

	/**
	 * TC4: One of the ranges is invalid (where lower bound exceeds upper bound)
	 * 
	 * Expects an IllegalArgumentException to be thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCombineTC4() {
		Range range1 = null;
		Range range2 = new Range(4, 2); // An exception should be thrown
		Range.combine(range1, range2);
	}

	/**
	 * TC5: range1 overlaps with range2 where range1 is to the left of range2
	 * 
	 * Expects combined range to have the lower bound of range1 and upper bound of
	 * range2
	 */
	@Test
	public void testCombineTC5() {
		Range range1 = new Range(-583, -1.04);
		Range range2 = new Range(-3, 4);
		Range combinedRange = Range.combine(range1, range2);
		assertSame(
				"Combined range has incorrect bounds when ranges are overlapping. Expected lower bound is -5.83 and upper bound is 4",
				new Range(-5.83, 4), combinedRange);
	}

	/**
	 * TC6: range1 does not overlap with range2 where range1 is to the right of
	 * range2
	 * 
	 * Expects combined range to have the upper bound of range1 and lower bound of
	 * range2
	 */
	@Test
	public void testCombineTC6() {
		Range range1 = new Range(3, 7);
		Range range2 = new Range(-15, -10);
		Range combinedRange = Range.combine(range1, range2);
		assertSame(
				"Combined range has incorrect bounds when ranges are overlapping. Expected lower bound is -5.83 and upper bound is 4",
				new Range(-15, 7), combinedRange);
	}

	/**
	 * TC7: range2 is contained within range1
	 * 
	 * Expects combined range to match range1
	 */
	@Test
	public void testCombineTC7() {
		Range range1 = new Range(5, 7);
		Range range2 = new Range(6, 6);
		Range combinedRange = Range.combine(range1, range2);
		assertSame(
				"Combined range has incorrect bounds when ranges are overlapping. Expected lower bound is -5.83 and upper bound is 4",
				range1, combinedRange);
	}

	/**
	 * TC8: range1 is contained within range2
	 * 
	 * Expects combined range to match range2
	 */
	@Test
	public void testCombineTC8() {
		Range range1 = new Range(-7, -5);
		Range range2 = new Range(-10, -2);
		Range combinedRange = Range.combine(range1, range2);
		assertSame(
				"Combined range has incorrect bounds when ranges are overlapping. Expected lower bound is -5.83 and upper bound is 4",
				range2, combinedRange);
	}

	/**
	 * TC9: range1 is adjacent with range2 where range1 is to the left of range2
	 * 
	 * Expects combined range to have lower bound of range1 and upper bound of
	 * range2
	 */
	@Test
	public void testCombineTC9() {
		Range range1 = new Range(-4, 5);
		Range range2 = new Range(5, 10);
		Range combinedRange = Range.combine(range1, range2);
		assertSame(
				"Combined range has incorrect bounds when ranges are overlapping. Expected lower bound is -5.83 and upper bound is 4",
				new Range(-4, 10), combinedRange);
	}

	// ------------------- intersects function tests ---------------------

	/**
	 * SECT3.1: The input range completely contains the range object.
	 * 
	 * Expects {@code intersects} to return true.
	 */
	@Test
	public void testIntersects_ShouldBeTrue_OnFullContainment() {
		double lower = -10;
		double upper = 10;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertTrue("SECT3.1 - Intersects should be true. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);

	}

	/**
	 * SECT3.2: Input range partially overlaps with the range object from the left
	 * of the range object.
	 * 
	 * Expects {@code intersects} to return true.
	 */
	@Test
	public void testIntersects_ShouldBeTrue_OnLeftOverlap() {
		double lower = -10;
		double upper = 0;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertTrue("SECT3.2 - Intersects should be true. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.3: Input range partially overlaps with the range object from the right
	 * of the range object.
	 * 
	 * Expects {@code intersects} to return true.
	 */
	@Test
	public void testIntersects_ShouldBeTrue_OnRightOverlap() {
		double lower = 0;
		double upper = 10;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertTrue("SECT3.3 - Intersects should be true. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.4: Input range is left of the range object and does not overlap
	 * (assuming lower <= upper).
	 * 
	 * Expects {@code intersects} to return false.
	 */
	@Test
	public void testIntersects_ShouldBeFalse_OnLeftNonOverlap() {
		double lower = -10;
		double upper = -6;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertFalse("SECT3.4 - Intersects should be false. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.5: Input range is right of the range object and does not overlap
	 * (assuming lower <= upper).
	 * 
	 * Expects {@code intersects} to return false.
	 */
	@Test
	public void testIntersects_ShouldBeFalse_OnRightNonOverlap() {
		double lower = 6;
		double upper = 10;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertFalse("SECT3.5 - Intersects should be false. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.6: Input range is contained by the range object (assuming lower <=
	 * upper).
	 * 
	 * Expects {@code intersects} to return true.
	 */
	@Test
	public void testIntersects_ShouldBeTrue_OnBeingContained() {
		double lower = -1;
		double upper = 1;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertTrue("SECT3.6 - Intersects should be true. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.7: Lower bound exceeds upper bound (lower > upper).
	 * 
	 * Expects {@code intersects} to throw IllegalArgumentException.
	 */
	@Test()
	public void testIntersects_ShouldThrowException_OnLowerBoundExceedingUpperBound() {
		double lower = 10;
		double upper = -10;

		assertThrows("SECT3.7 - Should throw IllegalArgumentException when lower > upper",
				IllegalArgumentException.class, () -> {
					rangeObjectToTestForIntersects.intersects(lower, upper);

				});
	}

	/**
	 * SECT3.8 (BVA): Input range does not overlap with the range object from the
	 * left by a very small margin (upper = this.lower – δ).
	 * 
	 * Expects {@code intersects} to return false.
	 */
	@Test
	public void testIntersects_ShouldBeFalse_OnLeftNonOverlapByVerySmallMargin() {
		double lower = -10;
		double upper = -5.00001;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertFalse("SECT3.8 - Intersects should be false. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.9 (BVA): Input range overlaps with range object from the left just at
	 * the border (upper = this.lower).
	 * 
	 * Expects {@code intersects} to return true.
	 */
	@Test
	public void testIntersects_ShouldBeTrue_OnLeftOverlapAtExactlyTheLowerBound() {
		double lower = -10;
		double upper = -5;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertTrue("SECT3.9 - Intersects should be true. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}

	/**
	 * SECT3.10 (BVA): Input range is just contained by the range object (equal
	 * ranges).
	 * 
	 * Expects {@code intersects} to return true.
	 */
	@Test
	public void testIntersects_ShouldBeTrue_OnEqualRanges() {
		double lower = -5;
		double upper = 5;
		boolean isIntersecting = rangeObjectToTestForIntersects.intersects(lower, upper);
		assertTrue("SECT3.10 - Intersects should be true. Instead got: " + isIntersecting + " where input range is ("
				+ lower + ", " + upper + ") ", isIntersecting);
	}
}
