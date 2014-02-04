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

import org.ujmp.core.Matrix;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.LabelAlphabet;
import cc.mallet.types.LabelSequence;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class Matrix2TokenSequencePipe extends Pipe {
	private static final long serialVersionUID = -3076008797062267841L;

	public Matrix2TokenSequencePipe() {
		super(null, new LabelAlphabet());
	}

	public Instance pipe(Instance carrier) {
		Matrix matrix = (Matrix) carrier.getData();
		TokenSequence tokenSequence = new TokenSequence();

		for (int i = 0; i < matrix.getRowCount(); i++) {
			String s = matrix.getAsString(i, 0);
			tokenSequence.add(new Token(s));
		}

		LabelSequence labelSequence = null;
		if (matrix.getColumnCount() == 2) {
			labelSequence = new LabelSequence(getTargetAlphabet());

			for (int i = 0; i < matrix.getRowCount(); i++) {
				String s = matrix.getAsString(i, 1);
				labelSequence.add(s);
			}
		}

		return new Instance(tokenSequence, labelSequence, null, null);
	}

}
