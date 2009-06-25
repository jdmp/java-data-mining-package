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

package org.jdmp.jetty.html.elements;

import org.jdmp.core.sample.Sample;
import org.jdmp.jetty.html.tags.DivTag;
import org.ujmp.core.Matrix;

public class DefaultSampleDiv extends DivTag {
	private static final long serialVersionUID = 6587788027413384557L;

	public DefaultSampleDiv(Sample sample) {
		setParameter("class", "sample");
		if (sample == null) {
			add("[]");
		} else {
			Matrix score = sample.getMatrix("Score");
			if (score != null) {
				// add(score.doubleValue() + " ");
			}
			Matrix subject = sample.getMatrix("Subject");
			if (subject != null) {
				add("[" + subject.stringValue() + "] ");
			}
			Matrix verb = sample.getMatrix("Verb");
			if (verb != null) {
				add("[" + verb.stringValue() + "] ");
			}
			Matrix object = sample.getMatrix("Object");
			if (object != null) {
				add("[" + object.stringValue() + "] ");
			}
			add(sample.getLabel());
		}
	}

}
