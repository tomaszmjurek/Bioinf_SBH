package com.bioinf;

import java.util.*;

public class Main {

    public static int OLIGOS_SIZE = 3;

    public static void main(String[] args) {

        String DNA1 = "ATGCGTCAGCGACTGTACGTACGTACGTGCATGCAGT";
        String DNA2 = "ATGATGATC"; //ATGx2 TGAx2 GATx2 GAT ATC

        DNASpectrum spectrum = new DNASpectrum(DNA2);
        spectrum.cutDNAIntoOligos();
//        spectrum.shuffleOligos();

        spectrum.printOligos();


    }
}
