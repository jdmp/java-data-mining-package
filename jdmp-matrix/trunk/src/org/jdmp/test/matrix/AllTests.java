package org.jdmp.test.matrix;

import junit.framework.TestSuite;

import org.jdmp.test.matrix.calculation.string.TestReplaceRegex;
import org.jdmp.test.matrix.implementations.TestDefaultFullDoubleMatrix2D;
import org.jdmp.test.matrix.implementations.TestDefaultFullObjectMatrix2D;
import org.jdmp.test.matrix.util.collections.TestMap;
import org.jdmp.test.matrix.util.collections.TestRingBufferList;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestReplaceRegex.class);
		suite.addTestSuite(TestMap.class);
		suite.addTestSuite(TestRingBufferList.class);
		suite.addTestSuite(TestDefaultFullDoubleMatrix2D.class);
		suite.addTestSuite(TestDefaultFullObjectMatrix2D.class);
		// TODO:errors here:
		// suite.addTestSuite(TestDefaultFullStringMatrix2D.class);
		return suite;
	}

}
