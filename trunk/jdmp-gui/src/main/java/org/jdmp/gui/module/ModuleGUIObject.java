/*
 * Copyright (C) 2008-2011 by Holger Arndt
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

package org.jdmp.gui.module;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdmp.core.module.Module;
import org.jdmp.gui.interpreter.CommandWindow;
import org.ujmp.gui.AbstractGUIObject;

public class ModuleGUIObject extends AbstractGUIObject {
	private static final long serialVersionUID = -317629566695423286L;

	private CommandWindow commandWindow = null;

	private Module module = null;

	private transient JFrame frame = null;

	private transient JPanel panel = null;

	public ModuleGUIObject(Module module) {
		this.module = module;
		commandWindow = new CommandWindow(this.getCoreObject());
	}

	public CommandWindow getCommandWindow() {
		return commandWindow;
	}

	public void clear() {
		module.clear();
	}

	public Icon getIcon() {
		return null;
	}

	public String getLabel() {
		return module.getLabel();
	}

	public void setLabel(String label) {
		module.setLabel(label);
	}
	
	public Object getLabelObject() {
		return module.getLabelObject();
	}

	public void setLabelObject(Object label) {
		module.setLabelObject(label);
	}

	public String getDescription() {
		return module.getDescription();
	}

	public void setDescription(String description) {
		module.setDescription(description);
	}

	
	public String toString() {
		return module.toString();
	}

	
	public Module getCoreObject() {
		return module;
	}

	
	public JFrame getFrame() {
		if (frame == null) {
			frame = new ModuleFrame(this);
		}
		return frame;
	}

	
	public JPanel getPanel() {
		if (panel == null) {
			panel = new ModulePanel(this);
		}
		return panel;
	}

}
