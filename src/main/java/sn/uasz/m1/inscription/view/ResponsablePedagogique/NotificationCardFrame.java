// package sn.uasz.m1.inscription.view.ResponsablePedagogique;

// import javax.swing.*;
// import javax.swing.border.*;
// import javax.swing.plaf.nimbus.NimbusLookAndFeel;

// import java.awt.*;
// import java.awt.event.*;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;

// import sn.uasz.m1.inscription.email.model.Notification;
// import sn.uasz.m1.inscription.email.service.NotificationService;
// import sn.uasz.m1.inscription.model.ResponsablePedagogique;
// import sn.uasz.m1.inscription.model.Utilisateur;
// import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;
// import sn.uasz.m1.inscription.utils.SessionManager;
// import sn.uasz.m1.inscription.view.components.Navbar;
// import sn.uasz.m1.inscription.view.components.NavbarPanel;

// public class NotificationCardFrame extends JFrame {

//     private static final long serialVersionUID = 1L;
//     private JPanel mainContainer;
//     private JPanel cardsContainer;
//     private JDialog messageDialog;
//     private JLabel dialogTitleLabel;
//     private JTextArea dialogMessageArea;
//     private JLabel dialogDateLabel;

//     private List<Notification> notifications;

//     // Couleurs et styles

//     private final Color BACKGROUND_COLOR = new Color(245, 245, 250);
//     private final Color CARD_COLOR = new Color(255, 255, 255);
//     private final Color CARD_BORDER_COLOR = new Color(230, 230, 235);
//     private final Color UNREAD_INDICATOR_COLOR = new Color(0, 120, 255);
//     private final Color TITLE_COLOR = new Color(25, 25, 25);
//     private final Color DATE_COLOR = new Color(120, 120, 120);
//     private final Color BUTTON_COLOR = new Color(0, 120, 230);
//     private final Color BUTTON_TEXT_COLOR = new Color(255, 255, 255);
//     private final Color VERT_COLOR_1 = new Color(0x113F36);
//     private final Color VERT_COLOR_2 = new Color(0x128E64);
//     private final Color BG_COLOR = new Color(0xF2F2F2);
//     private final Color GRAY_COLOR = new Color(0xF1f1F1);
//     private final Font TITLE_FONT = new Font("Poppins", Font.BOLD, 14);
//     private final Font DATE_FONT = new Font("Poppins", Font.PLAIN, 12);
//     private final Font BUTTON_FONT = new Font("Poppins", Font.PLAIN, 12);
//     private final Font MESSAGE_FONT = new Font("Poppins", Font.PLAIN, 14);

//     private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//     private final ResponsablePedagogique responsable;
//     private final ResponsablePedagogiqueService rService;
//     private final NotificationService notificationService;

//     public NotificationCardFrame() {
//         setTitle("Centre de Notifications");
//         setSize(1500, 700);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setUndecorated(true);
//         setLocationRelativeTo(null);

//         try {
//             UIManager.setLookAndFeel(new NimbusLookAndFeel());
//         } catch (Exception e) {
//             e.printStackTrace();
//         }

//         //
//         Utilisateur user = SessionManager.getUtilisateur();
//         this.rService = new ResponsablePedagogiqueService();
//         this.notificationService = new NotificationService();
//         this.responsable = this.rService.getResponsableById(user.getId());

//         // Initialisation des données
//         notifications = notificationService.recupererNotifications(responsable.getEmail());

//         // Mise en place de l'interface
//         initComponents();
//         setupLayout();
//         createMessageDialog();
//     }

//     private void initComponents() {
//         // Container principal
//         mainContainer = new JPanel();
//         mainContainer.setLayout(new BorderLayout());
//         mainContainer.setBackground(BACKGROUND_COLOR);

//         // Utilisation de la classe Navbar existante
//         Navbar navbar = new Navbar(this);

//         // Création du panel de titre
//         JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         titlePanel.setBackground(new Color(248, 249, 252));
//         titlePanel.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)),
//                 BorderFactory.createEmptyBorder(15, 20, 15, 20)));

//         JLabel titleLabel = new JLabel("Centre de Notifications de l'UASZ");
//         titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
//         titleLabel.setForeground(new Color(40, 40, 40));
//         titlePanel.add(titleLabel);

//         // Container pour les cartes
//         cardsContainer = new JPanel();
//         cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
//         cardsContainer.setBackground(BACKGROUND_COLOR);
//         cardsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

//         // Scroll pane
//         JScrollPane scrollPane = new JScrollPane(cardsContainer);
//         scrollPane.setBorder(BorderFactory.createEmptyBorder());
//         scrollPane.getVerticalScrollBar().setUnitIncrement(16);
//         scrollPane.setBackground(BACKGROUND_COLOR);

//         // South Panel - Corrigé
//         JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         southPanel.setBackground(new Color(248, 249, 252));
//         southPanel.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 235)), // Bordure en haut au lieu du bas
//                 BorderFactory.createEmptyBorder(15, 20, 15, 20)));

//         JButton returnButton = new JButton("Return To Dashboard");
//         returnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
//         returnButton.setForeground(Color.WHITE); // Texte blanc pour un meilleur contraste
//         returnButton.setBackground(VERT_COLOR_1);
//         returnButton.setFocusPainted(false); // Enlève la bordure de focus
//         returnButton.setBorderPainted(false); // Enlève la bordure du bouton
//         returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur de main au survol
//         returnButton.setPreferredSize(new Dimension(200, 35)); // Taille fixe pour le bouton

//         // Ajout de l'action listener
//         returnButton.addActionListener(e -> navigateToDashboard());

//         southPanel.add(returnButton);

//         // Panel central qui contiendra le titre et le scrollPane
//         JPanel centerPanel = new JPanel(new BorderLayout());
//         centerPanel.setBackground(BACKGROUND_COLOR);
//         centerPanel.add(titlePanel, BorderLayout.NORTH);
//         centerPanel.add(scrollPane, BorderLayout.CENTER);
//         centerPanel.add(southPanel, BorderLayout.SOUTH);

//         // Ajout des composants au container principal
//         mainContainer.add(navbar, BorderLayout.NORTH);
//         mainContainer.add(centerPanel, BorderLayout.CENTER);

//         // Ajout du container principal au frame
//         setContentPane(mainContainer);
//     }

//     private void setupLayout() {
//         // Configuration du layout manager pour que les cartes s'adaptent à la largeur
//         cardsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
//     }

//     private void createMessageDialog() {
//         messageDialog = new JDialog(this, "Détails du message", true);
//         messageDialog.setUndecorated(true);
//         messageDialog.setSize(500, 400);
//         messageDialog.setLocationRelativeTo(this);
//         messageDialog.setLayout(new BorderLayout(10, 10));

//         JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));
//         dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//         dialogPanel.setBackground(Color.WHITE);

//         dialogTitleLabel = new JLabel();
//         dialogPanel.setForeground(GRAY_COLOR);
//         dialogTitleLabel.setFont(new Font("Poppins", Font.BOLD, 16));

//         dialogDateLabel = new JLabel();
//         dialogDateLabel.setFont(DATE_FONT);
//         dialogDateLabel.setForeground(DATE_COLOR);

//         dialogMessageArea = new JTextArea();
//         dialogMessageArea.setFont(MESSAGE_FONT);
//         dialogMessageArea.setForeground(Color.BLACK);
//         dialogMessageArea.setLineWrap(true);
//         dialogMessageArea.setWrapStyleWord(true);
//         dialogMessageArea.setEditable(false);
//         dialogMessageArea.setBackground(GRAY_COLOR);

//         JScrollPane messageScrollPane = new JScrollPane(dialogMessageArea);
//         messageScrollPane.setBorder(BorderFactory.createLineBorder(CARD_BORDER_COLOR));

//         JPanel headerPanel = new JPanel(new BorderLayout());
//         headerPanel.setBackground(Color.WHITE);
//         headerPanel.add(dialogTitleLabel, BorderLayout.NORTH);
//         headerPanel.add(dialogDateLabel, BorderLayout.SOUTH);
//         headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

//         JButton closeButton = new JButton("Fermer");
//         closeButton.addActionListener(e -> messageDialog.setVisible(false));
//         closeButton.setFont(BUTTON_FONT);

//         dialogPanel.add(headerPanel, BorderLayout.NORTH);
//         dialogPanel.add(messageScrollPane, BorderLayout.CENTER);
//         dialogPanel.add(closeButton, BorderLayout.SOUTH);

//         messageDialog.setContentPane(dialogPanel);
//     }

//     public void setNotifications() {
//         this.notifications =  notificationService.recupererNotifications(responsable.getEmail());
//         refreshNotifications();
//     }

//     public void refreshNotifications() {
//         // Supprimer tous les composants du conteneur
//         cardsContainer.removeAll();

//         // Vérifier si la liste des notifications est vide
//         if (notifications.isEmpty() || notifications == null) {
//             // Afficher un label indiquant qu'il n'y a pas de notifications
//             JLabel label = new JLabel("Aucune notification à afficher.");
//             label.setFont(new Font("Arial", Font.ITALIC, 14));
//             label.setForeground(Color.GRAY);
//             label.setHorizontalAlignment(SwingConstants.CENTER);

//             // Ajouter ce label dans le container
//             cardsContainer.add(label);
//         } else {
//             // Ajouter une carte pour chaque notification
//             for (Notification notification : notifications) {
//                 JPanel card = createNotificationCard(notification);
//                 cardsContainer.add(card);
//                 cardsContainer.add(Box.createVerticalStrut(10)); // Espace entre les cartes
//             }
//         }

//         // Revalider et repeindre le conteneur pour refléter les changements
//         cardsContainer.revalidate();
//         cardsContainer.repaint();
//     }

//     private JPanel createNotificationCard(Notification notification) {
//         JPanel card = new JPanel();
//         card.setLayout(new BorderLayout(5, 5));
//         card.setBackground(CARD_COLOR);
//         card.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(CARD_BORDER_COLOR, 1),
//                 BorderFactory.createEmptyBorder(12, 15, 12, 15)));
//         card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

//         // Container pour le titre et l'indicateur de notification non lue
//         JPanel titleContainer = new JPanel(new BorderLayout(5, 0));
//         titleContainer.setBackground(CARD_COLOR);

//         // Titre de la notification
//         JLabel titleLabel = new JLabel(notification.getTitre());
//         titleLabel.setFont(TITLE_FONT);
//         titleLabel.setForeground(TITLE_COLOR);

//         // Ajout de l'indicateur de notification non lue si nécessaire
//         if (!notification.isLue()) {
//             JPanel unreadPanel = new JPanel();
//             unreadPanel.setPreferredSize(new Dimension(8, 8));
//             unreadPanel.setBackground(UNREAD_INDICATOR_COLOR);
//             unreadPanel.setBorder(null);

//             JPanel indicatorContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
//             indicatorContainer.setBackground(CARD_COLOR);
//             indicatorContainer.add(unreadPanel);

//             titleContainer.add(indicatorContainer, BorderLayout.WEST);
//         }

//         titleContainer.add(titleLabel, BorderLayout.CENTER);

//         // Date d'envoi
//         JLabel dateLabel = new JLabel("Envoyé le " + notification.getDateEnvoi().format(dateFormatter));
//         dateLabel.setFont(DATE_FONT);
//         dateLabel.setForeground(DATE_COLOR);

//         // Bouton "Voir message"
//         JButton viewButton = new JButton("Voir message");
//         viewButton.setFont(BUTTON_FONT);
//         viewButton.setForeground(BUTTON_TEXT_COLOR);
//         viewButton.setBackground(VERT_COLOR_1);
//         viewButton.setBorderPainted(false);
//         viewButton.setFocusPainted(false);
//         viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
//         viewButton.addActionListener(e -> showMessageDetails(notification));

//         // Panel pour la date et le bouton (alignés respectivement à gauche et à droite)
//         JPanel bottomPanel = new JPanel(new BorderLayout());
//         bottomPanel.setBackground(CARD_COLOR);
//         bottomPanel.add(dateLabel, BorderLayout.WEST);
//         bottomPanel.add(viewButton, BorderLayout.EAST);

//         // Assemblage de la carte
//         card.add(titleContainer, BorderLayout.NORTH);
//         card.add(bottomPanel, BorderLayout.SOUTH);

//         return card;
//     }

//     private void showMessageDetails(Notification notification) {
//         // Définir le contenu de la boîte de dialogue

//         dialogTitleLabel.setText(notification.getTitre());
//         dialogTitleLabel.setForeground(TITLE_COLOR);
//         dialogMessageArea.setText(notification.getMessage());
//         dialogDateLabel.setText("Envoyé le " + notification.getDateEnvoi().format(dateFormatter));

//         notificationService.marquerNotificationCommeLue(notification.getId());

//         // Afficher la boîte de dialogue
//         messageDialog.setVisible(true);
//         refreshNotifications();
//     }

//     public void afficher() {
//         this.setVisible(true);
//     }

//     public void fermer() {
//         this.dispose();
//     }

//     private void navigateToDashboard() {
//         try {
//             DashboardResponsableUI homePage = new DashboardResponsableUI();
//             homePage.afficher();
//             fermer();
//         } catch (Exception exp) {
//             System.err.println(exp.getMessage());
//             exp.printStackTrace();
//         }
//     }
//     // Méthode principale pour tester
//     // public static void main(String[] args) {
//     // try {
//     // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//     // } catch (Exception e) {
//     // e.printStackTrace();
//     // }

//     // SwingUtilities.invokeLater(() -> {
//     // NotificationCardFrame frame = new NotificationCardFrame();

//     // // // Données de test
//     // // List<Notification> testNotifications = new ArrayList<>();
//     // // testNotifications.add(new Notification(1L, "user1@uasz.edu.sn",
//     // "Confirmation d'inscription",
//     // // "Votre inscription a été confirmée avec succès. Veuillez vous présenter au
//     // bureau des étudiants pour récupérer votre carte d'étudiant.",
//     // // false, java.time.LocalDate.now()));
//     // // testNotifications.add(new Notification(2L, "user1@uasz.edu.sn", "Rappel de
//     // paiement",
//     // // "Nous vous rappelons que le paiement des frais de scolarité doit être
//     // effectué avant le 30 mars 2025.",
//     // // true, java.time.LocalDate.now().minusDays(2)));
//     // // testNotifications.add(new Notification(3L, "user1@uasz.edu.sn",
//     // "Modification d'emploi du temps",
//     // // "Le cours de Java Avancé du jeudi 13 mars est déplacé au vendredi 14 mars
//     // de 14h à 17h en salle 205.",
//     // // false, java.time.LocalDate.now().minusDays(1)));
//     // // testNotifications.add(new Notification(4L, "user1@uasz.edu.sn", "Fermeture
//     // pour jour férié",
//     // // "Nous vous informons que l'université sera fermée le lundi 17 mars en
//     // raison d'un jour férié.",
//     // // false, java.time.LocalDate.now().minusDays(3)));

//     // // frame.setNotifications(testNotifications);
//     // frame.setVisible(true);
//     // });
//     // }
// }

package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import sn.uasz.m1.inscription.email.model.Notification;
import sn.uasz.m1.inscription.email.service.NotificationService;
import sn.uasz.m1.inscription.model.ResponsablePedagogique;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.service.ResponsablePedagogiqueService;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.components.Navbar;
import sn.uasz.m1.inscription.view.components.NavbarPanel;

public class NotificationCardFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel mainContainer;
    private JPanel cardsContainer;
    private JDialog messageDialog;
    private JLabel dialogTitleLabel;
    private JTextArea dialogMessageArea;
    private JLabel dialogDateLabel;

    private List<Notification> notifications;

    // Couleurs et styles
    private final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private final Color CARD_COLOR = new Color(255, 255, 255);
    private final Color CARD_BORDER_COLOR = new Color(230, 230, 235);
    private final Color UNREAD_INDICATOR_COLOR = new Color(0, 120, 255);
    private final Color TITLE_COLOR = new Color(25, 25, 25);
    private final Color DATE_COLOR = new Color(120, 120, 120);
    private final Color BUTTON_COLOR = new Color(0, 120, 230);
    private final Color BUTTON_TEXT_COLOR = new Color(255, 255, 255);
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF2F2F2);
    private final Color GRAY_COLOR = new Color(0xF1f1F1);
    private final Font TITLE_FONT = new Font("Poppins", Font.BOLD, 14);
    private final Font DATE_FONT = new Font("Poppins", Font.PLAIN, 12);
    private final Font BUTTON_FONT = new Font("Poppins", Font.PLAIN, 12);
    private final Font MESSAGE_FONT = new Font("Poppins", Font.PLAIN, 14);

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final ResponsablePedagogique responsable;
    private final ResponsablePedagogiqueService rService;
    private final NotificationService notificationService;

    public NotificationCardFrame() {
        setTitle("Centre de Notifications");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Récupération de l'utilisateur courant
        Utilisateur user = SessionManager.getUtilisateur();
        this.rService = new ResponsablePedagogiqueService();
        this.notificationService = new NotificationService();
        this.responsable = this.rService.getResponsableById(user.getId());

        // Initialisation des composants UI
        initComponents();
        setupLayout();
        createMessageDialog();

        // Chargement des notifications depuis la base de données
        loadNotificationsFromDatabase();
    }

    /**
     * Charge les notifications depuis la base de données
     */
    private void loadNotificationsFromDatabase() {
        try {
            // Récupération des notifications pour l'utilisateur courant
            notifications = notificationService.recupererNotifications(responsable.getEmail());

            // Rafraîchir l'affichage des notifications
            refreshNotifications();

            System.out.println("Notifications chargées: " + notifications.size());
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement des notifications: " + e.getMessage());
            e.printStackTrace();

            // Initialiser avec une liste vide en cas d'erreur
            notifications = new ArrayList<>();
            refreshNotifications();
        }
    }

    private void initComponents() {
        // Container principal
        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(BACKGROUND_COLOR);

        // Utilisation de la classe Navbar existante
        Navbar navbar = new Navbar(this);

        // Création du panel de titre
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(248, 249, 252));
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel titleLabel = new JLabel("Centre de Notifications de l'UASZ");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(40, 40, 40));
        titlePanel.add(titleLabel);

        // Container pour les cartes
        cardsContainer = new JPanel();
        cardsContainer.setLayout(new BoxLayout(cardsContainer, BoxLayout.Y_AXIS));
        cardsContainer.setBackground(BACKGROUND_COLOR);
        cardsContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(cardsContainer);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(BACKGROUND_COLOR);

        // Bouton de rafraîchissement des notifications
        JButton refreshButton = new JButton("Rafraîchir");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(VERT_COLOR_2);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshButton.setPreferredSize(new Dimension(120, 35));
        refreshButton.addActionListener(e -> loadNotificationsFromDatabase());

        // South Panel - Corrigé
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        southPanel.setBackground(new Color(248, 249, 252));
        southPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 235)),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JButton returnButton = new JButton("Return To Dashboard");
        returnButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        returnButton.setForeground(Color.WHITE);
        returnButton.setBackground(VERT_COLOR_1);
        returnButton.setFocusPainted(false);
        returnButton.setBorderPainted(false);
        returnButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        returnButton.setPreferredSize(new Dimension(200, 35));
        returnButton.addActionListener(e -> navigateToDashboard());

        southPanel.add(refreshButton);
        southPanel.add(returnButton);

        // Panel central qui contiendra le titre et le scrollPane
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(titlePanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(southPanel, BorderLayout.SOUTH);

        // Ajout des composants au container principal
        mainContainer.add(navbar, BorderLayout.NORTH);
        mainContainer.add(centerPanel, BorderLayout.CENTER);

        // Ajout du container principal au frame
        setContentPane(mainContainer);
    }

    private void setupLayout() {
        // Configuration du layout manager pour que les cartes s'adaptent à la largeur
        cardsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void createMessageDialog() {
        messageDialog = new JDialog(this, "Détails du message", true);
        messageDialog.setUndecorated(true);
        messageDialog.setSize(500, 400);
        messageDialog.setLocationRelativeTo(this);
        messageDialog.setLayout(new BorderLayout(10, 10));

        JPanel dialogPanel = new JPanel(new BorderLayout(10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dialogPanel.setBackground(Color.WHITE);

        dialogTitleLabel = new JLabel();
        dialogPanel.setForeground(GRAY_COLOR);
        dialogTitleLabel.setFont(new Font("Poppins", Font.BOLD, 16));

        dialogDateLabel = new JLabel();
        dialogDateLabel.setFont(DATE_FONT);
        dialogDateLabel.setForeground(DATE_COLOR);

        dialogMessageArea = new JTextArea();
        dialogMessageArea.setFont(MESSAGE_FONT);
        dialogMessageArea.setForeground(Color.BLACK);
        dialogMessageArea.setLineWrap(true);
        dialogMessageArea.setWrapStyleWord(true);
        dialogMessageArea.setEditable(false);
        dialogMessageArea.setBackground(GRAY_COLOR);

        JScrollPane messageScrollPane = new JScrollPane(dialogMessageArea);
        messageScrollPane.setBorder(BorderFactory.createLineBorder(CARD_BORDER_COLOR));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.add(dialogTitleLabel, BorderLayout.NORTH);
        headerPanel.add(dialogDateLabel, BorderLayout.SOUTH);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> messageDialog.setVisible(false));
        closeButton.setFont(BUTTON_FONT);
        closeButton.setBackground(VERT_COLOR_1);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);

        dialogPanel.add(headerPanel, BorderLayout.NORTH);
        dialogPanel.add(messageScrollPane, BorderLayout.CENTER);
        dialogPanel.add(closeButton, BorderLayout.SOUTH);

        messageDialog.setContentPane(dialogPanel);
    }

    public void setNotifications() {
        // Cette méthode publique permet de rafraîchir les notifications
        loadNotificationsFromDatabase();
    }

    public void refreshNotifications() {
        // Supprimer tous les composants du conteneur
        cardsContainer.removeAll();

        // Vérifier si la liste des notifications est vide
        if (notifications == null || notifications.isEmpty()) {
            // Afficher un label indiquant qu'il n'y a pas de notifications
            JLabel label = new JLabel("Aucune notification à afficher.");
            label.setFont(new Font("Arial", Font.ITALIC, 14));
            label.setForeground(Color.GRAY);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Centrer le label dans le container
            JPanel centerPanel = new JPanel(new GridBagLayout());
            centerPanel.setBackground(BACKGROUND_COLOR);
            centerPanel.add(label);
            cardsContainer.add(centerPanel);
        } else {
            // Ajouter une carte pour chaque notification
            for (Notification notification : notifications) {
                JPanel card = createNotificationCard(notification);
                cardsContainer.add(card);
                cardsContainer.add(Box.createVerticalStrut(10)); // Espace entre les cartes
            }
        }

        // Revalider et repeindre le conteneur pour refléter les changements
        cardsContainer.revalidate();
        cardsContainer.repaint();
    }

    private JPanel createNotificationCard(Notification notification) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(5, 5));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(CARD_BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Container pour le titre et l'indicateur de notification non lue
        JPanel titleContainer = new JPanel(new BorderLayout(5, 0));
        titleContainer.setBackground(CARD_COLOR);

        // Titre de la notification
        JLabel titleLabel = new JLabel(notification.getTitre());
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TITLE_COLOR);

        // Ajout de l'indicateur de notification non lue si nécessaire
        if (!notification.isLue()) {
            JPanel unreadPanel = new JPanel();
            unreadPanel.setPreferredSize(new Dimension(8, 8));
            unreadPanel.setBackground(UNREAD_INDICATOR_COLOR);
            unreadPanel.setBorder(null);

            JPanel indicatorContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            indicatorContainer.setBackground(CARD_COLOR);
            indicatorContainer.add(unreadPanel);

            titleContainer.add(indicatorContainer, BorderLayout.WEST);
        }

        titleContainer.add(titleLabel, BorderLayout.CENTER);

        // Date d'envoi
        JLabel dateLabel = new JLabel("Envoyé le " + notification.getDateEnvoi().format(dateFormatter));
        dateLabel.setFont(DATE_FONT);
        dateLabel.setForeground(DATE_COLOR);

        // Bouton "Voir message"
        JButton viewButton = new JButton("Voir message");
        viewButton.setFont(BUTTON_FONT);
        viewButton.setForeground(BUTTON_TEXT_COLOR);
        viewButton.setBackground(VERT_COLOR_1);
        viewButton.setBorderPainted(false);
        viewButton.setFocusPainted(false);
        viewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        viewButton.addActionListener(e -> showMessageDetails(notification));

        // Panel pour la date et le bouton (alignés respectivement à gauche et à droite)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(CARD_COLOR);
        bottomPanel.add(dateLabel, BorderLayout.WEST);
        bottomPanel.add(viewButton, BorderLayout.EAST);

        // Assemblage de la carte
        card.add(titleContainer, BorderLayout.NORTH);
        card.add(bottomPanel, BorderLayout.SOUTH);

        return card;
    }

    private void showMessageDetails(Notification notification) {
        // Définir le contenu de la boîte de dialogue

        dialogTitleLabel.setText(notification.getTitre());
        dialogTitleLabel.setForeground(TITLE_COLOR);
        dialogMessageArea.setText(notification.getMessage());
        dialogDateLabel.setText("Envoyé le " + notification.getDateEnvoi().format(dateFormatter));

        notificationService.marquerNotificationCommeLue(notification.getId());

        // Afficher la boîte de dialogue
        messageDialog.setVisible(true);
        refreshNotifications();
    }

    public void afficher() {
        this.setVisible(true);
    }

    public void fermer() {
        this.dispose();
    }

    private void navigateToDashboard() {
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