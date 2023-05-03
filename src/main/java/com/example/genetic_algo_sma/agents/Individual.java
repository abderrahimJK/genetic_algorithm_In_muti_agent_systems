package com.example.genetic_algo_sma.agents;

import jade.core.Agent;

import java.util.Random;

public class Individual extends Agent {

    private char[] genes;
    private double fitness;
    private Random random;

    public Individual(int numGenes) {
        genes = new char[numGenes];
        Random r = new Random();
        for (int i = 0; i < numGenes; i++) {
            genes[i] = newChar();
        }
    }

    public Individual(char[] genes) {
        this.genes = genes;
    }

    public void calculateFitness(String target) {
        double score = 0;
        for (int i = 0; i < genes.length; i++) {
            if (genes[i] == target.charAt(i)) {
                score++;
            }
        }
        fitness = score / target.length();
    }

    public Individual crossover(Individual other) {
        int mid = (int) (genes.length * Math.random());
        char[] childGenes = new char[genes.length];
        for (int i = 0; i < genes.length; i++) {
            if (i > mid) {
                childGenes[i] = genes[i];
            } else {
                childGenes[i] = other.getGenes()[i];
            }
        }
        return new Individual(childGenes);
    }

    public void mutate(double mutationRate) {
        Random r = new Random();
        for (int i = 0; i < genes.length; i++) {
            if (Math.random() < mutationRate) {
                genes[i] = newChar();
            }
        }
    }

    private char newChar() {
        random = new Random();
        int c = (int) Math.floor(Math.random() * (122 - 63 + 1)) + 63;
        if (c == 63) {
            c = 32;
        }
        if (c == 64) {
            c = 46;
        }
        return (char) c;
    }

    public String getPhrase(){
        return new String(genes);
    }

    public char[] getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }
}
