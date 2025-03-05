package org.jfree.data.test;

import org.jfree.data.Range;

public class Main {

	  public static Range combineX(Range range1, Range range2) {
	        if (range1 == null) {
	            return range2;
	        }
	        if (range2 == null) {
	            return range1;
	        }
	        double l = Math.min(range1.getLowerBound(), range2.getLowerBound());
	        double u = Math.max(range1.getUpperBound(), range2.getUpperBound());
	        return new Range(l, u);
	    }

	  
	public static void main(String[] args) {
		Range range = new Range(1, 5);
		System.out.println(new Range(-5.83, -3));
	}

}
