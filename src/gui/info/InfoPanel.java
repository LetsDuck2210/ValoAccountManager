package gui.info;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.GuiConstants;
import valorant.Account;

public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 4202177239755879659L;
	private RankPanel rankPanel;

	public InfoPanel() {
		var layout = new GridLayout(6, 1);
		setLayout(layout);
		setBorder(new EmptyBorder(0, 0, 10, 0));
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);
		
		for(var display : InfoDisplay.values())
			add(display.getButton());
		add(rankPanel = new RankPanel());
	}

	public void display(Account account) {
		for (var display : InfoDisplay.values())
			display.display(account);
		rankPanel.showRank(account);
	}

}
