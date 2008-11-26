package org.jdmp.core.script.jdmp;

import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;
import org.jdmp.core.script.jdmp.analysis.DepthFirstAdapter;
import org.jdmp.core.script.jdmp.node.AArray;
import org.jdmp.core.script.jdmp.node.AArrayAssignment;
import org.jdmp.core.script.jdmp.node.AArrayMatrix;
import org.jdmp.core.script.jdmp.node.ABooleanLiteral;
import org.jdmp.core.script.jdmp.node.ACharLiteral;
import org.jdmp.core.script.jdmp.node.AColumn;
import org.jdmp.core.script.jdmp.node.AColumnMatrix;
import org.jdmp.core.script.jdmp.node.ACommaValue;
import org.jdmp.core.script.jdmp.node.AEmptyFunction;
import org.jdmp.core.script.jdmp.node.AEmptyMatrix;
import org.jdmp.core.script.jdmp.node.AExpressionDivExpression;
import org.jdmp.core.script.jdmp.node.AExpressionMinusExpression;
import org.jdmp.core.script.jdmp.node.AExpressionMultExpression;
import org.jdmp.core.script.jdmp.node.AExpressionPlusExpression;
import org.jdmp.core.script.jdmp.node.AExpressionValue;
import org.jdmp.core.script.jdmp.node.AFloatingPointLiteral;
import org.jdmp.core.script.jdmp.node.AFunctionValue;
import org.jdmp.core.script.jdmp.node.AIdentifierAssignment;
import org.jdmp.core.script.jdmp.node.AIdentifierValue;
import org.jdmp.core.script.jdmp.node.AIntegerLiteral;
import org.jdmp.core.script.jdmp.node.ALiteralValue;
import org.jdmp.core.script.jdmp.node.AMatrixValue;
import org.jdmp.core.script.jdmp.node.AMinusExpression;
import org.jdmp.core.script.jdmp.node.AParameterFunction;
import org.jdmp.core.script.jdmp.node.APlusExpression;
import org.jdmp.core.script.jdmp.node.ARow;
import org.jdmp.core.script.jdmp.node.ARowMatrix;
import org.jdmp.core.script.jdmp.node.ASemicolonRow;
import org.jdmp.core.script.jdmp.node.ASemicolonValue;
import org.jdmp.core.script.jdmp.node.AStatement;
import org.jdmp.core.script.jdmp.node.AStringLiteral;
import org.jdmp.core.script.jdmp.node.AValueExpression;
import org.jdmp.core.script.jdmp.node.AValueMatrix;
import org.jdmp.core.script.jdmp.node.Node;
import org.jdmp.core.script.jdmp.node.PArray;
import org.jdmp.core.script.jdmp.node.PColumn;
import org.jdmp.core.script.jdmp.node.PCommaValue;
import org.jdmp.core.script.jdmp.node.PExpression;
import org.jdmp.core.script.jdmp.node.PFunction;
import org.jdmp.core.script.jdmp.node.PLiteral;
import org.jdmp.core.script.jdmp.node.PMatrix;
import org.jdmp.core.script.jdmp.node.PName;
import org.jdmp.core.script.jdmp.node.PRow;
import org.jdmp.core.script.jdmp.node.PSemicolonRow;
import org.jdmp.core.script.jdmp.node.PSemicolonValue;
import org.jdmp.core.script.jdmp.node.PValue;
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

	private Variable getVariable(PName identifier) {
		String label = identifier.toString().trim();
		Variable v = module.getVariableList().get(label);
		if (v == null) {
			v = VariableFactory.labeledVariable(label);
			module.getVariableList().put(label, v);
		}
		return v;
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
		PExpression expr = aRow.getExpression();
		m.setObject(getValue(expr), 0, 0);
		int c = 1;
		for (PCommaValue commaValue : aRow.getAdditionalValues()) {
			ACommaValue aCommaValue = (ACommaValue) commaValue;
			expr = aCommaValue.getExpression();
			m.setObject(getValue(expr), 0, c++);
		}

		int r = 1;
		for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
			aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
			expr = aRow.getExpression();
			m.setObject(getValue(expr), r, 0);
			c = 1;
			for (PCommaValue commaValue : aRow.getAdditionalValues()) {
				ACommaValue aCommaValue = (ACommaValue) commaValue;
				expr = aCommaValue.getExpression();
				m.setObject(getValue(expr), r, c++);
			}
			r++;
		}

		return m;
	}

	private Matrix getMatrix(PRow row) {
		ARow aRow = (ARow) row;
		int columns = aRow.getAdditionalValues().size() + 1;
		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, 1, columns);
		m.setObject(getValue(aRow.getExpression()), 0, 0);
		int i = 1;
		for (PCommaValue commaValue : aRow.getAdditionalValues()) {
			PExpression expr = ((ACommaValue) commaValue).getExpression();
			m.setObject(getValue(expr), 0, i++);
		}
		return m;
	}

	private Matrix getMatrix(PColumn column) {
		AColumn aColumn = (AColumn) column;
		int rows = aColumn.getAdditionalValues().size() + 1;
		Matrix m = MatrixFactory.zeros(ValueType.OBJECT, rows, 1);
		m.setObject(getValue(aColumn.getExpression()), 0, 0);
		int i = 1;
		for (PSemicolonValue semicolonValue : aColumn.getAdditionalValues()) {
			PExpression expr = ((ASemicolonValue) semicolonValue).getExpression();
			m.setObject(getValue(expr), i++, 0);
		}
		return m;
	}

	@Override
	public void defaultOut(Node node) {
		System.out.println(node.getClass().getSimpleName() + ": " + node);
	}

	public Result getResult() {
		return result;
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
		} else if (expression instanceof AMinusExpression) {
			AMinusExpression op = (AMinusExpression) expression;
			PExpression right = op.getRight();
			Matrix rightM = getMatrix(right);
			return rightM.times(-1);
		} else if (expression instanceof APlusExpression) {
			APlusExpression op = (APlusExpression) expression;
			PExpression right = op.getRight();
			Matrix rightM = getMatrix(right);
			return rightM;
		}

		MatrixException e = new MatrixException("Unknown expression type: "
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
		} else if (value instanceof AFunctionValue) {
			return getMatrix(((AFunctionValue) value).getFunction());
		}

		MatrixException e = new MatrixException("Unknown value type: "
				+ value.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Matrix getMatrix(PFunction function) {
		if (function instanceof AParameterFunction) {
			String name = ((AParameterFunction) function).getName().toString().trim();
			result = new Result("should not execute function: " + name);
			return MatrixFactory.rand(5, 5);
		} else if (function instanceof AEmptyFunction) {
			String name = ((AEmptyFunction) function).getName().toString().trim();
			result = new Result("should not execute function: " + name);
			return MatrixFactory.rand(5, 5);
		}
		MatrixException e = new MatrixException("Unknown function type: "
				+ function.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Matrix getMatrix(PMatrix matrix) {
		if (matrix instanceof AEmptyMatrix) {
			return MatrixFactory.emptyMatrix();
		} else if (matrix instanceof AValueMatrix) {
			return MatrixFactory.linkToValue(((AValueMatrix) matrix).getExpression());
		} else if (matrix instanceof ARowMatrix) {
			return getMatrix(((ARowMatrix) matrix).getRow());
		} else if (matrix instanceof AColumnMatrix) {
			return getMatrix(((AColumnMatrix) matrix).getColumn());
		} else if (matrix instanceof AArrayMatrix) {
			return getMatrix(((AArrayMatrix) matrix).getArray());
		}
		MatrixException e = new MatrixException("Unknown matrix type: "
				+ matrix.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getValue(PExpression expression) {
		Matrix m = getMatrix(expression);
		if (m.isScalar()) {
			return m.getObject(0, 0);
		} else {
			return m.getEuklideanValue();
		}
	}

	private Object getValue(PLiteral literal) {
		if (literal instanceof AIntegerLiteral) {
			return Long.parseLong(literal.toString().trim());
		} else if (literal instanceof AFloatingPointLiteral) {
			String s = literal.toString().trim();
			if ("NaN".equalsIgnoreCase(s)) {
				return Double.NaN;
			}
			if ("Inf".equalsIgnoreCase(s)) {
				return Double.POSITIVE_INFINITY;
			}
			if ("-Inf".equalsIgnoreCase(s)) {
				return Double.NEGATIVE_INFINITY;
			}
			return Double.parseDouble(s);
		} else if (literal instanceof ABooleanLiteral) {
			return Boolean.parseBoolean(literal.toString().trim());
		} else if (literal instanceof ACharLiteral) {
			String s = literal.toString().trim();
			return s.substring(1, s.length() - 1);
		} else if (literal instanceof AStringLiteral) {
			String s = literal.toString().trim();
			return s.substring(1, s.length() - 1);
		}

		MatrixException e = new MatrixException("Unknown literal type: "
				+ literal.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private boolean parseReservedKeywords(AStatement node) {
		PExpression expr = node.getExpression();

		if (expr instanceof AValueExpression) {
			AValueExpression avexpr = (AValueExpression) expr;
			if (avexpr.getValue() instanceof AIdentifierValue) {

				String name = avexpr.toString().trim();

				if ("exit".equals(name) || "quit".equals(name)) {
					System.exit(0);
				} else if ("clear".equals(name)) {
					module.clear();
					return true;
				} else if ("help".equals(name) || "info".equals(name) || "doc".equals(name)) {
					result = new Result("Please visit http://www.jdmp.org/ for more information.");
					return true;
				}

			}
		}

		return false;
	}

	@Override
	public void outAStatement(AStatement node) {
		boolean executed = parseReservedKeywords(node);

		if (executed) {
			if (result == null) {
				result = new Result("");
			}
			return;
		}

		PExpression expr = node.getExpression();

		if (expr instanceof AValueExpression) {
			AValueExpression avexpr = (AValueExpression) expr;
			if (avexpr.getValue() instanceof AIdentifierValue) {
				String id = avexpr.getValue().toString().trim();
				Variable v = module.getVariableList().get(id);
				Matrix m = null;
				if (v != null) {
					m = v.getMatrix();
					result = new Result(id + " = " + m);
				} else {
					MatrixException e = new MatrixException("Unknown object or command: " + id);
					result = new Result(e);
				}
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
