package org.jdmp.core.script.jdmp;

import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;
import org.jdmp.core.script.jdmp.analysis.DepthFirstAdapter;
import org.jdmp.core.script.jdmp.node.AArrayAssignment;
import org.jdmp.core.script.jdmp.node.ABooleanLiteral;
import org.jdmp.core.script.jdmp.node.ACharLiteral;
import org.jdmp.core.script.jdmp.node.AExpressionDivExpression;
import org.jdmp.core.script.jdmp.node.AExpressionMinusExpression;
import org.jdmp.core.script.jdmp.node.AExpressionMultExpression;
import org.jdmp.core.script.jdmp.node.AExpressionPlusExpression;
import org.jdmp.core.script.jdmp.node.AExpressionValue;
import org.jdmp.core.script.jdmp.node.AFloatingPointLiteral;
import org.jdmp.core.script.jdmp.node.AIdentifierAssignment;
import org.jdmp.core.script.jdmp.node.AIdentifierValue;
import org.jdmp.core.script.jdmp.node.AIntegerLiteral;
import org.jdmp.core.script.jdmp.node.ALiteralValue;
import org.jdmp.core.script.jdmp.node.AMatrixValue;
import org.jdmp.core.script.jdmp.node.AStatement;
import org.jdmp.core.script.jdmp.node.AStringLiteral;
import org.jdmp.core.script.jdmp.node.AValueExpression;
import org.jdmp.core.script.jdmp.node.Node;
import org.jdmp.core.script.jdmp.node.PExpression;
import org.jdmp.core.script.jdmp.node.PLiteral;
import org.jdmp.core.script.jdmp.node.PMatrix;
import org.jdmp.core.script.jdmp.node.PName;
import org.jdmp.core.script.jdmp.node.PValue;
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

	private Variable getVariable(PName identifier) {
		String label = identifier.toString().trim();
		Variable v = module.getVariableList().get(label);
		if (v == null) {
			v = VariableFactory.labeledVariable(label);
			module.getVariableList().put(label, v);
		}
		return v;
	}

	// private Matrix getMatrix(PFactor factor) {
	// if (factor instanceof ATermFactor) {
	// return getMatrix(((ATermFactor) factor).getTerm());
	// } else if (factor instanceof AMultiplicationFactor) {
	// return getMatrixProduct(((AMultiplicationFactor) factor).getFactor(),
	// ((AMultiplicationFactor) factor).getTerm());
	// } else if (factor instanceof ADivisionFactor) {
	// return getMatrixDivision(((ADivisionFactor) factor).getFactor(),
	// ((ADivisionFactor) factor).getTerm());
	// } else {
	// throw new MatrixException("unknown factor type: " +
	// factor.getClass().getSimpleName());
	// }
	// }
	//
	//	
	//
	//	
	//
	//
	// private Matrix getMatrix(PArray array) {
	// AArray aArray = (AArray) array;
	//
	// ARow aRow = (ARow) aArray.getRow();
	//
	// int rows = aArray.getAdditionalRows().size() + 1;
	// int columns = aRow.getAdditionalValues().size() + 1;
	//
	// for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
	// aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
	// columns = Math.max(columns, aRow.getAdditionalValues().size() + 1);
	// }
	// Matrix m = MatrixFactory.zeros(ValueType.OBJECT, rows, columns);
	//
	// aRow = (ARow) aArray.getRow();
	// PValue value = aRow.getValue();
	// m.setObject(getValue(value), 0, 0);
	// int c = 1;
	// for (PCommaValue commaValue : aRow.getAdditionalValues()) {
	// ACommaValue aCommaValue = (ACommaValue) commaValue;
	// value = aCommaValue.getValue();
	// m.setObject(getValue(value), 0, c++);
	// }
	//
	// int r = 1;
	// for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
	// aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
	// value = aRow.getValue();
	// m.setObject(getValue(value), r, 0);
	// c = 1;
	// for (PCommaValue commaValue : aRow.getAdditionalValues()) {
	// ACommaValue aCommaValue = (ACommaValue) commaValue;
	// value = aCommaValue.getValue();
	// m.setObject(getValue(value), r, c++);
	// }
	// r++;
	// }
	//
	// return m;
	// }
	//
	// private Matrix getMatrix(PRow row) {
	// ARow aRow = (ARow) row;
	// int columns = aRow.getAdditionalValues().size() + 1;
	// Matrix m = MatrixFactory.zeros(ValueType.OBJECT, 1, columns);
	// m.setObject(getValue(aRow.getValue()), 0, 0);
	// int i = 1;
	// for (PCommaValue commaValue : aRow.getAdditionalValues()) {
	// PValue value = ((ACommaValue) commaValue).getValue();
	// m.setObject(getValue(value), 0, i++);
	// }
	// return m;
	// }
	//
	// private Matrix getMatrix(PColumn column) {
	// AColumn aColumn = (AColumn) column;
	// int rows = aColumn.getAdditionalValues().size() + 1;
	// Matrix m = MatrixFactory.zeros(ValueType.OBJECT, rows, 1);
	// m.setObject(getValue(aColumn.getValue()), 0, 0);
	// int i = 1;
	// for (PSemicolonValue semicolonValue : aColumn.getAdditionalValues()) {
	// PValue value = ((ASemicolonValue) semicolonValue).getValue();
	// m.setObject(getValue(value), i++, 0);
	// }
	// return m;
	// }
	//
	// private Object getValue(PValue value) {
	// if (value instanceof AIntegerValue) {
	// return Integer.parseInt(value.toString().trim());
	// } else if (value instanceof AFloatingPointValue) {
	// return Double.parseDouble(value.toString().trim());
	// } else if (value instanceof ABooleanValue) {
	// return Boolean.parseBoolean(value.toString().trim());
	// } else if (value instanceof ACharacterValue) {
	// String s = value.toString().trim();
	// return s.substring(1, s.length() - 1);
	// } else if (value instanceof AStringValue) {
	// String s = value.toString().trim();
	// return s.substring(1, s.length() - 1);
	// } else {
	// throw new MatrixException("unknown value type: " +
	// value.getClass().getSimpleName());
	// }
	// }
	//
	// private Matrix getMatrix(PValue value) {
	// return MatrixFactory.linkToValue(getValue(value));
	// }

	@Override
	public void defaultOut(Node node) {
		System.out.println(node.getClass().getSimpleName() + ": " + node);
	}

	// @Override
	// public void outAVerboseAssignment(AVerboseAssignment node) {
	// super.outAVerboseAssignment(node);
	// System.out.println(" " + node.getIdentifier() + " = " +
	// node.getExpression());
	// Variable v = getVariable(node.getIdentifier());
	// Matrix m = getMatrix(node.getExpression());
	// v.addMatrix(m);
	// result = new Result(v.getLabel() + " = " + m.toString());
	// System.out.println(v.getLabel() + " = " + m.toString());
	// }
	//
	// @Override
	// public void outASilentAssignment(ASilentAssignment node) {
	// super.outASilentAssignment(node);
	// System.out.println(" " + node.getIdentifier() + " = " +
	// node.getExpression());
	// Variable v = getVariable(node.getIdentifier());
	// Matrix m = getMatrix(node.getExpression());
	// v.addMatrix(m);
	// result = new Result("");
	// System.out.println(v.getLabel() + " = " + m.toString());
	// }

	public Result getResult() {
		return result;
	}

	public static void main(String[] args) throws Exception {
		Module m = ModuleFactory.emptyModule();
		m.execute("a=5;");
		m.execute("b=5;");
		m.execute("c=a;");
		m.execute("c;");
	}

	@Override
	public void outAArrayAssignment(AArrayAssignment node) {

	}

	@Override
	public void outAIdentifierAssignment(AIdentifierAssignment node) {
		Matrix m = getMatrix(node.getExpression());
		if (m != null) {
			Variable v = getVariable(node.getName());
			v.addMatrix(m);
			result = new Result(v.getLabel() + " = " + m.toString());
			System.out.println(v.getLabel() + " = " + m.toString());
		}
	}

	/*
	 * TODO: many expressions are not parsed correctly
	 */
	private Matrix getMatrix(PExpression expression) {
		if (expression instanceof AValueExpression) {
			return getMatrix(((AValueExpression) expression).getValue());
		} else if (expression instanceof AExpressionPlusExpression) {
			AExpressionPlusExpression op = (AExpressionPlusExpression) expression;
			PValue left = op.getLeft();
			PExpression right = op.getRight();
			Matrix leftM = getMatrix(left);
			Matrix rightM = getMatrix(right);
			return leftM.plus(rightM);
		} else if (expression instanceof AExpressionMinusExpression) {
			AExpressionMinusExpression op = (AExpressionMinusExpression) expression;
			PValue left = op.getLeft();
			PExpression right = op.getRight();
			Matrix leftM = getMatrix(left);
			Matrix rightM = getMatrix(right);
			return leftM.minus(rightM);
		} else if (expression instanceof AExpressionMultExpression) {
			AExpressionMultExpression op = (AExpressionMultExpression) expression;
			PValue left = op.getLeft();
			PExpression right = op.getRight();
			Matrix leftM = getMatrix(left);
			Matrix rightM = getMatrix(right);
			return leftM.mtimes(rightM);
		} else if (expression instanceof AExpressionDivExpression) {
			AExpressionDivExpression op = (AExpressionDivExpression) expression;
			PValue left = op.getLeft();
			PExpression right = op.getRight();
			Matrix leftM = getMatrix(left);
			Matrix rightM = getMatrix(right);
			return leftM.divide(rightM);
		}

		MatrixException e = new MatrixException("unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Matrix getMatrix(PValue value) {
		if (value instanceof ALiteralValue) {
			return MatrixFactory.linkToValue(getValue(((ALiteralValue) value).getLiteral()));
		} else if (value instanceof AIdentifierValue) {
			String name = ((AIdentifierValue) value).getName().toString().trim();
			Variable v = module.getVariableList().get(name);
			return v.getMatrix();
		} else if (value instanceof AMatrixValue) {
			return getMatrix(((AMatrixValue) value).getMatrix());
		} else if (value instanceof AExpressionValue) {
			return getMatrix(((AExpressionValue) value).getExpression());
		}

		MatrixException e = new MatrixException("unknown value type: "
				+ value.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Matrix getMatrix(PMatrix matrix) {
		MatrixException e = new MatrixException("unknown matrix type: "
				+ matrix.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getValue(PLiteral literal) {
		if (literal instanceof AIntegerLiteral) {
			return Long.parseLong(literal.toString().trim());
		} else if (literal instanceof AFloatingPointLiteral) {
			return Double.parseDouble(literal.toString().trim());
		} else if (literal instanceof ABooleanLiteral) {
			return Boolean.parseBoolean(literal.toString().trim());
		} else if (literal instanceof ACharLiteral) {
			String s = literal.toString().trim();
			return s.substring(1, s.length() - 1);
		} else if (literal instanceof AStringLiteral) {
			String s = literal.toString().trim();
			return s.substring(1, s.length() - 1);
		}

		MatrixException e = new MatrixException("unknown literal type: "
				+ literal.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	@Override
	public void outAStatement(AStatement node) {
		PExpression expr = node.getExpression();

		if (expr instanceof AValueExpression) {
			AValueExpression avexpr = (AValueExpression) expr;
			if (avexpr.getValue() instanceof AIdentifierValue) {
				String id = avexpr.getValue().toString().trim();
				Variable v = module.getVariableList().get(id);
				Matrix m = null;
				if (v != null) {
					m = v.getMatrix();
				}
				result = new Result(id + " = " + m);
				return;
			}
		}

		String label = "ans";
		Variable v = module.getVariableList().get(label);
		if (v == null) {
			v = VariableFactory.labeledVariable(label);
			module.getVariableList().put(label, v);
		}
		Matrix m = getMatrix(node.getExpression());
		v.addMatrix(m);
		result = new Result(v.getLabel() + " = " + m);
		System.out.println(v.getLabel() + " = " + m);

	}

}
