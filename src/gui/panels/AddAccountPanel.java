package gui.panels;

import java.util.ArrayList;

import gui.components.Input;
import main.ValoAccountManager;
import valorant.Account;
import valorant.Currency;

public class AddAccountPanel extends AccountPanel {
	private static final long serialVersionUID = -1081282648038615739L;
	
	public AddAccountPanel() {
		super("Add Account");
	}
	
	@Override
	void submit() {
		if (informationFilled()) {
			ValoAccountManager.addAccount(fromInput());
			clearInputs();
		}
	}

	private Account fromInput() {
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

		var acc = new Account(riotId, password, name, tag, additional, currency);
		acc.getRank(null);
		return acc;
	}

	void clearInputs() {
		for (var input : inputs)
			input.clear();
	}

	boolean informationFilled() {
		var filled = true;
		var neccessaryInputs = new ArrayList<Input>();
		for (int i = 0; i < 3; i++)
			neccessaryInputs.add(inputs.get(i));
		for (var input : neccessaryInputs)
			if (!input.isFilled())
				filled = false;

		var fullName = neccessaryInputs.get(2).get();
		if (fullName.split("#").length != 2)
			return false;
		var name = fullName.split("#")[0];
		var tag = fullName.split("#")[1];
		if (name.length() > 16 || tag.length() > 5)
			filled = false;

		return filled;
	}

}
