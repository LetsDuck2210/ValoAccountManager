package gui.components;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import gui.GuiConstants;
import main.ValoAccountManager;
import valorant.crosshair.CrosshairConverter;
import valorant.crosshair.OfflineConverter;

public class CrosshairButton extends JButton {
	private static final long serialVersionUID = -6025143413244650519L;
	private final String code;
	private CrosshairConverter converter;
	
	public CrosshairButton(String code) {
		super();
		this.code = code;
		
		setPreferredSize(GuiConstants.CROSSHAIR_SIZE);
		setOpaque(true);
		setBackground(GuiConstants.COMPONENT_COLOR_ALT);
		
		converter = new OfflineConverter(getPreferredSize());
		setIcon(new ImageIcon(converter.convert(this.code)));
		
		addActionListener(a -> {
			var clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			clip.setContents(new StringSelection((this.code)), null);
		});
		var inst = this;
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				ValoAccountManager.setCurrentCrosshair(Optional.of(inst));
			}
		});
		
		repaint();
	}

	public String getCode() {
		return code;
	}
	
	
	public CrosshairButton clone() {
		var btn = new CrosshairButton(code);
		btn.converter = converter;
		return btn;
	}
}
