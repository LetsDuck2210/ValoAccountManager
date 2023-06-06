package gui.info;

import valorant.Account;

public class RiotidButton extends InfoButton {
	private static final long serialVersionUID = -877504883281813743L;

	public RiotidButton() {
		super();
	}

	@Override
	public void showInformation(Account acc) {
		value = acc.getRiotId();
		setText("RiotID: " + value);
	}
}
