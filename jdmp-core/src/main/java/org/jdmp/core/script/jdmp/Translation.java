package org.jdmp.core.script.jdmp;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jdmp.core.algorithm.Algorithm;
import org.jdmp.core.algorithm.AlgorithmMapping;
import org.jdmp.core.dataset.DataSet;
import org.jdmp.core.dataset.DataSetFactory;
import org.jdmp.core.dataset.RegressionDataSet;
import org.jdmp.core.module.Module;
import org.jdmp.core.module.ModuleFactory;
import org.jdmp.core.sample.Sample;
import org.jdmp.core.sample.SampleFactory;
import org.jdmp.core.script.Result;
import org.jdmp.core.script.jdmp.analysis.DepthFirstAdapter;
import org.jdmp.core.script.jdmp.node.AAndLevel7;
import org.jdmp.core.script.jdmp.node.AArgumentListArgumentList;
import org.jdmp.core.script.jdmp.node.AArray;
import org.jdmp.core.script.jdmp.node.AArrayAssignment;
import org.jdmp.core.script.jdmp.node.AArrayMatrix;
import org.jdmp.core.script.jdmp.node.ABitComplementLevel2;
import org.jdmp.core.script.jdmp.node.ABooleanLiteral;
import org.jdmp.core.script.jdmp.node.AColumn;
import org.jdmp.core.script.jdmp.node.AColumnMatrix;
import org.jdmp.core.script.jdmp.node.ACommaValue;
import org.jdmp.core.script.jdmp.node.AComplementLevel2;
import org.jdmp.core.script.jdmp.node.ADotLdivLevel3;
import org.jdmp.core.script.jdmp.node.ADotMultLevel3;
import org.jdmp.core.script.jdmp.node.ADotPowerLevel1;
import org.jdmp.core.script.jdmp.node.ADotRdivLevel3;
import org.jdmp.core.script.jdmp.node.ADotTransposeLevel1;
import org.jdmp.core.script.jdmp.node.AEmptyFunction;
import org.jdmp.core.script.jdmp.node.AEmptyMatrix;
import org.jdmp.core.script.jdmp.node.AEqLevel6;
import org.jdmp.core.script.jdmp.node.AExpressionArgumentList;
import org.jdmp.core.script.jdmp.node.AExpressionLevel0;
import org.jdmp.core.script.jdmp.node.AFloatingPointLiteral;
import org.jdmp.core.script.jdmp.node.AFunctionLevel0;
import org.jdmp.core.script.jdmp.node.AGtLevel6;
import org.jdmp.core.script.jdmp.node.AGteqLevel6;
import org.jdmp.core.script.jdmp.node.AIdentifierAssignment;
import org.jdmp.core.script.jdmp.node.AIdentifierLevel0;
import org.jdmp.core.script.jdmp.node.AIntegerLiteral;
import org.jdmp.core.script.jdmp.node.ALdivLevel3;
import org.jdmp.core.script.jdmp.node.ALevel0Level1;
import org.jdmp.core.script.jdmp.node.ALevel10Expression;
import org.jdmp.core.script.jdmp.node.ALevel1Level2;
import org.jdmp.core.script.jdmp.node.ALevel2Level3;
import org.jdmp.core.script.jdmp.node.ALevel3Level4;
import org.jdmp.core.script.jdmp.node.ALevel4Level5;
import org.jdmp.core.script.jdmp.node.ALevel5Level6;
import org.jdmp.core.script.jdmp.node.ALevel6Level7;
import org.jdmp.core.script.jdmp.node.ALevel7Level8;
import org.jdmp.core.script.jdmp.node.ALevel8Level9;
import org.jdmp.core.script.jdmp.node.ALevel9Level10;
import org.jdmp.core.script.jdmp.node.ALiteralLevel0;
import org.jdmp.core.script.jdmp.node.ALogicalAndLevel9;
import org.jdmp.core.script.jdmp.node.ALogicalOrLevel10;
import org.jdmp.core.script.jdmp.node.ALtLevel6;
import org.jdmp.core.script.jdmp.node.ALteqLevel6;
import org.jdmp.core.script.jdmp.node.AMatrixLevel0;
import org.jdmp.core.script.jdmp.node.AMinusLevel2;
import org.jdmp.core.script.jdmp.node.AMinusLevel4;
import org.jdmp.core.script.jdmp.node.AMultLevel3;
import org.jdmp.core.script.jdmp.node.ANeqLevel6;
import org.jdmp.core.script.jdmp.node.AOrLevel8;
import org.jdmp.core.script.jdmp.node.AParameterFunction;
import org.jdmp.core.script.jdmp.node.APlusLevel2;
import org.jdmp.core.script.jdmp.node.APlusLevel4;
import org.jdmp.core.script.jdmp.node.APowerLevel1;
import org.jdmp.core.script.jdmp.node.AQualifiedName;
import org.jdmp.core.script.jdmp.node.ARangeLevel5;
import org.jdmp.core.script.jdmp.node.ARdivLevel3;
import org.jdmp.core.script.jdmp.node.ARow;
import org.jdmp.core.script.jdmp.node.ARowMatrix;
import org.jdmp.core.script.jdmp.node.ASemicolonRow;
import org.jdmp.core.script.jdmp.node.ASemicolonValue;
import org.jdmp.core.script.jdmp.node.ASimpleName;
import org.jdmp.core.script.jdmp.node.AStatement;
import org.jdmp.core.script.jdmp.node.AStringLiteral;
import org.jdmp.core.script.jdmp.node.ATransposeLevel1;
import org.jdmp.core.script.jdmp.node.AValueMatrix;
import org.jdmp.core.script.jdmp.node.Node;
import org.jdmp.core.script.jdmp.node.PArgumentList;
import org.jdmp.core.script.jdmp.node.PArray;
import org.jdmp.core.script.jdmp.node.PColumn;
import org.jdmp.core.script.jdmp.node.PCommaValue;
import org.jdmp.core.script.jdmp.node.PExpression;
import org.jdmp.core.script.jdmp.node.PFunction;
import org.jdmp.core.script.jdmp.node.PLevel0;
import org.jdmp.core.script.jdmp.node.PLevel1;
import org.jdmp.core.script.jdmp.node.PLevel10;
import org.jdmp.core.script.jdmp.node.PLevel2;
import org.jdmp.core.script.jdmp.node.PLevel3;
import org.jdmp.core.script.jdmp.node.PLevel4;
import org.jdmp.core.script.jdmp.node.PLevel5;
import org.jdmp.core.script.jdmp.node.PLevel6;
import org.jdmp.core.script.jdmp.node.PLevel7;
import org.jdmp.core.script.jdmp.node.PLevel8;
import org.jdmp.core.script.jdmp.node.PLevel9;
import org.jdmp.core.script.jdmp.node.PLiteral;
import org.jdmp.core.script.jdmp.node.PMatrix;
import org.jdmp.core.script.jdmp.node.PName;
import org.jdmp.core.script.jdmp.node.PRow;
import org.jdmp.core.script.jdmp.node.PSemicolonRow;
import org.jdmp.core.script.jdmp.node.PSemicolonValue;
import org.jdmp.core.variable.Variable;
import org.jdmp.core.variable.VariableFactory;
import org.ujmp.core.Matrix;
import org.ujmp.core.calculation.Calculation.Ret;
import org.ujmp.core.exceptions.MatrixException;
import org.ujmp.core.objectmatrix.DenseObjectMatrix2D;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.StringUtil;

public class Translation extends DepthFirstAdapter {

	public static final String ANS = "ans";

	private Module module = null;

	private Result result = null;

	private final boolean ignoreNaN = true;

	public Translation(Module module) {
		this.module = module;
	}

	private Variable getVariable(PName identifier) {
		String label = identifier.toString().trim();
		Variable v = module.getVariables().get(label);
		if (v == null) {
			v = VariableFactory.labeledVariable(label);
			module.getVariables().put(label, v);
		}
		return v;
	}

	private Matrix getMatrix(PArray array) throws Exception {
		AArray aArray = (AArray) array;

		ARow aRow = (ARow) aArray.getRow();

		int rows = aArray.getAdditionalRows().size() + 1;
		int columns = aRow.getAdditionalValues().size() + 1;

		for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
			aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
			columns = Math.max(columns, aRow.getAdditionalValues().size() + 1);
		}
		Matrix m = DenseObjectMatrix2D.Factory.zeros(rows, columns);

		aRow = (ARow) aArray.getRow();
		PExpression expr = aRow.getExpression();
		m.setAsObject(getSingleValue(expr), 0, 0);
		int c = 1;
		for (PCommaValue commaValue : aRow.getAdditionalValues()) {
			ACommaValue aCommaValue = (ACommaValue) commaValue;
			expr = aCommaValue.getExpression();
			m.setAsObject(getSingleValue(expr), 0, c++);
		}

		int r = 1;
		for (PSemicolonRow semicolonRow : aArray.getAdditionalRows()) {
			aRow = (ARow) ((ASemicolonRow) semicolonRow).getRow();
			expr = aRow.getExpression();
			m.setAsObject(getSingleValue(expr), r, 0);
			c = 1;
			for (PCommaValue commaValue : aRow.getAdditionalValues()) {
				ACommaValue aCommaValue = (ACommaValue) commaValue;
				expr = aCommaValue.getExpression();
				m.setAsObject(getSingleValue(expr), r, c++);
			}
			r++;
		}

		return m;
	}

	private Matrix getMatrix(PRow row) throws Exception {
		ARow aRow = (ARow) row;
		int columns = aRow.getAdditionalValues().size() + 1;
		Matrix m = DenseObjectMatrix2D.Factory.zeros(1, columns);
		m.setAsObject(getSingleValue(aRow.getExpression()), 0, 0);
		int i = 1;
		for (PCommaValue commaValue : aRow.getAdditionalValues()) {
			PExpression expr = ((ACommaValue) commaValue).getExpression();
			m.setAsObject(getSingleValue(expr), 0, i++);
		}
		return m;
	}

	private Matrix getMatrix(PColumn column) throws Exception {
		AColumn aColumn = (AColumn) column;
		int rows = aColumn.getAdditionalValues().size() + 1;
		Matrix m = DenseObjectMatrix2D.Factory.zeros(rows, 1);
		m.setAsObject(getSingleValue(aColumn.getExpression()), 0, 0);
		int i = 1;
		for (PSemicolonValue semicolonValue : aColumn.getAdditionalValues()) {
			PExpression expr = ((ASemicolonValue) semicolonValue).getExpression();
			m.setAsObject(getSingleValue(expr), i++, 0);
		}
		return m;
	}

	public void defaultOut(Node node) {
		System.out.println(node.getClass().getSimpleName() + ": " + node);
	}

	public Result getResult() {
		return result;
	}

	public void outAArrayAssignment(AArrayAssignment node) {
		Exception e = new MatrixException("array assignments are not supported yet.");
		result = new Result(e);
		e.printStackTrace();
	}

	public void outAIdentifierAssignment(AIdentifierAssignment node) {
		try {
			String id = node.getName().toString().trim();
			Object o = getObject(node.getExpression());
			if (o instanceof Module) {
				Module m = (Module) o;
				if (!ANS.equals(id)) {
					module.getModules().put(id, m);
				}
				result = new Result(id, m);
			} else if (o instanceof DataSet) {
				DataSet ds = (DataSet) o;
				if (!ANS.equals(id)) {
					module.getDataSets().put(id, ds);
				}
				result = new Result(id, ds);
			} else if (o instanceof Sample) {
				Sample s = (Sample) o;
				if (!ANS.equals(id)) {
					module.getSamples().put(id, s);
				}
				result = new Result(id, s);
			} else if (o instanceof Variable) {
				Variable v = (Variable) o;
				if (!ANS.equals(id)) {
					module.getVariables().put(id, v);
				}
				result = new Result(id, v);
			} else if (o instanceof Algorithm) {
				Algorithm a = (Algorithm) o;
				if (!ANS.equals(id)) {
					module.getAlgorithms().put(id, a);
				}
				result = new Result(id, a);
			} else {
				Matrix m = MathUtil.getMatrix(o);
				if (m != null) {
					Variable v = getVariable(node.getName());
					v.addMatrix(m);
					result = new Result(v.getLabel(), m);
					System.out.println(result.toString());
				}
			}
		} catch (Exception e) {
			result = new Result(e);
			e.printStackTrace();
		}
	}

	private Object getObject(PExpression expression) throws Exception {
		if (expression instanceof ALevel10Expression) {
			return getObject(((ALevel10Expression) expression).getLevel10());
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel10 expression) throws Exception {
		if (expression instanceof ALevel9Level10) {
			return getObject(((ALevel9Level10) expression).getLevel9());
		} else if (expression instanceof ALogicalOrLevel10) {
			ALogicalOrLevel10 exp = (ALogicalOrLevel10) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.or(Ret.NEW, right);
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel9 expression) throws Exception {
		if (expression instanceof ALevel8Level9) {
			return getObject(((ALevel8Level9) expression).getLevel8());
		} else if (expression instanceof ALogicalAndLevel9) {
			ALogicalAndLevel9 exp = (ALogicalAndLevel9) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.and(Ret.NEW, right);
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel8 expression) throws Exception {
		if (expression instanceof ALevel7Level8) {
			return getObject(((ALevel7Level8) expression).getLevel7());
		} else if (expression instanceof AOrLevel8) {
			// TODO
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel7 expression) throws Exception {
		if (expression instanceof ALevel6Level7) {
			return getObject(((ALevel6Level7) expression).getLevel6());
		} else if (expression instanceof AAndLevel7) {
			// TODO
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel6 expression) throws Exception {
		if (expression instanceof ALevel5Level6) {
			return getObject(((ALevel5Level6) expression).getLevel5());
		} else if (expression instanceof AEqLevel6) {
			AEqLevel6 exp = (AEqLevel6) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.eq(Ret.NEW, right);
		} else if (expression instanceof ANeqLevel6) {
			ANeqLevel6 exp = (ANeqLevel6) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.ne(Ret.NEW, right);
		} else if (expression instanceof AGtLevel6) {
			AGtLevel6 exp = (AGtLevel6) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.gt(Ret.NEW, right);
		} else if (expression instanceof ALtLevel6) {
			ALtLevel6 exp = (ALtLevel6) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.lt(Ret.NEW, right);
		} else if (expression instanceof AGteqLevel6) {
			AGteqLevel6 exp = (AGteqLevel6) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.ge(Ret.NEW, right);
		} else if (expression instanceof ALteqLevel6) {
			ALteqLevel6 exp = (ALteqLevel6) expression;
			Matrix left = MathUtil.getMatrix(getObject(exp.getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(exp.getRight()));
			return left.le(Ret.NEW, right);
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel5 expression) throws Exception {
		if (expression instanceof ALevel4Level5) {
			return getObject(((ALevel4Level5) expression).getLevel4());
		} else if (expression instanceof ARangeLevel5) {
			// TODO
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ expression.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel4 calculation) throws Exception {
		if (calculation instanceof ALevel3Level4) {
			return getObject(((ALevel3Level4) calculation).getLevel3());
		} else if (calculation instanceof APlusLevel4) {
			APlusLevel4 op = (APlusLevel4) calculation;
			PLevel4 left = op.getLeft();
			PLevel3 right = op.getRight();
			Matrix leftM = MathUtil.getMatrix(getObject(left));
			Matrix rightM = MathUtil.getMatrix(getObject(right));
			return leftM.plus(Ret.NEW, ignoreNaN, rightM);
		} else if (calculation instanceof AMinusLevel4) {
			AMinusLevel4 op = (AMinusLevel4) calculation;
			PLevel4 left = op.getLeft();
			PLevel3 right = op.getRight();
			Matrix leftM = MathUtil.getMatrix(getObject(left));
			Matrix rightM = MathUtil.getMatrix(getObject(right));
			return leftM.minus(Ret.NEW, ignoreNaN, rightM);
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ calculation.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel3 term) throws Exception {
		if (term instanceof ALevel2Level3) {
			return getObject(((ALevel2Level3) term).getLevel2());
		} else if (term instanceof AMultLevel3) {
			Matrix left = MathUtil.getMatrix(getObject(((AMultLevel3) term).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((AMultLevel3) term).getRight()));
			return left.mtimes(Ret.NEW, ignoreNaN, right);
		} else if (term instanceof ADotMultLevel3) {
			Matrix left = MathUtil.getMatrix(getObject(((ADotMultLevel3) term).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((ADotMultLevel3) term).getRight()));
			return left.times(Ret.NEW, ignoreNaN, right);
		} else if (term instanceof ARdivLevel3) {
			Matrix left = MathUtil.getMatrix(getObject(((ARdivLevel3) term).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((ARdivLevel3) term).getRight()));
			return left.divide(Ret.NEW, ignoreNaN, right);
		} else if (term instanceof ADotRdivLevel3) {
			Matrix left = MathUtil.getMatrix(getObject(((ADotRdivLevel3) term).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((ADotRdivLevel3) term).getRight()));
			return left.divide(Ret.NEW, ignoreNaN, right);
		} else if (term instanceof ALdivLevel3) {
			Matrix left = MathUtil.getMatrix(getObject(((ALdivLevel3) term).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((ALdivLevel3) term).getRight()));
			// TODO
		} else if (term instanceof ADotLdivLevel3) {
			Matrix left = MathUtil.getMatrix(getObject(((ADotLdivLevel3) term).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((ADotLdivLevel3) term).getRight()));
			// TODO
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ term.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel0 factor) throws Exception {
		if (factor instanceof ALiteralLevel0) {
			return Matrix.Factory.linkToValue(getValue(((ALiteralLevel0) factor).getLiteral()));
		} else if (factor instanceof AMatrixLevel0) {
			return getMatrix(((AMatrixLevel0) factor).getMatrix());
		} else if (factor instanceof AExpressionLevel0) {
			return getObject(((AExpressionLevel0) factor).getExpression());
		} else if (factor instanceof AFunctionLevel0) {
			return getObject(((AFunctionLevel0) factor).getFunction());
		} else if (factor instanceof AIdentifierLevel0) {
			String name = ((AIdentifierLevel0) factor).getName().toString().trim();

			if (ANS.equals(name) && module.getVariables().get(ANS) == null) {
				module.getVariables().put(ANS, VariableFactory.labeledVariable(ANS));
			}

			Variable v = module.getVariables().get(name);
			if (v != null) {
				return v.getMatrix();
			}

			DataSet ds = module.getDataSets().get(name);
			if (ds != null) {
				return ds;
			}

			Module m = module.getModules().get(name);
			if (m != null) {
				return m;
			}

			Sample s = module.getSamples().get(name);
			if (s != null) {
				return s;
			}

			Algorithm a = module.getAlgorithms().get(name);
			if (a != null) {
				return a;
			}

			a = getAlgorithm(name);
			if (a != null) {
				return executeAlgorithm(a, null);
			}

			MatrixException e = new MatrixException("Unknown object or command: " + name);
			result = new Result(e);
			throw e;

		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ factor.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel2 factor) throws Exception {
		if (factor instanceof ALevel1Level2) {
			return getObject(((ALevel1Level2) factor).getLevel1());
		} else if (factor instanceof AMinusLevel2) {
			Matrix m = MathUtil.getMatrix(getObject(((AMinusLevel2) factor).getLevel1()));
			return m.times(-1);
		} else if (factor instanceof APlusLevel2) {
			return getObject(((APlusLevel2) factor).getLevel1());
		} else if (factor instanceof AComplementLevel2) {
			Matrix m = MathUtil.getMatrix(getObject(((AComplementLevel2) factor).getLevel1()));
			return m.not(Ret.NEW);
		} else if (factor instanceof ABitComplementLevel2) {
			// TODO
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ factor.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(PLevel1 factor) throws Exception {
		if (factor instanceof ALevel0Level1) {
			return getObject(((ALevel0Level1) factor).getLevel0());
		} else if (factor instanceof APowerLevel1) {
			Matrix left = MathUtil.getMatrix(getObject(((APowerLevel1) factor).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((APowerLevel1) factor).getRight()));
			// TODO
		} else if (factor instanceof ADotPowerLevel1) {
			Matrix left = MathUtil.getMatrix(getObject(((ADotPowerLevel1) factor).getLeft()));
			Matrix right = MathUtil.getMatrix(getObject(((ADotPowerLevel1) factor).getRight()));
			return left.power(Ret.NEW, right);
		} else if (factor instanceof ATransposeLevel1) {
			Matrix m = MathUtil.getMatrix(getObject(((ATransposeLevel1) factor).getLevel0()));
			return m.transpose();
		} else if (factor instanceof ADotTransposeLevel1) {
			Matrix m = MathUtil.getMatrix(getObject(((ADotTransposeLevel1) factor).getLevel0()));
			return m.transpose();
		}
		MatrixException e = new MatrixException("Unknown expression type: "
				+ factor.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	public Object executeFunction(PName name, PArgumentList arguments) throws Exception {
		if (name instanceof ASimpleName) {
			ASimpleName sn = (ASimpleName) name;
			String id = sn.getIdentifier().toString().trim();
			Algorithm algorithm = getAlgorithm(id);
			if (algorithm == null) {
				MatrixException e = new MatrixException("Unknown algorithm: " + id);
				result = new Result(e);
				throw e;
			}
			return executeAlgorithm(algorithm, arguments);
		} else if (name instanceof AQualifiedName) {
			AQualifiedName qn = (AQualifiedName) name;
			String id = qn.getName().toString().trim();
			Object object = getObject(id);
			if (object == null) {

				// try other classes
				String methodName = qn.getIdentifier().toString().trim();
				Class<?> c = null;
				if ("VariableFactory".equals(id)) {
					c = VariableFactory.class;
				} else if ("SampleFactory".equals(id)) {
					c = SampleFactory.class;
				} else if ("ModuleFactory".equals(id)) {
					c = ModuleFactory.class;
				} else if ("DataSetFactory".equals(id)) {
					c = DataSetFactory.class;
				}
				if (c != null) {
					return executeMethod(c, null, methodName, arguments);
				}

				MatrixException e = new MatrixException("Unknown identifier: " + id);
				result = new Result(e);
				throw e;
			}
			return executeMethod(object.getClass(), object, qn.getIdentifier().toString().trim(),
					arguments);
		}

		MatrixException e = new MatrixException("Unknown function: "
				+ name.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private List<Object> getArgumentsAsObjects(PArgumentList arguments) throws Exception {
		List<Object> obj = new ArrayList<Object>();
		if (arguments == null) {
			return obj;
		}
		if (arguments instanceof AExpressionArgumentList) {
			PExpression expr = ((AExpressionArgumentList) arguments).getExpression();
			obj.add(getObject(expr));
			return obj;
		} else if (arguments instanceof AArgumentListArgumentList) {
			PExpression expr = ((AArgumentListArgumentList) arguments).getExpression();
			obj.addAll(getArgumentsAsObjects(((AArgumentListArgumentList) arguments)
					.getArgumentList()));
			obj.add(getObject(expr));
			return obj;
		}
		MatrixException e = new MatrixException("Unknown arguments: "
				+ arguments.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object executeAlgorithm(Algorithm algorithm, PArgumentList arguments) throws Exception {
		List<Object> matrices = getArgumentsAsObjects(arguments);
		Map<String, Object> ret = algorithm.calculateObjects(matrices);
		if (ret.isEmpty()) {
			return null;
		} else {
			return ret.values().iterator().next();
		}
	}

	private Algorithm getAlgorithm(String id) throws Exception {
		Class<?> c = null;

		String cname = AlgorithmMapping.get(id);
		if (cname != null) {
			try {
				c = Class.forName(cname);
			} catch (Exception e) {
			}
		}

		// no algorithm found, maybe its a module method
		if (c == null) {
			return null;
		}

		Constructor<?> constr = c.getConstructor(Variable.VARIABLEARRAY);
		Algorithm algorithm = (Algorithm) constr.newInstance(new Object[] { new Variable[] {} });
		return algorithm;
	}

	private Object executeMethod(Class<?> c, Object object, String name, PArgumentList argumentList)
			throws Exception {
		List<Object> matrices = getArgumentsAsObjects(argumentList);
		for (Method method : c.getMethods()) {
			if (method.getName().equals(name)
					&& method.getParameterTypes().length == matrices.size()) {
				Object[] arguments = convertObjects(matrices, method.getParameterTypes());
				Object o = method.invoke(object, arguments);
				return o;
			}
		}
		MatrixException e = new MatrixException("Unknown method: " + name);
		result = new Result(e);
		throw e;
	}

	private Object[] convertObjects(List<Object> matrices, Class<?>[] classes) {
		List<Object> objects = new ArrayList<Object>();
		for (int i = 0; i < classes.length; i++) {
			Class<?> c = classes[i];
			Object m = matrices.get(i);
			Object o = convertObject(m, c);
			objects.add(o);
		}
		return objects.toArray();
	}

	private Object convertObject(Object m, Class<?> c) {
		if (c == Matrix.class) {
			return MathUtil.getMatrix(m);
		} else if (c == Object.class) {
			return m;
		} else if (c == Integer.TYPE) {
			return MathUtil.getInt(m);
		} else if (c == Double.TYPE) {
			return MathUtil.getDouble(m);
		} else if (c == Long.TYPE) {
			return MathUtil.getLong(m);
		} else if (c == Boolean.TYPE) {
			return MathUtil.getBoolean(m);
		} else if (c == String.class) {
			return StringUtil.convert(m);
		} else if (c == RegressionDataSet.class) {
			return m;
		}
		MatrixException e = new MatrixException("cannot convert to desired object type "
				+ c.getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Object getObject(String name) {
		Object o = module.getVariables().get(name);
		if (o != null) {
			return ((Variable) o).getMatrix();
		}
		o = module.getDataSets().get(name);
		if (o != null) {
			return o;
		}
		o = module.getAlgorithms().get(name);
		if (o != null) {
			return o;
		}
		o = module.getSamples().get(name);
		if (o != null) {
			return o;
		}
		o = module.getModules().get(name);
		if (o != null) {
			return o;
		}
		return o;
	}

	private Object getObject(PFunction function) throws Exception {
		if (function instanceof AParameterFunction) {
			return executeFunction(((AParameterFunction) function).getName(),
					((AParameterFunction) function).getArgumentList());
		} else if (function instanceof AEmptyFunction) {
			return executeFunction(((AEmptyFunction) function).getName(), null);
		}
		MatrixException e = new MatrixException("Unknown function type: "
				+ function.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private Matrix getMatrix(PMatrix matrix) throws Exception {
		if (matrix instanceof AEmptyMatrix) {
			return Matrix.Factory.emptyMatrix();
		} else if (matrix instanceof AValueMatrix) {
			return Matrix.Factory.linkToValue(((AValueMatrix) matrix).getExpression());
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

	private Object getSingleValue(PExpression expression) throws Exception {
		Matrix m = MathUtil.getMatrix(getObject(expression));
		if (m.isScalar()) {
			return m.getAsObject(0, 0);
		} else {
			return m.doubleValue();
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
		} else if (literal instanceof AStringLiteral) {
			String s = literal.toString().trim();
			return s.substring(1, s.length() - 1);
		}

		MatrixException e = new MatrixException("Unknown literal type: "
				+ literal.getClass().getSimpleName());
		result = new Result(e);
		throw e;
	}

	private String getLiteral(PExpression expr) {
		if (expr instanceof ALevel10Expression) {
			return getLiteral(((ALevel10Expression) expr).getLevel10());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel10 expr) {
		if (expr instanceof ALevel9Level10) {
			return getLiteral(((ALevel9Level10) expr).getLevel9());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel9 expr) {
		if (expr instanceof ALevel8Level9) {
			return getLiteral(((ALevel8Level9) expr).getLevel8());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel8 expr) {
		if (expr instanceof ALevel7Level8) {
			return getLiteral(((ALevel7Level8) expr).getLevel7());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel7 expr) {
		if (expr instanceof ALevel6Level7) {
			return getLiteral(((ALevel6Level7) expr).getLevel6());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel6 expr) {
		if (expr instanceof ALevel5Level6) {
			return getLiteral(((ALevel5Level6) expr).getLevel5());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel5 expr) {
		if (expr instanceof ALevel4Level5) {
			return getLiteral(((ALevel4Level5) expr).getLevel4());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel4 expr) {
		if (expr instanceof ALevel3Level4) {
			return getLiteral(((ALevel3Level4) expr).getLevel3());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel3 expr) {
		if (expr instanceof ALevel2Level3) {
			return getLiteral(((ALevel2Level3) expr).getLevel2());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel2 expr) {
		if (expr instanceof ALevel1Level2) {
			return getLiteral(((ALevel1Level2) expr).getLevel1());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel1 expr) {
		if (expr instanceof ALevel0Level1) {
			return getLiteral(((ALevel0Level1) expr).getLevel0());
		} else {
			return null;
		}
	}

	private String getLiteral(PLevel0 expr) {
		if (expr instanceof ALiteralLevel0) {
			String s = expr.toString().trim();
			if ("true".equalsIgnoreCase(s)) {
				return null;
			}
			if ("false".equalsIgnoreCase(s)) {
				return null;
			}
			return s;
		} else if (expr instanceof AIdentifierLevel0) {
			String s = expr.toString().trim();
			if ("true".equalsIgnoreCase(s)) {
				return null;
			}
			if ("false".equalsIgnoreCase(s)) {
				return null;
			}
			return s;
		} else {
			return null;
		}
	}

	public void outAStatement(AStatement node) {
		try {
			// handle already known objects
			String id = getLiteral(node.getExpression());
			if (id != null) {
				Variable v = module.getVariables().get(id);
				if (v != null) {
					result = new Result(id, v.getMatrix());
					return;
				}
				DataSet ds = module.getDataSets().get(id);
				if (ds != null) {
					result = new Result(id, ds);
					return;
				}
				Algorithm a = module.getAlgorithms().get(id);
				if (a != null) {
					result = new Result(id, a);
					return;
				}
				Sample s = module.getSamples().get(id);
				if (s != null) {
					result = new Result(id, s);
					return;
				}
				Module m = module.getModules().get(id);
				if (m != null) {
					result = new Result(id, m);
					return;
				}
			}

			// handle unknown objects or algorithms
			Object o = getObject(node.getExpression());

			Variable v = module.getVariables().get(ANS);
			if (v == null) {
				v = VariableFactory.labeledVariable(ANS);
				module.getVariables().put(ANS, v);
			}
			v.addMatrix(MathUtil.getMatrix(o));

			result = new Result(ANS, o);
			System.out.println(result);
		} catch (Exception e) {
			result = new Result(e);
			e.printStackTrace();
		}
	}

}
