package com.bioinf.graph;

import java.util.LinkedList;

public class Graph {
	private LinkedList<Vertex> vertexes = new LinkedList<>(); //index musi sie pokrywac z Vertex.index
	private int vertexCount;

	public Graph(int vertexCount) {
		this.vertexCount = 0;
	}

	public String toString() {
		String out = new String();
		for(Vertex v : vertexes)
			if(v != null && v.toString() != "")
				out = out + v.toString() + "\n";
		return out;
	}

	// zwraca l.p.
	public int addVertex(String oligo, int count) {
		vertexes.add(new Vertex(vertexCount, oligo, count));
		vertexCount++;
		return vertexCount-1;
	}

	public void addEdge(int bIndex, int eIndex, int w) {
		vertexes.get(bIndex).addEdge(new Edge(vertexes.get(bIndex), vertexes.get(eIndex), w));
	}

	public Vertex getVertex(int i) {
		return vertexes.get(i); //mozliwe ze bedzie trzeba iterowac szukajac v.index
	}

	public LinkedList<Vertex> getVertexes() {
		return new LinkedList<Vertex>(vertexes);
	}






}
