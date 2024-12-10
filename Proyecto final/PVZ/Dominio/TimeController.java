package Dominio;

import java.util.concurrent.TimeUnit;
import javax.swing.Timer;




public class TimeController {
    private Timer gameTimer;
    private long gameTimeDuration;
    int percentageProgress;
    public TimeController(){
        gameTimer = new Timer(500, e -> verifyGameStatus());
        gameTimer.start();
        gameTimeDuration =  System.currentTimeMillis();
    }

    private boolean checkTime() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - gameTimeDuration;  
        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
      
        long totalDuration = GameConfig.getGameDuration();
        percentageProgress = (int) ((elapsedTime * 100) / (totalDuration * 60 * 1000)); 

        //progressBar.setValue(percentageProgress); //actualiza el valor y pinta otra vez la barrita de progreso

        //updateSpamZombiesByTimeGame();

        if (elapsedMinutes >= totalDuration) {
            gameTimer.stop();
            return true;
        }
        return false;
    } 

    private String verifyGameStatus() { 
        if (GameConfig.getIsGameOver()) {
            return "Game Over";
        } else if (checkTime()) {
            return "Ganaste";
        } else {
            return  "" ;
        }
    }

    public int getPercentageProgress(){
        return percentageProgress;
    }
}
