package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.components.Navbar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilResponsableUI extends JFrame {
    private final ResponsablePedagogiqueService service;
    private ResponsablePedagogique responsable;

    private JTextField nomField;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField dptField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    // 🎨 Déclaration des couleurs (conservées de l'original)
    private static final Color VERT_COLOR_1 = new Color(0x113F36);
    private static final Color VERT_COLOR_2 = new Color(0x128E64);
    private static final Color VERT_3 = new Color(0x0B7968);
    private static final Color BLA_COLOR = new Color(0x151d21);
    private static final Color BG_COLOR = new Color(0xF2F2F2);
    private static final Color RED_COLOR = new Color(0xcc1a1a);
    private static final Color GRAY_COLOR = new Color(0xC6BFBF);

    // Couleurs supplémentaires pour amélioration visuelle
    private static final Color TEXT_COLOR = new Color(0x333333);
    private static final Color HOVER_COLOR = new Color(0xE6E6E1);
    private static final Color CARD_COLOR = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(0xDDDDD8);

    // 🖋 Déclaration des polices
    private static final Font HEADER_FONT = new Font("Poppins", Font.BOLD, 20);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Poppins", Font.BOLD, 13);
    private static final Font TABLE_HEADER_FONT = new Font("Poppins", Font.BOLD, 14);

    public ProfilResponsableUI() {
        this.service = new ResponsablePedagogiqueService();
        Utilisateur user = SessionManager.getUtilisateur();

        this.responsable = service.getResponsableById(user.getId());

        setTitle("Profil Responsable Pédagogique");
        setSize(1500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Ajout d'une bordure pour avoir des marges
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BG_COLOR);

        // Ajout de la barre de navigation
        add(new Navbar(this), BorderLayout.NORTH);

        // Ajout du contenu principal
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(createFormPanel(), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BG_COLOR);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Gestion du Profil");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(VERT_COLOR_1);

        JLabel subtitleLabel = new JLabel("Modifiez vos informations personnelles");
        subtitleLabel.setFont(REGULAR_FONT);
        subtitleLabel.setForeground(TEXT_COLOR);

        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        labelPanel.setBackground(BG_COLOR);
        labelPanel.add(titleLabel);
        labelPanel.add(subtitleLabel);

        headerPanel.add(labelPanel, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createFormPanel() {
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.setBackground(BG_COLOR);
        // Création du panel pour les informations personnelles
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(VERT_COLOR_1),
                "Informations Personnelles",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                HEADER_FONT,
                VERT_COLOR_1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Nom
        addFormField(infoPanel, "Nom:", responsable.getNom(), gbc, 0);
        nomField.setPreferredSize(new Dimension(200, 30));

        // Prénom
        addFormField(infoPanel, "Prénom:", responsable.getPrenom(), gbc, 1);
        prenomField.setPreferredSize(new Dimension(200, 30));

        // Email
        addFormField(infoPanel, "Email:", responsable.getEmail(), gbc, 2);
        emailField.setPreferredSize(new Dimension(300, 30));

        // Département
        addFormField(infoPanel, "Département:", responsable.getDepartement(), gbc, 3);
        dptField.setPreferredSize(new Dimension(200, 30));

        // Panel pour le changement de mot de passe
        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BG_COLOR),
                "Modifier le mot de passe (optionnel)",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                HEADER_FONT,
                VERT_COLOR_1));

        // Nouveau mot de passe
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel passwordLabel = new JLabel("Nouveau mot de passe:");
        passwordLabel.setFont(REGULAR_FONT);
        passwordPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordPanel.add(passwordField, gbc);

        // Confirmation du mot de passe
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel confirmLabel = new JLabel("Confirmer le mot de passe:");
        confirmLabel.setFont(REGULAR_FONT);
        passwordPanel.add(confirmLabel, gbc);

        gbc.gridx = 1;
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(200, 30));
        passwordPanel.add(confirmPasswordField, gbc);

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields();
            }
        });

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.setFont(BUTTON_FONT);
        updateButton.setBackground(BLA_COLOR);
        updateButton.setForeground(Color.WHITE);
        updateButton.setPreferredSize(new Dimension(150, 40));
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProfil();
            }
        });


        JButton returbButton = new JButton("Return Dashboard");
        returbButton.setFont(BUTTON_FONT);
        returbButton.setBackground(BLA_COLOR);
        returbButton.setForeground(Color.WHITE);
        returbButton.setPreferredSize(new Dimension(150, 40));
        returbButton.addActionListener( e -> navigateToDashboard());

        buttonPanel.add(returbButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(updateButton);

        // Assemblage des panels
        JPanel formPanel = new JPanel();
        formContainer.setBackground(BG_COLOR);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.add(infoPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(passwordPanel);

        formContainer.add(formPanel, BorderLayout.CENTER);
        formContainer.add(buttonPanel, BorderLayout.SOUTH);

        return formContainer;
    }

    private void addFormField(JPanel panel, String labelText, String value, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel label = new JLabel(labelText);
        label.setFont(REGULAR_FONT);
        panel.add(label, gbc);

        gbc.gridx = 1;
        JTextField field;

        switch (row) {
            case 0:
                nomField = new JTextField(value);
                field = nomField;
                break;
            case 1:
                prenomField = new JTextField(value);
                field = prenomField;
                break;
            case 2:
                emailField = new JTextField(value);
                field = emailField;
                break;
            case 3:
                dptField = new JTextField(value);
                field = dptField;
                break;
            default:
                field = new JTextField(value);
        }

        panel.add(field, gbc);
    }

    private void resetFields() {
        nomField.setText(responsable.getNom());
        prenomField.setText(responsable.getPrenom());
        emailField.setText(responsable.getEmail());
        dptField.setText(responsable.getDepartement());
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void updateProfil() {
        // Validation basique
        if (nomField.getText().trim().isEmpty() ||
                prenomField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                dptField.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Tous les champs d'information sont obligatoires.",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérification des mots de passe si renseignés
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Les mots de passe ne correspondent pas.",
                    "Erreur de validation",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mise à jour des informations
        responsable.setNom(nomField.getText().trim());
        responsable.setPrenom(prenomField.getText().trim());
        responsable.setEmail(emailField.getText().trim());
        responsable.setDepartement(dptField.getText().trim());

        // Mise à jour du mot de passe si renseigné
        if (!password.isEmpty()) {
        }

        // Mise à jour dans la base de données
        service.updateResponsable(responsable.getId(), responsable);

        JOptionPane.showMessageDialog(this,
                "Profil mis à jour avec succès !",
                "Mise à jour",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void navigateToDashboard() {
        try {
            DashboardResponsableUI homePage = new DashboardResponsableUI();
            homePage.afficher();
            this.dispose();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }
}