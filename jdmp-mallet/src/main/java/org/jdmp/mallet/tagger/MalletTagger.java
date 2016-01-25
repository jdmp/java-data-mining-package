/*
 * Copyright (C) 2008-2016 by Holger Arndt
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

import org.jdmp.core.algorithm.tagger.AbstractTagger;
import org.jdmp.mallet.MalletUtil;
import org.jdmp.mallet.sample.TextInstance;
import org.ujmp.core.Matrix;
import org.ujmp.core.listmatrix.ListMatrix;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.text.TextBlock;
import org.ujmp.core.text.TextSentence;

import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFOptimizableByLabelLikelihood;
import cc.mallet.fst.CRFTrainerByValueGradients;
import cc.mallet.optimize.Optimizable;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
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

	private Pipe getPipes() {
		if (serialPipes == null) {
			serialPipes = MalletUtil.createDefaultPipes(dataAlphabet, targetAlphabet);
		}
		return serialPipes;
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
