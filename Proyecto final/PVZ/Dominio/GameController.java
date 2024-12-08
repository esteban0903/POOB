package Dominio;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class GameController {

    public static void saveGame(Grid grid, Map<String, String> characterTypes) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Juego Como");
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (FileOutputStream fileOut = new FileOutputStream(file);
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(grid);
                out.writeObject(characterTypes);
                JOptionPane.showMessageDialog(null, "Juego guardado con exito en " + file.getPath());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el juego: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    public static Object[] loadGame() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Abrir Juego Guardado");
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream in = new ObjectInputStream(fileIn)) {
                Grid grid = (Grid) in.readObject();
                Map<String, String> characterTypes = (Map<String, String>) in.readObject();
                return new Object[] {grid, characterTypes};
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar el juego: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
}
