package sn.uasz.m1.inscription.view;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.*;

import sn.uasz.m1.inscription.service.AuthentificationService;
import sn.uasz.m1.inscription.utils.SecurityUtil;
import sn.uasz.m1.inscription.view.ResponsablePedagogique.DashboardResponsableUI;

public class LoginUI extends JFrame {

    // Définition des couleurs
    private final Color VERT_COLOR_1 = new Color(0x113F36);
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
        JLabel mainTitle = new JLabel("Bienvenue dans l'Espace Responsable");
        mainTitle.setFont(new Font("Poppins", Font.BOLD, 28));
        mainTitle.setForeground(new Color(0x333333));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(mainTitle);

        // Sous-titre
        JLabel subTitle = new JLabel("Prenez le contrôle et optimisez la gestion des ressources !");
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
        ImageIcon gifIcon = new ImageIcon("src/main/resources/static/img/gif/infinite.gif");
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
                if (SecurityUtil.verifierEmailEtudiant(emailField.getText())) {
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
                        JOptionPane.showMessageDialog(null, "Seuls les responsables peuvent se connecter ici.",
                                "Accès refusé", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    navigateToDashBoard(); // Rediriger si la connexion est réussie

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la connexion.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();

        // Afficher le modal
        loadingDialog.setVisible(true);
    }


    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(BG_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 10, 15, 10);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2; // Occuper toute la largeur
        constraints.anchor = GridBagConstraints.CENTER; // Centrage horizontal
        constraints.fill = GridBagConstraints.NONE; // Ne pas étirer horizontalement

        JLabel titleLabel = new JLabel("Connexion");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0x333333));

        inputPanel.add(titleLabel, constraints);

        // Panel pour email (regroupement icône + champ)
        constraints.gridy = 1;
        JPanel emailWrapper = createFieldWrapper();

        // Icône Email
        JLabel emailIconLabel = new JLabel(resizeIcon("src/main/resources/static/img/png/email-icon.png", 20, 20));
        emailIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        emailWrapper.add(emailIconLabel, BorderLayout.WEST);

        // Champ Email avec placeholder
        emailField = new JTextField(23);
        emailField.setFont(new Font("Poppins", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));

        // Placeholder pour email
        TextPrompt emailPrompt = new TextPrompt("", emailField);
        emailPrompt.setFont(new Font("Poppins", Font.ITALIC, 14));
        emailPrompt.setForeground(new Color(180, 180, 180));

        emailWrapper.add(emailField, BorderLayout.CENTER);

        constraints.gridwidth = 2;
        inputPanel.add(emailWrapper, constraints);

        // Panel pour mot de passe (regroupement icône + champ + toggle)
        constraints.gridy = 2;
        JPanel passwordWrapper = createFieldWrapper();

        // Icône Mot de passe
        JLabel passwordIconLabel = new JLabel(
                resizeIcon("src/main/resources/static/img/png/password-icon.png", 20, 20));
        passwordIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));
        passwordWrapper.add(passwordIconLabel, BorderLayout.WEST);

        // Champ Mot de passe avec placeholder
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createEmptyBorder(8, 5, 8, 5));
        passwordField.setEchoChar('•'); // Utiliser un bullet point moderne

        // Placeholder pour mot de passe
        TextPrompt passwordPrompt = new TextPrompt("", passwordField);
        passwordPrompt.setFont(new Font("Poppins", Font.ITALIC, 14));
        passwordPrompt.setForeground(new Color(180, 180, 180));

        passwordWrapper.add(passwordField, BorderLayout.CENTER);

        // Toggle pour afficher/masquer le mot de passe (avec icônes)
        JLabel togglePasswordVisibility = new JLabel(
                resizeIcon("src/main/resources/static/img/png/close-eye.png", 22, 22));
        togglePasswordVisibility.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));
        togglePasswordVisibility.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final boolean[] passwordVisible = { false };
        togglePasswordVisibility.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordVisible[0] = !passwordVisible[0];
                if (passwordVisible[0]) {
                    passwordField.setEchoChar((char) 0);
                    togglePasswordVisibility
                            .setIcon(resizeIcon("src/main/resources/static/img/png/open-eye.png", 22, 22));
                } else {
                    passwordField.setEchoChar('•');
                    togglePasswordVisibility
                            .setIcon(resizeIcon("src/main/resources/static/img/png/close-eye.png", 22, 22));
                }
            }
        });

        passwordWrapper.add(togglePasswordVisibility, BorderLayout.EAST);
        inputPanel.add(passwordWrapper, constraints);

        // Bouton de connexion
        constraints.gridy = 3;
        constraints.insets = new Insets(25, 10, 10, 10);

        JButton loginButton = new JButton("SE CONNECTER");
        loginButton.setFont(new Font("Poppins", Font.BOLD, 14));
        loginButton.setBackground(VERT_COLOR_2);
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(200, 45));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet de survol du bouton
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

        loginButton.addActionListener(e -> {
            ouvrirModalConnexion();
        });

        inputPanel.add(loginButton, constraints);

        return inputPanel;
    }

    // Méthode pour créer un wrapper de champ avec bordure et style moderne
    private JPanel createFieldWrapper() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(0, 0, 0, 0)));
        return wrapper;
    }

    // Classe pour les placeholders de texte (adapté de SwingX TextPrompt)
    class TextPrompt extends JLabel implements FocusListener {
        private final JTextField textComponent;

        public TextPrompt(String text, JTextField component) {
            super(text);
            this.textComponent = component;
            component.addFocusListener(this);
            component.setLayout(new BorderLayout());
            component.add(this, BorderLayout.NORTH);
            setVisible(component.getText().isEmpty());
        }

        @Override
        public void focusGained(FocusEvent e) {
            setVisible(textComponent.getText().isEmpty());
        }

        @Override
        public void focusLost(FocusEvent e) {
            setVisible(textComponent.getText().isEmpty());
        }
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
