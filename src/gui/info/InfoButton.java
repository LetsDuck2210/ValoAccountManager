package gui.info;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import gui.GuiConstants;

public class InfoButton extends JButton {
	private static final long serialVersionUID = 6880347885377211418L;
	protected String text, value;

	public InfoButton(String text, String value) {		
		super(text + value);
		
		this.text = text;
		this.value = value;
		
		//TODO: this is ugly
		if(text.equals("Currency: ") || text.equals("Notes: "))
			setEnabled(false);
		
		setOpaque(true);
		setFont(GuiConstants.FONT);
		setHorizontalAlignment(SwingConstants.LEFT);

		setBackground(GuiConstants.COMPONENT_COLOR);
		setForeground(GuiConstants.TEXT_COLOR);
		
		addActionListener(a -> {
			var clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(new StringSelection(value), null);
		});
	}
}
