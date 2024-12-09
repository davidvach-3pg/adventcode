import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cz.itexpert.adventofcode.DataMapper.readString;
import static java.lang.String.join;
import static java.util.Arrays.copyOf;

public class Day13Runner {

    private String fileName;

    public Day13Runner(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {

        Day13Runner day10 = new Day13Runner("day13/src/main/resources/test.txt");
        LocalDateTime startDT = LocalDateTime.now();
        Duration duration = Duration.between(startDT, LocalDateTime.now());

        System.out.println("countA:  " + day10.part1() + "  duration: " + duration.toSeconds());

        System.out.println("countB:  " + day10.part2() + "  duration: " + duration.toSeconds());

    }

    public String getResourceAsString() {
        try {
            return Files.readString(new File(fileName).toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    public long part1() {
        List<String> lines = List.of(getResourceAsString().split("\n"));

        List<Pattern> patterns = new ArrayList<Pattern>();
        Pattern pattern = new Pattern();
        for (String line : lines) {
            if (line.isBlank()) {
                pattern.computeColumns();
                patterns.add(pattern);
                pattern = new Pattern();
            }
            else {
                pattern.appendLine(line);
            }
        }
        pattern.computeColumns();
        patterns.add(pattern);

        // Part 1
        int result =   patterns.stream().mapToInt(p -> p.getVerticalRFeflectionCount()).sum() + 100 * patterns.stream().mapToInt(p -> p.getHorizontalRFeflectionCount()).sum();
        System.out.println("Result part 1 : " + result);
        return result;
    }


    public long part2() {
        List<String> lines = List.of(getResourceAsString().split("\n"));

        List<Pattern> patterns = new ArrayList<Pattern>();
        Pattern pattern = new Pattern();
        for (String line : lines) {
            line = line.trim();
            if (line.isBlank()) {
                pattern.computeColumns();
                patterns.add(pattern);
                pattern = new Pattern();
            }
            else {
                pattern.appendLine(line);
            }
        }
        pattern.computeColumns();
        patterns.add(pattern);

        // Part 2
        int verticalReflectionCount = 0;
        int horizontalReflectionCount = 0;
        boolean found = false;
        for (Pattern p : patterns) {
            List<Integer> originalVerticalRFeflectionCount = p.getVerticalReflectionCollection();
            List<Integer> originalHorizontalRFeflectionCount = p.getHorizontalRFeflectionCollection();
            found = false;
            for (int x=0;x<p.columns.size()&&!found;x++) {
                for (int y=0;y<p.lines.size()&&!found;y++) {
                    p.swap(x,y);
                    List<Integer> newVerticalRFeflectionCount = p.getVerticalReflectionCollection();
                    List<Integer> newHorizontalRFeflectionCount = p.getHorizontalRFeflectionCollection();
                    newVerticalRFeflectionCount.removeAll(originalVerticalRFeflectionCount);
                    newHorizontalRFeflectionCount.removeAll(originalHorizontalRFeflectionCount);

                    if (!newVerticalRFeflectionCount.isEmpty()) {
                        if (newVerticalRFeflectionCount.size() > 1) throw new IllegalStateException();// nemuze nastat
                        verticalReflectionCount += newVerticalRFeflectionCount.get(0);
                    }
                    if (!newHorizontalRFeflectionCount.isEmpty()) {
                        if (newHorizontalRFeflectionCount.size() > 1) throw new IllegalStateException(); // nemuze nastat
                        horizontalReflectionCount += newHorizontalRFeflectionCount.get(0);
                    }

                    found |= !(newVerticalRFeflectionCount.isEmpty() && newHorizontalRFeflectionCount.isEmpty());
                    p.swap(x,y);
                }
            }
        }
        return  (verticalReflectionCount + 100 * horizontalReflectionCount);
    }




}
