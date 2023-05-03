package com.example.genetic_algo_sma.agents;

import jade.core.Agent;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

public class Sketch extends GuiAgent {

    private SketchGUI sketchGUI;
    @Override
    protected void setup() {
        sketchGUI = (SketchGUI) getArguments()[0];

        sketchGUI.setSketchAgent(this);
    }

    @Override
    protected void onGuiEvent(GuiEvent guiEvent) {
        char[] result = (char[]) guiEvent.getParameter(0);

        System.out.println(result);
    }
}
