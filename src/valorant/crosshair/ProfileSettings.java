package valorant.crosshair;

import java.awt.Color;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.Float.parseFloat;

public class ProfileSettings {
    public Color color = Color.white;
    public boolean useCustomColor = false;
    public boolean hasOutline = true;
    public float outlineThickness = 1f;
    public Color outlineColor;
    public float outlineOpacity = 0.5f;
    public float centerDotSize = 2;
    public float centerDotOpacity = 1;
    public boolean displayCenterDot = false;
    public boolean hideCrosshair;
    public LineSettings innerLines = LineSettings.innerDefaults();
    public LineSettings outerLines = LineSettings.outerDefaults();
    
    private static final Color[] colorPresets = new Color[] {
		Color.white, 				// default
		new Color(3, 255, 0),		// green
		new Color(187, 255, 0),		// yellow green
		new Color(240, 255, 0),		// green yellow
		new Color(255, 255, 0),		// yellow
		new Color(3, 255, 255),		// cyan
		new Color(255, 0, 255),		// pink
		Color.red,
		Color.white					// custom
	};
    
    private Color hexToColor(String hex) {
    	var r = parseInt(hex.substring(0, 2), 16);
    	var g = parseInt(hex.substring(2, 4), 16);
    	var b = parseInt(hex.substring(4, 6), 16);
    	int a = hex.length() == 8 ? a = parseInt(hex.substring(6), 16) : 255;
    	
    	return new Color(r, g, b, a);
    }
    
    // Map<class => Map<prefix => Map<char => variable setter>>>
    private Map<String, Map<String, Map<Character, Consumer<String>>>> tables = Map.of(
    	"P", Map.of( 	// class: primary
	    	"", Map.of( 	// general
		    	'c', colorCode -> { 
		    		this.color = colorPresets[parseInt(colorCode)];
		    		this.useCustomColor = (parseInt(colorCode) == 8); 
		    	},
		    	'u', colorHex -> this.color = hexToColor(colorHex),
		    	'h', outlines -> this.hasOutline = (parseInt(outlines) == 1),
		    	'o', opac -> this.outlineOpacity = parseFloat(opac),
		    	't', thick -> this.outlineThickness = parseFloat(thick),
		    	'd', cd -> this.displayCenterDot = (parseInt(cd) == 1),
		    	'a', cdOpac -> this.centerDotOpacity = parseFloat(cdOpac),
		    	'z', cdSize -> this.centerDotSize = parseFloat(cdSize)
	    	),
	    	"0", Map.of( // inner line
	    		'b', show -> this.innerLines.showLines = (parseInt(show) == 1),
	    		'a', opac -> this.innerLines.opacity = parseFloat(opac),
	    		'l', len -> this.innerLines.horizLength = this.innerLines.vertLength = parseFloat(len),
	    		'v', vlen -> this.innerLines.vertLength = parseFloat(vlen),
	    		't', thick -> this.innerLines.lineThickness = parseFloat(thick),
	    		'o', offs -> this.innerLines.lineOffset = parseFloat(offs)
	    	),
	    	"1", Map.of( 
				'b', show -> this.outerLines.showLines = (parseInt(show) == 1),
	    		'a', opac -> this.outerLines.opacity = parseFloat(opac),
	    		'l', len -> this.outerLines.horizLength = this.outerLines.vertLength = parseFloat(len),
				'v', vlen -> this.outerLines.vertLength = parseFloat(vlen),
	    		't', thick -> this.outerLines.lineThickness = parseFloat(thick),
	    		'o', offs -> this.outerLines.lineOffset = parseFloat(offs)
	    	)
    	),
    	"S", Map.of(), // Sniper
    	"A", Map.of() // ADS
    );
    
    public ProfileSettings() {}
    public ProfileSettings(String code) {
    	var classMatcher = Pattern.compile(";[A-Z];").matcher(code);
    	
    	int startCur = 0, endCur = 0;
    	while(true) {
    		boolean run = classMatcher.find();
    		
    		var clss = code.substring(startCur, endCur).replace(";", "");
    		
    		var tokens = code.substring(endCur, 
    				startCur = run ? classMatcher.start() : code.length());
    		endCur = run ? classMatcher.end() : code.length();

    		
	    	var tokenMatcher = Pattern.compile("\\d?\\w;(\\d+(\\.\\d+)?|[0-9A-Fa-f]{8});?").matcher(tokens);
	    	while(tokenMatcher.find()) {
	    		var token = tokens.substring(tokenMatcher.start(), tokenMatcher.end());
	    		var parts = token.split(";");
	    		assert parts.length == 2;
	    		
	    		var setting = parts[0];
	    		var prefix = setting.substring(0, setting.length() - 1);
	    		var settingCode = setting.charAt(setting.length() - 1);
	    		
	    		var classTable = tables.get(clss);
	    		if(classTable == null) continue;
	    		
	    		var settingTable = classTable.get(prefix);
	    		if(settingTable == null) continue;
	    		
	    		var settingCons = settingTable.get(settingCode);
	    		if(settingCons == null) continue;
	    		settingCons.accept(parts[1]);
	    	}
	    	
	    	if(!run) break;
    	}
    }
    
    public ProfileSettings copyWithScale(float scale) {
    	var copy = new ProfileSettings();
    	copy.color = color;
    	copy.useCustomColor = useCustomColor;
    	copy.hasOutline = hasOutline;
    	copy.outlineThickness = outlineThickness * scale;
    	copy.outlineColor = outlineColor;
    	copy.outlineOpacity = outlineOpacity;
    	copy.centerDotSize = centerDotSize * scale;
    	copy.centerDotOpacity = centerDotOpacity;
    	copy.displayCenterDot = displayCenterDot;
    	copy.hideCrosshair = hideCrosshair;
    	copy.innerLines = innerLines.copyWithScale(scale);
    	copy.outerLines = outerLines.copyWithScale(scale);
    	return copy;
    }

	@Override
	public String toString() {
		return "ProfileSettings [color=" + color + ", useCustomColor=" + useCustomColor
				+ ", hasOutline=" + hasOutline + ", outlineThickness=" + outlineThickness + ", outlineColor="
				+ outlineColor + ", outlineOpacity=" + outlineOpacity + ", centerDotSize=" + centerDotSize
				+ ", centerDotOpacity=" + centerDotOpacity + ", displayCenterDot=" + displayCenterDot
				+ ", hideCrosshair=" + hideCrosshair + ", innerLines=" + innerLines + ", outerLines=" + outerLines
				+ "]";
	}
}