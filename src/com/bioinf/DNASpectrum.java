package com.bioinf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DNASpectrum {

	private String dna;
	private Set<String> oligos = new HashSet<>();
	private int [] oligosDuplication;
	private String firstOligo;

	public DNASpectrum(String DNA) {
		this.dna = DNA;
		this.firstOligo = DNA.substring(0, 3);
	}

	/**
	 * Cuts given DNA into oligos, process duplicates
	 */
	public void cutDNAIntoOligos() {
		int oligosNum = dna.length() - Main.OLIGOS_SIZE + 1;
		for (int i = 0; i < oligosNum; i++) {
			String oligo = dna.substring(i, i + Main.OLIGOS_SIZE);
			if (oligos.add(oligo) == false) { // duplicate
				//manage dups
			}
		}
	}

	/**
	 * Sorts oligos alphabetically to simulate loosing initial order
	 */
//	public void shuffleOligos() {
//		Collections.sort(oligos);
//	}

	public void processDuplicates() {
		//find dups
		//override array
		//add info with num of dups
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

	public Set<String> getOligos() {
		return oligos;
	}

	public void printOligos() {
		for (String o: oligos) {
			System.out.print(o + ' ');
		}
	}
}
