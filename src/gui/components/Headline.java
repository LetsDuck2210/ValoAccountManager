package gui.components;

import java.awt.Font;

import javax.swing.JLabel;

import gui.GuiConstants;

public class Headline extends JLabel {
	private static final long serialVersionUID = -7380933037918441974L;

	public Headline(String text) {
		super(text);
		setForeground(GuiConstants.TEXT_COLOR);
		setHorizontalAlignment(CENTER);
		var font = GuiConstants.FONT;
		var headFont = new Font(font.getFontName(), font.getStyle(), font.getSize() + 10);
		setFont(headFont);
	}
}
