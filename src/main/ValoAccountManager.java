package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import gui.GuiConstants;
import gui.HomeScreen;
import gui.panels.AccountInfoPanel;
import valorant.crosshair.OfflineConverter;

public class ValoAccountManager extends JFrame {
	private static final long serialVersionUID = -1983716182184486275L;
	private static AccountInfoPanel infoPanel;

	public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new MetalLookAndFeel());
		/* var mainframe = */ new ValoAccountManager();
		String[] codes = {
			"0;P;t;2;o;1;d;1;0t;10;0l;19;0v;0;0g;1;0o;1;0a;0;0e;0;1l;10;1v;0;1g;1;1o;19;1a;0;1s;0;1e;0", // glasses
			"0;P;c;5;o;0.000;t;7;d;1;a;0.313;z;6;1l;1;1t;10;1o;40",					// default
			"0;P;c;4;h;0;d;1;f;0;0t;6;0l;1;0o;1;0a;1;0f;0;1b;0",					// X
			"0;P;c;5;h;0;f;0;0t;1;0l;3;0o;0;0a;1;0f;0;1t;3;1o;0;1a;1;1m;0;1f;0",	// diamond
			"0;s;1;P;c;5;h;0;m;1;0l;4;0o;2;0a;1;0f;0;1b;0;S;c;4;o;1",				// TenZ
			"0;P;c;1;t;6;o;1;d;1;z;6;a;0;f;0;m;1;0t;10;0l;20;0o;20;0a;1;0m;1;0e;0.1;1t;10;1l;10;1o;40;1a;1;1m;0", // windmill
			"0;P;c;6;t;6;o;0.3;f;0;0t;1;0l;5;0o;5;0a;1;0f;0;1t;10;1l;4;1o;5;1a;0.5;1m;0;1f;0" // reyna flash
		};
		int i = 0;
		for(var code : codes)
			ImageIO.write((RenderedImage) new OfflineConverter(new Dimension(400, 400)).convert(code), "png", new File(i++ + ".png"));
	}
	
	public ValoAccountManager() {
		super("ValoAccountManager");
		
		setSize(GuiConstants.PREFERED_SIZE);
		setLayout(new GridLayout(1, 1));
		setVisible(true);
		setDefaultCloseOperation(3);
		
		add(new HomeScreen());
		
		repaint();
		revalidate();
	}
}
