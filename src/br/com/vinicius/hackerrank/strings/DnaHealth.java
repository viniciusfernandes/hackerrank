package br.com.vinicius.hackerrank.strings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class DnaHealth {

  public static void main(String[] args) throws IOException {
    int maxGenes = 100000;
    int maxValue = 10000000;
    int maxLenght = 2 * 1000000;
    var init = new Date();
    Scanner scanner = new Scanner(new FileInputStream("/home/vinicius/Documents/dna-strands-1.txt"));
    //Scanner scanner = new Scanner(System.in);

    int numGenes = scanner.nextInt();
    if (numGenes < 1 || numGenes > maxGenes) {
      return;
    }

    String[] genes = new String[numGenes];
    long totalLength = 0;
    for (int i = 0; i < numGenes; i++) {
      genes[i] = scanner.next();
      totalLength += genes[i].length();
    }


    long[] healths = new long[numGenes];
    for (int i = 0; i < numGenes; i++) {
      healths[i] = scanner.nextLong();
      if (healths[i] > maxValue) {
        return;
      }
    }

    int numStrands = scanner.nextInt();
    if (numStrands < 1 || numStrands > maxGenes) {
      return;
    }

    var dnaStrands = new DnaStrand[numStrands];
    for (int i = 0; i < numStrands; i++) {
      int first = scanner.nextInt();
      int last = scanner.nextInt();
      if (first < 0 || first >= numGenes || last < 0 || last >= numGenes) {
        return;
      }
      String dna = scanner.next();
      dnaStrands[i] = new DnaStrand(first, last, dna);
      totalLength += dna.length();
    }

    scanner.close();
    if (totalLength < 1 || totalLength > maxLenght) {
      return;
    }
    new DnaHealthCalculator().calcMinMaxHealths(genes, healths, dnaStrands);
    System.out.println("t=" + (new Date().getTime() - init.getTime()) / 1000d);
  }
}

class DnaStrand {
  final int first;

  final int last;
  final String dna;

  public DnaStrand(int first, int last, String dna) {
    this.first = first;
    this.last = last;
    this.dna = dna;
  }

}

class DnaHealthCalculator {

  public void calcMinMaxHealths(String[] genes, long[] healths, DnaStrand[] strands) {
    long minHealth = Long.MAX_VALUE;
    long maxHealth = Long.MIN_VALUE;
    for (var strand : strands) {
      calcTotalHealth(genes, healths, strand);
      maxHealth = Math.max(maxHealth, totalHealth);
      minHealth = Math.min(minHealth, totalHealth);
    }
    System.out.println(minHealth + " " + maxHealth);
  }

  long totalHealth = 0;
  long health = 0;
  String gen = null;
  long totalGenOccur = 0;

  private void calcTotalHealth(String[] genes, long[] healths, DnaStrand strand) {

    for (int i = strand.first; i <= strand.last; i++) {
      health = healths[i];
      gen = genes[i];
      countGeneOccurence(gen, strand.dna);
      totalHealth += health * totalGenOccur;
    }

  }

  private void countGeneOccurence(String gene, String dna) {
    totalGenOccur = 0;
    int index = dna.indexOf(gene);

    while (index != -1) {
      totalGenOccur++;
      index = dna.indexOf(gene, index + 1);
    }
  }
}

