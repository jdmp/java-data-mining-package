package org.jdmp.mtj;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(org.jdmp.mtj.TestMTJFullDoubleMatrix2D.class);
		return suite;
	}

}
