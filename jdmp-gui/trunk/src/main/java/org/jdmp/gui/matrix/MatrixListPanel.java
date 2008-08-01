package org.jdmp.gui.matrix;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.matrix.actions.FillWithValueAction;
import org.jdmp.gui.matrix.actions.MatrixActions;
import org.ujmp.core.doublecalculation.Calculation.Ret;
import org.ujmp.core.doublecalculation.entrywise.creator.Rand;
import org.ujmp.core.doublecalculation.entrywise.creator.Randn;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.interfaces.HasMatrixList;
import org.ujmp.core.interfaces.HasToolTip;

public class MatrixListPanel extends JPanel implements MouseListener, ListSelectionListener,
		TableModelListener {
	private static final long serialVersionUID = -6599031713168986357L;

	private JTable jTable = null;

	private HasMatrixList variable = null;

	private MatrixListTableModel dataModel = null;

	private boolean scroll = true;

	public MatrixListPanel(HasMatrixList variable) {
		this.variable = variable;
		this.setLayout(new GridBagLayout());

		dataModel = new MatrixListTableModel(variable);

		setBorder(BorderFactory.createTitledBorder("Matrices (" + dataModel.getRowCount() + ")"));

		jTable = new JTable(dataModel) {
			private static final long serialVersionUID = -1349144990029853301L;

			@Override
			public String getToolTipText(MouseEvent event) {
				int row = rowAtPoint(event.getPoint());
				if (row < jTable.getRowCount()) {
					Object o = jTable.getValueAt(row, 0);
					if (o instanceof HasToolTip) {
						return ((HasToolTip) o).getToolTipText();
					}
				}
				return null;
			}
		};

		jTable.setSelectionModel(dataModel.getRowSelectionModel());
		jTable.getSelectionModel().addListSelectionListener(this);
		jTable.addMouseListener(this);
		addMouseListener(this);
		// jTable.addKeyListener(this);
		jTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		dataModel.addTableModelListener(this);

		int rowHeight = UIManager.getInt("Table.rowHeight");
		if (rowHeight < 1)
			rowHeight = 32;

		jTable.setRowHeight(rowHeight);

		jTable.getColumnModel().getColumn(0).setMinWidth(45);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(45);
		jTable.setDefaultRenderer(MatrixGUIObject.class, new MatrixTableCellRenderer());

		this.add(jTable.getTableHeader(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
		this.add(new JScrollPane(jTable), new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0,
				GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

		registerKeyboardActions();

	}

	private void registerKeyboardActions() {
		for (JComponent c : new MatrixActions(this, null, dataModel)) {
			if (c instanceof JMenuItem) {
				registerKeyboardAction(((JMenuItem) c).getAction());
			}
		}
	}

	private void registerKeyboardAction(Action a) {
		KeyStroke keyStroke = (KeyStroke) a.getValue(Action.ACCELERATOR_KEY);
		getActionMap().put(a.getValue(Action.NAME), a);
		getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keyStroke, a.getValue(Action.NAME));
	}

	public void mouseClicked(MouseEvent e) {
		try {
			if (e.getSource() == this)
				mouseClickedOnObjectList(e);
			else
				mouseClickedOnObject(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void mouseClickedOnObjectList(MouseEvent e) {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			break;
		case MouseEvent.BUTTON2:
			break;
		case MouseEvent.BUTTON3:
			// JPopupMenu popup = null;
			// popup = variable.getActions();
			// if (popup != null)
			// popup.show(this, e.getX(), e.getY());
			// break;
		}
	}

	public void mouseClickedOnObject(MouseEvent e) throws MatrixException {
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			if (e.getClickCount() == 2) {
				int row = jTable.rowAtPoint(e.getPoint());
				jTable.setRowSelectionInterval(row, row);
				MatrixGUIObject m = (MatrixGUIObject) dataModel.getValueAt(jTable.getSelectedRow(),
						0);
				MatrixFrame mf = new MatrixFrame(m);
				mf.setVisible(true);
			}
			break;
		case MouseEvent.BUTTON2:
			break;
		case MouseEvent.BUTTON3:
			int row = jTable.rowAtPoint(e.getPoint());
			jTable.setRowSelectionInterval(row, row);
			MatrixGUIObject m = (MatrixGUIObject) dataModel.getValueAt(jTable.getSelectedRow(), 0);
			JPopupMenu popup = new JPopupMenu();

			if (variable instanceof Variable) {
				for (JComponent c : new MatrixActions(this, m, variable)) {
					popup.add(c);
				}
			} else {
				for (JComponent c : new MatrixActions(this, m, variable)) {
					popup.add(c);
				}
			}

			popup.show(jTable, e.getX(), e.getY());
			break;
		}
	}

	public void mousePressed(MouseEvent e) {
		scroll = false;
	}

	public void mouseReleased(MouseEvent e) {
		scroll = true;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void keyTyped(KeyEvent e) {
		try {
			MatrixGUIObject m = null;
			int row = jTable.getSelectedRow();
			if (row >= 0 && row < dataModel.getRowCount()) {
				m = (MatrixGUIObject) dataModel.getValueAt(row, 0);
			}

			if (e.getKeyChar() == 127) {
				// if (ref != null) {
				// int response = JOptionPane.showConfirmDialog(null, "Do you
				// really
				// want to delete this Object?");
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
				// Workspace.getInstance().copyToClipboard(ref);
				// }
			} else {
				System.out.println("char entered in MatrixListPanel: " + e.getKeyChar());
			}

			if (variable instanceof Variable) {
				if (m != null) {
					switch (e.getKeyChar()) {
					case '.':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '-':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '0':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '1':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '2':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '3':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '4':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '5':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '6':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '7':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '8':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case '9':
						new FillWithValueAction(null, m, variable, "" + e.getKeyChar())
								.actionPerformed(null);
						break;
					case 'g':
						new Randn(m.getMatrix()).calc(Ret.ORIG);
						((Variable) variable).fireValueChanged(m.getMatrix());
						break;
					case 'u':
						new Rand(m.getMatrix()).calc(Ret.ORIG);
						((Variable) variable).fireValueChanged(m.getMatrix());
						break;
					default:
						break;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void valueChanged(ListSelectionEvent e) {
		if (scroll && e.getValueIsAdjusting() == false) {
			int minRow = jTable.getSelectionModel().getMinSelectionIndex();
			int maxRow = jTable.getSelectionModel().getMaxSelectionIndex();
			if (minRow == maxRow) {
				JViewport viewport = (JViewport) jTable.getParent();
				Rectangle rect = jTable.getCellRect(minRow, 0, true);
				Rectangle viewRect = viewport.getViewRect();
				rect.setLocation(rect.x - viewRect.x, rect.y - viewRect.y);
				int centerX = (viewRect.width - rect.width) / 2;
				int centerY = (viewRect.height - rect.height) / 2;
				if (rect.x < centerX) {
					centerX = -centerX;
				}
				if (rect.y < centerY) {
					centerY = -centerY;
				}
				rect.translate(centerX, centerY);
				viewport.scrollRectToVisible(rect);
			}
		}
		fireValueChanged(e); // for MatrixPaintPanel
	}

	public void fireValueChanged(ListSelectionEvent e) {
		for (Object o : listenerList.getListenerList()) {
			if (o instanceof ListSelectionListener) {
				((ListSelectionListener) o).valueChanged(e);
			}
		}
	}

	public void addListSelectionListener(ListSelectionListener l) {
		listenerList.add(ListSelectionListener.class, l);
	}

	public void removeListSelectionListener(ListSelectionListener l) {
		listenerList.add(ListSelectionListener.class, l);
	}

	public MatrixGUIObject getSelectedMatrix() {
		ListSelectionModel lsm = jTable.getSelectionModel();
		if (lsm.isSelectionEmpty() || lsm.getValueIsAdjusting())
			return null;
		int selectedRow = lsm.getMinSelectionIndex();
		return (MatrixGUIObject) jTable.getModel().getValueAt(selectedRow,
				MatrixListTableModel.MATRIXCOLUMN);
	}

	public void updateTitle() {
		getBorder().setTitle("Matrices (" + jTable.getRowCount() + ")");
		repaint(1000);
	}

	public void tableChanged(TableModelEvent e) {
		updateTitle();
	}

	public TitledBorder getBorder() {
		return (TitledBorder) super.getBorder();
	}

}
