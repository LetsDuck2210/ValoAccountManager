package valorant;

public enum Currency {
	TRY, OTHER;

	public static Currency fromString(String string) throws IllegalArgumentException {
		if (string.toUpperCase().equals(Currency.TRY.toString()))
			return Currency.TRY;
		else if (string.toUpperCase().equals(Currency.OTHER.toString()))
			return Currency.OTHER;
		else
			throw new IllegalArgumentException("currency invalid");
	}
}
