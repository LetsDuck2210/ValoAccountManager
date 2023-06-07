package gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.info.InfoDisplay;
import valorant.Account;

public class AccountInfoPanel extends JPanel {
	private static final long serialVersionUID = 4202177239755879659L;
	
	public AccountInfoPanel() {
		setLayout(new GridLayout(6, 1));
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);
	}
	
	public void display(Account account) {
		for(var display : InfoDisplay.values())
			add(display.display(account));
	}
}
