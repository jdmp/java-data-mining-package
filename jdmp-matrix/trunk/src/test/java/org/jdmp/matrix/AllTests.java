package org.jdmp.matrix;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.jdmp.matrix.calculation.AllTests.suite());
		suite.addTest(org.jdmp.matrix.implementations.AllTests.suite());
		suite.addTest(org.jdmp.matrix.util.AllTests.suite());
		return suite;
	}

}
