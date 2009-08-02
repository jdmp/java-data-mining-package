package org.jdmp.jgroups;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestReplicatedSparseMatrix.class);
		return suite;
	}

}