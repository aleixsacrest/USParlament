package es.upc.fib.prop.usParlament.domain;

import es.upc.fib.prop.shared13.Node;

/**
 * version of node for testing purposes
 * Created by miquel on 20/03/15.
 */
public final class TNode extends Node
{
	private Integer id;

	public TNode(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
