import javafx.util.Pair;

import java.util.*;

public class AStar {
    private List<Vertex> vertexs;
    private List<Edge> roads;
    private int numberCC;
    private HashMap<Vertex, Integer> services;
    private static final int MAX_INTEGER = 2147483647;

    public AStar(List<Vertex> vertexs, List<Edge> roads, int numberCC) {
        this.vertexs = new ArrayList<>(vertexs);
        this.roads = roads;
        this.numberCC = numberCC;
        this.services = new HashMap<>();

        for(int i = 0; i < this.vertexs.size() ; i ++){
            this.services.put(this.vertexs.get(i),MAX_INTEGER);
        }
    }

    public void updateServices(Pair<Vertex,Integer> pair){
        int value = services.get(pair.getKey());
        services.put(pair.getKey(), pair.getValue() < value ? pair.getValue() : value);
    }

    /*A*
            initialize the open list
            initialize the closed list
            put the starting node on the open list (you can leave its f zero)

            while the open list is not empty
                find the node with the least f on the open list, call it "q"
                pop q off the open list
                generate q's 8 successors and set their parents to q
                for each successor
                    if successor is the goal, stop the search
                    successor.g = q.g + distance between successor and q
                    successor.h = distance from goal to successor
                    successor.f = successor.g + successor.h

                    if a node with the same position as successor is in the OPEN list \
                        which has a lower f than successor, skip this successor
                    if a node with the same position as successor is in the CLOSED list \
                        which has a lower f than successor, skip this successor
                    otherwise, add the node to the open list
                end
                push q on the closed list
            end
             */
    public void search(List<Vertex> results){
        int tempCC = this.numberCC;
        List<Vertex> openList = this.vertexs;

        double g = 0;

        while(tempCC > 0 && !openList.isEmpty()) {
            Vertex v = openList.get(0);
            //find the node with the highest f on the open list
            for(int i = 1; i < openList.size(); i++){

                if(f(v,g,tempCC) > f(openList.get(i),g,tempCC)){
                    v = openList.get(i);
                }
            }
            ArrayList<Pair<Vertex,Integer>> coveredLoc = v.getServedLocalities(new Graph(this.vertexs,this.roads));
            for(Pair<Vertex,Integer> pair : coveredLoc){
                updateServices(pair);
            }
            openList.remove(v);
            results.add(v);
            v.setHasHealthCareCenter(true);
            g += v.getRatio();
            tempCC--;
        }
    }

    public int dynamicSearch(List<Vertex> results){
        int numberCC = 0;
        List<Vertex> openList = this.vertexs;

        double g = 0;

        while(!openList.isEmpty()) {
            Vertex v = openList.get(0);
            for(int i = 1; i < openList.size(); i++){

                if(f(v,g,0) > f(openList.get(i),g,0)){
                    v = openList.get(i);
                }
            }
            ArrayList<Pair<Vertex,Integer>> coveredLoc = v.getServedLocalities(new Graph(this.vertexs,this.roads));
            for(Pair<Vertex,Integer> pair : coveredLoc){
                updateServices(pair);
            }
            ArrayList<Pair<Vertex,Integer>>proxLoc = v.getProxLocalities(new Graph(this.vertexs,this.roads));
            for(Pair<Vertex,Integer> pair : proxLoc){
                openList.remove(pair.getKey());
            }
            //openList.remove(v);
            results.add(v);
            v.setHasHealthCareCenter(true);
            g += v.getRatio() + ((double) 10000/ v.getServedPopulation());
            numberCC++;

        }
        return numberCC;
    }

    public double f(Vertex v, double g, int numberCC){
        double i = h(numberCC) + g + (v.getRatio() + (Search.MAX_DIST / this.services.get(v)));
        return i;
    }

    public int h(int numberCC){
        int h = 0;
        List<Vertex> openList = new ArrayList<>(this.vertexs);

        for(int i = 0; i < numberCC; i++){
            if(!openList.isEmpty()){
                Vertex v = findHighestRatio(openList);
                h += v.getRatio() + (Search.MAX_DIST / this.services.get(v));
                openList.remove(v);
            }else
                break;
        }

        return h;
    }

    private Vertex findHighestRatio(List<Vertex> openList) {
        Vertex best = openList.get(0);

        for(int i = 0; i < openList.size(); i++){
            if(best.getRatio() > openList.get(i).getRatio()){
                best = openList.get(i);
            }
        }
        return best;
    }
}
