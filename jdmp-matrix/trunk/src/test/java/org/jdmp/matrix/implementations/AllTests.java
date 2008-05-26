package org.jdmp.matrix.implementations;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestDefaultFullDoubleMatrix2D.class);
		suite.addTestSuite(TestDefaultFullObjectMatrix2D.class);
		suite.addTestSuite(TestDefaultFullStringMatrix2D.class);
		suite.addTestSuite(TestCommonsMathRealMatrix.class);
		suite.addTestSuite(TestDefaultTiledObjectMatrix2D.class);
		return suite;
	}

}
