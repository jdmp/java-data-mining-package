package org.jdmp.gui.interpreter;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jdmp.core.interpreter.Result;
import org.jdmp.core.module.Module;

public class CommandWindow extends JPanel implements KeyListener {
	private static final long serialVersionUID = 5931204715283753689L;

	private Module module = null;

	private JTextArea textField = new JTextArea();

	public CommandWindow(Module m) {
		this.module = m;

		textField.addKeyListener(this);

		textField.setText(">> ");

		setBorder(BorderFactory.createTitledBorder("Command Window"));
		setLayout(new BorderLayout());
		add(new JScrollPane(textField), BorderLayout.CENTER);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		try {
			int line = textField.getLineCount() - 1;
			int lineStart = textField.getLineStartOffset(line);
			int lineEnd = textField.getLineEndOffset(line);
			int length = lineEnd - lineStart;
			String text = textField.getText(lineStart + 3, length - 3);

			if (e.getKeyCode() == KeyEvent.VK_UP) {
				e.consume();
			} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (text.length() == 3 && text.startsWith(">> ")) {
					e.consume();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				Result result = module.execute(text);
				textField.append("\n" + result + "\n>> ");
				e.consume();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
