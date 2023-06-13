package gui.components;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;

public class Button extends JButton {
	private static final long serialVersionUID = -1005035904033681266L;

	public Button(String text, ActionListener actionListener) {
		this(text, actionListener, null);
	}
	public Button(String text, ActionListener actionListener, Icon icon) {
		super();
		setOpaque(true);
		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);
		setFont(GuiConstants.FONT);
		setText(text);
		addActionListener(actionListener);
		
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
				new EmptyBorder(5, 5, 0, 5)));
		
		setIcon(icon);
	}
}
