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

package org.jdmp.mallet.sample;

import org.ujmp.core.text.TextSentence;
import org.ujmp.core.text.TextToken;

import cc.mallet.types.Alphabet;
import cc.mallet.types.Instance;
import cc.mallet.types.LabelSequence;
import cc.mallet.types.Token;
import cc.mallet.types.TokenSequence;

public class TextInstance extends Instance {
	private static final long serialVersionUID = 8362542181138846692L;

	public TextInstance(TextSentence textSentence, Alphabet targetAlphabet) {
		super(new TokenSequence(), new LabelSequence(targetAlphabet), textSentence.getId(), textSentence);

		TokenSequence tokenSequence = (TokenSequence) getData();
		LabelSequence labelSequence = (LabelSequence) getTarget();

		for (TextToken textToken : textSentence) {
			String text = textToken.getText();
			String label = textToken.getTag();
			tokenSequence.add(new Token(text));
			labelSequence.add(label);
		}
	}

}
