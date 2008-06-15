package org.jdmp.weka;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTest(org.jdmp.weka.classifier.AllTests.suite());
		suite.addTest(org.jdmp.weka.clusterer.AllTests.suite());
		suite.addTest(org.jdmp.weka.wrappers.AllTests.suite());
		return suite;
	}

}
