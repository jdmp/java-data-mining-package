package org.jdmp.jmatrices;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestJMatricesMatrix.class);
		return suite;
	}

}