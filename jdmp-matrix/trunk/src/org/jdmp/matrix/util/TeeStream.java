package org.jdmp.matrix.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class TeeStream extends PrintStream {

	private OutputStream tee = null;

	public TeeStream(OutputStream out, OutputStream tee) {
		super(out);
		this.tee = tee;
	}

	@Override
	public void write(byte[] buf, int off, int len) {
		super.write(buf, off, len);
		try {
			tee.write(buf, off, len);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		super.close();
		try {
			tee.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void flush() {
		super.flush();
		try {
			tee.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
