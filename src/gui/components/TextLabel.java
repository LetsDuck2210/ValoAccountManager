package gui.components;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import gui.GuiConstants;

public class TextLabel extends JLabel {
	private static final long serialVersionUID = 6536763916032675825L;

	public TextLabel(String text) {
		super(text);
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(GuiConstants.FONT);
		setForeground(GuiConstants.TEXT_COLOR);
	}
}
