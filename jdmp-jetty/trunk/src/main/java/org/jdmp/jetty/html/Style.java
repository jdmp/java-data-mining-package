/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

public class Style implements Html {
	private static final long serialVersionUID = -6723038450667039070L;

	private String style = null;

	public Style(String style) {
		this.style = style;
	}

	public Style() {
		String EOL = System.getProperty("line.separator");
		style = "html {" + EOL;
		style += "  font-family: arial, helvetica, verdana, lucida, sans-serif;"
				+ EOL;
		style += "  font-size: 12px;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += "h1 {" + EOL;
		style += "  color: #000066;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += ".label {" + EOL;
		style += "  font-size: 14px;" + EOL;
		style += "  color: #0000dd;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += ".sample {" + EOL;
		style += "  margin-bottom: 10px;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += ".type {" + EOL;
		style += "  color: #808080;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += ".value {" + EOL;
		style += "  color: #800000;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += ".key {" + EOL;
		style += "  font-weight: bold;" + EOL;
		style += "  color: #800000;" + EOL;
		style += "}" + EOL;
		style += EOL;
		style += ".url {" + EOL;
		style += "  color: #008000;" + EOL;
		style += "}" + EOL;
	}

	public String toString() {
		String EOL = System.getProperty("line.separator");
		StringBuffer s = new StringBuffer();
		s.append("<style type=\"text/css\">" + EOL);
		s.append("<!--" + EOL);
		s.append(style + EOL);
		s.append("-->" + EOL);
		s.append("</style>" + EOL);
		return s.toString();
	}

}
