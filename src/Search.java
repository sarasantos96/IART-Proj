import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.System.out;

public class Search{
	
	private ArrayList<Vertex> localities;
	private ArrayList<Edge> connections;
	public static int localityID = 0;
	public static  int connectionID = 0;
	
	public Search(){
		 this.localities = new ArrayList<>();
		 this.connections = new ArrayList<>();
	}
	
	public void loadLocalities(ArrayList<Vertex> localities){
		File localities_file = new File("res/localidades.txt");
		
		if(!localities_file.exists()){
			out.println("Error opening file");
			System.exit(-1);
		}
		
		try {
			FileReader fileReader = new FileReader(localities_file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String [] split = line.split(":");
				Vertex locality = new Vertex(""+localityID,split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]));
				localities.add(locality);
				localityID++;
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void loadRoads(ArrayList<Edge> roads){
		File roads_file = new File("res/roads.txt");
		
		if(!roads_file.exists()){
			out.println("Error opening file");
			System.exit(-1);
		}
		try {
			FileReader fileReader = new FileReader(roads_file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String [] split = line.split(":");
				Vertex localidade1 = getLocalidade(split[0]);
				Vertex localidade2 = getLocalidade(split[1]);
				int distancia = Integer.parseInt(split[2]);
				if(localidade1 == null || localidade2 == null){
					out.println("Road " + split[0] + " - "+split[1]+": localidades nao existem");
					System.exit(-1);
				}
				Edge road = new Edge(""+connectionID,localidade1, localidade2, distancia);
				roads.add(road);
				connectionID++;
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public Vertex getLocalidade(String name){
		Vertex localidade = null;
		
		for(int i=0 ; i < localities.size(); i++){
			if(localities.get(i).getName().equals(name))
				localidade = localities.get(i);
		}
		
		return localidade;
	}
	
	public static void main(String [] args) {
        Search pesquisa = new Search();
        pesquisa.loadLocalities(pesquisa.localities);
        out.println("Numero de localidades: " + pesquisa.localities.size());
        pesquisa.loadRoads(pesquisa.connections);
        out.println("Numero de roads: " + pesquisa.connections.size());
        out.println();

        Graph graph = new Graph(pesquisa.localities, pesquisa.connections);
        graph.displayGraph();

        DijsktraAlgorithm dijsktra = new DijsktraAlgorithm(graph.getVertexes(),graph.getEdges());
        dijsktra.run(graph.getVertexes().get(0));
        LinkedList<Vertex> shortestPath = dijsktra.getPath(graph.getVertexes().get(3));
        for(Vertex v : shortestPath){
            out.println(v.getName());
        }

        /*Collections.sort(pesquisa.localidades, new Comparator<Locality>() {
            @Override
            public int compare(Locality l1, Locality l2) {
                return (int) (l1.getAvaluation() - l2.getAvaluation()); // Ascending
            }
        });

        for(Locality localidade : pesquisa.localidades){
            System.out.println(localidade.getName()+ ":"+localidade.getAvaluation());
        }*/
    }
}
