package com.bioinf.algorithm;

import com.bioinf.graph.Graph;

import java.util.ArrayList;
import java.util.Map;

public class GeneticAlgorithm {
	private ArrayList<Candidate> population = new ArrayList<>();
	private Population populationZero;
	private ArrayList<Candidate> nextPopulation = new ArrayList<>();
	private Map<String, Integer> oligosOriginalMap; /* used to initialize localMap for every candidate*/

	public GeneticAlgorithm(Map<String, Integer> oligos) {
		this.oligosOriginalMap= oligos;
	}

	public void generatePopulationZero(Graph graph, int size) {
		populationZero = new Population(graph, size, oligosOriginalMap);
	}

	public void printPopulationZero() {
		System.out.println("\nPopulation:");
		populationZero.printPopulation();
		System.out.println("\nBest fitness = " + populationZero.getBestFitness());
	}

}
