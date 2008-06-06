package org.jdmp.gui.dataset.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.core.sample.Sample;
import org.jdmp.gui.dataset.DataSetGUIObject;

public class ConvertDesiredOutputToVectorAction extends DataSetAction {
	private static final long serialVersionUID = -5096995308098618259L;

	private int numberOfClasses = 0;

	public ConvertDesiredOutputToVectorAction(JComponent c, DataSetGUIObject ds, int numberOfClasses) {
		super(c, ds);
		this.numberOfClasses = numberOfClasses;
		putValue(Action.NAME, "Convert DesiredOutput to Vector");
		putValue(Action.SHORT_DESCRIPTION, "Convert DesiredOutput to Vector");
	}

	public Object call() {
		try {
			setStatus("Converting desired output to Matrix...");
			setProgress(0);
			getDataSet().getDataSet().getVariable(ClassificationDataSet.RMSE).setSize(
					numberOfClasses, 1);
			double i = 0;
			double total = getDataSet().getDataSet().getSampleCount();
			for (Sample p : getDataSet().getDataSet().getSampleList()) {
				((ClassificationSample) p).getDesiredOutputVariable().convertIntToVector(
						numberOfClasses);
				setProgress(i++ / total);
			}
			return null;
		} catch (Exception e) {
			logger.log(Level.WARNING, "Error converting Desired Output to Matrix", e);
			return null;
		} finally {
			setProgress(1);
		}
	}

}
