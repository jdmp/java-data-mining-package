package org.jdmp.gui.variable;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdmp.core.variable.Variable;
import org.jdmp.gui.actions.ExportAction;
import org.jdmp.gui.util.CanBeRepainted;
import org.jdmp.gui.util.CanRenderGraph;
import org.jdmp.gui.util.ColorUtil;
import org.jdmp.gui.util.GraphicsExecutor;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.io.ExportJPEG;
import org.jdmp.matrix.io.ExportPDF;
import org.jdmp.matrix.io.ExportPNG;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.Drawable;
import org.jfree.ui.ExtensionFileFilter;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class VariableChartPanel extends ChartPanel implements CanBeRepainted, CanRenderGraph, DatasetChangeListener,
		ComponentListener, PlotChangeListener, ListSelectionListener {
	private static final long serialVersionUID = -6852675670416844022L;

	protected static final Logger logger = Logger.getLogger(VariableChartPanel.class.getName());

	public static final int MAXPOINTCOUNT = 100000;

	private BufferedImage bufferedImage = null;

	private boolean xyPlot = false;

	private boolean showLines = true;

	private boolean showMarkers = false;

	private boolean showLabels = false;

	private boolean showValues = false;

	private boolean showOnlyEuklideanValues = false;

	private Variable variable = null;

	private XYAnnotation circle = null;

	private IntervalMarker rangeSelection = new IntervalMarker(0, 0);

	private ValueMarker zeroMarker = new ValueMarker(0.0);

	private XYPlot plot = null;

	private XYSeriesCollectionWrapper dataset = null;

	public LegendTitle legend = null;

	public VariableChartPanel(Variable v, boolean showBorder) {
		super(null, false);
		this.variable = v;
		v.getRowSelectionModel().addListSelectionListener(this);

		addComponentListener(this);

		updatePopupMenu();

		dataset = new XYSeriesCollectionWrapper(v);
		dataset.addChangeListener(this);

		if (showBorder) {
			setBorder(BorderFactory.createTitledBorder("Variable Chart"));
		}

		this.setPreferredSize(new Dimension(800, 600));

		setMaximumDrawWidth(2000);
		setMaximumDrawHeight(2000);

		final JFreeChart chart = ChartFactory.createXYLineChart(null, null, null, dataset, PlotOrientation.VERTICAL,
				true, true, false);

		chart.setBackgroundPaint(UIManager.getColor("Panel.background"));
		plot = chart.getXYPlot();
		plot.setBackgroundPaint(new Color(206, 203, 186));
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setDomainCrosshairVisible(false);
		plot.setRangeCrosshairVisible(false);
		plot.addChangeListener(this);

		zeroMarker.setPaint(new Color(0, 0, 0, 128));
		plot.addRangeMarker(zeroMarker, Layer.FOREGROUND);

		plot.addRangeMarker(dataset.getMeanMarker(0));
		plot.addRangeMarker(dataset.getStandardDeviationMarker(0));
		plot.addRangeMarker(dataset.getMinMaxMarker(0));

		rangeSelection.setPaint(new Color(200, 200, 235, 128));
		rangeSelection.setLabelPaint(new Color(0, 0, 0));
		rangeSelection.setLabelAnchor(RectangleAnchor.TOP);
		rangeSelection.setLabelTextAnchor(TextAnchor.TOP_CENTER);
		rangeSelection.setOutlinePaint(new Color(50, 50, 235));
		plot.addDomainMarker(rangeSelection, Layer.FOREGROUND);

		legend = chart.getLegend();
		chart.clearSubtitles();

		this.setChart(chart);
		this.setMouseZoomable(false);
	}

	public void paintComponent(Graphics g) {
		if (ui != null) {
			Graphics scratchGraphics = (g == null) ? null : g.create();
			try {
				ui.update(scratchGraphics, this);
			} finally {
				scratchGraphics.dispose();
			}
		}

		if (bufferedImage != null) {
			g.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), null);
		} else {
			g.setColor(Color.GRAY);
			g.drawLine(0, 0, getWidth(), getHeight());
			g.drawLine(getWidth(), 0, 0, getHeight());
			GraphicsExecutor.scheduleUpdate(this);
		}
	}

	public void repaintUI() {
		// plot.clearRangeMarkers();
		// plot.clearDomainMarkers();

		// if (circle != null && plot.getAnnotations().contains(circle)) {
		// plot.removeAnnotation(circle);
		// }
		//
		// if (minSelectedIndex == maxSelectedIndex) {
		// Matrix m = variable.getMatrix(minSelectedIndex);
		//
		// if (isXyPlot()) {
		// CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f));
		// circle = new XYDrawableAnnotation(m.getValue(0), m.getValue(1), 11,
		// 11, cd);
		// plot.addAnnotation(circle);
		// } else {
		// ValueMarker vm = null;
		// if (variable.getMinTime() == 0.0 && variable.getMaxTime() == 0.0) {
		// vm = new ValueMarker(minSelectedIndex);
		// } else {
		// vm = new ValueMarker(m.getTime());
		// }
		// vm.setPaint(Color.red);
		// plot.addDomainMarker(vm);
		// }
		// } else {
		//
		// }

		BufferedImage tempBufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = tempBufferedImage.getGraphics();
		super.paintComponent(g);
		bufferedImage = tempBufferedImage;
	}

	public void doSaveAs() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		ExtensionFileFilter filter = new ExtensionFileFilter(localizationResources.getString("PNG_Image_Files"), ".png");
		fileChooser.addChoosableFileFilter(filter);

		int option = fileChooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			String filename = fileChooser.getSelectedFile().getPath();
			if (isEnforceFileExtensions()) {
				if (!filename.endsWith(".png")) {
					filename = filename + ".png";
				}
			}
			getChart().setBackgroundPaint(Color.white);
			ChartUtilities.saveChartAsPNG(new File(filename), getChart(), 1024, 600);
			getChart().setBackgroundPaint(UIManager.getColor("Panel.background"));
		}
	}

	protected void updatePopupMenu() {
		JPopupMenu menu = getPopupMenu();
		menu.add(new JSeparator());

		JCheckBoxMenuItem xyplotitem = new JCheckBoxMenuItem(new XYPlotAction(this));
		xyplotitem.setState(this.isXyPlot());
		menu.add(xyplotitem);
		JCheckBoxMenuItem showLinesItem = new JCheckBoxMenuItem(new ShowLinesAction(this));
		showLinesItem.setState(!this.isShowLines());
		menu.add(showLinesItem);
		JCheckBoxMenuItem showMarkersItem = new JCheckBoxMenuItem(new ShowMarkersAction(this));
		showMarkersItem.setState(this.isShowMarkers());
		menu.add(showMarkersItem);
		JCheckBoxMenuItem showLabelsItem = new JCheckBoxMenuItem(new ShowLabelsAction(this));
		showLabelsItem.setState(!this.isShowLabels());
		menu.add(showLabelsItem);
		JCheckBoxMenuItem showValuesItem = new JCheckBoxMenuItem(new ShowValuesAction(this));
		showValuesItem.setState(this.isShowValues());
		menu.add(showValuesItem);
		JCheckBoxMenuItem showTitleItem = new JCheckBoxMenuItem(new ShowTitleAction(this));
		showTitleItem.setState(this.isShowTitle());
		menu.add(showTitleItem);
		JCheckBoxMenuItem showLegendItem = new JCheckBoxMenuItem(new ShowLegendAction(this));
		showLegendItem.setState(this.isShowLegend());
		menu.add(showLegendItem);
		JCheckBoxMenuItem showOnlyEuklideanValuesItem = new JCheckBoxMenuItem(new ShowOnlyEuklideanValuesAction(this));
		showOnlyEuklideanValuesItem.setState(this.isShowOnlyEuklideanValues());
		menu.add(showOnlyEuklideanValuesItem);

		menu.add(new JSeparator());
		menu.add(new ExportAction(this, this.getVariable()));
	}

	private void clearChart() {
		try {
			// plot.clearAnnotations();
		} catch (Exception e) {
			logger.log(Level.WARNING, "error clearing annotations");
		}
	}

	private void showTitle() {
		try {
			if (isShowTitle()) {
				getChart().setTitle(variable.getLabel());
			} else {
				getChart().setTitle((String) null);
			}
		} catch (Exception e) {
		}
	}

	private void showLegend() {
		try {
			if (isShowLegend()) {
				getChart().removeLegend();
				getChart().addLegend(legend);
			} else {
				getChart().removeLegend();
			}
		} catch (Exception e) {
		}
	}

	private void configureRenderer() {
		XYItemRenderer renderer = plot.getRenderer();
		if (renderer instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer rr = (XYLineAndShapeRenderer) renderer;
			rr.setShapesFilled(true);
			rr.setItemLabelsVisible(true);
			for (int i = 0; i < ColorUtil.TRACECOLORS.length; i++) {
				rr.setSeriesPaint(i, ColorUtil.TRACECOLORS[i]);
				rr.setSeriesLinesVisible(i, isShowLines());
				rr.setSeriesShapesVisible(i, isShowMarkers());
			}
		}
	}

	private void doXYPlot() throws MatrixException {
		String text = null;

		XYSeries ts = new XYSeries("TimeSeries", false);

		int stepSize = (int) Math.ceil((double) variable.getMatrixList().size() / (double) MAXPOINTCOUNT);

		for (int j = 0; j < variable.getMatrixList().size(); j += stepSize) {
			text = null;
			Matrix m = variable.getMatrixList().get(j);
			double x = m.getDouble(0, 0);
			double y = m.getDouble(0, 1);
			ts.add(x, y);

			if (isShowLabels() && isShowValues()) {
				if (!"null".equals(m.getLabel()))
					text = m.getLabel() + " (" + x + "," + y + ")";
				else
					text = "(" + x + "," + y + ")";
			} else if (!isShowLabels() && isShowValues()) {
				text = "(" + x + "," + y + ")";
			} else if (isShowLabels() && !isShowValues()) {
				if (!"null".equals(m.getLabel()))
					text = m.getLabel();
			}
			if (text != null) {
				XYTextAnnotation anno = new XYTextAnnotation(text, x, y);
				anno.setTextAnchor(TextAnchor.BOTTOM_CENTER);
				if (text.length() > 10)
					anno.setRotationAngle(-0.1);
				plot.addAnnotation(anno);
			}
		}
	}

	private void doNormalPlot() throws MatrixException {
		String text = null;

		for (int i = 0; i < ColorUtil.TRACECOLORS.length && i < variable.getMatrixList().getTraceCount(); i++) {
			XYSeries ts = new XYSeriesWrapper(variable, i);

			int stepSize = (int) Math.ceil((double) variable.getMatrixList().size() / (double) MAXPOINTCOUNT);

			for (int j = 0; j < variable.getMatrixList().size(); j += stepSize) {
				text = null;
				Matrix m = variable.getMatrixList().get(j);
				double x = j;
				double y = m.getDouble(i % m.getRowCount(), i / m.getRowCount());
				// ts.addOrUpdate(x, y);

				if (isShowLabels() && isShowValues()) {
					if (!"null".equals(m.getLabel()))
						text = m.getLabel() + " (" + x + "," + y + ")";
					else
						text = "(" + x + "," + y + ")";
				} else if (!isShowLabels() && isShowValues()) {
					text = "(" + x + "," + y + ")";
				} else if (isShowLabels() && !isShowValues()) {
					if (!"null".equals(m.getLabel()))
						text = m.getLabel();
				}
				if (text != null) {
					XYTextAnnotation anno = new XYTextAnnotation(text, x, y);
					anno.setTextAnchor(TextAnchor.BOTTOM_CENTER);
					if (text.length() > 10)
						anno.setRotationAngle(-0.1);
					plot.addAnnotation(anno);
				}
			}
		}
	}

	private void doEuklideanPlot() throws MatrixException {
		String text = null;

		XYSeries ts = new XYSeries("Euklidean Values");

		int stepSize = (int) Math.ceil((double) variable.getMatrixList().size() / (double) MAXPOINTCOUNT);

		for (int j = 0; j < variable.getMatrixList().size(); j += stepSize) {
			text = null;
			Matrix m = variable.getMatrixList().get(j);
			ts.addOrUpdate(0.0, m.getEuklideanValue());

			if (isShowLabels() && isShowValues()) {
				if (!"null".equals(m.getLabel()))
					text = m.getLabel() + " (" + 0.0 + "," + m.getEuklideanValue() + ")";
				else
					text = "(" + 0.0 + "," + m.getEuklideanValue() + ")";
			} else if (!isShowLabels() && isShowValues()) {
				text = "(" + 0.0 + "," + m.getEuklideanValue() + ")";
			} else if (isShowLabels() && !isShowValues()) {
				if (!"null".equals(m.getLabel()))
					text = m.getLabel();
			}
			if (text != null) {
				XYTextAnnotation anno = new XYTextAnnotation(text, 0.0, m.getEuklideanValue());
				anno.setTextAnchor(TextAnchor.BOTTOM_CENTER);
				if (text.length() > 10)
					anno.setRotationAngle(-0.1);
				plot.addAnnotation(anno);
			}
		}
	}

	public void exportToPDF() {
		exportToPDF(ExportPDF.selectFile(this));
	}

	public void exportToPDF(String file) {
		exportToPDF(new File(file));
	}

	public void exportToPDF(File file) {
		getChart().setBackgroundPaint(Color.white);
		ExportPDF.save(file, this);
		getChart().setBackgroundPaint(UIManager.getColor("Panel.background"));
	}

	public Variable getVariable() {
		return variable;
	}

	public boolean isXyPlot() {
		return xyPlot;
	}

	public void setXyPlot(boolean xyPlot) {
		this.xyPlot = xyPlot;
	}

	public boolean isShowLines() {
		return showLines;
	}

	public void setShowLines(boolean showLines) {
		this.showLines = showLines;
	}

	public boolean isShowMarkers() {
		return showMarkers;
	}

	public void setShowMarkers(boolean showMarkers) {
		this.showMarkers = showMarkers;
	}

	public boolean isShowLegend() {
		return getChart() != null && getChart().getLegend() == null;
	}

	public void setShowLegend(boolean showLegend) {
		getChart().clearSubtitles();
		if (showLegend) {
			getChart().addLegend(legend);
		}
	}

	public boolean isShowTitle() {
		return getChart() != null && getChart().getTitle() == null;
	}

	public void setShowTitle(boolean showTitle) {
		if (showTitle) {
			getChart().setTitle(variable.getLabel());
		} else {
			getChart().setTitle((TextTitle) null);
		}
	}

	private void setSelectedIndex(int index) throws MatrixException {
		if (index == -1) {
			return;
		}

		Matrix m = variable.getMatrix(index);

		plot.clearRangeMarkers();
		plot.clearDomainMarkers();

		if (circle != null && plot.getAnnotations().contains(circle))
			plot.removeAnnotation(circle);

		if (isXyPlot()) {
			CircleDrawer cd = new CircleDrawer(Color.red, new BasicStroke(1.0f));
			circle = new XYDrawableAnnotation(m.getDouble(0, 0), m.getDouble(1, 0), 11, 11, cd);
			plot.addAnnotation(circle);
		} else {
			ValueMarker vm = null;
			if (variable.getMinTime() == 0.0 && variable.getMaxTime() == 0.0) {
				vm = new ValueMarker(index);
			} else {
				vm = new ValueMarker(0.0);
			}
			vm.setPaint(Color.red);
			plot.addDomainMarker(vm);
		}

		IntervalMarker target = new IntervalMarker(-1.0, 1.0);
		target.setLabel("mean");
		target.setLabelFont(new Font("Arial", Font.BOLD, 11));
		target.setLabelAnchor(RectangleAnchor.LEFT);
		target.setLabelTextAnchor(TextAnchor.CENTER_LEFT);
		target.setPaint(new Color(222, 222, 255, 128));
		plot.addRangeMarker(target, Layer.FOREGROUND);
	}

	public boolean isShowLabels() {
		return showLabels;
	}

	public void setShowLabels(boolean showLabels) {
		this.showLabels = showLabels;
	}

	public boolean isShowValues() {
		return showValues;
	}

	public void setShowValues(boolean showValues) {
		this.showValues = showValues;
	}

	public boolean isShowOnlyEuklideanValues() {
		return showOnlyEuklideanValues;
	}

	public void setShowOnlyEuklideanValues(boolean showOnlyEuklideanValue) {
		this.showOnlyEuklideanValues = showOnlyEuklideanValue;
	}

	public void datasetChanged(DatasetChangeEvent event) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void componentShown(ComponentEvent e) {
	}

	public void plotChanged(PlotChangeEvent event) {
		dataset.setRange(plot.getDomainAxis(0).getRange());
		GraphicsExecutor.scheduleUpdate(this);
	}

	public void exportToPNG(File file) {
		ExportPNG.save(file, this);
	}

	public void exportToJPEG(File file) {
		ExportJPEG.save(file, this);
	}

	public void renderGraph(Graphics2D g2) {
		Rectangle2D rectangle2D = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
		getChart().draw(g2, rectangle2D);
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
			int min = variable.getRowSelectionModel().getMinSelectionIndex();
			int max = variable.getRowSelectionModel().getMaxSelectionIndex();
			rangeSelection.setStartValue(min);
			rangeSelection.setEndValue(max);
			if (min == max) {
				rangeSelection.setLabel("[" + min + "]");
			} else {
				rangeSelection.setLabel("[" + min + ":" + max + "]");
			}
			GraphicsExecutor.scheduleUpdate(this);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		super.mouseDragged(e);
		// System.out.println("dragged");
	}

	@Override
	public void mousePressed(MouseEvent event) {
		super.mousePressed(event);

		// int x = event.getX();
		// Insets insets = getInsets();
		// System.out.println("event.x=" + x);
		// System.out.println("insets=" + insets);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		// System.out.println("released");
	}

}

class CircleDrawer implements Drawable {

	private Stroke stroke = null;

	private Color color = null;

	public CircleDrawer(Color color, Stroke stroke) {
		this.stroke = stroke;
		this.color = color;
	}

	public void draw(Graphics2D g2, Rectangle2D area) {
		Ellipse2D.Double circle = new Ellipse2D.Double(area.getX(), area.getY(), area.getWidth(), area.getHeight());
		g2.setStroke(stroke);
		g2.setColor(color);
		g2.draw(circle);
	}

}

class XYPlotAction extends AbstractAction {
	private static final long serialVersionUID = -2406895684883268116L;

	VariableChartPanel panel = null;

	public XYPlotAction(VariableChartPanel panel) {
		super("XYPlot", UIManager.getIcon("JDMP.icon.Xyplot"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setXyPlot(!panel.isXyPlot());
	}
}

class ShowLabelsAction extends AbstractAction {
	private static final long serialVersionUID = -5842367578576999634L;

	VariableChartPanel panel = null;

	public ShowLabelsAction(VariableChartPanel panel) {
		super("Show Labels", UIManager.getIcon("JDMP.icon.Labels"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowLabels(!panel.isShowLabels());
	}
}

class ShowValuesAction extends AbstractAction {
	private static final long serialVersionUID = -8552342514752797881L;

	VariableChartPanel panel = null;

	public ShowValuesAction(VariableChartPanel panel) {
		super("Show Values", UIManager.getIcon("JDMP.icon.values"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowValues(!panel.isShowValues());
	}
}

class ShowOnlyEuklideanValuesAction extends AbstractAction {
	private static final long serialVersionUID = 8216215021207311313L;

	VariableChartPanel panel = null;

	public ShowOnlyEuklideanValuesAction(VariableChartPanel panel) {
		super("Show Only Euklidean Values", UIManager.getIcon("JDMP.icon.euklid"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowOnlyEuklideanValues(!panel.isShowOnlyEuklideanValues());
	}
}

class ShowLinesAction extends AbstractAction {
	private static final long serialVersionUID = -270625078448131949L;

	VariableChartPanel panel = null;

	public ShowLinesAction(VariableChartPanel panel) {
		super("Show Lines", UIManager.getIcon("JDMP.icon.lines"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowLines(!panel.isShowLines());
	}
}

class ShowMarkersAction extends AbstractAction {
	private static final long serialVersionUID = 2340964625412717395L;

	VariableChartPanel panel = null;

	public ShowMarkersAction(VariableChartPanel panel) {
		super("Show Markers", UIManager.getIcon("JDMP.icon.markers"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowMarkers(!panel.isShowMarkers());
	}
}

class ShowTitleAction extends AbstractAction {
	private static final long serialVersionUID = -5246832573098018052L;

	VariableChartPanel panel = null;

	public ShowTitleAction(VariableChartPanel panel) {
		super("Show Title", UIManager.getIcon("JDMP.icon.title"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowTitle(!panel.isShowTitle());
	}
}

class ShowLegendAction extends AbstractAction {
	private static final long serialVersionUID = 1725646165679773343L;

	VariableChartPanel panel = null;

	public ShowLegendAction(VariableChartPanel panel) {
		super("Show Legend", UIManager.getIcon("JDMP.icon.legend"));
		this.panel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		panel.setShowLegend(!panel.isShowLegend());
	}
}
