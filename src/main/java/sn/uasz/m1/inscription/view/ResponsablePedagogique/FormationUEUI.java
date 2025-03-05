package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import sn.uasz.m1.inscription.controller.FormationController;
import sn.uasz.m1.inscription.controller.UEController;
import sn.uasz.m1.inscription.controller.UEController;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;
import sn.uasz.m1.inscription.view.HomeUI;
import sn.uasz.m1.inscription.view.LoginUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FormationUEUI extends JFrame {
    // 🎨 Déclaration des couleurs
    private static final Color VERT_COLOR_1 = new Color(0x113F36);
    private static final Color VERT_COLOR_2 = new Color(0x128E64);
    private static final Color BG_COLOR = new Color(0xF5F5F0);
    private static final Color RED_COLOR = new Color(0xcc1a1a);
    private static final Color GRAY_COLOR = new Color(0xededed);

    // 🖋 Déclaration des polices
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);

    // Declarations des composants
    private JTable table;
    private DefaultTableModel tableModel;
    int selectedRow = -1;

    // Declaration des controller
    private Formation formation;
    private UEController ueController;

    public FormationUEUI(Formation formation) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.ueController = new UEController();
        this.formation = formation;

        // Setup Frame
        setTitle("UEs de la Formation");
        setLayout(new BorderLayout());
        setSize(1500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(BG_COLOR);

        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);
        // Add Components
        mainPanel.add(createPanelNorth(), BorderLayout.NORTH);
        mainPanel.add(createContentPanel(), BorderLayout.CENTER);

        // Load Groups
        chargerUEs();
    }

    // Main content panel (center)
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BG_COLOR);
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
        panel.setBackground(BG_COLOR);
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
        panel.setBackground(BG_COLOR);
        // Title
        JLabel titleLabel = new JLabel("UEs de la Formation : " + formation.getLibelle(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 18));
        titleLabel.setForeground(VERT_COLOR_1);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Action buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(BG_COLOR);
        buttonsPanel.add(createSortAscButton());
        buttonsPanel.add(createSortDescButton());
        buttonsPanel.add(createFilterButton());
        buttonsPanel.add(createAddGroupButton());

        panel.add(buttonsPanel, BorderLayout.EAST);

        panel.add(createSearchPanel(), BorderLayout.WEST);

        return panel;
    }

    private JButton createSortAscButton() {
        JButton button = new JButton("🔼 Trier (A-Z)");
        button.setFont(BOLD_FONT);
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> loadUEs(true));
        return button;
    }

    private JButton createSortDescButton() {
        JButton button = new JButton("🔽 Trier (Z-A)");
        button.setFont(BOLD_FONT);
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> loadUEs(false));
        return button;
    }

    private JButton createFilterButton() {
        JButton button = new JButton(createIcon("src/main/resources/static/img/png/filter.png"));
        button.setFont(BOLD_FONT);
        button.setForeground(Color.WHITE); // White text color
        button.setBackground(VERT_COLOR_1); // Background color
        button.setToolTipText("Filtrer les UEs");
        button.addActionListener(e -> filterTable());
        return button;
    }

    private JButton createAddGroupButton() {
        JButton button = new JButton("+ Ajouter UE");
        button.setFont(BOLD_FONT);
        button.setForeground(Color.WHITE); // White text color
        button.setBackground(VERT_COLOR_1); // Background color
        button.addActionListener(e -> openAddUEModal());
        return button;
    }

    private JButton createRetourButton() {
        JButton retourButton = new JButton("⬅ Retour");
        retourButton.setFont(BOLD_FONT);
        retourButton.setForeground(Color.WHITE); // White text color
        retourButton.setBackground(VERT_COLOR_1); // Background color
        retourButton.addActionListener(e -> navigateToDashboard());
        return retourButton;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnNames = { "ID", "CODE", "LIBELLE", "CREDIT", "COEFFICIENT", "VOLUME HORAIRE", "ENSEIGNANT",
                "OPTION" };
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(REGULAR_FONT);
        table.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = table.getSelectedRow();
        });
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_COLOR);

        // Création du bouton "Modifier"
        ImageIcon modify_icon = new ImageIcon("src/main/resources/static/img/png/edit.png");
        Image modifyImage = modify_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon modifyIcon = new ImageIcon(modifyImage);
        JButton modifyButton = new JButton(modifyIcon);
        modifyButton.setBackground(VERT_COLOR_1);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            editUE(row);
        });

        // Ajouter le bouton "Modifier" au panel
        panel.add(modifyButton);

        // Vérification de l'UE sélectionnée pour savoir si elle est optionnelle
        ImageIcon delete_icon = new ImageIcon("src/main/resources/static/img/png/delete.png");
        Image deleteImage = delete_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(deleteImage);
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setBackground(RED_COLOR);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            // Appeler la méthode pour supprimer l'UE
            int row = table.getSelectedRow();
            deletedUE(row);
        });

        // Ajouter le bouton "Supprimer" au panel
        panel.add(deleteButton);
        return panel;
    }

    private void loadUEs(boolean ordreCroissant) {
        tableModel.setRowCount(0); // Vider la table avant de recharger

        List<UE> ues = ueController.getFormationUEsTriesParLibelle(ordreCroissant, formation.getId());

        for (UE ue : ues) {
            tableModel.addRow(new Object[] {
                    ue.getId(),
                    ue.getCode(),
                    ue.getLibelle(),
                    ue.getCredit(),
                    ue.getCoefficient(),
                    ue.getVolumeHoraire(),
                    (ue.getEnseignant() != null)
                            ? ue.getEnseignant().getPrenom() + " " + ue.getEnseignant().getNom()
                            : "Aucun",
                    (ue.isObligatoire()) ? "Obligatoire" : "Optionnelle"

            });
        }
    }

    private void filterTable() {
        // Récupérer les données actuelles de la table
        int rowCount = tableModel.getRowCount();
        List<Object[]> data = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            data.add(new Object[] {
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
                    tableModel.getValueAt(i, 3),
                    tableModel.getValueAt(i, 4),
                    tableModel.getValueAt(i, 5),
                    tableModel.getValueAt(i, 6),
                    tableModel.getValueAt(i, 7)
            });
        }

        // Trier les données par nive
        data.sort(Comparator.comparing(o -> o[1].toString()));

        // Réinsérer les données triées dans le modèle
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

    }

    private JTextField createSearchField() {
        JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 25));
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher");

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrerUE(searchField.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerUE(searchField.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerUE(searchField.getText());
            }
        });

        return searchField;
    }

    
    private JPanel createSearchPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(BG_COLOR);

        panel.add(createSearchField());
        
        panel.add(new JLabel(createIcon("src/main/resources/static/img/png/search.png")));

        return panel;
    }

    private void filtrerUE(String recherche) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        if (recherche.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + recherche, 2));
        }
    }

    private void openAddUEModal() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter une UE", true);
        dialog.setSize(500, 300);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(this);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Sélection de l'UE
        JLabel ueLabel = new JLabel("UE :");
        ueLabel.setFont(REGULAR_FONT);
        contentPanel.add(ueLabel, gbc);
        gbc.gridx = 1;
        JComboBox<UE> ueComboBox = new JComboBox<>();
        ueComboBox.setFont(REGULAR_FONT);
        List<UE> ues = ueController.listerUEsDisponbles(formation.getId());

        for (UE ue : ues) {
            ueComboBox.addItem(ue);
        }

        ueComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof UE) {
                    UE ue = (UE) value;
                    setText(ue.getCode() + " - " + ue.getLibelle());
                }
                return this;
            }
        });

        contentPanel.add(ueComboBox, gbc);

        // Sélection de l'obligation
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel optionJLabel = new JLabel("Obligatoire :");
        optionJLabel.setFont(REGULAR_FONT);
        contentPanel.add(optionJLabel, gbc);
        gbc.gridx = 1;
        JCheckBox obligatoireCheckBox = new JCheckBox("Oui");
        obligatoireCheckBox.setFont(REGULAR_FONT);
        contentPanel.add(obligatoireCheckBox, gbc);

        // Formation affichée (non modifiable)
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel formationLabel = new JLabel("Formation :");
        formationLabel.setFont(REGULAR_FONT);
        contentPanel.add(formationLabel, gbc);
        gbc.gridx = 1;
        JLabel formationJLabel = new JLabel(formation.getLibelle());
        formationJLabel.setFont(REGULAR_FONT);
        contentPanel.add(formationJLabel, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BG_COLOR);
        JButton saveButton = new JButton("Enregistrer");
        saveButton.setFont(BOLD_FONT);
        saveButton.setBackground(VERT_COLOR_1);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveGroup(ueComboBox, obligatoireCheckBox, dialog));

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BOLD_FONT);
        cancelButton.setBackground(RED_COLOR);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        contentPanel.add(buttonPanel, gbc);

        // 💡 Définir `contentPanel` comme le content pane du JDialog
        dialog.setContentPane(contentPanel);

        dialog.setVisible(true);
    }

    private void saveGroup(JComboBox<UE> ueComboBox, JCheckBox obligatoireCheckBox, JDialog dialog) {
        UE selectedUE = (UE) ueComboBox.getSelectedItem();
        boolean obligatoire = obligatoireCheckBox.isSelected();

        if (selectedUE == null) {
            JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner une UE.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String message = ueController.associerUEDansFormation(selectedUE, formation.getId(), obligatoire);
        JOptionPane.showMessageDialog(dialog, message, "Ajout Groupe", JOptionPane.INFORMATION_MESSAGE);

        // Rafraîchir la liste après ajout
        chargerUEs();
        dialog.dispose();
    }

    private void editUE(int row) {
        if (row != -1) {
            Long ueId = (Long) table.getValueAt(row, 0);

            // Récupérer l'UE à partir de son ID
            UE ue = ueController.trouverUEParId(ueId);
            if (ue == null) {
                JOptionPane.showMessageDialog(this, "UE introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Créer le modal pour modifier l'UE
            JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier une UE", true);
            dialog.setSize(600, 300);
            dialog.setUndecorated(true);
            dialog.setLocationRelativeTo(this);

            JPanel contentPanel = new JPanel(new GridBagLayout());
            contentPanel.setBackground(BG_COLOR);

            // Définir le style et les contraintes du layout
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;

            // Afficher l'UE actuelle (Code - Libellé)
            JLabel ueLabel = new JLabel("UE :");
            ueLabel.setFont(REGULAR_FONT);
            contentPanel.add(ueLabel, gbc);
            gbc.gridx = 1;
            JLabel ueJLabel = new JLabel(ue.getCode() + " - " + ue.getLibelle());
            ueJLabel.setFont(REGULAR_FONT);
            contentPanel.add(ueJLabel, gbc);

            // Sélection de l'obligation
            gbc.gridx = 0;
            gbc.gridy++;
            JLabel optionJLabel = new JLabel("Obligatoire :");
            optionJLabel.setFont(REGULAR_FONT);
            contentPanel.add(optionJLabel, gbc);
            gbc.gridx = 1;
            JCheckBox obligatoireCheckBox = new JCheckBox("Oui");
            obligatoireCheckBox.setSelected(ue.isObligatoire());
            obligatoireCheckBox.setFont(REGULAR_FONT);
            contentPanel.add(obligatoireCheckBox, gbc);

            // Formation affichée (non modifiable)
            gbc.gridx = 0;
            gbc.gridy++;
            JLabel formationLabel = new JLabel("Formation :");
            formationLabel.setFont(REGULAR_FONT);
            contentPanel.add(formationLabel, gbc);
            gbc.gridx = 1;
            JLabel formationJLabel = new JLabel(formation.getLibelle());
            formationJLabel.setFont(REGULAR_FONT);
            contentPanel.add(formationJLabel, gbc);

            // Boutons
            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;

            JButton enregistrerButton = new JButton("Enregistrer");
            enregistrerButton.setBackground(VERT_COLOR_1);
            enregistrerButton.setForeground(Color.WHITE);
            enregistrerButton.setFont(BOLD_FONT);

            JButton annulerButton = new JButton("Annuler");
            annulerButton.setBackground(RED_COLOR);
            annulerButton.setForeground(Color.WHITE);
            annulerButton.setFont(BOLD_FONT);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(BG_COLOR);
            buttonPanel.add(enregistrerButton);
            buttonPanel.add(annulerButton);

            contentPanel.add(buttonPanel, gbc);

            // Action sur le bouton "Enregistrer"
            enregistrerButton.addActionListener(e -> {
                // Appel de la méthode updateUE pour mettre à jour l'UE
                updateUE(ue, obligatoireCheckBox, dialog);
            });

            // Action sur le bouton "Annuler"
            annulerButton.addActionListener(e -> dialog.dispose());

            // Afficher le modal
            dialog.setContentPane(contentPanel);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une UE.", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateUE(UE ue, JCheckBox obligatoireCheckBox, JDialog dialog) {
        boolean obligatoire = obligatoireCheckBox.isSelected();

        // Mise à jour de l'UE (changement de l'état obligatoire)
        String message = ueController.modifierEtatUE(ue.getId(), obligatoire);
        JOptionPane.showMessageDialog(dialog, message, "Modification de l'UE", JOptionPane.INFORMATION_MESSAGE);

        // Rafraîchir la liste après modification
        chargerUEs();
        dialog.dispose();
    }

    private void deletedUE(int row) {
        // Vérifie si une ligne est sélectionnée
        if (row != -1) {
            // Récupère l'ID de l'UE à partir de la ligne sélectionnée
            Long ueID = (Long) table.getValueAt(row, 0);

            // Recherche l'UE correspondante
            UE ue = ueController.trouverUEParId(ueID);

            // Vérifie si l'UE est obligatoire
            if (ue.isObligatoire()) {
                // Si l'UE est obligatoire, affichage d'un message d'erreur
                JOptionPane.showMessageDialog(this, "Suppression Impossible! UE obligatoire.", "Erreur",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // Si l'UE n'est pas obligatoire, demande de confirmation pour la suppression
                int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation",
                        JOptionPane.YES_NO_OPTION);

                // Si l'utilisateur confirme, suppression de l'UE
                if (confirm == JOptionPane.YES_OPTION) {
                    // Appel au contrôleur pour retirer l'UE de la formation
                    String message = ueController.retirerUEDeFormation(ueID);

                    // Affichage du message de confirmation
                    JOptionPane.showMessageDialog(this, message, "Suppression", JOptionPane.INFORMATION_MESSAGE);

                    // Rafraîchissement de la liste des UEs après suppression
                    chargerUEs();
                }
            }
        } else {
            // Message si aucune UE n'est sélectionnée
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une UE.", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void chargerUEs() {
        tableModel.setRowCount(0);

        // Récupération des UEs depuis le service
        List<UE> ues = ueController.getUEsByFormation(formation.getId());

        // Remplissage du tableau avec les UEs
        if (!ues.isEmpty()) {
            for (UE ue : ues) {
                tableModel.addRow(new Object[] {
                        ue.getId(),
                        ue.getCode(),
                        ue.getLibelle(),
                        ue.getCredit(),
                        ue.getCoefficient(),
                        ue.getVolumeHoraire(),
                        (ue.getEnseignant() != null)
                                ? ue.getEnseignant().getPrenom() + " " + ue.getEnseignant().getNom()
                                : "Aucun",
                        (ue.isObligatoire()) ? "Obligatoire" : "Optionnelle"

                });
            }
        }
    }

    public void afficher() {
        this.setVisible(true);
    }

    public void fermer() {
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
