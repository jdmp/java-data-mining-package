package org.jdmp.test.matrix.calculation.string;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestReplaceRegex.class);
		return suite;
	}

}