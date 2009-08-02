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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.tokenizer.AbstractTokenizer;
import org.jdmp.core.algorithm.tokenizer.Tokenizer;
import org.ujmp.core.Matrix;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordTokenizer extends AbstractTokenizer {
	private static final long serialVersionUID = -6822348129510526065L;

	private static StanfordTokenizer instance = null;

	public StanfordTokenizer() throws Exception {
	}

	@Override
	public List<Matrix> tokenize(String input) throws Exception {
		List<Matrix> result = new ArrayList<Matrix>();
		StringReader sr = new StringReader(input);
		List<Sentence<? extends HasWord>> sentences = MaxentTagger
				.tokenizeText(sr);
		for (Sentence<? extends HasWord> se : sentences) {
			result.add(new StanfordSentenceMatrix(se));
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		String s = "But now and then, the state’s Western heritage comes storming through the saloon doors to remind one and all just what this place was like not so long ago.";
		Tokenizer t = new StanfordTokenizer();
		System.out.println(t.tokenize(s));
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
