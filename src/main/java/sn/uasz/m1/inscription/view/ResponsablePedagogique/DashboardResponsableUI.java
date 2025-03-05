package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.HomeUI;
import sn.uasz.m1.inscription.view.LoginUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardResponsableUI extends JFrame {
    private JPanel sidebar, contentPanel;
    private CardLayout cardLayout;
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF5F5F0);
    private final Color RED_COLOR = new Color(0xcc1a1a);
    private final Color GRAY_COLOR = new Color(0xededed);

    public DashboardResponsableUI() {

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        setTitle("Dashboard Responsable");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Sidebar
        sidebar = new JPanel(new GridBagLayout());
        sidebar.setBackground(VERT_COLOR_1);
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
        contentPanel.setBackground(BG_COLOR);

        // Panel pour centrer les éléments de navigation
        JPanel navPanel = new JPanel(new GridBagLayout());
        navPanel.setBackground(VERT_COLOR_1);
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
                createMainPanel(null), navPanel, navGbc);
        addNavItem("Formations", "src/main/resources/static/img/png/formation.png", 
                createMainPanel( new FormationUI()),navPanel, navGbc);
        addNavItem("Groupes", "src/main/resources/static/img/png/group.png",
                createMainPanel(new GroupeUI()), navPanel, navGbc);
        addNavItem("UEs", "src/main/resources/static/img/png/ue.png",
                createMainPanel(new UEUI()), navPanel, navGbc);
        addNavItem("Paramètres", "src/main/resources/static/img/png/seetings.png",
                createMainPanel(null), navPanel, navGbc);

        // Récupérer l'utilisateur connecté
        Utilisateur utilisateur = SessionManager.getUtilisateur();
        String userName = (utilisateur != null) ? utilisateur.getNom() + " " + utilisateur.getPrenom()
                : "Utilisateur inconnu";

        // Création du panel utilisateur
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS)); // Dispose les éléments verticalement
        userPanel.setBackground(VERT_COLOR_1);
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrage horizontal

        // Chargement et redimensionnement de l’avatar
        ImageIcon avatIcon = new ImageIcon("src/main/resources/static/img/png/avatar.png");
        Image resizedImage = avatIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel(new ImageIcon(resizedImage));
        avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrage

        // Ajout du nom de l’utilisateur sous l’avatar
        JLabel userNameLabel = new JLabel(userName);
        userNameLabel.setForeground(Color.WHITE);
        userNameLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        userNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrage

        // Ajout des composants au `userPanel`
        userPanel.add(avatarLabel);
        userPanel.add(Box.createVerticalStrut(5));
        userPanel.add(userNameLabel);

        // Placement du `userPanel` dans `sidebar`
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
        navLabel.setBackground(VERT_COLOR_1);
        navLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel navItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navItemPanel.setBackground(VERT_COLOR_1);
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

    private JPanel createMainPanel(JPanel p) {
        if (p == null) {
            p = new JPanel(); // ou autre valeur par défaut
        }
    
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(BG_COLOR);
        panel.add(createPanelNorth(), BorderLayout.NORTH);
        panel.add(p, BorderLayout.CENTER);
    
        return panel;
    }
    

    private JPanel createPanelNorth() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRAY_COLOR);

        // Panel pour aligner "Logout" à gauche
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        ImageIcon logout_icon = new ImageIcon("src/main/resources/static/img/png/logout.png");
        Image logoutImage = logout_icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon logoutIcon = new ImageIcon(logoutImage);
        JButton logout = new JButton(logoutIcon);
        logout.setBackground(RED_COLOR);
        logout.setFont(new Font("Poppins", Font.BOLD, 14));
        // logout.setBorder(null);
        // logout.setPreferredSize(new Dimension(30, 30));
        leftPanel.add(logout);
        leftPanel.setOpaque(false);

        logout.addActionListener(e -> navigateToLogin());

        // Panel pour aligner "Return" à droite
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ImageIcon return_icon = new ImageIcon("src/main/resources/static/img/png/return.png");
        Image returnImage = return_icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon returnIcon = new ImageIcon(returnImage);
        JButton returnButton = new JButton(returnIcon);
        returnButton.setBackground(VERT_COLOR_2);
        returnButton.setFont(new Font("Poppins", Font.BOLD, 14));
        // returnButton.setBorder(null);
        // returnButton.setPreferredSize(new Dimension(30, 30));
        rightPanel.add(returnButton);
        rightPanel.setOpaque(false);

        returnButton.addActionListener(e -> navigateToHome());

        // Ajouter les panels à la barre du haut
        panel.add(leftPanel, BorderLayout.EAST);
        panel.add(rightPanel, BorderLayout.WEST);

        return panel;
    }

    public void afficher() {
        setVisible(true);
    }

    public void fermer() {
        dispose();
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

    private void navigateToLogin() {
        try {
            LoginUI homePage = new LoginUI();
            homePage.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardResponsableUI::new);
    }
}
