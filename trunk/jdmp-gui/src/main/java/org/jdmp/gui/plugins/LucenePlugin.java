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

package org.jdmp.gui.plugins;

import java.util.Collection;
import java.util.LinkedList;

import org.ujmp.core.util.AbstractPlugin;

public class LucenePlugin extends AbstractPlugin {

	private AbstractPlugin plugin = null;

	public LucenePlugin() {
		try {
			Class<?> c = Class.forName("org.jdmp.lucene.Plugin");
			plugin = (AbstractPlugin) c.newInstance();
		} catch (Exception e) {
		}
	}

	@Override
	public Collection<String> getNeededClasses() {
		return plugin == null ? new LinkedList<String>() : plugin.getNeededClasses();
	}

	@Override
	public String getDescription() {
		return plugin == null ? "n/a" : plugin.getDescription();
	}

	@Override
	public void setDescription(String description) {
	}

	@Override
	public Collection<Object> getDependencies() {
		return plugin == null ? new LinkedList<Object>() : plugin.getDependencies();
	}

}
