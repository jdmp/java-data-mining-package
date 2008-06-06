package org.jdmp.gui.util;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Comparator;
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
import javax.swing.table.TableRowSorter;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.HasAlgorithms;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.HasDataSets;
import org.jdmp.core.module.HasModules;
import org.jdmp.core.module.Module;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.HasSamples;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.util.CoreObject;
import org.jdmp.core.variable.HasVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.gui.actions.ObjectActions;
import org.jdmp.gui.algorithm.AlgorithmListTableModel;
import org.jdmp.gui.algorithm.AlgorithmTableCellRenderer;
import org.jdmp.gui.algorithm.actions.AlgorithmListActions;
import org.jdmp.gui.dataset.DataSetListTableModel;
import org.jdmp.gui.dataset.DataSetTableCellRenderer;
import org.jdmp.gui.dataset.actions.DataSetListActions;
import org.jdmp.gui.module.ModuleListTableModel;
import org.jdmp.gui.module.ModuleTableCellRenderer;
import org.jdmp.gui.module.actions.ModuleListActions;
import org.jdmp.gui.sample.SampleListTableModel;
import org.jdmp.gui.sample.SampleTableCellRenderer;
import org.jdmp.gui.sample.actions.SampleListActions;
import org.jdmp.gui.variable.VariableGUIObject;
import org.jdmp.gui.variable.VariableListTableModel;
import org.jdmp.gui.variable.VariableTableCellRenderer;
import org.jdmp.gui.variable.actions.VariableListActions;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.interfaces.GUIObject;
import org.jdmp.matrix.interfaces.HasToolTip;

public class ObjectListPanel extends JPanel implements MouseListener, KeyListener,
		ListSelectionListener, TableModelListener {
	private static final long serialVersionUID = -8449938064172061258L;

	protected static final Logger logger = Logger.getLogger(ObjectListPanel.class.getName());

	private static final int IVARIABLES = 1;

	private static final int IALGORITHMS = 2;

	private static final int IDATASETS = 3;

	private static final int IMODULES = 4;

	private static final int ISAMPLES = 5;

	private JTable jTable = null;

	private int type = 0;

	private GUIObject object = null;

	private int ICONWIDTH = UIManager.getInt("Table.iconWidth");

	private TableModel dataModel = null;

	private JScrollPane scrollPane = null;

	public ObjectListPanel() {
		this.setLayout(new GridBagLayout());

		setBorder(BorderFactory.createTitledBorder("Object List"));

		jTable = new JTable(dataModel) {
			private static final long serialVersionUID = -1349144990029853301L;

			@Override
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
		jTable.setAutoCreateRowSorter(true);

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

	public ObjectListPanel(HasAlgorithms iAlgorithms) {
		this();
		this.object = (GUIObject) iAlgorithms;
		this.type = IALGORITHMS;

		dataModel = new AlgorithmListTableModel(iAlgorithms);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(Algorithm.class, new AlgorithmTableCellRenderer());
		jTable.setModel(dataModel);

		jTable.getColumnModel().getColumn(AlgorithmListTableModel.ICONCOLUMN)
				.setMinWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(AlgorithmListTableModel.ICONCOLUMN)
				.setMaxWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(AlgorithmListTableModel.ICONCOLUMN).setPreferredWidth(
				ICONWIDTH);

		updateTitle();
	}

	public ObjectListPanel(HasDataSets iDataSets) {
		this();
		this.object = (GUIObject) iDataSets;
		this.type = IDATASETS;

		dataModel = new DataSetListTableModel(iDataSets);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(DataSet.class, new DataSetTableCellRenderer());

		jTable.setModel(dataModel);

		jTable.getColumnModel().getColumn(DataSetListTableModel.ICONCOLUMN).setMinWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(DataSetListTableModel.ICONCOLUMN).setMaxWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(DataSetListTableModel.ICONCOLUMN).setPreferredWidth(
				ICONWIDTH);

		updateTitle();
	}

	public ObjectListPanel(HasModules iModules) {
		this();
		this.object = (GUIObject) iModules;
		this.type = IMODULES;

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

	public ObjectListPanel(HasSamples iSamples) {
		this();
		if (iSamples instanceof CoreObject) {
			this.object = ((CoreObject) iSamples).getGUIObject();
		} else {
			this.object = (GUIObject) iSamples;
		}
		this.type = ISAMPLES;

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
							Matrix m1 = s1.getRMSEMatrix();
							Matrix m2 = s2.getRMSEMatrix();
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

	public ObjectListPanel(HasVariables iVariables) {
		this();
		if (iVariables instanceof CoreObject) {
			this.object = ((CoreObject) iVariables).getGUIObject();
		} else {
			this.object = (GUIObject) iVariables;
		}
		this.type = IVARIABLES;

		dataModel = new VariableListTableModel(iVariables);
		dataModel.addTableModelListener(this);
		jTable.setDefaultRenderer(Variable.class, new VariableTableCellRenderer());

		jTable.setModel(dataModel);

		jTable.getColumnModel().getColumn(VariableListTableModel.ICONCOLUMN).setMinWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(VariableListTableModel.ICONCOLUMN).setMaxWidth(ICONWIDTH);
		jTable.getColumnModel().getColumn(VariableListTableModel.ICONCOLUMN).setPreferredWidth(
				ICONWIDTH);

		updateTitle();
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
			switch (type) {
			case IVARIABLES:
				actions = new VariableListActions(this, (HasVariables) object);
				break;
			case IALGORITHMS:
				actions = new AlgorithmListActions(this, (HasAlgorithms) object);
				break;
			case IDATASETS:
				actions = new DataSetListActions(this, (HasDataSets) object);
				break;
			case IMODULES:
				actions = new ModuleListActions(this, (HasModules) object);
				break;
			case ISAMPLES:
				actions = new SampleListActions(this, (HasSamples) object);
				break;
			}
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
			if (o instanceof CoreObject) {
				otemp = ((CoreObject) o).getGUIObject();
			} else
				otemp = (GUIObject) o;
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
			otemp = (GUIObject) dataModel.getValueAt(selectedRow, 0);
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
					for (JComponent jc : new ObjectActions(this, o)) {
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
				Variable v = ((VariableGUIObject) o).getVariable();
				switch (e.getKeyChar()) {
				case 'g':
					v.fillGaussian();
					break;
				case 'u':
					v.fillUniform();
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

	public void updateTitle() {
		String name = "";
		switch (type) {
		case IALGORITHMS:
			name = "Algorithms";
			break;
		case IVARIABLES:
			name = "Variables";
			break;
		case IDATASETS:
			name = "DataSets";
			break;
		case IMODULES:
			name = "Modules";
			break;
		case ISAMPLES:
			name = "Samples";
			break;
		}
		getBorder().setTitle(name + " (" + jTable.getRowCount() + ")");
		repaint(1000);
	}

	public void tableChanged(TableModelEvent e) {
		updateTitle();
	}
}
