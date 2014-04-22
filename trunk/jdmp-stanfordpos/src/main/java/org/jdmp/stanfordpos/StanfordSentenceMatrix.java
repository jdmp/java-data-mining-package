/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

import java.util.ArrayList;

import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.interfaces.Wrapper;
import org.ujmp.core.matrix.factory.DenseMatrix2DFactory;
import org.ujmp.core.stringmatrix.stub.AbstractDenseStringMatrix2D;

import edu.stanford.nlp.ling.TaggedWord;

public class StanfordSentenceMatrix extends AbstractDenseStringMatrix2D implements Wrapper<ArrayList<TaggedWord>> {
	private static final long serialVersionUID = 9175120136686956227L;

	private ArrayList<TaggedWord> sentence = null;

	public StanfordSentenceMatrix(ArrayList<TaggedWord> sentence) {
		super(sentence.size(), 2);
		this.sentence = sentence;
	}

	public String getString(long row, long column) {
		if ((int) row < sentence.size()) {
			TaggedWord tw = sentence.get((int) row);
			if (column == 0) {
				return tw.word();
			} else {
				return tw.tag();
			}
		} else {
			throw new RuntimeException("Attempted to access (" + row + "," + column
					+ "); index out of bounds because size [" + (int) getSize()[0] + "," + (int) getSize()[1]);
		}
	}

	public void setString(String value, long row, long column) {
		if ((int) row < sentence.size()) {
			TaggedWord tw = sentence.get((int) row);
			if (column == 0) {
				tw.setWord(value);
			} else {
				tw.setTag(value);
			}
		} else {
			throw new RuntimeException("Attempted to access (" + row + "," + column
					+ "); index out of bounds because size [" + (int) getSize()[0] + "," + (int) getSize()[1]);
		}
	}

	public long[] getSize() {
		if (sentence.size() == 0) {
			return new long[] { 0, 0 };
		}
		return new long[] { sentence.size(), 2 };
	}

	public ArrayList<TaggedWord> getWrappedObject() {
		return sentence;
	}

	public void setWrappedObject(ArrayList<TaggedWord> object) {
		this.sentence = object;
	}

	public DenseMatrix2DFactory<? extends DenseMatrix2D> getFactory() {
		// TODO Auto-generated method stub
		return null;
	}
}
