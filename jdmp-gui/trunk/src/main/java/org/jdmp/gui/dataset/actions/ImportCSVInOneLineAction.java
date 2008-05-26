package org.jdmp.gui.dataset.actions;

import java.util.logging.Level;

import javax.swing.Action;
import javax.swing.JComponent;

import org.jdmp.core.dataset.ClassificationDataSet;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.sample.ClassificationSample;
import org.jdmp.matrix.util.IntelligentFileReader;

public class ImportCSVInOneLineAction extends DataSetAction {
	private static final long serialVersionUID = 2560365188173770556L;

	private String filename;

	private int startPos;

	private int featureCount;

	private int maxSampleCount;

	private int classPos;

	private int classLabelPos;

	public ImportCSVInOneLineAction(JComponent c, ClassificationDataSet ds, String filename, int startPos, int count,
			int classPos, int classLabelPos, int maxSampleCount) {
		this(c, ds);
		this.filename = filename;
		this.startPos = startPos;
		this.featureCount = count;
		this.classPos = classPos;
		this.classLabelPos = classLabelPos;
		this.maxSampleCount = maxSampleCount;
	}

	public ImportCSVInOneLineAction(JComponent c, DataSet ds) {
		super(c, ds);
		putValue(Action.NAME, "Import from CSV...");
		putValue(Action.SHORT_DESCRIPTION, "Import from CSV file");
	}

	public Object call() {
		setStatus("Counting samples...");
		try {
			IntelligentFileReader lr = new IntelligentFileReader(filename);
			String line = null;
			int totalLineCount = 0;

			while ((line = lr.readLine()) != null) {
			}

			totalLineCount = Math.min(lr.getLineNumber(), maxSampleCount);
			setProgress(0);
			lr = new IntelligentFileReader(filename);

			for (int i = 0; i < maxSampleCount && (line = lr.readLine()) != null; i++) {
				ClassificationSample p = new ClassificationSample();
				p.createFromLine(line, startPos, featureCount, classPos, classLabelPos);
				((ClassificationDataSet) getDataSet()).addSample(p);
				if (i % 100 == 0) {
					setProgress((double) lr.getLineNumber() / (double) totalLineCount);
					setStatus(lr.getLineNumber() + " of " + totalLineCount + " Samples loaded");
				}
			}
			setStatus("" + lr.getLineNumber() + " Samples loaded.");
			return null;
		} catch (Exception e) {
			setStatus("Could not load Samples");
			logger.log(Level.WARNING, "could not load file: " + filename, e);
			return null;
		} finally {
			setProgress(1);
		}
	}

}
