public class Race {

    private long time;

    private long distance;

    public Race(long time, long distance) {
        this.time = time;
        this.distance = distance;
    }

    public long getTime() {
        return time;
    }

    public long getDistance() {
        return distance;
    }

    public int countNumberOfPossibleWins() {
        int wins = 0;
        for (int wait = 0 ; wait <= time; wait++) {
            if (((time-wait)*wait) > distance) {
                wins++;
            }
        }
        return wins;
    }

    @Override
    public String toString() {
        return "Race{" +
                "time=" + time +
                ", distance=" + distance +
                '}';
    }
}
