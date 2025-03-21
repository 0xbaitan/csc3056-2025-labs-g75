package org.jfree.data;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jfree.data.Range;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class RangeTest {

	private Range rangeObjectUnderTest;
	private Range rangeObjectToTestForIntersects;
	private Range rangeObjectToTestForConstrain;

	@Before
	public void setUp() throws Exception {
		rangeObjectUnderTest = new Range(-1, 1);
		rangeObjectToTestForIntersects = new Range(-5, 5);
		rangeObjectToTestForConstrain = new Range(-5, 5);
	}

	@After
	public void tearDown() throws Exception {
		rangeObjectUnderTest = null;
		rangeObjectToTestForIntersects = null;
		rangeObjectToTestForConstrain = null;
	}

	@Ignore
	@Test
	public void testCentralValueShouldBeZero() {
		assertEquals("The central value of -1 and 1 should be 0", 0, rangeObjectUnderTest.getCentralValue(),
				0.000000001d);
	}

	// ------------------- combine function tests ---------------------

	/**
	 * 
	 * SECT1.1: First range is null, and second range is a valid range.
	 * 
	 * Expected that the second range should be returned.
	 */
	@Test
	public void testCombine_ShouldReturnSecondRange_WhenFirstRangeIsNull() {
		Range range1 = null;
		Range range2 = new Range(5, 6);
		Range combinedRange = Range.combine(range1, range2);
		assertEquals("SECT1.1: Combined range should be the second range when first range is null", range2,
				combinedRange);
	}

	/**
	 * 
	 * SECT1.2: Second range is null, and the first range is valid.
	 * 
	 * Expected that the first range should be returned.
	 */
	@Test
	public void testCombine_ShouldReturnFirstRange_WhenSecondRangeIsNull() {
		Range range1 = new Range(5, 8);
		Range range2 = null;
		Range combinedRange = Range.combine(range1, range2);
		assertEquals("SECT1.2: Combined range should be the first range when second range is null", range1,
				combinedRange);
	}

	/**
	 * SECT1.3: Both ranges are null.
	 * 
	 * Expected that the combined range should be null too.
	 */
	@Test
	public void testCombine_ShouldReturnNull_WhenBothRangesAreNull() {
		Range range1 = null;
		Range range2 = null;
		Range combinedRange = Range.combine(range1, range2);
		assertNull("SECT1.3 Combined range should be null when both ranges are null", combinedRange);
	}

	/**
	 * 
	 * SECT1.4: Both ranges are valid and overlapping.
	 * 
	 * Expected that the combined range should span from the lower bound of the
	 * first range to the upper bound of the second range.
	 */
	@Test
	public void testCombine_ShouldReturnCombinedRange_WhenRangesOverlap() {
		Range range1 = new Range(7.5, 15);
		Range range2 = new Range(5, 10);
		Range combinedRange = Range.combine(range1, range2);
		Range expectedRange = new Range(5, 15);
		assertEquals("SECT1.4: Combined range should span from 5 to 15", expectedRange, combinedRange);
	}

	/**
	 * 
	 * SECT1.5: Both ranges are valid but do not overlap.
	 * 
	 * Expected that the combined range should span from the lower bound of the
	 * first range to the upper bound of the second range.
	 */
	@Test
	public void testCombine_ShouldReturnCombinedRange_WhenRangesDoNotOverlap() {
		Range range1 = new Range(15, 20);
		Range range2 = new Range(4, 10);
		Range combinedRange = Range.combine(range1, range2);
		Range expectedRange = new Range(4, 20);
		assertEquals("SECT1.5: Combined range should span from 4 to 20", expectedRange, combinedRange);
	}

	/**
	 * 
	 * SECT1.6: Second range contains the first range.
	 * 
	 * Expected that the second range should be returned.
	 */
	@Test
	public void testCombine_ShouldReturnLargerRange_WhenOneRangeContainsTheOtherRange() {
		Range range1 = new Range(6, 6);
		Range range2 = new Range(5, 7);
		Range combinedRange = Range.combine(range1, range2);
		assertEquals("SECT1.6: Combined range should be the second range", range2, combinedRange);
	}

	/**
	 * 
	 * SECT1.7: Both ranges are adjacent to each other.
	 * 
	 * Expected that the combined range should span from the lower bound of the
	 * first range to the upper bound of the second range.
	 */
	@Test
	public void testCombine_ShouldReturnCombinedRange_WhenRangesAreAdjacent() {
		Range range1 = new Range(2, 4);
		Range range2 = new Range(-6, 2);
		Range combinedRange = Range.combine(range1, range2);
		Range expectedRange = new Range(-6, 4);
		assertEquals("SECT1.7: Combined range should span from -6 to 4", expectedRange, combinedRange);
	}

	/**
	 * 
	 * SECT1.8: Both ranges are a single value range.
	 * 
	 * Expected that the combined range should span from the lower value to the
	 * upper value.
	 */
	@Test
	public void testCombine_ShouldReturnCombinedRange_WhenBothRangesAreSingleValue() {
		Range range1 = new Range(5, 5);
		Range range2 = new Range(-5, -5);
		Range combinedRange = Range.combine(range1, range2);
		Range expectedRange = new Range(-5, 5);
		assertEquals("SECT1.8: Combined range should span from -5 to 5", expectedRange, combinedRange);
	}

	/**
	 * 
	 * SECT1.9: Both ranges are identical.
	 * 
	 * Expected that the same range should be returned.
	 */
	@Test
	public void testCombine_ShouldReturnSameRange_WhenBothRangesAreIdentical() {
		Range range1 = new Range(15, 19);
		Range range2 = new Range(15, 19);
		Range combinedRange = Range.combine(range1, range2);
		assertEquals("SECT1.9: Combined range should be the same as both input ranges", range1, combinedRange);
	}

	// ------------------- expandToInclude function tests ----------------

	/**
	 * SECT2.1: The value is left of the range.
	 * 
	 * Expects the range expand and have the value at the lower bound.
	 */
	@Test
	public void testExpandToInclude_ShouldExpandTLowerBoundToValue_OnValueLeftOfRange() {
		Range range = new Range(-3, 5);
		double value = -4;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(-4, 5);
		assertEquals("SECT2.1 - The value should be at the lower bound of the range but is not", expectedRange,
				expandedRange);

	}

	/**
	 * 
	 * SECT2.2: Value is within the range.
	 * 
	 * Expects the range to remain unchanged.
	 */
	@Test
	public void testExpandToInclude_ShouldNotChangeRange_OnValueWithinRange() {
		Range range = new Range(-3, 5);
		double value = 0;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(-3, 5);
		assertEquals("SECT2.2 - The range should remain unchanged but it did not", expectedRange, expandedRange);
	}

	/**
	 * 
	 * SECT2.3: Value is right of the range.
	 * 
	 * Expects the range to expand and have the value at the upper bound.
	 */
	@Test
	public void testExpandToInclude_ShouldExpandToUpperBound_OnValueRightOfRange() {
		Range range = new Range(-3, 5);
		double value = 7;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(-3, 7);
		assertEquals("SECT2.3 - The value should be at the upper bound of the range but is not", expectedRange,
				expandedRange);
	}

	/**
	 * 
	 * SECT2.4: Value is not a number.
	 * 
	 * Expects the range to remain unchanged.
	 */
	@Test
	public void testExpandToInclude_ShouldNotChangeRange_OnValueIsNaN() {
		Range range = new Range(5, 5);
		double value = Double.NaN;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(5, 5);
		assertEquals("SECT2.4 - The range should remain unchanged when value is NaN but it did not", expectedRange,
				expandedRange);
	}

	/**
	 * 
	 * SECT2.5: Range is null.
	 * 
	 * Expects an InvalidParameterException to be thrown.
	 */
	@Test()
	public void testExpandToInclude_ShouldThrowException_OnRangeIsNull() {
		Range range = null;
		double value = 5;

		assertThrows("SECT2.5 - An InvalidParameterException should be thrown when the range passed is null",
				InvalidParameterException.class, () -> {
					Range.expandToInclude(range, value);
				});
	}

	/**
	 * 
	 * SECT2.6 (BVA): Value is at lower bound of the range.
	 * 
	 * Expects the range to remain unchanged.
	 */
	@Test
	public void testExpandToInclude_ShouldNotChangeRange_OnValueAtLowerBound() {
		Range range = new Range(3, 5);
		double value = 3;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(3, 5);
		assertEquals("SECT2.6 - The range should remain unchanged when value is at lower bound but it did not",
				expectedRange, expandedRange);
	}

	/**
	 * 
	 * SECT2.7 (BVA): Value is at upper bound of the range.
	 * 
	 * Expects the range to remain unchanged.
	 */
	@Test
	public void testExpandToInclude_ShouldNotChangeRange_OnValueAtUpperBound() {
		Range range = new Range(3, 5);
		double value = 5;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(3, 5);
		assertEquals("SECT2.7 - The range should remain unchanged when value is at upper bound but it did not",
				expectedRange, expandedRange);
	}

	/**
	 * 
	 * SECT2.8 (BVA): Value is just left of the lower bound by a small margin.
	 * 
	 * Expects the range to expand to include the value at the lower bound.
	 */
	@Test
	public void testExpandToInclude_ShouldExpandLowerBoundToValue_OnValueJustLeftOfLowerBound() {
		Range range = new Range(-3, 5);
		double value = -3.000001;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(-3.000001, 5);
		assertEquals("SECT2.8 - The range should expand to include the value at the lower bound but it did not",
				expectedRange, expandedRange);
	}

	/**
	 * 
	 * SECT2.9 (BVA): Value is just right of the upper bound by a small margin.
	 * 
	 * Expects the range to expand to include the value at the upper bound.
	 */
	@Test
	public void testExpandToInclude_ShouldExpandUpperBoundToValue_OnValueJustRightOfUpperBound() {
		Range range = new Range(-3, 5);
		double value = 5.000001;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(-3, 5.000001);
		assertEquals("SECT2.9 - The range should expand to include the value at the upper bound but it did not",
				expectedRange, expandedRange);
	}

	/**
	 * 
	 * SECT2.10 (BVA): Value is within equal range.
	 * 
	 * Expects the range to remain unchanged.
	 */
	@Test
	public void testExpandToInclude_ShouldNotChangeRange_OnValueWithinEqualRange() {
		Range range = new Range(5, 5);
		double value = 5;
		Range expandedRange = Range.expandToInclude(range, value);
		Range expectedRange = new Range(5, 5);
		assertEquals("SECT2.10 - The range should remain unchanged when value is within equal range but it did not",
				expectedRange, expandedRange);
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

	// ------------------- shift function tests --------------------------

	/*
	 * SECT4.1: Range with negative bounds is shifted rightwards by an
	 * inconsequential delta.
	 * 
	 * Expects the range to be naively shifted towards the rightward with no bound
	 * crossing the zero mark so there is no clamping.
	 */
	@Test
	public void testShift_ShouldShiftRightWithoutClamping_OnNegativeRangeAndInconsequentialPositiveDelta() {
		Range base = new Range(-10, -5);
		double delta = 4;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(-6, -1);
		assertEquals("SECT4.1 - Range should be naively shifted rightwards without clamping", expectedRange,
				shiftedRange);
	}

	/**
	 * 
	 * SECT4.2: Range with positive bounds is shifted leftwards by an
	 * inconsequential delta.
	 * 
	 * Expects the range to be naively shifted towards the left with no bound
	 * crossing the zero mark so there is no clamping.
	 */
	@Test
	public void testShift_ShouldShiftLeftWithoutClamping_OnPositiveRangeAndInconsequentialNegativeDelta() {
		Range base = new Range(5, 10);
		double delta = -4;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(1, 6);
		assertEquals("SECT4.2 - Range should be naively shifted leftwards without clamping", expectedRange, shiftedRange);
	}

	/**
	 * 
	 * SECT4.3: Range with one positive and one negative bound is shifted rightwards
	 * with zero crossing on the lower bound.
	 * 
	 * Expects the range to be shifted rightwards with the lower bound clamped at
	 * zero and the size maintained.
	 */
	@Test
	public void testShift_ShouldClampAtZeroAndMaintainSize_OnStraddlingRangeAndPositiveDelta() {
		Range base = new Range(-4, 5);
		double delta = 5;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(0, 9);
		assertEquals("SECT4.3 - Range should be shifted rightwards with lower bound clamped at zero and size maintained",
				expectedRange, shiftedRange);
	}

	/**
	 * 
	 * SECT4.4: Range with one positive and one negative bound is shifted leftwards
	 * with zero crossing on the upper bound.
	 * 
	 * Expects the range to be shifted leftwards with the upper bound clamped at
	 * zero and the size maintained.
	 */
	@Test
	public void testShift_ShouldClampAtZeroAndMaintainSize_OnStraddlingRangeAndNegativeDelta() {
		Range base = new Range(-5, 4);
		double delta = -5;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(-9, 0);
		assertEquals("SECT4.4 - Range should be shifted leftwards with upper bound clamped at zero and size maintained",
				expectedRange, shiftedRange);
	}

	/**
	 * 
	 * SECT4.5: Range shifted by 0.
	 * 
	 * Expects the range to remain unchanged.
	 */
	@Test
	public void testShift_ShouldNotChangeRange_OnAnyRangeAndZeroDelta() {
		Range base = new Range(-5, 0);
		double delta = 0;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(-5, 0);
		assertEquals("SECT4.5 - Range should remain unchanged when shifted by zero", expectedRange, shiftedRange);
	}

	/**
	 * 
	 * SECT4.6: Null range.
	 * 
	 * Expects an InvalidParameterException to be thrown.
	 */
	@Test()
	public void testShift_ShouldThrowException_OnNullRangeAndAnyDelta() {
		Range base = null;
		double delta = 5;

		assertThrows("SECT4.6: Expects an InvalidParameterException when base range is null",
				InvalidParameterException.class, () -> {
					Range.shift(base, delta);
				});

	}

	/**
	 * 
	 * SECT4.7: Range shifted by NaN.
	 * 
	 * Expects a range with NaN bounds to be returned.
	 */
	@Test
	public void testShift_ShouldReturnNaNRange_OnZeroRangeAndNaNDelta() {
		Range base = new Range(0, 0);
		double delta = Double.NaN;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(Double.NaN, Double.NaN);
		assertEquals("SECT4.7 - Range should be a not a number range when NaN is passed as delta", expectedRange,
				shiftedRange);
	}

	/**
	 * 
	 * SECT4.8 (BVA): Range with equal bounds at 0 is shifted rightwards.
	 * 
	 * Expects the range to be shifted rightwards without clamping.
	 */
	@Test
	public void testShift_ShouldShiftRightFromZero_OnZeroRangeAndPositiveDelta() {
		Range base = new Range(0, 0);
		double delta = 5;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(5, 5);
		assertEquals("SECT4.8 - Range should be shifted rightwards from zero", expectedRange, shiftedRange);
	}

	/**
	 * 
	 * SECT4.9 (BVA): Range with equal bounds at 0 is shifted leftwards.
	 * 
	 * Expects the range to be shifted leftwards without clamping.
	 */
	@Test
	public void testShift_ShouldShiftLeftFromZero_OnZeroRangeAndNegativeDelta() {
		Range base = new Range(0, 0);
		double delta = -5;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(-5, -5);
		assertEquals("SECT4.9 - Range should be shifted leftwards from zero", expectedRange, shiftedRange);
	}

	/**
	 * 
	 * SECT4.10 (BVA): Range with both negative bounds crossing zero on shifting
	 * rightwards.
	 * 
	 * Expects the range to be shifted rightwards with the upper bound clamped at
	 * zero and the size maintained.
	 */
	@Test
	public void testShift_ShouldClampAtZeroAndMaintainSize_OnNegativeRangeShiftRightCrossingZero() {
		Range base = new Range(-3, -1);
		double delta = 4;
		Range shiftedRange = Range.shift(base, delta);
		Range expectedRange = new Range(-2, 0);
		assertEquals("SECT4.10 - Range should be shifted rightwards with upper bound clamped at zero and size maintained",
				expectedRange, shiftedRange);
	}

	// ------------------- constrains function tests ---------------------

	/**
	 * SECT5.1: Value is within bounds.
	 * 
	 * Expects {@code constrain} to return the same value.
	 */
	@Test
	public void testConstrain_ShouldBeSame_OnValueWithinRange() {
		double value = 0;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.1 - Same value (" + value + ") to be returned but instead got: " + constrainedValue, value,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.2: Value is to the left of the range’s lower bound.
	 * 
	 * Expects {@code constrain} to return the lower bound.
	 */
	@Test
	public void testConstrain_ShouldBeLowerBound_OnValueLeftOfLowerBound() {
		double value = -10;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.2 - Lower bound (-5) to be returned but instead got: " + constrainedValue, -5,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.3: Value is to the right of the range’s upper bound.
	 * 
	 * Expects {@code constrain} to return the upper bound.
	 */
	@Test
	public void testConstrain_ShouldBeUpperBound_OnValueRightOfUpperBound() {
		double value = 10;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.3 - Upper bound (5) to be returned but instead got: " + constrainedValue, 5,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.4: Not a number.
	 * 
	 * Expects {@code constrain} to return NaN.
	 */
	@Test
	public void testConstrain_ShouldBeNaN_OnValueBeingNaN() {
		double value = Double.NaN;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertTrue("SECT5.4 - Should be NaN but instead got: " + constrainedValue, Double.isNaN(constrainedValue));
	}

	/**
	 * SECT5.5 (BVA): Value is to the right of the range’s lower bound by a very
	 * small margin.
	 * 
	 * Expects {@code constrain} to return the same value.
	 */
	@Test
	public void testConstrain_ShouldBeSame_OnValueRightOfLowerBoundBySmallMargin() {
		double value = -4.999999;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.5 - Same value (" + value + ") to be returned but instead got: " + constrainedValue, value,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.6 (BVA): Value is to the left of the range’s upper bound by a very
	 * small margin.
	 * 
	 * Expects {@code constrain} to return the same value.
	 */
	@Test
	public void testConstrain_ShouldBeSame_OnValueLeftOfUpperBoundBySmallMargin() {
		double value = 4.999999;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.6 - Same value (" + value + ") to be returned but instead got: " + constrainedValue, value,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.7 (BVA): Value is just at the lower bound.
	 * 
	 * Expects {@code constrain} to return the lower bound.
	 */
	@Test
	public void testConstrain_ShouldBeUpperBound_OnValueBeingUpperBound() {
		double value = 5;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.7 - Value (5) to be returned but instead got: " + constrainedValue, value, constrainedValue,
				1e-6);
	}

	/**
	 * SECT5.8 (BVA): Value is just at the upper bound.
	 * 
	 * Expects {@code constrain} to return the upper bound.
	 */
	@Test
	public void testConstrain_ShouldBeLowerBound_OnValueBeingLowerBound() {
		double value = -5;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.8 - value (-5) to be returned but instead got: " + constrainedValue, value,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.9 (BVA): Value is just left of the lower bound.
	 * 
	 * Expects {@code constrain} to return the lower bound.
	 */
	@Test
	public void testConstrain_ShouldBeLowerBound_OnValueLeftOfLowerBoundBySmallMargin() {
		double value = -5.000001;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.9 - Lower bound (-5) to be returned but instead got: " + constrainedValue, -5,
				constrainedValue, 1e-6);
	}

	/**
	 * SECT5.10 (BVA): Value is just right of the upper bound.
	 * 
	 * Expects {@code constrain} to return the upper bound.
	 */
	@Test
	public void testConstrain_ShouldBeUpperBound_OnValueRightOfUpperBoundBySmallMargin() {
		double value = 5.000001;
		double constrainedValue = rangeObjectToTestForConstrain.constrain(value);
		assertEquals("SECT5.10 - Upper bound (5) to be returned but instead got: " + constrainedValue, 5,
				constrainedValue, 1e-6);
	}
	
	
	// ------------------------------ White Box Testing -----------------------------------------
	
	@Test
	public void testRangeConstructor_ShouldThrowException_OnLowerBoundExceedingUpperBound() {
		double lowerBound = 10;
		double upperBound = 5; 
		// upperBound exceeds lowerBound
		assertThrows("WBT-R-1.1: IllegalArgumentException expected but not thrown", IllegalArgumentException.class, () -> {
			Range illegalRange = new Range (lowerBound , upperBound);
			System.out.println(illegalRange);	
		});
	}

	@Test
	public void testShift_ShouldReturnCorrectShiftedRange_OnAllowZeroCrossingIsTrue() {
		Range base = new Range(-5, -3);
		double delta = 10;
		boolean allowZeroCrossing = true;
		Range expectedRange = new Range (5, 7);
		Range actualRange = Range.shift(base, delta, allowZeroCrossing);
		assertEquals("WBT-R-2.1: Expected the correct range to be returned on allow zero crossing", expectedRange, actualRange);
	}
	
	@Test 
	public void testExpand_ShouldReturnNullOrCorrectRange_OnNullRange() {
	// one null one not null 
		Range base = new Range(5,10);
		assertEquals("WBT-R-3.1: Expected correct range after expansion", 
				new Range(4, 11.5), Range.expand(base, 0.2, 0.3));
		
		assertThrows("WBT-R-3.2: IllegalArgumentException expected for null range", 
				IllegalArgumentException.class, () -> Range.expand(null, 1, 2));
	}
	
	@Test 
	public void testCentralValue() {
		Range range1 = new Range(2, 6);
		Range range2 = new Range(-8, -2);
		Range range3 = new Range(-4, 4);
		
		assertEquals("WBT-R-4.1: Expected central value mismatch", 4.0, range1.getCentralValue(), 0.0001);
		assertEquals("WBT-R-4.2: Expected central value mismatch", -5.0, range2.getCentralValue(), 0.0001);
		assertEquals("WBT-R-4.3: Expected central value mismatch", 0.0, range3.getCentralValue(), 0.0001);
	}
}
