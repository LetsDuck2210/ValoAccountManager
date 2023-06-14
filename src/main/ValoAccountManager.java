package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
		accountFileManager.ensureExists();
		crosshairFileManager.ensureExists();
		for (var line : accountFileManager.readLines()) {
			var acc = Account.fromString(line);
			accounts.add(acc);
			acc.getRankIcon(null);
		}
		crosshairFileManager.readLines().forEach(l -> crosshairs.add(l));

		UIManager.setLookAndFeel(new MetalLookAndFeel());
		new ValoAccountManager();

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
}
