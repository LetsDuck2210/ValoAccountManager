package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

@SuppressWarnings("unused")
public final class GuiConstants {
	public final static Font FONT = new Font("Arial", 0, 20);
	private final static Color DARK_COMPONENTS = new Color(0, 0, 80);
	private final static Color DARK_BACKGROUND = Color.DARK_GRAY;
	private final static Color DARK_TEXT = Color.white;
	
	private final static Color LIGHT_COMPONENTS = new Color(125, 140, 255);
	private final static Color LIGHT_BACKGROUND = Color.white;
	private static final Color LIGHT_TEXT = Color.black;
	
	public final static Color COMPONENT_COLOR = DARK_COMPONENTS;
	public static final Color BACKGROUND_COLOR = DARK_BACKGROUND;
	public static final Color TEXT_COLOR = DARK_TEXT;
	
	public static final Dimension PREFERED_SIZE = new Dimension(800, 600);
}
