package gui.info;

import java.util.function.Function;

import valorant.Account;

public enum InfoDisplay {
	RIOTID		(acc -> new InfoButton("ID: ", acc.riotId(), true)),
	PASSWORD	(acc -> new InfoButton("Password: ", acc.password(), true)),
	NAME		(acc -> new InfoButton("Name: ", acc.name() + " #" + acc.tagline(),true)),
	CURRENCY	(acc -> new InfoButton("Currency: ", acc.currency().name(), false)),
	ADDITIONAL	(acc -> new InfoButton("Notes: ", acc.additional(), false));
	
	private final Function<Account, InfoButton> display;
	
	private InfoDisplay(Function<Account, InfoButton> display) {
		this.display = display;
	}
	
	public InfoButton display(Account acc) {
		return display.apply(acc);
	}
}
