package org.jdmp.jexcelapi;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestDenseExcelMatrix2D.class);
		return suite;
	}

}