package com.bioinf.graph;

import java.util.LinkedList;

public class Vertex {
	private int index; //liczba porzadkowa
	private String oligo;
	private int count;
	private LinkedList<Edge> edge = new LinkedList<>();

	public Vertex(int i, String o, int c) {
		this.index = i;
		this.oligo = o;
		this.count = c;
	}

	public String toString() {
		if(edge.size() == 0)
			return "";

		StringBuilder out = new StringBuilder();
		for(Edge e : edge)
			out.append(e.toString()).append("\n");
		return out.toString();
	}

	public String getOligo() {
		return oligo;
	}

	public int getCount() {
		return count;
	}

	public LinkedList<Edge> getEdges() {
		return new LinkedList<>(edge);
	}

	public Edge getEdge(int i) {
		return edge.get(i);
	}

	public void addEdge(Edge e) {
		edge.add(e);
	}

	public int getIndex() {
		return index;
	}

//	public Edge getEdge(String o) {
//		for(Edge e : edge)
//			if(e.getEnd()) c
//				return e;
//		return null;
//	}

}
