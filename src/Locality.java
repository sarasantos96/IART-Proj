
public class Locality{
	private String name;
	private int populacao;
	private int custoContrucao;
	private double avaluation;
	
	public Locality(String name, int populacao, int custoContrucao) {
		this.name = name;
		this.populacao = populacao;
		this.custoContrucao = custoContrucao;
		this.avaluation = this.populacao / this.custoContrucao;
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

	public double getAvaluation() {
		return avaluation;
	}

	public void setAvaluation(double avaluation) {
		this.avaluation = avaluation;
	}
}
