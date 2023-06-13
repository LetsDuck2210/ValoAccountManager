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
import gui.components.EmptyComponent;
import gui.components.Headline;
import gui.components.Input;
import gui.components.InputField;
import valorant.ValoConstants;

public abstract class AccountPanel extends JPanel {
	private static final long serialVersionUID = -1081282648038615739L;
	List<Input> inputs = new ArrayList<>();

	public AccountPanel(String headline) {
		var layout = new GridLayout(8, 1);
		setLayout(layout);
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);

		add(new Headline(headline));
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
			submit();
		});
		submit.setBorder(BorderFactory.createCompoundBorder(
				new MatteBorder(5, 0, 10, 5, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 0, 10, 5)));

		add(new EmptyComponent());
		add(submit);
	}

	abstract void submit();
	
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
		var name = fullName.split("#")[0];
		var tag = fullName.split("#")[1];
		if (name.length() > ValoConstants.MAX_NAME_LENGTH || tag.length() > ValoConstants.MAX_TAG_LENGTH)
			filled = false;

		return filled;
	}
}
