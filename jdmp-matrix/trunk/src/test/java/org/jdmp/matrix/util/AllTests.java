package org.jdmp.matrix.util;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.jdmp.matrix.util.collections.AllTests.suite());
		return suite;
	}

}
