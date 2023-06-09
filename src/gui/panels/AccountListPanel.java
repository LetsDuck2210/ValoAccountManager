package gui.panels;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.GuiConstants;
import gui.list.ListButton;
import valorant.Account;

public class AccountListPanel extends JPanel {
	private static final long serialVersionUID = -827302597575965602L;

	public AccountListPanel() {
		var layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		setBackground(GuiConstants.BACKGROUND_COLOR);
//		setBackground(Color.cyan);
		setOpaque(true);
		
		var scroll = new JScrollPane(this);
		scroll.setSize(this.getSize());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		this.setAutoscrolls(true);
		
//		var hgap = layout.getHgap();
//		setPreferredSize(new Dimension(getPreferredSize().width, getComponentCount() * (GuiConstants.LISTBUTTON_HEIGHT + hgap) + hgap));
	}
	
	public void addAccount(Account acc) {
		this.add(new ListButton(acc));
	}
}
