import cz.itexpert.adventofcode.grid.NumberGrid;

import java.util.Arrays;

public class DistrictPathSolver {

    private NumberGrid numberGrid;

    private int[][] map;

    final static int TRIED = 2;
    final static int PATH = 3;

    public DistrictPathSolver(NumberGrid grid) {
        this.numberGrid = grid;
        this.map = new int[grid.height()][grid.width()];
    }

    public boolean solve() {
        return traverse(0, 0);
    }

    private boolean traverse(int i, int j) {
        if (!isValid(i, j)) {
            return false;
        }
        if (isEnd(i, j)) {
            map[i][j] = PATH;
            return true;
        } else {
            map[i][j] = TRIED;
        }
        // North
        if (traverse(i - 1, j)) {
            map[i - 1][j] = PATH;
            return true;
        }
        // East
        if (traverse(i, j + 1)) {
            map[i][j + 1] = PATH;
            return true;
        }
        // South
        if (traverse(i + 1, j)) {
            map[i + 1][j] = PATH;
            return true;
        }
        // West
        if (traverse(i, j - 1)) {
            map[i][j - 1] = PATH;
            return true;
        }
        return false;
    }

    private boolean isTried(int i, int j) {
        return map[i][j] == TRIED;
    }

    private boolean isEnd(int i, int j) {
        return i == numberGrid.height() - 1 && j == numberGrid.width() - 1;
    }

    private boolean isValid(int i, int j) {
        if (inRange(i, j) && isOpen(i, j) && !isTried(i, j)) {
            return true;
        }
        return false;
    }

    private boolean isOpen(int i, int j) {
        return numberGrid.grid[i][j] == 1;
    }

    private boolean inRange(int i, int j) {
        return inHeight(i) && inWidth(j);
    }

    private boolean inHeight(int i) {
        return i >= 0 && i < numberGrid.height();
    }

    private boolean inWidth(int j) {
        return j >= 0 && j < numberGrid.width();
    }

    public String toString() {
        String s = "";
        for (int[] row : map) {
            s += Arrays.toString(row) + "\n";
        }
        return s;
    }
}
