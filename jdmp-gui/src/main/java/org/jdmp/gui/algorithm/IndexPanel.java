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

package org.jdmp.gui.algorithm;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdmp.core.algorithm.index.Index;

public class IndexPanel extends JPanel implements KeyListener, ActionListener {
	private static final long serialVersionUID = -7338702449011774328L;

	private Index indexer = null;

	private JTextField query = null;

	private JButton button = null;

	public IndexPanel(Index indexer) {
		this.indexer = indexer;
		setLayout(new FlowLayout());
		query = new JTextField();
		query.addKeyListener(this);
		query.setPreferredSize(new Dimension(200, 20));
		add(query);
		button = new JButton("Search");
		button.addActionListener(this);
		add(button);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			actionPerformed(null);
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
		String s = query.getText();
		if (s != null) {
			s = s.trim();
			try {
				indexer.search(s).showGUI();
			} catch (Exception ex) {
			}
		}

	}
}
