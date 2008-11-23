package org.jdmp.core.grammar.jdmp.interpreter;

import org.jdmp.core.grammar.jdmp.analysis.DepthFirstAdapter;
import org.jdmp.core.grammar.jdmp.node.ASilentAssignment;
import org.jdmp.core.grammar.jdmp.node.ASilentStatement;
import org.jdmp.core.grammar.jdmp.node.AVerboseAssignment;
import org.jdmp.core.grammar.jdmp.node.AVerboseStatement;
import org.jdmp.core.grammar.jdmp.node.Node;

public class Translation extends DepthFirstAdapter {

	@Override
	public void defaultOut(Node node) {
		System.out.println(node.getClass().getSimpleName());
	}

	@Override
	public void outAVerboseAssignment(AVerboseAssignment node) {
		super.outAVerboseAssignment(node);
		System.out.println("   " + node.getIdentifier() + " = " + node.getExpression());
	}

	@Override
	public void outASilentAssignment(ASilentAssignment node) {
		super.outASilentAssignment(node);
		System.out.println("   " + node.getIdentifier() + "= " + node.getExpression());
	}

	@Override
	public void outASilentStatement(ASilentStatement node) {
		super.outASilentStatement(node);
		System.out.println("   " + node.getExpression());
	}

	@Override
	public void outAVerboseStatement(AVerboseStatement node) {
		super.outAVerboseStatement(node);
		System.out.println("   " + node.getExpression());
	}

}
