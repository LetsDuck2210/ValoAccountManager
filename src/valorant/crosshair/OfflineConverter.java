package valorant.crosshair;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class OfflineConverter implements CrosshairConverter {
//	public static final float SCALE = 2;
	private enum Direction {
		UP(0), DOWN(2), LEFT(1), RIGHT(3);
		
		final int code;
		private Direction(int code) {
			this.code = code;
		}
	}
	private float scale;
	private Dimension outputSize;
	
	public OfflineConverter(Dimension outputSize) {
		this.outputSize = outputSize;
	}

	@Override
	public Image convert(String code) {
		var settings = new ProfileSettings(code);
		var crosshairSize = Math.max(
			settings.outerLines.horizLength + settings.outerLines.lineOffset,
			settings.innerLines.horizLength + settings.innerLines.lineOffset
		) + settings.outlineThickness; // max size of crosshair
		var minSize = Math.min(outputSize.width, outputSize.height);
		scale = minSize / (crosshairSize * 2f); // calc scale to fit outputSize
		settings = settings.copyWithScale(Math.round(scale * 1000f) / 1000f);
		
		var img = new BufferedImage(outputSize.width, outputSize.height, BufferedImage.TYPE_INT_ARGB);
		for(var dir : Direction.values())
			drawArm(img, settings, dir);
		
		drawCenterDot(img, settings);
		
		return img;
	}
	@Override
	public Dimension getOutputSize() {
		return outputSize;
	}
	
	@Override
	public void setOutputSize(Dimension outputSize) {
		this.outputSize = outputSize;
	}
	
	private void drawArm(BufferedImage img, ProfileSettings settings, Direction dir) {
		float innerTh = settings.innerLines.lineThickness, 
			innerLenHoriz = settings.innerLines.horizLength, innerLenVert = settings.innerLines.vertLength;
		var innerRect = dir.code % 2 == 0 ? 
				new Dimension(round(innerTh), round(innerLenVert)) 	// vert
			  : new Dimension(round(innerLenHoriz), round(innerTh));// horiz
		
		float outerTh = settings.outerLines.lineThickness,
			outerLenHoriz = settings.outerLines.horizLength, outerLenVert = settings.outerLines.vertLength;
		var outerRect = dir.code % 2 == 0 ?
				new Dimension(round(outerTh), round(outerLenVert)) 	// vert
			  : new Dimension(round(outerLenHoriz), round(outerTh));// horiz
		
		var innerPos = calcArmPosition(dir, new Dimension(img.getWidth(), img.getHeight()),
												settings.innerLines);
		var outerPos = calcArmPosition(dir, new Dimension(img.getWidth(), img.getHeight()),
												settings.outerLines);
		
		var g = (Graphics2D) img.getGraphics();
		if(settings.innerLines.showLines)
			drawRect(g, settings, settings.innerLines.opacity,
					new Rectangle(innerPos.x, innerPos.y, innerRect.width, innerRect.height));
		if(settings.outerLines.showLines)
			drawRect(g, settings, settings.outerLines.opacity,
					new Rectangle(outerPos.x, outerPos.y, outerRect.width, outerRect.height));
	}
	
	private void drawCenterDot(BufferedImage img, ProfileSettings settings) {
		var g = (Graphics2D) img.getGraphics();
		
		if(!settings.displayCenterDot) {
			var cs = scale;
			g.setBackground(withAlpha(Color.black, 0));
			g.clearRect(
				round(img.getWidth() / 2f - cs / 2), 
				round(img.getHeight() / 2f - cs / 2),
				round(cs),
				round(cs)
			);
			return;
		}
		
		var cds = settings.centerDotSize + scale * 1.1f;
		var outt = settings.outlineThickness;
		
		if(settings.hasOutline) {
			g.setColor(withAlpha(Color.black, round(settings.outlineOpacity * 255)));
			g.setStroke(new BasicStroke(settings.outlineThickness));
			var cdos = cds + outt + scale / 2;
			g.drawRect(
				round(img.getWidth() / 2f - cdos / 2), // x center
				round(img.getHeight() / 2f - cdos / 2),// y center
				round(cdos),
				round(cdos)
			);
		}
		
		g.setColor(withAlpha(settings.color, round(settings.centerDotOpacity * 255)));
		g.setStroke(new BasicStroke());
		g.fillRect(
			round(img.getWidth() / 2f - cds / 2), // x center
			round(img.getHeight() / 2f - cds / 2),// y center
			round(cds),
			round(cds)
		);
	}
	private void drawRect(Graphics2D g, ProfileSettings settings, float opacity, Rectangle rect) {
		if(rect.width <= 0 || rect.height <= 0) return;
		
		g.setColor(withAlpha(settings.color, round(opacity * 255)));
		g.fillRect(rect.x, rect.y, round(rect.width), round(rect.height));
		
		if(settings.hasOutline) {
			g.setColor(new Color(0, 0, 0, round(settings.outlineOpacity * 255)));
			g.setStroke(new BasicStroke(settings.outlineThickness));
			g.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
	
	private Color withAlpha(Color orig, int alpha) {
		return new Color(
			orig.getRed(),
			orig.getGreen(),
			orig.getBlue(),
			alpha
		);
	}
	
	private Point calcArmPosition(Direction dir, Dimension size, LineSettings line) {
		var thickness = line.lineThickness;
		var offset = line.lineOffset + scale / 2;
		var length = line.horizLength;
		int x = switch(dir) {
				case UP, DOWN -> round(size.width / 2f - thickness / 2); 		// horiz centered
				case LEFT -> round(size.width / 2f - offset - length);
				case RIGHT -> round(size.width / 2f + offset);
			},
			y = switch(dir) {
				case LEFT, RIGHT -> round(size.height / 2f - thickness / 2); 	// vert centered
				case UP -> round(size.height / 2f - offset - length);
				case DOWN -> round(size.height / 2f + offset);
			};
		return new Point(x, y);
	}
	
	private int round(float f) {
		return (int) Math.round(f);
	}
}
