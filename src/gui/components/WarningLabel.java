package gui.components;

import javax.swing.Icon;

import gui.GuiConstants;

public class WarningLabel extends TextLabel {
	private static final long serialVersionUID = 2110379363166807496L;
	public WarningLabel(String text, boolean visible) {
		this(text, null, visible);
	}
	public WarningLabel(String text) {
		this(text, null, false);
	}
	public WarningLabel(String text, Icon icon, boolean visible) {
		super(text);
		setVisible(visible);
		setForeground(GuiConstants.TEXT_COLOR_WARNING);
		setIcon(icon);
	}
}
