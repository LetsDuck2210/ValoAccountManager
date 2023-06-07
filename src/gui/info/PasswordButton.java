package gui.info;

import valorant.Account;

public class PasswordButton extends InfoButton {
	private static final long serialVersionUID = 7098995499102081948L;

	public PasswordButton() {
		super();
	}

	@Override
	public void showInformation(Account acc) {
		value = acc.getPassword();
		setText("Password: " + value);
	}
	
	
}
