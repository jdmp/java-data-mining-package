package org.jdmp.core.variable;

import java.awt.Component;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.io.util.FileSelector;
import org.jdmp.matrix.io.util.IntelligentFileWriter;

public abstract class ExportPLT {

	private static final Logger logger = Logger.getLogger(ExportPLT.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		return FileSelector.selectFile(c, "PLT Files", ".plt");
	}

	public static final void save(File file, Matrix m) {

	}

	public static final void save(File pltFile, AbstractVariable v) {
		String EOL = System.getProperty("line.separator");

		try {
			if (!pltFile.getAbsolutePath().toLowerCase().endsWith(".plt"))
				pltFile = new File(pltFile.getAbsolutePath() + ".plt");
			File datFile = new File(pltFile.getAbsolutePath().replace(".plt", ".dat"));
			String datFilename = datFile.getName();
			IntelligentFileWriter out = new IntelligentFileWriter(pltFile);
			out.write("set xlabel \"Value\"" + EOL);
			out.write("set ylabel \"Time\"" + EOL);
			out.write("set title \"" + v.getLabel() + "\"" + EOL);
			out.write("plot \"" + datFilename + "\" using 1:2 title \"Trace 1\" with linespoints");
			for (int i = 1; i < v.getMatrixList().getTraceCount(); i++) {
				out.write(", \"" + datFilename + "\" using 1:" + (2 + i) + " title \"Trace " + (i + 1)
						+ "\" with linespoints");
			}
			out.close();

			out = new IntelligentFileWriter(datFile);
			for (int i = 0; i < v.getMatrixCount(); i++) {
				Matrix m = v.getMatrixList().get(i);
				out.write("3.14\t");
				long valueCount = m.getValueCount();
				for (int j = 0; j < valueCount; j++) {
					out.write("" + m.getDouble(i % m.getRowCount(), i / m.getRowCount()));
					if (j < valueCount - 1)
						out.write("\t");
					else
						out.write(EOL);
				}
			}
			out.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save GnuPlot file", e);
		}
	}

}
