import java.util.function.UnaryOperator;

import static java.lang.Math.toIntExact;

public class Loc  {
  public final long x;
  public final long y;

  public Loc() {
    this(0, 0);
  }

  public Loc(Loc p) {
    this(p.x, p.y);
  }

  public Loc(long x, long y) {
    this.x = x;
    this.y = y;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Loc loc = (Loc) o;
    if (x != loc.x) return false;
    return y == loc.y;
  }

  @Override
  public int hashCode() {
    int result = (int) (x ^ (x >>> 32));
    result = 31 * result + (int) (y ^ (y >>> 32));
    return result;
  }

  public int intX() {
    return toIntExact(x);
  }

  public int intY() {
    return toIntExact(y);
  }

  public Loc translate(UnaryOperator<Long> mapper) {
    return new Loc(mapper.apply(x), mapper.apply(y));
  }



}
