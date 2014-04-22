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

import org.jdmp.core.algorithm.tagger.AbstractTagger;
import org.ujmp.core.Matrix;

import cc.mallet.fst.CRF;
import cc.mallet.fst.CRFOptimizableByLabelLikelihood;
import cc.mallet.fst.CRFTrainerByValueGradients;
import cc.mallet.fst.SimpleTagger;
import cc.mallet.optimize.Optimizable;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.TokenSequence2FeatureVectorSequence;
import cc.mallet.pipe.tsf.TokenText;
import cc.mallet.pipe.tsf.TokenTextCharNGrams;
import cc.mallet.pipe.tsf.TokenTextCharPrefix;
import cc.mallet.pipe.tsf.TokenTextCharSuffix;
import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.util.CommandOption;

public class MalletTagger extends AbstractTagger {
	private static final long serialVersionUID = 4905729231422289232L;

	private static final CommandOption.String defaultOption = new CommandOption.String(SimpleTagger.class,
			"default-label", "STRING", true, "O", "Label for initial context and uninteresting tokens", null);

	private CRF crf = null;

	private CRFTrainerByValueGradients crfTrainer = null;

	private SerialPipes serialPipes = null;

	public MalletTagger() {
	}

	public List<Matrix> tag(String input) throws Exception {
		return null;
	}

	public List<Matrix> tag(Matrix input) throws Exception {
		return null;
	}

	private SerialPipes getPipes() {
		if (serialPipes == null) {
			List<Pipe> pipes = new ArrayList<Pipe>();
			pipes.add(new Matrix2TokenSequencePipe());
			pipes.add(new TokenText());
			pipes.add(new TokenTextCharPrefix());
			pipes.add(new TokenTextCharSuffix());
			pipes.add(new TokenTextCharNGrams());
			pipes.add(new TokenSequence2FeatureVectorSequence());
			serialPipes = new SerialPipes(pipes);
			serialPipes.getTargetAlphabet().lookupIndex(defaultOption.value);
			serialPipes.setTargetProcessing(true);
		}
		return serialPipes;
	}

	public void train(List<String> sentences) throws Exception {
		for (String s : sentences) {
			// train(s);
		}
	}

	public Alphabet getLabelAlphabet() {
		return getPipes().getTargetAlphabet();
	}

	public Alphabet getDataAlphabet() {
		return getPipes().getDataAlphabet();
	}

	public void train(Matrix matrix) throws Exception {
		Instance matrixInstance = new Instance(matrix, null, null, null);
		InstanceList trainingData = new InstanceList(getPipes());
		trainingData.addThruPipe(matrixInstance);

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

	public void test(Matrix matrix) throws Exception {
		Instance matrixInstance = new Instance(matrix, null, null, null);
		Instance i = crf.label(matrixInstance);
		System.out.println(i);
	}

}
