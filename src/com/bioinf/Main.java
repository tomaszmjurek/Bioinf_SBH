package com.bioinf;

import com.bioinf.algorithm.GeneticAlgorithm;
import com.bioinf.graph.Graph;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static int OLIGO_SIZE = 7;
    public static int DNA_SIZE = 300;
    public static int POPULATION_SIZE = 10; //500
    public static int GENERATIONS_NUMBER = 2; //200
    public static double CROSSOVER_PROBABILITY = 95; // %
    public static double MUTATION_PROBABILITY = 4; // %
    public static int ERRORS_STEP = 5; // error for each step todo change for 5%
    public static char [] NUCLEOTIDES = { 'A', 'T', 'C', 'G'};

    private static String getDNAFromFile() throws IOException {
        FileReader fileReader = new FileReader("wuhan-hu-1-genome.txt");
        int fileSize = 29903;
        int begin = ThreadLocalRandom.current().nextInt(fileSize - DNA_SIZE);
        int end = begin + DNA_SIZE;
        StringBuilder DNABuilder = new StringBuilder();
        while (begin < end) {
            DNABuilder.append((char) fileReader.read());
            begin++;
        }
        return  DNABuilder.toString();
    }

    public static void main(String[] args) {

//        String DNA1 = "ATGATGATC"; //ATGx2 TGAx2 GATx2 ATC
//        String DNA2 = "TTCGCATGACTGCATGCTGACTAGCTACGTATATTACGCGGCGCTAGCCCATGCACGCTAGTACGTCGTACGT";

        String DNA = "";
        try {
            DNA = getDNAFromFile();
        } catch (Exception e) {
            System.out.println(e);
        }

//        DNA_SIZE = DNA1.length();
        DNASpectrum spectrum = new DNASpectrum(DNA);
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
