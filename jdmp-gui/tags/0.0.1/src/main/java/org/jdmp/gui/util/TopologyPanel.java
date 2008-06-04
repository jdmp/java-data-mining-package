package org.jdmp.gui.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.UIManager;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.util.AbstractGUIObject;
import org.jdmp.core.util.interfaces.HasAlgorithmsAndVariables;
import org.jdmp.core.variable.Variable;
import org.jdmp.gui.variable.VariableIcon;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.EdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.PickableEdgePaintFunction;
import edu.uci.ics.jung.graph.decorators.ToolTipFunction;
import edu.uci.ics.jung.graph.decorators.VertexIconFunction;
import edu.uci.ics.jung.visualization.PluggableRenderer;

public class TopologyPanel extends JungGraphPanel {
	private static final long serialVersionUID = -6852675670416844022L;

	private static Map<Variable, Icon> variableIcons = new HashMap<Variable, Icon>();

	public TopologyPanel(HasAlgorithmsAndVariables iTopology) {
		TopologyGraphWrapper graph = new TopologyGraphWrapper(iTopology);

		// ((PluggableRenderer) getRenderer()).setVertexShapeFunction(new
		// VertexShapeFunction() {
		//
		// public Shape getShape(Vertex v) {
		// AbstractObject object = (AbstractObject)
		// v.getUserDatum(Data.JDMPObject);
		// return TopologyPanel.getShape(object);
		// }
		// });

		((PluggableRenderer) getRenderer()).setVertexIconFunction(new VertexIconFunction() {

			public Icon getIcon(ArchetypeVertex v) {
				AbstractGUIObject object = (AbstractGUIObject) v.getUserDatum(Data.JDMPObject);
				return TopologyPanel.getIcon(object);
			}
		});

		setToolTipFunction(new ToolTipFunction() {
			public String getToolTipText(Vertex v) {
				return ((AbstractGUIObject) v.getUserDatum(Data.JDMPObject)).getToolTipText();
			}

			public String getToolTipText(Edge e) {
				return null;
			}

			public String getToolTipText(MouseEvent event) {
				return null;
			}
		});

		// pluggableRenderer.getGraphLabelRenderer().setRotateEdgeLabels(false);

		((PluggableRenderer) getRenderer()).setEdgePaintFunction(new EdgePaintFunction() {
			public Paint getDrawPaint(Edge e) {
				if (isShowEdges())
					return Color.black;
				return null;
			}

			public Paint getFillPaint(Edge e) {
				return null;
			}
		});

		// ((PluggableRenderer) getRenderer()).setEdgeFontFunction(new
		// EdgeFontFunction() {
		// public Font getFont(Edge e) {
		// return UIDefaults.DEFAULTFONT;
		// }
		// });

		// ((PluggableRenderer) getRenderer()).setVertexFontFunction(new
		// VertexFontFunction() {
		// public Font getFont(Vertex v) {
		// return UIDefaults.BOLDFONT;
		// }
		// });

		((PluggableRenderer) getRenderer()).setEdgePaintFunction(new PickableEdgePaintFunction(
				((PluggableRenderer) getRenderer()), Color.black, Color.cyan));

		setGraph(graph);

	}

	public static final Icon getIcon(AbstractGUIObject o) {
		if (o instanceof Algorithm) {
			return UIManager.getIcon("Algorithm.icon");
		} else if (o instanceof Variable) {
			Icon i = variableIcons.get(o);
			if (i == null) {
				Variable v = (Variable) o;
				i = new VariableIcon(v);
				variableIcons.put(v, i);
			}
			return i;
		} else {
			return UIManager.getIcon("Image");
		}
	}

	public static final Shape getShape(AbstractGUIObject o) {
		Icon icon = getIcon(o);
		Shape shape = getShape(icon, 30);
		int width = icon.getIconWidth();
		int height = icon.getIconHeight();
		AffineTransform transform = AffineTransform.getTranslateInstance(-width / 2, -height / 2);
		shape = transform.createTransformedShape(shape);
		return shape;
	}

	public static Shape getShape(Icon icon, int max) {
		BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		icon.paintIcon(null, g, 0, 0);
		g.dispose();
		return getShape(bi, max);
	}

	public static Shape getShape(Image image, int max) {
		BufferedImage bi = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return getShape(bi, max);
	}

}
