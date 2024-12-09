import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RunnerDay7 {

    public static boolean jokerMode = true;

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        InputFileParser inputFileParser = new InputFileParser();
        List<Hand> handCollection = inputFileParser.parseFile(RunnerDay7.class.getResourceAsStream("/day7.txt"));

        Collections.sort(handCollection);

        int order = 1;
        int sumA = 0;
        for (Hand hand : handCollection) {
            sumA += (hand.getBet() * order++);
        }


        Duration duration = Duration.between(start, LocalDateTime.now());
        System.out.println("SumA:  " + sumA + "  duration: " + duration.toSeconds());

    }


}
