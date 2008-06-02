/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.matrix.implementations.misc;

import java.io.Flushable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.collections.DefaultMatrixList;
import org.jdmp.matrix.collections.MatrixList;
import org.jdmp.matrix.coordinates.Coordinates;
import org.jdmp.matrix.exceptions.MatrixException;
import org.jdmp.matrix.implementations.basic.DefaultSparseGenericMatrix;
import org.jdmp.matrix.interfaces.HasSourceMatrix;
import org.jdmp.matrix.stubs.AbstractObjectMatrix;

public class BufferedObjectMatrix extends AbstractObjectMatrix implements Flushable, HasSourceMatrix {
	private static final long serialVersionUID = 7750549087897737457L;

	private Matrix inputBuffer = null;

	private Set<Coordinates> outputBuffer = null;

	private int outputBufferSize = Integer.MAX_VALUE;

	private Matrix original = null;

	private Thread writeThread = null;

	public BufferedObjectMatrix(Matrix original) {
		this.original = original;
		setInputBufferSize(0);
		setOutputBufferSize(Integer.MAX_VALUE);
		writeThread = new WriteThread();
		writeThread.start();
	}

	public BufferedObjectMatrix(Matrix original, int outputBufferSize) {
		this.original = original;
		setInputBufferSize(0);
		setOutputBufferSize(outputBufferSize);
	}

	public BufferedObjectMatrix(Matrix original, int outputBufferSize, int inputBufferSize) {
		this.original = original;
		setInputBufferSize(inputBufferSize);
		setOutputBufferSize(outputBufferSize);
	}

	public synchronized long[] getSize() {
		return inputBuffer.getSize();
	}

	public synchronized Object getObject(long... coordinates) throws MatrixException {
		Object o = null;
		o = inputBuffer.getObject(coordinates);
		if (o == null) {
			o = original.getObject(coordinates);
			inputBuffer.setObject(o, coordinates);
		}
		return o;
	}

	@Override
	public synchronized long getValueCount() {
		return original.getValueCount();
	}

	public synchronized boolean isSparse() {
		return inputBuffer.isSparse();
	}

	public synchronized Iterable<long[]> allCoordinates() {
		return original.allCoordinates();
	}

	public synchronized void setObject(Object value, long... coordinates) throws MatrixException {
		inputBuffer.setObject(value, coordinates);
		outputBuffer.add(new Coordinates(coordinates));
	}

	public synchronized void setInputBufferSize(int numElements) {
		if (numElements < 1) {
			inputBuffer = new VolatileSparseObjectMatrix(original.getSize());
		} else {
			inputBuffer = new DefaultSparseGenericMatrix(numElements, original.getSize());
		}
	}

	public synchronized void setOutputBufferSize(int numElements) {
		try {
			flush();
			outputBuffer = Collections.synchronizedSet(new HashSet<Coordinates>());
			outputBufferSize = numElements;
		} catch (IOException e) {
			logger.log(Level.WARNING, "could not set output buffer", e);
		}
	}

	public synchronized void flush() throws IOException {
		while (outputBuffer != null && outputBuffer.size() != 0) {
			try {
				outputBuffer.wait();
			} catch (InterruptedException e) {
				logger.log(Level.WARNING, "could not flush buffer", e);
			}
		}
	}

	public synchronized int getOutputBufferSize() {
		return outputBufferSize;
	}

	class WriteThread extends Thread {
		public void run() {
			while (true) {
				try {
					while (outputBuffer != null && !outputBuffer.isEmpty()) {
						Coordinates c = outputBuffer.iterator().next();
						outputBuffer.remove(c);
						double value = inputBuffer.getDouble(c.dimensions);
						original.setDouble(value, c.dimensions);
					}
					Thread.sleep(100);
				} catch (Exception e) {
					logger.log(Level.WARNING, "error writing to matrix", e);
				}
			}
		}
	}

	public boolean contains(long... coordinates) {
		return inputBuffer.contains(coordinates) || original.contains(coordinates);
	}

	@Override
	public boolean isReadOnly() {
		return original.isReadOnly();
	}

	public Matrix getSourceMatrix() {
		return original;
	}

	public MatrixList getSourceMatrices() {
		MatrixList matrices = new DefaultMatrixList();
		if (getSourceMatrix() instanceof HasSourceMatrix) {
			matrices.addAll(((HasSourceMatrix) getSourceMatrix()).getSourceMatrices());
		}
		matrices.add(getSourceMatrix());
		return matrices;
	}

}
