package gui.home;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.GuiConstants;

public class TaskbarButton extends JButton {
	private static final long serialVersionUID = 8867407780741080424L;

	public TaskbarButton(String text, JPanel panel) {
		setPreferredSize(new Dimension(100, GuiConstants.TASKBAR_HEIGHT));
		setText(text);
		setOpaque(true);
		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);
		addActionListener(a -> {
			var home = (HomeScreen) getParent().getParent();
			home.toggleExtended(panel);
		});
	}
}
