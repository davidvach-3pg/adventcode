import cz.itexpert.adventofcode.Tuple;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import static cz.itexpert.adventofcode.DataMapper.readString;
import static java.lang.String.join;
import static java.util.Arrays.copyOf;

public class Day12Runner {

    private String fileName;

    public Day12Runner(String fileName) {
        this.fileName = fileName;
    }

    public static void main(String[] args) {

        Day12Runner day10 = new Day12Runner("day12/src/main/resources/test.txt");
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
        return input().mapToLong(spring ->
                countArrangements(new HashMap<>(), spring.map(), spring.amounts().stream().mapToInt(e -> e).toArray(), 0, 0, 0)
        ).sum();
    }

    protected Stream<String> dayStream(String delimiter) {
        return Arrays.stream(getResourceAsString().split(delimiter));
    }

    public record Spring(String map, List<Integer> amounts) {
    }

    public long part2() {
        return input().mapToLong(spring ->
                countArrangements(new HashMap<>(), join("?", spring.map(), spring.map(), spring.map(), spring.map(), spring.map()), repeat(spring.amounts().stream().mapToInt(e -> e).toArray(), 5), 0, 0, 0)
        ).sum();
    }

    public static int[] repeat(int[] arr, int times) {
        int newLength = arr.length * times;
        int[] dup = copyOf(arr, newLength);
        for (int last = arr.length; last != 0 && last < newLength; last <<= 1) {
            System.arraycopy(dup, 0, dup, last, Math.min(last << 1, newLength) - last);
        }
        return dup;
    }

    private Stream<Spring> input() {
        return dayStream("\n").map(s -> readString(s, "%s %li", ",", Spring.class));
    }

    public long countArrangements(HashMap<Tuple<Integer, Integer, Integer>, Long> blockMap, String map, int[] amounts, int i, int j, int cur) {
        var key = new Tuple<>(i, j, cur);
        if (blockMap.containsKey(key)) {
            return blockMap.get(key);
        }
        if (i == map.length()) {
            return (j == amounts.length && cur == 0) || (j == amounts.length - 1 && amounts[j] == cur) ? 1 : 0;
        }
        long total = 0;
        char c = map.charAt(i);
        if ((c == '.' || c == '?') && cur == 0) {
            total += countArrangements(blockMap, map, amounts, i + 1, j, 0);
        } else if ((c == '.' || c == '?') && cur > 0 && j < amounts.length && amounts[j] == cur) {
            total += countArrangements(blockMap, map, amounts, i + 1, j + 1, 0);
        }
        if (c == '#' || c == '?') {
            total += countArrangements(blockMap, map, amounts, i + 1, j, cur + 1);
        }
        blockMap.put(key, total);
        return total;
    }
}
