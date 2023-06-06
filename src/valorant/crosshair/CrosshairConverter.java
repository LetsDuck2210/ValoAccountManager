package valorant.crosshair;

import java.awt.Dimension;
import java.awt.Image;

public interface CrosshairConverter {
	public Image convert(String code, Dimension outputSize);
}
