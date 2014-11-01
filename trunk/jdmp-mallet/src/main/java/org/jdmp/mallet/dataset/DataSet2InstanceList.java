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

package org.jdmp.mallet.dataset;

import java.util.List;

import org.jdmp.core.dataset.ListDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.mallet.sample.Sample2Instance;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.types.LabelAlphabet;

public class DataSet2InstanceList extends InstanceList {
	private static final long serialVersionUID = -6205824050882100522L;

	public DataSet2InstanceList(ListDataSet dataSet, LabelAlphabet inputAlphabet, LabelAlphabet targetAlphabet,
			List<Integer> cumSum) {
		super(new EmptyPipe());

		getPipe().setDataAlphabet(inputAlphabet);
		getPipe().setTargetAlphabet(targetAlphabet);

		int i = 0;
		for (Sample s : dataSet) {
			if (++i % 1000 == 0) {
				System.out.println("Converting Sample " + i);
			}
			add(new Sample2Instance(s, inputAlphabet, targetAlphabet, getPipe(), cumSum));
		}

	}
}

class EmptyPipe extends Pipe {
	private static final long serialVersionUID = 3037084660867965364L;

	public Instance pipe(Instance carrier) {
		return carrier;
	}

}