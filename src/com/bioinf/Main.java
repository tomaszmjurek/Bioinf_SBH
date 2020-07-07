package com.bioinf;

import com.bioinf.graph.Graph;

import java.util.*;

public class Main {

    public static int OLIGOS_SIZE = 3;

    public static void main(String[] args) {

        String DNA1 = "ATGCGTCAGCGACTGTACGTACGTACGTGCATGCAGT";
        String DNA2 = "ATGATGATC"; //ATGx2 TGAx2 GATx2 ATC

        DNASpectrum spectrum = new DNASpectrum(DNA2);
        spectrum.cutDNAIntoOligos();
        // bledy pozytywne - dodajemy pare nieistniejacych wczesniej oligo o licznosci 1
        // bledy negatywne - usuwamy pare oligo (zmniejszamy licznosc czy tylko te z 1?)

        System.out.println("Oligonucleotides:\n");
        spectrum.printOligos();

        Graph graph = new Graph();
        graph.buildGraph(spectrum.getOligosMap());
        System.out.println("\nGraph: ");
        graph.printGraph();

        System.out.println("Graph starts from: " + graph.getGraphStart().getOligo());
    }
}
