package gui.panels;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import gui.GuiConstants;
import gui.components.TaskbarButton;

public class TaskbarPanel extends JPanel {
	private static final long serialVersionUID = 5118137174535785959L;

	public TaskbarPanel() {
		setBackground(GuiConstants.BACKGROUND_COLOR_ALT);
		setOpaque(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(new TaskbarButton("add", new AddAccountPanel()));
		add(new TaskbarButton("edit", new TestPanel(Color.pink)));
		add(new TaskbarButton("crosshairs", new TestPanel(Color.green)));
	}
}
