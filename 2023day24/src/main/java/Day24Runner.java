
import cz.itexpert.adventofcode.DayRunner;
import cz.itexpert.adventofcode.Loc3D;
import cz.itexpert.adventofcode.network.FetchInput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day24Runner extends DayRunner {


    public Day24Runner() {
        super(24, "");
        new FetchInput().retrieveInput(24, 2023, getFIleFolder(24));
    }

    public static void main(String[] args) {
        new Day24Runner().printParts();
    }

    @Override
    public Object part1() {
        List<Stone> stones  = parseInput();
        double min = 200000000000000.0;
        double max = 400000000000000.0;
        long answer = 0;
        for (int i = 0; i < stones.size(); i++) {
            for (int j = i + 1; j < stones.size(); j++) {
                Stone first = stones.get(i);
                Stone second = stones.get(j);
                double denom = (first.velocity.x * second.velocity.y) - (first.velocity.y * second.velocity.x);
                if (denom == 0) {
                    if (first.loc3D.x == second.loc3D.x && first.loc3D.y == second.loc3D.y) {
                        if (first.loc3D.x >= min && first.loc3D.x <= max && first.loc3D.y >= min && first.loc3D.y <= max) {
                            answer++;
                        }
                    }
                }
                double numer1 = ((second.loc3D.x - first.loc3D.x) * second.velocity.y) - ((second.loc3D.y - first.loc3D.y) * second.velocity.x);
                double numer2 = ((first.loc3D.x - second.loc3D.x) * first.velocity.y) - ((first.loc3D.y - second.loc3D.y) * first.velocity.x);
                double intersectionX = (numer1 / denom) * first.velocity.x + first.loc3D.x;
                double intersectionY = (numer1 / denom) * first.velocity.y + first.loc3D.y;
                if (intersectionX >= min && intersectionX <= max && intersectionY >= min && intersectionY <= max) {
                    if ((numer1 / denom) > 0 && (numer2 / denom) < 0) {
                        answer++;
                    }
                }
            }
        }


        return answer;
    }

    @Override
    public Object part2() {
        List<Stone> stones = parseInput();
        Stone h1 = stones.get(0);
        Stone h2 = stones.get(1);

        int range = 500;
        for (int vx = -range; vx <= range; vx++) {
            for (int vy = -range; vy <= range; vy++) {
                for (int vz = -range; vz <= range; vz++) {
                    System.out.println("vx: " + vx + " vy: " + vy + " vz: " + vz);
                    if (vx == 0 || vy == 0 || vz == 0) {
                        continue;
                    }

                    // Find starting point for rock that will intercept first two hailstones (x,y) on this trajectory

                    // simultaneous linear equation (from part 1):
                    // H1:  x = A + a*t   y = B + b*t
                    // H2:  x = C + c*u   y = D + d*u
                    //
                    //  t = [ d ( C - A ) - c ( D - B ) ] / ( a * d - b * c )
                    //
                    // Solve for origin of rock intercepting both hailstones in x,y:
                    //     x = A + a*t - vx*t   y = B + b*t - vy*t
                    //     x = C + c*u - vx*u   y = D + d*u - vy*u

                    long A = h1.loc3D.x, a = h1.velocity.x - vx;
                    long B = h1.loc3D.y, b = h1.velocity.y - vy;
                    long C = h2.loc3D.x, c = h2.velocity.x - vx;
                    long D = h2.loc3D.y, d = h2.velocity.y - vy;

                    // skip if division by 0
                    if (c == 0 || (a * d) - (b * c) == 0) {
                        continue;
                    }

                    // Rock intercepts H1 at time t
                    long t = (d * (C - A) - c * (D - B)) / ((a * d) - (b * c));

                    // Calculate starting position of rock from intercept point
                    long x = h1.loc3D.x + h1.velocity.x * t - vx * t;
                    long y = h1.loc3D.y + h1.velocity.y * t - vy * t;
                    long z = h1.loc3D.z + h1.velocity.z * t - vz * t;


                    // check if this rock throw will hit all hailstones

                    boolean hitall = true;
                    for (int i = 0; i < stones.size(); i++) {

                        Stone h = stones.get(i);
                        long u;
                        if (h.velocity.x != vx) {
                            u = (x - h.loc3D.x) / (h.velocity.x - vx);
                        } else if (h.velocity.y != vy) {
                            u = (y - h.loc3D.y) / (h.velocity.y - vy);
                        } else if (h.velocity.z != vz) {
                            u = (z - h.loc3D.z) / (h.velocity.z - vz);
                        } else {
                            throw new RuntimeException();
                        }

                        if ((x + u * vx != h.loc3D.x + u * h.velocity.x) || (y + u * vy != h.loc3D.y + u * h.velocity.y) || ( z + u * vz != h.loc3D.z + u * h.velocity.z)) {
                            hitall = false;
                            break;
                        }
                    }

                    if (hitall) {
                        System.out.printf("%d %d %d   %d %d %d   %d %n", x, y, z, vx, vy, vz, x + y + z);
                        return  x + y + z;
                    }
                }
            }
        }
        return 0;

    }

    public record Velocity(int x, int y, int z) {
        Velocity(int[] data) {
            this(data[0], data[1], data[2]);
        }
    }

    public class Line {
        private long a;
        private long b=1;
        private long c;

        public Line(Loc3D loc3D1, Loc3D loc3D2) {
            calulateA(loc3D1, loc3D2);
            calulateC(loc3D1, loc3D2);
        }

        private void calulateA(Loc3D loc3D1, Loc3D loc3D2) {
            a =  (loc3D1.y-loc3D1.y)/(loc3D2.x-loc3D2.x);
        }
        private void  calulateC(Loc3D loc3D1, Loc3D loc3D2) {
           c  =  (loc3D1.y-a*loc3D2.x);
        }

    }

    public Loc3D calculateIntersection(Line line1, Line line2) {
        long x = (line1.b*line2.c- line2.b*line1.c)/(line1.a* line2.b- line2.a* line1.b);
        long y = (line1.c*line2.a- line2.c*line1.a)/(line1.a* line2.b- line2.a* line1.b);
        return new Loc3D(x, y, 0);
    }

    public record Stone(Loc3D loc3D, Velocity velocity) {

        private long getA() {
            Loc3D loc2 = new Loc3D(loc3D).move(velocity().x, velocity().y, velocity().z);
            return (loc3D.y-loc2.y)/(loc3D.x-loc2.x);
        }

        private long getB() {
            return (loc3D.y-getA()*loc3D.x);
        }
    }

    public List<Stone> parseInput() {
        return Arrays.stream(getFileAsString().split("\n")).map(line -> {
                    String[] data = line.split("@");
                    return new Stone(
                            new Loc3D(Arrays.stream(data[0].split(",")).map(String::trim).mapToLong(Long::valueOf).toArray()),
                            new Velocity(Arrays.stream(data[1].split(",")).map(String::trim).mapToInt(Integer::valueOf).toArray())
                    );

                }

        ).collect(Collectors.toList());
    }


}
