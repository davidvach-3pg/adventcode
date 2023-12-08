import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunnerDay8 {

    public static boolean jokerMode = true;

    public static void main(String[] args) {
        LocalDateTime start = LocalDateTime.now();

        InputFileParser inputFileParser = new InputFileParser();
        PlayMap playMap = inputFileParser.parseFile(RunnerDay8.class.getResourceAsStream("/day8.txt"));


        Node currentNode = playMap.getStartNode();
        long countA = 0, countB  = 0;
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
        List<Node> newNodes = new ArrayList<>();
        int[] hopsToz = new int[startingNodes.size()];
        for (Node node: playMap.getNodeCache().values()) {
            if (node.getName().endsWith("A")) {
                startingNodes.add(node);
            }
        }
        int z = 0;

        boolean areAllNodesOnEnd = false;

        while (!areAllNodesOnEnd) {
            for (Path path : playMap.getPathList()) {
                countB++;
                for (Node node : startingNodes) {
                    newNodes.add(node.walk(path));
                }

                areAllNodesOnEnd = true;
                for (Node node : newNodes) {
                    if (!node.getName().endsWith("Z")) {
                        areAllNodesOnEnd = false;
                    } else {
                        z++;
                    }
                }

                z=0;
                startingNodes.clear();
                startingNodes.addAll(newNodes);
                newNodes.clear();
            }

        }

// 10 921 547 990 923
        Duration duration = Duration.between(start, LocalDateTime.now());
        System.out.println("countA:  " + countA + "  duration: " + duration.toSeconds());
        System.out.println("countB:  " + countB + "  duration: " + duration.toSeconds());

    }


}
