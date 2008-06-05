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

package org.jdmp.matrix.io.util;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipOutputStream;

public class IntelligentFileWriter extends Writer implements Appendable, Closeable, Flushable {

	private static final String SEVENZIPOUTPUTSTREAM = "org.jdmp.j7zip.SevenZipOutputStream";

	private FileWriter fw = null;

	private OutputStream zip = null;

	private BufferedWriter bw = null;

	public IntelligentFileWriter(String filename) throws IOException, ClassNotFoundException {
		this(new File(filename));
	}

	public IntelligentFileWriter(OutputStream outputStream) throws IOException {
		bw = new BufferedWriter(new OutputStreamWriter(outputStream));
	}

	public IntelligentFileWriter(File file) throws IOException {
		if (file.getAbsolutePath().toLowerCase().endsWith(".gz")) {
			zip = new GZIPOutputStream(new FileOutputStream(file));
			bw = new BufferedWriter(new OutputStreamWriter(zip));
		} else if (file.getAbsolutePath().toLowerCase().endsWith(".z")) {
			zip = new ZipOutputStream(new FileOutputStream(file));
			bw = new BufferedWriter(new OutputStreamWriter(zip));
		} else if (file.getAbsolutePath().toLowerCase().endsWith(".7z")) {
			try {
				Class<?> c = Class.forName(SEVENZIPOUTPUTSTREAM);
				Constructor con = c.getConstructor(new Class[] { FileOutputStream.class });
				zip = (OutputStream) con.newInstance(new Object[] { new FileOutputStream(file) });
			} catch (ClassNotFoundException e) {
				throw new IOException("Could not find jdmp-j7zip in classpath", e);
			} catch (Exception e) {
				throw new IOException("Could not create SevenZipOutputStream", e);
			}
			bw = new BufferedWriter(new OutputStreamWriter(zip));
		} else {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
		}
	}

	public static final void save(String filename, String text) throws Exception {
		IntelligentFileWriter fw = new IntelligentFileWriter(filename);
		fw.write(text);
		fw.close();
	}

	public void close() throws IOException {
		if (bw != null)
			bw.close();
		if (fw != null)
			fw.close();
		if (zip != null)
			zip.close();
	}

	public void flush() throws IOException {
		if (bw != null)
			bw.flush();
		if (fw != null)
			fw.flush();
		if (zip != null)
			zip.flush();
	}

  public void write(char[] cbuf, int off, int len) throws IOException {
    fw.write(cbuf, off, len);    
  }

}