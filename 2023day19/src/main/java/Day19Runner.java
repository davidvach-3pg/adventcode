import cz.itexpert.adventofcode.DayRunner;
import cz.itexpert.adventofcode.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

public class Day19Runner extends DayRunner {


    public Day19Runner() {
        super(19, "-test");
    }

    public static void main(String[] args) {
        new Day19Runner().printParts();
    }

    @Override
    public Object part1() {
        Map<String, List<WorkflowItem>> workflows = getWorkflows();
        List<Part> parts = getParts();
        return parts.stream().filter(p -> isAccepted(workflows, p, "in")).mapToLong(p -> p.numbers.values().stream().mapToLong(l -> l).sum()).sum();
    }

    private boolean isAccepted(Map<String, List<WorkflowItem>> workflows, Part p, String workflow) {
        var items = workflows.get(workflow);
        for (var item : items) {
            if (item.c == ' ') {
                return checkItem(workflows, p, item);
            } else {
                if (item.op == '<') {
                    if (p.numbers.get(item.c) < item.n) return checkItem(workflows, p, item);
                } else if (item.op == '>') {
                    if (p.numbers.get(item.c) > item.n) return checkItem(workflows, p, item);
                } else throw new IllegalStateException("Unknown operator: " + item.op);
            }
        }
        throw new IllegalStateException("Reached end = impossible");
    }

    private boolean checkItem(Map<String, List<WorkflowItem>> workflows, Part p, WorkflowItem item) {
        if (item.s.equals("A")) return true;
        else if (item.s.equals("R")) return false;
        else return isAccepted(workflows, p, item.s);
    }

    private  List<Part> getParts() {
        boolean secondPart = false;
        // {x=787,m=2655,a=1222,s=2876}
        List<Number> numbers;
        List<Part> parts = new ArrayList<>();
        for (String line: getFileAsString().split("\n")) {
            if (!secondPart) {
                secondPart = line.trim().isEmpty();
                if (!secondPart) continue;
            }
            if (line.trim().isEmpty()) {
                continue;
            }
            numbers = new ArrayList<>();
            String assignments = line.substring(line.indexOf("{")+1, line.indexOf("}"));
            for (String assignment: assignments.split(",")) {
                numbers.add(new Number(assignment.charAt(0), Long.parseLong(assignment.split("=")[1])));
            }
            parts.add(new Part(numbers));
        }
        return parts;
    }

    public record Part(Map<Character, Long> numbers) {
        public Part(List<Number> numbers) {
            this(numbers.stream().collect(toMap(n -> n.c, n -> n.n)));
        }
    }

    @Override
    public Object part2() {
        var workflows = getWorkflows();
        return countAccepted(workflows, new HashMap<>("xmas".chars().boxed().collect(toMap(e -> (char) e.intValue(), e -> Constraint.create()))), "in");
    }

    private long countAccepted(Map<String, List<WorkflowItem>> workflows, Map<Character, Constraint> constraints, String workflow) {
        var items = workflows.get(workflow);
        long sum = 0;
        for (var item : items) {
            if (item.c == ' ') {
                sum += checkItem2(workflows, constraints, item);
            } else {
                if (item.op == '<') {
                    var newConstraints = new HashMap<>(constraints);
                    var constraint = constraints.get(item.c);
                    newConstraints.put(item.c, constraint.lessThan(item.n));
                    constraints.put(item.c, constraint.moreThan(item.n - 1));
                    sum += checkItem2(workflows, newConstraints, item);
                } else if (item.op == '>') {
                    var newConstraints = new HashMap<>(constraints);
                    var constraint = constraints.get(item.c);
                    newConstraints.put(item.c, constraint.moreThan(item.n));
                    constraints.put(item.c, constraint.lessThan(item.n + 1));
                    sum += checkItem2(workflows, newConstraints, item);
                } else throw new IllegalStateException("Unknown operator: " + item.op);
            }
        }
        return sum;
    }

    private long checkItem2(Map<String, List<WorkflowItem>> workflows, Map<Character, Constraint> constraints, WorkflowItem item) {
        if (item.s.equals("A"))
            return constraints.values().stream().mapToLong(Constraint::numsAccepted).reduce(1, (a, b) -> a * b);
        else if (item.s.equals("R")) return 0;
        else return countAccepted(workflows, constraints, item.s);
    }

    public record Constraint(long moreThan, long lessThan) {
        public static Constraint create() {
            return new Constraint(0, 4001);
        }

        public Constraint moreThan(long moreThan) {
            return new Constraint(Math.max(this.moreThan, moreThan), lessThan);
        }

        public Constraint lessThan(long lessThan) {
            return new Constraint(moreThan, Math.min(this.lessThan, lessThan));
        }

        public long numsAccepted() {
            if (moreThan > lessThan) return 0;
            return lessThan - moreThan - 1;
        }
    }



    public record Number(char c, long n) {
    }

    public record WorkflowItem(char c, char op, long n, String s) {
        public WorkflowItem(String s) {
            this(' ', ' ', 0, s);
        }
    }

    public record Workflow(List<WorkflowItem> steps, String name) {
        public Workflow(String name, List<String> strings) {
            this(strings.stream().map(s -> {
                try {
                    // a<2006:qkq
                    String[] splitText = s.split(":");
                    String nextRule = splitText[1];
                    char variable = splitText[0].charAt(0);
                    char operand = splitText[0].charAt(1);
                    long value = Long.parseLong(splitText[0].substring(2));

                    return new WorkflowItem(variable, operand, value, nextRule);
                } catch (Exception e) {
                    return new WorkflowItem(s);
                }
            }).toList(), name);
        }
    }

    private Map<String, List<WorkflowItem>> getWorkflows() {

        List<Workflow> workflows = new ArrayList<>();
        for (String line: getFileAsString().split("\n")) {
            if (line.trim().isEmpty()) {
                break;
            }
            String name = line.substring(0, line.indexOf("{"));
            String rules = line.substring(line.indexOf("{")+1, line.indexOf("}"));
            System.out.println(name);
            workflows.add(new Workflow(name, List.of(rules.split(","))));
        }

       return  workflows.stream().collect(toMap(e -> e.name, e -> e.steps));
    }
}
