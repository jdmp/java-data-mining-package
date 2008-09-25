package org.jdmp.core.interpreter;

public class Result {

	private String text = null;

	public Result(String text) {
		this.text = text;
	}

	public Result() {
		this.text = "OK";
	}

	public String toString() {
		return text;
	}
}
