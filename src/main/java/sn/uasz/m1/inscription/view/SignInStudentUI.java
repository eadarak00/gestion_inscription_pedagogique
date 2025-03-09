package sn.uasz.m1.inscription.view;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.*;

import sn.uasz.m1.inscription.service.AuthentificationService;
import sn.uasz.m1.inscription.view.Etudiant.HomeStudentUI;

public class SignInStudentUI extends JFrame {
      //Declaration des couleurs
      private  final Color VERT_COLOR_1 = new Color(0x113F36);
      private final Color VERT_COLOR_2 = new Color(0x128E64);
      private final Color BG_COLOR = new Color(0xF5F5F5);

    //
    private JTextField emailField;
    private JPasswordField passwordField;

    //
    private AuthentificationService authService;

    // Constructeur
    public SignInStudentUI() {
        authService = new AuthentificationService();
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Espace Etudiant ~ Gestion des Inscriptions Pédagogiques");
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
        ImageIcon originalIcon = new ImageIcon("src/main/resources/static/img/png/sign-in.png");
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
                "<html><span style='font-size:20px; font-weight:bold;'>Bienvenue dans votre espace étudiant</span></html>");
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
                        navigateToHomeStudent();
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
    

    // Panel des champs de saisie (Email et Mot de passe)
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBackground(BG_COLOR);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 20, 10, 20); // Espacement entre les composants
        constraints.anchor = GridBagConstraints.WEST; // Alignement des composants à gauche
        constraints.gridx = 0;
        constraints.gridy = 0;

        // Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        emailLabel.setForeground(new Color(0x5e5e5e));
        inputPanel.add(emailLabel, constraints);

        constraints.gridx = 1;
        emailField = new JTextField(20);
        emailField.setFont(new Font("Poppins", Font.PLAIN, 14));
        inputPanel.add(emailField, constraints);

        // Mot de passe
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel passwordLabel = new JLabel("Mot de Passe:");
        passwordLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(0x5e5e5e));
        inputPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        inputPanel.add(passwordField, constraints);

        // Checkbox pour voir le mot de passe en clair
        constraints.gridx = 2;
        constraints.gridy = 1;
        JCheckBox showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
        showPasswordCheckBox.setFont(new Font("Poppins", Font.PLAIN, 12));
        showPasswordCheckBox.setBackground(BG_COLOR);
        showPasswordCheckBox.setForeground(new Color(0x5e5e5e));

        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Affiche le mot de passe en clair
            } else {
                passwordField.setEchoChar('*'); // Masque le mot de passe
            }
        });

        inputPanel.add(showPasswordCheckBox, constraints);

        // Bouton de connexion
        constraints.gridx = 1;
        constraints.gridy = 2;
        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 14));
        loginButton.setBackground(VERT_COLOR_2);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(250, 40));
        // Lors de l'ajout du bouton de connexion
        // loginButton.addActionListener(e -> {
        //     // String email = emailField.getText();
        //     // String motDePasse = new String(passwordField.getPassword()); // Récupérer le mot de passe

        //     // // Appeler la méthode d'authentification
        //     // if (authService.authentifier(email, motDePasse)) {
        //     //     // Si l'authentification réussie, rediriger ou effectuer une action
        //     //     // supplémentaire
        //     //     JOptionPane.showMessageDialog(this, "Connexion reussi !", "Success", JOptionPane.INFORMATION_MESSAGE);
        //     // } else {
        //     //     // Si l'authentification échoue, rester sur la même page
        //     //     JOptionPane.showMessageDialog(this, "Les informations d'authentification sont incorrectes.",
        //     //             "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
        //     // }
            
        // });

        loginButton.addActionListener(e -> ouvrirModalConnexion());

        inputPanel.add(loginButton, constraints);

        // Paragraphe d'inscription avec un MouseListener pour la redirection
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        // Création du label d'inscription
        JLabel signupLabel = new JLabel("Vous n'avez pas de compte ? Inscrivez-vous ici.");
        signupLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        signupLabel.setForeground(Color.BLACK); // Couleur du lien
        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur main au survol

        // Gestion de l'événement click
        signupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SignUpStudentUI signUpUI = new SignUpStudentUI();
                signUpUI.afficher(); // Ouvrir la page d'inscription
                fermer(); // Fermer la page actuelle
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                signupLabel.setForeground(Color.BLACK);
                signupLabel.setFont(new Font("Poppins", Font.BOLD, 12));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signupLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
                // Revenir à la couleur de base
            }
        });

        // Ajout au panel
        inputPanel.add(signupLabel, constraints);

        inputPanel.add(signupLabel, constraints);

        return inputPanel;
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

    private void navigateToHomeStudent() {
        try {
            HomeStudentUI homePage = new HomeStudentUI();
            homePage.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }

}
