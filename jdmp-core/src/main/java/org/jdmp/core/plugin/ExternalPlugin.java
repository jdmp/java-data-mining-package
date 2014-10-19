/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.jdmp.core.plugin;

import java.util.Collection;
import java.util.LinkedList;

import org.ujmp.core.util.AbstractPlugin;

public abstract class ExternalPlugin {

	private AbstractPlugin plugin = null;

	public ExternalPlugin(String className) {
		try {
			Class<?> pluginClass = Class.forName(className);
			plugin = (AbstractPlugin) pluginClass.newInstance();
		} catch (Exception e) {
		}
	}

	public final Collection<String> getNeededClasses() {
		return plugin == null ? new LinkedList<String>() : plugin.getNeededClasses();
	}

	public final String getDescription() {
		return plugin == null ? "n/a" : plugin.getDescription();
	}

	public final Collection<String> getDependencies() {
		return plugin == null ? new LinkedList<String>() : plugin.getDependencies();
	}

	public final String getStatus() {
		return plugin == null ? "n/a" : plugin.getStatus();
	}

	public final boolean isAvailable() {
		return plugin == null ? false : plugin.isAvailable();
	}

}
