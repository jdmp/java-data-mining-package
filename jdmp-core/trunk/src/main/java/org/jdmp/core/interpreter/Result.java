package org.jdmp.core.interpreter;

public class Result {

	private String text = "";

	public Result(String text) {
		this.text = text;
	}

	public Result() {
	}

	public String toString() {
		return text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
