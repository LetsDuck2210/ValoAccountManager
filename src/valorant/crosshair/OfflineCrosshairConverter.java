package valorant.crosshair;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class OfflineCrosshairConverter implements CrosshairConverter {
	private enum Direction {
		UP(0), DOWN(2), LEFT(1), RIGHT(3);
		
		final int code;
		private Direction(int code) {
			this.code = code;
		}
	}

	@Override
	public Image convert(String code) {
		var settings = new ProfileSettings(code);
		var img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		
		for(var dir : Direction.values())
			drawArm(img, settings, dir);
		
		return null;
	}

	private void drawArm(BufferedImage img, ProfileSettings settings, Direction dir) {
		float innerTh = settings.innerLines.lineThickness, innerLen = settings.innerLines.lineLength;
		var innerRect = dir.code % 2 == 0 ? 
				new Dimension(round(innerTh), round(innerLen))
			  : new Dimension(round(innerLen), round(innerTh));
		
		float outerTh = settings.outerLines.lineThickness, outerLen = settings.outerLines.lineLength;
		var outerRect = dir.code % 2 == 0 ?
				new Dimension(round(outerTh), round(outerLen))
			  : new Dimension(round(outerLen), round(outerTh));
		
		var innerPos = calcArmPosition(dir, new Dimension(img.getWidth(), img.getHeight()), 
												innerTh, innerLen, settings.innerLines.lineOffset);
		var outerPos = calcArmPosition(dir, new Dimension(img.getWidth(), img.getHeight()),
												outerTh, outerLen, settings.outerLines.lineOffset);
		
		var g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(settings.innerLines.showLines)
			drawArm(g, settings.color, settings.innerLines, new Rectangle(innerPos.x, innerPos.y, innerRect.width, innerRect.height));
		if(settings.outerLines.showLines)
			drawArm(g, settings.color, settings.outerLines, new Rectangle(outerPos.x, outerPos.y, outerRect.width, outerRect.height));
		
		g.setColor(settings.color);
	}
	
	private void drawArm(Graphics2D g, Color color, LineSettings line, Rectangle rect) {
		var outerCol = new Color(
			color.getRed(),
			color.getGreen(),
			color.getBlue(),
			line.opacity * 255
		);
		g.setColor(outerCol); // use line specific opacity
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	private Point calcArmPosition(Direction dir, Dimension size, float thickness, float length, float offset) {
		int x = switch(dir) {
				case UP, DOWN -> round(size.width / 2 - thickness / 2); 		// horiz centered
				case LEFT -> round(size.width / 2 - offset - length);
				case RIGHT -> round(size.width / 2 + offset);
			},
			y = switch(dir) {
				case LEFT, RIGHT -> round(size.height / 2 - thickness / 2); 	// vert centered
				case UP -> round(size.height / 2 - offset - length);
				case DOWN -> round(size.height / 2 + offset);
			};
		return new Point(x, y);
	}
	
	private int round(float f) {
		return (int) Math.round(f);
	}
}
