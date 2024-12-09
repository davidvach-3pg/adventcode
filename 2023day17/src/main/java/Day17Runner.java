import cz.itexpert.adventofcode.DayRunner;
import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.grid.NumberGrid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class Day17Runner extends DayRunner {



//https://github.com/ryanheath/aoc2023/blob/master/Day17.cs

    public Day17Runner() {
        super(17,"");
    }

    public static void main(String[] args) {
        new Day17Runner().printParts();
    }

    @Override
    public Object part1() {
        var in = new NumberGrid(getFileAsString(), "\n", "");
        return shortestPath(in, in.sizeX() - 1, in.sizeY() - 1, true) - in.get(new Point(0, 0));
    }

    public record Cell(Loc loc, long distance, List<Direction> prev, List<Loc> path) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Cell cell)) return false;
            return loc.equals(cell.loc) && distance == cell.distance;
        }
    }

    static long shortestPath(NumberGrid g, int row, int col, boolean part1) {
        long[][] grid = g.grid;
        long[][] dist = new long[g.sizeX()][g.sizeY()];

        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[0].length; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        dist[0][0] = 0;

        PriorityQueue<Cell> pq = new PriorityQueue<>(row * col, Comparator.comparing(Cell::distance));
        pq.add(new Cell(new Loc(0, 0), dist[0][0], new ArrayList<>(), new ArrayList<>(List.of(new Loc(0, 0)))));
        while (!pq.isEmpty()) {
            Cell curr = pq.poll();
            List<Direction> possible = new ArrayList<>();
            if (curr.prev.isEmpty()) {
                possible.add(Direction.EAST);
                possible.add(Direction.SOUTH);
            } else {
                if (part1 || curr.prev.size() >= 4) {
                    possible.add(curr.prev().get(0).turn(true));
                    possible.add(curr.prev().get(0).turn(false));
                }
                if ((part1 && curr.prev.size() <= 3) || (!part1 && curr.prev.size() <= 10)) {
                    possible.add(curr.prev().get(0));
                }
            }
            for (Direction dir : possible) {
                Loc newLoc = dir.move(curr.loc);
                int rows = newLoc.intX();
                int cols = newLoc.intY();

                if (isInsideGrid(newLoc, dist.length, dist[0].length)) {
                    long gridNum = grid[rows % grid.length][cols % grid.length] + (rows / grid.length) + (cols / grid[0].length);
                    if (gridNum >= 10) gridNum -= 9;

                    if (dist[rows][cols] > dist[curr.loc.intX()][curr.loc.intY()] + gridNum) {
                        if (dist[rows][cols] != Integer.MAX_VALUE) {
                            Cell adj = new Cell(new Loc(rows, cols), dist[rows][cols], new ArrayList<>(), new ArrayList<>());
                            pq.remove(adj);
                        }

                        dist[rows][cols] = dist[curr.loc.intX()][curr.loc.intY()] + gridNum;

                        var prev = new ArrayList<>(curr.prev);
                        if (!prev.contains(dir)) {
                            prev.clear();
                        }
                        prev.add(dir);
                        List<Loc> path = new ArrayList<>(curr.path());
                        path.add(new Loc(rows, cols));
                        pq.add(new Cell(new Loc(rows, cols), dist[rows][cols], prev, path));
                    }
                }
            }
        }
        return dist[dist.length - 1][dist[0].length - 1];
    }

    static boolean isInsideGrid(Loc l, int sizex, int sizey) {
        return l.x >= 0 && l.x < sizex && l.y >= 0 && l.y < sizey;
    }

    @Override
    public Object part2() {
        var in = new NumberGrid(getFileAsString(), "\n", "");
        return shortestPath(in, in.sizeX() - 1, in.sizeY() - 1, false) - in.get(new Point(0, 0));
    }



}
