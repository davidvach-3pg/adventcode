import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.ListMap;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.MyInfiniteGrid;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static cz.itexpert.adventofcode.Direction.*;
import static cz.itexpert.adventofcode.Direction.EAST;
import static cz.itexpert.adventofcode.Solver.solve;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.IntStream.range;

public class Day15Runner {
    private String fileName;

    protected Stream<String> dayStream(String delimiter) {
        return Arrays.stream(getResourceAsString().split(delimiter));
    }


    public Day15Runner(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {
        Day15Runner day14 = new Day15Runner("day15/src/main/resources/test.txt");
        LocalDateTime startDT = LocalDateTime.now();


        System.out.println("countA:  " + day14.part1() + "  duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());

        System.out.println("countB:  " + day14.part2() + "  duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());

    }

    private int hash(String s) {
        return s.chars().reduce(0, (acc, c) -> ((acc + c) * 17) % 256);
    }

    public String getResourceAsString() {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    public long part1() {
        return dayStream(",").map(String::strip).mapToLong(this::hash).sum();
    }

    public record Lens(String label, boolean dash, int value) {
    }


    public long part2() {
        ListMap<Integer, Lens> lenses = new ListMap<>();
        dayStream(",")
                .map(String::strip)
                .map(s -> s.contains("-") ? new Lens(s.replace("-", ""), true, 0) : new Lens(s.split("=")[0], false, parseInt(s.split("=")[1])))
                .forEach(lens -> registerLenses(lens, lenses));
        return lenses
                .entrySet()
                .stream()
                .flatMapToLong(e -> range(0, e.getValue().size()).mapToLong(i -> (e.getKey() + 1L) * (i + 1L) * e.getValue().get(i).value))
                .sum();
    }

    private void registerLenses(Lens lens, ListMap<Integer, Lens> lensMap) {
        int hash = hash(lens.label);
        List<Lens> lenses = lensMap.get(hash);
        if (lens.dash) {
            lenses.removeIf(l -> l.label.equals(lens.label));
        } else {
            lenses
                    .stream()
                    .filter(l -> l.label.equals(lens.label))
                    .findFirst()
                    .ifPresentOrElse(
                            l -> lenses.set(lenses.indexOf(l), lens),
                            () -> lensMap.addTo(hash, lens)
                    );
        }
    }


}
