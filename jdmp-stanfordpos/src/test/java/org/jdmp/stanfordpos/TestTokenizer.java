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

import junit.framework.TestCase;

import org.jdmp.core.algorithm.tokenizer.Tokenizer;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DefaultDataSet;
import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.ujmp.core.Matrix;

public class TestTokenizer extends TestCase {

	public static final String s1 = "The Universal Java Matrix Package is a very cool software. This is also true for the Java Data Mining Package.";

	public static final String s2 = "Have fun with it!";

	public void testTokenizer() throws Exception {
		DataSet ds = new DefaultDataSet();
		Sample sa1 = new DefaultSample();
		sa1.getVariables().setObject(Sample.INPUT, s1);
		sa1.setId("sample1");
		Sample sa2 = new DefaultSample();
		sa2.getVariables().setObject(Sample.INPUT, s2);
		sa2.setId("sample2");
		ds.getSamples().add(sa1);
		ds.getSamples().add(sa2);

		Tokenizer t = new StanfordTokenizer();
		t.tokenize(Sample.INPUT, ds);

		sa1 = ds.getSamples().get("sample1");
		sa2 = ds.getSamples().get("sample2");

		Matrix m1 = sa1.getVariables().getMatrix(Tokenizer.TOKENIZED);
		Matrix m2 = sa2.getVariables().getMatrix(Tokenizer.TOKENIZED);

		assertEquals(1, m1.getColumnCount());
		assertEquals(11, m1.getRowCount());
		assertEquals(1, m2.getColumnCount());
		assertEquals(5, m2.getRowCount());
	}
}
