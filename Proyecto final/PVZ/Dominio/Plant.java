package Dominio;

import javax.swing.ImageIcon;

import Presentation.SunGenerator;

public abstract class Plant extends Character{
    private static final String TYPE = "Plant";

    public Plant(String name, int health, int x, int y, int sunCost, ImageIcon image) {
        super(name, health, x, y, sunCost, image, TYPE);
    }

    public void startAction(Grid grid, SunGenerator sunGenerator) { // para las plantas que generan soles 
        
    }

    public void startAction() { // para cualquier otra planta 
    }
}

