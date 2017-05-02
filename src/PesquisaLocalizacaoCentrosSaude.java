import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PesquisaLocalizacaoCentrosSaude{
	
	private ArrayList<Localidade> localidades;
	private ArrayList<Road> roads;
	
	public PesquisaLocalizacaoCentrosSaude(){
		 this.localidades = new ArrayList<Localidade>();
		 this.roads = new ArrayList<Road>();
	}
	
	public void loadLocalidades(ArrayList<Localidade> localidades){
		File localidades_file = new File("res/localidades.txt");
		
		if(!localidades_file.exists()){
			System.out.println("Error opening file");
			System.exit(-1);
		}
		
		try {
			FileReader fileReader = new FileReader(localidades_file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String [] split = line.split(":");
				Localidade localidade = new Localidade(split[0],Integer.parseInt(split[1]),Integer.parseInt(split[2]));
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
			System.out.println("Error opening file");
			System.exit(-1);
		}
		
		try {
			FileReader fileReader = new FileReader(roads_file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String [] split = line.split(":");
				Localidade localidade1 = getLocalidade(split[0]);
				Localidade localidade2 = getLocalidade(split[1]);
				int distancia = Integer.parseInt(split[2]);
				if(localidade1 == null || localidade2 == null){
					System.out.println("Road " + split[0] + " - "+split[1]+": localidades nao existem");
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
	
	public Localidade getLocalidade(String name){
		Localidade localidade = null;
		
		for(int i=0 ; i < localidades.size(); i++){
			if(localidades.get(i).getName().equals(name))
				localidade = localidades.get(i);
		}
		
		return localidade;
	}
	
	public static void main(String [] args){
		PesquisaLocalizacaoCentrosSaude pesquisa = new PesquisaLocalizacaoCentrosSaude();
		pesquisa.loadLocalidades(pesquisa.localidades);
		System.out.println("Numero de localidades: "+ pesquisa.localidades.size());
		pesquisa.loadRoads(pesquisa.roads);
		System.out.println("Numero de roads: "+ pesquisa.roads.size());
		
		Graph graph = new Graph(pesquisa.localidades,pesquisa.roads);
		graph.displayGraph();
	}
}
