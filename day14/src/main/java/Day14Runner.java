import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.MyInfiniteGrid;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static cz.itexpert.adventofcode.Solver.solve;
import static java.lang.Math.abs;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static cz.itexpert.adventofcode.Direction.*;

public class Day14Runner {

    private String fileName;

    protected char[][] dayGrid(String delimiter) {
        return dayStream(delimiter).map(String::toCharArray).toArray(char[][]::new);
    }
    protected Stream<String> dayStream(String delimiter) {
        return Arrays.stream(getResourceAsString().split(delimiter));
    }


    public Day14Runner(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {

        Day14Runner day14 = new Day14Runner("day14/src/main/resources/test.txt");
        LocalDateTime startDT = LocalDateTime.now();

        System.out.println("countA:  " + day14.part1() + "  duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());

        System.out.println("countB:  " + day14.part2() + "  duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());

    }

    public String getResourceAsString() {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    public long part1() {
        var grid = new MyInfiniteGrid(dayGrid("\n"));
        turn(grid, Direction.NORTH);
        return calculateSum(grid);
    }


    public long part2() {
        var grid = new MyInfiniteGrid(dayGrid("\n"));
        return solve(Stream.iterate(grid, this::doTurn), Day14Runner::calculateSum, 1000000000L);
    }

    private MyInfiniteGrid doTurn(MyInfiniteGrid grid) {
        Stream.of(NORTH, WEST, SOUTH, EAST).forEach(d -> turn(grid, d));
        return grid;
    }

    private void turn(MyInfiniteGrid grid, Direction dir) {
        grid.findAll('O').sorted(dir == Direction.EAST || dir == Direction.SOUTH ? reverseOrder() : naturalOrder()).forEach(r -> {
            Loc loc = r;
            while (grid.get(dir.move(loc)).map(c -> c == '.').orElse(false)) {
                loc = dir.move(loc);
            }
            grid.set(r, '.');
            grid.set(loc, 'O');
        });
    }
    private static long calculateSum(MyInfiniteGrid grid) {
        return grid.findAll('O').mapToLong(l -> abs(l.y - grid.maxY()) + 1).sum();
    }





}
