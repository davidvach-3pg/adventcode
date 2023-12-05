import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public class RunnerDay5 {

    public static void main(String[] args) {

        InputFileParser inputFileParser = new InputFileParser();
        DataSet dataSet = inputFileParser.parseFile(RunnerDay5.class.getResourceAsStream("/day5.txt"));

        Long minimum = Long.MAX_VALUE;

        for (Seed seed: dataSet.getSeeds()) {
           Seed location = calculateLocation(dataSet, seed);

            minimum = (minimum < location.getNumber()) ? minimum : location.getNumber();
        }

        System.out.println("Minimum: " + minimum);
        minimum = Long.MAX_VALUE;
        LocalDateTime start = LocalDateTime.now();
        for (SeedData seedData: dataSet.getSeedsData()) {
            System.out.println("Counting seed: " + seedData.getStartingPosition().getNumber());
            for (int seedOffset = 0; seedOffset < seedData.getNoOfSeeds(); seedOffset++) {
                Seed location = calculateLocation(dataSet, new Seed(seedData.getStartingPosition().getNumber()+seedOffset));

                minimum = (minimum < location.getNumber()) ? minimum : location.getNumber();
            }
        }
        Duration duration = Duration.between(start, LocalDateTime.now());
        System.out.println("Minimum: " + minimum + " duration: " + duration.toSeconds());

    }

    private static Seed calculateLocation(DataSet dataSet, Seed seed) {
        Seed soil = dataSet.findNextSeed(InputFileParser.SoilType.SOIL, seed);
        Seed fertilizer  = dataSet.findNextSeed(InputFileParser.SoilType.FERTILIZER, soil);
        Seed water  = dataSet.findNextSeed(InputFileParser.SoilType.WATER, fertilizer);
        Seed light  = dataSet.findNextSeed(InputFileParser.SoilType.LIGHT, water);
        Seed temperature  = dataSet.findNextSeed(InputFileParser.SoilType.TEMPERATURE, light);
        Seed humidity  =dataSet.findNextSeed(InputFileParser.SoilType.HUMIDITY, temperature);
        Seed location = dataSet.findNextSeed(InputFileParser.SoilType.LOCATION, humidity);

//        System.out.println("Seed: " + seed.getNumber() + " SOIL: " + soil.getNumber() + " FERTILIZER: " + fertilizer.getNumber()
//                + " WATER: " + water.getNumber() + " LIGHT: " + light.getNumber() + " TEMPERATURE: " + temperature.getNumber()
//                + " HUMIDITY: " + humidity.getNumber() + " LOCATION: "  + location.getNumber());
        return location;
    }

}
