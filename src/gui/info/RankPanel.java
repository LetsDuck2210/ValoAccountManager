package gui.info;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import util.ImageUtil;
import valorant.Account;
import valorant.EmptyAccount;

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
	private JButton trackerLinkButton = new JButton();
	private String url = "https://tracker.gg/valorant";

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
		text = new JLabel("Level: ");
		text.setFont(GuiConstants.FONT);
		text.setForeground(Color.GRAY);
		add(text, constr);
		
		constr.gridx = 1;
		add(imageLabel, constr);

		trackerLinkButton.setFont(GuiConstants.FONT);
		trackerLinkButton.setForeground(Color.GRAY);
		trackerLinkButton.setBackground(GuiConstants.COMPONENT_COLOR_ALT);
		trackerLinkButton.setText("   Stats   ");
		trackerLinkButton.setFocusPainted(false);
		trackerLinkButton.setOpaque(true);
		trackerLinkButton.setBorder(BorderFactory.createCompoundBorder(new MatteBorder(0, 0, 5, 0, GuiConstants.COMPONENT_COLOR),
				new EmptyBorder(0, 0, 5, 0)));
		trackerLinkButton.addActionListener(a -> {
			try {
				openWebpage(url);
			} catch(Exception e) {
				e.printStackTrace();
			}
		});
		constr.gridx = 2;
		add(trackerLinkButton, constr);

		setBackground(GuiConstants.COMPONENT_COLOR);
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
				new EmptyBorder(5, 5, 0, 5)));
	}

	public void showRank(Account acc) {
		if(acc != EmptyAccount.get())
			url = "https://tracker.gg/valorant/profile/riot/" + acc.name().toLowerCase().replace(' ', '_') + "%23" + acc.tagline().toLowerCase().replace(' ', '_') + "/overview";
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
			text.setText("Level: " + (rr == null ? -1 : rr));
			repaint();
			revalidate();
		});
	}

	private static void openWebpage(String url) throws MalformedURLException, URISyntaxException, IOException {
		Desktop.getDesktop().browse(new URL(url).toURI());
	}
}
