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

package org.jdmp.gui.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.jdmp.core.JDMPCoreObject;
import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.variable.Variable;
import org.jdmp.gui.algorithm.AlgorithmGUIObject;
import org.jdmp.gui.module.ModuleGUIObject;
import org.jdmp.gui.module.actions.ModuleActions;
import org.jdmp.gui.sample.SampleGUIObject;
import org.jdmp.gui.sample.actions.SampleActions;
import org.jdmp.gui.variable.VariableGUIObject;
import org.jdmp.gui.variable.actions.VariableActions;
import org.ujmp.core.interfaces.GUIObject;
import org.ujmp.core.interfaces.HasToolTip;
import org.ujmp.gui.util.FrameManager;

public abstract class AbstractListPanel extends JPanel implements MouseListener, KeyListener,
		ListSelectionListener, TableModelListener {
	private static final long serialVersionUID = -8449938064172061258L;

	protected static final Logger logger = Logger.getLogger(AbstractListPanel.class.getName());

	protected JTable jTable = null;

	protected GUIObject object = null;

	protected TableModel dataModel = null;

	protected JScrollPane scrollPane = null;

	public AbstractListPanel() {
		this.setLayout(new GridBagLayout());

		setBorder(BorderFactory.createTitledBorder("Object List"));

		jTable = new JTable(dataModel) {
			private static final long serialVersionUID = -1349144990029853301L;

			
			public String getToolTipText(MouseEvent event) {
				try {
					int row = rowAtPoint(event.getPoint());
					if (row < jTable.getRowCount()) {
						Object o = jTable.getValueAt(row, 0);
						if (o instanceof HasToolTip) {
							return ((HasToolTip) o).getToolTipText();
						}
					}
				} catch (Exception e) {
				}
				return null;
			}
		};

		jTable.addMouseListener(this);
		jTable.addKeyListener(this);
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		int rowHeight = UIManager.getInt("Table.rowHeight");
		if (rowHeight < 1)
			rowHeight = 32;

		jTable.setRowHeight(rowHeight);

		scrollPane = new JScrollPane(jTable);
		this.addMouseListener(this);

		this.add(jTable.getTableHeader(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	}

	
	public TitledBorder getBorder() {
		return (TitledBorder) super.getBorder();
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this)
			mouseClickedOnObjectList(e);
		else
			mouseClickedOnObject(e);
	}

	public void mouseClickedOnObjectList(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			break;
		case MouseEvent.BUTTON2:
			break;
		case MouseEvent.BUTTON3:
			List<JComponent> actions = null;
			// switch (type) {
			// case IVARIABLES:
			// actions = new VariableListActions(this, (HasVariables) object);
			// break;
			// case IALGORITHMS:
			// actions = new AlgorithmListActions(this, (HasAlgorithms) object);
			// break;
			// case IDATASETS:
			// actions = new DataSetListActions(this, (HasDataSets) object);
			// break;
			// case IMODULES:
			// actions = new ModuleListActions(this, (HasModuleList) object);
			// break;
			// case ISAMPLES:
			// actions = new SampleListActions(this, (HasSampleList) object);
			// break;
			// }
			if (actions != null) {
				JPopupMenu popup = new JPopupMenu();
				for (JComponent jc : actions) {
					popup.add(jc);
				}
				popup.show(this, e.getX(), e.getY());
			}
			break;
		}
	}

	public void mouseClickedOnObject(MouseEvent e) {

		GUIObject otemp = null;

		int selectedRow = jTable.getSelectedRow();
		if (selectedRow >= 0) {
			Object o = dataModel.getValueAt(selectedRow, 0);
			if (o instanceof JDMPCoreObject) {
				otemp = ((JDMPCoreObject) o).getGUIObject();
			} else {
				otemp = (GUIObject) o;
			}
		}

		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			if (e.getClickCount() == 2) {
				if (otemp != null) {
					FrameManager.showFrame(otemp);
				}
			}
			break;
		case MouseEvent.BUTTON2:
			break;
		case MouseEvent.BUTTON3:
			int row = jTable.rowAtPoint(e.getPoint());
			jTable.setRowSelectionInterval(row, row);
			selectedRow = jTable.getSelectedRow();
			Object obj = dataModel.getValueAt(selectedRow, 0);
			if (obj instanceof JDMPCoreObject) {
				otemp = ((JDMPCoreObject) obj).getGUIObject();
			} else {
				otemp = (GUIObject) obj;
			}
			if (otemp != null) {
				// Reference ref = otemp.getReference();
				GUIObject o = otemp;

				// switch (type) {
				// case IVARIABLES:
				// o = Module.getInstance().getVariableForReference(ref);
				// break;
				// case IALGORITHMS:
				// o = Module.getInstance().getAlgorithmForReference(ref);
				// break;
				// case IDATASETS:
				// o = Module.getInstance().getDataSetForReference(ref);
				// break;
				// case IMODULES:
				// o = otemp;
				// break;
				// case ISAMPLES:
				// o = ((HasSamples) object).getSample(selectedRow);
				// break;
				// }
				if (o != null) {
					JPopupMenu popup = new JPopupMenu();
					List<JComponent> objectActions = null;

					if (o instanceof AlgorithmGUIObject) {
					} else if (o instanceof ModuleGUIObject) {
						objectActions = new ModuleActions(this, (ModuleGUIObject) o);
					} else if (o instanceof SampleGUIObject) {
						objectActions = new SampleActions(this, (SampleGUIObject) o);
					} else if (o instanceof VariableGUIObject) {
						objectActions = new VariableActions(this, (VariableGUIObject) o);
					}

					for (JComponent jc : objectActions) {
						popup.add(jc);
					}
					popup.show(jTable, e.getX(), e.getY());
				}
			}
			break;
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		try {
			GUIObject o = null;
			int row = jTable.getSelectedRow();
			if (row >= 0 && row < dataModel.getRowCount()) {
				o = (GUIObject) dataModel.getValueAt(row, 0);
				if (o != null) {
					// ref = o.getReference();
				}
			}

			if (e.getKeyChar() == 127) {
				// if (ref != null) {
				// int response = JOptionPane.showConfirmDialog(null, "Do you
				// really want to delete this Object?");
				// // if (response == JOptionPane.OK_OPTION)
				// // getWorkspace().removeObject(ref);
				//
				// if (jTable.getRowCount() > 0) {
				// row = (row == jTable.getRowCount() ? row - 1 : row);
				// jTable.setRowSelectionInterval(row, row);
				// }
				// }
			} else if (e.getKeyChar() == 3) {
				// System.out.println("copy:" + ref);
				// if (ref != null) {
				// // Workspace.getInstance().copyToClipboard(ref);
				// }
			} else
				System.out.println("char entered in ObjectListPanel: " + e.getKeyChar());

			if (o instanceof VariableGUIObject) {
				Variable v = ((VariableGUIObject) o).getCoreObject();
				switch (e.getKeyChar()) {
				case 'g':
					// v.fillGaussian();
					break;
				case 'u':
					// v.fillUniform();
					break;
				default:
					break;
				}
			} else if (o instanceof Algorithm) {
				Algorithm a = (Algorithm) o;
				switch (e.getKeyChar()) {
				case 'c':
					a.calculate();
					break;
				default:
					break;
				}
			}
		} catch (Exception ex) {
			logger.log(Level.WARNING, "error processing key event", e);
		}
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {
	}

	public abstract void updateTitle();

	public void tableChanged(TableModelEvent e) {
		updateTitle();
	}
}
