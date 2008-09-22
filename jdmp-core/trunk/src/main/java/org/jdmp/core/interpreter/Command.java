package org.jdmp.core.interpreter;

public abstract class Command {

	private String originalText = "";

	public Command() {
	}

	public String getOriginalText() {
		return originalText;
	}

	public void setOriginalText(String originalText) {
		this.originalText = originalText;
	}

}
