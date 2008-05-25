package org.jdmp.matrix;

import java.util.Iterator;

public class CoordinateIterator2D implements Iterable<long[]> {

	private static final int ROW = Matrix.ROW;

	private static final int COLUMN = Matrix.COLUMN;

	private long[] size = null;

	public CoordinateIterator2D(long... size) {
		this.size = size;
		if (size.length > 2) {
			new Exception(
					"warning: using a 2d iterator on a matrix or calculation that has more than 2 dimensions, results may be wrong")
					.printStackTrace();
		}
	}

	public Iterator<long[]> iterator() {
		return new Iterator<long[]>() {

			long[] cursor = new long[] { 0, -1 };

			long columnCount = size[COLUMN];

			long rowCount = size[ROW];

			public boolean hasNext() {
				return (cursor[ROW] != rowCount - 1 || cursor[COLUMN] != columnCount - 1) && columnCount != 0
						&& rowCount != 0;
			}

			public long[] next() {
				return ++cursor[COLUMN] == columnCount && (cursor[COLUMN] = 0) == ++cursor[ROW] ? cursor : cursor;
			}

			public void remove() {
				throw new MatrixException("not implemented");
			}
		};
	}

}
