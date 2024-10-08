package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import gui.GuiConstants;
import gui.components.CrosshairButton;
import gui.home.HomeScreen;
import gui.panels.Updatable;
import util.FileManager;
import valorant.Account;
import valorant.EmptyAccount;

public class ValoAccountManager extends JFrame {
	private static final long serialVersionUID = -1983716182184486275L;
	private static List<Account> accounts = new ArrayList<>();
	private static List<String> crosshairs = new ArrayList<>();
	private static FileManager accountFileManager = new FileManager("accounts.txt");
	private static FileManager crosshairFileManager = new FileManager("crosshairs.txt");
	private static Optional<Account> currentAccount = Optional.empty();
	private static Optional<CrosshairButton> currentCrosshair = Optional.empty();
	private static Set<Updatable> updatablePanels = new HashSet<>();
	private static HomeScreen home = new HomeScreen();


	//TODO
	/** configs */
	private static FileManager configFileManager = new FileManager("config.txt");
	private static boolean reversed = false; //list is sorted as chosen but reversed

	public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
		accountFileManager.ensureExists();
		crosshairFileManager.ensureExists();
		configFileManager.ensureExists();
		for (var line : accountFileManager.readLines()) {
			var acc = Account.fromString(line);
			accounts.add(acc);
			acc.getRank(null);
		}
        crosshairs.addAll(crosshairFileManager.readLines());

		UIManager.setLookAndFeel(new MetalLookAndFeel());
		new ValoAccountManager();

		//TODO
		//configFileManager.readLines().forEach(ValoAccountManager::readConfig);

		accounts.forEach(home::addAccountToList);
		crosshairs.forEach(home::addCrosshairToList);
	}

	public ValoAccountManager() {
		super("ValoAccountManager");
		setSize(GuiConstants.PREFERED_SIZE);
		setMinimumSize(new Dimension(600, 500));
		setLayout(new GridLayout());
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setDefaultCloseOperation(3);
		setVisible(true);

		add(home);
		showAccount(EmptyAccount.get());

		repaint();
		revalidate();
	}

	public static void showAccount(Account acc) {
		home.showAccount(acc);
		if (acc != EmptyAccount.get()) {
			currentAccount = Optional.of(acc);
			for (var panel : updatablePanels) {
				panel.update(acc);
			}
		}
		home.refresh();
	}

	public static void addAccount(Account acc) {
		System.out.println("Account is being added: " + acc.name());
		accountFileManager.writeLine(acc.toString());
		accounts.add(acc);
		home.addAccountToList(acc);
		home.refresh();
	}

	public static void removeAccount(Account acc) {
		System.out.println("Account is being removed: " + acc.name());
		accountFileManager.deleteLine(acc.toString());
		accounts.remove(acc);
		home.removeAccountFromList(acc);
		home.refresh();
	}
	
	public static void addCrosshair(String code) {
		System.out.println("Crosshair is being added: " + code);
		crosshairFileManager.writeLine(code);
		crosshairs.add(code);
		home.addCrosshairToList(code);
		home.refresh();
	}
	
	public static void removeCrosshair(String code) {
		System.out.println("Crosshair is being removed: " + code);
		crosshairFileManager.deleteLine(code);
		crosshairs.remove(code);
		home.removeCrosshairFromList(code);
		home.refresh();
	}

	public static Optional<Account> getCurrentAccount() {
		return currentAccount;
	}
	public static Optional<CrosshairButton> getCurrentCrosshair() {
		return currentCrosshair;
	}
	public static void setCurrentCrosshair(Optional<CrosshairButton> current) {
		currentCrosshair = current;
	}

	public static void addUpdateablePanel(Updatable panel) {
		updatablePanels.add(panel);
	}

	/**
	 * sets one of the settings that are saved in config file
	 * @param setting line from the config file, format: [key]=[value], e.g. "sort_by=name"
	 */
	private static void readConfig(String setting) {
		var split = setting.split("=");
		var key = split[0];
		var value = split[1];
		switch(key.toLowerCase().strip()) {
			case("sort_by"): {
				sortBy(value.toLowerCase().strip());
				break;
			}
			case("reversed"): {
				boolean rev;
				try {
					rev = Boolean.parseBoolean(value.toLowerCase().strip());
				} catch(Exception e) {
					throw new IllegalArgumentException("reversed must be a boolean value (true/false)");
				}
				if(rev) reverse();
				break;
			}
			default: {
				throw new IllegalArgumentException("unknown setting: " + key);
			}
		}
	}

	/**
	 * This method sorts the accounts in the list by the given parameter
	 * Possible values are: name, rank, custom
	 * custom will leave the accounts in the order they were added (TODO: change oder also after adding the accounts)
	 * @param order value the account list should be sorted by
	 */
	private static void sortBy(String order) {
		if(order == null) throw new IllegalArgumentException("order must be not null");
		switch(order) {
			case("name"): {
				accounts.sort(Comparator.comparing(Account::name));
				break;
			}
			case("rank"): {
				accounts.sort(Comparator.comparingInt(Account::getRR));
				break;
			}
			case("custom"): {
				break;
			}
			default: {
				throw new IllegalArgumentException("unknown order parameter: " + order);
			}
		}
		if(reversed)
			Collections.reverse(accounts);
	}

	private static void reverse() {
		Collections.reverse(accounts);
	}
}
