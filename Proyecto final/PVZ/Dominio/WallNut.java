package Dominio;

import javax.swing.ImageIcon;

public class WallNut extends Plant{

    public WallNut(int  x, int y) {
        super("WallNut", 4000, x, y, 50, new ImageIcon("resources/Characters/Wallnut.png"));
    }
    @Override
    public void stop(){
        
    }
}
