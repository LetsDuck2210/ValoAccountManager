package gui.info;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import util.ImageUtil;
import valorant.Account;

public class RankPanel extends JPanel {
	private static final long serialVersionUID = -3609953978930608614L;
	private GridBagConstraints constr;
	private Image rankImage;
	private JLabel text;
	private long id = 0;
	private JLabel imageLabel = new JLabel() {
		private static final long serialVersionUID = -4966967331855478013L;

		public void paintComponent(Graphics g) {
			g.drawImage(rankImage, 0, 2, getHeight() - 4, getHeight() - 4, null);
		}
	};

	public RankPanel() {
		var layout = new GridBagLayout();
		layout.rowWeights = new double[] {1};
		layout.columnWeights = new double[] {0, 1};
		layout.columnWidths = new int[2];
		layout.columnWidths[0] = 70;
		
		constr = new GridBagConstraints();
		constr.fill = GridBagConstraints.BOTH;
		constr.gridx = constr.gridy = 0;
		constr.gridheight = constr.gridwidth = 1;
		
		setLayout(layout);
		text = new JLabel("Rank: ");
		text.setFont(GuiConstants.FONT);
		text.setForeground(Color.GRAY);
		add(text, constr);
		
		constr.gridx = 1;
		add(imageLabel, constr);

		setBackground(GuiConstants.COMPONENT_COLOR);
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
				new EmptyBorder(5, 5, 0, 5)));
	}
	
	public void showRank(Account acc) {
		try {
			rankImage = ImageUtil.loadFile("assets/rankIcons/empty.png").catchErr(e -> {}).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long current = ++id;
		acc.getRank(i -> {
			if(current != id)
				return;
			rankImage = i;
			var rr = Account.rr.get(acc);
			text.setText("Rank: " + rr + "/100 ");
			repaint();
			revalidate();
		});
	}
}
