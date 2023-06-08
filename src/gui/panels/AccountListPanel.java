package gui.panels;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public class AccountListPanel extends JPanel {
	private static final long serialVersionUID = -827302597575965602L;

	public AccountListPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
//		setBackground(GuiConstants.BACKGROUND_COLOR);
		setBackground(Color.cyan);
		setOpaque(true);
	}
}
