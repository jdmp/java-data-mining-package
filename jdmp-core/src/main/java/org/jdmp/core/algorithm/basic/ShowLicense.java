/*
 * Copyright (C) 2008-2015 by Holger Arndt
 *
 * This file is part of the Java Data Mining Package (JDMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * JDMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * JDMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with JDMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.jdmp.core.algorithm.basic;

import java.util.HashMap;
import java.util.Map;

import org.jdmp.core.JDMP;
import org.jdmp.core.algorithm.AbstractAlgorithm;
import org.jdmp.core.variable.Variable;

public class ShowLicense extends AbstractAlgorithm {
	private static final long serialVersionUID = 363263434638588146L;

	public static final String DESCRIPTION = "displays license information for JDMP";

	public ShowLicense(Variable... variables) {
		super();
		setDescription(DESCRIPTION);
	}

	public Map<String, Object> calculateObjects(Map<String, Object> input) {
		Map<String, Object> result = new HashMap<String, Object>();
		String s = "\n";
		s += "                   Java Data Mining Package (JDMP)\n";
		s += "                           Version " + JDMP.JDMPVERSION + "\n";
		s += "Copyright (C) " + JDMP.COPYRIGHT + " " + JDMP.AUTHOR + "\n";
		s += "\n";
		s += "JDMP is free software; you can redistribute it and/or modify\n";
		s += "it under the terms of the GNU Lesser General Public License as\n";
		s += "published by the Free Software Foundation; either version 2\n";
		s += "of the License, or (at your option) any later version.\n";
		s += "\n";
		s += "JDMP is distributed in the hope that it will be useful,\n";
		s += "but WITHOUT ANY WARRANTY; without even the implied warranty of\n";
		s += "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n";
		s += "GNU Lesser General Public License for more details.\n";
		s += "\n";
		s += "\n";
		s += "                  GNU LESSER GENERAL PUBLIC LICENSE\n";
		s += "                       Version 3, 29 June 2007\n";
		s += "\n";
		s += "Copyright (C) 2007 Free Software Foundation, Inc. <http://fsf.org/>\n";
		s += "Everyone is permitted to copy and distribute verbatim copies\n";
		s += "of this license document, but changing it is not allowed.\n";
		s += "\n";
		s += "\n";
		s += "  This version of the GNU Lesser General Public License incorporates\n";
		s += "the terms and conditions of version 3 of the GNU General Public\n";
		s += "License, supplemented by the additional permissions listed below.\n";
		s += "\n";
		s += "  0. Additional Definitions. \n";
		s += "\n";
		s += "  As used herein, \"this License\" refers to version 3 of the GNU Lesser\n";
		s += "General Public License, and the \"GNU GPL\" refers to version 3 of the GNU\n";
		s += "General Public License.\n";
		s += "\n";
		s += "  \"The Library\" refers to a covered work governed by this License,\n";
		s += "other than an Application or a Combined Work as defined below.\n";
		s += "\n";
		s += "  An \"Application\" is any work that makes use of an interface provided\n";
		s += "by the Library, but which is not otherwise based on the Library.\n";
		s += "Defining a subclass of a class defined by the Library is deemed a mode\n";
		s += "of using an interface provided by the Library.\n";
		s += "\n";
		s += "  A \"Combined Work\" is a work produced by combining or linking an\n";
		s += "Application with the Library.  The particular version of the Library\n";
		s += "with which the Combined Work was made is also called the \"Linked\n";
		s += "Version\".\n";
		s += "\n";
		s += "  The \"Minimal Corresponding Source\" for a Combined Work means the\n";
		s += "Corresponding Source for the Combined Work, excluding any source code\n";
		s += "for portions of the Combined Work that, considered in isolation, are\n";
		s += "based on the Application, and not on the Linked Version.\n";
		s += "\n";
		s += "  The \"Corresponding Application Code\" for a Combined Work means the\n";
		s += "object code and/or source code for the Application, including any data\n";
		s += "and utility programs needed for reproducing the Combined Work from the\n";
		s += "Application, but excluding the System Libraries of the Combined Work.\n";
		s += "\n";
		s += "  1. Exception to Section 3 of the GNU GPL.\n";
		s += "\n";
		s += "  You may convey a covered work under sections 3 and 4 of this License\n";
		s += "without being bound by section 3 of the GNU GPL.\n";
		s += "\n";
		s += "  2. Conveying Modified Versions.\n";
		s += "\n";
		s += "  If you modify a copy of the Library, and, in your modifications, a\n";
		s += "facility refers to a function or data to be supplied by an Application\n";
		s += "that uses the facility (other than as an argument passed when the\n";
		s += "facility is invoked), then you may convey a copy of the modified\n";
		s += "version:\n";
		s += "\n";
		s += "   a) under this License, provided that you make a good faith effort to\n";
		s += "   ensure that, in the event an Application does not supply the\n";
		s += "   function or data, the facility still operates, and performs\n";
		s += "   whatever part of its purpose remains meaningful, or\n";
		s += "\n";
		s += "   b) under the GNU GPL, with none of the additional permissions of\n";
		s += "   this License applicable to that copy.\n";
		s += "\n";
		s += "  3. Object Code Incorporating Material from Library Header Files.\n";
		s += "\n";
		s += "  The object code form of an Application may incorporate material from\n";
		s += "a header file that is part of the Library.  You may convey such object\n";
		s += "code under terms of your choice, provided that, if the incorporated\n";
		s += "material is not limited to numerical parameters, data structure\n";
		s += "layouts and accessors, or small macros, inline functions and templates\n";
		s += "(ten or fewer lines in length), you do both of the following:\n";
		s += "\n";
		s += "   a) Give prominent notice with each copy of the object code that the\n";
		s += "   Library is used in it and that the Library and its use are\n";
		s += "   covered by this License.\n";
		s += "\n";
		s += "   b) Accompany the object code with a copy of the GNU GPL and this license\n";
		s += "   document.\n";
		s += "\n";
		s += "  4. Combined Works.\n";
		s += "\n";
		s += "  You may convey a Combined Work under terms of your choice that,\n";
		s += "taken together, effectively do not restrict modification of the\n";
		s += "portions of the Library contained in the Combined Work and reverse\n";
		s += "engineering for debugging such modifications, if you also do each of\n";
		s += "the following:\n";
		s += "\n";
		s += "   a) Give prominent notice with each copy of the Combined Work that\n";
		s += "   the Library is used in it and that the Library and its use are\n";
		s += "   covered by this License.\n";
		s += "\n";
		s += "   b) Accompany the Combined Work with a copy of the GNU GPL and this license\n";
		s += "   document.\n";
		s += "\n";
		s += "   c) For a Combined Work that displays copyright notices during\n";
		s += "   execution, include the copyright notice for the Library among\n";
		s += "   these notices, as well as a reference directing the user to the\n";
		s += "   copies of the GNU GPL and this license document.\n";
		s += "\n";
		s += "   d) Do one of the following:\n";
		s += "\n";
		s += "       0) Convey the Minimal Corresponding Source under the terms of this\n";
		s += "       License, and the Corresponding Application Code in a form\n";
		s += "       suitable for, and under terms that permit, the user to\n";
		s += "       recombine or relink the Application with a modified version of\n";
		s += "       the Linked Version to produce a modified Combined Work, in the\n";
		s += "       manner specified by section 6 of the GNU GPL for conveying\n";
		s += "       Corresponding Source.\n";
		s += "\n";
		s += "       1) Use a suitable shared library mechanism for linking with the\n";
		s += "       Library.  A suitable mechanism is one that (a) uses at run time\n";
		s += "       a copy of the Library already present on the user's computer\n";
		s += "       system, and (b) will operate properly with a modified version\n";
		s += "       of the Library that is interface-compatible with the Linked\n";
		s += "       Version. \n";
		s += "\n";
		s += "   e) Provide Installation Information, but only if you would otherwise\n";
		s += "   be required to provide such information under section 6 of the\n";
		s += "   GNU GPL, and only to the extent that such information is\n";
		s += "   necessary to install and execute a modified version of the\n";
		s += "   Combined Work produced by recombining or relinking the\n";
		s += "   Application with a modified version of the Linked Version. (If\n";
		s += "   you use option 4d0, the Installation Information must accompany\n";
		s += "   the Minimal Corresponding Source and Corresponding Application\n";
		s += "   Code. If you use option 4d1, you must provide the Installation\n";
		s += "   Information in the manner specified by section 6 of the GNU GPL\n";
		s += "   for conveying Corresponding Source.)\n";
		s += "\n";
		s += "  5. Combined Libraries.\n";
		s += "\n";
		s += "  You may place library facilities that are a work based on the\n";
		s += "Library side by side in a single library together with other library\n";
		s += "facilities that are not Applications and are not covered by this\n";
		s += "License, and convey such a combined library under terms of your\n";
		s += "choice, if you do both of the following:\n";
		s += "\n";
		s += "   a) Accompany the combined library with a copy of the same work based\n";
		s += "   on the Library, uncombined with any other library facilities,\n";
		s += "   conveyed under the terms of this License.\n";
		s += "\n";
		s += "   b) Give prominent notice with the combined library that part of it\n";
		s += "   is a work based on the Library, and explaining where to find the\n";
		s += "   accompanying uncombined form of the same work.\n";
		s += "\n";
		s += "  6. Revised Versions of the GNU Lesser General Public License.\n";
		s += "\n";
		s += "  The Free Software Foundation may publish revised and/or new versions\n";
		s += "of the GNU Lesser General Public License from time to time. Such new\n";
		s += "versions will be similar in spirit to the present version, but may\n";
		s += "differ in detail to address new problems or concerns.\n";
		s += "\n";
		s += "  Each version is given a distinguishing version number. If the\n";
		s += "Library as you received it specifies that a certain numbered version\n";
		s += "of the GNU Lesser General Public License \"or any later version\"\n";
		s += "applies to it, you have the option of following the terms and\n";
		s += "conditions either of that published version or of any later version\n";
		s += "published by the Free Software Foundation. If the Library as you\n";
		s += "received it does not specify a version number of the GNU Lesser\n";
		s += "General Public License, you may choose any version of the GNU Lesser\n";
		s += "General Public License ever published by the Free Software Foundation.\n";
		s += "\n";
		s += "  If the Library as you received it specifies that a proxy can decide\n";
		s += "whether future versions of the GNU Lesser General Public License shall\n";
		s += "apply, that proxy's public statement of acceptance of any version is\n";
		s += "permanent authorization for you to choose that version for the\n";
		s += "Library.\n";
		s += "\n";
		s += "\n";
		result.put(TARGET, s);
		return result;
	}

}
