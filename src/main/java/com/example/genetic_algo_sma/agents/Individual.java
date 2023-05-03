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
            genes[i] = (char) (r.nextInt(26) + 'a');
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
                genes[i] = (char) (r.nextInt(26) + 'a');
            }
        }
    }

    public char[] getGenes() {
        return genes;
    }

    public double getFitness() {
        return fitness;
    }
}
