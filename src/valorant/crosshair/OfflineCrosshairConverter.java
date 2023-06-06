package valorant.crosshair;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class OfflineCrosshairConverter implements CrosshairConverter {
//	public static final float SCALE = 2;
	private enum Direction {
		UP(0), DOWN(2), LEFT(1), RIGHT(3);
		
		final int code;
		private Direction(int code) {
			this.code = code;
		}
	}

	@Override
	public Image convert(String code, Dimension outputSize) {
		var settings = new ProfileSettings(code);
		var crosshairSize = Math.max(
			settings.outerLines.lineLength + settings.outerLines.lineOffset,
			settings.innerLines.lineLength + settings.innerLines.lineOffset
		) + settings.outlineThickness; // max size of crosshair
		var minSize = Math.min(outputSize.width, outputSize.height);
		var scale = minSize / (crosshairSize * 2f); // calc scale to fit outputSize
		settings = settings.copyWithScale(scale);
		
		var img = new BufferedImage(outputSize.width, outputSize.height, BufferedImage.TYPE_INT_ARGB);
		var g = (Graphics2D) img.getGraphics();
		for(var dir : Direction.values())
			drawArm(img, settings, dir);
		
		var cds = settings.centerDotSize;
		drawRect(g, settings, settings.centerDotOpacity, 
				new Rectangle(
					round(img.getWidth() / 2f - cds / 2),
					round(img.getHeight() / 2f - cds / 2),
					round(cds),
					round(cds)
				)
		);
		
		return img;
	}

	private void drawArm(BufferedImage img, ProfileSettings settings, Direction dir) {
		float innerTh = settings.innerLines.lineThickness , innerLen = settings.innerLines.lineLength ;
		var innerRect = dir.code % 2 == 0 ? 
				new Dimension(round(innerTh ), round(innerLen ))
			  : new Dimension(round(innerLen ), round(innerTh ));
		
		float outerTh = settings.outerLines.lineThickness , outerLen = settings.outerLines.lineLength ;
		var outerRect = dir.code % 2 == 0 ?
				new Dimension(round(outerTh ), round(outerLen ))
			  : new Dimension(round(outerLen ), round(outerTh ));
		
		var innerPos = calcArmPosition(dir, new Dimension(img.getWidth(), img.getHeight()), 
												innerTh, innerLen, settings.innerLines.lineOffset  );
		var outerPos = calcArmPosition(dir, new Dimension(img.getWidth(), img.getHeight()),
												outerTh, outerLen, settings.outerLines.lineOffset  );
		
		var g = (Graphics2D) img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(settings.innerLines.showLines)
			drawRect(g, settings, settings.innerLines.opacity,
					new Rectangle(innerPos.x, innerPos.y, innerRect.width, innerRect.height));
		if(settings.outerLines.showLines)
			drawRect(g, settings, settings.outerLines.opacity,
					new Rectangle(outerPos.x, outerPos.y, outerRect.width, outerRect.height));
	}
	
	private void drawRect(Graphics2D g, ProfileSettings settings, float opacity, Rectangle rect) {
		var outerCol = new Color(
			settings.color.getRed(),
			settings.color.getGreen(),
			settings.color.getBlue(),
			round(opacity * 255)
		);
		g.setColor(outerCol); // use line specific opacity
		g.fillRect(rect.x, rect.y, round(rect.width), round(rect.height));
		
		g.setColor(new Color(0, 0, 0, round(settings.outlineOpacity * 255)));
		g.setStroke(new BasicStroke(settings.outlineThickness));
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
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
