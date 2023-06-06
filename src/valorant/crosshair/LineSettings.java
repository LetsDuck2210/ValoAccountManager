package valorant.crosshair;

public class LineSettings {
    
	public float lineThickness;
    public float lineLength;
    public float lineOffset;
    public float opacity;
    public boolean showLines;
    
    public static LineSettings innerDefaults() {
    	var s = new LineSettings();
    	s.lineThickness = 2;
    	s.lineLength = 6;
    	s.lineOffset = 3;
    	s.opacity = 0.8f;
    	s.showLines = true;
    	return s;
    }
    public static LineSettings outerDefaults() {
    	var s = new LineSettings();
    	s.lineThickness = 2;
    	s.lineLength = 2;
    	s.lineOffset = 10;
    	s.opacity = 0.35f;
    	s.showLines = true;
    	return s;
    }
    
    @Override
	public String toString() {
		return "LineSettings [lineThickness=" + lineThickness + ", lineLength=" + lineLength + ", lineOffset="
				+ lineOffset + ", opacity=" + opacity + ", showLines=" + showLines + "]";
	}
}
