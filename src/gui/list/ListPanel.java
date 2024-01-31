package gui.list;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import gui.GuiConstants;
import valorant.Account;

public class ListPanel extends JPanel {
	private static final long serialVersionUID = -827302597575965602L;
	private JPanel pane;
	private FlowLayout paneLayout;
	private JScrollPane scroll;

	public ListPanel() {
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setLayout(new GridLayout(1, 1));
		setOpaque(true);

		pane = new JPanel();
		paneLayout = new FlowLayout(FlowLayout.LEFT);
		pane.setLayout(paneLayout);
		pane.setBackground(GuiConstants.BACKGROUND_COLOR);
		pane.setOpaque(true);

		scroll = new JScrollPane(pane);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			public Dimension getPreferredSize(JComponent c) {
				return new Dimension(0, 0);
			}
		});
		scroll.getVerticalScrollBar().setBackground(getBackground());
		scroll.setBorder(null);

		setAutoscrolls(true);

		add(scroll);

		refresh();
	}

	public void addAccount(Account acc) {
		pane.add(new ListButton(acc));
		refresh();
	}

	public void removeAccount(Account acc) {
		for (var comp : pane.getComponents()) {
			var castedComp = (ListButton) comp;
			if (castedComp.getAccount() == acc) {
				pane.remove(castedComp);
			}
		}
		refresh();
	}

	/**
	 * Removes all accounts currently shown in the list
	 */
	public void removeAllAccounts() {
		pane.removeAll();
		refresh();
	}

	public void repaint() {
		super.repaint();
		if (paneLayout == null)
			return;
		var hgap = paneLayout.getHgap();
		pane.setPreferredSize(new Dimension(scroll.getWidth(),
				pane.getComponentCount() * (GuiConstants.LISTBUTTON_HEIGHT + hgap) + hgap));
		for (var ch : pane.getComponents())
			ch.setPreferredSize(new Dimension(pane.getWidth(), GuiConstants.LISTBUTTON_HEIGHT));
	}
	
	private void refresh() {
		revalidate();
		repaint();
	}
}
