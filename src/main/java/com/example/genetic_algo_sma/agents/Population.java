package com.example.genetic_algo_sma.agents;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population extends Agent {

    private Individual[] population;
    private int maxIter;
    private List<Individual> matingPool;
    private int popMax;
    private String target;
    private Individual best;
    private Random random;
    private int generations;
    private double mutationRate;
    private boolean finished = false;
    private double perfectScore = 1.0;

    public Population(String target, int popMax, double mutationRate, int maxIter) {
        this.target = target;
        this.popMax = popMax;
        this.mutationRate = mutationRate;
        this.maxIter = maxIter;
        population = new Individual[popMax];
        matingPool = new ArrayList<>();
        random = new Random();
        for (int i = 0; i < popMax; i++) {
            population[i] = new Individual(target.length());
        }
        evaluate();
    }

    public void evaluate() {
        double record = 0;
        int index = 0;
        for (int i = 0; i < population.length; i++) {
            population[i].calculateFitness(target);
            if (population[i].getFitness() > record) {
                record = population[i].getFitness();
                index = i;
            }
        }
        best = new Individual(population[index].getGenes());
        if (best.getFitness() == perfectScore) {
            finished = true;
        }
    }

    public void naturalSelection() {
        matingPool.clear();
        double maxFitness = 0;
        for (Individual individual : population) {
            if (individual.getFitness() > maxFitness) {
                maxFitness = individual.getFitness();
            }
        }
        for (Individual individual : population) {
            double fitness = map(individual.getFitness(), 0, maxFitness, 0, 1);
            int n = (int) (fitness * 100);
            for (int j = 0; j < n; j++) {
                matingPool.add(individual);
            }
        }
    }

    public void evolve() {
        for (int i = 0; i < population.length; i++) {
            int a = random.nextInt(matingPool.size());
            int b = random.nextInt(matingPool.size());
            Individual partnerA = matingPool.get(a);
            Individual partnerB = matingPool.get(b);
            Individual child = partnerA.crossover(partnerB);
            child.mutate(mutationRate);
            population[i] = child;
        }
        generations++;
    }

    public double getAverageFitness() {
        double total = 0;
        for (int i = 0; i < this.population.length; i++) {
            total += this.population[i].getFitness();
        }
        return total / this.population.length;
    }

    public double map(double value, double start1, double stop1, double start2, double stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
    }

    //***************************Getters
    public Individual[] getPopulation() {
        return population;
    }

    public List<Individual> getMatingPool() {
        return matingPool;
    }

    public int getPopMax() {
        return popMax;
    }

    public String getTarget() {
        return target;
    }

    public Individual getBest() {
        return best;
    }

    public int getGenerations() {
        return generations;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public boolean isFinished() {
        return finished;
    }

    public void calcFitness() {
        for (int i = 0; i < this.population.length; i++) {
            this.population[i].calculateFitness(this.target);
        }
    }
}
