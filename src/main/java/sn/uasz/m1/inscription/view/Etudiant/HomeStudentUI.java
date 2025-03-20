package sn.uasz.m1.inscription.view.Etudiant;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;


import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.components.IconUI;
import sn.uasz.m1.inscription.view.components.NavbarStudent;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        JLabel imgLabel = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/landing_5.png", 500, 500));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(imgLabel, BorderLayout.CENTER); 
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
                button.setPreferredSize(new Dimension(400, 60)); 
                button.setLocation(button.getX() + 5, button.getY() + 5); 
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

    // private JPanel createContentPanel() {
    //     // Créer un JPanel avec un GridLayout
    //     JPanel panel = new JPanel();
    //     panel.setLayout(new GridLayout(3, 1, 0, 10)); // 3 lignes, 1 colonne, espacement vertical de 10px entre les
    //                                                   // composants
    //     panel.setBackground(BG_COLOR); // Couleur de fond pour le panel principal
    //     panel.setBorder(BorderFactory.createEmptyBorder(70, 40, 0, 40)); // Marges autour du contenu

    //     // Récupérer le nom de l'utilisateur connecté à partir de la session
    //     Utilisateur user = SessionManager.getUtilisateur(); 
    //     String username = "INCONNU";
        
    //     if(user!=null){
    //        username = user.getPrenom() + " " + user.getNom();
    //     }

    //     // Créer et configurer le label de bienvenue
    //     JLabel welcomeLabel = new JLabel("Bienvenue cher(e) " + username + " ,");
    //     welcomeLabel.setFont(REGULAR_FONT_18);
    //     welcomeLabel.setForeground(new Color(0x37474F)); // Couleur du texte
    //     welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrer horizontalement

    //     // Créer un panel pour les boutons
    //     JPanel buttons = createButtonsPanel();

    //     // Ajouter le welcomeLabel et les boutons au panel dans un style "grille"
    //     panel.add(welcomeLabel); // Ajouter le label de bienvenue
    //     panel.add(buttons); // Ajouter le panel des boutons

    //     return panel;
    // }

    // private JPanel createButtonsPanel() {
    //     // Créer un JPanel avec un BoxLayout vertical
    //     JPanel panel = new JPanel();
    //     panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Empile les composants verticalement
    //     panel.setBackground(BG_COLOR); // Fond du panneau
    //     panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Ajouter des marges au panel

    //     // Créer les boutons avec la méthode createButton
    //     JButton inscriptionButton = createButton("Inscription Pédagogique");
    //     JButton buttonTD = createButton("Mon Groupe TD");
    //     JButton buttonTP = createButton("Mon Groupe TP");

    //     inscriptionButton.addActionListener(e -> NavigateToInscription());

    //     // Ajouter les boutons au panneau avec un espacement vertical
    //     panel.add(inscriptionButton);
    //     panel.add(Box.createVerticalStrut(10)); // Espacement entre les boutons
    //     panel.add(buttonTD);
    //     panel.add(Box.createVerticalStrut(10));
    //     panel.add(buttonTP);

    //     // Centrer les boutons horizontalement
    //     panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrer horizontalement

    //     return panel;
    // }


    private JPanel createContentPanel() {
    // Créer un panel principal avec BorderLayout
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(BG_COLOR);
    panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

    // Panel central avec BoxLayout
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBackground(Color.WHITE);
    centerPanel.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(10, new Color(0, 0, 0, 20), 5),
        BorderFactory.createEmptyBorder(25, 30, 25, 30)
    ));

    // Récupérer le nom de l'utilisateur connecté
    Utilisateur user = SessionManager.getUtilisateur();
    String username = "INCONNU";
    
    if (user != null) {
        username = user.getPrenom() + " " + user.getNom();
    }

    // En-tête avec avatar et message de bienvenue
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
    headerPanel.setBackground(Color.WHITE);
    headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

    // Avatar de l'utilisateur
    JPanel avatarPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Dessiner un cercle comme avatar
            g2d.setColor(VERT_COLOR_2);
            g2d.fillOval(0, 0, getWidth(), getHeight());
            
            // Initiales de l'utilisateur
            Utilisateur user = SessionManager.getUtilisateur(); 
                String username = "INCONNU";
                
                if(user!=null){
                   username = user.getPrenom() + " " + user.getNom();
                }
            String initials = "?";
            if (!username.equals("INCONNU")) {
                String[] nameParts = username.split(" ");
                if (nameParts.length > 0) {
                    initials = String.valueOf(nameParts[0].charAt(0));
                    if (nameParts.length > 1) {
                        initials += String.valueOf(nameParts[1].charAt(0));
                    }
                }
            }
            
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Poppins", Font.BOLD, 20));
            FontMetrics metrics = g2d.getFontMetrics();
            int stringWidth = metrics.stringWidth(initials);
            int stringHeight = metrics.getHeight();
            int stringAscent = metrics.getAscent();
            
            // Centrer le texte dans le cercle
            int x = (getWidth() - stringWidth) / 2;
            int y = (getHeight() - stringHeight) / 2 + stringAscent;
            g2d.drawString(initials, x, y);
            
            g2d.dispose();
        }
    };
    avatarPanel.setPreferredSize(new Dimension(60, 60));
    avatarPanel.setMaximumSize(new Dimension(60, 60));
    avatarPanel.setMinimumSize(new Dimension(60, 60));
    avatarPanel.setOpaque(false);
    
    // Panel pour le message de bienvenue avec informations utilisateur
    JPanel welcomeTextPanel = new JPanel();
    welcomeTextPanel.setLayout(new BoxLayout(welcomeTextPanel, BoxLayout.Y_AXIS));
    welcomeTextPanel.setBackground(Color.WHITE);
    welcomeTextPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
    
    JLabel greetingLabel = new JLabel("Bonjour,");
    greetingLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
    greetingLabel.setForeground(new Color(0x666666));
    greetingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    JLabel nameLabel = new JLabel(username);
    nameLabel.setFont(new Font("Poppins", Font.BOLD, 22));
    nameLabel.setForeground(new Color(0x333333));
    nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    welcomeTextPanel.add(greetingLabel);
    welcomeTextPanel.add(Box.createVerticalStrut(2));
    welcomeTextPanel.add(nameLabel);
    
    headerPanel.add(avatarPanel);
    headerPanel.add(welcomeTextPanel);
    headerPanel.add(Box.createHorizontalGlue());
    
    // Ajouter la date du jour
    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.FRANCE);
    String currentDate = dateFormat.format(new Date());
    JLabel dateLabel = new JLabel(currentDate);
    dateLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
    dateLabel.setForeground(new Color(0x666666));
    headerPanel.add(dateLabel);
    
    centerPanel.add(headerPanel);
    centerPanel.add(Box.createVerticalStrut(25));
    
    // Séparateur élégant
    JSeparator separator = new JSeparator();
    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    separator.setForeground(new Color(0xEEEEEE));
    centerPanel.add(separator);
    centerPanel.add(Box.createVerticalStrut(25));
    
    // Section titre pour les actions rapides
    JLabel actionsTitle = new JLabel("Actions rapides");
    actionsTitle.setFont(new Font("Poppins", Font.BOLD, 18));
    actionsTitle.setForeground(new Color(0x333333));
    actionsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
    centerPanel.add(actionsTitle);
    centerPanel.add(Box.createVerticalStrut(15));
    
    // Panneau de boutons modernisés
    JPanel buttonsPanel = createModernButtonsPanel();
    buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    centerPanel.add(buttonsPanel);
    
    // Ajouter le panel central au panel principal
    panel.add(centerPanel, BorderLayout.CENTER);
    
    return panel;
}

private JPanel createModernButtonsPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new GridLayout(1, 3, 15, 0)); // Disposition en ligne avec espacement horizontal
    panel.setBackground(Color.WHITE);
    
    // Créer les cartes d'action au lieu de simples boutons
    JPanel inscriptionCard = createActionCard("Inscription Pédagogique", "document-text", e -> NavigateToInscription());
    JPanel tdCard = createActionCard("Mon Groupe TD", "users", null);
    JPanel tpCard = createActionCard("Mon Groupe TP", "code", null);
    
    panel.add(inscriptionCard);
    panel.add(tdCard);
    panel.add(tpCard);
    
    return panel;
}

private JPanel createActionCard(String title, String iconType, ActionListener action) {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(Color.WHITE);
    card.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(8, new Color(0, 0, 0, 10), 3),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    ));
    
    // Créer une icône représentative
    JPanel iconPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Fond circulaire léger
            g2d.setColor(new Color(VERT_COLOR_2.getRed(), VERT_COLOR_2.getGreen(), VERT_COLOR_2.getBlue(), 30));
            g2d.fillOval(0, 0, 50, 50);
            
            // Dessiner l'icône (simplifiée pour cet exemple)
            g2d.setColor(VERT_COLOR_2);
            g2d.setStroke(new BasicStroke(2));
            
            // Dessiner une icône simple basée sur le type
            switch (iconType) {
                case "document-text":
                    // Document avec ligne
                    g2d.drawRect(15, 10, 20, 30);
                    g2d.drawLine(20, 20, 30, 20);
                    g2d.drawLine(20, 25, 30, 25);
                    g2d.drawLine(20, 30, 25, 30);
                    break;
                case "users":
                    // Icône de groupe
                    g2d.drawOval(15, 10, 10, 10); // Tête 1
                    g2d.drawLine(20, 20, 20, 30); // Corps 1
                    g2d.drawOval(25, 10, 10, 10); // Tête 2
                    g2d.drawLine(30, 20, 30, 30); // Corps 2
                    break;
                case "code":
                    // Code symbol (< >)
                    g2d.drawString("</>", 17, 30);
                    break;
                default:
                    // Icône par défaut
                    g2d.drawRoundRect(15, 15, 20, 20, 5, 5);
            }
            
            g2d.dispose();
        }
    };
    
    iconPanel.setPreferredSize(new Dimension(50, 50));
    iconPanel.setMaximumSize(new Dimension(50, 50));
    iconPanel.setMinimumSize(new Dimension(50, 50));
    iconPanel.setOpaque(false);
    iconPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Titre de l'action
    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Poppins", Font.BOLD, 14));
    titleLabel.setForeground(new Color(0x333333));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    // Espace entre l'icône et le titre
    card.add(iconPanel);
    card.add(Box.createVerticalStrut(10));
    card.add(titleLabel);
    
    // Ajouter un peu d'espace flexible
    card.add(Box.createVerticalGlue());
    
    // Ajouter un bouton "Plus d'infos"
    JLabel infoLabel = new JLabel("En savoir plus >");
    infoLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
    infoLabel.setForeground(VERT_COLOR_2);
    infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    card.add(Box.createVerticalStrut(5));
    card.add(infoLabel);
    
    // Rendre la carte cliquable
    card.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    // Effet de hover
    MouseAdapter hoverAdapter = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            card.setBackground(new Color(0xF5F9FF));
            titleLabel.setForeground(VERT_COLOR_2);
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            card.setBackground(Color.WHITE);
            titleLabel.setForeground(new Color(0x333333));
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            if (action != null) {
                action.actionPerformed(new ActionEvent(e.getSource(), ActionEvent.ACTION_PERFORMED, "click"));
            }
        }
    };
    
    card.addMouseListener(hoverAdapter);
    
    return card;
}

// Classe pour créer des bordures arrondies avec ombre
class RoundedBorder extends AbstractBorder {
    private int radius;
    private Color shadowColor;
    private int shadowSize;
    
    public RoundedBorder(int radius, Color shadowColor, int shadowSize) {
        this.radius = radius;
        this.shadowColor = shadowColor;
        this.shadowSize = shadowSize;
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dessiner l'ombre
        for (int i = 0; i < shadowSize; i++) {
            g2d.setColor(new Color(
                shadowColor.getRed(), 
                shadowColor.getGreen(), 
                shadowColor.getBlue(), 
                shadowColor.getAlpha() * (shadowSize - i) / shadowSize));
            g2d.drawRoundRect(
                x + i, 
                y + i, 
                width - 1 - (i * 2), 
                height - 1 - (i * 2), 
                radius, 
                radius);
        }
        
        // Dessiner la bordure principale
        g2d.setColor(new Color(0xDDDDDD));
        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        
        g2d.dispose();
    }
    
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(shadowSize, shadowSize, shadowSize * 2, shadowSize * 2);
    }
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
