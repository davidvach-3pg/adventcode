
import cz.itexpert.adventofcode.*;
import cz.itexpert.adventofcode.grid.CharGrid;
import cz.itexpert.adventofcode.grid.CharacterGrid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;

public class Day21Runner extends DayRunner {


    public Day21Runner() {
        super(21);
    }

    public static void main(String[] args) {
        new Day21Runner().printParts();
    }

    @Override
    public Object part1() {
        var grid = new CharacterGrid(getResourceAsString());
        Builder<Set<Loc>> places = new Builder<>(HashSet::new);
        places.get().add(grid.findAll('S').findAny().get());
        for (int i = 0; i < 64; i++) {
            places.get().stream().flatMap(l -> Direction.four().map(d -> d.move(l))).filter(l -> grid.getChar(l) != '#').forEach(places.getNew()::add);
            places.refresh();
        }
        return places.get().size();
    }

    @Override
    public Object part2() {
        char[][] grid = new CharGrid(getResourceAsString()).grid;
        var infGrid = new CharacterGrid(grid);
        Loc start = infGrid.findAll('S').findAny().get();

        // Core algorithm by abnew123: https://github.com/abnew123/aoc2023/blob/main/src/solutions/Day21.java
        Set<Loc> reached = new HashSet<>();
        Builder<Set<Loc>> places = new Builder<>(HashSet::new);
        places.get().add(start);
        reached.add(start);
        List<Long> totals = new ArrayList<>();
        long totalReached = 0;
        for (int i = 1; ; i++) {
            places.get().stream().flatMap(l -> Direction.four().map(d -> d.move(l))).forEach(l -> {
                if (!reached.contains(l)) {
                    if (grid[((l.intX() % grid.length) + grid.length) % grid.length][((l.intY() % grid.length) + grid.length) % grid.length] != '#') {
                        places.getNew().add(l);
                        reached.add(l);
                    }
                }
            });
            if (i % 2 == 1) {
                totalReached += places.getNew().size();
                if (i % 262 == 65) {
                    totals.add(totalReached);
                    List<Long> deltaDeltas = deltaAtLevel(totals, 2);
                    if (deltaDeltas.size() > 1) {
                        long neededLoopCount = 26501365 / 262 - 1;
                        long currentLoopCount = i / 262 - 1;
                        long deltaLoopCount = neededLoopCount - currentLoopCount;
                        long deltaLoopCountTriangular = (neededLoopCount * (neededLoopCount + 1)) / 2 - (currentLoopCount * (currentLoopCount + 1)) / 2;
                        long deltaDelta = deltaDeltas.get(deltaDeltas.size() - 1);
                        long initialDelta = deltaAtLevel(totals, 1).get(0);
                        return deltaDelta * deltaLoopCountTriangular + initialDelta * deltaLoopCount + totalReached;
                    }
                }
            }
            places.refresh();
        }
    }

    private static List<Long> deltaAtLevel(List<Long> deltas, int level) {
        for (int i = 0; i < level; i++) {
            deltas = ACUtil.connectedPairs(deltas).map(p -> p.b() - p.a()).collect(toCollection(ArrayList::new));
        }
        return deltas;
    }
}
