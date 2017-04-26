
public class Localidade {
	private String name;
	private int populacao;
	private int custoContrucao;
	
	public Localidade(String name, int populacao, int custoContrucao) {
		this.name = name;
		this.populacao = populacao;
		this.custoContrucao = custoContrucao;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public int getPopulacao() {
		return populacao;
	}

	public void setPopulacao(int populacao) {
		this.populacao = populacao;
	}

	public int getCustoContrucao() {
		return custoContrucao;
	}

	public void setCustoContrucao(int custoContrucao) {
		this.custoContrucao = custoContrucao;
	}
	
}
