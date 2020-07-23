package com.bioinf.algorithm;

import com.bioinf.Main;
import com.bioinf.graph.Graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {
	private Population populationZero;
	private Population nextGeneration;
	private Population prevGeneration;
	private ArrayList<Candidate> children = new ArrayList<>();
	private Map<String, Integer> oligosOriginalMap = new HashMap<>(); /* used to initialize localMap for every candidate*/
	private Graph graph;
	private ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
	private static int HUNDRED_PERCENT = 100;

	public GeneticAlgorithm(Map<String, Integer> oligos, Graph graph) {
		oligosOriginalMap.putAll(oligos);
		this.graph = graph;
	}

	public String run() {
		generatePopulationZero();
		printPopulationZero();

		prevGeneration = populationZero;

		int newBestFitness, stagnationCount = 0;
		int oldBestFitness = prevGeneration.getBestCandidate().getFitness();
		for (int i = 1; i < Main.GENERATIONS_NUMBER; i++) {
			if (prevGeneration.getPopulationSize() < 2 || isStagnation(stagnationCount)) {
				System.out.println("Found the best candidate OR stagnation reached! Stopping the loop at iteration " + i);
				break;
			}
			System.out.println("\nGENERATION " + i);
			nextGeneration = new Population();

			crossoverInPopulation();
			mutationInPopulation();
			selectionInPopulation(prevGeneration);

			// New population produced
			nextGeneration.printPopulation();
			prevGeneration = nextGeneration;

			// Calculating solutions stagnation
			newBestFitness = prevGeneration.getBestCandidate().getFitness();
			if (newBestFitness == oldBestFitness) stagnationCount++;
			else stagnationCount = 0;
			oldBestFitness = newBestFitness;
		}
		return prevGeneration.getBestCandidate().getDna();
	}

	public void generatePopulationZero() {
		populationZero = new Population();
		populationZero.populate(graph, oligosOriginalMap);
	}

	/**
	 * Duels between candidates in population. Population size must always stay even.
	 */
	public void selectionInPopulationOLD(Population prevGeneration) {
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

	public void selectionInPopulation(Population prevGeneration) {
		while (nextGeneration.getPopulationSize() != Main.POPULATION_SIZE) {
			int randomIndex1 = threadLocalRandom.nextInt(prevGeneration.getPopulationSize());
			int randomIndex2 = threadLocalRandom.nextInt(prevGeneration.getPopulationSize());
			if (randomIndex1 != randomIndex2) {
				Candidate candidate1 = prevGeneration.getCandidate(randomIndex1);
				Candidate candidate2 = prevGeneration.getCandidate(randomIndex2);
				if (candidate1.getFitness() > candidate2.getFitness()) {
					nextGeneration.addCandidate(candidate1);
					prevGeneration.removeCandidate(candidate1);
				}
				else {
					nextGeneration.addCandidate(candidate2);
					prevGeneration.removeCandidate(candidate2);
				}
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
		children.clear();
		for (int i = 0; i < prevGeneration.getPopulationSize() - 2; i += 2) {
			if (threadLocalRandom.nextInt(HUNDRED_PERCENT) <= Main.CROSSOVER_PROBABILITY)
				crossover(prevGeneration.getCandidate(i).getDna(), prevGeneration.getCandidate(i + 1).getDna());
		}
		prevGeneration.addCandidates(children);
	}

	/**
	 * Creating 2 children from 2 parents by cutting their dna parts at random point
	 * and joining them together. Then children have fitness calculated and they're added to generation.
	 * Cut point must not exceed shorter DNA size.
	 */
	private void crossover(String parent1Dna, String parent2Dna) {
		System.out.println("crossing");
		int shorterDnaLength = Math.min(parent1Dna.length(), parent2Dna.length());
		int cutPoint = threadLocalRandom.nextInt(Main.OLIGO_SIZE, shorterDnaLength);

		Candidate child1 = new Candidate();
		child1.setDna(parent1Dna.substring(0, cutPoint) + parent2Dna.substring(cutPoint));
		Candidate child2 = new Candidate();
		child2.setDna(parent2Dna.substring(0, cutPoint) + parent1Dna.substring(cutPoint));

		child1.setOligosFromDna();
		child2.setOligosFromDna();
		child1.calculateFitness(oligosOriginalMap);
		child2.calculateFitness(oligosOriginalMap);

		children.add(child1);
		children.add(child2);
	}

	/**
	 * Choosing candidate for mutation at percent of MUTATION_PROBABILITY
	 * After mutating updating it's oligos and fitness
	 */
	private void mutationInPopulation() {
		for (int i = 0; i < prevGeneration.getPopulationSize(); i++)
			if (threadLocalRandom.nextInt(HUNDRED_PERCENT) <= Main.MUTATION_PROBABILITY) {
				Candidate candidateToMutate = prevGeneration.getCandidate(i);
				candidateToMutate.setDna(mutation(candidateToMutate.getDna()));
				candidateToMutate.setOligosFromDna();
				candidateToMutate.calculateFitness(oligosOriginalMap);
				prevGeneration.replaceCandidate(i, candidateToMutate);
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

	/**
	 * If 20 consecutive bestFitness is same method triggers stopping the main loop
	 */
	private boolean isStagnation(int count) {
		if (count > 35) return true;
		return false;
	}
}
