package valorant;

public final class EmptyAccount {
	private static final Account acc = new Account("", "", "", "", "", Currency.OTHER);
	public static Account get() {
		return acc;
	}
}
