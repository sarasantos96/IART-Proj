
public class Road {
	private Locality source;
	private Locality destination;
	private int distancia;
	
	public Road(Locality source, Locality destination, int distancia) {
		this.source = source;
		this.destination = destination;
		this.distancia = distancia;
	}

	public Locality getSource() {
		return source;
	}

	public void setSource(Locality source) {
		this.source = source;
	}

	public Locality getDestination() {
		return destination;
	}

	public void setDestination(Locality destination) {
		this.destination = destination;
	}

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}
}
