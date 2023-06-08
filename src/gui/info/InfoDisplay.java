package gui.info;

import java.util.function.Function;

import valorant.Account;

public enum InfoDisplay {
	RIOTID		(acc -> new InfoButton("ID: ", acc.riotId())),
	PASSWORD	(acc -> new InfoButton("Password: ", acc.password())),
	NAME		(acc -> new InfoButton("Name: ", acc.name() + " #" + acc.tagline())),
	CURRENCY	(acc -> new InfoButton("Currency: ", acc.currency().name())),
	ADDITIONAL	(acc -> new InfoButton("Notes: ", acc.additional()));
	
	private final Function<Account, InfoButton> display;
	
	private InfoDisplay(Function<Account, InfoButton> display) {
		this.display = display;
	}
	
	public InfoButton display(Account acc) {
		return display.apply(acc);
	}
}
