import java.io.InputStream;
import java.util.*;

public class InputFileParser {

    enum SoilType {UNKNOWN, SOIL, FERTILIZER, WATER, LIGHT, TEMPERATURE, HUMIDITY, LOCATION}

    ;

    public List<Hand> parseFile(InputStream inputStream) {
        Scanner scanner = new
                Scanner(Objects.requireNonNull(inputStream));
        List<Hand> handCollection = new ArrayList<>();

        String[] line ;
        while (scanner.hasNext()) {
            line = scanner.nextLine().replaceAll("\\s{1,}", " ").split(" ");
            handCollection.add(new Hand(line[0], Long.valueOf(line[1])));
        }



        return handCollection;
    }



}
