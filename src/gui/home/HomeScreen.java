package gui.home;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JPanel;

import gui.GuiConstants;
import gui.info.InfoPanel;
import gui.list.ListPanel;
import gui.panels.AddAccountPanel;
import gui.panels.CrosshairPanel;
import gui.panels.DeleteAccountPanel;
import gui.panels.EditAccountPanel;
import gui.panels.Updatable;
import main.ValoAccountManager;
import valorant.Account;

public class HomeScreen extends JPanel {
	private static final long serialVersionUID = -1459499358513683681L;

	private ListPanel listPanel = new ListPanel();
	private InfoPanel infoPanel = new InfoPanel();
	private TaskbarPanel taskbarPanel = new TaskbarPanel();
	private HomeScreenView mainView = new HomeScreenView(listPanel, infoPanel);
	private Set<Extendable> extendables = new HashSet<>();
	private CrosshairPanel crosshairPanel;

	private Optional<JPanel> extendedShowing = Optional.empty();

	public HomeScreen() {
		extendables.add(mainView);
		extendables.add(taskbarPanel);

		var layout = new GridBagLayout();
		layout.rowWeights = new double[] { 1, 0 };
		layout.rowHeights = new int[] { 0, GuiConstants.TASKBAR_HEIGHT };
		layout.columnWeights = new double[] { 1 };
		setLayout(layout);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;
		c.gridwidth = c.gridheight = 1;
		add(mainView, c);
		c.gridy = 1;
		add(taskbarPanel, c);

		setOpaque(true);
		setBackground(GuiConstants.BACKGROUND_COLOR);
		
		addExtendedPanel("add", new AddAccountPanel());
		addExtendedPanel("edit", new EditAccountPanel());
		addExtendedPanel("delete", new DeleteAccountPanel());
		addExtendedPanel("crosshairs", crosshairPanel = new CrosshairPanel());

		showDefault();
	}

	public void showAccount(Account acc) {
		infoPanel.display(acc);
		refresh();
	}

	public void addAccountToList(Account acc) {
		listPanel.addAccount(acc);
	}

	public void removeAccountFromList(Account acc) {
		listPanel.removeAccount(acc);
	}

	//TODO (not sure if this is needed)
	/**
	 * this method will clear the whole account list and add the accounts of the given list in the given order
	 * @param accs new account list
	 */
	public void resetAccountList(List<Account> accs) {
		listPanel.removeAllAccounts();
		accs.forEach(this::addAccountToList);
		refresh();
	}

	public void toggleExtended(JPanel panel) {
		if (extendedShowing.isEmpty()) {
			setSizeWindow(3 / 2.0);
			showExtended(panel);
			refresh();
		} else if (extendedShowing.get() != panel) {
			showExtended(panel);
			refresh();
		} else {
			setSizeWindow(2 / 3.0);
			collapse();
		}
	}

	private void showExtended(JPanel panel) {
		extendedShowing = Optional.of(panel);
	
		extendables.forEach((e) -> {
			e.showExtended(panel);
		});

		refresh();
	}

	private void collapse() {
		extendedShowing = Optional.empty();
		showDefault();
	}

	private void showDefault() {
		extendables.forEach((e) -> {
			e.showDefault();
		});

		refresh();
	}

	private void setSizeWindow(double factor) {
		Component parent = this;
		do {
			parent = parent.getParent();
		} while (parent.getParent() != null);
		parent.setSize((int) Math.round(parent.getWidth() * factor), parent.getHeight());
	}

	public void refresh() {
		listPanel.revalidate();
		listPanel.repaint();
		infoPanel.revalidate();
		infoPanel.repaint();
		revalidate();
		repaint();
	}
	
	private void addExtendedPanel(String text, JPanel panel) {
		taskbarPanel.addExtendedPanelButton(text, panel);
		if(panel instanceof Updatable u)
			ValoAccountManager.addUpdateablePanel(u);
	}
	
	public void addCrosshairToList(String code) {
		crosshairPanel.addCrosshair(code);
	}

	public void removeCrosshairFromList(String code) {
		crosshairPanel.removeCrosshair(code);
	}

}
