package sn.uasz.m1.inscription.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class HomeUI  extends JFrame{
    private Color vertColor1 = new Color(0x113F36);
    private Color vertColor2 = new Color (0x0d996c);
    private Color fondColor = new Color(0xF5F5F0);
    private Color bColor = new Color(0x151d21);
    public HomeUI(){
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Gestion des Inscriptions Pedagogique");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    private JPanel centerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(fondColor);
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 20, 15, 20);  
        constraints.anchor = GridBagConstraints.CENTER;  // Centrer les composants dans le GridBagLayout
        constraints.gridwidth = 1;
    
        // Création du panel pour le titre et le paragraphe
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Arrange les composants verticalement
        textPanel.setBackground(fondColor);
    
        // Titre
        JLabel sectionTitle = new JLabel("<html><span style='font-size:20px; font-weight:bold;'>Bienvenue dans votre espace d’inscription pédagogique</span></html>");
        sectionTitle.setFont(new Font("Poppins", Font.BOLD, 20));  
        sectionTitle.setForeground(vertColor2);  
        textPanel.add(sectionTitle);
    
        // Paragraphe avec des côtes
        JLabel paragraph = new JLabel("<html><div style='border-left: 4px solid " + vertColor2 + "; padding-left: 10px; margin-left: 20px; font-size: 16px;'>"
                + "\" Là où vos inscriptions pédagogiques se font en toute simplicité !\"</div></html>");
        paragraph.setFont(new Font("Poppins", Font.PLAIN, 14)); 
        paragraph.setForeground(new Color(0x5e5e5e));
        textPanel.add(paragraph);
    
        // Ajouter le textPanel au panel principal
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(textPanel, constraints);
    
        // Créer un panel pour les boutons avec un FlowLayout centré
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(fondColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));  // Aligne les boutons au centre avec espacement de 20px horizontal et 10px vertical
    
        // Créer les boutons pour Responsable Pédagogique et Étudiant
        JButton button1 = new JButton("Responsable Pédagogique");
        button1.setFont(new Font("Poppins", Font.BOLD, 14));
        button1.setBackground(bColor);
        button1.setForeground(Color.WHITE);
        button1.setPreferredSize(new Dimension(250, 40));
    
        JButton button2 = new JButton("Étudiant");
        button2.setFont(new Font("Poppins", Font.BOLD, 14));
        button2.setBackground(vertColor1);
        button2.setForeground(Color.WHITE);
        button2.setPreferredSize(new Dimension(250, 40));
    
        // Ajouter les boutons au panel des boutons
        buttonPanel.add(button1);
        buttonPanel.add(button2);
    
        // Ajouter le panel des boutons au panel principal
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(buttonPanel, constraints);
    
        return panel;
    }
    
    
    

    public void afficher(){
        this.setVisible(true);
    }

     

}
