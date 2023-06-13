package gui.components;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;

public class InputField extends JPanel implements Input {
	private static final long serialVersionUID = -1448562131639331504L;

	protected JLabel label;
	protected JTextField input = new JTextField();

	public InputField(String text) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBackground(GuiConstants.COMPONENT_COLOR);
		setOpaque(true);

		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
				new EmptyBorder(5, 5, 0, 5)));

		label = new JLabel(text);
		label.setForeground(GuiConstants.TEXT_COLOR);
		label.setFont(GuiConstants.FONT);

//		input.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.COMPONENT_COLOR), new EmptyBorder(5, 5, 0, 5)));
//		input.setPreferredSize(new Dimension(getWidth() - label.getWidth(), 30));
		input.setFont(GuiConstants.FONT);
		input.setOpaque(true);
		input.setBackground(GuiConstants.COMPONENT_COLOR_ALT);
		input.setForeground(GuiConstants.TEXT_COLOR);
		input.setCaretColor(GuiConstants.TEXT_COLOR);

		add(label);
		add(input);

	}

	@Override
	public String get() {
		return input.getText();
	}
	
	@Override
	public void set(String text) {
		this.input.setText(text);
	}

	@Override
	public void clear() {
		input.setText("");
	}

	@Override
	public boolean isFilled() {
		return (!input.getText().equals(""));
	}
}
