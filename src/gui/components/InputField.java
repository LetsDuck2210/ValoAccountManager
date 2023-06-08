package gui.components;

import java.awt.Dimension;

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
	
	private JLabel label;
	private JTextField input;
	
	public  InputField(String text) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
//		setBackground(GuiConstants.BACKGROUND_COLOR);
		setBackground(GuiConstants.COMPONENT_COLOR);
		setOpaque(true);
		
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 5, 0, 5)));
		
		label = new JLabel(text + ": ");
		label.setForeground(GuiConstants.TEXT_COLOR);
		label.setFont(GuiConstants.FONT);
		
		input = new JTextField();
		input.setPreferredSize(new Dimension(getWidth() - label.getWidth(), 30));
//		input.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.COMPONENT_COLOR), new EmptyBorder(5, 5, 0, 5)));
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
}