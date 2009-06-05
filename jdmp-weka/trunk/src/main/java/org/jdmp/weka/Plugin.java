/*
 * Copyright (C) 2008-2009 Holger Arndt, A. Naegele and M. Bundschus
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

package org.jdmp.weka;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ujmp.core.util.AbstractPlugin;

public class Plugin extends AbstractPlugin {

	private final List<Object> dependencies = new ArrayList<Object>();

	private final List<String> neededClasses = new ArrayList<String>();

	public Plugin() {
		dependencies.add("ujmp-core");
		dependencies.add("jdmp-core");
		dependencies.add("weka.jar");
		neededClasses.add("weka.classifiers.Classifier");
	}

	@Override
	public String getDescription() {
		return "interface to connect to Weka";
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public Collection<Object> getDependencies() {
		return dependencies;
	}

	@Override
	public Collection<String> getNeededClasses() {
		return neededClasses;
	}

}
