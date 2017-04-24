
public class Road {
	private Localidade localidade1;
	private Localidade localidade2;
	private int distancia;
	
	public Road(Localidade localidade1, Localidade localidade2, int distancia) {
		this.localidade1 = localidade1;
		this.localidade2 = localidade2;
		this.distancia = distancia;
	}

	public Localidade getLocalidade1() {
		return localidade1;
	}

	public void setLocalidade1(Localidade localidade1) {
		this.localidade1 = localidade1;
	}

	public Localidade getLocalidade2() {
		return localidade2;
	}

	public void setLocalidade2(Localidade localidade2) {
		this.localidade2 = localidade2;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
}
