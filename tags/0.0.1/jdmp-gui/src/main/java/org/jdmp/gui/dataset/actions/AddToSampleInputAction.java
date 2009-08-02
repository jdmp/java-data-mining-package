package org.jdmp.gui.dataset.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.BasicDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;

public class AddToSampleInputAction extends DataSetAction {
	private static final long serialVersionUID = -3223013004052501737L;

	private double value = 2.0;

	public AddToSampleInputAction(JComponent c, BasicDataSet ds) {
		super(c, ds);
		putValue(Action.NAME, "Add to Sample Input...");
		putValue(Action.SHORT_DESCRIPTION, "Add a value to the input of all samples");
	}

	public AddToSampleInputAction(JComponent c, BasicDataSet ds, double value) {
		this(c, ds);
		this.value = value;
	}

	public Object call() {
		try {
			setStatus("Adding to input...");
			setProgress(0);
			double i = 0;
			double total = getDataSet().getSampleCount();
			for (Sample p : getDataSet().getSampleList()) {
				((ClassificationSample) p).getInputVariable().plus(value);
				setProgress(i++ / total);
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error adding to input", e);
			return null;
		} finally {
			setProgress(1);
		}
	}

}
