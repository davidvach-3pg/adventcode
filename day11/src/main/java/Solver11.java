import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collection;

public class Solver11 {
    public static String getResourceAsString() {
        try {
            return Files.readString(new File("day11/src/main/resources/day11.txt").toPath());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public static void main(String[] args) {

        Solver11 s11 = new Solver11();
        s11.solve(Arrays.asList(getResourceAsString().split("\n")), 1_000_000);
//        Solver11.solve("day11.txt", new Day11Solver(2), new Day11Sol/ver(1_000_000));
    }

    public void solve(Collection<String> lines, int expandRate) {
        var universe = new Universe(lines);
        long result = 0;
        var indexMap = universe.getGalaxyIndexMap();
        for (int i = 1; i <= indexMap.keySet().size() - 1; i++) {
            for (int j = i + 1; j <= indexMap.keySet().size(); j++) {
                result += universe.computeDistance(indexMap.get(i), indexMap.get(j), expandRate);
            }
        }
        System.out.println(result);
    }
}

/*

Team,

We will have a meeting on Wednesday at 9:30am (15:30 for the Czech folks) where we will discuss Architecture and Implementation Design for the Website 3.0 (~Anewgo Website Platform).
We will meet over the Keng's zoom link: https://us02web.zoom.us/j/89672147286?pwd=bWl4SmFNWDhWaW9OMmpOVVNET2pjZz09 .

Outcome of the meeting: For each topic, we will assign a group of two developer and their task will be to come with proposal for a solutions


 */
