package gui.info;

import java.util.function.Function;

import valorant.Account;

public enum InfoDisplay {
	RIOTID		(Account::riotId, true),
	PASSWORD	(Account::password, true),
	NAME		(acc -> acc.name() + " #" + acc.tagline(), true),
	CURRENCY	(acc -> acc.currency().name(), false),
	ADDITIONAL	(Account::additional, false);
	
	private final InfoButton infoButton;
	private final Function<Account, String> display;	
	
	InfoDisplay(Function<Account, String> display, boolean enabled) {
		this.infoButton = new InfoButton("", enabled);
		this.display = display;
	}
	
	public void display(Account acc) {
		infoButton.setValue(display.apply(acc));
	}
	
	public InfoButton getButton() {
		return infoButton;
	}
}
