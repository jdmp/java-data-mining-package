package org.jdmp.matrix.io;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public abstract class ExportJPEG {

	private static final Logger logger = Logger.getLogger(ExportJPEG.class.getName());

	public static final File selectFile() {
		return selectFile(null);
	}

	public static final File selectFile(Component c) {
		return FileSelector.selectFile(c, "JPEG Files", ".jpg");
	}

	public static final void save(String filename, Component c) {
		save(new File(filename), c, c.getWidth());
	}

	public static final void save(String filename, Component c, int width) {
		save(new File(filename), c, width);
	}

	public static final void save(File file, Component c) {
		save(file, c, c.getWidth());
	}

	public static final void save(File file, Component c, int width) {
		if (file == null) {
			logger.log(Level.WARNING, "no file selected");
			return;
		}
		if (c == null) {
			logger.log(Level.WARNING, "no component provided");
			return;
		}
		double factor = width / c.getWidth();
		BufferedImage myImage = new BufferedImage((int) (c.getWidth() * factor), (int) (c.getHeight() * factor),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = myImage.createGraphics();
		g2.scale(factor, factor);
		c.paint(g2);
		try {
			OutputStream out = new FileOutputStream(file);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(myImage);
			param.setQuality(1.0f, true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(myImage);
			out.close();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not save jpg image", e);
		}
	}
}