package org.jdmp.gui.matrix;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import org.jdmp.core.matrix.MatrixGUIObject;
import org.jdmp.gui.util.GraphicsExecutor;
import org.jdmp.gui.util.JungGraphPanel;
import org.jdmp.matrix.exceptions.MatrixException;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.EdgeArrowFunction;
import edu.uci.ics.jung.graph.decorators.EdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.EdgeStrokeFunction;
import edu.uci.ics.jung.graph.decorators.VertexPaintFunction;
import edu.uci.ics.jung.graph.decorators.VertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.visualization.ArrowFactory;
import edu.uci.ics.jung.visualization.PluggableRenderer;

public class MatrixGraphPanel extends JungGraphPanel {
	private static final long serialVersionUID = 5267278346111012739L;

	private MatrixGUIObject matrix = null;

	public MatrixGraphPanel(MatrixGUIObject matrix) throws MatrixException {
		this();
		setMatrix(matrix);
	}

	public MatrixGraphPanel() {
		setShowEdgeLabels(false);

		((PluggableRenderer) getRenderer()).setEdgeShapeFunction(new EdgeShape.Line());

		((PluggableRenderer) getRenderer()).setEdgePaintFunction(new EdgePaintFunction() {
			public Paint getDrawPaint(Edge e) {
				if (isShowEdges()) {
					// Double v = (Double) e.getUserDatum(Data.Value);
					return new Color(128, 128, 128, 80);
				}
				return null;
			}

			public Paint getFillPaint(Edge e) {
				return null;
			}
		});

		((PluggableRenderer) getRenderer()).setVertexStringer(new VertexStringer() {
			public String getLabel(ArchetypeVertex v) {
				//if (v.degree() > 29 && v.getUserDatum(Data.Label) != null) {
				//	System.out.println(v.getUserDatum(Data.Label));
					return "" + v.getUserDatum(Data.Label);
				//}
				//return null;
			}
		});

		((PluggableRenderer) getRenderer()).setVertexPaintFunction(new VertexPaintFunction() {
			public Paint getFillPaint(Vertex v) {
				// boolean inhibitory = (Boolean)
				// v.getUserDatum(Data.Inhibitory);
				// boolean excitatory = (Boolean)
				// v.getUserDatum(Data.Excitatory);
				// if (inhibitory)
				// return new Color(255, 64, 64);
				// if (excitatory)
				// return new Color(64, 255, 64);
				if (v.degree() == 0) {
					return new Color(0, 200, 0, 20);
				} else if (v.degree() > 29) {
					return new Color(200, 0, 0, 80);
				} else {
					return new Color(0, 200, 0, 80);
				}
			}

			public Paint getDrawPaint(Vertex v) {
				return null;
			}
		});

		((PluggableRenderer) getRenderer()).setEdgeStrokeFunction(new EdgeStrokeFunction() {
			public Stroke getStroke(Edge e) {
				// double value = (Double) e.getUserDatum(Data.Value);
				// double absValue = Math.abs(value);
				// if (absValue < 0.5)
				// return new BasicStroke(0.5f);
				// if (absValue < 1.0)
				// return new BasicStroke(1.0f);
				// if (absValue < 2.0)
				// return new BasicStroke(2);
				// if (absValue < 3.0)
				// return new BasicStroke(3);
				return new BasicStroke(0.5f);
			}
		});

		((PluggableRenderer) getRenderer()).setEdgeArrowFunction(new EdgeArrowFunction() {
			public Shape getArrow(Edge e) {
				// double value = (Double) e.getUserDatum(Data.Value);
				// if (value > 0)
				// return ArrowFactory.getNotchedArrow(12, 12, 5);
				return ArrowFactory.getNotchedArrow(6, 6, 2);
				// else
				// return new Ellipse2D.Double(-5, -5, 12, 12);
			}
		});

		((PluggableRenderer) getRenderer()).setVertexShapeFunction(new VertexShapeFunction() {
			public Shape getShape(Vertex v) {
				// if (v.getUserDatum(Data.Label).equals("test1"))
				// return new Rectangle2D.Float(-25, -15, 50, 30);
				// else
				int size = 2 + v.degree() * 2;
				return new Ellipse2D.Float(-size / 2, -size / 2, size, size);
			}
		});

	}

	public void setMatrix(MatrixGUIObject matrix) throws MatrixException {
		setGraph(new MatrixGraphWrapper(matrix));
		GraphicsExecutor.scheduleUpdate(this);
	}

	public MatrixGUIObject getMatrix() {
		return matrix;
	}

}
