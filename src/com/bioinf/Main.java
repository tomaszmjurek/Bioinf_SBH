package com.bioinf;

import com.bioinf.algorithm.GeneticAlgorithm;
import com.bioinf.graph.Graph;

public class Main {

    public static int OLIGOS_SIZE = 3;
    public static int DNA_SIZE;
    private static int POPULATION_SIZE = 10;

    public static void main(String[] args) {

        String DNA1 = "ATGCGTCAGCGACTGTACGTACGTACGTGCATGCAGT";
        String DNA2 = "ATGATGATC"; //ATGx2 TGAx2 GATx2 ATC

        DNA_SIZE = DNA2.length();
        DNASpectrum spectrum = new DNASpectrum(DNA2);
        spectrum.cutDNAIntoOligos();
        // bledy pozytywne - dodajemy pare nieistniejacych wczesniej oligo o licznosci 1
        // bledy negatywne - usuwamy pare oligo (zmniejszamy licznosc czy tylko te z 1?)

        System.out.println("Oligonucleotides:");
        spectrum.printOligos();

        Graph graph = new Graph();
        graph.buildGraph(spectrum.getOligosMap());
        System.out.println("\nGraph:");
        graph.printGraph();
        System.out.println("Graph starts from: " + graph.getGraphStart().getOligo());

        GeneticAlgorithm algorithm = new GeneticAlgorithm(spectrum.getOligosMap());
        algorithm.getRandomPopulation(graph, POPULATION_SIZE);
        algorithm.printPopulation();

        System.out.println("\nBest fitness = " + algorithm.getBestFitness());
    }
}
