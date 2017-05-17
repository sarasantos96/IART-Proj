import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AStar {
    private List<Vertex> vertexs;
    private int numberCC;

    public AStar(List<Vertex> vertexs, int numberCC) {
        this.vertexs = vertexs;
        this.numberCC = numberCC;
    }

    public List<Vertex> getVertexs() {
        return vertexs;
    }

    public void setVertexs(List<Vertex> vertexs) {
        this.vertexs = vertexs;
    }

    public int getNumberCC() {
        return numberCC;
    }

    public void setNumberCC(int numberCC) {
        this.numberCC = numberCC;
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

                if(f(v,g,tempCC) < f(openList.get(i),g,tempCC)){
                    v = openList.get(i);
                }
            }
            openList.remove(v);
            results.add(v);
            g += v.getRatio();
            tempCC--;

        }
    }

    public double f(Vertex v, double g, int numberCC){
        double i = h(numberCC) + g + v.getRatio();
        return i;
    }

    public int h(int numberCC){
        int h = 0;
        List<Vertex> openList = new ArrayList<>(this.vertexs);

        for(int i = 0; i < numberCC; i++){
            if(!openList.isEmpty()){
                Vertex v = findHighestRatio(openList);
                h += v.getRatio();
                openList.remove(v);
            }else
                break;
        }

        return h;
    }

    private Vertex findHighestRatio(List<Vertex> openList) {
        Vertex best = openList.get(0);

        for(int i = 0; i < openList.size(); i++){
            if(best.getRatio() < openList.get(i).getRatio()){
                best = openList.get(i);
            }
        }
        return best;
    }
}
