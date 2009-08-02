package org.jdmp.ehcache;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestEhcacheMap.class);
		suite.addTestSuite(TestSparseEhcacheMatrix.class);
		suite.addTestSuite(TestTiledEhcacheMatrix2D.class);
		return suite;
	}

}
