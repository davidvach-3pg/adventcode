import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayMap {

    private List<Path> pathList = new ArrayList<>();

    private Map<String, Node> nodeCache = new HashMap();

    private Node startNode;

    private Node endNode;

    public List<Path> getPathList() {
        return pathList;
    }

    public Map<String, Node> getNodeCache() {
        return nodeCache;
    }

    public void registerNode(String rootNodeSymbol, String leftNodeSymbol, String rightNodeSymbol) {
        rootNodeSymbol = rootNodeSymbol.trim();
        leftNodeSymbol = leftNodeSymbol.trim();
        rightNodeSymbol = rightNodeSymbol.trim();
       findNodeAndCreateIfNotExists(rootNodeSymbol).registerDirection(findNodeAndCreateIfNotExists(leftNodeSymbol), findNodeAndCreateIfNotExists(rightNodeSymbol));
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void regusterStartAndEndNode() {
        startNode = nodeCache.get(("AAA"));
        endNode = nodeCache.get(("ZZZ"));
    }



    private Node findNodeAndCreateIfNotExists(String nodeName) {
        Node nodeToReturn;
        if (!nodeCache.containsKey(nodeName)) {
            nodeToReturn =  new Node(nodeName);
            nodeCache.put(nodeName,nodeToReturn );
        } else {
            nodeToReturn = nodeCache.get(nodeName);
        }
        return nodeToReturn;
    }
}
