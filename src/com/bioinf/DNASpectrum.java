package com.bioinf;

import java.util.*;

public class DNASpectrum {

	private String dna;
	private String firstOligo; // trzeba go zlokalizowac w hashmapie
	private Map<String, Integer> oligosMap = new HashMap<>();

	public DNASpectrum(String DNA) {
		this.dna = DNA;
		this.firstOligo = DNA.substring(0, 3);
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

	public String getFirstOligo() {
		return firstOligo;
	}

	public void setFirstOligo(String firstOligo) {
		this.firstOligo = firstOligo;
	}

//	public Set<String> getOligos() {
//		return oligos;
//	}

	public void printOligos() {
		oligosMap.entrySet().forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
	}
}
