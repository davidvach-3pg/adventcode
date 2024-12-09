
import cz.itexpert.adventofcode.ACUtil;
import cz.itexpert.adventofcode.DayRunner;
import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.grid.CharacterGrid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;

public class Day18Runner extends DayRunner {



    public Day18Runner() {
        super(18);
    }

    public static void main(String[] args) {
        new Day18Runner().printParts();
    }

    public record Dig(Direction dir, int n, long part2N, Direction part2Dir) {
        public Dig(char dir, int n, String hex) {
            this(Direction.getByDirCode(dir), n, Long.parseLong(hex.substring(0, hex.length() - 1), 16), hex.charAt(hex.length() - 1) == '0' ? Direction.EAST : hex.charAt(hex.length() - 1) == '1' ? Direction.SOUTH : hex.charAt(hex.length() - 1) == '2' ? Direction.WEST : Direction.NORTH);
        }
    }

    @Override
    public Object part1() {
        var in = input();
        Loc start = new Loc(0, 0);
        CharacterGrid grid = new CharacterGrid();
        for (Dig dig : in) {
            Loc end = dig.dir.move(start, dig.n);
            grid.draw(start, end, '#');
            start = end;
        }
        grid.floodFill(new Loc(1, 1), c -> c != '#').forEach(l -> grid.set(l, '#'));

        return grid.grid.size();
    }

    @Override
    public Object part2() {
        var in = input();
        Loc start = new Loc(0, 0);
        java.util.List<Loc> route = new ArrayList<>();
        CharacterGrid grid = new CharacterGrid();
        long outline = 0;
        for (Dig dig : in) {
            Loc end = dig.part2Dir.move(start, dig.part2N);
            outline += start.distance(end);
            route.add(end);
            grid.draw(start, end, '#');
            start = end;
        }
        return outline / 2 + ACUtil.connectedPairs(route).mapToLong(p -> (p.a().y + p.b().y) * (p.a().x - p.b().x)).sum() / 2 + 1;
    }

    private List<Dig> input() {
        return dayStream().map(s -> {
            String[] text = s.split(" ");
            return new Dig(text[0].charAt(0), Integer.parseInt(text[1]),  text[2].substring(text[2].indexOf("(")+2, text[2].indexOf(")")));
        }).toList();
    }
}
