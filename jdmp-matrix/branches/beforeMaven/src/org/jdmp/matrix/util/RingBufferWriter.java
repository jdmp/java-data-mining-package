package org.jdmp.matrix.util;

import java.io.IOException;
import java.io.OutputStream;

public class RingBufferWriter extends OutputStream {

	private int start = -1;

	private int end = -1;

	private byte values[];

	public RingBufferWriter() {
		this(10);
	}

	public RingBufferWriter(int maximumSize) {
		values = new byte[maximumSize];
	}

	public int maxSize() {
		return values.length;
	}

	public boolean add(byte a) {
		if (end >= 0) {
			end++;
			if (end >= values.length) {
				end = 0;
			}
			if (end == start) {
				start++;
			}
			if (start >= values.length) {
				start = 0;
			}
		} else {
			start = 0;
			end = 0;
		}
		values[end] = a;
		return true;
	}

	public int size() {
		if (end < 0) {
			return 0;
		}
		return end < start ? values.length : end - start + 1;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			s.append((char) get(i));
		}
		return s.toString();
	}

	public byte get(int index) {
		return values[(start + index) % values.length];
	}

	public byte set(int index, byte a) {
		byte old = values[(start + index) % values.length];
		values[(start + index) % values.length] = a;
		return old;
	}

	public void clear() {
		start = -1;
		end = -1;
	}

	public void add(int index, char element) {
		new Exception("not implemented").printStackTrace();
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public void write(int b) throws IOException {
		add((byte) b);
	}

}