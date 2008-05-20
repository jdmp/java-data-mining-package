package org.jdmp.core.variable;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.jdmp.core.matrix.wrappers.MatrixListToMatrixWrapper;
import org.jdmp.core.matrix.wrappers.MatrixToMatrixListWrapper;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.core.util.AbstractEvent.EventType;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.MatrixList;
import org.jdmp.matrix.calculation.Calculation.Ret;
import org.jdmp.matrix.calculation.basic.Divide;
import org.jdmp.matrix.calculation.basic.Plus;
import org.jdmp.matrix.interfaces.HasMatrix;
import org.jdmp.matrix.interfaces.HasMatrixList;
import org.jdmp.matrix.util.IntelligentFileReader;
import org.jdmp.matrix.util.IntelligentFileWriter;

public abstract class Variable extends AbstractGUIObject implements HasMatrix, HasMatrixList {

	protected static transient Logger logger = Logger.getLogger(Variable.class.getName());

	private transient Matrix matrixListMatrix = null;

	private long[] size = new long[] { 0, 0 };

	protected Variable() {
		super();
	}

	public static final Variable fromMatrix(Matrix m) {
		return new DefaultVariable(new MatrixToMatrixListWrapper(m));
	}

	public final void exportToM(File filename) {
		try {
			if (!filename.getAbsolutePath().toLowerCase().endsWith(".m"))
				filename = new File(filename.getAbsolutePath() + ".m");
			IntelligentFileWriter out = new IntelligentFileWriter(filename);
			out.write("plot(1,1);");
			out.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save Matlab file", e);
		}
	}

	public final long[] getSize() {
		return size;
	}

	public final void setSize(long... size) {
		this.size = Coordinates.copyOf(size);
	}

	public final void exportToPLT(File pltFile) {
		ExportPLT.save(pltFile, this);
	}

	public final void loadTSP(InputStream is) {
		String line = null;
		setSize(2, 1);
		int count = 0;
		String[] fields = null;
		List<Matrix> mList = new ArrayList<Matrix>();

		try {
			IntelligentFileReader lr = new IntelligentFileReader(is);
			while ((line = lr.readLine()) != null) {
				if (line.toLowerCase().startsWith("name")) {
					fields = line.split(":");
					setLabel(fields[1].trim());
				} else if (line.toLowerCase().startsWith("comment")) {
					fields = line.split(":");
					setDescription(getDescription() + " " + fields[1].trim());
				} else if (line.toLowerCase().startsWith("type")) {
					;
				} else if (line.toLowerCase().startsWith("dimension")) {
					;
				} else if (line.toLowerCase().startsWith("edge")) {
					;
				} else if (line.toLowerCase().startsWith("node")) {
					;
				} else if (line.toLowerCase().startsWith("eof")) {
					;
				} else {
					fields = line.split(" ");
					if (fields != null && fields.length == 3) {
						double x = Double.parseDouble(fields[1]);
						double y = Double.parseDouble(fields[2]);
						count++;
						Matrix m = MatrixFactory.zeros(2, 1);
						m.setDouble(x, 0, 0);
						m.setDouble(y, 1, 0);
						mList.add(m);
					}
				}
			}

			lr.close();

			// setMatrixList(new DefaultMatrixList(mList.size()));
			for (Matrix m : mList) {
				addMatrix(m);
			}

		} catch (Exception e) {
			logger.log(Level.WARNING, "could not read from stream", e);
		}
	}

	public final void loadTSP(String file) {
		try {
			InputStream is = new FileInputStream(file);
			loadTSP(is);
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not read from file: " + file, e);
		}
	}

	public final double getLength() {
		if (getMatrixList() == null)
			return 0;
		return getMatrixList().getLength();
	}

	public final double getMinTime() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().getMinTime();
		}
	}

	public final double getMaxTime() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().getMaxTime();
		}
	}

	public final double getTime() {
		return getMaxTime();
	}

	public final int getMemorySize() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().getMaxCount();
		}
	}

	public final int getMatrixCount() {
		if (getMatrixList() == null) {
			return 0;
		} else {
			return getMatrixList().size();
		}
	}

	public final Matrix getMatrix() {
		if (getMatrixList() == null) {
			return null;
		} else {
			return getMatrixList().getLast();
		}
	}

	public final Matrix getMatrixCopy() {
		Matrix m = getMatrix();
		if (m != null) {
			return m.clone();
		} else {
			return null;
		}
	}

	public final Matrix getMatrix(int index) {
		if (getMatrixList() == null)
			return null;
		return getMatrixList().get(index);
	}

	public final Matrix getMatrixCopy(int index) {
		Matrix m = getMatrix(index);
		if (m == null) {
			return null;
		} else {
			return m.clone();
		}
	}

	public final String getLongStatus() {
		return getLabel() + " [" + getRowCount() + "x" + getColumnCount() + "]" + " length:" + getLength()
				+ " entries:" + getMatrixCount() + "/" + getMemorySize();
	}

	public final String getShortStatus() {
		return getLabel() + " [" + getRowCount() + "x" + getColumnCount() + "]";
	}

	public final void clearHistory() {
		if (getMatrixList() != null)
			getMatrixList().clear();
	}

	public final double getValue() throws MatrixException {
		if (getMatrix() != null)
			return getMatrix().getEuklideanValue();
		else
			return 0.0;
	}

	public final void setValue(double value) {
		Matrix m = MatrixFactory.fromValue(value);
		addMatrix(m);
	}

	public final void dispose() {
		clear();
	}

	public abstract MatrixList getMatrixList();

	public Matrix getAsMatrix() {
		if (matrixListMatrix == null) {
			matrixListMatrix = new MatrixListToMatrixWrapper(this);
		}
		return matrixListMatrix;
	}

	public final void fireValueChanged(Matrix m) {
		fireVariableEvent(new VariableEvent(this, EventType.UPDATED, getIndexOfMatrix(m), m));
	}

	public final void addMatrix(Matrix m) {
		if (m != null) {
			if (getColumnCount() != m.getColumnCount() || getRowCount() != m.getRowCount()) {
				setSize(m.getRowCount(), m.getColumnCount());
			}
			getMatrixList().add(m);
			fireVariableEvent(new VariableEvent(this, EventType.ADDED, getMatrixList().size() - 1, m));
		}
	}

	public final void removeVariableListener(VariableListener l) {
		getListenerList().add(VariableListener.class, l);
	}

	public final void fireVariableEvent(VariableEvent e) {
		for (Object o : getListenerList().getListenerList()) {
			if (o instanceof VariableListener) {
				((VariableListener) o).valueChanged(e);
			}
		}
	}

	public final void setMatrix(int index, Matrix m) {
		if (m != null) {
			if (getColumnCount() == 0 || getRowCount() == 0) {
				setSize(m.getRowCount(), m.getColumnCount());
			}
			getMatrixList().set(index, m);
			fireVariableEvent(new VariableEvent(this, EventType.UPDATED, index, m));
		}
	}

	public final void addVariableListener(VariableListener l) {
		getListenerList().add(VariableListener.class, l);
	}

	public final int getIndexOfMatrix(Matrix m) {
		if (getMatrixList() != null) {
			return getMatrixList().indexOf(m);
		} else {
			return -1;
		}
	}

	public final void clear() {
		if (getMatrixList() != null) {
			getMatrixList().clear();
		}
	}

	public final void fillGaussian() throws MatrixException {
		Matrix m = MatrixFactory.randn(getRowCount(), getColumnCount());
		addMatrix(m);
	}

	public final void fillUniform() throws MatrixException {
		Matrix m = MatrixFactory.rand(getRowCount(), getColumnCount());
		addMatrix(m);
	}

	public final void divideBy(double v) throws MatrixException {
		new Divide(this.getAsMatrix(), v).calc(Ret.ORIG);
	}

	public final void plus(double v) throws MatrixException {
		for (Matrix m : getMatrixList()) {
			m.calc(new Plus(true, m, v), Ret.ORIG);
		}
	}

	public final void fillWithValue(double v) throws MatrixException {
		Matrix m = MatrixFactory.fill(v, getRowCount(), getColumnCount());
		addMatrix(m);
	}

	public final Icon getIcon() {
		try {
			TableModel dataModel = new AbstractTableModel() {
				private static final long serialVersionUID = 5562866897873790623L;

				public int getColumnCount() {
					return 1;
				}

				public int getRowCount() {
					return 1;
				}

				public Object getValueAt(int row, int col) {
					return getMatrix();
				}
			};
			JTable table = new JTable(dataModel);
			table.getColumnModel().getColumn(0).setWidth(32);
			table.setRowHeight(32);

			int WIDTH = table.getColumnModel().getColumn(0).getWidth() - 1;
			int HEIGHT = table.getRowHeight(0) - 1;

			Class<?> cl = Class.forName("org.jdmp.gui.matrix.MatrixRenderer");
			DefaultTableCellRenderer mr = (DefaultTableCellRenderer) cl.newInstance();
			Component c = mr.getTableCellRendererComponent(table, getMatrix(), false, false, 0, 0);
			BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
			c.paint(bi.getGraphics());
			return new ImageIcon(bi);

		} catch (Exception e) {
			return new ImageIcon("resources/icons/rebuild.png");
		}
	}

	public abstract void setMemorySize(int size);

	public abstract void convertIntToVector(int numberOfClasses) throws MatrixException;

	public final long getRowCount() {
		return getSize()[ROW];
	}

	public final long getColumnCount() {
		return getSize()[COLUMN];
	}

	public final ListSelectionModel getRowSelectionModel() {
		if (getMatrixList() != null) {
			return getMatrixList().getRowSelectionModel();
		} else {
			return null;
		}
	}

	public final ListSelectionModel getColumnSelectionModel() {
		if (getMatrixList() != null) {
			return getMatrixList().getColumnSelectionModel();
		} else {
			return null;
		}
	}

	public final double getEuklideanValue() throws MatrixException {
		Matrix m = getMatrix();
		if (m != null)
			return m.getEuklideanValue();
		else
			return 0;
	}

	public double getMinValue() throws MatrixException {
		return getAsMatrix().getMinValue();
	}

	public double getMaxValue() throws MatrixException {
		return getAsMatrix().getMaxValue();
	}

	public long getIndexOfMaximum() throws MatrixException {
		return getAsMatrix().getCoordinatesOfMaximum()[ROW];
	}

	public long getIndexOfMinimum() throws MatrixException {
		return getAsMatrix().getCoordinatesOfMinimum()[ROW];
	}

	public Matrix getMeanMatrix() throws MatrixException {
		return getAsMatrix().mean(Ret.NEW, ROW, true);
	}

	public Matrix getMaxMatrix() throws MatrixException {
		return getAsMatrix().max(Ret.NEW, ROW);
	}

	public Matrix getMinMatrix() throws MatrixException {
		return getAsMatrix().min(Ret.NEW, ROW);
	}

	public Matrix getVarianceMatrix() throws MatrixException {
		return getAsMatrix().var(Ret.NEW, ROW, true);
	}

	public Matrix getStandardDeviationMatrix() throws MatrixException {
		return getAsMatrix().std(Ret.NEW, ROW, true);
	}

}
