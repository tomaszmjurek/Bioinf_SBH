package com.bioinf.algorithm;

import com.bioinf.Main;
import com.bioinf.graph.Graph;

import java.util.ArrayList;
import java.util.Map;

public class GeneticAlgorithm {
	private ArrayList<Candidate> population = new ArrayList<>();
	private Population populationZero;
	private ArrayList<Candidate> nextPopulation = new ArrayList<>();
	private Map<String, Integer> oligosOriginalMap; /* used to initialize localMap for every candidate*/
	private Graph graph;

	public GeneticAlgorithm(Map<String, Integer> oligos, Graph graph) {
		this.oligosOriginalMap= oligos;
		this.graph = graph;
	}

	public void run() {
		generatePopulationZero();
		printPopulationZero();
		Population nextGeneration = candidateSelection(populationZero);
		nextGeneration.printPopulation();
	}

	public void generatePopulationZero() {
		populationZero = new Population();
		populationZero.populate(graph, oligosOriginalMap);
	}

	/**
	 * Duels between candidates in population
	 */
	public Population candidateSelection(Population population) {
		Population nextGeneration = new Population();
		for (int i = 0; i < Main.POPULATION_SIZE - 1; i += 2) {
			Candidate candidate1 = population.getCandidate(i);
			Candidate candidate2 = population.getCandidate(i+1);
			if (candidate1.getFitness() > candidate2.getFitness()) nextGeneration.addCandidate(candidate1);
			else nextGeneration.addCandidate(candidate2);
		}
		return nextGeneration;
	}

	public void printPopulationZero() {
		System.out.println("\nPopulation Zero:");
		populationZero.printPopulation();
		System.out.println("\nBest fitness = " + populationZero.getBestFitness());
	}

}
