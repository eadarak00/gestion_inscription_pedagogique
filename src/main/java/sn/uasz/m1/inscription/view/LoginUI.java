package sn.uasz.m1.inscription.view;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.*;

import sn.uasz.m1.inscription.service.AuthentificationService;
import sn.uasz.m1.inscription.view.ResponsablePedagogique.DashboardResponsableUI;

public class LoginUI extends JFrame {

    // Définition des couleurs
    private  final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF5F5F0);

    //
    private JTextField emailField;
    private JPasswordField passwordField;

    //
    private AuthentificationService authService;

    // Constructeur
    public LoginUI() {
        authService = new AuthentificationService();
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Espace Reponsable Pedagogique ~ Gestion des Inscriptions Pédagogiques");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setLayout(new BorderLayout());

        // Ajouter les panels gauche et centre
        mainPanel.add(createStartPanel(), BorderLayout.NORTH);
        mainPanel.add(createLeftPanel(), BorderLayout.WEST);
        mainPanel.add(createCenterPanel(), BorderLayout.CENTER);
    }

    // return butoon
    private JPanel createStartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        // Panel pour aligner "Logout" à gauche
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // ImageIcon retIcon = new
        // ImageIcon("src/main/resources/static/icons/return-icon.png");
        JButton returnButton = new JButton("return");
        rightPanel.add(returnButton);
        rightPanel.setOpaque(false);

        returnButton.addActionListener(e -> navigateToHome());
        panel.add(rightPanel, BorderLayout.WEST);

        return panel;

    }

    // Panel gauche (pour l'icône)
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(600, 0));
        panel.setBackground(BG_COLOR);

        // Charger et redimensionner l'icône
        ImageIcon originalIcon = new ImageIcon("src/main/resources/static/img/png/login.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(700, 700, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel(resizedIcon);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(BG_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 20, 15, 20);
        constraints.anchor = GridBagConstraints.CENTER; // Centrer les composants dans le GridBagLayout
        constraints.gridwidth = 1;

        // Création du panel pour le titre et le paragraphe
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS)); // Arrange les composants verticalement
        textPanel.setBackground(BG_COLOR);

        // Titre
        JLabel sectionTitle = new JLabel(
                "<html><span style='font-size:20px; font-weight:bold;'>Simplifiez la gestion pédagogique en toute sérénité !</span></html>");
        sectionTitle.setFont(new Font("Poppins", Font.BOLD, 20));
        sectionTitle.setForeground(Color.BLACK);
        textPanel.add(sectionTitle);

        // Paragraphe avec des côtes
        JLabel paragraph = new JLabel("<html><div style='text-align:center; margin-top:20px;'>"
                + "<hr style='border: 1px solid #6b6b6b;'/>"
                + "<span style='font-size: 14px;'>Informations</span>"
                + "<hr style='border: 1px solid #6b6b6b;'/>"
                + "</div></html>");
        paragraph.setFont(new Font("Poppins", Font.PLAIN, 14));
        paragraph.setForeground(new Color(0x5e5e5e));
        paragraph.setHorizontalAlignment(JLabel.CENTER);
        textPanel.add(paragraph);

        // Ajouter le textPanel au panel principal
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(textPanel, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;

        panel.add(createInputPanel(), constraints);

        return panel;
    }

    private void ouvrirModalConnexion() {
        // Création de la boîte de dialogue modale
        JDialog loadingDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Connexion en cours...", true);
        loadingDialog.setSize(400, 250);
        loadingDialog.setLayout(new GridBagLayout());
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setUndecorated(true);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    
        // Définir une couleur de fond
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BG_COLOR);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        // Ajout d'un texte "Connexion en cours..."
        JLabel loadingLabel = new JLabel("Connexion en cours...");
        loadingLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        loadingLabel.setForeground(Color.BLACK); // Changer la couleur du texte
        contentPanel.add(loadingLabel, gbc);
    
        // Ajout d'un GIF de chargement
        gbc.gridy++;
        ImageIcon gifIcon = new ImageIcon("src/main/resources/static/img/gif/infinite.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        contentPanel.add(gifLabel, gbc);
    
        // Définir le contentPane avec notre panel personnalisé
        loadingDialog.setContentPane(contentPanel);
    
        // Exécuter l'authentification en arrière-plan
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                // Simulation d'un délai réseau (2 sec)
                try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
                return authService.authentifier(emailField.getText(), new String(passwordField.getPassword()));
            }
    
            @Override
            protected void done() {
                try {
                    boolean success = get();
                    loadingDialog.dispose(); // Fermer le modal après la connexion
    
                    if (success) {
                        navigateToDashBoard();
                    } else {
                        JOptionPane.showMessageDialog(null, "Identifiants incorrects.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    
        // Afficher le modal
        loadingDialog.setVisible(true);
    }
    
    
    /** 🔥 Associez cette méthode au bouton "Se connecter" */
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BG_COLOR);
    
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 5, 10, 5);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
    
        // Icône Email
        JLabel emailIconLabel = new JLabel(resizeIcon("src/main/resources/static/img/png/email-icon.png", 25, 25));
        inputPanel.add(emailIconLabel, constraints);
    
        // Champ Email
        constraints.gridx = 1;
        emailField = new JTextField(20);
        emailField.setFont(new Font("Poppins", Font.PLAIN, 14));
        inputPanel.add(emailField, constraints);
    
        // Icône Mot de passe
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel passwordIconLabel = new JLabel(resizeIcon("src/main/resources/static/img/png/password-icon.png", 25, 25));
        inputPanel.add(passwordIconLabel, constraints);
    
        // Champ Mot de passe
        constraints.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        inputPanel.add(passwordField, constraints);
    
        // Checkbox pour afficher le mot de passe
        constraints.gridx = 2;
        constraints.gridy = 1;
        JCheckBox showPasswordCheckBox = new JCheckBox("Afficher");
        showPasswordCheckBox.setFont(new Font("Poppins", Font.PLAIN, 12));
        showPasswordCheckBox.setBackground(BG_COLOR);
        showPasswordCheckBox.setForeground(new Color(0x5e5e5e));
    
        showPasswordCheckBox.addActionListener(e -> {
            passwordField.setEchoChar(showPasswordCheckBox.isSelected() ? (char) 0 : '*');
        });
    
        inputPanel.add(showPasswordCheckBox, constraints);
    
        // Label pour le loader
        constraints.gridx = 1;
        constraints.gridy = 2;
        JLabel loadingLabel = new JLabel();
        loadingLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        loadingLabel.setForeground(new Color(0x5e5e5e));
        loadingLabel.setVisible(false); // Caché au début
        inputPanel.add(loadingLabel, constraints);


        constraints.gridx = 1;
        constraints.gridy = 2;
    
        // Bouton de connexion
        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 14));
        loginButton.setBackground(VERT_COLOR_2);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(250, 40));
    
        loginButton.addActionListener(e -> ouvrirModalConnexion());
    
        inputPanel.add(loginButton, constraints);
        return inputPanel;
    }
    
    
    /** 🔄 Méthode utilitaire pour redimensionner une icône */
    private ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    

    // Affichage de la fenêtre
    public void afficher() {
        this.setVisible(true);
    }

    public void fermer() {
        this.dispose();
    }

    private void navigateToHome() {
        try {
            HomeUI homePage = new HomeUI();
            homePage.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }

    private void navigateToDashBoard() {
        try {
            DashboardResponsableUI homePage = new DashboardResponsableUI();
            homePage.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }


}
