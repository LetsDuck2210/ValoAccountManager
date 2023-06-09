package gui.home;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Optional;

import javax.swing.JPanel;

import gui.GuiConstants;
import gui.info.InfoPanel;
import gui.list.ListPanel;
import valorant.Account;

public class HomeScreen extends JPanel {
	private static final long serialVersionUID = -1459499358513683681L;
	
	private GridBagConstraints c = new GridBagConstraints();
//	private GridBagLayout defaultLayout;
//	private GridBagLayout extendedLayout;
	
//	private ListPanel listPanel = new ListPanel();
	private ListPanel listPanel = new ListPanel();
	private InfoPanel infoPanel = new InfoPanel();
	private TaskbarPanel taskbarPanel = new TaskbarPanel();
	private HomeScreenView mainView = new HomeScreenView(listPanel, infoPanel);
	
//	private boolean extendedShowing = false;
	private Optional<JPanel> extendedShowing = Optional.empty();
	
	public HomeScreen() {
//		defaultLayout = new GridBagLayout();
//		defaultLayout.columnWeights = new double[] {1, 1};
//		defaultLayout.rowWeights = new double[] {1, 0};
//		defaultLayout.rowHeights = new int[2];
//		defaultLayout.rowHeights[1] = GuiConstants.TASKBAR_HEIGHT;
//		
//		extendedLayout = defaultLayout;
//		extendedLayout.columnWeights = new double[] {1, 1, 1};
		
		var layout = new GridBagLayout();
		layout.rowWeights = new double[] {1, 0};
		layout.rowHeights = new int[2];
		layout.rowHeights[1] = GuiConstants.TASKBAR_HEIGHT;
		layout.columnWeights = new double[] {1, 1};
		setLayout(layout);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;
		c.gridwidth = c.gridheight = 1;
		add(mainView, c);
		c.gridy = 1;
		add(taskbarPanel, c);
		
		setOpaque(true);
		setBackground(Color.gray);
		
		showDefault();
	}
	
	public void showAccount(Account acc) {
		infoPanel.display(acc);
		revalidate();
		repaint();
	}
	public void addAccountToList(Account acc) {
		listPanel.addAccount(acc);
	}
	
	public void toggleExtended(JPanel panel) {
		if(extendedShowing.isEmpty()) {
			setSizeWindow(3/2.0);
			showExtended(panel);
		} else if(extendedShowing.get() != panel) {
			showExtended(panel);
		} else {
			collapse();
			setSizeWindow(2/3.0);
		}
	}
	
	private void showExtended(JPanel panel) {
		extendedShowing = Optional.of(panel);
//		removeAll();
		
//		setLayout(extendedLayout);
//		
//		c.fill = GridBagConstraints.BOTH;
//		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
//		add(listPanel, c);
//		
//		c.gridx = 1;
//		add(infoPanel, c);
//		
//		c.gridx = 2;
//		add(panel, c);
//		
//		c.gridx = 0; c.gridy = 1; c.gridwidth = 3;
//		add(taskbarPanel, c);
		
		mainView.showExtended(panel);
		
		revalidate();
		repaint();
	}

	private void collapse() {
		extendedShowing = Optional.empty();
		showDefault();
	}
	
	private void showDefault() {
//		removeAll();
		
//		setLayout(defaultLayout);
//		c.fill = GridBagConstraints.BOTH;
//		c.gridx = 0; c.gridy = 0; c.gridheight = 1; c.gridwidth = 1;
//		add(listPanel, c);
//		
//		c.gridx = 1;
//		add(infoPanel, c);
//		
//		c.gridy = 1; c.gridx = 0; c.gridwidth = 2;
//		add(taskbarPanel, c);22
		mainView.showDefault();
		
		revalidate();
		repaint();
	}
	
	private void setSizeWindow(double factor) {
		Component parent = this;
		do {
			parent = parent.getParent();
		} while(parent.getParent() != null);
		parent.setSize((int) (parent.getWidth() * factor), parent.getHeight());
	}

	public void update() {
		listPanel.revalidate();
		listPanel.repaint();
		infoPanel.revalidate();
		infoPanel.repaint();
		revalidate();
		repaint();
	}
}
