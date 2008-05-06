package org.jdmp.matrix.implementations.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.stubs.AbstractDenseDoubleMatrix2D;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

public class ImageMatrix extends AbstractDenseDoubleMatrix2D {
	private static final long serialVersionUID = -1354524587823816194L;

	private File file = null;

	private BufferedImage bufferedImage = null;

	private int[] pixels = null;

	public ImageMatrix(String filename) throws ImageFormatException, IOException {
		this(new File(filename));
	}

	public ImageMatrix(File file) throws ImageFormatException, IOException {
		this.file = file;
		FileInputStream fis = new FileInputStream(file);
		JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(fis);
		bufferedImage = decoder.decodeAsBufferedImage();
		pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		fis.close();
	}

	public double getDouble(long... coordinates) throws MatrixException {
		int pos = (int) (coordinates[ROW] * bufferedImage.getWidth() + coordinates[COLUMN]);
		return pixels[pos];
	}

	public void setDouble(double value, long... coordinates) throws MatrixException {
		int pos = (int) (coordinates[ROW] * bufferedImage.getWidth() + coordinates[COLUMN]);
		pixels[pos] = (int) value;
	}

	public long[] getSize() {
		return new long[] { bufferedImage.getHeight(), bufferedImage.getWidth() };
	}

	public Object getMatrixAnnotation() {
		return file;
	}

}
