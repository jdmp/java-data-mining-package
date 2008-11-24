package org.jdmp.core.grammar.jdmp.interpreter;

import org.jdmp.core.grammar.jdmp.analysis.DepthFirstAdapter;
import org.jdmp.core.grammar.jdmp.node.AArray;
import org.jdmp.core.grammar.jdmp.node.AArrayMatrix;
import org.jdmp.core.grammar.jdmp.node.ABooleanValue;
import org.jdmp.core.grammar.jdmp.node.ACharacterValue;
import org.jdmp.core.grammar.jdmp.node.AColumn;
import org.jdmp.core.grammar.jdmp.node.AColumnMatrix;
import org.jdmp.core.grammar.jdmp.node.ACommaValue;
import org.jdmp.core.grammar.jdmp.node.AFactorExpression;
import org.jdmp.core.grammar.jdmp.node.AFloatingPointValue;
import org.jdmp.core.grammar.jdmp.node.AIntegerValue;
import org.jdmp.core.grammar.jdmp.node.AMatrixTerm;
import org.jdmp.core.grammar.jdmp.node.ARow;
import org.jdmp.core.grammar.jdmp.node.ARowMatrix;
import org.jdmp.core.grammar.jdmp.node.AScalarMatrix;
import org.jdmp.core.grammar.jdmp.node.ASemicolonRow;
import org.jdmp.core.grammar.jdmp.node.ASemicolonValue;
import org.jdmp.core.grammar.jdmp.node.ASilentAssignment;
import org.jdmp.core.grammar.jdmp.node.ASilentStatement;
import org.jdmp.core.grammar.jdmp.node.AStringValue;
import org.jdmp.core.grammar.jdmp.node.ATermFactor;
import org.jdmp.core.grammar.jdmp.node.AValueMatrix;
import org.jdmp.core.grammar.jdmp.node.AVerboseAssignment;
import org.jdmp.core.grammar.jdmp.node.AVerboseStatement;
import org.jdmp.core.grammar.jdmp.node.Node;
import org.jdmp.core.grammar.jdmp.node.PArray;
import org.jdmp.core.grammar.jdmp.node.PColumn;
import org.jdmp.core.grammar.jdmp.node.PCommaValue;
import org.jdmp.core.grammar.jdmp.node.PExpression;
import org.jdmp.core.grammar.jdmp.node.PFactor;
import org.jdmp.core.grammar.jdmp.node.PIdentifier;
import org.jdmp.core.grammar.jdmp.node.PMatrix;
import org.jdmp.core.grammar.jdmp.node.PRow;
import org.jdmp.core.grammar.jdmp.node.PSemicolonRow;
import org.jdmp.core.grammar.jdmp.node.PSemicolonValue;
import org.jdmp.core.grammar.jdmp.node.PTerm;
import org.jdmp.core.grammar.jdmp.node.PValue;
import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.MatrixFactory;
import org.ujmp.core.enums.ValueType;
import org.ujmp.core.exceptions.MatrixException;

public class Translation extends DepthFirstAdapter {

	private Module module = null;

	private Result result = null;

	public Translation(Module module) {
		this.module = module;
	}

	private Variable getVariable(PIdentifier identifier) {
		String label = identifier.toString().trim();
		Variable v = module.getVariableList().get(label);
		if (v == null) {
			v = VariableFactory.labeledVariable(label);
			module.getVariableList().put(label, v);
		}
		return v;
	}

	private Matrix getMatrix(PFactor factor) {
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
		} else if (matrix instanceof AValueMatrix) {
			return getMatrix(((AValueMatrix) matrix).getValue());
		} else if (matrix instanceof ARowMatrix) {
			return getMatrix(((ARowMatrix) matrix).getRow());
		} else if (matrix instanceof AColumnMatrix) {
			return getMatrix(((AColumnMatrix) matrix).getColumn());
		} else if (matrix instanceof AArrayMatrix) {
			return getMatrix(((AArrayMatrix) matrix).getArray());
		} else {
			throw new MatrixException("unknown matrix type: " + matrix.getClass().getSimpleName());
		}
	}

	private Matrix getMatrix(PArray array) {
		AArray aArray = (AArray) array;

		ARow aRow = (ARow) aArray.getRow();

		int rows = aArray.getAdditionalRows().size() + 1;
		int columns = aRow.getAdditionalValues().size() + 1;

		for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
			aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
			columns = Math.max(columns, aRow.getAdditionalValues().size() + 1);
		}
		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, rows, columns);

		aRow = (ARow) aArray.getRow();
		PValue value = aRow.getValue();
		m.setObject(getValue(value), 0, 0);
		int c = 1;
		for (PCommaValue commaValue : aRow.getAdditionalValues()) {
			ACommaValue aCommaValue = (ACommaValue) commaValue;
			value = aCommaValue.getValue();
			m.setObject(getValue(value), 0, c++);
		}

		int r = 1;
		for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
			aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
			value = aRow.getValue();
			m.setObject(getValue(value), r, 0);
			c = 1;
			for (PCommaValue commaValue : aRow.getAdditionalValues()) {
				ACommaValue aCommaValue = (ACommaValue) commaValue;
				value = aCommaValue.getValue();
				m.setObject(getValue(value), r, c++);
			}
			r++;
		}

		return m;
	}

	private Matrix getMatrix(PRow row) {
		ARow aRow = (ARow) row;
		int columns = aRow.getAdditionalValues().size() + 1;
		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, 1, columns);
		m.setObject(getValue(aRow.getValue()), 0, 0);
		int i = 1;
		for (PCommaValue commaValue : aRow.getAdditionalValues()) {
			PValue value = ((ACommaValue) commaValue).getValue();
			m.setObject(getValue(value), 0, i++);
		}
		return m;
	}

	private Matrix getMatrix(PColumn column) {
		AColumn aColumn = (AColumn) column;
		int rows = aColumn.getAdditionalValues().size() + 1;
		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, rows, 1);
		m.setObject(getValue(aColumn.getValue()), 0, 0);
		int i = 1;
		for (PSemicolonValue semicolonValue : aColumn.getAdditionalValues()) {
			PValue value = ((ASemicolonValue) semicolonValue).getValue();
			m.setObject(getValue(value), i++, 0);
		}
		return m;
	}

	private Object getValue(PValue value) {
		if (value instanceof AIntegerValue) {
			return Integer.parseInt(value.toString().trim());
		} else if (value instanceof AFloatingPointValue) {
			return Double.parseDouble(value.toString().trim());
		} else if (value instanceof ABooleanValue) {
			return Boolean.parseBoolean(value.toString().trim());
		} else if (value instanceof ACharacterValue) {
			String s = value.toString().trim();
			return s.substring(1, s.length() - 1);
		} else if (value instanceof AStringValue) {
			String s = value.toString().trim();
			return s.substring(1, s.length() - 1);
		} else {
			throw new MatrixException("unknown value type: " + value.getClass().getSimpleName());
		}
	}

	private Matrix getMatrix(PValue value) {
		return MatrixFactory.linkToValue(getValue(value));
	}

	private Matrix getMatrix(PExpression expression) {
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
		System.out.println(v.getLabel() + " = " + m.toString());
	}

	@Override
	public void outASilentAssignment(ASilentAssignment node) {
		super.outASilentAssignment(node);
		System.out.println("   " + node.getIdentifier() + " = " + node.getExpression());
		Variable v = getVariable(node.getIdentifier());
		Matrix m = getMatrix(node.getExpression());
		v.addMatrix(m);
		result = new Result("");
		System.out.println(v.getLabel() + " = " + m.toString());
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

	public static void main(String[] args) throws Exception {
		Module m = ModuleFactory.emptyModule();
		m.execute("a=[ 5.034,'d',True;3,5,3 ; 7,8]");
	}

}
