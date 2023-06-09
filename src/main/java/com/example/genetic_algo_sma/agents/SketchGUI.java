package com.example.genetic_algo_sma.agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SketchGUI implements Initializable {

    public Label geneNumber;
    public Label avgFitness;
    public Label totalPop;
    public Label mutationRate;
    private Population population;
    public Label bestPhrase;
    public Button startBtn;
    private Task<String> brutForceTask;
    Sketch sketchAgent;
    GuiEvent guiEvent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            startContainer();
            String target = "To be or not to be";
            double mutationRate = 0.01;
            int popMax = 200;
            int maxIter = 10;

            population = new Population(target, popMax, mutationRate ,maxIter);
            startBtn.setOnAction(actionEvent -> evolve());
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    private void startContainer() throws StaleProxyException {
        Runtime runtime = Runtime.instance();
        ProfileImpl profile = new ProfileImpl();
        profile.setParameter(profile.MAIN_HOST, "localhost");
        AgentContainer container = runtime.createAgentContainer(profile);
        AgentController agentController = container.createNewAgent("sketch", "com.example.genetic_algo_sma.agents.Sketch", new Object[]{this});
        agentController.start();
    }

    private void evolve(){

        if (brutForceTask != null && brutForceTask.isRunning()) {
            return; // already running
        }
        brutForceTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                int iteration = 0;
                while (iteration < population.getMaxIter() || !population.isFinished()){
                    population.naturalSelection();
                    population.evolve();
                    population.calcFitness();
                    population.evaluate();
                    iteration++;
                    GuiEvent guiEvent = new GuiEvent(this, 1);
                    guiEvent.addParameter(population.getBest().getGenes());
                    sketchAgent.onGuiEvent(guiEvent);
                    updateMessage(population.getBest().getPhrase());
                    int finalIteration = iteration;
                    Platform.runLater( ()->{

                        geneNumber.setText(String.valueOf(population.getGenerations()));
                        totalPop.setText(String.valueOf(population.getPopMax()));
                        mutationRate.setText(String.valueOf(population.getMutationRate()));
                        avgFitness.setText(String.valueOf(population.getAverageFitness()));

                    });
                    Thread.sleep(100);
                    
                }
                return null;
            }

        };

        bestPhrase.textProperty().bind(brutForceTask.messageProperty());
        new Thread(brutForceTask).start();
    }

    public void setSketchAgent(Sketch sketchAgent) {
        this.sketchAgent = sketchAgent;
    }
}
