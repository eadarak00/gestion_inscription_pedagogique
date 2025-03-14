package sn.uasz.m1.inscription.view.components;

import javax.swing.ImageIcon;


import javax.swing.*;
import java.awt.*;

public class IconUI {
    /**
     * Crée une icône redimensionnée à partir d'une image.
     *
     * @param path  Chemin de l'image
     * @param width Largeur souhaitée
     * @param height Hauteur souhaitée
     * @return ImageIcon redimensionnée
     */
    public static ImageIcon createIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
