import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class Graph {
	private DirectedSparseGraph graph;
	private List<Vertex> vertexs;
	private List<Edge> edges;
	
	public Graph(ArrayList<Locality> localidades, ArrayList<Road> roads){
		graph = new DirectedSparseGraph();
		vertexs = new ArrayList<>();
		edges = new ArrayList<>();

		for(int i = 0 ; i < localidades.size(); i++){
			graph.addVertex(localidades.get(i).getName());
			Vertex v = new Vertex("" + i, localidades.get(i).getName(), localidades.get(i));
            vertexs.add(v);
		}
		for(int i = 0 ; i < roads.size(); i++){
			graph.addEdge(""+i,roads.get(i).getlocality1().getName(),roads.get(i).getlocality2().getName());
			graph.addEdge(""+i,roads.get(i).getlocality1().getName(),roads.get(i).getlocality2().getName());
            Edge v = new Edge("" + i, roads.get(i).getSource(), roads.get(i).getDestination(), roads.get(i).getDistancia(), roads.get(i));
            vertexs.add(v);
		}
	}

	public void displayGraph(){
		VisualizationImageServer vs =  new VisualizationImageServer(new CircleLayout(graph), new Dimension(400, 400));
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(vs);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}
