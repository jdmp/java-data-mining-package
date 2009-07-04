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

package org.jdmp.gui.module;

import org.jdmp.core.CoreObject;
import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;
import org.jdmp.gui.util.AbstractListPanel;
import org.ujmp.core.interfaces.GUIObject;

public class ModuleListPanel extends AbstractListPanel {
	private static final long serialVersionUID = -4213201884857411875L;

	public ModuleListPanel(HasModules iModules) {
		super();
		if (iModules instanceof CoreObject) {
			this.object = ((CoreObject) iModules).getGUIObject();
		} else {
			this.object = (GUIObject) iModules;
		}

		dataModel = new ModuleListTableModel(iModules);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(Module.class, new ModuleTableCellRenderer());

		jTable.setModel(dataModel);

		jTable.getColumnModel().getColumn(ModuleListTableModel.ICONCOLUMN).setMinWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(ModuleListTableModel.ICONCOLUMN).setMaxWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(ModuleListTableModel.ICONCOLUMN).setPreferredWidth(
				ICONWIDTH);

		updateTitle();
	}

	@Override
	public void updateTitle() {
		getBorder().setTitle("Modules (" + jTable.getRowCount() + ")");
		repaint(1000);
	}

}
