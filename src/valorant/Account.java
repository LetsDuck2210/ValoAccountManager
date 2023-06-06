package valorant;

import java.awt.Image;

public class Account {
	private String riotId;
	private String password;
	private String name;
	private String additional;
	private Currency currency;
	
	public Account(String riotId, String pw, String name, String additional, Currency currency) {
		this.riotId = riotId;
		this.password = pw;
		this.name = name;
		this.additional = additional;
		this.currency = currency;
	}
	
	public Account(String riotId, String pw, String name, Currency currency) {
		this(riotId, pw, name, "", currency);
	}
	
	public Image getRankImage() {
		return null;
	}
	
}
