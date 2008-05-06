package org.jdmp.matrix.interfaces;

import java.io.Serializable;

/**
 * A GUIObject is an object that can be displayed in a Frame. This interface is
 * needed to indicate that an object can be displayed when the package
 * org.jdmp.gui is available. In org.jdmp.matrix is also known that such an
 * object exists, but not what methods it provides. The object will be created
 * using the Reflection Api.
 * 
 * @author Holger Arndt
 * 
 */
public interface GUIObject extends Serializable, Disposable, Cloneable, HasLabel, HasDescription, HasToolTip {

	/**
	 * Indicates that changed in the object have been made, that should be
	 * updated on the screen.
	 */
	public void fireValueChanged();

}
