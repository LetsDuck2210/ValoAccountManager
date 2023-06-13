package gui.info;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import gui.GuiConstants;

class InfoButton extends JButton {
	private static final long serialVersionUID = 6880347885377211418L;
	protected String text, value;

	public InfoButton(String text, String value, boolean enabled) {
		super(text + value);

		this.text = text;
		this.value = value;

		setEnabled(enabled);

		setFocusPainted(false);
		setOpaque(true);
		setFont(GuiConstants.FONT);
		setHorizontalAlignment(SwingConstants.LEFT);
		setBorder(BorderFactory.createCompoundBorder(new MatteBorder(5, 5, 0, 5, GuiConstants.BACKGROUND_COLOR),
				new EmptyBorder(5, 5, 0, 5)));

		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);

		addActionListener(a -> {
			var clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(new StringSelection(this.value), null);
		});
	}

	public void setValue(String value) {
		this.value = value;
		setText(text + value);
	}
}
