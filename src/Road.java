
public class Road {
	private Locality locality1;
	private Locality locality2;
	private int distancia;
	
	public Road(Locality locality1, Locality locality2, int distancia) {
		this.locality1 = locality1;
		this.locality2 = locality2;
		this.distancia = distancia;
	}

	public Locality getlocality1() {
		return locality1;
	}

	public void setlocality1(Locality locality1) {
		this.locality1 = locality1;
	}

	public Locality getlocality2() {
		return locality2;
	}

	public void setlocality2(Locality locality2) {
		this.locality2 = locality2;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
}
