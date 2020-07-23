package com.bioinf;

import com.bioinf.algorithm.GeneticAlgorithm;
import com.bioinf.graph.Graph;

public class Main {

    public static int OLIGOS_SIZE = 3;
    public static int DNA_SIZE;
    public static int POPULATION_SIZE = 100; //500
    public static int GENERATIONS_NUMBER = 100; //200
    public static double CROSSOVER_PROBABILITY = 95; // %
    public static double MUTATION_PROBABILITY = 4; // %
    public static int ERRORS_STEP = 5; // error for each step
    public static char [] NUCLEOTIDES = { 'A', 'T', 'C', 'G'};

    public static void main(String[] args) {

        String DNA1 = "ATGCGTCAGCGACTGTACGTACGTACGTGCATGCAGT";
        String DNA2 = "ATGATGATC"; //ATGx2 TGAx2 GATx2 ATC
        String DNA3 = "TTCGCATGACTGCATGCTGACTAGCTACGTATATTACGCGGCGCTAGCCCATGCACGCTAGTACGTCGTACGT";

        DNA_SIZE = DNA1.length();
        DNASpectrum spectrum = new DNASpectrum(DNA1);
        System.out.println("Spectrum: " + spectrum.getDna());

        spectrum.cutDNAIntoOligos();
        System.out.println("\nOligonucleotides without errors:");
        spectrum.printOligos();

        spectrum.generateNegativeErrors();
        spectrum.generatePositiveErrors();

        Graph graph = new Graph();
        graph.buildGraph(spectrum.getOligosMap());
        System.out.println("\nGraph:");
        graph.printGraph();
        System.out.println("Graph starts from: " + graph.getGraphStart().getOligo());

        GeneticAlgorithm algorithm = new GeneticAlgorithm(spectrum.getOligosMap(), graph);
        algorithm.run();

        System.out.println("\nOriginal DNA was " + spectrum.getDna());
    }
}
