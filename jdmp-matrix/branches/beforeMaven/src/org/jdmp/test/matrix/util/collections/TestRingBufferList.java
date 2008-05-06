package org.jdmp.test.matrix.util.collections;

import junit.framework.TestCase;

import org.jdmp.matrix.util.collections.RingBufferList;

public class TestRingBufferList extends TestCase {

	public void testAdd() {
		RingBufferList<Integer> rl = new RingBufferList<Integer>(4);

		assertEquals(0, rl.size());

		rl.add(0);
		assertEquals(1, rl.size());
		assertEquals(0, (int) rl.get(0));

		rl.add(1);
		assertEquals(2, rl.size());
		assertEquals(1, (int) rl.get(1));

		rl.add(2);
		assertEquals(3, rl.size());
		assertEquals(2, (int) rl.get(2));

		rl.set(1, 5);
		assertEquals(3, rl.size());
		assertEquals(5, (int) rl.get(1));

		rl.add(3);
		assertEquals(4, rl.size());
		assertEquals(3, (int) rl.get(3));

		rl.add(4);
		assertEquals(4, rl.size());
		assertEquals(4, (int) rl.get(3));

		rl.add(5);
		assertEquals(4, rl.size());
		assertEquals(5, (int) rl.get(3));

	}

}
