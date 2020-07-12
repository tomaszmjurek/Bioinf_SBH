package com.bioinf.algorithm;

import java.util.ArrayList;
import java.util.Map;

public class Candidate {
	private String dna;
	private int fitness;
	private ArrayList<String> oligos = new ArrayList<>();

	public Candidate () {
		this.fitness = 0;
	}

	public String getDna() {
		return dna;
	}

	public int getFitness() {
		return fitness;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public ArrayList<String> getOligos() {
		return oligos;
	}

	public void addOligo(String oligo) {
		this.oligos.add(oligo);
	}

	/**
	 * Vertex's count minus 1 for every usage.
	 * Candidate fitness +1 if vertex was used less times than it's oligo occurs
	 * otherwise -1
	 */
	public void calculateFitness(Map<String, Integer> localOligosMap) {
//		System.out.println("calculating vertex fitness...");
		int activeCount;
		for (int i = 0; i < oligos.size(); i++) {
			activeCount = localOligosMap.get(oligos.get(i));
			if (activeCount > 0) this.fitness++;
			else this.fitness--;
			localOligosMap.replace(oligos.get(i), activeCount-1);
		}
	}
}
