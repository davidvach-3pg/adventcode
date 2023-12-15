package cz.itexpert.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public enum Direction {

    NORTH(1, 'U'), EAST(4, 'R'), SOUTH(2, 'D'), WEST(3, 'L'), CENTER(8, 'C');

    public final int num;
    public final int code;

    Direction(int num, char code) {
        this.num = num;
        this.code = code;
    }

    public static Stream<Direction>  four() {
        return Arrays.stream(fourDirections());
    }

    public static Direction[] fourDirections() {
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }

    public Loc   move(Loc currentLocation) {
        return move(currentLocation, 1);
    }

    public Loc move(Loc currentLocation, long amount) {
        return switch (this) {
            case SOUTH -> new Loc(currentLocation.x, currentLocation.y + amount);
            case NORTH -> new Loc(currentLocation.x, currentLocation.y - amount);
            case EAST -> new Loc(currentLocation.x + amount, currentLocation.y);
            case WEST -> new Loc(currentLocation.x - amount, currentLocation.y);
            case CENTER -> new Loc(currentLocation.x, currentLocation.y);
        };
    }

    public Direction turnDegrees(int degrees, boolean right) {
        int num = degrees % 360;
        Direction dir = this;
        while (num > 0) {
            dir = dir.turn(right);
            num -= 90;
        }
        return dir;
    }

    public Direction turnDegrees(int degrees) {
        return turnDegrees(abs(degrees), degrees > 0);
    }

    public Direction turn(boolean right) {
        int cur = ordinal() + (right ? 1 : -1);
        if (cur == fourDirections().length) cur = 0;
        else if (cur == -1) cur = fourDirections().length - 1;
        return fourDirections()[cur];
    }
}
