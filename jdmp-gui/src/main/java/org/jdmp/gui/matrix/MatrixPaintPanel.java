package org.jdmp.gui.matrix;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.gui.io.ExportJPEG;
import org.jdmp.gui.io.ExportPDF;
import org.jdmp.gui.io.ExportPNG;
import org.jdmp.gui.matrix.actions.MatrixActions;
import org.jdmp.gui.util.CanBeRepainted;
import org.jdmp.gui.util.GraphicsExecutor;
import org.jdmp.matrix.interfaces.HasToolTip;

public class MatrixPaintPanel extends JPanel implements ComponentListener, TableModelListener,
		MouseListener, MouseMotionListener, CanBeRepainted, HasToolTip, ListSelectionListener {
	private static final long serialVersionUID = 843653796010276950L;

	private static final Logger logger = Logger.getLogger(MatrixPaintPanel.class.getName());

	private MatrixGUIObject matrix = null;

	private MatrixRenderer renderer = new MatrixRenderer();

	private BufferedImage bufferedImage = null;

	private static int PADDINGX = UIManager.getInt("Table.paddingX");

	private static int PADDINGY = UIManager.getInt("Table.paddingY");

	private int startRow = 0;

	private int startCol = 0;

	private String oldToolTip = "";

	public MatrixPaintPanel(MatrixGUIObject matrix, boolean showBorder) {
		if (showBorder) {
			setBorder(BorderFactory.createTitledBorder("Matrix Visualization"));
		}

		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		setPreferredSize(new Dimension(600, 400));

		setMatrix(matrix);

		ToolTipManager.sharedInstance().registerComponent(this);

		registerKeyboardActions();
	}

	private void registerKeyboardActions() {
		for (JComponent c : new MatrixActions(this, matrix, null)) {
			if (c instanceof JMenuItem) {
				registerKeyboardAction(((JMenuItem) c).getAction());
			}
		}
	}

	private void registerKeyboardAction(Action a) {
		KeyStroke keyStroke = (KeyStroke) a.getValue(Action.ACCELERATOR_KEY);
		getActionMap().put(a.getValue(Action.NAME), a);
		getInputMap(WHEN_IN_FOCUSED_WINDOW).put(keyStroke, a.getValue(Action.NAME));
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		renderer.setSize(getSize());
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentShown(ComponentEvent e) {
	}

	public void tableChanged(TableModelEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void mouseClicked(MouseEvent e) {
		if (matrix != null) {

			if (e.getButton() == MouseEvent.BUTTON3) {

				int newRow = getRowPos(e.getY());
				int newCol = getColPos(e.getX());

				newRow = newRow < 0 ? 0 : newRow;
				newCol = newCol < 0 ? 0 : newCol;
				newRow = newRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : newRow;
				newCol = newCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : newCol;

				JPopupMenu popup = new MatrixPopupMenu(this, matrix, newRow, newCol);
				popup.show(this, e.getX(), e.getY());
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (matrix != null) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				startRow = getRowPos(e.getY());
				startCol = getColPos(e.getX());
				startRow = startRow < 0 ? 0 : startRow;
				startCol = startCol < 0 ? 0 : startCol;
				startRow = startRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : startRow;
				startCol = startCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1
						: startCol;
				matrix.getRowSelectionModel().setValueIsAdjusting(true);
				matrix.getColumnSelectionModel().setValueIsAdjusting(true);
				matrix.getRowSelectionModel().setSelectionInterval(startRow, startRow);
				matrix.getColumnSelectionModel().setSelectionInterval(startCol, startCol);
			}
			repaint(100);
		}
	}

	private int getRowPos(int y) {
		return (int) Math.floor((double) matrix.getRowCount() * (double) y / (double) getHeight());
	}

	private int getColPos(int x) {
		return (int) Math
				.floor((double) matrix.getColumnCount() * (double) x / (double) getWidth());
	}

	public void mouseReleased(MouseEvent e) {
		if (matrix != null) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				int newRow = getRowPos(e.getY());
				int newCol = getColPos(e.getX());
				newRow = newRow < 0 ? 0 : newRow;
				newCol = newCol < 0 ? 0 : newCol;
				newRow = newRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : newRow;
				newCol = newCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : newCol;
				matrix.getRowSelectionModel().setValueIsAdjusting(false);
				matrix.getColumnSelectionModel().setValueIsAdjusting(false);
				matrix.getRowSelectionModel().setSelectionInterval(startRow, newRow);
				matrix.getColumnSelectionModel().setSelectionInterval(startCol, newCol);
				repaint(100);
			}
		}
	}

	public MatrixGUIObject getMatrix() {
		return matrix;
	}

	public String getToolTipText(MouseEvent e) {
		if (matrix != null) {
			int r = getRowPos(e.getY());
			int c = getColPos(e.getX());
			r = r < 0 ? 0 : r;
			c = c < 0 ? 0 : c;
			r = r >= matrix.getRowCount() ? matrix.getRowCount() - 1 : r;
			c = c >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : c;

			Object o = matrix.getValueAt(r, c);
			String toolTip = "<html><b>[" + r + "," + c + "]</b><br>";
			if (o != null) {
				toolTip += "<br>" + o;
			}
			toolTip += "</html>";
			if (("" + toolTip).length() == ("" + oldToolTip).length()) {
				toolTip = toolTip + "\n";
			}
			oldToolTip = toolTip;
			return toolTip;
		} else {
			return null;
		}
	}

	public void setMatrix(MatrixGUIObject m) {
		if (matrix != null) {
			matrix.removeTableModelListener(this);
			matrix.getRowSelectionModel().removeListSelectionListener(this);
			matrix.getColumnSelectionModel().removeListSelectionListener(this);
		}

		if (m != null) {
			matrix = m;
			renderer.setMatrix(m);
		} else {
			matrix = null;
		}

		if (matrix != null) {
			matrix.addTableModelListener(this);
			matrix.getRowSelectionModel().addListSelectionListener(this);
			matrix.getColumnSelectionModel().addListSelectionListener(this);
		}

		GraphicsExecutor.scheduleUpdate(this);
	}

	protected void finalize() throws Throwable {
		super.finalize();
		if (matrix != null) {
			matrix.removeTableModelListener(this);
		}
		ToolTipManager.sharedInstance().unregisterComponent(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (matrix != null && bufferedImage != null) {
			g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);

			if (!matrix.getRowSelectionModel().isSelectionEmpty()) {
				g2d.setColor(Color.BLUE);

				int x1 = matrix.getColumnSelectionModel().getMinSelectionIndex();
				int x2 = matrix.getColumnSelectionModel().getMaxSelectionIndex();
				int y1 = matrix.getRowSelectionModel().getMinSelectionIndex();
				int y2 = matrix.getRowSelectionModel().getMaxSelectionIndex();
				double sx = (double) (getWidth() - PADDINGX - PADDINGX)
						/ (double) matrix.getColumnCount();
				double sy = (double) (getHeight() - PADDINGY - PADDINGY)
						/ (double) matrix.getRowCount();
				g2d.setStroke(new BasicStroke(2.0f));
				g2d.drawRect((int) Math.floor(PADDINGX + x1 * sx), (int) Math.floor(PADDINGY + y1
						* sy), (int) Math.ceil(sx + (x2 - x1) * sx), (int) Math.ceil(sy + (y2 - y1)
						* sy));
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
				g2d.fillRect((int) Math.floor(PADDINGX + x1 * sx), (int) Math.floor(PADDINGY + y1
						* sy), (int) Math.ceil(sx + (x2 - x1) * sx), (int) Math.ceil(sy + (y2 - y1)
						* sy));
			}
		} else {
			g2d.setColor(Color.GRAY);
			g2d.drawLine(0, 0, getWidth(), getHeight());
			g2d.drawLine(0, getHeight(), getWidth(), 0);
		}
	}

	public void repaintUI() {
		if (matrix != null && getWidth() > 0 && getHeight() > 0) {
			BufferedImage tempBufferedImage = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_RGB);
			renderer.setSize(getWidth(), getHeight());
			Graphics2D g2d = (Graphics2D) tempBufferedImage.getGraphics();
			renderer.paintComponent(g2d);
			g2d.dispose();
			bufferedImage = tempBufferedImage;
		}
	}

	public void exportToPDF(File file) {
		ExportPDF.save(file, this);
	}

	public void exportToJPEG(File file) {
		ExportJPEG.save(file, this);
	}

	public void exportToPNG(File file) {
		ExportPNG.save(file, this);
	}

	public void mouseDragged(MouseEvent e) {
		if (matrix != null) {
			if (matrix.getRowSelectionModel().getValueIsAdjusting()) {
				int newRow = getRowPos(e.getY());
				int newCol = getColPos(e.getX());
				newRow = newRow < 0 ? 0 : newRow;
				newCol = newCol < 0 ? 0 : newCol;
				newRow = newRow >= matrix.getRowCount() ? matrix.getRowCount() - 1 : newRow;
				newCol = newCol >= matrix.getColumnCount() ? matrix.getColumnCount() - 1 : newCol;
				matrix.getRowSelectionModel().setSelectionInterval(startRow, newRow);
				matrix.getColumnSelectionModel().setSelectionInterval(startCol, newCol);
				repaint(100);
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void valueChanged(ListSelectionEvent e) {
		repaint(100);
	}
}
