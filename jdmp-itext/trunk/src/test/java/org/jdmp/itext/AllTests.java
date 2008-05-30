package org.jdmp.itext;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(org.jdmp.itext.TestExportPDF.class);
		return suite;
	}

}
