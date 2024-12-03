package Dominio;

import javax.swing.ImageIcon;

import Presentation.SunGenerator;

public abstract class Plant extends Character{
    private static final String TYPE = "Plant";

    public Plant(String name, int health, int x, int y, int sunCost, ImageIcon image) {
        super(name, health, x, y, sunCost, image, TYPE);
    }

    public void startAction(Grid grid, SunGenerator sunGenerator) { 
        
    }

    public void startAction() { 

    }   
}

