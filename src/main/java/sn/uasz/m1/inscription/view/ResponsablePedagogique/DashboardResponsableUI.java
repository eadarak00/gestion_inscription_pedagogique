package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import sn.uasz.m1.inscription.email.service.NotificationService;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.HomeUI;
import sn.uasz.m1.inscription.view.LoginUI;
import sn.uasz.m1.inscription.view.components.BadgePanel;
import sn.uasz.m1.inscription.view.components.IconUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DashboardResponsableUI extends JFrame {
    private JPanel sidebar, contentPanel;
    private CardLayout cardLayout;
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF5F5F0);
    private final Color RED_COLOR = new Color(0xcc1a1a);
    private final Color GRAY_COLOR = new Color(0xededed);
    private final Color TEXT_COLOR = Color.WHITE;
    private final Color HOVER_COLOR = new Color(0x1A5F4F);
    private final Color ACTIVE_COLOR = new Color(0x128E64);
    private final Color INDICATOR_COLOR = new Color(0xFFFFFF);

    // Polices
    private static final Font BOLD_FONT_18 = new Font("Poppins", Font.BOLD, 18);
    private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);

    // Liste pour suivre les éléments de navigation
    private List<JPanel> navItems = new ArrayList<>();
    private String activeSection = "Accueil"; // Section active par défaut

    private final NotificationService notificationService;
    int nonLus ;

    public DashboardResponsableUI() {
         Utilisateur utilisateur  = SessionManager.getUtilisateur();
        this.notificationService = new NotificationService();
        nonLus = notificationService.recupererNotificationsNonLues(utilisateur.getEmail());
        
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

        // Sidebar moderne
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setBackground(VERT_COLOR_1);
        sidebar.setPreferredSize(new Dimension(250, 600));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0x0C2E28)));

        // Panel d'en-tête du sidebar
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(VERT_COLOR_1);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Logo
        JLabel logoLabel = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/logo_uasz.png", 80, 80));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(logoLabel, BorderLayout.CENTER);

        // Titre en dessous du logo
        JLabel titleLabel = new JLabel("UASZ Admin", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        headerPanel.add(titleLabel, BorderLayout.SOUTH);

        sidebar.add(headerPanel, BorderLayout.NORTH);

        // Panel pour les éléments de navigation
        JPanel navContainer = new JPanel();
        navContainer.setLayout(new BoxLayout(navContainer, BoxLayout.Y_AXIS));
        navContainer.setBackground(VERT_COLOR_1);
        // navContainer.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Initialisation du CardLayout et contentPanel
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(BG_COLOR);

        // Ajout des sections avec le design moderne
        addNavItem("Accueil", "src/main/resources/static/img/png/home.png",
                createMainPanel(new HomeDashboardPanel()), navContainer);
        addNavItem("Formations", "src/main/resources/static/img/png/formation.png",
                createMainPanel(new FormationUI()), navContainer);
        addNavItem("UEs", "src/main/resources/static/img/png/ue.png",
                createMainPanel(new UEUI()), navContainer);
        addNavItem("Inscriptions", "src/main/resources/static/img/png/proposal.png",
                createMainPanel(new InscriptionsPanel()), navContainer);
        addNavItem("Treated Subscribes", "src/main/resources/static/img/png/proposal.png",
            createMainPanel(new InscriptionTreatedPanel()), navContainer);

        // Ajouter de l'espace entre les boutons de navigation et le profil utilisateur
        navContainer.add(Box.createVerticalGlue());

        sidebar.add(navContainer, BorderLayout.CENTER);
       
        // Récupérer l'utilisateur connecté
        String userName = (utilisateur != null) ? utilisateur.getNom() + " " + utilisateur.getPrenom()
                : "Utilisateur inconnu";

        // Panel utilisateur en bas du sidebar
        JPanel userPanel = createUserPanel(userName);
        sidebar.add(userPanel, BorderLayout.SOUTH);

        // Ajout au frame
        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Activer l'élément par défaut
        updateActiveNavItem("Accueil");
    }

    private JPanel createUserPanel(String userName) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.setBackground(new Color(0x0C2E28));
        userPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        userPanel.setPreferredSize(new Dimension(250, 80));

        // Panel pour l'avatar et le nom
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(new BorderLayout(10, 0));
        userInfoPanel.setBackground(new Color(0x0C2E28));

        // Avatar
        ImageIcon avatarIcon = new ImageIcon("src/main/resources/static/img/png/avatar.png");
        Image resizedAvatar = avatarIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        JLabel avatarLabel = new JLabel(new ImageIcon(resizedAvatar));
        // avatarLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Information utilisateur (nom et rôle)
        JPanel userTextPanel = new JPanel();
        userTextPanel.setLayout(new BoxLayout(userTextPanel, BoxLayout.Y_AXIS));
        userTextPanel.setBackground(new Color(0x0C2E28));

        JLabel nameLabel = new JLabel(userName);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Poppins", Font.BOLD, 13));

        JLabel roleLabel = new JLabel("Responsable pédagogique");
        roleLabel.setForeground(new Color(0xCCCCCC));
        roleLabel.setFont(new Font("Poppins", Font.ITALIC, 11));

        userTextPanel.add(nameLabel);
        userTextPanel.add(Box.createVerticalStrut(3));
        userTextPanel.add(roleLabel);

        userInfoPanel.add(avatarLabel, BorderLayout.WEST);
        userInfoPanel.add(userTextPanel, BorderLayout.CENTER);

        // Button de déconnexion
        JLabel logoutLabel = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/logout-white.png", 18, 18));
        logoutLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                logout();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                logoutLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutLabel.setBorder(null);
            }
        });

        userInfoPanel.add(logoutLabel, BorderLayout.EAST);
        userPanel.add(userInfoPanel, BorderLayout.CENTER);

        return userPanel;
    }

    private void addNavItem(String label, String iconPath, JPanel panel, JPanel parent) {
        // Ajouter le panneau au cardLayout
        contentPanel.add(panel, label);

        // Créer un panneau personnalisé pour l'élément de navigation
        JPanel navItemPanel = new JPanel();
        navItemPanel.setLayout(new BorderLayout());
        navItemPanel.setBackground(VERT_COLOR_1);
        navItemPanel.setMaximumSize(new Dimension(250, 50));
        navItemPanel.setPreferredSize(new Dimension(250, 50));
        navItemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        navItemPanel.setName(label); // Stocker le nom pour référence

        // Indicateur de sélection (barre verticale à gauche)
        JPanel indicator = new JPanel();
        indicator.setPreferredSize(new Dimension(5, 50));
        indicator.setBackground(GRAY_COLOR); // Initialement même couleur que le fond
        navItemPanel.add(indicator, BorderLayout.WEST);

        // Contenu principal de l'élément (icône + texte)
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        contentPanel.setBackground(VERT_COLOR_1);

        // Icône
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));

        // Texte
        JLabel textLabel = new JLabel(label);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(BOLD_FONT_18);

        contentPanel.add(iconLabel);
        contentPanel.add(textLabel);
        navItemPanel.add(contentPanel, BorderLayout.CENTER);

        // Gestionnaire d'événements
        navItemPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!label.equals(activeSection)) {
                    navItemPanel.setBackground(HOVER_COLOR);
                    contentPanel.setBackground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!label.equals(activeSection)) {
                    navItemPanel.setBackground(VERT_COLOR_1);
                    contentPanel.setBackground(VERT_COLOR_1);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(DashboardResponsableUI.this.contentPanel, label);
                updateActiveNavItem(label);
            }
        });

        // Stocker la référence à l'élément et ses composants pour pouvoir les manipuler
        // plus tard
        navItems.add(navItemPanel);

        // Ajouter au conteneur parent avec espacement
        parent.add(navItemPanel);
        parent.add(Box.createVerticalStrut(5)); // Espace entre les éléments
    }

    private void updateActiveNavItem(String activeLabel) {
        this.activeSection = activeLabel;

        // Mettre à jour les styles des éléments de navigation
        for (JPanel navItem : navItems) {
            String itemName = navItem.getName();
            JPanel indicator = (JPanel) navItem.getComponent(0);
            JPanel contentPanel = (JPanel) navItem.getComponent(1);

            if (itemName.equals(activeLabel)) {
                // Style actif
                navItem.setBackground(ACTIVE_COLOR);
                contentPanel.setBackground(ACTIVE_COLOR);
                indicator.setBackground(INDICATOR_COLOR);
            } else {
                // Style inactif
                navItem.setBackground(VERT_COLOR_1);
                contentPanel.setBackground(VERT_COLOR_1);
                indicator.setBackground(VERT_COLOR_1);
            }
        }
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
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(VERT_COLOR_1);
        panel.setPreferredSize(new Dimension(70, 70));

        // === Label Menu Hamburger ===
        JLabel menuLabel = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/hamburger.png", 30, 30));
        menuLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuLabel.setOpaque(false);
        menuLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Menu déroulant ===
        JPopupMenu menuPopup = new JPopupMenu();
        menuPopup.setBackground(VERT_COLOR_1);

        JMenuItem menuItem1 = new JMenuItem("Profil");
        JMenuItem menuItem2 = new JMenuItem("Déconnexion");

        // Appliquer le style au menu
        customizeMenuItem(menuItem1);
        customizeMenuItem(menuItem2);

        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profile();
            }
        });

        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });

        menuPopup.add(menuItem1);
        menuPopup.add(menuItem2);

        // Action pour afficher le menu lors du clic sur le label
        menuLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPopup.show(menuLabel, 0, menuLabel.getHeight());
            }
        });

        // === Titre de la vue ===
        JLabel title = new JLabel("Vue Responsable - Gestion des Étudiants");
        title.setFont(BOLD_FONT_18);
        title.setForeground(TEXT_COLOR);
        title.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Marge à gauche

        JPanel notificationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        notificationPanel.setOpaque(false);

        // Icône de notification
        JLabel notificationIcon = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/notification.png", 25, 25));
        notificationIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Badge arrondi avec le nombre de notifications
        BadgePanel badgeNotif = new BadgePanel(""+nonLus);

        // Ajouter l'icône et le badge dans le panneau
        notificationPanel.add(notificationIcon);
        notificationPanel.add(badgeNotif);

        // Ajouter l'icône et le badge
        notificationPanel.add(notificationIcon);
        notificationPanel.add(badgeNotif);

        // Action pour afficher le panneau de notifications
        notificationIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // afficherPanneauNotifications(); // Méthode pour afficher les notifications
                navigateToNotif();
            }
        });

        // === Conteneur à droite (Notifications + Menu) ===
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        rightPanel.setOpaque(false);
        rightPanel.add(notificationPanel);
        rightPanel.add(menuLabel);

        // === Ajout des composants à la Navbar ===
        panel.add(title, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private void customizeMenuItem(JMenuItem item) {
        item.setFont(REGULAR_FONT);
        item.setForeground(TEXT_COLOR);
        item.setBackground(VERT_COLOR_1);
        item.setOpaque(true);
        item.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    public void afficher() {
        setVisible(true);
    }

    public void fermer() {
        dispose();
    }

    private void navigateToNotif() {
        try {
            NotificationCardFrame notifPage = new NotificationCardFrame();
            notifPage.afficher();
            fermer();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
            exp.printStackTrace();
        }
    }

    private void logout() {
        try {

            HomeUI homePage = new HomeUI();
            homePage.afficher();
            fermer();
            SessionManager.logout();
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

    private void profile() {
        ProfilResponsableUI homeUI = new ProfilResponsableUI();
    
        this.dispose();
    
        homeUI.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardResponsableUI::new);
    }
}