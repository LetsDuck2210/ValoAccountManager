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
import util.AccountsList;
import util.FileManager;
import valorant.Account;
import valorant.EmptyAccount;

public class ValoAccountManager extends JFrame {
	private static final long serialVersionUID = -1983716182184486275L;
	private static AccountsList accounts = new AccountsList();
	private static List<String> crosshairs = new ArrayList<>();
	private static FileManager accountFileManager = new FileManager("accounts.txt");
	private static FileManager crosshairFileManager = new FileManager("crosshairs.txt");
	private static Optional<Account> currentAccount = Optional.empty();
	private static Optional<CrosshairButton> currentCrosshair = Optional.empty();
	private static Set<Updatable> updatablePanels = new HashSet<>();
	private static HomeScreen home = new HomeScreen();


	//TODO: fix problems with rank fetching and sorting the list
	// currently when a rank has been fetched, the accounts list is sorted. Because the sorting manipulates the list,
	// there is a time period, in which we have a critical section. Could be fixed by
	// - waiting for all ranks to be fetched before sorting and showing the accounts
	// - showing the custom list before all accounts have been fetched
	// - favorite: initially put the accounts in custom order, then, with every fetched rank, put the account to its
	//		right position relatively to the other, already fetched, accounts.

	//TODO
	/** configs */
	private static FileManager configFileManager = new FileManager("config.txt");

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
		new ValoAccountManager(); //if necessary, initially invisible, then turn visible after configs have been read

		configFileManager.readLines().forEach(ValoAccountManager::applyConfig);

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

	public static void sortBy(String chosen) {
		switch(chosen.strip().toLowerCase()) {
			case "name": accounts.sortByName(); break;
			case "rank": accounts.sortByRank(); break;
			case "custom": accounts.revertSort(); break;
			default: throw new IllegalArgumentException("Selection [" + chosen + "] does not exist");
		}
		home.resetAccountList(accounts);

		updateConfig("sort_by", chosen);
	}

	/**
	 * When this method is called and the list is currently sorted by rank, the ranks will be checked again and the list
	 * will, if needed, be sorted again.
	 * We call this function everytime the rank of an account has been fetched to ensure eventually displaying the right
	 * order while still asynchronously fetching the ranks.
	 */
	public static void updateSortByRank() {
		if(readConfig("sort_by").equals("rank")) {
			sortBy("rank");
		}
	}

	/**
	 * reverses the order of the accounts list
	 */
	public static void reverse() {
		accounts.reverse();
		home.resetAccountList(accounts);

		var rev = Boolean.parseBoolean(readConfig("reversed"));
		updateConfig("reversed", "" + !rev);
	}

	/**
	 * This method should only be used internally, when the config file is initially read after starting the program.
	 * It does not update the config file but reverses the accounts list.
	 */
	private static void reverseWithoutConfig() {
		accounts.reverse();
		home.resetAccountList(accounts);
	}

	/**
	 * Applies one of the settings that are saved in config file
	 * @param setting line from the config file, format: [key]=[value], e.g. "sort_by=name"
	 */
	private static void applyConfig(String setting) {
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
				if(rev) reverseWithoutConfig();
				break;
			}
			default: {
				throw new IllegalArgumentException("unknown setting: " + key);
			}
		}
	}

	/**
	 * returns the value of the given setting
	 * @param setting requested setting
	 * @return value of the given setting
	 */
	private static String readConfig(String setting) {
		final String[] result = new String[1];
		configFileManager.readLines().forEach(a -> {
			var line = a.split("=");
			if(line[0].equals(setting)) {
				result[0] = line[1];
			}
		});
		if(result[0] == null) throw new IllegalArgumentException();
		return result[0];
	}

	/**
	 * Sets the given setting to the given value in the config file
	 * @param setting setting to be changed
	 * @param value value of the setting
	 */
	private static void updateConfig(String setting, String value) {
		if (setting == null || value == null || setting.isEmpty() || value.isEmpty())
			throw new IllegalArgumentException("invalid setting");
		var setting_code = setting.strip().toLowerCase().replace(" ", "_");
		var value_code = value.strip().toLowerCase().replace(" ", "_");
		for(String line : configFileManager.readLines()) {
			if(line.startsWith(setting_code)) {
				configFileManager.deleteLine(line);
			}
		}
		configFileManager.writeLine(setting_code + "=" + value_code);
	}
}
