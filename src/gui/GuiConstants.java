package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

@SuppressWarnings("unused")
public final class GuiConstants {
	public final static Font FONT = new Font("Arial", 0, 20);
	private static final Color 	
			DARK_COMPONENTS = new Color(0x45454c),
			DARK_COMPONENTS_ALT = new Color(0x676871),
			DARK_BACKGROUND = new Color(0x222326),
			DARK_BACKGROUND_ALT = new Color(0x676871),
			DARK_TEXT = Color.white,
			DARK_TEXT_WARNING = Color.red,

			LIGHT_COMPONENTS = new Color(125, 140, 255),
			LIGHT_BACKGROUND = Color.white,
			LIGHT_TEXT = Color.black;
	
	public static final Color 
			COMPONENT_COLOR = DARK_COMPONENTS,
			COMPONENT_COLOR_ALT = DARK_COMPONENTS_ALT,
			BACKGROUND_COLOR = DARK_BACKGROUND,
			BACKGROUND_COLOR_ALT = DARK_BACKGROUND_ALT,
			TEXT_COLOR = DARK_TEXT,
			TEXT_COLOR_WARNING = DARK_TEXT_WARNING;
	
	public static final Dimension 
			PREFERED_SIZE = new Dimension(800, 600),
			CROSSHAIR_SIZE = new Dimension(125, 125);
	public static final int
			TASKBAR_HEIGHT = 30,
			LISTBUTTON_HEIGHT = 50,
			DEFAULT_HGAP = 2,
			DEFAULT_VGAP = 5;
}
