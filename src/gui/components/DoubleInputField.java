package gui.components;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import gui.GuiConstants;
import valorant.ValoConstants;

public class DoubleInputField extends JPanel implements Input {
	private static final long serialVersionUID = -525185342370275371L;
	private InputField field1;
	private InputField field2;

	public DoubleInputField(String text1, String text2) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(GuiConstants.COMPONENT_COLOR);
		setOpaque(true);

		field1 = new InputField(text1);
		field2 = new InputField(text2);

		add(field1);
		add(field2);
	}

	@Override
	public String get() {
		return field1.get() + "#" + field2.get();
	}

	@Override
	public void clear() {
		field1.clear();
		field2.clear();
	}

	@Override
	public boolean isFilled() {
		return field1.isFilled() && field2.isFilled();
	}

	@Override 
	public void set(String text) {
		String[] split = text.split("#");
		if(split.length != 2 || split[0].length() == 0 || split[1].length() == 0 || 
				split[0].length() > ValoConstants.MAX_NAME_LENGTH || split[1].length() > ValoConstants.MAX_TAG_LENGTH) 
			throw new IllegalArgumentException("text must have format: NAME#TAG");
		this.field1.set(split[0]);
		this.field2.set(split[1]);
	}

}
