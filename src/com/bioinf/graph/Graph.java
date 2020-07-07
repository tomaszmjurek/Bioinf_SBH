package com.bioinf.graph;

import com.bioinf.DNASpectrum;
import com.bioinf.Main;

import java.util.LinkedList;
import java.util.Map;

public class Graph {
	private LinkedList<Vertex> vertexes = new LinkedList<>(); // index musi sie pokrywac z Vertex.index
	private int vertexCount;

	public int getStartIndex() {
		return startIndex;
	}

	public Vertex getGraphStart() {
		return vertexes.get(startIndex);
	}

	private int startIndex;

	public Graph() {
		this.vertexCount = 0;
	}

	public void printGraph() {
		String out = new String();
		for(Vertex v : vertexes)
			if(v != null && v.toString() != "")
				out = out + v.toString() + "\n";
		System.out.println(out);
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

	public void buildGraph(Map<String, Integer> oligos) {
		// Build vertexes
		oligos.forEach((key, value) -> addVertex(key, value));
		buildEdges();
	}

	/**
	 * Compares all vertexes.
	 * If there is a matching pattern (substring) between two
	 * then creates edge with weight of number of matching chars
	 */
	private void buildEdges() {
		for (int i = 0; i < vertexCount; i++) {
			if (isFirstOligoIndex(i)) startIndex = i;
			for(int j = 0; j < vertexCount; j++) {
				if (i == j) continue;
				for (int x = 1; x < Main.OLIGOS_SIZE; x++) {
					if (hasSameSubstring(x, i, j)) addEdge(i, j, Main.OLIGOS_SIZE-x);
				}
			}
		}
	}

	private boolean isFirstOligoIndex(int i) {
		if (vertexes.get(i).getOligo().compareTo(DNASpectrum.firstOligo) == 0) return true;
		return false;
	}

	/**
	 * Compares two substrings the same size.
	 * One is suffix of the begin vertex oligo
	 * Another is prefix of the end vertex oligo
	 */
	private boolean hasSameSubstring(int begin, int beginVertexIndex, int endVertexIndex) {
		if (vertexes.get(beginVertexIndex).getOligo().substring(begin, Main.OLIGOS_SIZE).compareTo(
				vertexes.get(endVertexIndex).getOligo().substring(0, Main.OLIGOS_SIZE - begin)) == 0)
			return true;
		return false;
	}
}
