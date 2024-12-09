import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RunnerDay9 {


    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();
        long countA = 0, countB = 0;

        Scanner scanner = new
                Scanner(Objects.requireNonNull(RunnerDay9.class.getResourceAsStream("/day9.txt")));

        String[] singleLine;
        long[][] input;
        int line = 0;
        while (scanner.hasNext()) {
            line = 0;
            singleLine = scanner.nextLine().replaceAll("\\s{1,}", " ").split(" ");
            input = new long[singleLine.length][singleLine.length];
            input[line] = Arrays.stream(singleLine).mapToLong(Long::parseLong).toArray();
            while(!LongStream.of(input[line]).allMatch(i -> i == 0)) {
                for (int i = 0; i < input[line].length-1-line; i++) {
                    input[line+1][i] = input[line][i+1] - input[line][i];
                }
                line++;
            }

            int lastIndex = input[0].length - line;
            long tempA = 0;
            long tempB = 0;
            while (line >= 0) {
                tempA  +=  input[line][lastIndex-1];
                if (line >= 1) {
                    tempB = input[line - 1][0] - tempB;
                }
                line--; lastIndex++;
            }
            countA += tempA;
            countB += tempB;
            System.out.println("tempA: " + tempA + " tempB: " + tempB);
        }

        Duration duration = Duration.between(start, LocalDateTime.now());
        System.out.println("countA:  " + countA + "  duration: " + duration.toSeconds());
        System.out.println("countB:  " + countB + "  duration: " + duration.toSeconds());

    }


}
