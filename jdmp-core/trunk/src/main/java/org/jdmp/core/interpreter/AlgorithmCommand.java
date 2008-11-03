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

package org.jdmp.core.interpreter;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmCommand extends Command {

	private String algorithm = null;

	private List<String> targets = new ArrayList<String>();

	private List<String> sources = new ArrayList<String>();

	public void addTarget(String target) {
		targets.add(target);
	}

	public void addSource(String source) {
		sources.add(source);
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String toString() {
		return targets + " = " + algorithm + "(" + sources + ")";
	}

	public List<String> getSources() {
		return sources;
	}

	public List<String> getTargets() {
		return targets;
	}

	public String getAlgorithm() {
		return algorithm;
	}

}
