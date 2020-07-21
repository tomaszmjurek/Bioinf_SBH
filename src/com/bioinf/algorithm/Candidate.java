package com.bioinf.algorithm;

import com.bioinf.Main;

import java.util.ArrayList;
import java.util.HashMap;
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

	public void setOligos(ArrayList<String> oligos) {
		this.oligos = oligos;
	}

	/**
	 * Vertex's count minus 1 for every usage.
	 * Candidate fitness +1 if vertex was used less times than it's oligo occurs
	 * otherwise -1
	 */
	public void calculateFitness(Map<String, Integer> globalOligosMap) {
		Map<String, Integer> localOligosMap = new HashMap<>(globalOligosMap);
		int activeCount;
		fitness = 0;
		for (int i = 0; i < oligos.size(); i++) {
			if (localOligosMap.containsKey(oligos.get(i))) {
				activeCount = localOligosMap.get(oligos.get(i));
				if (activeCount > 0) fitness++;
//				else fitness--;
				localOligosMap.replace(oligos.get(i), activeCount - 1);
			}
		}
	}

	public void setOligosFromDna() {
		int oligosNum = dna.length() - Main.OLIGOS_SIZE + 1;
		oligos.clear();
		for (int i = 0; i < oligosNum; i++) {
			String oligo = dna.substring(i, i + Main.OLIGOS_SIZE);
			oligos.add(oligo);
		}
	}

	private void printOligos() {
		System.out.println("Candidate Oligos: ");
		oligos.forEach(o -> System.out.print(" " + o));
	}
}
