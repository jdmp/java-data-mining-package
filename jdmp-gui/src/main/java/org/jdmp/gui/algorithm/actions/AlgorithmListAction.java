/*
 * Copyright (C) 2008-2013 by Holger Arndt
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

package org.jdmp.gui.algorithm.actions;

import javax.swing.JComponent;

import org.jdmp.core.JDMPCoreObject;
import org.jdmp.core.algorithm.HasAlgorithms;
import org.ujmp.gui.actions.ObjectAction;

public abstract class AlgorithmListAction extends ObjectAction {
	private static final long serialVersionUID = 4964145257911669819L;

	public AlgorithmListAction(JComponent c, HasAlgorithms iAlgorithms) {
		super(c, ((JDMPCoreObject) iAlgorithms).getGUIObject());
	}

	public HasAlgorithms getIAlgorithms() {
		return (HasAlgorithms) getGUIObject();
	}

}
