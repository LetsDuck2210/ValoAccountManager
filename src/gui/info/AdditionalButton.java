package gui.info;

import valorant.Account;

public class AdditionalButton extends InfoButton {
	private static final long serialVersionUID = -1466362417807649688L;

	public AdditionalButton() {
		super();
		setEnabled(false);
	}

	@Override
	public void showInformation(Account acc) {
		value = acc.getAdditional();
		setText("Additional: " + value);
	}
	
}
