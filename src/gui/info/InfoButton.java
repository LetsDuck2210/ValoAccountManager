package gui.info;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;

import gui.GuiConstants;

public class InfoButton extends JButton {
	private static final long serialVersionUID = 6880347885377211418L;
	protected String text, value;

	public InfoButton(String text, String value) {
		super(text + value);
		this.text = text;
		this.value = value;
		
//		setPreferredSize(new Dimension(300, 80));
		setOpaque(true);

		setBackground(GuiConstants.COMPONENT_COLOR);
//		setForeground(GuiConstants.TEXT_COLOR);
		
		addActionListener(a -> {
			var clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(new StringSelection(value), null);
		});
	}

//	public abstract void showInformation(Account acc);
}
