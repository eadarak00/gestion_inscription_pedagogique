// package sn.uasz.m1.inscription.view.ResponsablePedagogique;

// import javax.swing.*;
// import javax.swing.plaf.nimbus.NimbusLookAndFeel;
// import javax.swing.table.DefaultTableModel;
// import javax.swing.table.TableRowSorter;

// import java.awt.*;
// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;

// import sn.uasz.m1.inscription.controller.GroupeController;
// import sn.uasz.m1.inscription.model.Formation;
// import sn.uasz.m1.inscription.model.Groupe;
// import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;
// import sn.uasz.m1.inscription.view.HomeUI;
// import sn.uasz.m1.inscription.view.LoginUI;

// public class FormationGroupeUI extends JFrame {

//     private static final Color VERT_COLOR_1 = new Color(0x113F36);
//     private static final Color RED_COLOR = new Color(0xcc1a1a);
//     private static final Color FOND_COLOR = new Color(0xF5F5F0);
//     private static final Color GRAY_COLOR = new Color(0xefefef);

//     private JTable table;
//     private DefaultTableModel tableModel;
//     private GroupeController groupeController;
//     private Formation formation;

//     public FormationGroupeUI(Formation formation) {
//           try {
//             UIManager.setLookAndFeel(new NimbusLookAndFeel());
//         } catch (Exception e) {
//             System.out.println(e.getMessage());
//         }
//         this.groupeController = new GroupeController();
//         this.formation = formation;

//         // Setup Frame
//         setTitle("Groupes de la Formation");
//         setLayout(new BorderLayout());
//         setSize(1500, 700);
//         setLocationRelativeTo(null);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setBackground(FOND_COLOR);

//         // Add Components
//         add(createPanelNorth(), BorderLayout.NORTH);
//         add(createContentPanel(), BorderLayout.CENTER);

//         // Load Groups
//         loadGroups();
//     }

//     // Main content panel (center)
//     private JPanel createContentPanel() {
//         JPanel contentPanel = new JPanel(new GridBagLayout());
//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.fill = GridBagConstraints.HORIZONTAL;
//         gbc.gridx = 0;
//         gbc.weightx = 1.0;
//         gbc.insets = new Insets(5, 10, 5, 10);

//         contentPanel.add(createTopPanel(), gbc);
//         contentPanel.add(createTablePanel(), gbc);
//         contentPanel.add(createActionButtonsPanel(), gbc);
//         gbc.fill = GridBagConstraints.NONE;
//         gbc.weighty = 0.3;  
//         contentPanel.add(createRetourButton(), gbc);

//         return contentPanel;
//     }

//     // North Panel (Top)
//     private JPanel createPanelNorth() {
//         JPanel panel = new JPanel(new BorderLayout());
//         panel.setBackground(GRAY_COLOR);

//         // Logout and Return buttons
//         panel.add(createLogoutPanel(), BorderLayout.EAST);
//         panel.add(createReturnPanel(), BorderLayout.WEST);

//         return panel;
//     }

//     private JPanel createLogoutPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//         JButton logoutButton = new JButton(createIcon("src/main/resources/static/img/png/logout.png"));
//         logoutButton.setBackground(RED_COLOR);
//         logoutButton.addActionListener(e -> navigateToLogin());
//         panel.add(logoutButton);
//         return panel;
//     }

//     private JPanel createReturnPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
//         JButton returnButton = new JButton(createIcon("src/main/resources/static/img/png/return.png"));
//         returnButton.setBackground(VERT_COLOR_1);
//         returnButton.addActionListener(e -> navigateToHome());
//         panel.add(returnButton);
//         return panel;
//     }

//     private ImageIcon createIcon(String path) {
//         ImageIcon icon = new ImageIcon(path);
//         Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
//         return new ImageIcon(img);
//     }

//     // Top Panel with title and buttons (filter, add, sort)
//     private JPanel createTopPanel() {
//         JPanel panel = new JPanel(new BorderLayout());

//         // Title
//         JLabel titleLabel = new JLabel("Groupes de la Formation : " + formation.getLibelle(), SwingConstants.CENTER);
//         titleLabel.setFont(new Font("Poppins", Font.BOLD, 18));
//         titleLabel.setForeground(VERT_COLOR_1);
//         panel.add(titleLabel, BorderLayout.NORTH);

//         // Action buttons panel
//         JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//         buttonsPanel.add(createSortAscButton());
//         buttonsPanel.add(createSortDescButton());
//         buttonsPanel.add(createFilterButton());
//         buttonsPanel.add(createAddGroupButton());

//         panel.add(buttonsPanel, BorderLayout.EAST);

//         return panel;
//     }

//     private JButton createSortAscButton() {
//         JButton button = new JButton("🔼 Trier (A-Z)");
//         button.setFont(new Font("Poppins", Font.BOLD, 14));
//         button.setForeground(Color.BLACK);
//         button.addActionListener(e -> loadGroups(true));
//         return button;
//     }

//     private JButton createSortDescButton() {
//         JButton button = new JButton("🔽 Trier (Z-A)");
//         button.setFont(new Font("Poppins", Font.BOLD, 14));
//         button.setForeground(Color.BLACK);
//         button.addActionListener(e -> loadGroups(false));
//         return button;
//     }

//     private JButton createFilterButton() {
//         JButton button = new JButton(createIcon("src/main/resources/static/img/png/filter.png"));
//         button.setFont(new Font("Poppins", Font.BOLD, 14));
//         button.setForeground(Color.WHITE); // White text color
//         button.setBackground(VERT_COLOR_1); // Background color
//         button.setToolTipText("Filtrer les groupes");
//         button.addActionListener(e -> filterTable());
//         return button;
//     }

//     private JButton createAddGroupButton() {
//         JButton button = new JButton("+ Ajouter Groupe");
//         button.setFont(new Font("Poppins", Font.BOLD, 14));
//         button.setForeground(Color.WHITE); // White text color
//         button.setBackground(VERT_COLOR_1); // Background color
//         button.addActionListener(e -> openAddGroupModal());
//         return button;
//     }

//     private JButton createRetourButton() {
//         JButton retourButton = new JButton("⬅ Retour");
//         retourButton.setFont(new Font("Poppins", Font.BOLD, 14));
//         retourButton.setForeground(Color.WHITE); // White text color
//         retourButton.setBackground(VERT_COLOR_1); // Background color
//         retourButton.addActionListener(e -> navigateToDashboard());
//         return retourButton;
//     }

//     // Table Panel
//     private JPanel createTablePanel() {
//         JPanel panel = new JPanel(new BorderLayout());
//         String[] columnNames = { "ID", "Capacité", "Type" };
//         tableModel = new DefaultTableModel(columnNames, 0);
//         table = new JTable(tableModel);
//         table.setRowHeight(30);
//         table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//         JScrollPane scrollPane = new JScrollPane(table);
//         panel.add(scrollPane, BorderLayout.CENTER);

//         return panel;
//     }

//     // Action Buttons Panel
//     private JPanel createActionButtonsPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         ImageIcon modify_icon = new ImageIcon("src/main/resources/static/img/png/edit.png");
//         Image modifynImage = modify_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
//         ImageIcon modifyIcon = new ImageIcon(modifynImage);
//         JButton modifyButton = new JButton(modifyIcon);
//         modifyButton.setBackground(VERT_COLOR_1);
//         modifyButton.setForeground(Color.WHITE);
//         modifyButton.addActionListener(e -> {
//             int row = table.getSelectedRow();
//             editGroup(row);
//         });

//         ImageIcon delete_icon = new ImageIcon("src/main/resources/static/img/png/delete.png");
//         Image deleteImage = delete_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
//         ImageIcon deleteIcon = new ImageIcon(deleteImage);
//         JButton deleteButton = new JButton(deleteIcon);
//         deleteButton.setBackground(RED_COLOR);
//         deleteButton.setForeground(Color.WHITE);
//         deleteButton.addActionListener(e -> {
//             int row = table.getSelectedRow();
//             deletedGroup(row);
//         });

//         panel.add(modifyButton);
//         panel.add(deleteButton);

//         return panel;
//     }

//     // Table filtering and loading data
//     private void filterTable() {
//         int rowCount = tableModel.getRowCount();
//         List<Object[]> data = new ArrayList<>();

//         for (int i = 0; i < rowCount; i++) {
//             data.add(new Object[] {
//                     tableModel.getValueAt(i, 0),
//                     tableModel.getValueAt(i, 1),
//                     tableModel.getValueAt(i, 2),
//             });
//         }

//         // Trier les données par nive
//         data.sort(Comparator.comparingInt(o -> Integer.parseInt(o[1].toString())));

//         // Réinsérer les données triées dans le modèle
//         tableModel.setRowCount(0);
//         for (Object[] row : data) {
//             tableModel.addRow(row);
//         }
//     }

//     private void loadGroups() {
//         loadGroups(true); // Load by default in ascending order
//     }

//     private void loadGroups(boolean ascending) {
//         tableModel.setRowCount(0); 

//         List<Groupe> groupes = groupeController.getFormationGroupesTriesParType(ascending, formation);

//         for (Groupe groupe : groupes) {
//             tableModel.addRow(new Object[] {
//                     groupe.getId(),
//                     groupe.getCapacite(),
//                     groupe.getType(),
//             });
//         }
//     }

//     // Add group modal
//     private void openAddGroupModal() {
//         JDialog dialog = new JDialog(this, "Ajouter un Groupe", true);
//         dialog.setSize(600, 300);
//         dialog.setLayout(new GridBagLayout());
//         dialog.setLocationRelativeTo(this);

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.gridx = 0;
//         gbc.gridy = 0;

//         // Capacite field
//         dialog.add(new JLabel("Capacité :"), gbc);
//         gbc.gridx = 1;
//         JTextField capaciteField = new JTextField(10);
//         dialog.add(capaciteField, gbc);

//         // Type field
//         gbc.gridx = 0;
//         gbc.gridy++;
//         dialog.add(new JLabel("Type :"), gbc);
//         gbc.gridx = 1;
//         JComboBox<TypeGroupe> typeComboBox = new JComboBox<>(TypeGroupe.values());
//         dialog.add(typeComboBox, gbc);

//         // Formation label
//         gbc.gridx = 0;
//         gbc.gridy++;
//         dialog.add(new JLabel("Formation :"), gbc);
//         gbc.gridx = 1;
//         dialog.add(new JLabel(formation.getLibelle()), gbc);

//         // Buttons panel
//         JPanel buttonPanel = new JPanel();
//         JButton saveButton = new JButton("Enregistrer");
//         saveButton.setBackground(VERT_COLOR_1);
//         saveButton.addActionListener(e -> saveGroup(capaciteField, typeComboBox, dialog));

//         JButton cancelButton = new JButton("Annuler");
//         cancelButton.setBackground(RED_COLOR);
//         cancelButton.addActionListener(e -> dialog.dispose());

//         buttonPanel.add(saveButton);
//         buttonPanel.add(cancelButton);
//         gbc.gridx = 0;
//         gbc.gridy++;
//         dialog.add(buttonPanel, gbc);

//         dialog.setVisible(true);
//     }

//     private void saveGroup(JTextField capaciteField, JComboBox<TypeGroupe> typeComboBox, JDialog dialog) {
//         try {
//             int capacity = Integer.parseInt(capaciteField.getText());
//             TypeGroupe type = (TypeGroupe) typeComboBox.getSelectedItem();
//             String message = groupeController.ajouterGroupe(capacity, type, formation);
//             JOptionPane.showMessageDialog(dialog, message, "Ajout Groupe", JOptionPane.INFORMATION_MESSAGE);
//             loadGroups();
//             dialog.dispose();
//         } catch (NumberFormatException ex) {
//             JOptionPane.showMessageDialog(dialog, "La capacité doit être un nombre valide.", "Erreur",
//                     JOptionPane.ERROR_MESSAGE);
//         }
//     }

//     // Modify group method
//     private void editGroup(int row) {
//         if (row != -1) {
//             Long groupeId = (Long) table.getValueAt(row, 0);

//             // Récupérer le groupe à partir de son ID
//             Groupe groupe = groupeController.trouverGroupeParId(groupeId);
//             if (groupe == null) {
//                 JOptionPane.showMessageDialog(this, "Groupe introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
//                 return;
//             }
    
//             // Créer le modal pour modifier le groupe
//             JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier un Groupe", true);
//             dialog.setSize(600, 300);
//             dialog.setLayout(new GridBagLayout());
//             dialog.setLocationRelativeTo(this);
    
//             // Définir le style et les contraintes du layout
//             Font font = new Font("Poppins", Font.PLAIN, 14);
//             GridBagConstraints gbc = new GridBagConstraints();
//             gbc.insets = new Insets(10, 10, 10, 10);
//             gbc.anchor = GridBagConstraints.WEST;
//             gbc.gridx = 0;
//             gbc.gridy = 0;
    
//             // Champs du formulaire
//             JLabel capaciteLabel = new JLabel("Capacité :");
//             capaciteLabel.setFont(font);
//             dialog.add(capaciteLabel, gbc);
//             gbc.gridx = 1;
//             JTextField capaciteField = new JTextField(20);
//             capaciteField.setFont(font);
//             capaciteField.setText(String.valueOf(groupe.getCapacite()));
//             dialog.add(capaciteField, gbc);
    
//             gbc.gridx = 0;
//             gbc.gridy++;
//             JLabel typeLabel = new JLabel("Type :");
//             typeLabel.setFont(font);
//             dialog.add(typeLabel, gbc);
//             gbc.gridx = 1;
//             JComboBox<TypeGroupe> typeComboBox = new JComboBox<>(TypeGroupe.values());
//             typeComboBox.setSelectedItem(groupe.getType());
//             dialog.add(typeComboBox, gbc);
    
//             // Boutons
//             gbc.gridx = 0;
//             gbc.gridy++;
//             gbc.gridwidth = 2;
//             gbc.anchor = GridBagConstraints.CENTER;
    
//             JButton enregistrerButton = new JButton("Enregistrer");
//             enregistrerButton.setBackground(VERT_COLOR_1);
//             enregistrerButton.setForeground(Color.WHITE);
//             enregistrerButton.setFont(new Font("Poppins", Font.BOLD, 14));
    
//             JButton annulerButton = new JButton("Annuler");
//             annulerButton.setBackground(RED_COLOR);
//             annulerButton.setForeground(Color.WHITE);
//             annulerButton.setFont(new Font("Poppins", Font.BOLD, 14));
    
//             JPanel buttonPanel = new JPanel();
//             buttonPanel.add(enregistrerButton);
//             buttonPanel.add(annulerButton);
    
//             dialog.add(buttonPanel, gbc);
    
//             // Action sur le bouton "Enregistrer"
//             enregistrerButton.addActionListener(e -> {
//                 try {
//                     int capacite = Integer.parseInt(capaciteField.getText());
//                     TypeGroupe type = (TypeGroupe) typeComboBox.getSelectedItem();
    
//                     // Vérification des valeurs
//                     if (capacite <= 0) {
//                         JOptionPane.showMessageDialog(dialog, "La capacité doit être un entier supérieur à 0.", "Erreur",
//                                 JOptionPane.ERROR_MESSAGE);
//                         return;
//                     }
    
//                     // Mise à jour du groupe via `GroupeController`
//                     groupe.setCapacite(capacite);
//                     groupe.setType(type);
//                     String message = groupeController.modifierGroupe(groupeId, groupe);
//                     JOptionPane.showMessageDialog(dialog, message, "Modification Groupe", JOptionPane.INFORMATION_MESSAGE);
    
//                     // Rafraîchir la liste des groupes
//                     loadGroups();
    
//                     // Fermer le modal
//                     dialog.dispose();
//                 } catch (NumberFormatException ex) {
//                     JOptionPane.showMessageDialog(dialog, "La capacité doit être un nombre valide.", "Erreur",
//                             JOptionPane.ERROR_MESSAGE);
//                 }
//             });
    
//             // Bouton "Annuler"
//             annulerButton.addActionListener(e -> dialog.dispose());
    
//             // Affichage du modal
//             dialog.setVisible(true);
//         } else {
//             JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
//                     JOptionPane.WARNING_MESSAGE);
//         }
//     }

//     // Delete group method
//     private void deletedGroup(int row) {
//         if (row != -1) {
//             Long groupId = (Long) table.getValueAt(row, 0); // Get group ID from selected row
//             int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation",
//                     JOptionPane.YES_NO_OPTION);

//             if (confirm == JOptionPane.YES_OPTION) {
//                 String message = groupeController.supprimerGroupe(groupId);
//                 JOptionPane.showMessageDialog(this, message, "Suppression", JOptionPane.INFORMATION_MESSAGE);
//                 loadGroups();
//             }
//         } else {
//             JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
//                     JOptionPane.WARNING_MESSAGE);
//         }
//     }

//     public void afficher(){
//         setVisible(true);
//     }

//     public void fermer(){
//         dispose();
//     }

//     // Navigation Methods
//     private void navigateToLogin() {
//         LoginUI loginUI = new LoginUI();
//         loginUI.setVisible(true);
//         this.dispose();
//     }

//     private void navigateToHome() {
//         HomeUI homeUI = new HomeUI();
//         homeUI.setVisible(true);
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


// }


package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import sn.uasz.m1.inscription.controller.GroupeController;
import sn.uasz.m1.inscription.controller.UEController;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;
import sn.uasz.m1.inscription.view.components.IconUI;
import sn.uasz.m1.inscription.view.components.Navbar;

import java.awt.*;
import java.awt.event.*;

public class FormationGroupeUI extends JFrame {
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
    private static final Font HEADER_FONT = new Font("Poppins", Font.BOLD, 16);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Poppins", Font.BOLD, 13);
    private static final Font TABLE_HEADER_FONT = new Font("Poppins", Font.BOLD, 14);

    // 🏗 Composants principaux
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;
    private JTextField searchField;
    private JLabel statusLabel;

    // Controller et Service
    private final Formation formation;
    private final GroupeController groupeController;
    private final JPanel bottomPanel;

    public FormationGroupeUI(Formation formation) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.formation = formation;
        this.groupeController = new GroupeController();

        setTitle("Lister Groupes");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        Navbar navbar = new Navbar(this);

        add(navbar, BorderLayout.NORTH);
        add(createPrincipalPanel(), BorderLayout.CENTER);
        bottomPanel = createBottomPanel();
        bottomPanel.setVisible(false);
        add(bottomPanel, BorderLayout.SOUTH);

        chargerGroupes();

    }

    /** Personnalisation globale de l'UI */
    private void customizeUIDefaults() {
        UIManager.put("Button.arc", 8);
        UIManager.put("Component.arc", 8);
        UIManager.put("ProgressBar.arc", 8);
        UIManager.put("Button.margin", new Insets(8, 16, 8, 16));
        UIManager.put("TextField.caretForeground", VERT_COLOR_2);
        UIManager.put("TextField.selectionBackground",
                new Color(VERT_COLOR_2.getRed(), VERT_COLOR_2.getGreen(), VERT_COLOR_2.getBlue(), 80));
        UIManager.put("Table.selectionBackground",
                new Color(VERT_COLOR_2.getRed(), VERT_COLOR_2.getGreen(), VERT_COLOR_2.getBlue(), 40));
        UIManager.put("Table.selectionForeground", TEXT_COLOR);
        UIManager.put("Table.intercellSpacing", new Dimension(10, 5));
        UIManager.put("Table.showGrid", false);
    }

    private JPanel createPrincipalPanel() {
        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(BG_COLOR);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

        main.add(createHeader(), BorderLayout.NORTH);
        main.add(createMainContent(), BorderLayout.CENTER);

        return main;
    }

    /** 🏗 Crée l'en-tête de l'application */

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Gestion des Groupes");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(VERT_COLOR_1);

        statusLabel = new JLabel("Prêt");
        statusLabel.setFont(REGULAR_FONT);
        statusLabel.setForeground(new Color(0x666666));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(statusLabel, BorderLayout.EAST);

        return headerPanel;
    }

    /** 🏗 Crée le contenu principal */
    private JPanel createMainContent() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setOpaque(false);

        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createActionButtonsPanel(), BorderLayout.SOUTH);

        return mainPanel;
    }

    /** 🏗 Crée le panneau supérieur avec recherche et boutons */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Première ligne: titre et bouton ajouter
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);

        JButton addUEButton = createButton("+ Ajouter Groupe", VERT_COLOR_1, Color.WHITE,
                e -> ajouterGroupe());

        headerRow.add(addUEButton, BorderLayout.EAST);

        // Deuxième ligne: recherche et filtres
        JPanel controlsRow = new JPanel(new BorderLayout(10, 0));
        controlsRow.setOpaque(false);
        // controlsRow.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel searchPanel = createSearchPanel();
        JPanel filtersPanel = createFiltersPanel();

        controlsRow.add(searchPanel, BorderLayout.WEST);
        controlsRow.add(filtersPanel, BorderLayout.EAST);

        topPanel.add(headerRow);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(controlsRow);

        return topPanel;
    }

    /** 🏗 Crée un panneau de recherche moderne */
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);

        searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher un groupe...");
        searchField.setFont(REGULAR_FONT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrerGroupes(searchField.getText());
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerGroupes(searchField.getText());
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerGroupes(searchField.getText());
            }
        });

        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchField, BorderLayout.CENTER);

        // Icône de recherche
        JLabel searchIcon = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/search.png", 25,
                25));
        searchIcon.setBorder(new EmptyBorder(0, 0, 0, 10));
        searchContainer.add(searchIcon, BorderLayout.EAST);

        panel.add(searchContainer);
        return panel;
    }

    /** 🏗 Crée un bouton moderne avec texte et icône */
    private JButton createIconTextButton(String text, Icon icon, Color bgColor, Color fgColor,
            java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setFont(BUTTON_FONT);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 1, true),
                new EmptyBorder(8, 16, 8, 16)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        if (listener != null) {
            button.addActionListener(listener);
        }

        return button;
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setOpaque(false);

        JButton refreshButton = createIconTextButton("Actualiser",
                IconUI.createIcon("src/main/resources/static/img/png/refresh.png", 20, 20),
                GRAY_COLOR, TEXT_COLOR, e -> chargerGroupes());
        JButton returnButton = createIconTextButton("Return To Dashboard", null, BLA_COLOR, Color.WHITE,
                e -> navigateToDashboard());

        panel.add(refreshButton);
        panel.add(returnButton);

        return panel;
    }

    /** 🏗 Crée un panneau de filtres */
    private JPanel createFiltersPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);

        // Bouton de tri A-Z
        JButton sortAscButton = createIconTextButton("🔼 Trier (A-Z)", null,
                VERT_COLOR_2, Color.WHITE,
                e -> trierTable(true));

        // Bouton de tri Z-A
        JButton sortDescButton = createIconTextButton("🔽 Trier (Z-A)", null,
                GRAY_COLOR, TEXT_COLOR,
                e -> trierTable(false));

        // Bouton de filtre
        JButton filterButton = createIconTextButton("Filtrer",
                IconUI.createIcon("src/main/resources/static/img/png/filter.png", 20, 20),
                GRAY_COLOR, TEXT_COLOR,
                e -> filtrerParLibelle());

        panel.add(sortAscButton);
        panel.add(sortDescButton);
        panel.add(filterButton);

        return panel;
    }

    /** 🏗 Crée un tableau moderne avec les couleurs d'origine */
    private JPanel createTablePanel() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        // tableContainer.setBackground(GRAY_COLOR);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(15, 15, 15, 15)));

        tableModel = new DefaultTableModel(
                new String[] { "ID", "Capacité", "Type" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(40);
        table.setFont(REGULAR_FONT);
        // table.setBackground(GRAY_COLOR);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // En-tête du tableau
        JTableHeader header = table.getTableHeader();
        header.setFont(TABLE_HEADER_FONT);
        header.setBackground(VERT_COLOR_2);
        header.setForeground(Color.BLACK);
        header.setBorder(null);
        header.setPreferredSize(new Dimension(header.getWidth(), 45));

        // Personnalisation du rendu des cellules
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(new EmptyBorder(5, 15, 5, 15));

                if (isSelected) {
                    c.setBackground(
                            new Color(VERT_COLOR_2.getRed(), VERT_COLOR_2.getGreen(), VERT_COLOR_2.getBlue(), 40));
                    c.setForeground(TEXT_COLOR);
                } else if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                    c.setForeground(TEXT_COLOR);
                } else {
                    c.setBackground(new Color(0xF5F5F0));
                    c.setForeground(TEXT_COLOR);
                }

                return c;
            }
        };

        renderer.setHorizontalAlignment(SwingConstants.LEFT);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        // Gestion de la sélection
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow();
                bottomPanel.setVisible(selectedRow != -1);
                if (selectedRow != -1) {
                    statusLabel.setText("Groupe sélectionné: " + table.getValueAt(selectedRow, 1));
                } else {
                    statusLabel.setText("Prêt");
                }
            }
        });

        // Effet de survol
        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != -1 && row != table.getSelectedRow()) {
                    table.repaint();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_COLOR);

        tableContainer.add(scrollPane, BorderLayout.CENTER);

        // Message quand le tableau est vide
        JPanel emptyPanel = new JPanel(new BorderLayout());
        emptyPanel.setBackground(CARD_COLOR);

        JLabel emptyLabel = new JLabel("Aucun Groupe disponible", JLabel.CENTER);
        emptyLabel.setFont(REGULAR_FONT);
        emptyLabel.setForeground(new Color(0x9E9E9E));

        tableContainer.add(emptyPanel, BorderLayout.SOUTH);
        emptyPanel.setVisible(false);

        // Afficher message si tableau vide
        if (tableModel.getRowCount() == 0) {
            emptyPanel.setVisible(true);
            emptyPanel.add(emptyLabel, BorderLayout.CENTER);

        }

        return tableContainer;
    }

    private void filtrerParLibelle() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Trier par colonne 0 (nom ou ID) en ordre alphabétique
        sorter.setSortKeys(List.of(new RowSorter.SortKey(2, SortOrder.ASCENDING)));

        statusLabel.setText("Groupes triés par type");
    }

    /** 🏗 Crée le panneau d'informations de la formation sélectionnée */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(20, 20, 20, 20)));

        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Groupe sélectionné");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(VERT_COLOR_1);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton editButton = createIconTextButton("Modifier",
                IconUI.createIcon("src/main/resources/static/img/png/edit.png", 20, 20),
                VERT_COLOR_2, Color.WHITE, e -> modifierGroupe());

        JButton deleteButton = createIconTextButton("Supprimer",
                IconUI.createIcon("src/main/resources/static/img/png/delete.png", 20, 20),
                RED_COLOR, Color.WHITE, e -> supprimerGroupe());

        JButton studentsButton = createIconTextButton("Voir Etudiants",
                IconUI.createIcon("src/main/resources/static/img/png/group.png", 20, 20),
                BLA_COLOR, Color.WHITE, e -> voirEtudiants());

        buttonPanel.add(studentsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void voirEtudiants() {
        if (selectedRow == -1)
            return;
        Long groupeId = (Long) table.getValueAt(selectedRow, 0);
        Groupe groupe = groupeController.trouverGroupeParId(groupeId);
        if (groupe != null) {
            new StudentGroupe(groupe).afficher();
           this.fermer();
        }
    }

    private void trierTable(boolean asc) {
        List<Object[]> data = new ArrayList<>();
        int rowCount = tableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            data.add(new Object[] {
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
            });
        }

        // 🔄 Trie les formations par niveau (colonne 2)
        data.sort(Comparator.comparing(o -> (String) o[2]));
        // Si tri décroissant, on inverse
        if (!asc) {
            data.sort((o1, o2) -> ((String) o2[2]).compareTo((String) o1[2]));
        }
        // 🔄 Mise à jour du tableau
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        statusLabel.setText("Groupes triés " + (asc ? "croissant" : "décroissant"));
    }

    /** 🏗 Crée un bouton moderne */
    private JButton createButton(String text, Color bgColor, Color fgColor, java.awt.event.ActionListener listener) {
        return createIconTextButton(text, null, bgColor, fgColor, listener);
    }

    /** Filtre les formations selon le texte recherché */
    private void filtrerGroupes(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            chargerGroupes();
            return;
        }

        searchText = searchText.toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));

        statusLabel.setText(sorter.getViewRowCount() + " Groupe(s) trouvé(s)");
    }

    /** Charge les formations depuis le contrôleur */
    private void chargerGroupes() {
        tableModel.setRowCount(0);
        List<Groupe> groupes = groupeController.getGroupesByFormation(formation);
        for (Groupe groupe : groupes) {
            tableModel.addRow(new Object[] {
                   groupe.getId(),
                   groupe.getCapacite(),
                   groupe.getType()

            });
        }

        statusLabel.setText(tableModel.getRowCount() + " Groupe(s) chargé(s)");

        // En-tête du tableau
        JTableHeader header = table.getTableHeader();
        header.setBackground(BLA_COLOR);

        // Réinitialiser le filtre
        table.setRowSorter(null);
    }

    

  
    private void ajouterGroupe() {
                JDialog dialog = new JDialog(this, "Ajouter un Groupe", true);
                dialog.setSize(600, 300);
                dialog.setLayout(new GridBagLayout());
                dialog.setLocationRelativeTo(this);
        
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.gridx = 0;
                gbc.gridy = 0;
        
                // Capacite field
                dialog.add(new JLabel("Capacité :"), gbc);
                gbc.gridx = 1;
                JTextField capaciteField = new JTextField(10);
                dialog.add(capaciteField, gbc);
        
                // Type field
                gbc.gridx = 0;
                gbc.gridy++;
                dialog.add(new JLabel("Type :"), gbc);
                gbc.gridx = 1;
                JComboBox<TypeGroupe> typeComboBox = new JComboBox<>(TypeGroupe.values());
                dialog.add(typeComboBox, gbc);
        
                // Formation label
                gbc.gridx = 0;
                gbc.gridy++;
                dialog.add(new JLabel("Formation :"), gbc);
                gbc.gridx = 1;
                dialog.add(new JLabel(formation.getLibelle()), gbc);
        
                // Buttons panel
                JPanel buttonPanel = new JPanel();
                JButton saveButton = new JButton("Enregistrer");
                saveButton.setBackground(VERT_COLOR_1);
                saveButton.setFont(BUTTON_FONT);
                saveButton.addActionListener(e -> saveGroup(capaciteField, typeComboBox, dialog));
        
                JButton cancelButton = new JButton("Annuler");
                cancelButton.setBackground(RED_COLOR);
                cancelButton.setFont(BUTTON_FONT);
                cancelButton.addActionListener(e -> dialog.dispose());
        
                buttonPanel.add(saveButton);
                buttonPanel.add(cancelButton);
                gbc.gridx = 0;
                gbc.gridy++;
                dialog.add(buttonPanel, gbc);
        
                dialog.setVisible(true);
            }
        
            private void saveGroup(JTextField capaciteField, JComboBox<TypeGroupe> typeComboBox, JDialog dialog) {
                try {
                    int capacity = Integer.parseInt(capaciteField.getText());
                    TypeGroupe type = (TypeGroupe) typeComboBox.getSelectedItem();
                    String message = groupeController.ajouterGroupe(capacity, type, formation);
                    JOptionPane.showMessageDialog(dialog, message, "Ajout Groupe", JOptionPane.INFORMATION_MESSAGE);
                    chargerGroupes();
                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "La capacité doit être un nombre valide.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        
            // Modify group method
            private void modifierGroupe() {
                if (selectedRow != -1) {
                    Long groupeId = (Long) table.getValueAt(selectedRow, 0);
        
                    // Récupérer le groupe à partir de son ID
                    Groupe groupe = groupeController.trouverGroupeParId(groupeId);
                    if (groupe == null) {
                        JOptionPane.showMessageDialog(this, "Groupe introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
            
                    // Créer le modal pour modifier le groupe
                    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier un Groupe", true);
                    dialog.setSize(600, 300);
                    dialog.setLayout(new GridBagLayout());
                    dialog.setLocationRelativeTo(this);
            
                    // Définir le style et les contraintes du layout
                    Font font = new Font("Poppins", Font.PLAIN, 14);
                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.insets = new Insets(10, 10, 10, 10);
                    gbc.anchor = GridBagConstraints.WEST;
                    gbc.gridx = 0;
                    gbc.gridy = 0;
            
                    // Champs du formulaire
                    JLabel capaciteLabel = new JLabel("Capacité :");
                    capaciteLabel.setFont(font);
                    dialog.add(capaciteLabel, gbc);
                    gbc.gridx = 1;
                    JTextField capaciteField = new JTextField(20);
                    capaciteField.setFont(font);
                    capaciteField.setText(String.valueOf(groupe.getCapacite()));
                    dialog.add(capaciteField, gbc);
            
                    gbc.gridx = 0;
                    gbc.gridy++;
                    JLabel typeLabel = new JLabel("Type :");
                    typeLabel.setFont(font);
                    dialog.add(typeLabel, gbc);
                    gbc.gridx = 1;
                    JComboBox<TypeGroupe> typeComboBox = new JComboBox<>(TypeGroupe.values());
                    typeComboBox.setSelectedItem(groupe.getType());
                    dialog.add(typeComboBox, gbc);
            
                    // Boutons
                    gbc.gridx = 0;
                    gbc.gridy++;
                    gbc.gridwidth = 2;
                    gbc.anchor = GridBagConstraints.CENTER;
            
                    JButton enregistrerButton = new JButton("Enregistrer");
                    enregistrerButton.setBackground(VERT_COLOR_1);
                    enregistrerButton.setForeground(Color.WHITE);
                    enregistrerButton.setFont(BUTTON_FONT);
                    enregistrerButton.setFont(new Font("Poppins", Font.BOLD, 14));
            
                    JButton annulerButton = new JButton("Annuler");
                    annulerButton.setBackground(RED_COLOR);
                    annulerButton.setFont(BUTTON_FONT);
                    annulerButton.setForeground(Color.WHITE);
                    annulerButton.setFont(new Font("Poppins", Font.BOLD, 14));
            
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(enregistrerButton);
                    buttonPanel.add(annulerButton);
            
                    dialog.add(buttonPanel, gbc);
            
                    // Action sur le bouton "Enregistrer"
                    enregistrerButton.addActionListener(e -> {
                        try {
                            int capacite = Integer.parseInt(capaciteField.getText());
                            TypeGroupe type = (TypeGroupe) typeComboBox.getSelectedItem();
            
                            // Vérification des valeurs
                            if (capacite <= 0) {
                                JOptionPane.showMessageDialog(dialog, "La capacité doit être un entier supérieur à 0.", "Erreur",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
            
                            // Mise à jour du groupe via `GroupeController`
                            groupe.setCapacite(capacite);
                            groupe.setType(type);
                            String message = groupeController.modifierGroupe(groupeId, groupe);
                            JOptionPane.showMessageDialog(dialog, message, "Modification Groupe", JOptionPane.INFORMATION_MESSAGE);
            
                            // Rafraîchir la liste des groupes
                            chargerGroupes();
            
                            // Fermer le modal
                            dialog.dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(dialog, "La capacité doit être un nombre valide.", "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });
            
                    // Bouton "Annuler"
                    annulerButton.addActionListener(e -> dialog.dispose());
            
                    // Affichage du modal
                    dialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        
            // Delete group method
            private void supprimerGroupe() {
                if (selectedRow != -1) {
                    Long groupId = (Long) table.getValueAt(selectedRow, 0); // Get group ID from selected row
                    int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation",
                            JOptionPane.YES_NO_OPTION);
        
                    if (confirm == JOptionPane.YES_OPTION) {
                        String message = groupeController.supprimerGroupe(groupId);
                        JOptionPane.showMessageDialog(this, message, "Suppression", JOptionPane.INFORMATION_MESSAGE);
                        chargerGroupes();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
                            JOptionPane.WARNING_MESSAGE);
                }
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

