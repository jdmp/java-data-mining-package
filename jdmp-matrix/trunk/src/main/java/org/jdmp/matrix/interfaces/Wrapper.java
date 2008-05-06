package org.jdmp.matrix.interfaces;

/**
 * Classes implementing this interface indicate, that they act as a wrapper for
 * another class.
 * 
 * 
 * @param <A>
 *            The class that is wrapped inside
 */
public interface Wrapper<A> {

	/**
	 * Returns the object that is wrapped inside the wrapper
	 * 
	 * @return the inner object
	 */
	public A getWrappedObject();

	/**
	 * Sets the object that is wrapped inside the wrapper
	 * 
	 * @param object
	 *            new objects to wrap
	 */
	public void setWrappedObject(A object);
}
