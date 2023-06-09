package gui.panels;

import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.GuiConstants;
import gui.info.InfoDisplay;
import gui.info.RankPanel;
import valorant.Account;

public class AccountInfoPanel extends JPanel {
	private static final long serialVersionUID = 4202177239755879659L;

	public AccountInfoPanel() {
		var layout = new GridLayout(6, 1);
		setLayout(layout);
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);
	}

	public void display(Account account) {
		removeAll();
		for (var display : InfoDisplay.values())
			add(display.display(account));
		add(new RankPanel(account));
	}

}
