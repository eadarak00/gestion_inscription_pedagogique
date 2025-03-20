package sn.uasz.m1.inscription.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import sn.uasz.m1.inscription.controller.EtudiantController;
import sn.uasz.m1.inscription.model.enumeration.Sexe;

public class SignUpStudentUI extends JFrame {
    // Définition des couleurs
    private Color vertColor1 = new Color(0x113F36);
    private Color vertColor2 = new Color(0x128E64);
    private Color fondColor = new Color(0xF5F5F0);
    private Color bColor = new Color(0x151d21);

    // les champs
    private JTextField prenomField;
    private JTextField nomField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField ineField;
    private JFormattedTextField dateField;
    private JRadioButton homme;
    private JRadioButton femme;
    private JTextField adresseField;
    private EtudiantController etudiantController;

    public SignUpStudentUI() {
        etudiantController = new EtudiantController();
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Espace Etudiant ~ Gestion des Inscriptions Pédagogiques");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setBackground(fondColor);
        mainPanel.setLayout(new BorderLayout());

        // Ajouter les panels gauche et centre
        mainPanel.add(createStartPanel(), BorderLayout.NORTH);
        mainPanel.add(createRightPanel(), BorderLayout.EAST);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);

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
    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 0));
        panel.setBackground(fondColor);

        // Charger et redimensionner l'icône
        ImageIcon originalIcon = new ImageIcon("src/main/resources/static/img/png/sign-up.png");
        Image resizedImage = originalIcon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        JLabel label = new JLabel(resizedIcon);
        panel.add(label, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFormPanel() {
        Font poppinsFont = new Font("Poppins", Font.PLAIN, 14);
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(fondColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Labels et champs
        formPanel.add(new JLabel("Prénom :"), gbc);
        gbc.gridx = 1;
        prenomField = new JTextField(20);
        prenomField.setFont(poppinsFont);
        formPanel.add(prenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Nom :"), gbc);
        gbc.gridx = 1;
        nomField = new JTextField(20);
        nomField.setFont(poppinsFont);
        formPanel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Email :"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        emailField.setFont(poppinsFont);
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Mot de passe :"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        passwordField.setFont(poppinsFont);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("INE :"), gbc);
        gbc.gridx = 1;
        ineField = new JTextField(20);
        ineField.setFont(poppinsFont);
        formPanel.add(ineField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Date de naissance :"), gbc);
        gbc.gridx = 1;
        dateField = new JFormattedTextField();
        dateField.setFont(poppinsFont);
        dateField.setColumns(20);
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Sexe :"), gbc);
        gbc.gridx = 1;
        homme = new JRadioButton("Homme");
        femme = new JRadioButton("Femme");
        homme.setSelected(true);
        homme.setFont(poppinsFont);
        femme.setFont(poppinsFont);
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(homme);
        genderGroup.add(femme);
        JPanel genderPanel = new JPanel();
        genderPanel.add(homme);
        genderPanel.add(femme);
        formPanel.add(genderPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Adresse :"), gbc);
        gbc.gridx = 1;
        adresseField = new JTextField(20);
        adresseField.setFont(poppinsFont);
        formPanel.add(adresseField, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton signUpButton = new JButton("S'inscrire");
        signUpButton.setFont(new Font("Poppins", Font.BOLD, 14));
        signUpButton.setBackground(vertColor1);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setPreferredSize(new Dimension(250, 40));
        signUpButton.addActionListener(e -> inscrireEtudiant());
        formPanel.add(signUpButton, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 2;
        // JLabel loginLabel = new JLabel("Déjà un compte ? Connectez-vous ici.");
        // loginLabel.setForeground(vertColor1);
        // loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // loginLabel.setFont(new Font("Poppins", Font.BOLD, 12));
        // loginLabel.addMouseListener(new MouseAdapter() {
        //     @Override
        //     public void mouseClicked(MouseEvent e) {
        //         navigateToSignIn();
        //     }


        // });

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.setBackground(fondColor);
        JLabel loginLabel = new JLabel("Déjà un compte ? ");
        loginLabel.setFont(new Font("Poppins", Font.PLAIN, 12));
        JLabel loginLink = new JLabel("Connectez-vous ici");
        loginLink.setFont(new Font("Poppins", Font.BOLD, 12));
        loginLink.setForeground(vertColor2);
        loginLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginLink.setForeground(vertColor1);
                loginLink.setText("<html><u>Connectez-vous ici</u></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginLink.setForeground(vertColor2);
                loginLink.setText("Connectez-vous ici");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                navigateToSignIn();
            }
        });

        loginPanel.add(loginLabel);
        loginPanel.add(loginLink);
        // formPanel.add(loginLabel, gbc);
        formPanel.add(loginPanel, gbc);


        return formPanel;
    }

    private void inscrireEtudiant() {
        String ine = ineField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String motDePasse = new String(passwordField.getPassword());
        String dateNaissanceStr = dateField.getText();
        Sexe sexe = homme.isSelected() ? Sexe.HOMME : femme.isSelected() ? Sexe.FEMME : null;
        String adresse = adresseField.getText();
    
        // Vérification et conversion de la date
        LocalDate dateNaissance = null;
        try {
            dateNaissance = LocalDate.parse(dateNaissanceStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide. Utilisez yyyy-MM-dd.", "Erreur de format", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Appel du contrôleur
        String message = etudiantController.inscrire(ine, nom, prenom, email, motDePasse, dateNaissance, sexe, adresse);
    
        // Popup de confirmation ou d'erreur + Réinitialisation après succès
        if (message.contains("succès")) {
            JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);
            reinitialiserFormulaire();
            navigateToSignIn();
        } else {
            JOptionPane.showMessageDialog(this, message, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void reinitialiserFormulaire() {
        prenomField.setText("");
        nomField.setText("");
        emailField.setText("");
        passwordField.setText("");
        ineField.setText("");
        dateField.setText("");
        adresseField.setText("");
        homme.setSelected(true);
        femme.setSelected(false);
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

    private void navigateToSignIn() {
        try {
            SignInStudentUI homePage = new SignInStudentUI();
            homePage.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }

    public void afficher() {
        setVisible(true);
    }

    public void fermer() {
        this.dispose();
    }

}
