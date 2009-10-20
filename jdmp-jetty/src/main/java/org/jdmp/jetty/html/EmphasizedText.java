/*
 * Copyright (C) 2008-2009 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.jetty.html;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmphasizedText implements Html {
	private static final long serialVersionUID = 2822445985021115869L;

	private String highlightedText = null;

	public EmphasizedText(String text, String... highlightedWords) {
		highlightedText = HtmlUtils.replaceSpecialChars(text);
		for (String w : highlightedWords) {
			w = w.replaceAll("\\+", "");
			w = w.replaceAll("\\(", "");
			w = w.replaceAll("\\)", "");
			w = w.replaceAll("\\[", "");
			w = w.replaceAll("\\]", "");
			Pattern p = Pattern.compile(w, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(highlightedText);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				m.appendReplacement(sb, "<b>" + m.group() + "</b>");
			}
			m.appendTail(sb);
			highlightedText = sb.toString();
		}
	}

	
	public String toString() {
		return highlightedText;
	}

}
