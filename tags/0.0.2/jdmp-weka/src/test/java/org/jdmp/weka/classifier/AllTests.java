package org.jdmp.weka.classifier;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestWekaClassifier.class);
		return suite;
	}

}