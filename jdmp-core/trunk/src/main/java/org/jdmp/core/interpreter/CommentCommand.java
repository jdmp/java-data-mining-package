package org.jdmp.core.interpreter;

public class CommentCommand extends Command {

	public CommentCommand(String text) {
		setOriginalText(text);
	}

	public String toString() {
		return getOriginalText();
	}

}
