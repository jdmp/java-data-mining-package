package org.jdmp.matrix.io;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(org.jdmp.matrix.io.TestExportCSV.class);
		suite.addTestSuite(org.jdmp.matrix.io.TestExportXLS.class);
		return suite;
	}

}