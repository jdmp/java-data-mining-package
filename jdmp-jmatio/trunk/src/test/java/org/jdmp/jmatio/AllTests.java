package org.jdmp.jmatio;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestMLDoubleMatrix.class);
		suite.addTestSuite(TestExportMAT.class);
		return suite;
	}

}
