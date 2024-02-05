package valorant;

public enum Currency {
	TRY, OTHER;
	
	public static Currency fromString(String string) throws IllegalArgumentException {
		if(string == null) throw new IllegalArgumentException("string must not be null");
		for(var curr : Currency.values()) {
			if(string.equalsIgnoreCase(curr.toString())) {
				return curr;
			}
		}
		throw new IllegalArgumentException("string does not match with any currency");
	}
}
