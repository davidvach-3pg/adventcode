package cz.itexpert.adventofcode;

import java.util.List;
import java.util.stream.Stream;

import static cz.itexpert.adventofcode.Pair.pair;
import static java.util.stream.IntStream.range;

public interface ACUtil {

    public static <A> Stream<Pair<A, A>> allPairs(List<A> l) {
        return range(0, l.size()).boxed().flatMap(i -> range(i + 1, l.size()).mapToObj(j -> new Pair<>(l.get(i), l.get(j))));
    }

    public static <A> Stream<Pair<A, A>> connectedPairs(List<A> l) {
        return range(1, l.size()).mapToObj(i -> pair(l.get(i - 1), l.get(i)));
    }

}
