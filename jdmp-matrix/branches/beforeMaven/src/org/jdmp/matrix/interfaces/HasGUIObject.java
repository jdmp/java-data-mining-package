package org.jdmp.matrix.interfaces;

/**
 * Declares, that an object suitable for displaying on the screen with
 * listeners, icon, etc. can be returned.
 * 
 * @see GUIObject
 * @author Holger Arndt
 * 
 */
public interface HasGUIObject {

	/**
	 * Returns an object suitable for displaying on the screen. Ensures that,
	 * for each basic object, only one GUIObject can be returned.
	 * 
	 * @return object suitable for displaying on the screenf
	 */
	public GUIObject getGUIObject();

	/**
	 * This method is used to signal changes in the object to the corresponding
	 * GUIObject if it exists.
	 * <p>
	 * Maybe this method can be deleted, when all changes are made trough the
	 * GUIObject?
	 * 
	 */
	public void notifyGUIObject();

	/**
	 * This method will show the object in a JFrame on the screen.
	 * 
	 */
	public void showGUI();

}
