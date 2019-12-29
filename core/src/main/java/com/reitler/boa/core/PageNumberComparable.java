package com.reitler.boa.core;

public class PageNumberComparable implements Comparable<PageNumberComparable> {

	private int digit;
	private String letter;

	public PageNumberComparable(final String input) {
		String s = "";
		this.letter = "";
		for (char c : input.toCharArray()) {
			if (c >= '0' && c <= '9') {
				s += c;
			} else {
				this.letter += c;
			}
		}
		try {
			this.digit = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			System.out.println("NumberFormatException: " + e.getMessage());
		}
		System.out.println("digit: " + this.digit + "; letter: " + this.letter);
	}

	@Override
	public int compareTo(final PageNumberComparable o) {
		if (this.digit > o.digit) {
			return 1;
		} else if (this.digit < o.digit) {
			return -1;
		} else {
			return this.letter.compareToIgnoreCase(o.letter);
		}
	}

}
