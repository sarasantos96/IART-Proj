import java.io.*;
import java.util.*;

import static java.lang.System.out;

public class Search{
	
	private ArrayList<Vertex> localities;
	private ArrayList<Edge> connections;
	public static int localityID = 0;
	public static  int connectionID = 0;
	public static int MAX_DIST;
	private int number_hcc;
	private String localitiesFile;
	private String roadsFile;


	public Search(int max_dist, int number_hcc, String localitiesFile, String roadsFile){
         this.number_hcc = number_hcc;
         this.MAX_DIST = max_dist;
		 this.localities = new ArrayList<>();
		 this.connections = new ArrayList<>();
		 this.localitiesFile = localitiesFile;
		 this.roadsFile = roadsFile;
	}

	public void loadLocalities(ArrayList<Vertex> localities){
		File localities_file = new File("res/"+localitiesFile);

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
		File roads_file = new File("res/"+roadsFile);
		
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


	
	public static void main(String [] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int number_hc, max_dist;
        String localitiesFile, roadsFile;

        System.out.println("Load Localities from file: ");
        localitiesFile = br.readLine();
        System.out.println("Load Roads from file: ");
        roadsFile = br.readLine();
        System.out.println("Max distance to Health Care Center (in km): ");
        max_dist = Integer.parseInt(br.readLine());
        System.out.println("Number of Health Care Centers (zero for dynamic calculation):");
        number_hc = Integer.parseInt(br.readLine());

        Search pesquisa = new Search(max_dist,number_hc,localitiesFile,roadsFile);
        pesquisa.loadLocalities(pesquisa.localities);
        out.println("Number of localities: " + pesquisa.localities.size());
        pesquisa.loadRoads(pesquisa.connections);
        out.println("Number of roads: " + pesquisa.connections.size());
        out.println();

        ArrayList<Vertex> results = new ArrayList<>();
        AStar a = new AStar(pesquisa.localities,pesquisa.connections,pesquisa.number_hcc);

        if(pesquisa.number_hcc == 0){
            int n= a.dynamicSearch(results);
            System.out.println("Number of Health Care Centers: " + n);
        }else{
            a.search(results);
        }

        System.out.println("Result Localites to place Health Care Centers:");
        for(int i = 0; i < results.size(); i++){

            System.out.println(results.get(i).getName());
        }

        Graph graph = new Graph(pesquisa.localities, pesquisa.connections);
        graph.displayGraph(results);

    }
}
