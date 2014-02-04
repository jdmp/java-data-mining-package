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

package org.jdmp.mallet.sample;

import org.jdmp.core.sample.DefaultSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.mallet.matrix.MalletInputMatrix;
import org.jdmp.mallet.matrix.MalletOutputMatrix;
import org.ujmp.core.interfaces.Wrapper;

import cc.mallet.types.FeatureVector;
import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.LabelSequence;

public class Instance2Sample extends DefaultSample implements Wrapper<Instance> {
	private static final long serialVersionUID = 3834503288309969345L;

	private Instance instance = null;

	private int index = 0;

	public Instance2Sample(Instance instance, int index) {
		this.instance = instance;
		this.index = index;
		Object data = instance.getData();
		if (data instanceof FeatureVectorSequence) {
			FeatureVectorSequence fvs = (FeatureVectorSequence) data;
			getVariables().setMatrix(INPUT, new MalletInputMatrix(fvs.getFeatureVector(index)));
		}
		LabelSequence labelSequence = (LabelSequence) instance.getTarget();
		getVariables().setMatrix(Sample.TARGET, new MalletOutputMatrix(labelSequence, index));

		if (data instanceof FeatureVectorSequence) {
			FeatureVectorSequence fvs = (FeatureVectorSequence) data;
			FeatureVector fv = fvs.getFeatureVector(index);
			for (int i = fv.numLocations() - 1; i != -1; i--) {
				String word = (String) fv.getAlphabet().lookupObject(fv.getIndices()[i]);
				if (word.startsWith("W=")) {
					if (!word.matches(".*[0-9]$")) {
						setLabel(word.substring(2));
					}
				}
			}
		}
	}

	public Instance getWrappedObject() {
		return instance;
	}

	public void setWrappedObject(Instance object) {
		this.instance = object;
	}
}
