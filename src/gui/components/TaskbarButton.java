package gui.components;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.GuiConstants;
import gui.HomeScreen;

public class TaskbarButton extends JButton {
	private static final long serialVersionUID = 8867407780741080424L;

	public TaskbarButton(String text, JPanel panel) {
		setPreferredSize(new Dimension(100, GuiConstants.TASKBAR_HEIGHT));
		setText(text);
		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);
		addActionListener(a -> {
			var home = (HomeScreen) getParent().getParent();
			home.toggleExtended(panel);
		});
	}
}
