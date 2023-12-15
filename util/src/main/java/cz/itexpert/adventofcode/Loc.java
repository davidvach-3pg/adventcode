package cz.itexpert.adventofcode;

import java.util.Comparator;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

import static java.lang.Math.toIntExact;
import static java.lang.Math.abs;

public class Loc implements Comparable<Loc> {
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

  public long getX() {
    return x;
  }

  public long getY() {
    return y;
  }

  public long distance(Loc pt) {
    return abs(pt.x - x) + abs(pt.y - y);
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

  @Override
  public int compareTo(Loc o) {
    ToLongFunction<Loc> tlf = a -> a.x * Integer.MAX_VALUE + a.y;
    return Comparator.comparingLong(tlf).compare(this, o);
  }



}
