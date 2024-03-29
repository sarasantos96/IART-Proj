import javafx.util.Pair;
import java.util.ArrayList;
import java.util.Map;

public class Vertex {
    private String id;
    private String name;
    private int population;
    private int cost;
    private int servedPopulation;
    private double ratio;
    private boolean hasHealthCareCenter;

    public Vertex(String id, String name, int population, int cost) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.cost = cost;
        this.ratio = (double) cost /  (double) population;
        this.hasHealthCareCenter = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getCost() {
        return cost;
    }

    public double getRatio() {
        return ratio;
    }

    public boolean isHasHealthCareCenter() {
        return hasHealthCareCenter;
    }

    public void setHasHealthCareCenter(boolean hasHealthCareCenter) {
        this.hasHealthCareCenter = hasHealthCareCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return name != null ? name.equals(vertex.name) : vertex.name == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getServedPopulation() {
        return servedPopulation;
    }

    public void calculateServedPopulation(Graph graph){
        DijsktraAlgorithm dijsktra = new DijsktraAlgorithm(graph.getVertexes(),graph.getEdges());
        dijsktra.run(this);
        Map<Vertex,Integer> distances = dijsktra.getDistance();
        int pop = 0;
        for (Map.Entry<Vertex, Integer> entry : distances.entrySet()){
            Vertex vertex = entry.getKey();
            Integer distance = entry.getValue();
            if(distance < Search.MAX_DIST)
                pop +=  vertex.getPopulation();
        }
        this.servedPopulation = pop;
    }

    public ArrayList getServedLocalities(Graph graph){
        ArrayList<Pair<Vertex,Integer>> localites = new ArrayList<>();
        DijsktraAlgorithm dijsktra = new DijsktraAlgorithm(graph.getVertexes(),graph.getEdges());
        dijsktra.run(this);

        Map<Vertex,Integer> distances = dijsktra.getDistance();
        for (Map.Entry<Vertex, Integer> entry : distances.entrySet()){
            Integer distance = entry.getValue();
            localites.add(new Pair<>(entry.getKey(),distance));
        }

        return localites;
    }

    public ArrayList getProxLocalities(Graph graph){
        ArrayList<Pair<Vertex,Integer>> localites = new ArrayList<>();
        DijsktraAlgorithm dijsktra = new DijsktraAlgorithm(graph.getVertexes(),graph.getEdges());
        dijsktra.run(this);

        Map<Vertex,Integer> distances = dijsktra.getDistance();
        for (Map.Entry<Vertex, Integer> entry : distances.entrySet()){
            Integer distance = entry.getValue();
            if(distance < Search.MAX_DIST)
                localites.add(new Pair<>(entry.getKey(),distance));
        }

        return localites;
    }
}
