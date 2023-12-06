import java.io.InputStream;
import java.util.*;

public class InputFileParser {

    enum SoilType {UNKNOWN, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION}

    ;

    public Collection<Race> parseFile(InputStream inputStream) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(inputStream));
        Collection<Race> raceCollection = new ArrayList<>();

        String[] timeLine = scanner.nextLine().replaceAll("\\s{1,}", " ").split(":")[1].split(" ");
        String[] distanceLine = scanner.nextLine().replaceAll("\\s{1,}", " ").split(":")[1].split(" ");
        for (int i = 1; i < timeLine.length; i++) {
            raceCollection.add(new Race(Long.valueOf(timeLine[i]), Long.valueOf(distanceLine[i])));
        }


        return raceCollection;
    }



}
