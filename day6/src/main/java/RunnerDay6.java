import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;

public class RunnerDay6 {

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        InputFileParser inputFileParser = new InputFileParser();
        Collection<Race> raceCollection = inputFileParser.parseFile(RunnerDay6.class.getResourceAsStream("/day6b.txt"));



        int sumA = 1;
        int count = 0;
        for (Race race: raceCollection) {
            count = race.countNumberOfPossibleWins();
            System.out.println("Race " + race + " count: " + count);
            sumA *= count;
        }
        Duration duration = Duration.between(start, LocalDateTime.now());
        System.out.println("SumA:  " + sumA + "  duration: " + duration.toSeconds());

    }



}
