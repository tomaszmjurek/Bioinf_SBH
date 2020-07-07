package com.bioinf.graph;

public class Edge {
	private Vertex begin, end;
	private int weight;

	public Edge(Vertex b, Vertex e, int w) {
		this.begin = b;
		this.end = e;
		this.weight = w;
	}

	public String toString() {
		return  begin.getOligo() + " ---( "
				+ weight + " )---> "
				+ end.getOligo();
	}

	public Vertex getBegin() {
		return begin;
	}

	public Vertex getEnd() {
		return end;
	}

	public int getWeight() {
		return weight;
	}
}
