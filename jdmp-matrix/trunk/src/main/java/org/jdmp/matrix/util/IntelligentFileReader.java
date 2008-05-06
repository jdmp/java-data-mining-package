package org.jdmp.matrix.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;

public class IntelligentFileReader extends Reader {

	private static final Logger logger = Logger.getLogger(IntelligentFileReader.class.getName());

	private FileReader fr = null;

	private InputStream zip = null;

	private LineNumberReader lr = null;

	public IntelligentFileReader(String file) {
		this(new File(file));
	}

	public IntelligentFileReader(InputStream inputStream) {
		try {
			lr = new LineNumberReader(new InputStreamReader(inputStream));
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not open stream", e);
		}
	}

	public IntelligentFileReader(File file) {
		if (file != null && file.exists()) {
			if (file.getAbsolutePath().toLowerCase().endsWith(".gz")) {
				try {
					zip = new GZIPInputStream(new FileInputStream(file));
					lr = new LineNumberReader(new InputStreamReader(zip));
				} catch (Exception e) {
					logger.log(Level.WARNING, "could not open file " + file, e);
				}
			} else if (file.getAbsolutePath().toLowerCase().endsWith(".z")) {
				try {
					zip = new ZipInputStream(new FileInputStream(file));
					lr = new LineNumberReader(new InputStreamReader(zip));
				} catch (Exception e) {
					logger.log(Level.WARNING, "could not open file " + file, e);
				}
			} else {
				try {
					fr = new FileReader(file);
					lr = new LineNumberReader(fr);
				} catch (Exception e) {
					logger.log(Level.WARNING, "could not open file " + file, e);
				}
			}
		} else {
			logger.log(Level.WARNING, "cannot open file: " + file);
		}
	}

	public IntelligentFileReader(URLConnection connection) throws IOException {
		this(connection.getInputStream());
	}

	public String readLine() {
		if (lr == null)
			return null;
		try {
			return lr.readLine();
		} catch (Exception e) {
			logger.log(Level.WARNING, "could not read line", e);
		}
		return null;
	}

	public void close() {
		try {
			if (lr != null)
				lr.close();
		} catch (Exception e) {
		}
		try {
			if (fr != null)
				fr.close();
		} catch (Exception e) {
		}
		try {
			if (zip != null)
				zip.close();
		} catch (Exception e) {
		}
	}

	public int getLineNumber() {
		if (lr != null)
			return lr.getLineNumber();
		else
			return -1;
	}

  @Override
  public int read(char[] cbuf, int off, int len) throws IOException {
    return lr.read(cbuf, off, len);
  }

}
