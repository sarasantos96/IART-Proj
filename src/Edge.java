/**
 * Created by dalug on 02/05/2017.
 */
public class Edge {
    private String id;
    private Vertex source;
    private Vertex destination;
    private int weight;
    private Road road;

    public Edge(String id, Vertex source, Vertex destination, int weight, Road road) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.road = road;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "source=" + source +
                ", destination=" + destination +
                '}';
    }
}
