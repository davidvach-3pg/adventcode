import java.util.*;

public class DataSet {

    private List<Seed> seeds = new ArrayList<>();

    public List<Seed> getSeeds() {
        return seeds;
    }

    public List<SeedData> getSeedsData() {
            List<SeedData> multiSeeds = new ArrayList<>();
            Iterator<Seed> i = seeds.iterator();
            while (i.hasNext()) {
                long startSeed = i.next().getNumber();
                long numberOfFollowingSeeds = i.next().getNumber();
                multiSeeds.add(new SeedData(new Seed(startSeed), numberOfFollowingSeeds));
            }
        return multiSeeds;
    }

    private Map<InputFileParser.SoilType, List<SeedRange>> seedRangeMap =new HashMap();

    public Seed findNextSeed(InputFileParser.SoilType soilType, Seed originalSeed) {
//        System.out.println(soilType + " seed: " +originalSeed.getNumber());
        Seed convertedSeed = null;
        for (SeedRange seedRange: seedRangeMap.get(soilType)) {
            convertedSeed = seedRange.convertSeed(originalSeed);
            if (convertedSeed != null) {
//                System.out.println(" converted to: " + convertedSeed.getNumber());
                return convertedSeed;
            }

        }
        return originalSeed;
    }

    public List<SeedRange> getSeedRangeList(InputFileParser.SoilType soilType)  {
        if (!seedRangeMap.containsKey(soilType)) {
            seedRangeMap.put(soilType, new ArrayList<>());
        }
        return seedRangeMap.get(soilType);
    }

    public void addSeed(long seedNumber) {
        seeds.add(new Seed(seedNumber));
    }
}