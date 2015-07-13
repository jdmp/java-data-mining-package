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

package org.jdmp.mallet.dataset;

import java.util.Iterator;
import java.util.Map;

import org.jdmp.core.dataset.DefaultListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.mallet.sample.Instance2Sample;
import org.ujmp.core.collections.map.SoftHashMap;
import org.ujmp.core.interfaces.Wrapper;

import cc.mallet.types.FeatureVectorSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class InstanceList2DataSet extends DefaultListDataSet implements Wrapper<InstanceList> {
	private static final long serialVersionUID = -4306499108417523670L;

	private InstanceList instanceList = null;

	private final Map<Integer, Instance2Sample> integerToSampleMap = new SoftHashMap<Integer, Instance2Sample>();

	public InstanceList2DataSet(InstanceList instanceList) {
		this.instanceList = instanceList;
	}

	public InstanceList getWrappedObject() {
		return instanceList;
	}

	public void setWrappedObject(InstanceList object) {
		this.instanceList = object;
	}

	public int getSampleCount() {
		int count = 0;
		Iterator<?> it = instanceList.iterator();
		while (it.hasNext()) {
			Instance instance = (Instance) it.next();
			Object data = instance.getData();
			if (data instanceof FeatureVectorSequence) {
				FeatureVectorSequence fvs = (FeatureVectorSequence) data;
				count += fvs.size();
			}
		}
		return count;
	}

	public Sample getSample(int index) {
		Instance2Sample s = integerToSampleMap.get(index);
		if (s == null) {
			int offset = 0;
			Iterator<?> it = instanceList.iterator();
			while (it.hasNext()) {
				Instance instance = (Instance) it.next();
				Object data = instance.getData();
				if (data instanceof FeatureVectorSequence) {
					FeatureVectorSequence fvs = (FeatureVectorSequence) data;
					int size = fvs.size();
					if (index - offset < size) {
						s = new Instance2Sample(instance, index - offset);
						integerToSampleMap.put(index, s);
						return s;
					} else {
						offset += fvs.size();
					}
				}
			}
		}
		return s;
	}

}
