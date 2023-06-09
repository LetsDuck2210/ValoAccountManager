package gui.list;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import main.ValoAccountManager;
import valorant.Account;

public class ListButton extends JButton {
	private static final long serialVersionUID = 4314041358971090768L;

	public ListButton(Account acc) {
		super();
		setOpaque(true);
		setFocusPainted(false);
		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);
		setText(acc.name() + " #" + acc.tagline());
		setFont(GuiConstants.FONT);
		setHorizontalAlignment(SwingConstants.LEFT);
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR), 
				new EmptyBorder(5, 5, 0, 5)));
		setPreferredSize(new Dimension((int) getPreferredSize().getWidth(), GuiConstants.LISTBUTTON_HEIGHT));
		
		addActionListener(a -> {
			ValoAccountManager.showAccount(acc);
		});
	}
}
