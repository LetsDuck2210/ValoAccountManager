package gui.info;

import java.util.function.Function;

import valorant.Account;

public enum InfoDisplay {
	RIOTID		("ID: ", acc -> acc.riotId(), true),
	PASSWORD	("Password: ", acc -> acc.password(), true),
	NAME		("Name: ", acc -> acc.name() + " #" + acc.tagline(),true),
	CURRENCY	("Currency: ", acc -> acc.currency().name(), false),
	ADDITIONAL	("Notes: ", acc -> acc.additional(), false);
	
	private final InfoButton infoButton;
	private final Function<Account, String> display;	
	
	private InfoDisplay(String text, Function<Account, String> display, boolean enabled) {
		this.infoButton = new InfoButton(text, "", enabled);
		this.display = display;
	}
	
	public void display(Account acc) {
		infoButton.setValue(display.apply(acc));
	}
	
	public InfoButton getButton() {
		return infoButton;
	}
}
