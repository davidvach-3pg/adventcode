import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class RunnerDay8 {

    public static boolean jokerMode = true;

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        InputFileParser inputFileParser = new InputFileParser();
        PlayMap playMap = inputFileParser.parseFile(RunnerDay8.class.getResourceAsStream("/day8.txt"));


        Node currentNode = playMap.getStartNode();
        long countA = 0;
        long countB = 0;
        while (!currentNode.equals(playMap.getEndNode())) {
            for (Path path : playMap.getPathList()) {
                countA++;
                currentNode = currentNode.walk(path);
                if (currentNode.equals(playMap.getEndNode())) {
                    break;
                }
            }
        }

        // solution B
        List<Node> startingNodes = new ArrayList<>();
        List<Node> endNodes = new ArrayList<>();


        for (Node node : playMap.getNodeCache().values()) {
            if (node.getName().endsWith("A")) {
                startingNodes.add(node);
            } else if (node.getName().endsWith("Z")) {
                endNodes.add(node);
            }
        }

        Node node;
        long[] countsToEnd = new long[startingNodes.size()];
        while (!LongStream.of(countsToEnd).allMatch(i -> i > 0)) {
            for (Path path : playMap.getPathList()) {
                countB++;
                for (int position = 0; position < startingNodes.size(); position++) {
                    node = startingNodes.get(position).walk(path);
                    if (endNodes.contains(node)) {
                        countsToEnd[position] = countB;
                    }
                        startingNodes.set(position, node);
                }
            }
        }

        boolean toContinue = true;
        int t = 0;
        long[] countsToEndCopy =  countsToEnd.clone();
        while (toContinue) {
            t++;
            toContinue = false;
            countB =  Arrays.stream(countsToEnd).max().getAsLong();
            for (int position = 0; position < countsToEnd.length; position++) {
                if (countB % countsToEndCopy[position] != 0) {
                    toContinue = true;
                    countsToEnd[position] += countsToEndCopy[position];
                    break;
                }
            }

        }
        Duration duration = Duration.between(start, LocalDateTime.now());
        System.out.println("countA:  " + countA + "  duration: " + duration.toSeconds());
        System.out.println("countB:  " + countB + "  duration: " + duration.toSeconds());

    }


}
