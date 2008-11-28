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
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;

public class CommandWindow extends JPanel implements KeyListener {
	private static final long serialVersionUID = 5931204715283753689L;

	private Module module = null;

	private int endPos = 0;
	private final JTextPane textField = new JTextPane();

	private final Document doc = textField.getDocument();

	public CommandWindow(Module m) {
		this.module = m;

		textField.setFont(new Font("Monospaced", Font.PLAIN, 13));

		textField.addKeyListener(this);

		appendText("PARSER PROBLEM:\n");
		appendText("PLEASE USE PARENTHESES TO DEFINE THE ORDER OF CALCULATIONS!\n");
		appendText("Examples: ((2*3)+5)+4 and 2-(7*8)\n");
		appendText("\n");
		appendText(">> ");
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

				int line = doc.getDefaultRootElement().getElementCount() - 1;

				Element map = doc.getDefaultRootElement();
				Element lineElem = map.getElement(line);
				int lineStart = lineElem.getStartOffset();

				int lineCount = map.getElementCount();
				int endOffset = lineElem.getEndOffset();
				int lineEnd = ((line == lineCount - 1) ? (endOffset - 1) : endOffset);

				int length = lineEnd - lineStart;
				String text = doc.getText(lineStart + 3, length - 3);

				if (e.getKeyCode() == KeyEvent.VK_ENTER) {

					if (text.endsWith(";")) {
						Result result = module.execute(text);
						if (result.getException() != null) {
							appendError("\n" + result);
						}
					} else {
						Result result = module.execute(text + ";");
						if (result.getException() != null) {
							appendError("\n" + result);
						} else {
							appendText("\n" + result);
						}
					}

					appendText("\n\n>> ");
					endPos = textField.getText().length();
					textField.setCaretPosition(endPos);
					e.consume();

					// TODO: this is a hack and should be solved via
					// EventListeners correctly
					getParent().getParent().repaint();

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

	public void appendError(String s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
				Color.RED);

		int len = doc.getLength();
		textField.setCaretPosition(len);
		textField.setCharacterAttributes(aset, false);
		textField.replaceSelection(s);
	}

	public void appendText(String s) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
				Color.BLACK);

		int len = doc.getLength();
		textField.setCaretPosition(len);
		textField.setCharacterAttributes(aset, false);
		textField.replaceSelection(s);
	}

	public void filterKeys(KeyEvent e) {
		try {

			if (textField.getCaretPosition() < endPos) {
				textField.setCaretPosition(doc.getLength());
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
