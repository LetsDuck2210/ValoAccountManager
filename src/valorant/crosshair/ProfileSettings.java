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
    
    // Map<prefix => Map<char => variable setter>>
    private Map<String, Map<Character, Consumer<String>>> tables = Map.of(
    	"", Map.of( // general
	    	'c', colorCode -> { 
	    		this.color = colorPresets[parseInt(colorCode)];
	    		this.useCustomColor = (parseInt(colorCode) == 8); 
	    	},
	    	'u', colorHex -> this.color = hexToColor(colorHex),
	    	'h', outlines -> this.hasOutline = (parseInt(outlines) == 1),
	    	'o', oo -> this.outlineOpacity = parseFloat(oo),
	    	't', ot -> this.outlineThickness = parseFloat(ot),
	    	'd', cd -> this.displayCenterDot = (parseInt(cd) == 1),
	    	'a', cdo -> this.centerDotOpacity = parseFloat(cdo),
	    	'z', cds -> this.centerDotSize = parseFloat(cds)
    	),
    	"0", Map.of( // inner line
    		'b', show -> this.innerLines.showLines = (parseInt(show) == 1),
    		'a', opac -> this.innerLines.opacity = parseFloat(opac),
    		'l', len -> this.innerLines.lineLength = parseFloat(len),
    		't', thick -> this.innerLines.lineThickness = parseFloat(thick),
    		'o', offs -> this.innerLines.lineOffset = parseFloat(offs)
    	),
    	"1", Map.of( 
			'b', show -> this.outerLines.showLines = (parseInt(show) == 1),
    		'a', opac -> this.outerLines.opacity = parseFloat(opac),
    		'l', len -> this.outerLines.lineLength = parseFloat(len),
    		't', thick -> this.outerLines.lineThickness = parseFloat(thick),
    		'o', offs -> this.outerLines.lineOffset = parseFloat(offs)
    	)
    );
    
    public ProfileSettings(String code) {
    	// code: 0;P;c;1;d;1;...
    	var tokens = code.substring(code.indexOf(";P;") + 3); // -> c;1;d;1;...
    	var matcher = Pattern.compile("\\d?\\w;(\\d+(\\.\\d+)?|[0-9A-Fa-f]{8});?").matcher(tokens);
    	while(matcher.find()) {
    		var token = tokens.substring(matcher.start(), matcher.end());
    		var parts = token.split(";");
    		assert parts.length == 2;
    		
    		var setting = parts[0];
    		var prefix = setting.substring(0, setting.length() - 1);
    		var settingCode = setting.charAt(setting.length() - 1);
    		var settingTable = tables.get(prefix);
    		if(settingTable != null && settingTable.containsKey(settingCode)) {
    			settingTable.get(settingCode).accept(parts[1]);
    			System.out.println("parsed " + token + " to " + prefix + " " + settingCode + " = " + parts[1]);
    		}
    	}
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