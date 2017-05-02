import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class Graph {
	private DirectedSparseGraph JUNGgraph;
	private List<Vertex> vertexes;
	private List<Edge> edges;
	
	public Graph(List<Vertex> localities, List<Edge> connections){
		JUNGgraph = new DirectedSparseGraph();
		vertexes = localities;
		edges = connections;

		for(int i = 0 ; i < vertexes.size(); i++){
			JUNGgraph.addVertex(vertexes.get(i).getName());
		}
		for(int i = 0 ; i < edges.size(); i++){
			JUNGgraph.addEdge(""+i,edges.get(i).getSource().getName(),edges.get(i).getDestination().getName());
			JUNGgraph.addEdge(""+i,edges.get(i).getSource().getName(),edges.get(i).getDestination().getName());
		}
	}

	public void displayGraph(){
		VisualizationImageServer vs =  new VisualizationImageServer(new CircleLayout(JUNGgraph), new Dimension(400, 400));
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(vs);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
}
