package gui.panels;

import main.ValoAccountManager;
import valorant.Account;
import valorant.Currency;

public class EditAccountPanel extends AccountPanel implements Updatable {
	private static final long serialVersionUID = -3543949683339232381L;

	public EditAccountPanel() {
		super("Edit Account");

	}

	@Override
	void submit() {
		if(!ValoAccountManager.getCurrentAccount().isEmpty()) {
			if(informationFilled()) {
				editAccount(ValoAccountManager.getCurrentAccount().get());
				clearInputs();
			}
		}
	}
	
	private void editAccount(Account acc) {
		var accountData = new String[5];
		for (int i = 0; i < inputs.size(); i++) {
			accountData[i] = inputs.get(i).get();
		}
		var riotId = accountData[0];
		var password = accountData[1];
		var fullName = accountData[2].split("#");
		var name = fullName[0];
		var tag = fullName[1];
		var currency = Currency.fromString(accountData[3]);
		var additional = accountData[4];
		
		ValoAccountManager.removeAccount(acc);
		ValoAccountManager.addAccount(new Account(riotId, password, name, tag, additional, currency));
	}

	@Override
	public void update(Account acc) {
		this.inputs.get(0).set(acc.riotId());
		this.inputs.get(1).set(acc.password());
		this.inputs.get(2).set(acc.name() + "#" + acc.tagline());
		this.inputs.get(3).set(acc.currency().toString());
		this.inputs.get(4).set(acc.additional());
		revalidate();
		repaint();
	}

}
