package sn.uasz.m1.inscription.view.components;

import javax.swing.*;
import java.awt.*;

public class NavbarPanel extends JPanel {

    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color TEXT_COLOR = Color.WHITE; // Couleur du texte du menu

    private static final Font BOLD_FONT_18 = new Font("Poppins", Font.BOLD, 18);
    private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);

    public NavbarPanel(JFrame parentFrame) {
        setLayout(new BorderLayout());
        setBackground(VERT_COLOR_1);
        setPreferredSize(new Dimension(parentFrame.getWidth(), 75));

        // === Label Menu Hamburger ===
        JLabel menuLabel = new JLabel(IconUI.createIcon("static/img/png/hamburger.png", 30, 30));
        menuLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuLabel.setOpaque(false);
        menuLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Menu déroulant ===
        JPopupMenu menuPopup = new JPopupMenu();
        menuPopup.setBackground(VERT_COLOR_1);
        
        JMenuItem menuItem1 = new JMenuItem("Profil");
        JMenuItem menuItem2 = new JMenuItem("Déconnexion");

        // Appliquer le style au menu
        customizeMenuItem(menuItem1);
        customizeMenuItem(menuItem2);

        menuPopup.add(menuItem1);
        menuPopup.add(menuItem2);

        // Action pour afficher le menu lors du clic sur le label
        menuLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPopup.show(menuLabel, 0, menuLabel.getHeight());
            }
        });

        // === Ajout des composants à la Navbar ===
        add(createLogoPanel(), BorderLayout.WEST);
        add(menuLabel, BorderLayout.EAST);
    }

    private void customizeMenuItem(JMenuItem item) {
        item.setFont(REGULAR_FONT);
        item.setForeground(TEXT_COLOR);
        item.setBackground(VERT_COLOR_1);
        item.setOpaque(true);
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        logoPanel.setOpaque(false); // Fond transparent

        // Logo
        JLabel logoLabel = new JLabel(IconUI.createIcon("static/img/png/logo_uasz.png", 70, 70));

        // Nom de l’université
        JLabel universityNameLabel = new JLabel("Université Assane Seck de Ziguinchor");
        universityNameLabel.setForeground(Color.WHITE);
        universityNameLabel.setFont(BOLD_FONT_18);

        logoPanel.add(logoLabel);
        logoPanel.add(universityNameLabel);
        return logoPanel;
    }
}
