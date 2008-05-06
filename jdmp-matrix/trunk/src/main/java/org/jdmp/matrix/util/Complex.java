package org.jdmp.matrix.util;

public class Complex extends Number {
	private static final long serialVersionUID = 8739993129417798921L;

	private double real = 0.0;

	private double img = 0.0;

	public Complex(double real, double img) {
		this.real = real;
		this.img = img;
	}

	@Override
	public double doubleValue() {
		return real;
	}

	@Override
	public float floatValue() {
		return (float) real;
	}

	@Override
	public int intValue() {
		return (int) real;
	}

	@Override
	public long longValue() {
		return (long) real;
	}

	public double getReal() {
		return real;
	}

}
