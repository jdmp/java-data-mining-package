package org.jdmp.matrix.implementations.io;

import java.io.File;

import org.jdmp.matrix.util.StringUtil;

public class WaveMatrix extends DenseFileMatrix2D {
	private static final long serialVersionUID = -4952985947339369630L;

	public static final int HEADERLENGTH = 44;

	private boolean ignoreHeader = true;

	public WaveMatrix(String filename) {
		this(new File(filename));
	}

	public WaveMatrix(File file) {
		this(file, false);
	}

	public WaveMatrix(File file, boolean readOnly) {
		super(file, 1, 1, 44, SHORTLITTLEENDIAN, readOnly);
		setRowCount((int) (getDataLength() / (getBitsPerSample() / 8) / getChannels()));
		setColumnCount(getChannels());
		// setRowCount(100000000);
		System.out.println(toString());
	}

	public double getEstimatedMinValue(long timeOut) {
		return -32768;
	}

	public double getEstimatedMaxValue(long timeOut) {
		return 32768;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("RIFF Tag:         " + getRIFFTag() + "\n");
		s.append("WAVE Tag:         " + getWAVETag() + "\n");
		s.append("fmt  Tag:         " + getFmtTag() + "\n");
		s.append("data Tag:         " + getDataTag() + "\n");
		s.append("Format:           " + getFormat() + "\n");
		s.append("Channels:         " + getChannels() + "\n");
		s.append("SampleRate:       " + getSampleRate() + "\n");
		s.append("BitsPerSample:    " + getBitsPerSample() + "\n");
		s.append("BytesPerSecond:   " + getBytesPerSecond() + "\n");
		s.append("BlockAlign:       " + getBlockAlign() + "\n");
		s.append("DataLengthHeader: " + getDataLengthFromHeader() + "\n");
		s.append("DataLengthFile:   " + getDataLengthFromFile() + "\n");
		s.append("Duration:         " + StringUtil.format(getDuration()) + "s\n");
		s.append("RowCount:         " + getRowCount() + "\n");
		s.append("ColumnCount:      " + getColumnCount() + "\n");
		s.append("Header-Check:     " + (isWaveFile() ? "passed" : "error") + "\n");
		return s.toString();
	}

	public int getChannels() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(22, bytes);
			return getShortLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public double getDuration() {
		return (double) getDataLength() / (double) getBytesPerSecond();
	}

	public String getFormat() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(20, bytes);
			return getShortLittleEndian(bytes) == 1 ? "PCM" : "unknown";
		} catch (Exception e) {
			return "unknown";
		}
	}

	public String getFmtTag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(12, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public String getDataTag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(36, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public String getRIFFTag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(0, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public String getWAVETag() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(8, bytes);
			return new String(bytes);
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isWaveFile() {
		if (!"RIFF".equals(getRIFFTag())) {
			return false;
		}
		if (!"WAVE".equals(getWAVETag())) {
			return false;
		}
		if (!"fmt ".equals(getFmtTag())) {
			return false;
		}
		if (!"PCM".equals(getFormat())) {
			return false;
		}
		if (!"data".equals(getDataTag())) {
			return false;
		}
		if (getBitsPerSample() != 8 && getBitsPerSample() != 16 && getBitsPerSample() != 32) {
			return false;
		}
		return true;
	}

	public int getBitsPerSample() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(34, bytes);
			return getShortLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getBlockAlign() {
		byte[] bytes = new byte[2];
		try {
			getRandomAccessFile().read(32, bytes);
			return getShortLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public int getSampleRate() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(24, bytes);
			return getIntLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public long getDataLengthFromFile() {
		return getFile().length() - (long) HEADERLENGTH;
	}

	public int getDataLengthFromHeader() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(40, bytes);
			return getIntLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

	public long getDataLength() {
		return (ignoreHeader) ? getDataLengthFromFile() : getDataLengthFromHeader();
	}

	public int getBytesPerSecond() {
		byte[] bytes = new byte[4];
		try {
			getRandomAccessFile().read(28, bytes);
			return getIntLittleEndian(bytes);
		} catch (Exception e) {
			return 0;
		}
	}

}
