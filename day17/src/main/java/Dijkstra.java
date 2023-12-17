import cz.itexpert.adventofcode.grid.NumberGrid;

import java.util.Arrays;

public class Dijkstra {

    NumberGrid numberGrid;
    boolean visited[];
    int distance[];


    public Dijkstra(NumberGrid numberGrid) {
        this.numberGrid = numberGrid;
        this.visited = new boolean[numberGrid.height()];
        this.distance = new int[numberGrid.height()];
        this.distance[0] = 0;
        for (int i = 1; i < numberGrid.height(); i++) {
            this.distance[i] = Integer.MAX_VALUE;
        }
    }

    public void solve() {
        for (int i = 0; i < numberGrid.height()-1; i++) {
            // find vertex with min distance
            int minVertex = findMinVertex(distance, visited);
            visited[minVertex] = true;
            // Explore neigbours
            for (int j = 0; j < numberGrid.height(); j++) {
                if (numberGrid.grid[minVertex][j] != 0 && !visited[j] && distance[minVertex] != Integer.MAX_VALUE   ) {
                    int newDist = distance[minVertex] + (int) numberGrid.grid[minVertex][j];
                    if (newDist  < distance[j]) {
                        distance[j] = newDist;
                    }
                }

            }
        }
    }

    private int findMinVertex(int[] distance, boolean[] visited) {
        int minVertex = -1;
        for (int i  = 0; i < distance.length; i++) {
            if (!visited[i] && (minVertex == -1 || distance[i] < distance[minVertex])) {
                minVertex = i;
            }
        }
        return minVertex;
     }
}
