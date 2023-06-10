package gui.home;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import gui.GuiConstants;
import gui.panels.AddAccountPanel;
import gui.panels.EditAccountPanel;

public class TaskbarPanel extends JPanel {
	private static final long serialVersionUID = 5118137174535785959L;

	public TaskbarPanel() {
		setBackground(GuiConstants.BACKGROUND_COLOR_ALT);
		setOpaque(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
//		setLayout(new GridLayout());
		
		add(new TaskbarButton("add", new AddAccountPanel()));
		add(new TaskbarButton("edit", new EditAccountPanel()));
		add(new TaskbarButton("crosshairs", new AddAccountPanel()));
	}
}
