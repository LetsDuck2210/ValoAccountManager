package gui.components;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;

public class InputFieldWithButton extends InputField {
	private static final long serialVersionUID = -2992908601256563524L;

	public InputFieldWithButton(String text, String buttonText, ActionListener actionListener) {
		super(text);
		
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 10, 5, 5, GuiConstants.COMPONENT_COLOR),
				new EmptyBorder(5, 10, 5, 5)));
		
		var button = new JButton();
		button.setText(buttonText);
		button.setOpaque(true);
		button.setBackground(GuiConstants.COMPONENT_COLOR_ALT);
		button.setForeground(GuiConstants.TEXT_COLOR);
		button.setFont(GuiConstants.FONT);
		button.addActionListener(actionListener);
		button.setFocusPainted(false);
		button.setBorder(new MatteBorder(0, 10, 0, 0, GuiConstants.COMPONENT_COLOR));
		
		add(button);
		
		revalidate();
		repaint();
	}
	
	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}
}
