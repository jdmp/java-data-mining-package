package org.jdmp.gui.matrix.plot;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;

import org.jdmp.gui.matrix.MatrixGUIObject;
import org.jdmp.gui.util.CanBeUpdated;
import org.jdmp.gui.util.UIDefaults;
import org.jdmp.gui.util.UpdateListener;
import org.ujmp.core.Matrix;

public class MatrixPlot extends JPanel implements TableCellRenderer, CanBeUpdated,
		ListSelectionListener {
	private static final long serialVersionUID = -3845070497558608841L;

	private EventListenerList listenerList = null;

	private PlotSettings plotSettings = null;

	private XAxis xAxis = null;

	private YAxis yAxis = null;

	private ZeroAxis zeroAxis = null;

	private XGrid xGrid = null;

	private YGrid yGrid = null;

	private Traces traces = null;

	private PlotBackground plotBackground = null;

	private Selection selection = null;

	private RunningAveragePlot runningAveragePlot = null;

	public MatrixPlot(Matrix m) {
		this((MatrixGUIObject) m.getGUIObject(), false);
	}

	public MatrixPlot(MatrixGUIObject m, boolean registerListeners) {
		this();
		plotSettings.setMatrixGUIObject(m);
		if (registerListeners) {
			m.getRowSelectionModel().addListSelectionListener(this);
		}
	}

	public MatrixPlot() {
		this.plotSettings = new PlotSettings();
		this.xAxis = new XAxis(plotSettings);
		this.yAxis = new YAxis(plotSettings);
		this.zeroAxis = new ZeroAxis(plotSettings);
		this.xGrid = new XGrid(plotSettings);
		this.yGrid = new YGrid(plotSettings);
		this.plotBackground = new PlotBackground(plotSettings);
		this.traces = new Traces(plotSettings);
		this.selection = new Selection(plotSettings);
		this.runningAveragePlot = new RunningAveragePlot(plotSettings);
		setPreferredSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(50, 50));
	}

	public void addUpdateListener(UpdateListener l) {
		getListenerList().add(UpdateListener.class, l);
	}

	public void removeUpdateListener(UpdateListener l) {
		getListenerList().remove(UpdateListener.class, l);
	}

	public EventListenerList getListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}

	public void fireUpdated() {
		if (listenerList != null) {
			for (Object o : listenerList.getListenerList()) {
				if (o instanceof UpdateListener)
					((UpdateListener) o).updated();
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		try {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.addRenderingHints(UIDefaults.AALIAS);
			plotSettings.setHeight(getHeight());
			plotSettings.setWidth(getWidth());
			plotSettings.setMinXValue(0.0);
			plotSettings.setMaxXValue(plotSettings.getMatrixGUIObject().getRowCount() - 1);
			plotSettings.setMinYValue(plotSettings.getMatrixGUIObject().getEstimatedMinValue(100));
			plotSettings.setMaxYValue(plotSettings.getMatrixGUIObject().getEstimatedMaxValue(100));

			if (plotSettings.isShowPlotBackGround()) {
				plotBackground.paintComponent(g);
			}

			if (plotSettings.isShowXGrid()) {
				xGrid.paintComponent(g);
			}
			if (plotSettings.isShowYGrid()) {
				yGrid.paintComponent(g);
			}
			if (plotSettings.isShowXAxis()) {
				xAxis.paintComponent(g);
			}
			if (plotSettings.isShowYAxis()) {
				yAxis.paintComponent(g);
			}
			if (plotSettings.isShowZeroAxis()) {
				zeroAxis.paintComponent(g);
			}
			if (plotSettings.isShowSelection()) {
				selection.paintComponent(g);
			}
			if (plotSettings.isShowRunningAverage()) {
				runningAveragePlot.paintComponent(g);
			}

			traces.paintComponent(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
			boolean hasFocus, int row, int column) {
		plotSettings.setMatrixGUIObject((MatrixGUIObject) value);

		if (isSelected) {
			plotSettings.setPlotBackGroundColor(table.getSelectionBackground());
		} else {
			plotSettings.setPlotBackGroundColor(new Color(216, 213, 196));
		}
		return this;
	}

	public PlotSettings getPlotSettings() {
		return plotSettings;
	}

	public void valueChanged(ListSelectionEvent e) {
		fireUpdated();
	}

}
