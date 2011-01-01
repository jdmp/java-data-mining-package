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

package org.jdmp.gui.sample.actions;

import javax.swing.JComponent;

import org.jdmp.gui.sample.SampleGUIObject;
import org.ujmp.gui.actions.ObjectAction;

public abstract class SampleAction extends ObjectAction {
	private static final long serialVersionUID = 779960534963504237L;

	public SampleAction(JComponent c, SampleGUIObject p) {
		super(c, p);
	}

	public SampleGUIObject getSample() {
		return (SampleGUIObject) getGUIObject();
	}
}
