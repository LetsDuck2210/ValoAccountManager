package util;

public record Tuple<A,B>(A first, B second) {
  public final String toString() {
    return "(" + first + ", " + second + ")";
  }

  public static <A,B> Tuple<A,B> of(A first, B second) {
    return new Tuple<A,B>(first, second);
  }
}
