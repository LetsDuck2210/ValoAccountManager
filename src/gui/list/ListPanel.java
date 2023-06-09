package gui.list;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import gui.GuiConstants;
import valorant.Account;

public class ListPanel extends JPanel {
	private static final long serialVersionUID = -827302597575965602L;

	public ListPanel() {
//		var layout = new FlowLayout(FlowLayout.CENTER);
//		var layout = new DefaultMenuLayout(this, BoxLayout.Y_AXIS);
		var layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		
		setLayout(layout);
		setBackground(GuiConstants.BACKGROUND_COLOR);
//		setBackground(Color.cyan);
		setOpaque(true);
		
		var scroll = new JScrollPane(this);
		scroll.setSize(this.getSize());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		setAutoscrolls(true);
		
//		var hgap = layout.getHgap();
		var hgap = 10;
		setPreferredSize(new Dimension(getPreferredSize().width, getComponentCount() * (GuiConstants.LISTBUTTON_HEIGHT + hgap) + hgap));
	}
	
	public void addAccount(Account acc) {
		this.add(new ListButton(acc));
	}
}
