// package sn.uasz.m1.inscription.view.Etudiant;

// import javax.swing.*;
// import java.awt.*;

// public class StepIndicator extends JPanel {

//     // 🎨 Déclaration des couleurs
//     private final Color VERT_COLOR_1 = new Color(0x113F36);
//     private final Color VERT_COLOR_2 = new Color(0x128E64);
//     private final Color BG_COLOR = new Color(0xF2F2F2);
//     private final Color GRAY_COLOR = new Color(0xF1F1F1);

//     // 🖋 Déclaration des polices
//     private static final Font BOLD_FONT_18 = new Font("Poppins", Font.BOLD, 20);

//     private int currentStep;

//     public StepIndicator(int currentStep) {
//         this.currentStep = currentStep;
//         setPreferredSize(new Dimension(250, 50)); // Taille du panneau
//         setBackground(BG_COLOR);
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         Graphics2D g2d = (Graphics2D) g;
//         g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//         int radius = 35; // Taille des cercles
//         int y = 10; // Position verticale des cercles
//         int x1 = 50; // Position du premier cercle
//         int x2 = x1 + 100; // Position du deuxième cercle
//         int arrowX = x1 + radius + 10; // Position de la flèche

//         // 🎨 Couleurs
//         Color activeColor = new Color(0x128E64); // Vert pour l'étape active
//         Color inactiveColor = new Color(0xAAAAAA); // Gris pour l'étape inactive

//         // 🔵 Dessiner le premier cercle (toujours actif)
//         g2d.setColor(activeColor);
//         g2d.fillOval(x1, y, radius, radius);

//         // 🔢 Écriture du "1" bien centré
//         g2d.setFont(BOLD_FONT_18);
//         g2d.setColor(Color.WHITE);
//         drawCenteredText(g2d, "1", x1, y, radius);

//         if (currentStep == 2) {
//             // ➡ Dessiner la flèche
//             g2d.setColor(inactiveColor);
//             g2d.drawLine(arrowX, y + radius / 2, arrowX + 30, y + radius / 2);
//             g2d.fillPolygon(new int[]{arrowX + 30, arrowX + 25, arrowX + 25},
//                     new int[]{y + radius / 2, y + radius / 2 - 5, y + radius / 2 + 5}, 3);

//             // 🔵 Dessiner le deuxième cercle (actif en Step 2)
//             g2d.setColor(activeColor);
//         } else {
//             g2d.setColor(inactiveColor);
//         }
//         g2d.fillOval(x2, y, radius, radius);

//         // 🔢 Écriture du "2" bien centré
//         g2d.setColor(Color.WHITE);
//         drawCenteredText(g2d, "2", x2, y, radius);
//     }

//     /**
//      * Centre le texte dans un cercle
//      */
//     private void drawCenteredText(Graphics2D g2d, String text, int x, int y, int radius) {
//         FontMetrics fm = g2d.getFontMetrics();
//         int textWidth = fm.stringWidth(text);
//         int textHeight = fm.getAscent();
//         int centerX = x + (radius - textWidth) / 2;
//         int centerY = y + (radius + textHeight) / 2 - 5; // Ajustement vertical
//         g2d.drawString(text, centerX, centerY);
//     }
// }


package sn.uasz.m1.inscription.view.Etudiant;

import javax.swing.*;
import java.awt.*;

public class StepIndicator extends JPanel {

    // 🎨 Déclaration des couleurs
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF2F2F2);
    private final Color GRAY_COLOR = new Color(0xF1F1F1);

    // 🖋 Déclaration des polices
    private static final Font BOLD_FONT_18 = new Font("Poppins", Font.BOLD, 20);

    private int currentStep;

    public StepIndicator(int currentStep) {
        this.currentStep = currentStep;
        setPreferredSize(new Dimension(400, 50)); // Augmenter la taille du panneau pour le 3ème cercle
        setBackground(BG_COLOR);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int radius = 35; // Taille des cercles
        int y = 10; // Position verticale des cercles
        int x1 = 50; // Position du premier cercle
        int x2 = x1 + 100; // Position du deuxième cercle
        int x3 = x2 + 100; // Position du troisième cercle
        int arrowX1 = x1 + radius + 10; // Position de la première flèche
        int arrowX2 = x2 + radius + 10; // Position de la deuxième flèche

        // 🎨 Couleurs
        Color activeColor = new Color(0x128E64); // Vert pour l'étape active
        Color inactiveColor = new Color(0xAAAAAA); // Gris pour l'étape inactive

        // 🔵 Dessiner le premier cercle (toujours actif)
        g2d.setColor(activeColor);
        g2d.fillOval(x1, y, radius, radius);

        // 🔢 Écriture du "1" bien centré
        g2d.setFont(BOLD_FONT_18);
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, "1", x1, y, radius);

        if (currentStep >= 2) {
            // ➡ Dessiner la flèche vers le deuxième cercle
            g2d.setColor(inactiveColor);
            g2d.drawLine(arrowX1, y + radius / 2, arrowX1 + 30, y + radius / 2);
            g2d.fillPolygon(new int[]{arrowX1 + 30, arrowX1 + 25, arrowX1 + 25},
                    new int[]{y + radius / 2, y + radius / 2 - 5, y + radius / 2 + 5}, 3);

            // 🔵 Dessiner le deuxième cercle (actif si currentStep >= 2)
            g2d.setColor(currentStep >= 2 ? activeColor : inactiveColor);
        } else {
            g2d.setColor(inactiveColor);
        }
        g2d.fillOval(x2, y, radius, radius);

        // 🔢 Écriture du "2" bien centré
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, "2", x2, y, radius);

        if (currentStep >= 3) {
            // ➡ Dessiner la flèche vers le troisième cercle
            g2d.setColor(inactiveColor);
            g2d.drawLine(arrowX2, y + radius / 2, arrowX2 + 30, y + radius / 2);
            g2d.fillPolygon(new int[]{arrowX2 + 30, arrowX2 + 25, arrowX2 + 25},
                    new int[]{y + radius / 2, y + radius / 2 - 5, y + radius / 2 + 5}, 3);

            // 🔵 Dessiner le troisième cercle (actif si currentStep >= 3)
            g2d.setColor(currentStep >= 3 ? activeColor : inactiveColor);
        } else {
            g2d.setColor(inactiveColor);
        }
        g2d.fillOval(x3, y, radius, radius);

        // 🔢 Écriture du "3" bien centré
        g2d.setColor(Color.WHITE);
        drawCenteredText(g2d, "3", x3, y, radius);
    }

    /**
     * Centre le texte dans un cercle
     */
    private void drawCenteredText(Graphics2D g2d, String text, int x, int y, int radius) {
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int centerX = x + (radius - textWidth) / 2;
        int centerY = y + (radius + textHeight) / 2 - 5; // Ajustement vertical
        g2d.drawString(text, centerX, centerY);
    }
}

