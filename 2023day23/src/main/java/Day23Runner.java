
import cz.itexpert.adventofcode.Builder;
import cz.itexpert.adventofcode.DayRunner;
import cz.itexpert.adventofcode.Direction;
import cz.itexpert.adventofcode.Loc;
import cz.itexpert.adventofcode.grid.CharacterGrid;
import cz.itexpert.adventofcode.network.FetchInput;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23Runner extends DayRunner {


    public Day23Runner() {
        super(23);
        new FetchInput().retrieveInput(23, 2023, getFIleFolder(23));
    }

    public static void main(String[] args) {
        new Day23Runner().printParts();
    }

    public record Path(Set<Loc> visited, Loc currentLoc) {
    }

    @Override
    public Object part1() {
        return solve(false);
    }

    @Override
    public Object part2() {
        return solve(true);
    }

    private long solve(boolean part2) {
        var input = new CharacterGrid(getFileAsString());
        Loc start = new Loc(1, 0);
        Builder<Set<Path>> curr = new Builder<>(HashSet::new);
        curr.get().add(new Path(new HashSet<>(List.of(start)), start));
        Loc target = new Loc(input.width() - 2, input.height() - 1);
        long longest = 0;
        while (!curr.get().isEmpty()) {
            curr.setNew(curr.get().parallelStream().flatMap(path -> {
                Loc loc = path.currentLoc;
                return Direction.four()
                        .filter(d -> checkDir(d, input.getChar(loc), part2) && checkDir(d, input.getChar(d.move(loc)), part2))
                        .map(d -> d.move(loc))
                        .filter(l -> !path.visited.contains(l))
                        .map(l -> new Path(new HashSet<>(path.visited) {{
                            add(l);
                        }}, l));
            }).collect(Collectors.toSet()));
            longest = Math.max(longest, curr.getNew().stream().mapToInt(p -> p.visited.size() - 1).max().getAsInt());
            curr.getNew().removeIf(p -> p.currentLoc.equals(target));
            curr.refresh();
        }
        return longest;
    }

    private boolean checkDir(Direction d, char c, boolean part2) {
        return switch (c) {
            case '>' -> part2 || d == Direction.EAST;
            case '<' -> part2 || d == Direction.WEST;
            case '^' -> part2 || d == Direction.NORTH;
            case 'v' -> part2 || d == Direction.SOUTH;
            case '#', 0 -> false;
            default -> true;
        };
    }
}