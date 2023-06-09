package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalLookAndFeel;

import gui.GuiConstants;
import gui.home.HomeScreen;
import util.FileManager;
import valorant.Account;
import valorant.Currency;

public class ValoAccountManager extends JFrame {
	private static final long serialVersionUID = -1983716182184486275L;
	private static List<Account> accounts = new ArrayList<>();
	private static FileManager fm = new FileManager("accounts.txt");
	private static Optional<Account> currentAccount = Optional.empty();
	private static HomeScreen home = new HomeScreen();

	public static void main(String[] args) throws IOException, UnsupportedLookAndFeelException {
		for(var line : fm.readLines()) {
			var acc = Account.fromString(line);
			accounts.add(acc);
			acc.getRankIcon(null);
		}
		
		UIManager.setLookAndFeel(new MetalLookAndFeel());
		new ValoAccountManager();
		
		for(var acc : accounts)
			home.addAccountToList(acc);
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
		showAccount(new Account("", "", "", "", "", Currency.OTHER));
		
		repaint();
		revalidate();
	}
	
	public static void showAccount(Account acc) {
		currentAccount = Optional.of(acc);
		home.showAccount(acc);
	}

	public static void addAccount(Account acc) {
		System.out.println("Account is being added: " + acc.name());
		fm.writeLine(acc.toString());
		accounts.add(acc);
		home.addAccountToList(acc);
		home.update();       
	}
	
	public Optional<Account> getCurrentAccount() {
		return currentAccount;
	}
}
