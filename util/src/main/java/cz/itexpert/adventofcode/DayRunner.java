package cz.itexpert.adventofcode;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class DayRunner {

    private String fileName;

    public static final String DEFAULT_DELIMITER = "\n";

    private Object solutionPart1;
    private Object solutionPart2;

    public DayRunner(int day, String filePostfix) {
        this.fileName = String.format("%s/day%s.txt", getFIleFolder(day), filePostfix);
    }

    public String getFIleFolder(int day) {
        return String.format("day%s/src/main/resources", day);
    }

    public DayRunner(int day) {
      this(day, "");
    }

    protected Stream<String> dayStream() {
        return dayStream("\n");
    }

    protected Stream<String> dayStream(String delimiter) {
        return Arrays.stream(getFileAsString().split(delimiter));
    }

    public String getFileAsString() {
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
