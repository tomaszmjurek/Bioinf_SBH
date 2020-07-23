package com.bioinf;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class DNASpectrum {

	private String dna;
	public static String firstOligo;
	private Map<String, Integer> oligosMap = new HashMap<>();
	private List<String> oligosList = new ArrayList<>();
	private ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

	public DNASpectrum(String DNA) {
		this.dna = DNA;
		firstOligo = DNA.substring(0, Main.OLIGO_SIZE);
	}

	public Map<String, Integer> getOligosMap() {
		return oligosMap;
	}

	/**
	 * Cuts given DNA into oligos, count duplicates, shuffle order
     */
	public void cutDNAIntoOligos() {
		int oligosNum = dna.length() - Main.OLIGO_SIZE + 1;
		for (int i = 0; i < oligosNum; i++) {
			String oligo = dna.substring(i, i + Main.OLIGO_SIZE);
			oligosMap.merge(oligo, 1, Integer::sum);
		}
		createOligosList();
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

	/**
	 * Add negative errors to simulate real life experiment.
	 * Some oligonucleotides from original DNA where lost.
	 * If oligo count was 1 it is removed from map.
	 */
	public void generateNegativeErrors() {
		int toDeleteCount = oligosMap.size() / Main.ERRORS_PROBABILITY;
		for (int i = 0; i < toDeleteCount; i++) {
			int toDeleteIndex = threadLocalRandom.nextInt(oligosMap.size());
			String oligo =  oligosList.get(toDeleteIndex);
			if /* it's first oligo */ (oligo.equals(firstOligo)) i--;
			else {
				if (oligosMap.get(oligo) == 1) oligosMap.remove(oligo);
				else oligosMap.replace(oligo, oligosMap.get(oligo) - 1);
				oligosList.remove(toDeleteIndex);
				System.out.println("Negative error occurred - removed one " + oligo);
			}
		}
	}

	/**
	 * Add negative errors to simulate real life experiment.
	 * We got some extra oligos not occurring in original DNA.
	 * If oligo already exists it's count is incremented.
	 */
	public void generatePositiveErrors() {
		int toAddCount = oligosMap.size() / Main.ERRORS_PROBABILITY;
		for (int i = 0; i < toAddCount; i++) {
			String randomOligo = generateRandomOligo();
			if  (oligosMap.containsKey(randomOligo))
				oligosMap.replace(randomOligo, oligosMap.get(randomOligo) + 1);
			else oligosMap.put(randomOligo, 1);
			System.out.println("Positive error occurred - added one " + randomOligo);
		}
	}

	private void createOligosList() {
		for (Map.Entry<String, Integer> entry : oligosMap.entrySet()) {
			int count = entry.getValue();
			while (count > 0) {
				oligosList.add(entry.getKey());
				count--;
			}
		}
	}

	private String generateRandomOligo() {
		StringBuilder oligoBuilder = new StringBuilder();
		for (int i = 0; i < Main.OLIGO_SIZE; i++) {
			char randomNucleotide = Main.NUCLEOTIDES[threadLocalRandom.nextInt(4)];
			oligoBuilder.append(randomNucleotide);
		}
		return oligoBuilder.toString();
	}
}
