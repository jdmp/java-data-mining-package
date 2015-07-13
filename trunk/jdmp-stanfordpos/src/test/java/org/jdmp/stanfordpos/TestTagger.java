/*
 * Copyright (C) 2008-2015 by Holger Arndt
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

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.jdmp.core.algorithm.tagger.Tagger;
import org.jdmp.core.algorithm.tokenizer.Tokenizer;
import org.jdmp.core.dataset.DefaultListDataSet;
import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;

public class TestTagger {

	public static final String s1 = "The Universal Java Matrix Package is a very cool software. This is also true for the Java Data Mining Package.";

	public static final String s2 = "Have fun with it!";

	private static Tagger tagger = null;

	private static Tokenizer tokenizer = null;

	@BeforeClass
	public static void setUp() {
		try {
			tokenizer = new StanfordTokenizer();
			tagger = new StanfordPOSTagger();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testStringTagger() throws Exception {
		if (tagger == null) {
			return;
		}

		List<ListMatrix<MapMatrix<String, Object>>> list = tagger.tag(s1);

		assertEquals(2, list.size());
		assertEquals(2, list.get(0).getColumnCount());
		assertEquals(11, list.get(0).getRowCount());
		assertEquals(2, list.get(1).getColumnCount());
		assertEquals(11, list.get(1).getRowCount());
	}

	@Test
	public void testTagger() throws Exception {
		if (tagger == null) {
			return;
		}

		ListDataSet ds = new DefaultListDataSet();
		Sample sa1 = new DefaultSample();
		sa1.put(Sample.INPUT, s1);
		Sample sa2 = new DefaultSample();
		sa2.put(Sample.INPUT, s2);
		ds.add(sa1);
		ds.add(sa2);

		tokenizer.tokenize(Sample.INPUT, ds);
		tagger.tag(ds);

		sa1 = ds.get(0);
		sa2 = ds.get(1);

		Matrix m1 = sa1.getAsMatrix(Tagger.TAGGED);
		Matrix m2 = sa2.getAsMatrix(Tagger.TAGGED);

		assertEquals(2, m1.getColumnCount());
		assertEquals(11, m1.getRowCount());
		assertEquals(2, m2.getColumnCount());
		assertEquals(5, m2.getRowCount());
	}
}
