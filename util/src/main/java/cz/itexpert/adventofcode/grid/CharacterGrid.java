package cz.itexpert.adventofcode.grid;

import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class CharacterGrid {

    public Map<Loc, Character> grid = new HashMap<>();

    public CharacterGrid() {
    }

    public CharacterGrid(CharacterGrid g) {
        this.grid = g.grid;
    }


    public CharacterGrid(Collection<Loc> locs, char c) {
        locs.forEach(l -> grid.put(l, c));
    }

    public CharacterGrid(char[][] g, char transparent) {
        for (int i = 0; i < g.length; i++) {
            for (int j = 0; j < g[i].length; j++) {
                if (transparent != g[i][j]) {
                    grid.put(new Loc(j, i), g[i][j]);
                }
            }
        }
    }

    public CharacterGrid(String fileAsString) {
        char[][] gridAsChar = Arrays.stream(fileAsString.split("\n")).map(String::toCharArray).toArray(char[][]::new);

        for (int i = 0; i < gridAsChar.length; i++) {
            for (int j = 0; j < gridAsChar[i].length; j++) {
                grid.put(new Loc(j, i), gridAsChar[i][j]);
            }
        }
    }

    public CharacterGrid(char c, long width, long height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid.put(new Loc(j, i), c);
            }
        }
    }

    public CharacterGrid(char[][] grid) {
        this(grid, ' ');
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

    private CharGrid toCharGrid() {
        long minX = minX();
        long minY = minY();
        CharGrid g = new CharGrid(' ', new Loc(width(), height()));
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

    public CharacterGrid withBorder(long thickness, char borderChar) {
        CharacterGrid g = new CharacterGrid(this);
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
        long minX = minX(), minY = minY(), maxX = maxX(), maxY = maxY();
        Predicate<Loc> inBounds = l -> l.x >= minX && l.x <= maxX && l.y >= minY && l.y <= maxY;
        toCheck.add(start);
        while (!toCheck.isEmpty()) {
            Set<Loc> newToCheck = new HashSet<>();
            for (Loc l : toCheck) {
                if (predicate.test(getChar(l))) {
                    output.add(l);
                    newToCheck.addAll(Direction.four().map(d -> d.move(l)).filter(inBounds).filter(l2 -> !output.contains(l2)).collect(Collectors.toSet()));
                }
            }
            toCheck = newToCheck;
        }
        return output;
    }

    public char getChar(Loc p) {
        return get(p).orElse((char) 0);
    }

    public Map<Long, List<Loc>> columns() {
        return stream().collect(Collectors.groupingBy(Loc::getX));
    }

    public Map<Long, List<Loc>> rows() {
        return stream().collect(Collectors.groupingBy(Loc::getY));
    }


    public void draw(Loc start, Loc end, char c) {
        for (Loc l : start.line(end)) {
            set(l, c);
        }
    }
}
