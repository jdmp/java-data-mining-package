package org.jdmp.gui.matrix;

import java.util.HashMap;
import java.util.Map;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.gui.util.JungGraphPanel.Data;
import org.jdmp.matrix.Coordinates;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;

import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.UserData;

public class MatrixGraphWrapper extends DirectedSparseGraph implements TableModelListener {

	public static final int MAXEDGES = 256000;

	public static final int MAXVERTICES = 128000;

	public static final double MINEDGEVALUE = 0.5;

	private MatrixGUIObject matrix = null;

	private Map<Integer, Vertex> vertices = new HashMap<Integer, Vertex>();

	public MatrixGraphWrapper(Matrix m) throws MatrixException {
		this((MatrixGUIObject) m.getGUIObject());
	}

	public MatrixGraphWrapper(MatrixGUIObject m) throws MatrixException {
		super();
		this.matrix = m;

		if (m != null) {

			// limit the number of vertices
			int stepsize = matrix.getColumnCount() / MAXVERTICES + 1;

			for (int i = 0; i < matrix.getColumnCount(); i += stepsize) {
				Vertex v = new DirectedSparseVertex();
				v.setUserDatum(Data.Column, i, UserData.SHARED);
				if (matrix.getColumnName(i) != null) {
					v.setUserDatum(Data.Label, matrix.getColumnName(i), UserData.SHARED);
				} else
                  v.setUserDatum(Data.Label, ""+i, UserData.SHARED);
				  
				addVertex(v);
				vertices.put(i, v);

				if (i % 100 == 0) {
					System.out.println(i);
				}
			}

			System.out.println("edges");

			int i = 0;
			for (long[] rc : matrix.coordinates()) {
				if (i++ % 100 == 0) {
					System.out.println(i);
				}
				double value = matrix.getDoubleValueAt(rc);
				if (Math.abs(value) >= MINEDGEVALUE) {
					Vertex v1 = getVertex((int) rc[Coordinates.ROW]);
					Vertex v2 = getVertex((int) rc[Coordinates.COLUMN]);
					if (v1 != null && v2 != null) {
						if (numEdges() < MAXEDGES) {
							Edge e = new DirectedSparseEdge(v1, v2);
							e.addUserDatum(Data.Label, matrix.getDoubleValueAt(rc), UserData.SHARED);
							e.addUserDatum(Data.RowColumn, rc, UserData.SHARED);
							e.addUserDatum(Data.Value, matrix.getDoubleValueAt(rc), UserData.SHARED);
							addEdge(e);
						}
					}
				}
			}

			m.addTableModelListener(this);

		}
	}

	public void tableChanged() throws MatrixException {
		if (true) {
			return;
		}
		for (long[] rc : matrix.coordinates()) {

			Vertex v1 = getVertex((int) rc[Coordinates.ROW]);
			Vertex v2 = getVertex((int) rc[Coordinates.COLUMN]);

			if (v1 != null && v2 != null) {

				Edge e = v1.findEdge(v2);
				double newValue = matrix.getDoubleValueAt(rc);

				if (e == null && Math.abs(newValue) >= MINEDGEVALUE) {
					if (numEdges() < MAXEDGES) {
						e = new DirectedSparseEdge(v1, v2);
						e.addUserDatum(Data.Label, matrix.getDoubleValueAt(rc), UserData.SHARED);
						e.addUserDatum(Data.RowColumn, rc, UserData.SHARED);
						e.addUserDatum(Data.Value, matrix.getDoubleValueAt(rc), UserData.SHARED);
						addEdge(e);
					}
				} else if (e != null && Math.abs(newValue) >= MINEDGEVALUE) {
					double oldValue = (Double) e.getUserDatum(Data.Value);
					if (oldValue != newValue) {
						e.setUserDatum(Data.Value, newValue, UserData.SHARED);
					}
				} else if (e != null && Math.abs(newValue) < MINEDGEVALUE) {
					removeEdge(e);
				}
			}
		}
		mGraphListenerHandler.handleAdd((Edge) null);
	}

	private Vertex getVertex(int index) {
		return vertices.get(index);
		// if (v == null) {
		// for (Object o : getVertices()) {
		// v = (Vertex) o;
		// int row = (Integer) v.getUserDatum(Data.Column);
		// if (row == index) {
		// vertices.put(index, v);
		// return v;
		// }
		// }
		// }
		// return null;
	}

	public void tableChanged(TableModelEvent e) {
		try {
			tableChanged();
		} catch (MatrixException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		matrix.removeTableModelListener(this);
	}

}
