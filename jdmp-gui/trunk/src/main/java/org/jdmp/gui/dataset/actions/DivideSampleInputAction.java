package org.jdmp.gui.dataset.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.BasicDataSet;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.WeightedSample;

public class DivideSampleInputAction extends DataSetAction {
	private static final long serialVersionUID = 1055286178211140515L;

	private double value = 2.0;

	public DivideSampleInputAction(JComponent c, BasicDataSet ds) {
		super(c, ds);
		putValue(Action.NAME, "Divide Sample Input...");
		putValue(Action.SHORT_DESCRIPTION, "Divide the input of all samples");
	}

	public DivideSampleInputAction(JComponent c, BasicDataSet ds, double value) {
		this(c, ds);
		this.value = value;
	}

	public Object call() {
		try {
			setStatus("Dividing input...");
			setProgress(0);
			double i = 0;
			double total = getDataSet().getSampleCount();
			for (Sample p : getDataSet().getSampleList()) {
				((WeightedSample) p).getInputVariable().divideBy(value);
				setProgress(i++ / total);
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error dividing input", e);
			return null;
		} finally {
			setProgress(1);
		}
	}

}
