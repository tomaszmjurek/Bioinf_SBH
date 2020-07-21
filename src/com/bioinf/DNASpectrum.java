package com.bioinf;

import java.util.*;

public class DNASpectrum {

	private String dna;
	public static String firstOligo;
	private Map<String, Integer> oligosMap = new HashMap<>();

	public Map<String, Integer> getOligosMap() {
		return oligosMap;
	}

	public DNASpectrum(String DNA) {
		this.dna = DNA;
		firstOligo = DNA.substring(0, Main.OLIGOS_SIZE);
	}

	/**
	 * Cuts given DNA into oligos, count duplicates, shuffle order
     */
	public void cutDNAIntoOligos() {
		int oligosNum = dna.length() - Main.OLIGOS_SIZE + 1;
		for (int i = 0; i < oligosNum; i++) {
			String oligo = dna.substring(i, i + Main.OLIGOS_SIZE);
			oligosMap.merge(oligo, 1, Integer::sum);
		}
	}

	public String getDna() {
		return dna;
	}

	public void setDna(String dna) {
		this.dna = dna;
	}

	public void printOligos() {
		oligosMap.forEach((key, value) -> System.out.println(key + " " + value));
	}

	public void generatePositiveErrors() {
		
	}

}
