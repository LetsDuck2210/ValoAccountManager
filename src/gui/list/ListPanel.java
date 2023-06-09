package gui.list;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.GuiConstants;
import valorant.Account;

public class ListPanel extends JPanel {
	private static final long serialVersionUID = -827302597575965602L;
	private JPanel pane;
	private FlowLayout paneLayout;
	private JScrollPane scroll;

	public ListPanel() {
//		var layout = new FlowLayout(FlowLayout.CENTER);
//		var layout = new DefaultMenuLayout(this, BoxLayout.Y_AXIS);
		
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setBorder(BorderFactory.createLineBorder(Color.RED));
		setLayout(new GridLayout(1, 1));
		setOpaque(true);
		
		pane = new JPanel();
		paneLayout = new FlowLayout(FlowLayout.LEADING);
		pane.setLayout(paneLayout);
		pane.setBackground(GuiConstants.BACKGROUND_COLOR);
		pane.setOpaque(true);
		
		scroll = new JScrollPane(pane);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		setAutoscrolls(true);
		
		add(scroll);
		
		revalidate();
	}
	
	public void addAccount(Account acc) {
		pane.add(new ListButton(acc));

		var hgap = paneLayout.getHgap();
		pane.setPreferredSize(new Dimension(scroll.getWidth(), pane.getComponentCount() * (GuiConstants.LISTBUTTON_HEIGHT + hgap) + hgap));
		System.out.println(pane.getPreferredSize());
	}
}
