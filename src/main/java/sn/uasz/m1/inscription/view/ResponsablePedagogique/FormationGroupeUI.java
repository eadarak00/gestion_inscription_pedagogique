package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import sn.uasz.m1.inscription.controller.GroupeController;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;
import sn.uasz.m1.inscription.view.HomeUI;
import sn.uasz.m1.inscription.view.LoginUI;

public class FormationGroupeUI extends JFrame {

    private static final Color VERT_COLOR_1 = new Color(0x113F36);
    private static final Color RED_COLOR = new Color(0xcc1a1a);
    private static final Color FOND_COLOR = new Color(0xF5F5F0);
    private static final Color GRAY_COLOR = new Color(0xefefef);

    private JTable table;
    private DefaultTableModel tableModel;
    private GroupeController groupeController;
    private Formation formation;

    public FormationGroupeUI(Formation formation) {
          try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.groupeController = new GroupeController();
        this.formation = formation;

        // Setup Frame
        setTitle("Groupes de la Formation");
        setLayout(new BorderLayout());
        setSize(1500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(FOND_COLOR);

        // Add Components
        add(createPanelNorth(), BorderLayout.NORTH);
        add(createContentPanel(), BorderLayout.CENTER);

        // Load Groups
        loadGroups();
    }

    // Main content panel (center)
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        contentPanel.add(createTopPanel(), gbc);
        contentPanel.add(createTablePanel(), gbc);
        contentPanel.add(createActionButtonsPanel(), gbc);
        gbc.fill = GridBagConstraints.NONE;
        gbc.weighty = 0.3;  
        contentPanel.add(createRetourButton(), gbc);

        return contentPanel;
    }

    // North Panel (Top)
    private JPanel createPanelNorth() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GRAY_COLOR);

        // Logout and Return buttons
        panel.add(createLogoutPanel(), BorderLayout.EAST);
        panel.add(createReturnPanel(), BorderLayout.WEST);

        return panel;
    }

    private JPanel createLogoutPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton(createIcon("src/main/resources/static/img/png/logout.png"));
        logoutButton.setBackground(RED_COLOR);
        logoutButton.addActionListener(e -> navigateToLogin());
        panel.add(logoutButton);
        return panel;
    }

    private JPanel createReturnPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton returnButton = new JButton(createIcon("src/main/resources/static/img/png/return.png"));
        returnButton.setBackground(VERT_COLOR_1);
        returnButton.addActionListener(e -> navigateToHome());
        panel.add(returnButton);
        return panel;
    }

    private ImageIcon createIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // Top Panel with title and buttons (filter, add, sort)
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Groupes de la Formation : " + formation.getLibelle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        titleLabel.setForeground(VERT_COLOR_1);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Action buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(createSortAscButton());
        buttonsPanel.add(createSortDescButton());
        buttonsPanel.add(createFilterButton());
        buttonsPanel.add(createAddGroupButton());

        panel.add(buttonsPanel, BorderLayout.EAST);

        return panel;
    }

    private JButton createSortAscButton() {
        JButton button = new JButton("🔼 Trier (A-Z)");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> loadGroups(true));
        return button;
    }

    private JButton createSortDescButton() {
        JButton button = new JButton("🔽 Trier (Z-A)");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> loadGroups(false));
        return button;
    }

    private JButton createFilterButton() {
        JButton button = new JButton(createIcon("src/main/resources/static/img/png/filter.png"));
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.WHITE); // White text color
        button.setBackground(VERT_COLOR_1); // Background color
        button.setToolTipText("Filtrer les groupes");
        button.addActionListener(e -> filterTable());
        return button;
    }

    private JButton createAddGroupButton() {
        JButton button = new JButton("+ Ajouter Groupe");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.WHITE); // White text color
        button.setBackground(VERT_COLOR_1); // Background color
        button.addActionListener(e -> openAddGroupModal());
        return button;
    }

    private JButton createRetourButton() {
        JButton retourButton = new JButton("⬅ Retour");
        retourButton.setFont(new Font("Poppins", Font.BOLD, 14));
        retourButton.setForeground(Color.WHITE); // White text color
        retourButton.setBackground(VERT_COLOR_1); // Background color
        retourButton.addActionListener(e -> navigateToDashboard());
        return retourButton;
    }

    // Table Panel
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = { "ID", "Capacité", "Type" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Action Buttons Panel
    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon modify_icon = new ImageIcon("src/main/resources/static/img/png/edit.png");
        Image modifynImage = modify_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon modifyIcon = new ImageIcon(modifynImage);
        JButton modifyButton = new JButton(modifyIcon);
        modifyButton.setBackground(VERT_COLOR_1);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            editGroup(row);
        });

        ImageIcon delete_icon = new ImageIcon("src/main/resources/static/img/png/delete.png");
        Image deleteImage = delete_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(deleteImage);
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setBackground(RED_COLOR);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            deletedGroup(row);
        });

        panel.add(modifyButton);
        panel.add(deleteButton);

        return panel;
    }

    // Table filtering and loading data
    private void filterTable() {
        int rowCount = tableModel.getRowCount();
        List<Object[]> data = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            data.add(new Object[] {
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
            });
        }

        // Trier les données par nive
        data.sort(Comparator.comparingInt(o -> Integer.parseInt(o[1].toString())));

        // Réinsérer les données triées dans le modèle
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private void loadGroups() {
        loadGroups(true); // Load by default in ascending order
    }

    private void loadGroups(boolean ascending) {
        tableModel.setRowCount(0); 

        List<Groupe> groupes = groupeController.getFormationGroupesTriesParType(ascending, formation);

        for (Groupe groupe : groupes) {
            tableModel.addRow(new Object[] {
                    groupe.getId(),
                    groupe.getCapacite(),
                    groupe.getType(),
            });
        }
    }

    // Add group modal
    private void openAddGroupModal() {
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
        saveButton.addActionListener(e -> saveGroup(capaciteField, typeComboBox, dialog));

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setBackground(RED_COLOR);
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
            loadGroups();
            dialog.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "La capacité doit être un nombre valide.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Modify group method
    private void editGroup(int row) {
        if (row != -1) {
            Long groupeId = (Long) table.getValueAt(row, 0);

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
            enregistrerButton.setFont(new Font("Poppins", Font.BOLD, 14));
    
            JButton annulerButton = new JButton("Annuler");
            annulerButton.setBackground(RED_COLOR);
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
                    loadGroups();
    
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
    private void deletedGroup(int row) {
        if (row != -1) {
            Long groupId = (Long) table.getValueAt(row, 0); // Get group ID from selected row
            int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                String message = groupeController.supprimerGroupe(groupId);
                JOptionPane.showMessageDialog(this, message, "Suppression", JOptionPane.INFORMATION_MESSAGE);
                loadGroups();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void afficher(){
        setVisible(true);
    }

    public void fermer(){
        dispose();
    }

    // Navigation Methods
    private void navigateToLogin() {
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true);
        this.dispose();
    }

    private void navigateToHome() {
        HomeUI homeUI = new HomeUI();
        homeUI.setVisible(true);
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
