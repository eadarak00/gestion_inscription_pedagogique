package sn.uasz.m1.inscription.view;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.*;

import sn.uasz.m1.inscription.service.AuthentificationService;
import sn.uasz.m1.inscription.utils.SecurityUtil;
import sn.uasz.m1.inscription.view.Etudiant.HomeStudentUI;
import sn.uasz.m1.inscription.view.components.IconUI;

public class SignInStudentUI extends JFrame {
    // Declaration des couleurs
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF5F5F5);

    // Déclaration des polices
    private static final Font HEADER_FONT = new Font("Poppins", Font.BOLD, 16);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Poppins", Font.BOLD, 13);
    private static final Font TABLE_HEADER_FONT = new Font("Poppins", Font.BOLD, 14);

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
        // ImageIcon("static/icons/return-icon.png");
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
    JLabel label = new JLabel(IconUI.createIcon("static/img/png/sign-in.png", 700, 700));
    panel.add(label, BorderLayout.CENTER);

    return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Création du panel pour le header avec logo et titre
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Logo (si disponible)
        try {
            ImageIcon originalIcon = new ImageIcon("resources/logo.png");
            Image scaledImage = originalIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPanel.add(logoLabel);
            headerPanel.add(Box.createVerticalStrut(15));
        } catch (Exception e) {
            // Gérer l'absence de logo
        }

        // Titre principal
        JLabel mainTitle = new JLabel("Espace Étudiant");
        mainTitle.setFont(new Font("Poppins", Font.BOLD, 28));
        mainTitle.setForeground(new Color(0x333333));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(mainTitle);

        // Sous-titre
        JLabel subTitle = new JLabel("Connectez-vous pour accéder à vos ressources");
        subTitle.setFont(new Font("Poppins", Font.PLAIN, 16));
        subTitle.setForeground(new Color(0x666666));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(subTitle);

        panel.add(headerPanel);

        // Séparateur élégant
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(500, 1));
        separator.setForeground(new Color(0xEEEEEE));
        panel.add(separator);
        panel.add(Box.createVerticalStrut(30));

        // Carte de connexion avec effet d'élévation
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(5, 3, 10),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.setMaximumSize(new Dimension(450, 600));

        // Ajouter le panel de saisie à la carte
        cardPanel.add(createInputPanel());

        panel.add(cardPanel);
        panel.add(Box.createVerticalStrut(30));

        // Pied de page
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(BG_COLOR);
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Liens dans le pied de page
        String[] links = { "Confidentialité", "Conditions d'utilisation", "Aide" };
        for (int i = 0; i < links.length; i++) {
            JLabel linkLabel = new JLabel(links[i]);
            linkLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
            linkLabel.setForeground(new Color(0x666666));
            linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            final int index = i;
            linkLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    linkLabel.setText("<html><u>" + links[index] + "</u></html>");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    linkLabel.setText(links[index]);
                }
            });

            footerPanel.add(linkLabel);

            // Ajouter un séparateur entre les liens (sauf pour le dernier)
            if (i < links.length - 1) {
                JLabel separ = new JLabel(" • ");
                separ.setFont(new Font("Poppins", Font.PLAIN, 12));
                separ.setForeground(new Color(0x666666));
                footerPanel.add(separ);
            }
        }

        panel.add(footerPanel);

        return panel;
    }

    // Classe pour créer une bordure avec ombre
    class ShadowBorder extends AbstractBorder {
        private int shadowSize;
        private int cornerRadius;
        private int shadowOpacity;

        public ShadowBorder(int shadowSize, int cornerRadius, int shadowOpacity) {
            this.shadowSize = shadowSize;
            this.cornerRadius = cornerRadius;
            this.shadowOpacity = shadowOpacity;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dessiner l'ombre
            for (int i = 0; i < shadowSize; i++) {
                int alpha = shadowOpacity * (shadowSize - i) / shadowSize;
                g2.setColor(new Color(0, 0, 0, alpha));
                g2.drawRoundRect(x + i, y + i, width - i * 2 - 1, height - i * 2 - 1, cornerRadius, cornerRadius);
            }

            // Dessiner le contour principal
            g2.setColor(new Color(0xEEEEEE));
            g2.drawRoundRect(x, y, width - 1, height - 1, cornerRadius, cornerRadius);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(shadowSize, shadowSize, shadowSize * 2, shadowSize * 2);
        }
    }

    private void ouvrirModalConnexion() {
        // Création de la boîte de dialogue modale
        JDialog loadingDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Connexion en cours...",
                true);
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
        loadingLabel.setForeground(Color.BLACK);
        contentPanel.add(loadingLabel, gbc);

        // Ajout d'un GIF de chargement
        gbc.gridy++;
        ImageIcon gifIcon = new ImageIcon(getClass().getClassLoader().getResource("static/img/gif/infinite.gif"));
        JLabel gifLabel = new JLabel(gifIcon);
        contentPanel.add(gifLabel, gbc);

        // Définir le contentPane avec notre panel personnalisé
        loadingDialog.setContentPane(contentPanel);

        // Exécuter l'authentification en arrière-plan
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Vérification si l'utilisateur est un responsable
                if (SecurityUtil.verifierEmailResponsable(emailField.getText())) {
                    return false; // Retourner false pour bloquer la connexion
                }

                return authService.authentifier(emailField.getText(), new String(passwordField.getPassword()));
            }

            @Override
            protected void done() {
                try {
                    boolean success = get();
                    loadingDialog.dispose();

                    if (!success) {
                        JOptionPane.showMessageDialog(null, "Seuls les étudiants peuvent se connecter ici.",
                                "Accès refusé", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    navigateToHomeStudent(); // Rediriger si la connexion est réussie

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();

        // Afficher le modal
        loadingDialog.setVisible(true);
    }

    // Panel des champs de saisie (Email et Mot de passe) - Version moderne
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBackground(BG_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Titre de connexion
        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x333333));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputPanel.add(titleLabel);

        inputPanel.add(Box.createVerticalStrut(25));

        // Email
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(BG_COLOR);
        emailPanel.setMaximumSize(new Dimension(350, 70));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(REGULAR_FONT);
        emailLabel.setForeground(new Color(0x5e5e5e));
        emailPanel.add(emailLabel, BorderLayout.NORTH);

        emailField = new JTextField();
        emailField.setFont(new Font("Poppins", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDDDDDD), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        emailPanel.add(emailField, BorderLayout.CENTER);

        inputPanel.add(emailPanel);
        inputPanel.add(Box.createVerticalStrut(15));

        // Mot de passe
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(BG_COLOR);
        passwordPanel.setMaximumSize(new Dimension(350, 70));

        JLabel passwordLabel = new JLabel("Mot de Passe");
        passwordLabel.setFont(REGULAR_FONT);
        passwordLabel.setForeground(new Color(0x5e5e5e));
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);

        JPanel passwordFieldPanel = new JPanel(new BorderLayout());
        passwordFieldPanel.setBackground(BG_COLOR);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDDDDDD), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        passwordFieldPanel.add(passwordField, BorderLayout.CENTER);

        // Icône pour afficher/masquer le mot de passe
        JToggleButton showPasswordButton = new JToggleButton(
                IconUI.createIcon("static/img/png/close-eye.png", 20, 20));
        showPasswordButton.setSelectedIcon(IconUI.createIcon("static/img/png/open-eye.png", 20, 20));
        showPasswordButton.setBorder(BorderFactory.createEmptyBorder());
        showPasswordButton.setBackground(Color.WHITE);
        showPasswordButton.setFocusPainted(false);
        showPasswordButton.addActionListener(e -> {
            if (showPasswordButton.isSelected()) {
                passwordField.setEchoChar((char) 0); // Affiche le mot de passe
            } else {
                passwordField.setEchoChar('•'); // Masque le mot de passe
            }
        });
        passwordFieldPanel.add(showPasswordButton, BorderLayout.EAST);

        passwordPanel.add(passwordFieldPanel, BorderLayout.CENTER);
        inputPanel.add(passwordPanel);

        // Option "Se souvenir de moi"
        JPanel rememberPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        rememberPanel.setBackground(BG_COLOR);
        rememberPanel.setMaximumSize(new Dimension(350, 30));

        JCheckBox rememberCheckBox = new JCheckBox("Se souvenir de moi");
        rememberCheckBox.setFont(new Font("Poppins", Font.PLAIN, 13));
        rememberCheckBox.setBackground(BG_COLOR);
        rememberCheckBox.setForeground(new Color(0x5e5e5e));
        rememberPanel.add(rememberCheckBox);

        inputPanel.add(rememberPanel);
        inputPanel.add(Box.createVerticalStrut(20));

        // Bouton de connexion
        JButton loginButton = new JButton("SE CONNECTER");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 14));
        loginButton.setBackground(VERT_COLOR_2);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        loginButton.setMaximumSize(new Dimension(350, 45));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setFocusPainted(false);

        // Effet hover
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(
                        Math.max(0, VERT_COLOR_2.getRed() - 20),
                        Math.max(0, VERT_COLOR_2.getGreen() - 20),
                        Math.max(0, VERT_COLOR_2.getBlue() - 20)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(VERT_COLOR_2);
            }
        });

        loginButton.addActionListener(e -> ouvrirModalConnexion());

        inputPanel.add(loginButton);
        inputPanel.add(Box.createVerticalStrut(25));

        // Séparateur ou texte ou
        JPanel orPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        orPanel.setBackground(BG_COLOR);
        orPanel.setMaximumSize(new Dimension(350, 20));

        JSeparator leftSep = new JSeparator();
        leftSep.setPreferredSize(new Dimension(100, 1));
        leftSep.setForeground(new Color(0xDDDDDD));

        JLabel orLabel = new JLabel(" ou ");
        orLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        orLabel.setForeground(new Color(0x888888));

        JSeparator rightSep = new JSeparator();
        rightSep.setPreferredSize(new Dimension(100, 1));
        rightSep.setForeground(new Color(0xDDDDDD));

        orPanel.add(leftSep);
        orPanel.add(orLabel);
        orPanel.add(rightSep);

        inputPanel.add(orPanel);
        inputPanel.add(Box.createVerticalStrut(15));

        // Lien d'inscription
        JPanel signupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupPanel.setBackground(BG_COLOR);
        signupPanel.setMaximumSize(new Dimension(350, 30));

        JLabel signupTextLabel = new JLabel("Vous n'avez pas de compte ? ");
        signupTextLabel.setFont(new Font("Poppins", Font.PLAIN, 13));
        signupTextLabel.setForeground(new Color(0x555555));

        JLabel signupLinkLabel = new JLabel("Inscrivez-vous");
        signupLinkLabel.setFont(new Font("Poppins", Font.BOLD, 13));
        signupLinkLabel.setForeground(VERT_COLOR_2);
        signupLinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Gestion de l'événement click
        signupLinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SignUpStudentUI signUpUI = new SignUpStudentUI();
                signUpUI.afficher(); // Ouvrir la page d'inscription
                fermer(); // Fermer la page actuelle
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                signupLinkLabel.setText("<html><u>Inscrivez-vous</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                signupLinkLabel.setText("Inscrivez-vous");
            }
        });

        signupPanel.add(signupTextLabel);
        signupPanel.add(signupLinkLabel);

        inputPanel.add(signupPanel);

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
