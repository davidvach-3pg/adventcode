import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.grid.CharacterGrid;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class Day16Runner {
    private String fileName;

    protected Stream<String> dayStream(String delimiter) {
        return Arrays.stream(getResourceAsString().split(delimiter));
    }

    public String getResourceAsString() {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public Day16Runner(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        Day16Runner day16 = new Day16Runner("day16/src/main/resources/day16.txt");
        LocalDateTime startDT = LocalDateTime.now();


        System.out.println("countA 7434:  " + day16.part1() + "  duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());

        System.out.println("countB 8183:  " + day16.part2() + "  duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());

    }

    public record VisitedPath(Loc loc, Direction direction) {
    }


    public long part1() {
        return getVisitedItemInGrid(new CharacterGrid(getResourceAsString()), new Loc(0, 0), Direction.EAST);
    }

    public long getVisitedItemInGrid(CharacterGrid grid, Loc start, Direction direction) {
        Set<VisitedPath> cache = new HashSet<>();
        move(grid, start, direction, cache);
//        printVisited(grid, cache);
        long result =  cache.stream().map(visitedPath -> visitedPath.loc).distinct().count();

        if (result == 8184) {
            printVisited(grid, cache);
        }
        return result;
    }

    private static void printVisited(CharacterGrid grid, Set<VisitedPath> cache) {
        CharacterGrid gridVisited = new CharacterGrid('-', grid.width(), grid.height());
        cache.stream().forEach(visitedPath -> gridVisited.set(visitedPath.loc, '#'));
        System.out.println(gridVisited);
    }


    public void move(CharacterGrid grid, Loc start, Direction direction, Set<VisitedPath> cache) {
        int vpravo = 90, vlevo = -90;
//        printVisited(grid, cache);
        if (cache.contains(new VisitedPath(start, direction))) {
            return;
        } else {
            cache.add(new VisitedPath(start, direction));
        }
        Loc nextLoc = direction.move(start);
        if (!grid.contains(nextLoc)) {
            return;
        }
        switch (grid.getOptimistic(nextLoc)) {
            case '.' -> move(grid, nextLoc, direction, cache);
            case '\\' -> {
                switch (direction) {
                    case Direction.WEST -> move(grid, nextLoc, direction.turnDegrees(vpravo), cache);
                    case Direction.EAST -> move(grid, nextLoc, direction.turnDegrees(vpravo), cache);
                    case Direction.NORTH -> move(grid, nextLoc, direction.turnDegrees(vlevo), cache);
                    case Direction.SOUTH -> move(grid, nextLoc, direction.turnDegrees(vlevo), cache);
                }
            }
            case '/' -> {
                switch (direction) {
                    case Direction.WEST -> move(grid, nextLoc, direction.turnDegrees(vlevo), cache);
                    case Direction.EAST -> move(grid, nextLoc, direction.turnDegrees(vlevo), cache);
                    case Direction.NORTH -> move(grid, nextLoc, direction.turnDegrees(vpravo), cache);
                    case Direction.SOUTH -> move(grid, nextLoc, direction.turnDegrees(vpravo), cache);
                }
            }
            case '|' -> {
                switch (direction) {
                    case Direction.NORTH, Direction.SOUTH -> move(grid, nextLoc, direction, cache);
                    default -> {
                        move(grid, nextLoc, direction.turnDegrees(-90), cache);
                        move(grid, nextLoc, direction.turnDegrees(90), cache);
                    }
                }
            }
            case '-' -> {
                switch (direction) {
                    case Direction.EAST, Direction.WEST -> move(grid, nextLoc, direction, cache);
                    default -> {
                        move(grid, nextLoc, direction.turnDegrees(90), cache);
                        move(grid, nextLoc, direction.turnDegrees(-90), cache);
                    }
                }
            }

        }

    }


    public long part2() {
        CharacterGrid grid = new CharacterGrid(getResourceAsString());

        Set<VisitedPath> toVist = new HashSet<>();
        for (int x = 0; x < grid.width(); x++) {
            toVist.add(new VisitedPath(new Loc(x, 0), Direction.SOUTH));
            toVist.add(new VisitedPath(new Loc(x, grid.height() - 1), Direction.NORTH));
        }


        for (int y = 0; y < grid.height(); y++) {
            toVist.add(new VisitedPath(new Loc(0, y), Direction.EAST));
            toVist.add(new VisitedPath(new Loc(grid.width() - 1, y), Direction.WEST));
        }

        return toVist.stream().map(visitedPath -> {
            long result = getVisitedItemInGrid(grid, visitedPath.loc, visitedPath.direction);
            System.out.println(visitedPath.loc + " " + visitedPath.direction + " :" + result);
            return result;
        }).mapToLong(Long::longValue).max().getAsLong();
    }


}
