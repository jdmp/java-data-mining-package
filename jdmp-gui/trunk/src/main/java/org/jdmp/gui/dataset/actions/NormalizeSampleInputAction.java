package org.jdmp.gui.dataset.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.WeightedSample;
import org.jdmp.gui.dataset.DataSetGUIObject;
import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixFactory;

public class NormalizeSampleInputAction extends DataSetAction {
	private static final long serialVersionUID = 1630916629005253538L;

	public NormalizeSampleInputAction(JComponent c, DataSetGUIObject ds) {
		super(c, ds);
		putValue(Action.NAME, "Normalize Input");
		putValue(Action.SHORT_DESCRIPTION, "Normalizes the input across all samples");
	}

	public Object call() {
		try {
			System.out.println("norm");
			setStatus("Normalizing input...");
			setProgress(0);
			Matrix input = ((WeightedSample) getDataSet().getDataSet().getSample(0))
					.getInputMatrix();
			int count = getDataSet().getDataSet().getSampleCount();
			Matrix sum = MatrixFactory.zeros(input.getRowCount(), input.getColumnCount());
			double i = 0;
			double total = getDataSet().getDataSet().getSampleCount() * 2;
			for (Sample p : getDataSet().getDataSet().getSampleList()) {
				sum = sum.plus(((WeightedSample) p).getInputMatrix());
				setProgress(i++ / total);
			}
			Matrix mean = sum.divide(count);
			for (Sample p : getDataSet().getDataSet().getSampleList()) {
				// p.getInputVariable().subtract(mean);
				setProgress(i++ / total);
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error normalizing input", e);
			return null;
		} finally {
			setProgress(1);
		}
	}

}
