package es.upc.fib.prop.usParlament.beans;

import es.upc.fib.prop.usParlament.shared.Edge;
import es.upc.fib.prop.usParlament.shared.Node;


public abstract class EdgeImpl implements Edge {
	@Override
	public void setOrigin(Node origin) {

	}

	@Override
	public void setDestiny(Node destiny) {

	}

	@Override
	public Node getOrigin() {
		return null;
	}

	@Override
	public Node getDestiny() {
		return null;
	}

	@Override
	public double getWeight() {
		return 0;
	}
}
