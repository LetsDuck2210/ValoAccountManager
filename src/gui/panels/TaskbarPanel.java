package gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import gui.HomeScreen;

public class TaskbarPanel extends JPanel {
	private static final long serialVersionUID = 5118137174535785959L;

	public TaskbarPanel() {
//		setBackground(GuiConstants.BACKGROUND_COLOR);
		setBackground(Color.BLACK);
		setOpaque(true);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JButton addButton = new JButton();
		add(addButton);
		addButton.setPreferredSize(new Dimension(75, 50));
		addButton.setText("test");
		addButton.addActionListener(a -> {
			var home = (HomeScreen) getParent();
			if(!home.extendedShowing())
				home.show(new AccountListPanel());
			else home.collapse();
		});
		
	}
}
