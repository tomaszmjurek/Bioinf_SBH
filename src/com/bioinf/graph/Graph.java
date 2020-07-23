package com.bioinf.graph;

import com.bioinf.DNASpectrum;
import com.bioinf.Main;

import java.util.LinkedList;
import java.util.Map;

public class Graph {
	private LinkedList<Vertex> vertexes = new LinkedList<>(); // index musi sie pokrywac z Vertex.index
	private int vertexCount;
	private int startIndex;

	public Graph() {
		this.vertexCount = 0;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public Vertex getGraphStart() {
		return vertexes.get(startIndex);
	}

	public void printGraph() {
		StringBuilder out = new StringBuilder();
		for(Vertex v : vertexes)
			if(v != null && !"".equals(v.toString()))
				out.append(v.toString()).append("\n");
		System.out.println(out.toString());
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
		return vertexes.get(i);
	}

	public LinkedList<Vertex> getVertexes() {
		return new LinkedList<>(vertexes);
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
				for (int x = 1; x < Main.OLIGO_SIZE; x++) {
					if (hasSameSubstring(x, i, j)) addEdge(i, j, Main.OLIGO_SIZE -x);
				}
			}
		}
	}

	private boolean isFirstOligoIndex(int i) {
		return vertexes.get(i).getOligo().equals(DNASpectrum.firstOligo);
	}

	/**
	 * Compares two substrings the same size.
	 * One is suffix of the begin vertex oligo
	 * Another is prefix of the end vertex oligo
	 */
	private boolean hasSameSubstring(int begin, int beginVertexIndex, int endVertexIndex) {
		return vertexes.get(beginVertexIndex).getOligo().substring(begin, Main.OLIGO_SIZE).equals(
				vertexes.get(endVertexIndex).getOligo().substring(0, Main.OLIGO_SIZE - begin));
	}

}
