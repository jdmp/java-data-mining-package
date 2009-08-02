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
