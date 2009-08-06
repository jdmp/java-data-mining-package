package org.jdmp.jetty.html.elements;

import org.jdmp.jetty.html.EmphasizedText;
import org.jdmp.jetty.html.tags.DivTag;

public class DescriptionDiv extends DivTag {
	private static final long serialVersionUID = 4950854806202181784L;

	public DescriptionDiv(String description, String... highlightedWords) {
		setParameter("class", "description");
		if (description != null && description.length() > 0) {
			add(new EmphasizedText(description, highlightedWords));
		}
	}
}
