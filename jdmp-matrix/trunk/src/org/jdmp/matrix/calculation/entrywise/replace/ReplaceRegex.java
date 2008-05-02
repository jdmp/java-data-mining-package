package org.jdmp.matrix.calculation.entrywise.replace;

import java.util.regex.Pattern;

import org.jdmp.matrix.Matrix;
import org.jdmp.matrix.MatrixException;
import org.jdmp.matrix.MatrixFactory;
import org.jdmp.matrix.Matrix.EntryType;
import org.jdmp.matrix.calculation.StringCalculation;

public class ReplaceRegex extends StringCalculation {
	private static final long serialVersionUID = 8158807887609103123L;

	private Pattern searchPattern = null;

	private String replaceString = null;

	public ReplaceRegex(Matrix matrix, String searchString, String replaceString) {
		this(matrix, Pattern.compile(searchString), replaceString);
	}

	public ReplaceRegex(Matrix matrix, Pattern searchPattern, String replaceString) {
		super(matrix);
		this.searchPattern = searchPattern;
		this.replaceString = replaceString;
	}

	@Override
	public String getString(long... coordinates) throws MatrixException {
		String src = getSource().getString(coordinates);

		return (src == null) ? null : searchPattern.matcher(src).replaceAll(replaceString);
	}

	public static Matrix calc(Matrix source, Pattern search, String replacement) throws MatrixException {
		Matrix ret = MatrixFactory.zeros(EntryType.STRING, source.getSize());
		for (long[] c : source.availableCoordinates()) {
			String src = source.getString(c);
			ret.setString((src == null) ? null : search.matcher(src).replaceAll(replacement), c);
		}
		return ret;
	}

	public static Matrix calc(Matrix source, String searchString, String replacement) throws MatrixException {
		return calc(source, Pattern.compile(searchString), replacement);
	}
}
