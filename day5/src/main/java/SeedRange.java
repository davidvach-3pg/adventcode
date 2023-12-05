public class SeedRange {

    private long  sourceRange;
    private long destinationRange;
    private long length;

    public SeedRange(long startRange, long endRange, long length) {
        this.sourceRange = startRange;
        this.destinationRange = endRange;
        this.length = length;
    }

    public boolean isInRange(Seed seedToConvert) {
        return seedToConvert.getNumber() >= this.sourceRange && seedToConvert.getNumber() <= (this.sourceRange + this.length);
    }

    public Seed convertSeed(Seed seedToConvert) {
        if (isInRange(seedToConvert)) {
            return new Seed(this.destinationRange + (seedToConvert.getNumber() - this.sourceRange));
        } else {
            return null;
        }
    }
}
