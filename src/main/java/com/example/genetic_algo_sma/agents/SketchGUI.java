package com.example.genetic_algo_sma.agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SketchGUI implements Initializable {

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
            String target = "hello";
            double mutationRate = 0.01;
            int popMax = 200;

            population = new Population(target, popMax, mutationRate);
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
        while (!population.isFinished()){

            population.naturalSelection();
            population.generate();
            population.calcFitness();
            population.evaluate();
            GuiEvent guiEvent = new GuiEvent(this, 1);
            guiEvent.addParameter(population.getBest().getGenes());
            sketchAgent.onGuiEvent(guiEvent);
        }
    }

    public void setSketchAgent(Sketch sketchAgent) {
        this.sketchAgent = sketchAgent;
    }
}
