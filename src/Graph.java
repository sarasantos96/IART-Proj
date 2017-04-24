import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class Graph {
	private DirectedSparseGraph graph;
	
	public Graph(ArrayList<Localidade> localidades, ArrayList<Road> roads){
		graph = new DirectedSparseGraph();
		
		for(int i = 0 ; i < localidades.size(); i++){
			graph.addVertex(localidades.get(i).getName());
		}
		for(int i = 0 ; i < roads.size(); i++){
			graph.addEdge(""+i,roads.get(i).getLocalidade1().getName(),roads.get(i).getLocalidade2().getName());
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
