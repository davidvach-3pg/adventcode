import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.Math.abs;

public enum Direction {

    SEVER(1, 'U'), VYCHOD(4, 'R'), JIH(2, 'D'), ZAPAD(3, 'L'), CENTER(8, 'C');

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
        return new Direction[]{SEVER, VYCHOD, JIH, ZAPAD};
    }

    public Loc   move(Loc currentLocation) {
        return move(currentLocation, 1);
    }

    public Loc move(Loc currentLocation, long amount) {
        return switch (this) {
            case JIH -> new Loc(currentLocation.x, currentLocation.y + amount);
            case SEVER -> new Loc(currentLocation.x, currentLocation.y - amount);
            case VYCHOD -> new Loc(currentLocation.x + amount, currentLocation.y);
            case ZAPAD -> new Loc(currentLocation.x - amount, currentLocation.y);
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
