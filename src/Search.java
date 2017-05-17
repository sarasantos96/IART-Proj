import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static java.lang.System.out;

public class Search{
	
	private ArrayList<Vertex> localities;
	private ArrayList<Edge> connections;
	public static int localityID = 0;
	public static  int connectionID = 0;
	public static final int MAX_DIST = 15;

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
				connectionID++;
				Edge road2 = new Edge(""+connectionID,localidade2, localidade1, distancia);
				roads.add(road);
				roads.add(road2);
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
        graph.setVertexesServedPopulation();
        graph.sortVertexesByServedPopulation();
        List<Vertex> v = graph.getVertexes();

        /*for(int i = 0; i < v.size(); i++){
            System.out.println(v.get(i).getName() + " serves " + v.get(i).getServedPopulation());
        }*/

        AStar a = new AStar(v,2);
        ArrayList<Vertex> results = new ArrayList<>();
        a.search(results);

        System.out.println("results:");
        for(int i = 0; i < results.size(); i++){

            System.out.println(results.get(i).getName());
        }

    }
}
