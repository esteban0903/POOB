package Dominio;

import javax.swing.ImageIcon;

public class ECIPlant extends Sunflower {

    public ECIPlant(int x, int y) {
        super(x, y);
        setName("ECIPlant"); 
        setCost(75); 
        setImage(new ImageIcon("resources/ECIPlant.png")); 
        setSunValue(50); 
        setCharacterHealth(150);
    }
}
