import java.util.*;

/**
 * Created by Sara Santos on 02/05/2017.
 */
public class DijsktraAlgorithm {
    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> resolvedNodes;
    private Set<Vertex> unresolvedNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;

    public DijsktraAlgorithm(List<Vertex> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        this.resolvedNodes = new HashSet<>();
        this.unresolvedNodes = new HashSet<>();
        this.predecessors = new HashMap<>();
        this.distance = new HashMap<>();
    }

    public Map<Vertex, Integer> getDistance() {
        return distance;
    }

    public Map<Vertex, Vertex> getPredecessors() {
        return predecessors;
    }

    public void run(Vertex source){
        distance.put(source,0);
        unresolvedNodes.add(source);
        while(unresolvedNodes.size() != 0){
            Vertex node = getMin();
            resolvedNodes.add(node);
            unresolvedNodes.remove(node);
            findMinDistances(node);
        }
    }

    private void findMinDistances(Vertex node){
        List<Vertex> adjNodes = getNeighbors(node);
        for (Vertex v : adjNodes){
            if(getShortDistance(v)> getShortDistance(node) + getDistance(node,v)){
                distance.put(v, getShortDistance(node) + getDistance(node,v));
                predecessors.put(v,node);
                unresolvedNodes.add(v);
            }
        }
    }

    private int getDistance(Vertex node, Vertex v){
        for (Edge edge : edges){
            if(edge.getSource().equals(node) && edge.getDestination().equals(v)){
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Edge Not Found (Vertexes with no connections)");
    }

    private List<Vertex> getNeighbors(Vertex node){
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges ){
            if(edge.getSource().equals(node) && !isResolvedNode(edge.getDestination())){
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private Vertex getMin(){
        Vertex min = null;
        for(Vertex vertex : unresolvedNodes){
            if(min == null){
                min = vertex;
            }else if(getShortDistance(vertex) < getShortDistance(min)){
                min = vertex;
            }
        }
        return min;
    }

    private boolean isResolvedNode(Vertex node){
        return resolvedNodes.contains(node);
    }

    private int getShortDistance(Vertex dest){
        Integer dist = distance.get(dest);
        if(dist == null){
            return Integer.MAX_VALUE;
        }else{
            return dist;
        }
    }

    public LinkedList<Vertex> getPath(Vertex destination){
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = destination;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}
