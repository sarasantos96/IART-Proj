import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static java.lang.System.out;

public class Search{
	
	private ArrayList<Locality> localidades;
	private ArrayList<Road> roads;
	
	public Search(){
		 this.localidades = new ArrayList<Locality>();
		 this.roads = new ArrayList<Road>();
	}
	
	public void loadLocalidades(ArrayList<Locality> localidades){
		File localidades_file = new File("res/localidades.txt");
		
		if(!localidades_file.exists()){
			out.println("Error opening file");
			System.exit(-1);
		}
		
		try {
			FileReader fileReader = new FileReader(localidades_file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String [] split = line.split(":");
				Locality localidade = new Locality(split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]));
				localidades.add(localidade);
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void loadRoads(ArrayList<Road> roads){
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
				Locality localidade1 = getLocalidade(split[0]);
				Locality localidade2 = getLocalidade(split[1]);
				int distancia = Integer.parseInt(split[2]);
				if(localidade1 == null || localidade2 == null){
					out.println("Road " + split[0] + " - "+split[1]+": localidades nao existem");
					System.exit(-1);
				}
				Road road = new Road(localidade1, localidade2, distancia);
				roads.add(road);			
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public Locality getLocalidade(String name){
		Locality localidade = null;
		
		for(int i=0 ; i < localidades.size(); i++){
			if(localidades.get(i).getName().equals(name))
				localidade = localidades.get(i);
		}
		
		return localidade;
	}
	
	public static void main(String [] args) {
        Search pesquisa = new Search();
        pesquisa.loadLocalidades(pesquisa.localidades);
        out.println("Numero de localidades: " + pesquisa.localidades.size());
        pesquisa.loadRoads(pesquisa.roads);
        out.println("Numero de roads: " + pesquisa.roads.size());
        out.println();
        for(Locality localidade : pesquisa.localidades){
            System.out.println(localidade.getName()+ ":"+localidade.getAvaluation());
        }

        Graph graph = new Graph(pesquisa.localidades, pesquisa.roads);
        graph.displayGraph();

        Collections.sort(pesquisa.localidades, new Comparator<Locality>() {
            @Override
            public int compare(Locality l1, Locality l2) {
                return (int) (l1.getAvaluation() - l2.getAvaluation()); // Ascending
            }
        });

        for(Locality localidade : pesquisa.localidades){
            System.out.println(localidade.getName()+ ":"+localidade.getAvaluation());
        }
    }
}
