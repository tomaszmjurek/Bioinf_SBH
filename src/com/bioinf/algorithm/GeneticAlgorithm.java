package com.bioinf.algorithm;
import com.bioinf.Main;
import com.bioinf.graph.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
	private ArrayList<Candidate> population = new ArrayList<>();
	private ArrayList<Candidate> nextPopulation = new ArrayList<>();
	private Map<String, Integer> oligosOriginalMap; /* used to initialize currentMap for every candidate*/
	private Map<String, Integer> oligosCurrentMap = new HashMap<>(); /* used for calculating vertexes visits for candidate fitness */
	private int candidateFitness = 0;

	public GeneticAlgorithm(Map<String, Integer> oligos) {
		this.oligosOriginalMap= oligos;
	}

	public void getRandomPopulation(Graph graph, int size) {
		for (int i = 0; i < size; i++) {
			System.out.println("candidate: ");
			population.add(getRandomCandidate(graph));
		}
	}

	private Candidate getRandomCandidate(Graph graph) {
		StringBuilder candidateBuilder = new StringBuilder();
		oligosCurrentMap.clear();
		oligosCurrentMap.putAll(oligosOriginalMap);
		candidateFitness = 0;

		// Processing first vertex
		Vertex processedVertex = graph.getGraphStart();
		candidateBuilder.append(processedVertex.getOligo());

		// Choosing random edge and processing
		int randomEdgeNum;
		Edge processedEdge;

		for /* result length similar to original */ (; candidateBuilder.toString().length() < Main.DNA_SIZE; ) {
			calculateVertexFitness(processedVertex.getOligo());
			if /* vertex has edges */(processedVertex.getEdges().size() != 0) {
				randomEdgeNum = ThreadLocalRandom.current().nextInt(0, processedVertex.getEdges().size());
				System.out.println("edge num " + randomEdgeNum);
				processedEdge = processedVertex.getEdge(randomEdgeNum);
				candidateBuilder.append(processedEdge.getEnd().getOligo(), processedEdge.getWeight(), Main.OLIGOS_SIZE);
				processedVertex = processedEdge.getEnd();
			} else break;
		}
		return new Candidate(candidateBuilder.toString(), candidateFitness);
	}

	/**
	 * Vertex's count minus 1 for every usage.
	 * Candidate fitness +1 if vertex was used less times than it's oligo occurs
	 * otherwise -1
	 */
	private void calculateVertexFitness(String oligo) {
//		System.out.println("calculating vertex fitness...");
		int activeCount = oligosCurrentMap.get(oligo);
		if (activeCount > 0) candidateFitness++;
		else candidateFitness--;
		oligosCurrentMap.replace(oligo, activeCount-1);
	}

	public void printPopulation() {
		System.out.println("\nPopulation:");
		population.forEach(p -> System.out.println(p.getDna() + " | fitness = " + p.getFitness()));
	}

	public int getBestFitness() {
		int bestFitness = 0;
		for (Candidate candidate : population)
			if (candidate.getFitness() > bestFitness) bestFitness = candidate.getFitness();
		return bestFitness;
	}
}
