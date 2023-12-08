public class Node {

    private String name;

    private Node left, right;

    public Node(String name) {
        this.name = name;
    }

    public void registerDirection(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
    public Node walk(Path path) {
        if (path instanceof LeftPath) {
            return left;
        } else {
            return right;
        }
    }

    public String getName() {
        return name;
    }
}
