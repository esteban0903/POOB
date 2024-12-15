package Presentation;

import Dominio.GameConfig;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * A window for selecting characters or plants from available options before starting a game.
 */

public class CharacterSelectionWindow extends Window {
    AudioPlayer player = new AudioPlayer("resources/Music/SoundTrack/characterSelect.wav");
    private JPanel availablePlantsPanel;
    private JPanel selectedPlantsPanel;
    private final Map<String, JPanel> plantButtons; 
    private final Map<String, String> plantImages;  
    private final Map<String, String> selectedPlantsMap;  
    private Color green = new Color(34, 139, 34); //color verde de boton
    private JButton startGameButton = AssistantGraphic.createButton("Iniciar Juego", 250, 640, 200, 40, green, Color.WHITE);
    private JButton returnButton = AssistantGraphic.createButton("Volver", 850, 640, 200, 40, green, Color.WHITE);
    public static void main(String[] args) {
        new CharacterSelectionWindow();
    }

    /**
     * Constructs the character selection window, initializing UI components and layout.
     */

    public CharacterSelectionWindow() {
        super("Selección de Plantas", "resources/Backgrounds/characterSelection.jpg");

        plantButtons = new HashMap<>();
        plantImages = new HashMap<>();
        plantImages.put("Peashooter", "resources/Characters/Peashooter.png"); // para probar algunas plantas 
        plantImages.put("Sunflower", "resources/Characters/Sunflower.png");
        plantImages.put("Wallnut", "resources/Characters/Wallnut.png");
        plantImages.put("EciPlant", "resources/Characters/EciPlant.png");
        plantImages.put("Evolve", "resources/Characters/Evolve.png");

    
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

    /**
     * Initializes panels for displaying available and selected plants.
     */

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

    /**
     * Adds plant options to the available plants panel.
     */

    private void addAvailablePlants() {
        for (String plant : plantImages.keySet()) {
            int width = 30;  
            int height = 30; 

            JPanel plantPanel = AssistantGraphic.createButtonWithImage(plantImages.get(plant),null,plant,width,height);

            addActionToPlantPanel(plant, plantPanel);
            plantButtons.put(plant, plantPanel);
            availablePlantsPanel.add(plantPanel);
        }
    }

    /**
     * Configures the action listeners for the plant selection panels. When a plant panel is clicked, the corresponding plant is added to the selected plants panel.
     *
     * @param plant The name of the plant associated with the plant panel.
     * @param plantPanel The JPanel containing the plant button.
     */

    private void addActionToPlantPanel(String plant, JPanel plantPanel) {
        for (Component comp : plantPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(e -> addToSelectedPlants(plant, plantPanel));
            }
        }
    }

    /**
     * Adds a selected plant to the selected plants panel.
     *
     * @param plant The name of the plant to be added.
     * @param plantPanel The JPanel containing the plant button.
     */
    private void addToSelectedPlants(String plant, JPanel plantPanel) {

        disablePlantPanel(plantPanel);
        int width = 24;  
        int height = 25; 

        JPanel selectedPlantPanel = AssistantGraphic.createButtonWithImage(plantImages.get(plant),null,plant,width,height                   
        );

        selectedPlantsMap.put(plant, plantImages.get(plant));

        addActionToSelectedPlantPanel(plant, selectedPlantPanel);

        selectedPlantsPanel.add(selectedPlantPanel);

        selectedPlantsPanel.revalidate();
        selectedPlantsPanel.repaint();
    }

    /**
     * Configures the action listeners for the selected plant panels. When a selected plant panel is clicked, the corresponding plant is removed from the selected plants panel.
     *
     * @param plant The name of the plant associated with the selected plant panel.
     * @param selectedPlantPanel The JPanel containing the selected plant button.
     */
    private void addActionToSelectedPlantPanel(String plant, JPanel selectedPlantPanel) {
        for (Component comp : selectedPlantPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addActionListener(e -> removeFromSelectedPlants(plant, selectedPlantPanel));
            }
        }
    }

    /**
     * Removes a selected plant from the selection panel and re-enables its corresponding button in the available plants panel.
     *
     * @param plant The identifier for the plant to remove.
     * @param selectedPlantPanel The JPanel from which the plant is being removed.
     */

    private void removeFromSelectedPlants(String plant, JPanel selectedPlantPanel) {
        selectedPlantsMap.remove(plant);  

        JPanel plantPanel = plantButtons.get(plant);
        enablePlantPanel(plantPanel);

        selectedPlantsPanel.remove(selectedPlantPanel);
        selectedPlantsPanel.revalidate();
        selectedPlantsPanel.repaint();
    }

    /**
     * Disables all buttons within a given plant panel, preventing further interactions.
     *
     * @param plantPanel The JPanel whose buttons will be disabled.
     */

    private void disablePlantPanel(JPanel plantPanel) {
        for (Component comp : plantPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
            }
        }
    }

    /**
     * Enables all buttons within a given plant panel, allowing interactions.
     *
     * @param plantPanel The JPanel whose buttons will be enabled.
     */

    private void enablePlantPanel(JPanel plantPanel) {
        for (Component comp : plantPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(true);
            }
        }
    }

    /**
     * Configures the start game button with an action listener that stops music, disposes of the current window, and opens the game grid UI.
     */

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

    /**
     * Configures the return button to navigate back to the initial window and stop the current window's music.
     */
    
    public void configureReturnButton() {
        returnButton.addActionListener(e -> {
            NameInputWindow nameInputWindow  = new NameInputWindow ();
            player.stopMusic();
            nameInputWindow.setVisible(true);
            dispose();
        });
    }
}
