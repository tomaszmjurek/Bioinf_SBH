package com.bioinf.algorithm;

import com.bioinf.Main;
import com.bioinf.graph.Graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
	private ArrayList<Population> populations = new ArrayList<>();
	private Population populationZero;
	private Map<String, Integer> oligosOriginalMap; /* used to initialize localMap for every candidate*/
	private Graph graph;

	public GeneticAlgorithm(Map<String, Integer> oligos, Graph graph) {
		this.oligosOriginalMap= oligos;
		this.graph = graph;
	}

	public void run() {
//		generatePopulationZero();
//		printPopulationZero();
//		Population nextGeneration = selectionInPopulation(populationZero);
//		nextGeneration.printPopulation();
//		crossoverInPopulation();
		mutationInPopulation();
	}

	public void generatePopulationZero() {
		populationZero = new Population();
		populationZero.populate(graph, oligosOriginalMap);
	}

	/**
	 * Duels between candidates in population
	 */
	public Population selectionInPopulation(Population population) {
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

	//todo: tylko najlepsze rozwiazania, bez usuwania istniejacych
	public void crossoverInPopulation() {
		double crossoverRate = 0.1;
		Candidate c1 = new Candidate();
		Candidate c2 = new Candidate();
		c1.setDna("CGCGCGCGCGCG");
		c2.setDna("ATATATATATAT");
		crossover(c1, c2);
	}

	private void crossover(Candidate parent1, Candidate parent2) {
		int cutPoint = ThreadLocalRandom.current().nextInt(Main.OLIGOS_SIZE, Main.DNA_SIZE);
		String parent1Dna = parent1.getDna();
		String parent2Dna = parent2.getDna();

		Candidate child1 = new Candidate();
		child1.setDna(parent1Dna.substring(0, cutPoint) + parent2Dna.substring(cutPoint));
		Candidate child2 = new Candidate();
		child2.setDna(parent2Dna.substring(0, cutPoint) + parent1Dna.substring(cutPoint));
		System.out.println("Child 1 dna: " + child1.getDna());
		System.out.println("Child 2 dna: " + child2.getDna());
		child1.setOligosFromDna();
		child2.setOligosFromDna();


		child1.calculateFitness(oligosOriginalMap);
		child2.calculateFitness(oligosOriginalMap);
		System.out.println("child 1 fitness " + child1.getFitness());
		System.out.println("child 2 fitness " + child2.getFitness());
	}

	//todo: mutacja dla 2-4% z pominieciem najlepszych rozwiazan
	private void mutationInPopulation() {

		Candidate candidate = new Candidate();
		candidate.setDna(mutation(candidate.getDna()));
	}

	/**
	 * Changes one random nucleotide at random position to random value
	 */
	public String mutation(String dna) {
		//dla 1-4%
		StringBuilder dnaBuilder = new StringBuilder(dna);
		int mutationPoint = ThreadLocalRandom.current().nextInt(dna.length());
		char randomNucleotide = Main.NUCLEOTIDES[ThreadLocalRandom.current().nextInt(4)];
		dnaBuilder.setCharAt(mutationPoint, randomNucleotide);
		return dnaBuilder.toString();
	}
}
