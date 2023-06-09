package gui.panels;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import gui.components.CurrencySelect;
import gui.components.DoubleInputField;
import gui.components.Headline;
import gui.components.Input;
import gui.components.InputField;
import main.ValoAccountManager;
import valorant.Account;
import valorant.Currency;

public class AddAccountPanel extends JPanel {
	private static final long serialVersionUID = -1081282648038615739L;
	private List<Input> inputs = new ArrayList<>();

	public AddAccountPanel() {
		var layout = new GridLayout(8, 1);
		setLayout(layout);
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);

		add(new Headline("Add Account"));
		inputs.add(new InputField("ID: "));
		inputs.add(new InputField("Password: "));
		inputs.add(new DoubleInputField("Name: ", "# "));
		inputs.add(new CurrencySelect());
		inputs.add(new InputField("Notes: "));

		for (var input : inputs)
			add((JComponent) input);

		var submit = new JButton();
		submit.setText("submit");
		submit.setOpaque(true);
		submit.setBackground(GuiConstants.COMPONENT_COLOR);
		submit.setForeground(GuiConstants.TEXT_COLOR);
		submit.setFont(GuiConstants.FONT);
		submit.addActionListener(a -> {
			if(informationFilled()) {
				ValoAccountManager.addAccount(fromInput());
				clearInputs();
				
			}
		});
		submit.setBorder(BorderFactory.createCompoundBorder(
				new MatteBorder(5, 0, 10, 5, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 0, 10, 5)));

		add(new JComponent() {
			private static final long serialVersionUID = 1045449690138475005L;
		});
		add(submit);
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

		return new Account(riotId, password, name, tag, additional, currency);
	}

	private void clearInputs() {
		for (var input : inputs)
			input.clear();
	}

	private boolean informationFilled() {
		var filled = true;
		var neccessaryInputs = new ArrayList<Input>();
		for (int i = 0; i < 3; i++)
			neccessaryInputs.add(inputs.get(i));
		for(var input : neccessaryInputs) 
			if(!input.isFilled()) filled = false;
		return filled;
	}
}
