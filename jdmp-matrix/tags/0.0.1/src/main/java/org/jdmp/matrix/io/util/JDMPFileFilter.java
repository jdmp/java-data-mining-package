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

package org.jdmp.matrix.io.util;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.filechooser.FileFilter;

public class JDMPFileFilter extends FileFilter {

	private String label = null;

	private String suffix = null;

	public static final JDMPFileFilter PDFFilter = new JDMPFileFilter("PDF Files", ".pdf");

	public static final JDMPFileFilter PLTFilter = new JDMPFileFilter("PLT Files", ".plt");

	public static final JDMPFileFilter PNGFilter = new JDMPFileFilter("PNG Files", ".png");

	public static final JDMPFileFilter JPEGFilter = new JDMPFileFilter("JPEG Files", ".jpg");

	public static final JDMPFileFilter XLSFilter = new JDMPFileFilter("XLS Files", ".xls");

	public static final JDMPFileFilter XMLFilter = new JDMPFileFilter("XML Files", ".xls");

	public static final JDMPFileFilter MatlabFilter = new JDMPFileFilter("Matlab Files", ".m");

	public JDMPFileFilter(String label, String suffix) {
		this.label = label;
		this.suffix = suffix;
	}

	public static Collection<FileFilter> getChoosableFileFilters(Object o) {
		List<FileFilter> filters = new LinkedList<FileFilter>();
		filters.add(JDMPFileFilter.XMLFilter);
		filters.add(JDMPFileFilter.MatlabFilter);
		filters.add(JDMPFileFilter.PLTFilter);
		filters.add(JDMPFileFilter.XLSFilter);
		filters.add(JDMPFileFilter.PDFFilter);
		filters.add(JDMPFileFilter.PNGFilter);
		filters.add(JDMPFileFilter.JPEGFilter);

		return filters;
	}

	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		if (f.getName().toLowerCase().endsWith(suffix)) {
			return true;
		} else {
			return false;
		}
	}

	public String getDescription() {
		return label;
	}

	public String getSuffix() {
		return suffix;
	}

	public String toString() {
		return label + " (" + suffix + ")";
	}

}
