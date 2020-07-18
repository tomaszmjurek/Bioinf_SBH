package com.bioinf.algorithm;

import com.bioinf.Main;
import com.bioinf.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
	private Population populationZero;
	private Population nextGeneration = new Population();
	private ArrayList<Candidate> children = new ArrayList<>();
	private Map<String, Integer> oligosOriginalMap = new HashMap<>(); /* used to initialize localMap for every candidate*/
	private Graph graph;
	private ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
	private static int HUNDRED_PERCENT = 100;

	public GeneticAlgorithm(Map<String, Integer> oligos, Graph graph) {
//		this.oligosOriginalMap = oligos; //changed from this
		oligosOriginalMap.putAll(oligos);
		this.graph = graph;
	}

	public void run() {
		generatePopulationZero();
		printPopulationZero();
		selectionInPopulation(populationZero);
		System.out.println("\nAfter selection:");
		nextGeneration.printPopulation();

		crossoverInPopulation();
		System.out.println("\nAfter crossover:");
		nextGeneration.printPopulation();

		mutationInPopulation();
		System.out.println("\nAfter mutation:");
		nextGeneration.printPopulation();
	}

	public void generatePopulationZero() {
		populationZero = new Population();
		populationZero.populate(graph, oligosOriginalMap);
	}

	/**
	 * Duels between candidates in population. Population size must always stay even.
	 */
	public void selectionInPopulation(Population prevGeneration) {
//		Population nextGeneration = new Population();
		boolean isPopulationEven = false;
		if (prevGeneration.getPopulationSize() % 2 == 0) isPopulationEven = true;
		int lastPairIndex = prevGeneration.getPopulationSize() - 1;
		for (int i = 0; i < prevGeneration.getPopulationSize(); i += 2) {
			Candidate candidate1 = prevGeneration.getCandidate(i);
			if (!isPopulationEven && i == lastPairIndex)
				nextGeneration.addCandidate(candidate1);
			else {
				Candidate candidate2 = prevGeneration.getCandidate(i + 1);
				if (candidate1.getFitness() > candidate2.getFitness()) nextGeneration.addCandidate(candidate1);
				else nextGeneration.addCandidate(candidate2);
			}
		}
	}

	public void printPopulationZero() {
		System.out.println("\nPopulation Zero:");
		populationZero.printPopulation();
	}

	/**
	 * Choosing 2 parents for crossing at percent of CROSSOVER_PROBABILITY
	 */
	public void crossoverInPopulation() {
		children.clear(); //zeruje sie?
		for (int i = 0; i < nextGeneration.getPopulationSize() - 2; i += 2) {
//			if (threadLocalRandom.nextInt(HUNDRED_PERCENT) <= Main.CROSSOVER_PROBABILITY) //todo test
				crossover(nextGeneration.getCandidate(i).getDna(), nextGeneration.getCandidate(i+1).getDna());
		}
		nextGeneration.addCandidates(children);
	}

	/**
	 * Creating 2 children from 2 parents by cutting their dna parts at random point
	 * and joining them together. Then children have fitness calculated and they're added to generation.
	 * Cut point must not exceed shorter DNA size.
	 */
	private void crossover(String parent1Dna, String parent2Dna) {
		System.out.println("crossing");
		int shorterDnaLength = Math.max(parent1Dna.length(), parent2Dna.length());
		int cutPoint = threadLocalRandom.nextInt(Main.OLIGOS_SIZE, shorterDnaLength);

		Candidate child1 = new Candidate();
		child1.setDna(parent1Dna.substring(0, cutPoint) + parent2Dna.substring(cutPoint));
		Candidate child2 = new Candidate();
		child2.setDna(parent2Dna.substring(0, cutPoint) + parent1Dna.substring(cutPoint));

		child1.setOligosFromDna();
		child2.setOligosFromDna();
		child1.calculateFitness(oligosOriginalMap);
		child2.calculateFitness(oligosOriginalMap); //todo BLAD

		children.add(child1);
		children.add(child2);
	}

	/**
	 * Choosing candidate for mutation at percent of MUTATION_PROBABILITY
	 * After mutating updating it's oligos and fitness
	 */
	private void mutationInPopulation() {
		for (int i = 0; i < nextGeneration.getPopulationSize(); i++) {
//			if (threadLocalRandom.nextInt(HUNDRED_PERCENT) >= Main.MUTATION_PROBABILITY) {//todo test
				Candidate candidateToMutate = nextGeneration.getCandidate(i);
				candidateToMutate.setDna(mutation(candidateToMutate.getDna()));
				candidateToMutate.setOligosFromDna();
				candidateToMutate.calculateFitness(oligosOriginalMap); // to dziala? - nie
				nextGeneration.replaceCandidate(i, candidateToMutate);
		}
	}

	/**
	 * Chooses random point in DNA. Changes nucleotide to random. It can be the same as before.
	 */
	public String mutation(String dna) {
		System.out.println("mutating");
		StringBuilder dnaBuilder = new StringBuilder(dna);
		int mutationPoint = threadLocalRandom.nextInt(dna.length());
		char randomNucleotide = Main.NUCLEOTIDES[threadLocalRandom.nextInt(4)];
		dnaBuilder.setCharAt(mutationPoint, randomNucleotide);
		return dnaBuilder.toString();
	}
}
