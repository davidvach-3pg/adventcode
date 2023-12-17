package cz.itexpert.adventofcode;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class DayRunner {

    private String fileName;

    public static final String DEFAULT_DELIMITER = "\n";

    private Object solutionPart1;
    private Object solutionPart2;

    public DayRunner(int day, String filePostfix) {
        this.fileName = String.format("day%s/src/main/resources/day%s.txt", day, filePostfix);
    }

    public DayRunner(int day) {
      this(day, "");
    }

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

    public abstract Object part1();

    public abstract Object part2();

    public void printParts() {
        LocalDateTime startDT = LocalDateTime.now();
        this.solutionPart1 = part1();
        System.out.println("Part 1: " + solutionPart1 + " duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());
        this.solutionPart2 = part2();
        System.out.println("Part 2: " + solutionPart2+ " duration: " + Duration.between(startDT, LocalDateTime.now()).toSeconds());
    }

}
