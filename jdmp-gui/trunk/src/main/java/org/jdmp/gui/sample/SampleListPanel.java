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

package org.jdmp.gui.sample;

import java.util.Comparator;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jdmp.core.CoreObject;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;
import org.jdmp.gui.util.AbstractListPanel;
import org.ujmp.core.Matrix;
import org.ujmp.core.interfaces.GUIObject;

public class SampleListPanel extends AbstractListPanel {
	private static final long serialVersionUID = -5498996013925285012L;

	public SampleListPanel(HasSamples iSamples) {
		super();
		if (iSamples instanceof CoreObject) {
			this.object = ((CoreObject) iSamples).getGUIObject();
		} else {
			this.object = (GUIObject) iSamples;
		}

		dataModel = new SampleListTableModel(iSamples);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(Sample.class, new SampleTableCellRenderer());

		jTable.setModel(dataModel);

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable.getModel()) {

			@Override
			public Comparator<?> getComparator(int column) {
				switch (column) {
				case SampleListTableModel.RMSECOLUMN:
					return new Comparator<ClassificationSample>() {

						public int compare(ClassificationSample s1, ClassificationSample s2) {
							Matrix m1 = s1.getMatrix(Sample.RMSE);
							Matrix m2 = s2.getMatrix(Sample.RMSE);
							if (m1 != null && m2 != null) {
								return m1.compareTo(m2);
							} else {
								return 0;
							}
						}

					};
				}
				return super.getComparator(column);
			}

		};
		jTable.setRowSorter(sorter);

		jTable.getColumnModel().getColumn(SampleListTableModel.ICONCOLUMN).setMinWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(SampleListTableModel.ICONCOLUMN).setMaxWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(SampleListTableModel.ICONCOLUMN).setPreferredWidth(
				ICONWIDTH);

		updateTitle();
	}

	public void updateTitle() {
		getBorder().setTitle("Samples (" + jTable.getRowCount() + ")");
		repaint(1000);
	}

}
