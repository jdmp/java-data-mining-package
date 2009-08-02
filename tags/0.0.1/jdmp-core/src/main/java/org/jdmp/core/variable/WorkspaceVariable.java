package org.jdmp.core.variable;

public class WorkspaceVariable extends DefaultVariable {
	private static final long serialVersionUID = 8106731375954572830L;

	private static WorkspaceVariable variable = null;

	private WorkspaceVariable() {
		super("Workspace Variable");
	}

	public static WorkspaceVariable getInstance() {
		if (variable == null) {
			variable = new WorkspaceVariable();
		}
		return variable;
	}

}
