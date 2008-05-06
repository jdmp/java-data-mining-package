package org.jdmp.matrix.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Map;

import org.jdmp.matrix.util.collections.SoftHashMap;

public class BufferedRandomAccessFile extends RandomAccessFile {

	private int bufferSize = 65536;

	private Map<Long, byte[]> buffer = new SoftHashMap<Long, byte[]>();

	public BufferedRandomAccessFile(File file, String mode) throws FileNotFoundException {
		super(file, mode);
	}

	public BufferedRandomAccessFile(File file, String mode, int bufferSize) throws FileNotFoundException {
		super(file, mode);
		this.bufferSize = bufferSize;
	}

	public int read() throws IOException {
		byte[] b = new byte[1];
		read(b);
		seek(getFilePointer() + 1);
		new IOException("don't use this method").printStackTrace();
		return b[0];
	}

	@Override
	public void seek(long pos) throws IOException {
		new IOException("don't use this method").printStackTrace();
		super.seek(pos);

	}

	@Override
	public int read(byte[] b) throws IOException {
		new IOException("don't use this method").printStackTrace();
		return super.read(b);
	}

	public int read(long seek, byte b[]) throws IOException {
		if (b.length > bufferSize) {
			throw (new IOException("cannot read more than buffersize"));
		}

		long pos = (seek / bufferSize) * bufferSize;
		int offset = (int) (seek - pos);

		byte[] bytes = buffer.get(pos);
		if (bytes == null) {
			super.seek(pos);
			bytes = new byte[bufferSize];
			super.read(bytes);
			buffer.put(pos, bytes);
		}

		if (offset + b.length > bufferSize) {
			System.arraycopy(bytes, offset, b, 0, bufferSize - offset);
			pos += bufferSize;
			bytes = buffer.get(pos);
			if (bytes == null) {
				super.seek(pos);
				bytes = new byte[bufferSize];
				super.read(bytes);
				buffer.put(pos, bytes);
			}
			System.arraycopy(bytes, 0, b, bufferSize - offset, b.length - bufferSize + offset);
		} else {
			System.arraycopy(bytes, offset, b, 0, b.length);
		}
		return b.length;

	}

}
