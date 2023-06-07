package gui;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class AccountListPanel extends JPanel {
	private static final long serialVersionUID = -827302597575965602L;

	public AccountListPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);
	}
}
