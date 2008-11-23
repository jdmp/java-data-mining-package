package org.jdmp.core.grammar.jdmp.interpreter;

import java.io.PushbackReader;
import java.io.StringReader;

import org.jdmp.core.grammar.jdmp.lexer.Lexer;
import org.jdmp.core.grammar.jdmp.node.Start;
import org.jdmp.core.grammar.jdmp.parser.Parser;
import org.jdmp.core.interpreter.Result;

public class Interpreter {

	public Interpreter() {
	}

	public Result execute(String s) {
		try {
			StringReader sr = new StringReader(s);
			Parser p = new Parser(new Lexer(new PushbackReader(sr, 1024)));

			Start tree = p.parse();

			tree.apply(new Translation());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

}
