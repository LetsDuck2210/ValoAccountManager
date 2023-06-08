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

public class AddAccountPanel extends JPanel {
	private static final long serialVersionUID = -1081282648038615739L;
	private List<Input> inputs = new ArrayList<>();

	public AddAccountPanel() {
		var layout = new GridLayout(8, 1);
		setLayout(layout);
		setBackground(GuiConstants.BACKGROUND_COLOR);
		setOpaque(true);

		add(new Headline("Add Account"));
		inputs.add(new InputField("ID"));
		inputs.add(new InputField("Password"));
		inputs.add(new DoubleInputField("Name", "#"));
		inputs.add(new CurrencySelect());
		inputs.add(new InputField("Notes"));
		
		for (var input : inputs) {
			add((JComponent) input);
		}

		var submit = new JButton();
		submit.setText("submit");
		submit.setBackground(GuiConstants.COMPONENT_COLOR);
		submit.setForeground(GuiConstants.TEXT_COLOR);
		submit.setFont(GuiConstants.FONT);
		submit.addActionListener(a -> {
			addAccount();
		});
		submit.setBorder(BorderFactory.createCompoundBorder(
				new MatteBorder(10, 150, 10, 150, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 5, 0, 5)));

		add(new JComponent() {
			private static final long serialVersionUID = 1045449690138475005L;
		});
		add(submit);
	}
	//TODO
	private void addAccount() {
		
	}
}
