package gui.info;

import valorant.Account;

public class CurrencyButton extends InfoButton {
	private static final long serialVersionUID = -4906404618702404446L;

	public CurrencyButton() {
		super();
		setEnabled(false);
	}

	@Override
	public void showInformation(Account acc) {
		value = acc.currency().toString();
		setText("Currency: " + value);
	}
}
