import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.grid.CharacterGrid;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public class RunnerDay10 {

    private String fileName;

    public RunnerDay10(String fileName) {
        this.fileName = fileName;
    }

    public String getResourceAsString() {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static Stream<Direction> connections(char c) {
        return switch (c) {
            case 'F' -> Stream.of(Direction.EAST, Direction.SOUTH);
            case '7' -> Stream.of(Direction.SOUTH, Direction.WEST);
            case 'L' -> Stream.of(Direction.NORTH, Direction.EAST);
            case 'J' -> Stream.of(Direction.NORTH, Direction.WEST);
            case '-' -> Stream.of(Direction.EAST, Direction.WEST);
            case '|' -> Stream.of(Direction.NORTH, Direction.SOUTH);
            default -> Stream.empty();
        };
    }

    private long part1() {
        CharacterGrid grid = new CharacterGrid(getResourceAsString());
        Loc start = grid.findAll('S').findAny().get();

        Direction comeFrom = Direction.four()
                .filter(d -> grid.contains(d.move(start))) // all steps that goes to Start
                .filter(d -> connections(grid.get(d.move(start)).get()).anyMatch(d2 -> d2.equals(d.turnDegrees(180)))) // kdyz udelam krok tim smerem, vratim se do S ?
                .findAny() // vyberu nahodny
                .get();

        Loc currentLoc = comeFrom.move(start);
        int numWalked = 1;
        while (!currentLoc.equals(start)) {
            Direction nextDir = comeFrom;
            comeFrom = connections(grid.get(currentLoc).get())
                    .filter(d -> !d.equals(nextDir.turnDegrees(180)))
                    .findAny()
                    .get();
            currentLoc = comeFrom.move(currentLoc);
            numWalked++;
        }
        System.out.println(grid);
        return numWalked / 2;



    }


    public long part2()  {
        var grid = new CharacterGrid(getResourceAsString());
        var visitedBiggerGrid = new CharacterGrid(' ', grid.width() * 2, grid.height() * 2);
        Loc start = grid.findAll('S').findAny().get();
        Direction comeFrom = Direction.four()
                .filter(d -> grid.contains(d.move(start)))
                .filter(d -> connections(grid.get(d.move(start)).get()).anyMatch(d2 -> d2.equals(d.turnDegrees(180))))
                .findAny()
                .get();
        Loc currentLoc = moveLoc(start, comeFrom, visitedBiggerGrid);
        while (!currentLoc.equals(start)) {
            Direction nextDir = comeFrom;
            comeFrom = connections(grid.get(currentLoc).get()).filter(d -> !d.equals(nextDir.turnDegrees(180))).findAny().get();
            currentLoc = moveLoc(currentLoc, comeFrom, visitedBiggerGrid);
        }

        var pathGrid = new CharacterGrid('.', grid.width(), grid.height()); // grid s teckama
        visitedBiggerGrid.findAll('X').forEach(l -> pathGrid.set(l.translate(l2 -> l2 / 2), 'X')); // kriz, kde je cesta
//        System.out.println(visitedBiggerGrid);

        var borderGrid = pathGrid.withBorder(1, '.');
        var borderVisited = visitedBiggerGrid.withBorder(2, ' ');

        System.out.println("borderGrid ");
        System.out.println(borderGrid);

        System.out.println("borderVisited before");
        System.out.println(borderVisited);

        long result =  borderGrid.findAll('.').count() - borderVisited.floodFill(new Loc(0, 0), c -> c == ' ').stream().filter(l -> l.x % 2 == 0 && l.y % 2 == 0).count();

        return result;
//        System.out.println(visited);
//        System.out.println(borderVisited);
//        System.out.println(pathGrid);
//        System.out.println(borderGrid);
//        System.out.println(grid);

    }

    private static Loc moveLoc(Loc currentLoc, Direction comeFrom, CharacterGrid visited) {
        currentLoc = comeFrom.move(currentLoc);
        Loc zoomed = currentLoc.translate(l -> l * 2);
        visited.set(zoomed, 'X');
        Loc prevPos = comeFrom.turnDegrees(180).move(zoomed);
        visited.set(prevPos, 'X');
        return currentLoc;
    }
    public static void main(String[] args) {

        RunnerDay10 day10 = new RunnerDay10("day10/src/main/resources/day10.txt");
        LocalDateTime startDT = LocalDateTime.now();
        Duration duration = Duration.between(startDT, LocalDateTime.now());

        System.out.println("countA:  " + day10.part1() + "  duration: " + duration.toSeconds());

        System.out.println("countB:  " + day10.part2() + "  duration: " + duration.toSeconds());

    }


}
