import javax.xml.transform.Source;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by Daniel Garrido on 02/05/2017.
 */
public class Vertex {
    private String id;
    private String name;
    private int population;
    private int cost;
    private int servedPopulation;

    public Vertex(String id, String name, int population, int cost) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public int getCost() {
        return cost;
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

    public void setServedPopulation(int servedPopulation) {
        this.servedPopulation = servedPopulation;
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
}
