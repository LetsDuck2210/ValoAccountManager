package main;

import java.awt.GridLayout;

import javax.swing.JFrame;

import gui.AccountInfoPanel;
import gui.AccountListPanel;

public class ValoAccountManager extends JFrame {
	private static final long serialVersionUID = -1983716182184486275L;

	public static void main(String[] args) {
		/* var mainframe = */ new ValoAccountManager();
	}

	public ValoAccountManager() {
		super("ValoAccountManager");
		
		setSize(800, 600);
		setLayout(new GridLayout(1, 2));
		setVisible(true);
		
		add(new AccountListPanel());
		add(new AccountInfoPanel());
		
		repaint();
		revalidate();
	}
}
