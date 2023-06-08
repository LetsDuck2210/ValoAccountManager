package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import gui.GuiConstants;
import gui.HomeScreen;

public class ValoAccountManager extends JFrame {
	private static final long serialVersionUID = -1983716182184486275L;

	public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new MetalLookAndFeel());
		/* var mainframe = */ new ValoAccountManager();
	}
	
	public ValoAccountManager() {
		super("ValoAccountManager");
		
		setSize(GuiConstants.PREFERED_SIZE);
		setMinimumSize(new Dimension(600, 500));
		setLayout(new GridLayout());
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setVisible(true);
		setDefaultCloseOperation(3);
		
		var home = new HomeScreen();
		add(home);
//		home.showAccount(new Account("testid", "pw", "JJCooper", "187", "notes", Currency.TRY));
		
		repaint();
		revalidate();
	}
}
