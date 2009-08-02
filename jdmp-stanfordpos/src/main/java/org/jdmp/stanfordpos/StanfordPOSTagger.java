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

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jdmp.core.algorithm.tagger.AbstractTagger;
import org.ujmp.core.Matrix;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class StanfordPOSTagger extends AbstractTagger {
	private static final long serialVersionUID = -2655534427624643477L;

	public static final String MODELPATH = System.getProperty("user.home")
			+ "/datasets/models/pos";

	public static final String DEFAULTMODEL = "left3words-wsj-0-18.tagger";

	private MaxentTagger tagger = null;

	private static StanfordPOSTagger instance = null;

	public StanfordPOSTagger() throws Exception {
		this(new File(MODELPATH + "/" + DEFAULTMODEL));
	}

	public StanfordPOSTagger(File modelFile) throws Exception {
		if (!modelFile.exists()) {
			throw new Exception("model file not found: " + modelFile);
		}
		tagger = new MaxentTagger(modelFile.getAbsolutePath());
	}

	@Override
	public List<Matrix> tag(String input) throws Exception {
		List<Matrix> list = new ArrayList<Matrix>();

		StringReader sr = new StringReader(input);
		List<Sentence<? extends HasWord>> sentences = MaxentTagger
				.tokenizeText(sr);

		for (Sentence<? extends HasWord> sentence : sentences) {
			Sentence<TaggedWord> tSentence = tagger.processSentence(sentence);
			Matrix m = new StanfordSentenceMatrix(tSentence);
			list.add(m);
		}

		return list;
	}

	@Override
	public List<Matrix> tag(Matrix input) throws Exception {
		List<Matrix> list = new ArrayList<Matrix>();

		Sentence<? extends HasWord> sentence = null;
		if (input instanceof StanfordSentenceMatrix) {
			sentence = ((StanfordSentenceMatrix) input).getWrappedObject();
		} else {
			List<HasWord> words = new ArrayList<HasWord>();
			for (long[] c : input.availableCoordinates()) {
				String s = input.getAsString(c);
				words.add(new Word(s));
			}
			sentence = new Sentence<HasWord>(words);
		}
		Sentence<TaggedWord> tSentence = tagger.processSentence(sentence);
		Matrix m = new StanfordSentenceMatrix(tSentence);
		list.add(m);

		return list;
	}

	public static StanfordPOSTagger getInstance() {
		if (instance == null) {
			try {
				instance = new StanfordPOSTagger();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

}
