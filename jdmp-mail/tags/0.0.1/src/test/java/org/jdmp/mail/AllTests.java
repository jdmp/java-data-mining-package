package org.jdmp.mail;

import junit.framework.TestSuite;

public class AllTests extends TestSuite {

    public static TestSuite suite() {
        TestSuite suite = new TestSuite(AllTests.class.getName());
        return suite;
    }

}
