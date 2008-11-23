/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

package org.jdmp.gui.interpreter;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;

public class CommandWindow extends JPanel implements KeyListener {
	private static final long serialVersionUID = 5931204715283753689L;

	private Module module = null;

	private int endPos = 0;

	private final JTextArea textField = new JTextArea();

	public CommandWindow(Module m) {
		this.module = m;

		textField.addKeyListener(this);

		textField.setText(">> ");
		endPos = textField.getText().length();

		setBorder(BorderFactory.createTitledBorder("Command Window"));
		setLayout(new BorderLayout());
		add(new JScrollPane(textField), BorderLayout.CENTER);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		filterKeys(e);

		try {

			if (textField.getCaretPosition() >= endPos) {

				int line = textField.getLineCount() - 1;
				int lineStart = textField.getLineStartOffset(line);
				int lineEnd = textField.getLineEndOffset(line);
				int length = lineEnd - lineStart;
				String text = textField.getText(lineStart + 3, length - 3);

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Result result = module.execute(text);
					textField.append("\n" + result + "\n>> ");
					endPos = textField.getText().length();
					textField.setCaretPosition(endPos);
					e.consume();
					return;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
		filterKeys(e);

	}

	public void filterKeys(KeyEvent e) {
		try {

			if (textField.getCaretPosition() < endPos) {
				e.consume();
				return;
			}

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				e.consume();
				return;
			}

			if (textField.getCaretPosition() <= endPos) {
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					e.consume();
					return;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					e.consume();
					return;
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
