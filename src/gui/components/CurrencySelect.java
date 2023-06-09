package gui.components;

import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;
import valorant.Currency;

public class CurrencySelect extends JPanel implements Input {
	private static final long serialVersionUID = -4446144214869466429L;
	private Currency select = Currency.OTHER;
	private JRadioButton otherButton;
	private JRadioButton tryButton;

	public CurrencySelect() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setOpaque(true);
		setBackground(GuiConstants.COMPONENT_COLOR);
		
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR), new EmptyBorder(5, 5, 0, 5)));
		
		var bg = new ButtonGroup();
		otherButton = new JRadioButton();
		tryButton = new JRadioButton();
		var radioButtons = new HashSet<JRadioButton>();
		radioButtons.add(tryButton);
		radioButtons.add(otherButton);
		otherButton.setSelected(true);
		
		tryButton.addActionListener(a -> {
			select = Currency.TRY;
			System.out.println("selected: " + Currency.TRY.toString() + ", get(): " + get());
		});
		otherButton.addActionListener(a -> select = Currency.OTHER);
		
		tryButton.setText("TRY");
		otherButton.setText("Other");
		
		for(var button : radioButtons) {
			button.setBackground(GuiConstants.COMPONENT_COLOR);
			button.setForeground(GuiConstants.TEXT_COLOR);
			button.setFont(GuiConstants.FONT);
			bg.add(button);
			add(button);
		}
		
	}

	@Override
	public String get() {
		return select == Currency.OTHER ? "OTHER" : "TRY";
	}

	@Override
	public void clear() {
		otherButton.setSelected(true);
		tryButton.setSelected(false);
		select = Currency.OTHER;
	}

	@Override
	public boolean isFilled() {
		return true;
	}
}
