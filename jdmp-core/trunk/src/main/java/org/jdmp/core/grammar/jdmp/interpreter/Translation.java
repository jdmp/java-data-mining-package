package org.jdmp.core.grammar.jdmp.interpreter;

import org.jdmp.core.grammar.jdmp.analysis.DepthFirstAdapter;
import org.jdmp.core.grammar.jdmp.node.AFactorExpression;
import org.jdmp.core.grammar.jdmp.node.AIntegerValue;
import org.jdmp.core.grammar.jdmp.node.AMatrixTerm;
import org.jdmp.core.grammar.jdmp.node.AScalarMatrix;
import org.jdmp.core.grammar.jdmp.node.ASilentAssignment;
import org.jdmp.core.grammar.jdmp.node.ASilentStatement;
import org.jdmp.core.grammar.jdmp.node.ATermFactor;
import org.jdmp.core.grammar.jdmp.node.AVerboseAssignment;
import org.jdmp.core.grammar.jdmp.node.AVerboseStatement;
import org.jdmp.core.grammar.jdmp.node.Node;
import org.jdmp.core.grammar.jdmp.node.PExpression;
import org.jdmp.core.grammar.jdmp.node.PFactor;
import org.jdmp.core.grammar.jdmp.node.PIdentifier;
import org.jdmp.core.grammar.jdmp.node.PMatrix;
import org.jdmp.core.grammar.jdmp.node.PTerm;
import org.jdmp.core.grammar.jdmp.node.PValue;
import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.exceptions.MatrixException;

public class Translation extends DepthFirstAdapter {

	private Module module = null;

	private Result result = null;

	public Translation(Module module) {
		this.module = module;
	}

	public Variable getVariable(PIdentifier identifier) {
		String label = identifier.toString().trim();
		Variable v = module.getVariableList().get(label);
		if (v == null) {
			v = VariableFactory.labeledVariable(label);
			module.getVariableList().put(label, v);
		}
		return v;
	}

	public Matrix getMatrix(PFactor factor) {
		if (factor instanceof ATermFactor) {
			return getMatrix(((ATermFactor) factor).getTerm());
		} else {
			throw new MatrixException("unknown factor type: " + factor.getClass().getSimpleName());
		}
	}

	private Matrix getMatrix(PTerm term) {
		if (term instanceof AMatrixTerm) {
			return getMatrix(((AMatrixTerm) term).getMatrix());
		} else {
			throw new MatrixException("unknown term type: " + term.getClass().getSimpleName());
		}
	}

	private Matrix getMatrix(PMatrix matrix) {
		if (matrix instanceof AScalarMatrix) {
			return getMatrix(((AScalarMatrix) matrix).getValue());
		} else {
			throw new MatrixException("unknown matrix type: " + matrix.getClass().getSimpleName());
		}
	}

	private Object getValue(PValue value) {
		if (value instanceof AIntegerValue) {
			return Integer.parseInt(value.toString().trim());
		} else {
			throw new MatrixException("unknown value type: " + value.getClass().getSimpleName());
		}
	}

	private Matrix getMatrix(PValue value) {
		return MatrixFactory.linkToValue(getValue(value));
	}

	public Matrix getMatrix(PExpression expression) {
		if (expression instanceof AFactorExpression) {
			return getMatrix(((AFactorExpression) expression).getFactor());
		} else {
			throw new MatrixException("unknown expression type: "
					+ expression.getClass().getSimpleName());
		}
	}

	@Override
	public void defaultOut(Node node) {
		System.out.println(node.getClass().getSimpleName());
	}

	@Override
	public void outAVerboseAssignment(AVerboseAssignment node) {
		super.outAVerboseAssignment(node);
		System.out.println("   " + node.getIdentifier() + " = " + node.getExpression());
		Variable v = getVariable(node.getIdentifier());
		Matrix m = getMatrix(node.getExpression());
		v.addMatrix(m);
		result = new Result(v.getLabel() + " = " + m.toString());
	}

	@Override
	public void outASilentAssignment(ASilentAssignment node) {
		super.outASilentAssignment(node);
		System.out.println("   " + node.getIdentifier() + " = " + node.getExpression());
		Variable v = getVariable(node.getIdentifier());
		Matrix m = getMatrix(node.getExpression());
		v.addMatrix(m);
		result = new Result("");
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

	public Result getResult() {
		return result;
	}

}
