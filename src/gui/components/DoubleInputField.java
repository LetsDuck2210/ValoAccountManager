package gui.components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gui.GuiConstants;

public class DoubleInputField extends JPanel implements Input {
	private static final long serialVersionUID = -525185342370275371L;
	private InputField field1;
	private InputField field2;

	public DoubleInputField(String text1, String text2) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(GuiConstants.COMPONENT_COLOR);
		setOpaque(true);
		
		var field1 = new InputField(text1);
		var field2 = new InputField(text2);
		
		add(field1);
		add(field2);
	}

	@Override
	public String get() {
		return field1.get() + "#" + field2.get();
	}
}
