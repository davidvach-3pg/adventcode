package cz.itexpert.adventofcode.grid;

import cz.itexpert.adventofcode.Loc;

import java.util.Arrays;

public class CharGrid {
    public char[][] grid;

    public CharGrid(String resourceAsText) {
        this.grid = Arrays.stream(resourceAsText.split("\n")).sequential().map(String::toCharArray).toArray(char[][]::new);
    }

    public CharGrid(char c, Loc size) {
        this.grid = new char[size.intY()][size.intX()];
        for (int y = 0; y < size.y; y++) {
            for (int x = 0; x < size.x; x++) {
                grid[y][x] = c;
            }
        }
    }

    public void set(Loc p, char i) {
        if (get(p) == 0)
            throw new IllegalStateException("Trying to write to coordinate outside of grid: " + p + ", " + i);
        grid[p.intY()][p.intX()] = i;
    }

    public char get(Loc p) {
        if (p.x >= 0 && p.x < grid[0].length && p.y >= 0 && p.y < grid.length) {
            return grid[p.intY()][p.intX()];
        }
        return 0;
    }
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (char[] nums : grid) res.append(new String(nums)).append("\n");
        return res.toString();
    }



}
