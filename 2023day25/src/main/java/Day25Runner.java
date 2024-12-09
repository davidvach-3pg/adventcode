import cz.itexpert.adventofcode.DayRunner;
import cz.itexpert.adventofcode.network.FetchInput;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day25Runner extends DayRunner {



    public Day25Runner() {
        super(25, "");
        new FetchInput().retrieveInput(25, 2023, getFIleFolder(25));
    }

    public static void main(String[] args) {
        new Day25Runner().printParts();
    }

    @Override
    public Object part1() {
        Map<String, Set<String>> graph = loadData();
        getFileAsString().lines().forEach(l->processLine(l, graph));
        Map<Set<String>, Integer> edgeFrequency = new HashMap<Set<String>, Integer>();
        List<String> vertexes = new ArrayList<>(graph.keySet());
        for(int i=0; i<vertexes.size(); ++i) {
            String start = vertexes.get(i);
            for(int j=i+1; j<vertexes.size(); ++j) {
                String target = vertexes.get(j);
                markEdges(start, target, graph, edgeFrequency);
            }
        }
        edgeFrequency.entrySet().stream().sorted(Comparator.<Entry<Set<String>, Integer>>comparingInt(e->e.getValue()).reversed()).limit(3).forEach(e->{
            cutEdge(graph, e.getKey());
        });
        int part1Size = findConnectedSize(graph.keySet().iterator().next(), graph);
        int part2Size = graph.size()-part1Size;
        System.out.println(part1Size*part2Size);
        System.out.println("Marry Christmas!");

        return part1Size*part2Size;
    }

    private static void markEdges(String start, String target, Map<String, Set<String>> graph,
                                  Map<Set<String>, Integer> edgeFrequency) {
        Queue<Step> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Step(start, List.of()));
        visited.add(start);
        while (!queue.isEmpty()) {
            Step curr = queue.poll();
            if(target.equals(curr.vertex)) {
                curr.edges.forEach(e->{
                    int v = edgeFrequency.getOrDefault(e, 0);
                    edgeFrequency.put(e, v+1);
                });
                return;
            }
            graph.get(curr.vertex).stream().filter(n->!visited.contains(n)).forEach(n->{
                List<Set<String>> nextEdges = new ArrayList<>(curr.edges());
                nextEdges.add(new HashSet<>(Set.of(curr.vertex, n)));
                Step nextStep = new Step(n, nextEdges);
                queue.add(nextStep);
                visited.add(n);
            });
        }
    }

    private static void cutEdge(Map<String, Set<String>> graph, Set<String> edge) {
        var it = edge.iterator();
        String a = it.next();
        String b = it.next();
        graph.get(a).remove(b);
        graph.get(b).remove(a);
    }

    private static int findConnectedSize(String start, Map<String, Set<String>> graph) {
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String curr = queue.poll();
            graph.get(curr).stream().filter(n->!visited.contains(n)).forEach(n->{
                queue.add(n);
                visited.add(n);
            });
        }
        return visited.size();
    }

    record Step(String vertex, List<Set<String>> edges) {

    }
    private static void processLine(String line, Map<String, Set<String>> graph) {
        var parts = line.split(": ");
        var name = parts[0];
        var cons = parts[1].split(" ");
        for(var con: cons) {
            markConnectionn(graph, name, con);
        }
    }

    private static void markConnectionn(Map<String, Set<String>> graph, String from, String to) {
        graph.computeIfAbsent(from, (k)->new HashSet<>()).add(to);
        graph.computeIfAbsent(to, (k)->new HashSet<>()).add(from);
    }

    @Override
    public Object part2() {
        return "";
    }

    private Map<String, Set<String>> loadData() {
        Map<String, Set<String>> result = new HashMap<>();
        Arrays.stream(getFileAsString().split("\n")).forEach(
                line -> {
                    result.put(line.split(":")[0].trim(), Arrays.stream(line.split(":")[1].split(" ")).map(String::trim).filter(item -> !item.isEmpty()).collect(Collectors.toSet()));
                }
        );
        return result;
    }
}
