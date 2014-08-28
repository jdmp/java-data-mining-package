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

package org.jdmp.mallet.tagger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.jdmp.core.algorithm.tagger.AbstractTagger;
import org.jdmp.mallet.sample.TextInstance;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.text.TextBlock;
import org.ujmp.core.text.TextSentence;
import org.ujmp.core.text.TextUtil;

import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFOptimizableByLabelLikelihood;
import cc.mallet.fst.CRFTrainerByValueGradients;
import cc.mallet.optimize.Optimizable;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.pipe.tsf.OffsetConjunctions;
import cc.mallet.pipe.tsf.RegexMatches;
import cc.mallet.pipe.tsf.TokenText;
import cc.mallet.pipe.tsf.TokenTextCharNGrams;
import cc.mallet.pipe.tsf.TokenTextCharPrefix;
import cc.mallet.pipe.tsf.TokenTextCharSuffix;
import cc.mallet.types.Alphabet;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.Sequence;

public class MalletTagger extends AbstractTagger {
	private static final long serialVersionUID = 4905729231422289232L;

	private CRF crf = null;

	private CRFTrainerByValueGradients crfTrainer = null;

	private SerialPipes serialPipes = null;
	private final Alphabet dataAlphabet = new Alphabet();
	private final Alphabet targetAlphabet = new Alphabet();

	public MalletTagger() {
	}

	public ListMatrix<ListMatrix<MapMatrix<String, Object>>> tag(String input) throws Exception {
		return null;
	}

	public ListMatrix<ListMatrix<MapMatrix<String, Object>>> tag(Matrix input) throws Exception {
		return null;
	}

	private SerialPipes getPipes() {
		if (serialPipes == null) {
			List<Pipe> pipes = new ArrayList<Pipe>();
			pipes.add(new TokenText());
			pipes.add(new TokenTextCharPrefix("PREFIX=", 2));
			pipes.add(new TokenTextCharPrefix("PREFIX=", 3));
			pipes.add(new TokenTextCharSuffix("SUFFIX=", 2));
			pipes.add(new TokenTextCharSuffix("SUFFIX=", 3));
			pipes.add(new TokenTextCharNGrams("NGRAM=", new int[] { 2, 3 }));
			pipes.add(new RegexMatches("ALL_CAPS_REGEX", Pattern.compile(TextUtil.ALL_CAPS_REGEX)));
			pipes.add(new RegexMatches("ALPHA_NUMERIC_REGEX", Pattern.compile(TextUtil.ALPHA_NUMERIC_REGEX)));
			pipes.add(new RegexMatches("CAPS_MIX_REGEX", Pattern.compile(TextUtil.CAPS_MIX_REGEX)));
			pipes.add(new RegexMatches("EMAIL_REGEX", Pattern.compile(TextUtil.EMAIL_REGEX)));
			pipes.add(new RegexMatches("END_DASH_REGEX", Pattern.compile(TextUtil.END_DASH_REGEX)));
			pipes.add(new RegexMatches("EXP_NUMBER_REGEX", Pattern.compile(TextUtil.EXP_NUMBER_REGEX)));
			pipes.add(new RegexMatches("FLOATING_POINT_NUMBER_REGEX", Pattern
					.compile(TextUtil.FLOATING_POINT_NUMBER_REGEX)));
			pipes.add(new RegexMatches("FOUR_CAPS_REGEX", Pattern.compile(TextUtil.FOUR_CAPS_REGEX)));
			pipes.add(new RegexMatches("FOUR_DIGITS_REGEX", Pattern.compile(TextUtil.FOUR_DIGITS_REGEX)));
			pipes.add(new RegexMatches("HAS_DASH_REGEX", Pattern.compile(TextUtil.HAS_DASH_REGEX)));
			pipes.add(new RegexMatches("HAS_DIGIT_REGEX", Pattern.compile(TextUtil.HAS_DIGIT_REGEX)));
			pipes.add(new RegexMatches("HEX_REGEX", Pattern.compile(TextUtil.HEX_REGEX)));
			pipes.add(new RegexMatches("HTML_REGEX", Pattern.compile(TextUtil.HTML_REGEX)));
			pipes.add(new RegexMatches("IN_PARENTHESES_REGEX", Pattern.compile(TextUtil.IN_PARENTHESES_REGEX)));
			pipes.add(new RegexMatches("INIT_CAPS_ALPHA_REGEX", Pattern.compile(TextUtil.INIT_CAPS_ALPHA_REGEX)));
			pipes.add(new RegexMatches("INIT_CAPS_REGEX", Pattern.compile(TextUtil.INIT_CAPS_REGEX)));
			pipes.add(new RegexMatches("INIT_DASH_REGEX", Pattern.compile(TextUtil.INIT_DASH_REGEX)));
			pipes.add(new RegexMatches("IP_REGEX", Pattern.compile(TextUtil.IP_REGEX)));
			pipes.add(new RegexMatches("NEGATIVE_INTEGER_REGEX", Pattern.compile(TextUtil.NEGATIVE_INTEGER_REGEX)));
			pipes.add(new RegexMatches("ONE_CAP_REGEX", Pattern.compile(TextUtil.ONE_CAP_REGEX)));
			pipes.add(new RegexMatches("ONE_DIGIT_REGEX", Pattern.compile(TextUtil.ONE_DIGIT_REGEX)));
			pipes.add(new RegexMatches("POSITIVE_INTEGER_REGEX", Pattern.compile(TextUtil.POSITIVE_INTEGER_REGEX)));
			pipes.add(new RegexMatches("PUNCTUATION_REGEX", Pattern.compile(TextUtil.PUNCTUATION_REGEX)));
			pipes.add(new RegexMatches("ROMAN_NUMBER_CAPITAL_REGEX", Pattern
					.compile(TextUtil.ROMAN_NUMBER_CAPITAL_REGEX)));
			pipes.add(new RegexMatches("ROMAN_NUMBER_SMALL_REGEX", Pattern.compile(TextUtil.ROMAN_NUMBER_SMALL_REGEX)));
			pipes.add(new RegexMatches("SINGLE_INITIAL_REGEX", Pattern.compile(TextUtil.SINGLE_INITIAL_REGEX)));
			pipes.add(new RegexMatches("THREE_CAPS_REGEX", Pattern.compile(TextUtil.THREE_CAPS_REGEX)));
			pipes.add(new RegexMatches("THREE_DIGITS_REGEX", Pattern.compile(TextUtil.THREE_DIGITS_REGEX)));
			pipes.add(new RegexMatches("TWO_CAPS_REGEX", Pattern.compile(TextUtil.TWO_CAPS_REGEX)));
			pipes.add(new RegexMatches("TWO_DIGITS_REGEX", Pattern.compile(TextUtil.TWO_DIGITS_REGEX)));
			pipes.add(new RegexMatches("URL_REGEX", Pattern.compile(TextUtil.URL_REGEX)));
			pipes.add(new RegexMatches("YEAR_REGEX", Pattern.compile(TextUtil.YEAR_REGEX)));
			pipes.add(new RegexMatches("OBD_REGEX", Pattern.compile(TextUtil.OBD_REGEX)));
			pipes.add(new RegexMatches("ONE_QUESTION_MARK_REGEX", Pattern.compile(TextUtil.ONE_QUESTION_MARK_REGEX)));
			pipes.add(new RegexMatches("TWO_QUESTION_MARKS_REGEX", Pattern.compile(TextUtil.TWO_QUESTION_MARKS_REGEX)));
			pipes.add(new RegexMatches("THREE_QUESTION_MARKS_REGEX", Pattern
					.compile(TextUtil.THREE_QUESTION_MARKS_REGEX)));
			pipes.add(new RegexMatches("MULTIPLE_QUESTION_MARKS_REGEX", Pattern
					.compile(TextUtil.MULTIPLE_QUESTION_MARKS_REGEX)));
			pipes.add(new RegexMatches("ONE_EXCLAMATION_MARK_REGEX", Pattern
					.compile(TextUtil.ONE_EXCLAMATION_MARK_REGEX)));
			pipes.add(new RegexMatches("TWO_EXCLAMATION_MARKS_REGEX", Pattern
					.compile(TextUtil.TWO_EXCLAMATION_MARKS_REGEX)));
			pipes.add(new RegexMatches("THREE_EXCLAMATION_MARKS_REGEX", Pattern
					.compile(TextUtil.THREE_EXCLAMATION_MARKS_REGEX)));
			pipes.add(new RegexMatches("MULTIPLE_EXCLAMATION_MARKS_REGEX", Pattern
					.compile(TextUtil.MULTIPLE_EXCLAMATION_MARKS_REGEX)));
			pipes.add(new RegexMatches("QUESTION_EXCLAMATION_MARK_REGEX", Pattern
					.compile(TextUtil.QUESTION_EXCLAMATION_MARK_REGEX)));
			pipes.add(new RegexMatches("EXCLAMATION_QUESTION_MARK_REGEX", Pattern
					.compile(TextUtil.EXCLAMATION_QUESTION_MARK_REGEX)));
			pipes.add(new OffsetConjunctions(new int[][] { { -1 }, { 1 } }));
			pipes.add(new TokenSequence2FeatureVectorSequence(targetAlphabet));
			serialPipes = new SerialPipes(pipes);
			serialPipes.setDataAlphabet(dataAlphabet);
			serialPipes.setTargetAlphabet(targetAlphabet);
			serialPipes.setTargetProcessing(true);
		}
		return serialPipes;
	}

	public Alphabet getTargetAlphabet() {
		return targetAlphabet;
	}

	public Alphabet getDataAlphabet() {
		return dataAlphabet;
	}

	public void train(TextBlock textBlock) throws Exception {
		InstanceList trainingData = new InstanceList(getPipes());
		for (TextSentence textSentence : textBlock) {
			Instance textInstance = new TextInstance(textSentence, getTargetAlphabet());
			trainingData.addThruPipe(textInstance);
		}

		if (crf == null) {
			crf = new CRF(getPipes(), null);
			crf.addFullyConnectedStatesForLabels();
			crf.setWeightsDimensionAsIn(trainingData, false);
			CRFOptimizableByLabelLikelihood optLabel = new CRFOptimizableByLabelLikelihood(crf, trainingData);
			Optimizable.ByGradientValue[] opts = new Optimizable.ByGradientValue[] { optLabel };
			crfTrainer = new CRFTrainerByValueGradients(crf, opts);
			crfTrainer.setMaxResets(0);
		}

		crfTrainer.train(trainingData, Integer.MAX_VALUE);
	}

	public void predict(TextBlock textBlock) throws Exception {
		getPipes().setTargetProcessing(false);
		InstanceList testData = new InstanceList(getPipes());
		for (TextSentence textSentence : textBlock) {
			Instance textInstance = new TextInstance(textSentence, getTargetAlphabet());
			testData.addThruPipe(textInstance);
		}
		for (Instance instance : testData) {
			FeatureVectorSequence sequence = (FeatureVectorSequence) instance.getData();
			Sequence<?> labelSequence = crf.transduce(sequence);
			System.out.println(labelSequence);
		}
		return;
	}

}
