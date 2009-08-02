package org.jdmp.complete;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.ujmp.complete.AllTests.suite());
		suite.addTest(org.jdmp.mail.AllTests.suite());
		suite.addTest(org.jdmp.core.AllTests.suite());
		suite.addTest(org.jdmp.ehcache.AllTests.suite());
		suite.addTest(org.jdmp.gui.AllTests.suite());
		suite.addTest(org.jdmp.itext.AllTests.suite());
		suite.addTest(org.jdmp.libsvm.AllTests.suite());
		suite.addTest(org.jdmp.weka.AllTests.suite());
		return suite;
	}

}
