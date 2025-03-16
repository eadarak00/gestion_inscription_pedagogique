package sn.uasz.m1.inscription.view.Etudiant;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.text.AbstractDocument.Content;

import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.components.NavbarPanel;
import sn.uasz.m1.inscription.view.components.NavbarStudent;

import java.awt.*;

public class HomeStudentUI extends JFrame {
    // Déclaration des couleurs
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF2F2F2);
    private final Color GRAY_COLOR = new Color(0xF1f1F1);

    private static final Font BOLD_FONT_18 = new Font("Poppins", Font.BOLD, 18);
    private static final Font BOLD_FONT_24 = new Font("Poppins", Font.BOLD, 20);
    private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private static final Font REGULAR_FONT_18 = new Font("Poppins", Font.PLAIN, 25);

    public HomeStudentUI() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setTitle("Accueil Étudiant");
        setSize(1500, 700);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        NavbarStudent navbar = new NavbarStudent(this);

        mainPanel.add(navbar, BorderLayout.NORTH);
        mainPanel.add(createRightPanel(), BorderLayout.EAST);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);
    }

    public void afficher() {
        this.setVisible(true);
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(650, 0));
        panel.setBackground(GRAY_COLOR);
        panel.setLayout(new BorderLayout()); // Utilisation de BorderLayout pour centrer l'image

        // Ajouter une marge à droite (par exemple, 20px)
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

        JLabel imgLabel = new JLabel(createIcon("src/main/resources/static/img/png/landing_5.png", 500, 500));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(imgLabel, BorderLayout.CENTER); // Ajouter l'image au centre du BorderLayout
        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT_24);
        button.setForeground(Color.WHITE);
        button.setBackground(VERT_COLOR_1);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(400, 50));

        // Ajouter un MouseListener pour les effets de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setPreferredSize(new Dimension(400, 60)); // Augmenter la taille
                button.setLocation(button.getX() + 5, button.getY() + 5); // Décaler légèrement
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setPreferredSize(new Dimension(400, 60)); // Rétablir la taille initiale
                button.setLocation(button.getX() - 5, button.getY() - 5); // Rétablir la position
                button.repaint();
            }
        });

        return button;
    }

    private JPanel createContentPanel() {
        // Créer un JPanel avec un GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 0, 10)); // 3 lignes, 1 colonne, espacement vertical de 10px entre les
                                                      // composants
        panel.setBackground(BG_COLOR); // Couleur de fond pour le panel principal
        panel.setBorder(BorderFactory.createEmptyBorder(70, 40, 0, 40)); // Marges autour du contenu

        // Récupérer le nom de l'utilisateur connecté à partir de la session
        Utilisateur user = SessionManager.getUtilisateur(); 
        String username = "INCONNU";
        
        if(user!=null){
           username = user.getPrenom() + " " + user.getNom();
        }

        // Créer et configurer le label de bienvenue
        JLabel welcomeLabel = new JLabel("Bienvenue cher(e) " + username + " ,");
        welcomeLabel.setFont(REGULAR_FONT_18);
        welcomeLabel.setForeground(new Color(0x37474F)); // Couleur du texte
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrer horizontalement

        // Créer un panel pour les boutons
        JPanel buttons = createButtonsPanel();

        // Ajouter le welcomeLabel et les boutons au panel dans un style "grille"
        panel.add(welcomeLabel); // Ajouter le label de bienvenue
        panel.add(buttons); // Ajouter le panel des boutons

        return panel;
    }

    private JPanel createButtonsPanel() {
        // Créer un JPanel avec un BoxLayout vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Empile les composants verticalement
        panel.setBackground(BG_COLOR); // Fond du panneau
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajouter des marges au panel

        // Créer les boutons avec la méthode createButton
        JButton inscriptionButton = createButton("Inscription Pédagogique");
        JButton buttonTD = createButton("Mon Groupe TD");
        JButton buttonTP = createButton("Mon Groupe TP");

        inscriptionButton.addActionListener(e -> NavigateToInscription());

        // Ajouter les boutons au panneau avec un espacement vertical
        panel.add(inscriptionButton);
        panel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons
        panel.add(buttonTD);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonTP);

        // Centrer les boutons horizontalement
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement

        return panel;
    }

    private ImageIcon createIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public void fermer(){
        this.dispose();
    }


    private void NavigateToInscription() {
        try {
            InscriptionUI home = new InscriptionUI();
            home.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // Lancer l'interface utilisateur avec le nom de l'utilisateur
        SwingUtilities.invokeLater(() -> new HomeStudentUI().setVisible(true));
    }
}
