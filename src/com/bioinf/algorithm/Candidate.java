package com.bioinf.algorithm;

public class Candidate {
	private String dna;
	private int fitness;

	public Candidate (String dna, int fitness) {
		this.dna = dna;
		this.fitness = fitness;
	}

	public String getDna() {
		return dna;
	}

	public int getFitness() {
		return fitness;
	}
}
