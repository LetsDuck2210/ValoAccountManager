package gui.components;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import valorant.Currency;

public class CurrencySelect extends JPanel implements Input {
	private static final long serialVersionUID = -4446144214869466429L;
	private Currency select = Currency.OTHER;

	public CurrencySelect() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(true);
		setBackground(GuiConstants.COMPONENT_COLOR);
		
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 5, 0, 5)));
		
		var defaultCurrency = Currency.OTHER;
		var bg = new ButtonGroup();
		for(var currency : Currency.values()) {
			var rb = new JRadioButton();
			rb.setText(currency.name());
			rb.addActionListener(a -> select = currency);
			if(currency == defaultCurrency)
				rb.setSelected(true);
			
			bg.add(rb);
			
			rb.setBackground(GuiConstants.COMPONENT_COLOR);
			rb.setForeground(GuiConstants.TEXT_COLOR);
			rb.setFont(GuiConstants.FONT);
			add(rb);
		}
	}

	//TODO: iterate over Currency.values()
	@Override
	public String get() {
		return select == Currency.OTHER ? "OTHER" : "TRY";
	}

	//TODO: radio button has to reset to OTHER
	@Override
	public void clear() {
		select = Currency.OTHER;
	}

	@Override
	public boolean isFilled() {
		return true;
	}
}
