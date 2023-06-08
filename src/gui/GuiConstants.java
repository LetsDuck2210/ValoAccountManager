package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

@SuppressWarnings("unused")
public final class GuiConstants {
	public final static Font FONT = new Font("Arial", 0, 20);
	private static final Color 	DARK_COMPONENTS = new Color(0, 0, 80),
								DARK_COMPONENTS_ALT = new Color(30, 30, 120),
								DARK_BACKGROUND = Color.DARK_GRAY,
								DARK_BACKGROUND_ALT = new Color(0x1f1f1f),
								DARK_TEXT = Color.white,

								LIGHT_COMPONENTS = new Color(125, 140, 255),
								LIGHT_BACKGROUND = Color.white,
								LIGHT_TEXT = Color.black;
	
	public static final Color 	COMPONENT_COLOR = DARK_COMPONENTS,
								COMPONENT_COLOR_ALT = DARK_COMPONENTS_ALT,
								BACKGROUND_COLOR = DARK_BACKGROUND,
								BACKGROUND_COLOR_ALT = DARK_BACKGROUND_ALT,
								TEXT_COLOR = DARK_TEXT;
	
	public static final Dimension PREFERED_SIZE = new Dimension(800, 600);
	public static final int TASKBAR_HEIGHT = 30;
}
