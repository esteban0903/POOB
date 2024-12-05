package Presentation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CharacterSelectionWindow extends Window {
    AudioPlayer player = new AudioPlayer("resources/characterSelect.wav");
    private JPanel availablePlantsPanel;
    private JPanel selectedPlantsPanel;
    private final Map<String, JPanel> plantButtons; 
    private final Map<String, String> plantImages;  
    private final Map<String, String> selectedPlantsMap;  
    private Color green = new Color(34, 139, 34); //color verde de boton
    private JButton startGameButton = GameController.createButton("Iniciar Juego", 250, 640, 200, 40, green, Color.WHITE);
    private JButton returnButton = GameController.createButton("Volver", 850, 640, 200, 40, green, Color.WHITE);
    public static void main(String[] args) {
        new CharacterSelectionWindow();
    }

    public CharacterSelectionWindow() {
        super("Selección de Plantas", "resources/characterSelection.jpg");

        plantButtons = new HashMap<>();
        plantImages = new HashMap<>();
        plantImages.put("Peashooter", "resources/Peashooter.png"); // para probar algunas plantas 
        plantImages.put("Sunflower", "resources/Sunflower.png");
        plantImages.put("Wallnut", "resources/Wallnut.png");
        plantImages.put("EciPlant", "resources/EciPlant.png");

    
        selectedPlantsMap = new HashMap<>();  

        initializePanels();

        addAvailablePlants();

        add(startGameButton);
        add(returnButton);
        configureStartGameButton();
        configureReturnButton();
        player.playMusic();
        showWindow();
    }

    private void initializePanels() {
        //se usa FlowLayout porque necesitamos que se llenen horizontalmente

        availablePlantsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 22, 25)); // se asignan separaciones manualmente
        availablePlantsPanel.setBounds(247, 235, 265, 260);
        availablePlantsPanel.setOpaque(false);
        add(availablePlantsPanel);

        selectedPlantsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 15)); // se asignan separaciones manualmente
        selectedPlantsPanel.setBounds(797, 235, 300, 250); 
        selectedPlantsPanel.setOpaque(false);
        add(selectedPlantsPanel);
    }

    private void addAvailablePlants() {
        for (String plant : plantImages.keySet()) {
            int width = 30;  
            int height = 30; 

            JPanel plantPanel = GameController.createButtonWithImage(plantImages.get(plant),null,plant,width,height);

            addActionToPlantPanel(plant, plantPanel);
            plantButtons.put(plant, plantPanel);
            availablePlantsPanel.add(plantPanel);
        }
    }

    private void addActionToPlantPanel(String plant, JPanel plantPanel) {
        for (Component comp : plantPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(e -> addToSelectedPlants(plant, plantPanel));
            }
        }
    }

    private void addToSelectedPlants(String plant, JPanel plantPanel) {
        /*if (selectedPlantsPanel.getComponentCount() >= 6) { // Máximo 6 espacios en un FlowLayout horizontal
            JOptionPane.showMessageDialog(this, "Ya seleccionaste el máximo de plantas.");
            return;
        }*/

        disablePlantPanel(plantPanel);
        int width = 24;  
        int height = 25; 

        JPanel selectedPlantPanel = GameController.createButtonWithImage(plantImages.get(plant),null,plant,width,height                   
        );

        selectedPlantsMap.put(plant, plantImages.get(plant));

        addActionToSelectedPlantPanel(plant, selectedPlantPanel);

        selectedPlantsPanel.add(selectedPlantPanel);

        selectedPlantsPanel.revalidate();
        selectedPlantsPanel.repaint();
    }

    private void addActionToSelectedPlantPanel(String plant, JPanel selectedPlantPanel) {
        for (Component comp : selectedPlantPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(e -> removeFromSelectedPlants(plant, selectedPlantPanel));
            }
        }
    }

    private void removeFromSelectedPlants(String plant, JPanel selectedPlantPanel) {
        selectedPlantsMap.remove(plant);  

        JPanel plantPanel = plantButtons.get(plant);
        enablePlantPanel(plantPanel);

        selectedPlantsPanel.remove(selectedPlantPanel);
        selectedPlantsPanel.revalidate();
        selectedPlantsPanel.repaint();
    }

    private void disablePlantPanel(JPanel plantPanel) {
        for (Component comp : plantPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
            }
        }
    }

    private void enablePlantPanel(JPanel plantPanel) {
        for (Component comp : plantPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(true);
            }
        }
    }

    private void configureStartGameButton() {
        startGameButton.addActionListener(e -> {
            player.stopMusic();
            dispose();
            GridGUI gridGUI = new GridGUI(selectedPlantsMap);  
            gridGUI.setVisible(true);
            GameConfig.setIsNotPaused();
            System.out.print(GameConfig.getIsPaused());
            GameConfig.setStartGame();
        });
    }

    public void configureReturnButton() {
        returnButton.addActionListener(e -> {
            GameModeWindow gameModeWindow  = new GameModeWindow ();
            player.stopMusic();
            gameModeWindow.setVisible(true);
            dispose();
        });
    }
}
