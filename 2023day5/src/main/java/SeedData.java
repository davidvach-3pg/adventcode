public class SeedData {

    private Seed startingPosition;

    private long noOfSeeds;

    public SeedData(Seed startingPosition, long noOfSeeds) {
        this.startingPosition = startingPosition;
        this.noOfSeeds = noOfSeeds;
    }

    public Seed getStartingPosition() {
        return startingPosition;
    }

    public long getNoOfSeeds() {
        return noOfSeeds;
    }
}
