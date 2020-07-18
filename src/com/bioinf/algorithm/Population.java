package com.bioinf.algorithm;

import com.bioinf.Main;
import com.bioinf.graph.Edge;
import com.bioinf.graph.Graph;
import com.bioinf.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Population {
	private ArrayList<Candidate> candidates = new ArrayList<>();
	private Map<String, Integer> oligosCurrentMap = new HashMap<>(); /* used for calculating vertexes visits for candidate fitness */

	public void populate(Graph graph, Map<String, Integer> oligosOriginalMap) {
		for (int i = 0; i < Main.POPULATION_SIZE; i++) {
//			System.out.println("candidate: ");
			candidates.add(getRandomCandidate(graph, oligosOriginalMap));
		}
	}

	private Candidate getRandomCandidate(Graph graph, Map<String, Integer> oligosOriginalMap) {
		StringBuilder candidateBuilder = new StringBuilder();
		Candidate candidate = new Candidate();

		// Processing first vertex
		Vertex processedVertex = graph.getGraphStart();
		candidateBuilder.append(processedVertex.getOligo());

		// Choosing random edge and processing
		int randomEdgeNum;
		Edge processedEdge;

		for /* result length similar to original */ (; candidateBuilder.toString().length() < Main.DNA_SIZE; ) {
			candidate.addOligo(processedVertex.getOligo());
			if /* vertex has edges */(processedVertex.getEdges().size() != 0) {
				randomEdgeNum = ThreadLocalRandom.current().nextInt(0, processedVertex.getEdges().size());
//				System.out.println("edge num " + randomEdgeNum);
				processedEdge = processedVertex.getEdge(randomEdgeNum);
				candidateBuilder.append(processedEdge.getEnd().getOligo(), processedEdge.getWeight(), Main.OLIGOS_SIZE);
				processedVertex = processedEdge.getEnd();
			} else break;
		}
		candidate.setDna(candidateBuilder.toString());
		oligosCurrentMap.clear();
		oligosCurrentMap.putAll(oligosOriginalMap);
		candidate.calculateFitness(oligosCurrentMap);
		return candidate;
	}

	public int getBestFitness() {
		int bestFitness = 0;
		for (Candidate candidate : candidates)
			if (candidate.getFitness() > bestFitness) bestFitness = candidate.getFitness();
		return bestFitness;
	}

	public void printPopulation() {
		System.out.println("\nPopulation:");
		candidates.forEach(p -> System.out.println(p.getDna() + " | fitness = " + p.getFitness()));
		System.out.println("\nBest fitness = " + getBestFitness());
	}

	public ArrayList<Candidate> getCandidates() {
		return candidates;
	}

	public void addCandidate(Candidate c) {
		candidates.add(c);
	}

	public void addCandidates(ArrayList<Candidate> candidates) {
		this.candidates.addAll(candidates);
	}

	public Candidate getCandidate(int index) {
		return candidates.get(index);
	}

	public int getPopulationSize() {
		return candidates.size();
	}

	public void replaceCandidate(int index, Candidate newCandidate) {
		candidates.set(index, newCandidate);
	}
}
