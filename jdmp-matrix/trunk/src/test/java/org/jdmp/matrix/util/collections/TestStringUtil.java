package org.jdmp.matrix.util.collections;

import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.util.StringUtil;

import junit.framework.TestCase;

public class TestStringUtil extends TestCase {

	public void testParseSelectionMatlab() {

		try {
			long[][] sel = StringUtil.parseSelection(":,2", new long[] { 3, 4 });
		} catch (MatrixException e) {
			// should not work yet
			return;
		}

		throw new RuntimeException("Matlab selection is available. Add tests");

	}

}
