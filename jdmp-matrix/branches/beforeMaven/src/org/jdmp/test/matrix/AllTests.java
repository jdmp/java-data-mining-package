package org.jdmp.test.matrix;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.jdmp.test.matrix.calculation.AllTests.suite());
		suite.addTest(org.jdmp.test.matrix.implementations.AllTests.suite());
		suite.addTest(org.jdmp.test.matrix.util.AllTests.suite());
		return suite;
	}

}
