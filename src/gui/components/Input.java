package gui.components;

public interface Input {
	public String get();
	public void clear();
	public boolean isFilled();
	public void set(String text);
}
