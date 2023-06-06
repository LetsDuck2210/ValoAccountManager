package gui.info;

import java.awt.Dimension;

import javax.swing.JButton;

import gui.GuiConstants;
import valorant.Account;

public abstract class InfoButton extends JButton {
	private static final long serialVersionUID = 6880347885377211418L;
	protected String value = "";

	public InfoButton() {
		super();
		setPreferredSize(new Dimension(300, 80));
		setOpaque(true);

		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);

	}

	public abstract void showInformation(Account acc);
}
