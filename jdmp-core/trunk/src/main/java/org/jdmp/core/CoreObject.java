package org.jdmp.core;

import java.io.Serializable;

import org.ujmp.core.interfaces.Clearable;
import org.ujmp.core.interfaces.HasDescription;
import org.ujmp.core.interfaces.HasGUIObject;
import org.ujmp.core.interfaces.HasLabel;

public interface CoreObject extends Serializable, Clearable, Cloneable, HasGUIObject, HasLabel,
		HasDescription {

}
