package org.jdmp.test.matrix.calculation;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.jdmp.test.matrix.calculation.string.AllTests.suite());
		return suite;
	}

}