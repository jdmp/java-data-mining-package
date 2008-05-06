package org.jdmp.matrix.util.collections;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public static TestSuite suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		suite.addTestSuite(TestRingBufferList.class);
		suite.addTestSuite(TestSerializedObjectMap.class);
		suite.addTestSuite(TestHashMapList.class);
		suite.addTestSuite(TestSoftHashMap.class);
		suite.addTestSuite(TestSoftHashMapList.class);
		return suite;
	}

}
