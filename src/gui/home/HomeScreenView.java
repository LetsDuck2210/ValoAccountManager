package gui.home;

import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.JPanel;

import gui.GuiConstants;

class HomeScreenView extends JPanel {
	private GridLayout defaultLayout = new GridLayout(1, 2);
	private GridLayout extendedLayout = new GridLayout(1, 3);
	private JPanel defaultPanel1, defaultPanel2;
	public HomeScreenView(JPanel defaultPanel1, JPanel defaultPanel2) {
		setOpaque(true);
		setBackground(GuiConstants.BACKGROUND_COLOR);
		
		this.defaultPanel1 = defaultPanel1;
		this.defaultPanel2 = defaultPanel2;
		
		showDefault();
	}
	
	void showDefault() {
		removeAll();
		setLayout(defaultLayout);
		add(defaultPanel1);
		add(defaultPanel2);
		refresh();
	}
	
	void showExtended(JPanel panel) {
		removeAll();
		setLayout(extendedLayout);
		add(defaultPanel1);
		add(defaultPanel2);
		add(panel);
		refresh();
	}
	
	private void refresh() {
		revalidate();
		repaint();
	}
}
