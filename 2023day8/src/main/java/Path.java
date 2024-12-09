public abstract class Path {

    public static Path of(String text) {
        if (text.equalsIgnoreCase("R")) {
            return new RightPath();
        } else if (text
                .equalsIgnoreCase("L")) {
            return new LeftPath();
        } else {
            throw new IllegalStateException("Unknown symbol" + text);
        }
    }


}
