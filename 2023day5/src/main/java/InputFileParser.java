import java.io.InputStream;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class InputFileParser {

    enum SoilType {UNKNOWN, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION}

    ;

    public DataSet parseFile(InputStream inputStream) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(inputStream));
        DataSet dataSet = new DataSet();

        String nextLine;
        try {
            while (scanner.hasNextLine()) {
                nextLine = scanner.nextLine().replaceAll("\\s{1,}", " ");
                if (nextLine.startsWith("seeds:")) {
                    parseSeeds(dataSet, nextLine);
                }
                SoilType lastSoilType = SoilType.UNKNOWN;
                while (true) {
                    nextLine = scanner.nextLine().replaceAll("\\s{1,}", " ");
                    if (nextLine.trim().length() == 0) {
                        continue;
                    } else if (nextLine.startsWith("seed-to-soil map:")) {
                        lastSoilType = SoilType.SOIL;
                        continue;
                    } else if (nextLine.startsWith("soil-to-fertilizer map:")) {
                        lastSoilType = SoilType.FERTILIZER;
                        continue;
                    } else if (nextLine.startsWith("fertilizer-to-water map:")) {
                        lastSoilType = SoilType.WATER;
                        continue;
                    } else if (nextLine.startsWith("water-to-light map:")) {
                        lastSoilType = SoilType.LIGHT;
                        continue;
                    } else if (nextLine.startsWith("light-to-temperature map:")) {
                        lastSoilType = SoilType.TEMPERATURE;
                        continue;
                    } else if (nextLine.startsWith("temperature-to-humidity map:")) {
                        lastSoilType = SoilType.HUMIDITY;
                        continue;
                    } else if (nextLine.startsWith("humidity-to-location map")) {
                        lastSoilType = SoilType.LOCATION;
                        continue;
                    }

                    String[] textValues = nextLine.split(" ");
                    dataSet.getSeedRangeList(lastSoilType).add(new SeedRange(Long.valueOf(textValues[1]), Long.valueOf(textValues[0]), Long.valueOf(textValues[2])));
                }

            }
        } catch (NoSuchElementException e) {
            // end of file
        }
        return dataSet;
    }

    private void parseSeeds(DataSet dataSet, String line) {
        for (String seedNumberAsText : line.split(":")[1].split(" ")) {
            if (seedNumberAsText.length() > 0) {
                dataSet.addSeed(Long.valueOf(seedNumberAsText));
            }
        }

    }

}
