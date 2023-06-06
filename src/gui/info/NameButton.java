package gui.info;

import valorant.Account;

public class NameButton extends InfoButton {
	private static final long serialVersionUID = 7215515476178622057L;

	public NameButton() {
		super();
	}

	@Override
	public void showInformation(Account acc) {
		value = acc.getName();
		setText("Name: " + value);
	}
}
