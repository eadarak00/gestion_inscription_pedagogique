package sn.uasz.m1.inscription.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class HomeUI  extends JFrame{
    private Color vertColor1 = new Color(0x113F36);
    private Color vertColor2 = new Color (0x128E64);
    private Color fondColor = new Color(0xF5F5F0);
    private Color bColor = new Color(0x151d21);
    private SignInStudentUI signInStudentUI;
    public HomeUI(){
        signInStudentUI = new SignInStudentUI();
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Gestion des Inscriptions Pedagogique");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setBackground(fondColor);

        mainPanel.add(leftPanel(), BorderLayout.WEST);
        mainPanel.add(centerPanel(), BorderLayout.CENTER);
    }

    private JPanel leftPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 0));
        panel.setBackground(fondColor);
        
        //ajout de l'icone 
        ImageIcon originalIcon = new ImageIcon("src/main/resources/static/img/png/test.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel(resizedIcon);

        panel.add(label, BorderLayout.CENTER);
    
        return panel;
    }

    // private JPanel centerPanel() {
    //     JPanel panel = new JPanel();
    //     panel.setLayout(new GridBagLayout());
    //     panel.setBackground(fondColor);
    
    //     GridBagConstraints constraints = new GridBagConstraints();
    //     constraints.insets = new Insets(15, 20, 15, 20);  
    //     constraints.anchor = GridBagConstraints.CENTER;  // Centrer les composants dans le GridBagLayout
    //     constraints.gridwidth = 1;
    
    //     // Création du panel pour le titre et le paragraphe
    //     JPanel textPanel = new JPanel();
    //     textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Arrange les composants verticalement
    //     textPanel.setBackground(fondColor);
    
    //     // Titre
    //     JLabel sectionTitle = new JLabel("<html><span style='font-size:20px; font-weight:bold;'>Bienvenue dans votre espace d’inscription pédagogique</span></html>");
    //     sectionTitle.setFont(new Font("Poppins", Font.BOLD, 20));  
    //     sectionTitle.setForeground(vertColor2);  
    //     textPanel.add(sectionTitle);
    
    //     // Paragraphe avec des côtes
    //     JLabel paragraph = new JLabel("<html><div style='border-left: 4px solid " + vertColor2 + "; padding-left: 10px; margin-left: 20px; font-size: 14px;'>"
    //             + "\" Là où vos inscriptions pédagogiques se font en toute simplicité !\"</div></html>");
    //     paragraph.setFont(new Font("Poppins", Font.PLAIN, 14)); 
    //     paragraph.setForeground(new Color(0x5e5e5e));
    //     textPanel.add(paragraph);
    
    //     // Ajouter le textPanel au panel principal
    //     constraints.gridx = 0;
    //     constraints.gridy = 0;
    //     panel.add(textPanel, constraints);
    
    //     // Créer un panel pour les boutons avec un FlowLayout centré
    //     JPanel buttonPanel = new JPanel();
    //     buttonPanel.setBackground(fondColor);
    //     buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));  // Aligne les boutons au centre avec espacement de 20px horizontal et 10px vertical
    
    //     // Créer les boutons pour Responsable Pédagogique et Étudiant
    //     JButton button1 = new JButton("Responsable Pédagogique");
    //     button1.setFont(new Font("Poppins", Font.BOLD, 14));
    //     button1.setBackground(bColor);
    //     button1.setForeground(Color.WHITE);
    //     button1.addActionListener( e -> navigateToLogin());
    //     button1.setPreferredSize(new Dimension(250, 40));
    
    //     JButton button2 = new JButton("Étudiant");
    //     button2.setFont(new Font("Poppins", Font.BOLD, 14));
    //     button2.setBackground(vertColor1);
    //     button2.setForeground(Color.WHITE);
    //     button2.setPreferredSize(new Dimension(250, 40));
    //     button2.addActionListener( e -> {
    //         signInStudentUI.afficher();
    //         fermer();
    //     });
    
    //     // Ajouter les boutons au panel des boutons
    //     buttonPanel.add(button1);
    //     buttonPanel.add(button2);
    
    //     // Ajouter le panel des boutons au panel principal
    //     constraints.gridx = 0;
    //     constraints.gridy = 1;
    //     panel.add(buttonPanel, constraints);
    
    //     return panel;
    // }
    private JPanel centerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(fondColor);
        
        // Ajout d'un léger effet d'ombre au panel principal
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(new Color(0xE0E0E0), 1, true)
        ));
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(25, 30, 25, 30);  // Augmentation des marges
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.HORIZONTAL;  // Permet aux composants de s'étendre horizontalement
        constraints.gridwidth = 1;
        
        // Panel pour le titre et le paragraphe avec effet de carte
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(fondColor);
        textPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 20, 15));
        
        // Titre avec police moderne et taille plus grande
        JLabel sectionTitle = new JLabel("<html><span style='font-size:24px; font-weight:bold; text-align:center'>Bienvenue dans votre espace d'inscription pédagogique</span></html>");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));  // Police moderne
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sectionTitle.setForeground(vertColor2);
        textPanel.add(sectionTitle);
        
        // Espace entre le titre et la citation
        textPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Citation stylisée avec une bordure plus élégante
        JLabel paragraph = new JLabel("<html><div style='border-left: 4px solid " + vertColor2 
                + "; padding: 12px 15px; margin: 5px 0; font-style: italic; font-size: 16px; background-color: #f8f8f8; border-radius: 4px;'>"
                + "\" Là où vos inscriptions pédagogiques se font en toute simplicité !\"</div></html>");
        paragraph.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        paragraph.setAlignmentX(Component.CENTER_ALIGNMENT);
        paragraph.setForeground(new Color(0x424242));
        textPanel.add(paragraph);
        
        // Ajout du textPanel au panel principal
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        panel.add(textPanel, constraints);
        
        // Espace vertical entre les sections
        constraints.gridy = 1;
        constraints.insets = new Insets(10, 0, 10, 0);
        panel.add(Box.createRigidArea(new Dimension(0, 20)), constraints);
        
        // Panel pour les boutons avec style moderne
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(fondColor);
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 0));  // Disposition en grille avec espacement
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        
        // Bouton Responsable Pédagogique avec style moderne
        JButton button1 = createModernButton("Responsable Pédagogique", bColor, e -> navigateToLogin());
        
        // Bouton Étudiant avec style moderne
        JButton button2 = createModernButton("Étudiant", vertColor1, e -> {
            signInStudentUI.afficher();
            fermer();
        });
        
        // Ajout des boutons au panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        
        // Ajout du panel de boutons au panel principal
        constraints.gridy = 2;
        constraints.insets = new Insets(10, 30, 30, 30);
        panel.add(buttonPanel, constraints);
        
        return panel;
    }
    
    // Méthode helper pour créer des boutons modernes et uniformes
    private JButton createModernButton(String text, Color bgColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);  // Supprime le contour de focus
        button.setBorderPainted(false); // Supprime la bordure standard
        button.setContentAreaFilled(true);
        button.setMargin(new Insets(12, 15, 12, 15));
        
        // Ajouter des coins arrondis et un effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(
                        Math.max((int)(bgColor.getRed() * 0.9), 0),
                        Math.max((int)(bgColor.getGreen() * 0.9), 0),
                        Math.max((int)(bgColor.getBlue() * 0.9), 0)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        button.addActionListener(action);
        return button;
    }
    
    public void fermer(){
        this.dispose();
    }    

    public void afficher(){
        this.setVisible(true);
    }

    public void navigateToLogin(){
        try {
            LoginUI login = new LoginUI();
            login.afficher();
            fermer();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
     

}
