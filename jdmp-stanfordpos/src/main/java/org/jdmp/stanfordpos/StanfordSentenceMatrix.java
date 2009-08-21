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

package org.jdmp.stanfordpos;

import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;

public class StanfordSentenceMatrix extends AbstractDenseStringMatrix2D
		implements Wrapper<Sentence<? extends HasWord>> {
	private static final long serialVersionUID = 9175120136686956227L;

	private Sentence<? extends HasWord> sentence = null;

	public StanfordSentenceMatrix(Sentence<? extends HasWord> sentence) {
		this.sentence = sentence;
	}

	
	public String getString(long row, long column) {
		HasWord w = sentence.get((int) row);
		if (column == 0) {
			return w.word();
		} else {
			return ((TaggedWord) w).tag();
		}
	}

	
	public void setString(String value, long row, long column) {
	}

	
	public long[] getSize() {
		if (sentence.size() == 0) {
			return new long[] { 0, 0 };
		}
		if (sentence.get(0) instanceof TaggedWord) {
			return new long[] { sentence.size(), 2 };
		} else {
			return new long[] { sentence.size(), 1 };
		}
	}

	
	public Sentence<? extends HasWord> getWrappedObject() {
		return sentence;
	}

	
	public void setWrappedObject(Sentence<? extends HasWord> object) {
		this.sentence = object;
	}
}
