import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

    public static String getResourceAsString() {
        try {
            return Files.readString(new File("2023day11/src/main/resources/test.txt").toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] grid = getResourceAsString().split("\n");

        List<Integer> emptyRows = findEmptyRows(grid);
        List<Integer> emptyCols = findEmptyColumns(grid);
        List<int[]> points = new ArrayList<>();
        // Find galaxy co-ordinates
        findGalaxies(grid, points);
        // Calculate total for part 1
        System.out.println(getTotal(points, emptyRows,  emptyCols,2));
        // Calculate total for part 2
        System.out.println(getTotal(points,  emptyRows,emptyCols,1000000));

    }

    private static void findGalaxies(String[] grid, List<int[]> points) {
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length(); c++) {
                if (grid[r].charAt(c) == '#') {
                    points.add(new int[]{r, c});
                }
            }
        }
    }

    private static long getTotal(List<int[]> points, List<Integer> emptyRows,List<Integer> emptyCols,int scale) {
        long total = 0;
        for (int i = 0; i < points.size(); i++) {
            int[] point1 = points.get(i);
            for (int j = 0; j < i; j++) {
                int[] point2 = points.get(j);

                for (int r = Math.min(point1[0], point2[0]); r < Math.max(point1[0], point2[0]); r++) {
                    total += emptyRows.contains(r) ? scale : 1;
                }

                for (int c = Math.min(point1[1], point2[1]); c < Math.max(point1[1], point2[1]); c++) {
                    total += emptyCols.contains(c) ? scale : 1;
                }
            }
        }
        return total;
    }

    public static List<Integer> findEmptyRows(String[] grid) {
        List<Integer> emptyRows = new ArrayList<>();

        // Check each row for the presence of '#'
        for (int row = 0; row < grid.length; row++) {
            if (!grid[row].contains("#")) {
                emptyRows.add(row);
            }
        }

        return emptyRows;
    }

    public static List<Integer> findEmptyColumns(String[] grid) {
        List<Integer> emptyColumns = new ArrayList<>();

        // Check each column for the presence of '#'
        for (int col = 0; col < grid[0].length(); col++) {
            System.out.println("grid[0].length(): " + grid[0].length());
            boolean isEmptyColumn = true;

            for (String row : grid) {
                row = row.trim();
                System.out.println("col: " + col);
                if (row.charAt(col) == '#') {
                    isEmptyColumn = false;
                    break;
                }
            }

            // If the column doesn't contain '#', add its index to the list
            if (isEmptyColumn) {
                emptyColumns.add(col);
            }
        }

        return emptyColumns;
    }
}