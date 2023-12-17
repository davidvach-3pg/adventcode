package cz.itexpert.adventofcode.grid;

import java.util.Arrays;

public class NumberGrid {

    public final long[][] grid;

    public NumberGrid(long[][] grid) {
        this.grid = grid;
    }

    public NumberGrid(String stringToParse, String lineDelimiter, String itemDelimiter) {
        this.grid = buildNumberGrid(stringToParse, lineDelimiter, itemDelimiter);
    }

    private long[][] buildNumberGrid(String str, String lineDelimiter, String itemDelimiter) {
        String[] lines = str.split(lineDelimiter);
        long[][] newNumberGrid = new long[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            newNumberGrid[i] = Arrays.stream(lines[i].split(itemDelimiter)).map(String::trim).filter(e -> !e.isEmpty()).mapToLong(Long::parseLong).toArray();
        }
        return newNumberGrid;
    }

    public int height() {
        return grid.length;
    }

    public int sizeX() {
        return height();
    }

    public int sizeY() {
        return width();
    }


    public int width() {
        return grid[0].length;
    }

}
