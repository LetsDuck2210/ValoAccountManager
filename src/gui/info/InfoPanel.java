package gui.info;

import java.awt.*;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.GuiConstants;
import valorant.Account;

public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 4202177239755879659L;
	private RankPanel rankPanel;

	public InfoPanel() {
		var layout = new GridBagLayout();
		var c = new GridBagConstraints();

		setLayout(layout);
		setBorder(new EmptyBorder(0, 0, 10, 0));
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(InfoDisplay.NAME.getButton(), c);
		c.gridwidth = 1;
		c.gridy = 1;
		add(InfoDisplay.RIOTID.getButton(), c);
		c.gridx = 1;
		add(InfoDisplay.PASSWORD.getButton(), c);
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 2;
		add(InfoDisplay.CURRENCY.getButton(), c);
		c.gridy = 3;
		add(InfoDisplay.ADDITIONAL.getButton(), c);
		c.gridy = 4;
		add(rankPanel = new RankPanel(), c);
	}

	public void display(Account account) {
		for (var display : InfoDisplay.values())
			display.display(account);
		rankPanel.showRank(account);
	}

}
