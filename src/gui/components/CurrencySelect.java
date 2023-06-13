package gui.components;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import valorant.Currency;

public class CurrencySelect extends JPanel implements Input {
	private static final long serialVersionUID = -4446144214869466429L;
	private Currency select = Currency.OTHER;
	private JRadioButton defaultSelect;
	private Map<Currency, JRadioButton> map = new HashMap<>();

	public CurrencySelect() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(true);
		setBackground(GuiConstants.COMPONENT_COLOR);

		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
				new EmptyBorder(5, 5, 0, 5)));

		var label = new JLabel("Currency: ");
		label.setFont(GuiConstants.FONT);
		label.setForeground(GuiConstants.TEXT_COLOR);
		add(label);
		
		var defaultCurrency = Currency.OTHER;
		var bg = new ButtonGroup();
		for (var currency : Currency.values()) {
			var rb = new JRadioButton();
			map.put(currency, rb);
			rb.setText(currency.name());
			rb.addActionListener(a -> select = currency);
			if (currency == defaultCurrency)
				(defaultSelect = rb).setSelected(true);

			bg.add(rb);

			rb.setBackground(GuiConstants.COMPONENT_COLOR);
			rb.setForeground(GuiConstants.TEXT_COLOR);
			rb.setFont(GuiConstants.FONT);
			add(rb);
		}
	}

	@Override
	public String get() {
		return select.name();
	}

	// TODO: radio button has to reset to OTHER
	@Override
	public void clear() {
		select = Currency.OTHER;
		defaultSelect.setSelected(true);
	}

	@Override
	public boolean isFilled() {
		return true;
	}

	@Override
	public void set(String text) throws IllegalArgumentException {
		var currency = Currency.fromString(text);
		select = currency;
		map.forEach((c, r) -> {
			r.setSelected(false);
		});
		map.get(currency).setSelected(true);
	}
}
