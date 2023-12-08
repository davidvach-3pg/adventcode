import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Day8 {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner file = new Scanner(RunnerDay8.class.getResourceAsStream("/day8.txt"));
        int steps = 0, allSteps = 0;
        char[] lr = file.nextLine().toCharArray();
        Map<String, List<String>> nodes = new HashMap<>();
        file.nextLine();
        while(file.hasNextLine()) {
            String s = file.nextLine();
            nodes.put(s.split(" = ")[0], Arrays.asList(s.split("\\(")[1].split(",")[0], s.split(", ")[1].split("\\)")[0]));
        }
        String curr = "AAA";
        List<String> allCurr = new ArrayList<>(nodes.keySet());
        allCurr.removeIf((e) -> { return !e.endsWith("A"); });
        int[] zLoop = new int[allCurr.size()];
        while(!curr.equals("ZZZ")) { curr = nodes.get(curr).get(lr[steps++ % lr.length] == 'L' ? 0 : 1); }

        while(!IntStream.of(zLoop).allMatch(i -> i > 0)) {
            for(int i = 0; i < allCurr.size(); i++) {
                if(allCurr.get(i).endsWith("Z")) { zLoop[i] = allSteps; }
                allCurr.set(i, nodes.get(allCurr.get(i)).get(lr[allSteps % lr.length] == 'L' ? 0 : 1));
            }
            allSteps++;
        }
        System.out.println("all steps: " + allSteps);
        System.out.printf("Steps to reach ZZZ: %s\nTotal steps for all **Z: %s\n", steps, lcmArr(zLoop, 0, zLoop.length));
    }

    public static long lcmArr(int[] arr, int start, int end) {
        if(end - start == 1) { return lcm(arr[start], arr[end - 1]); }
        return lcm(arr[start], lcmArr(arr, start + 1, end));
    }

    public static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    public static long gcd(long a, long b) {
        if(a < b) { return gcd(b, a); }
        if(a % b == 0) { return b; }
        return gcd(b, a % b);
    }
}