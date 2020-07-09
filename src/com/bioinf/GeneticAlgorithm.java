package com.bioinf;
import com.bioinf.graph.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
	private ArrayList<String> population = new ArrayList<>();
	private ArrayList<String> nextPopulation = new ArrayList<>();

	public void getRandomPopulation(Graph graph, int size) {
		for (int i = 0; i < size; i++) {
			population.add(getRandomCandidate(graph));
		}
	}

	private String getRandomCandidate(Graph graph) {
		StringBuilder candidateBuilder = new StringBuilder();

		// Processing first vertex
		Vertex processedVertex = graph.getGraphStart();
		candidateBuilder.append(processedVertex.getOligo());

		// Choosing random edge and processing
		int randomEdgeNum;
		Edge processedEdge;

		for (; candidateBuilder.toString().length() < Main.DNA_SIZE; ) {
			if /* has edges */(processedVertex.getEdges().size() != 0) {
				randomEdgeNum = ThreadLocalRandom.current().nextInt(0, processedVertex.getEdges().size());
//				System.out.println("edge num " + randomEdgeNum);
				processedEdge = processedVertex.getEdge(randomEdgeNum);
				candidateBuilder.append(processedEdge.getEnd().getOligo(), processedEdge.getWeight(), Main.OLIGOS_SIZE);
				processedVertex = processedEdge.getEnd();
			}
		}
		return candidateBuilder.toString();
	}

	public void printPopulation() {
		System.out.println("\nPopulation:");
		population.forEach(p -> System.out.println(p));
	}
}
