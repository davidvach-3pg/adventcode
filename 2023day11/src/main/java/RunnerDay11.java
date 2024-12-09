import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.MyInfiniteGrid;
import cz.itexpert.adventofcode.Pair;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toSet;
import static java.util.stream.LongStream.rangeClosed;
import static cz.itexpert.adventofcode.ACUtil.allPairs;

public class RunnerDay11 {

    private String fileName;

    public RunnerDay11(String fileName) {
        this.fileName = fileName;
    }

    public String getResourceAsString() {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    private long part1(boolean part1) {
        var grid = new MyInfiniteGrid(getResourceAsString());
        Set<Long> emptyRows = grid.rows().entrySet().stream().filter(e -> e.getValue().stream().allMatch(c -> grid.getOptimistic(c) == '.')).map(Map.Entry::getKey).collect(toSet());
        Set<Long> emptyCols = grid.columns().entrySet().stream().filter(e -> e.getValue().stream().allMatch(c -> grid.getOptimistic(c) == '.')).map(Map.Entry::getKey).collect(toSet());
        return allPairs(grid.findAll('#').toList()).mapToLong(p -> p.a().distance(p.b()) + countEmpty(p, Loc::getX, emptyCols, part1) + countEmpty(p, Loc::getY, emptyRows, part1)).sum();
    }

    private long countEmpty(Pair<Loc, Loc> p, Function<Loc, Long> func, Set<Long> emptyRows, boolean part1) {
        long coord1 = func.apply(p.a());
        long coord2 = func.apply(p.b());
        return rangeClosed(min(coord1, coord2), max(coord1, coord2)).filter(emptyRows::contains).count() * (part1 ? 1 : (1000000 - 1));
    }


    public long part2(boolean part1) {
        return part1(false);
    }

    public static void main(String[] args) {

        RunnerDay11 day10 = new RunnerDay11("2023day11/src/main/resources/test.txt");
        LocalDateTime startDT = LocalDateTime.now();
        Duration duration = Duration.between(startDT, LocalDateTime.now());

        System.out.println("countA:  " + day10.part1(true) + "  duration: " + duration.toSeconds());

        System.out.println("countB:  " + day10.part2(false) + "  duration: " + duration.toSeconds());

    }




}
