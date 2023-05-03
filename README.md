# Implement genetic algorithm in multi-agent system with JADE

Genetic Algorithm (GA) is a metaheuristic optimization algorithm inspired by the process of natural selection. The main concepts of GA include:

- Chromosome
- Fitness Function
- Evolving
  - Selection
  - Crossover
  - Mutation
- Population
- Generation

## project structure we follow when implementing a Genetic Algorithm in Java:

    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   ├── agents
    │   │   │   │   ├── Sketch.java
    │   │   │   │   ├── SketchGUI.java
    │   │   │   │   ├── Population.java
    │   │   │   │   └── Indevidual.java
    │   │   │   ├── App.java    
    │   │   │   └── MainContainer.java
    │   │   └── resources
    │   │       └──view.fxml
    │   ├── test
    │   │   └── java
    │   └── pom.xml
    └── README.md

# Overview

The genetic algorithm is implemented with two classes: *Individual* and *Population*.

`Individual` : represents an individual in the population and contains the individual's genes (i.e. a string of characters) and its fitness score. The fitness score is calculated based on how many characters in the individual's genes match the target string. The Individual class also includes methods for mutating the individual's genes and calculating its fitness score.

    public void calculateFitness(String target) {
      double score = 0;
      for (int i = 0; i < genes.length; i++) {
        if (genes[i] == target.charAt(i)) {
        score++;
        }
      }
      fitness = score / target.length();
    }

- Functionality:
  - convert Chromosome into a string
  - calculate Individual's "fitness"
  - mate Individual with another set of Individual
  - mutate and crossover Individuals



`Population`: represents the population and contains an array of Individual objects, as well as methods for generating new individuals, selecting individuals for mating, and evolving the population over time. The Population class includes a evolve method that runs the genetic algorithm until a perfect match is found or a certain number of generations have passed.

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

Based on fitness, each member will get added to the mating pool a certain number of times
   - a higher fitness = more entries to mating pool = more likely to be picked as a parent
   - a lower fitness = fewer entries to mating pool = less likely to be picked as a parent

    for (Individual individual : population) {
          double fitness = map(individual.getFitness(), 0, maxFitness, 0, 1);
          int n = (int) (fitness * 100);
          for (int j = 0; j < n; j++) {
              matingPool.add(individual);
          }
    }

Compute the current "most fit" member of the population

    evaluate() {
      let worldrecord = 0.0;
      let index = 0;
      for (let i = 0; i < this.population.length; i++) {
        if (this.population[i].fitness > worldrecord) {
          index = i;
          worldrecord = this.population[i].fitness;
        }
      }
  
      this.best = this.population[index].getPhrase();
      if (worldrecord === this.perfectScore) {
        this.finished = true;
      }
  }

# Test

`Sketch` : this agent has role to initialize the population and display current status of population

    population = new Population(target, popMax, mutationRate ,maxIter);
    

    while (iteration < population.getMaxIter() || !population.isFinished()){

                // Generate mating pool
                    population.naturalSelection();

                //Create next generation
                    population.evolve();

                // Calculate fitness
                    population.calcFitness();
                    population.evaluate();
                    iteration++;
                    GuiEvent guiEvent = new GuiEvent(this, 1);
                    guiEvent.addParameter(population.getBest().getGenes());
                    sketchAgent.onGuiEvent(guiEvent);

                // Display current status of population
                    updateMessage(Arrays.toString(population.getBest().getGenes()));
                    Thread.sleep(100);
                }

![Genetic Algorithm 2023-05-03 20-40-44](https://user-images.githubusercontent.com/63566276/236045691-b850cf89-bd20-4405-8437-4e8b393eb437.gif)

