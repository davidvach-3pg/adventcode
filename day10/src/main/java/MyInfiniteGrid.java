import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class MyInfiniteGrid {

    public Map<Loc, Character> grid = new HashMap<>();

    public MyInfiniteGrid(MyInfiniteGrid g) {
        this.grid = g.grid;
    }

    public MyInfiniteGrid(Collection<Loc> locs, char c) {
        locs.forEach(l -> grid.put(l, c));
    }

    public MyInfiniteGrid(String fileAsString) {
        char[][] gridAsChar = Arrays.stream(fileAsString.split("\n")).map(String::toCharArray).toArray(char[][]::new);

        for (int i = 0; i < gridAsChar.length; i++) {
            for (int j = 0; j < gridAsChar[i].length; j++) {
                grid.put(new Loc(j, i), gridAsChar[i][j]);
            }
        }
    }

    public MyInfiniteGrid(char c, long width, long height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid.put(new Loc(j, i), c);
            }
        }
    }

    public Stream<Loc> findAll(Character c) {
        return stream().filter(l -> contains(l) && getOptimistic(l) == c);
    }

    public Stream<Loc> stream() {
        return grid.keySet().stream();
    }

    public boolean contains(Loc p) {
        return grid.containsKey(p);
    }

    public Character getOptimistic(long x, long y) {
        return get(x, y).orElseThrow();
    }

    public Character getOptimistic(Loc l) {
        return get(l).orElseThrow();
    }

    public Optional<Character> get(Loc p) {
        return grid.containsKey(p) ? of(grid.get(p)) : empty();
    }

    public Optional<Character> get(long x, long y) {
        return get(x, y);
    }

    public String toString() {
        return toCharGrid().toString();
    }

    private MyCharGrid toCharGrid() {
        long minX = minX();
        long minY = minY();
        MyCharGrid g = new MyCharGrid(' ', new Loc(width(), height()));
        grid.forEach((p, i) -> g.set(new Loc(p.x - minX, p.y - minY), i));
        return g;
    }

    public long height() {
        return maxY() + 1 - minY();
    }

    public long width() {
        return maxX() + 1 - minX();
    }

    public long minY() {
        return grid.keySet().stream().mapToLong(e -> e.y).min().orElse(0);
    }

    public long maxY() {
        return grid.keySet().stream().mapToLong(e -> e.y).max().orElse(0);
    }

    public long minX() {
        return grid.keySet().stream().mapToLong(e -> e.x).min().orElse(0);
    }

    public long maxX() {
        return grid.keySet().stream().mapToLong(e -> e.x).max().orElse(0);
    }

    public void set(Loc p, char c) {
        grid.put(p, c);
    }
    public MyInfiniteGrid withBorder(long thickness, char borderChar) {
        MyInfiniteGrid g = new MyInfiniteGrid(this);
        long minX = minX();
        long minY = minY();
        long maxX = maxX();
        long maxY = maxY();
        for (long i = minY - thickness; i <= maxY + thickness; i++) {
            for (long j = 1; j <= thickness; j++) {
                g.set(new Loc(minX - j, i), borderChar);
                g.set(new Loc(maxX + j, i), borderChar);
            }
        }
        for (long i = minX - thickness; i <= maxX + thickness; i++) {
            for (long j = 1; j <= thickness; j++) {
                g.set(new Loc(i, minY - j), borderChar);
                g.set(new Loc(i, maxY + j), borderChar);
            }
        }
        return g;
    }

    public Set<Loc> floodFill(Loc start, Predicate<Character> predicate) {
        Set<Loc> output = new HashSet<>();
        Set<Loc> toCheck = new HashSet<>();
        toCheck.add(start);
        while (!toCheck.isEmpty()) {
            Set<Loc> newToCheck = new HashSet<>();
            for (Loc l : toCheck) {
                if (predicate.test(getChar(l))) {
                    output.add(l);
                    newToCheck.addAll(Direction.four().map(d -> d.move(l)).filter(this::contains).filter(l2 -> !output.contains(l2)).collect(Collectors.toSet()));
                }
            }
            toCheck = newToCheck;
        }

        System.out.println("floodFill");
        System.out.println(new MyInfiniteGrid(output, '*'));
        return output;
    }

    public char getChar(Loc p) {
        return get(p).orElse((char) 0);
    }


}
