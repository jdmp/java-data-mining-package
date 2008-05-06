package org.jdmp.matrix.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SeekableLineInputStream extends InputStream {

	private int bufferSize = 65536;

	private BufferedRandomAccessFile in = null;

	private List<Long> lineEnds = new ArrayList<Long>();

	public SeekableLineInputStream(String file) throws IOException {
		this(new File(file));
	}

	public SeekableLineInputStream(File file) throws IOException {
		in = new BufferedRandomAccessFile(file, "r", bufferSize);
		byte[] bytes = new byte[bufferSize];
		for (long pos = 0; pos < in.length(); pos += bufferSize) {
			Arrays.fill(bytes, (byte) 0);
			in.read(pos, bytes);

			for (int i = 0; i < bufferSize; i++) {
				byte b = bytes[i];
				if (b == 10) {
					lineEnds.add(pos + i);
				}
			}
		}
		System.out.println(getLineCount() + " lines");
	}

	public void close() throws IOException {
		in.close();
	}

	public int getLineCount() {
		return lineEnds.size() + 1;
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	public String readLine(int lineNumber) throws IOException {
		String line = null;
		if (line == null) {
			long start = 0;
			if (lineNumber > 0) {
				start = lineEnds.get(lineNumber - 1) + 1;
			}
			long end = 0;
			if (lineNumber < getLineCount() - 1) {
				end = lineEnds.get(lineNumber);
			} else {
				end = in.length();
			}
			int length = (int) (end - start);
			byte[] bytes = new byte[length];
			in.read(start, bytes);
			line = new String(bytes);
		}
		return line;
	}
}
