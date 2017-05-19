import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import org.apache.commons.collections15.Transformer;

public class Graph {
	private SparseGraph<String, String> JUNGgraph;
	private List<Vertex> vertexes;
	private List<Edge> edges;
	private List<Vertex> results;

	public Graph(List<Vertex> localities, List<Edge> connections){
		JUNGgraph = new SparseGraph<>() ;
		vertexes = localities;
		edges = connections;

		for(int i = 0 ; i < vertexes.size(); i++){
			JUNGgraph.addVertex(vertexes.get(i).getName());
		}
		for(int i = 0 ; i < edges.size(); i++){
			JUNGgraph.addEdge("road"+i+"_"+edges.get(i).getWeight()+"km",edges.get(i).getSource().getName(),edges.get(i).getDestination().getName(), EdgeType.UNDIRECTED);
		}
	}

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setVertexesServedPopulation(){

		for(int i = 0; i < vertexes.size();i++){
		    vertexes.get(i).calculateServedPopulation(this);
        }
	}

    public void displayGraph(List<Vertex> results){
		//VisualizationImageServer vs =  new VisualizationImageServer(new CircleLayout(JUNGgraph), new Dimension(400, 400));
        VisualizationViewer vs = new VisualizationViewer(new ISOMLayout(JUNGgraph), new Dimension(800, 800));
        vs.getRenderContext().setVertexLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        });
        vs.getRenderContext().setEdgeLabelTransformer(new Transformer<String, String>() {
            @Override
            public String transform(String arg0) {
                return arg0;
            }
        });

        vs.getRenderer().setVertexRenderer(new MyRenderer(results));
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(vs);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}

    class MyRenderer implements Renderer.Vertex<String, String> {
        private List<Vertex> results;
        MyRenderer(List<Vertex> results){
            this.results = results;
        }
        @Override
        public void paintVertex(RenderContext<String, String> rc, Layout<String, String> layout, String vertex) {
            GraphicsDecorator graphicsContext = rc.getGraphicsContext();
            Point2D center = layout.transform(vertex);
            Shape shape = null;
            Color color = null;
            if(containsVertex(vertex)){
                shape = new Rectangle((int) center.getX() - 17,(int) center.getY() - 17, 34, 34);
                color = new Color(255, 0, 0);
            } else {
                shape = new Ellipse2D.Double(center.getX() - 17, center.getY() - 17, 34, 34);
                color = new Color(0, 0, 255);
            }

            graphicsContext.setPaint(color);
            graphicsContext.fill(shape);
        }

        public boolean containsVertex(String vertex){
            for(Vertex v :results){
                if(v.getName().equals(vertex))
                    return true;
            }
            return  false;
        }
    }

	public void sortVertexesByServedPopulation(){
        Collections.sort(vertexes, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex l1, Vertex l2) {
                return (int) (l2.getServedPopulation() - l1.getServedPopulation());
            }
        });
    }
}
