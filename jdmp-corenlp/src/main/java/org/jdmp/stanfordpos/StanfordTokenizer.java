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

import java.io.StringReader;
import java.util.List;

import org.jdmp.core.algorithm.tokenizer.AbstractTokenizer;
import org.ujmp.core.listmatrix.DefaultListMatrix;
import org.ujmp.core.listmatrix.ListMatrix;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordTokenizer extends AbstractTokenizer {
	private static final long serialVersionUID = -6822348129510526065L;

	private static StanfordTokenizer instance = null;

	public StanfordTokenizer() throws Exception {
	}

	public ListMatrix<ListMatrix<String>> tokenize(String input) throws Exception {
		ListMatrix<ListMatrix<String>> result = new DefaultListMatrix<ListMatrix<String>>();
		StringReader sr = new StringReader(input);

		List<List<HasWord>> sentences = MaxentTagger.tokenizeText(sr);
		for (List<HasWord> tokSentence : sentences) {
			ListMatrix<String> m = new DefaultListMatrix<String>();

			for (HasWord t : tokSentence) {
				m.add(t.word());
			}
			result.add(m);
		}

		return result;
	}

	public static StanfordTokenizer getInstance() {
		if (instance == null) {
			try {
				instance = new StanfordTokenizer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

}
