package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;

import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardResponsableUI extends JFrame {
    private JPanel sidebar, contentPanel;
    private CardLayout cardLayout;
    private Color vertColor1 = new Color(0x113F36);
    private Color vertColor2 = new Color(0x128E64);
    private Color fondColor = new Color(0xF5F5F0);
    private Color bColor = new Color(0x151d21);

    public DashboardResponsableUI() {
        setTitle("Dashboard Responsable");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel(new GridBagLayout());
        sidebar.setBackground(vertColor1);
        sidebar.setPreferredSize(new Dimension(200, 600));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Titre du Dashboard en haut
        JLabel titleLabel = new JLabel("Dashboard Responsable", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0;
        sidebar.add(titleLabel, gbc);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(fondColor);

        // Panel pour centrer les éléments de navigation
        JPanel navPanel = new JPanel(new GridBagLayout());
        navPanel.setBackground(vertColor1);
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        sidebar.add(navPanel, gbc);

        GridBagConstraints navGbc = new GridBagConstraints();
        navGbc.fill = GridBagConstraints.HORIZONTAL;
        navGbc.gridx = 0;
        navGbc.weightx = 1.0;
        navGbc.insets = new Insets(5, 5, 5, 5);

        // Ajout des sections manuellement avec icônes
        addNavItem("Accueil", "src/main/resources/static/img/png/home.png",
                createContentPanel("Bienvenue sur le Dashboard"), navPanel, navGbc);
        addNavItem("Formations", "src/main/resources/static/img/png/formation.png", createUserManagementPanel(),
                navPanel, navGbc);
        addNavItem("Groupes", "src/main/resources/static/img/png/group.png",
                createContentPanel("Planning - Organisation des emplois du temps"), navPanel, navGbc);
        addNavItem("UEs", "src/main/resources/static/img/png/ue.png",
                createContentPanel("Statistiques - Aperçu des performances et données"), navPanel, navGbc);
        addNavItem("Paramètres", "src/main/resources/static/img/png/seetings.png",
                createContentPanel("Paramètres - Configuration des préférences"), navPanel, navGbc);

        // recuperer l'utilisateur connecte
        // Utilisateur utilisateur = SessionManager.getUtilisateur();
        // String userName = (utilisateur != null) ? utilisateur.getNom() + " " +
        // utilisateur.getPrenom() : "Utilisateur inconnu";

        // Ajout de l'avatar et du nom de l'utilisateur en bas
        // JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // userPanel.setBackground(vertColor1);

        // ImageIcon avatIcon = new
        // ImageIcon("src/main/resources/static/img/png/avatar.png");
        // Image resizedImage = avatIcon.getImage().getScaledInstance(30, 30,
        // Image.SCALE_SMOOTH);
        // JLabel avatarLabel = new JLabel(new ImageIcon(resizedImage));

        // JLabel userNameLabel = new JLabel(userName);
        // userNameLabel.setForeground(Color.WHITE);
        // userNameLabel.setFont(new Font("Poppins", Font.BOLD, 18));

        // // userPanel.add(avatarLabel);
        // userPanel.add(userNameLabel);

        // // gbc.gridy = 2;
        // // gbc.weighty = 3;
        // // gbc.anchor = GridBagConstraints.SOUTH;
        // // sidebar.add(userPanel, gbc);
        // gbc.gridx = 0;
        // gbc.gridy = 2;
        // gbc.weighty = 1.0;
        // gbc.anchor = GridBagConstraints.SOUTH;
        // sidebar.add(userPanel, gbc);

        //   Récupérer l'utilisateur connecté
        Utilisateur utilisateur = SessionManager.getUtilisateur();
        String userName = (utilisateur != null) ? utilisateur.getNom() + " " + utilisateur.getPrenom()
                : "Utilisateur inconnu";

        //   Création du panel utilisateur
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS)); // 🔄 Dispose les éléments verticalement
        userPanel.setBackground(vertColor1);
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT); //   Centrage horizontal

        //   Chargement et redimensionnement de l’avatar
        ImageIcon avatIcon = new ImageIcon("src/main/resources/static/img/png/avatar.png");
        Image resizedImage = avatIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel(new ImageIcon(resizedImage));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //   Centrage

        //   Ajout du nom de l’utilisateur sous l’avatar
        JLabel userNameLabel = new JLabel(userName);
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //   Centrage

        // Ajout des composants au `userPanel`
        userPanel.add(avatarLabel);
        userPanel.add(Box.createVerticalStrut(5)); 
        userPanel.add(userNameLabel);

        //  Placement du `userPanel` dans `sidebar`
        GridBagConstraints gbcUser = new GridBagConstraints();
        gbcUser.gridx = 0;
        gbcUser.gridy = 2;
        gbcUser.weighty = 1.0;
        gbcUser.anchor = GridBagConstraints.SOUTH;
        sidebar.add(userPanel, gbcUser);

        // Mise à jour de l'affichage
        userNameLabel.revalidate();
        userNameLabel.repaint();

        // Ajout au frame
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        // afficher();

    }

    private void addNavItem(String label, String iconPath, JPanel panel, JPanel parent, GridBagConstraints gbc) {
        contentPanel.add(panel, label);

        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));

        JLabel navLabel = new JLabel(label, SwingConstants.LEFT);
        navLabel.setForeground(Color.WHITE);
        navLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        navLabel.setOpaque(true);
        navLabel.setBackground(vertColor1);
        navLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel navItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navItemPanel.setBackground(vertColor1);
        navItemPanel.add(iconLabel);
        navItemPanel.add(navLabel);

        navLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                navLabel.setForeground(new Color(173, 216, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                navLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(contentPanel, label);
            }
        });

        gbc.gridy++;
        parent.add(navItemPanel, gbc);
    }

    private JPanel createContentPanel(String text) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(text));
        return panel;
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Table des utilisateurs
        String[] columnNames = { "ID", "Nom", "Email" };
        Object[][] data = { { 1, "Jean Dupont", "jean.dupont@example.com" },
                { 2, "Marie Curie", "marie.curie@example.com" } };
        JTable userTable = new JTable(data, columnNames);
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Panel des boutons CRUD
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        JButton updateButton = new JButton("Modifier");
        JButton deleteButton = new JButton("Supprimer");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    public void afficher() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardResponsableUI::new);
    }
}
