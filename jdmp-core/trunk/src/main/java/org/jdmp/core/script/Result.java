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

package org.jdmp.core.script;

import org.ujmp.core.exceptions.MatrixException;

public class Result {

	private Object object = null;

	private String text = null;

	private String label = null;

	private Throwable exception = null;

	public Result(String text) {
		this(null, null, text, null);
	}

	public Result(Throwable e) {
		this(null, null, null, e);
	}

	public Result(String s, Throwable e) {
		this(null, null, s, e);
	}

	public Result(String s, Object o) {
		this(s, o, null, null);
	}

	public Result(String label, Object o, String s, Throwable e) {
		this.label = label;
		this.object = o;
		this.text = s;
		this.exception = e;
	}

	public Throwable getException() {
		return exception;
	}

	public void setException(Throwable exception) {
		this.exception = exception;
	}

	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		if (label != null) {
			s.append(label + " =\n");
		}
		if (object != null) {
			s.append(object.toString());
		}
		if (text != null) {
			s.append(text);
		}
		if (text != null && exception != null) {
			s.append(": ");
		}
		if (exception != null) {
			s.append(getExceptionText());
		}
		return s.toString();
	}

	public String getExceptionText() {
		if (exception instanceof MatrixException) {
			return exception.getMessage();
		} else {
			return exception.toString();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
