package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import sn.uasz.m1.inscription.controller.UEController;
import sn.uasz.m1.inscription.model.Enseignant;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.service.EnseignantService;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UEUI extends JPanel {

    // Color Declaration
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BLACK_COLOR = new Color(0x575757);
    private final Color RED_COLOR = new Color(0xcc1a1a);
    private final Color BG_COLOR = new Color(0xF5F5F0);

    // Font Declaration
    private final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);

    // Components declaration
    private JPanel tablePanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private int selectedRow = -1;

    // Controller Declaration
    private UEController ueController;
    private EnseignantService enseignantService;

    public UEUI() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Initialize controller here
        ueController = new UEController();
        enseignantService = new EnseignantService();

        setBackground(BG_COLOR);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Panneau supérieur
        add(createTopPanel(), gbc);

        // Ajouter le panneau contenant la table
        tablePanel = createTablePanel();
        gbc.gridy = 1;
        add(tablePanel, gbc);

        gbc.gridy = 2;
        add(createActionButtonsPanel(), gbc);

        chargerUEs();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_COLOR);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        rightPanel.add(createSortAscButton());
        rightPanel.add(createSortDescButton());

        rightPanel.add(createFilterButton());
        rightPanel.add(createAddUEButton());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        leftPanel.add(createSearchPanel());

        topPanel.add(rightPanel, BorderLayout.WEST);
        topPanel.add(leftPanel, BorderLayout.EAST);

        return topPanel;
    }

    private ImageIcon createIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private JButton createSortAscButton() {
        JButton button = new JButton("🔼 Trier (A-Z)");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> loadUEs(true));
        return button;
    }

    private JButton createSortDescButton() {
        JButton button = new JButton("🔽 Trier (Z-A)");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.addActionListener(e -> loadUEs(false));
        return button;
    }

    private JButton createFilterButton() {
        JButton button = new JButton(createIcon("src/main/resources/static/img/png/filter.png"));
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setToolTipText("Filtrer les UEs");
        button.addActionListener(e -> filterTable());
        return button;
    }

    private JButton createAddUEButton() {
        JButton button = new JButton("+ Ajouter UE");
        button.setFont(new Font("Poppins", Font.BOLD, 14));
        button.setForeground(Color.WHITE); 
        button.setBackground(VERT_COLOR_1); 
        button.addActionListener(e -> ouvrirModalAjoutUE());
        return button;
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

    private void ouvrirModalAjoutUE() {
        // Création du `JDialog` pour l'ajout d'une UE
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter un UE", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        // Définition du style
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Champs du formulaire
        JLabel codeLabel = new JLabel("Code :");
        codeLabel.setFont(REGULAR_FONT);
        dialog.add(codeLabel, gbc);
        gbc.gridx = 1;
        JTextField codeField = new JTextField(20);
        codeField.setFont(REGULAR_FONT);
        dialog.add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel libelleLabel = new JLabel("Libellé :");
        libelleLabel.setFont(REGULAR_FONT);
        dialog.add(libelleLabel, gbc);
        gbc.gridx = 1;
        JTextField libelleField = new JTextField(20);
        libelleField.setFont(REGULAR_FONT);
        dialog.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel volumeHoraireLabel = new JLabel("Volume Horaire :");
        volumeHoraireLabel.setFont(REGULAR_FONT);
        dialog.add(volumeHoraireLabel, gbc);
        gbc.gridx = 1;
        JTextField volumeHoraireField = new JTextField(10);
        volumeHoraireField.setFont(REGULAR_FONT);
        dialog.add(volumeHoraireField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel coefficientLabel = new JLabel("Coefficient :");
        coefficientLabel.setFont(REGULAR_FONT);
        dialog.add(coefficientLabel, gbc);
        gbc.gridx = 1;
        JTextField coefficientField = new JTextField(10);
        coefficientField.setFont(REGULAR_FONT);
        dialog.add(coefficientField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel creditLabel = new JLabel("Crédit :");
        creditLabel.setFont(REGULAR_FONT);
        dialog.add(creditLabel, gbc);
        gbc.gridx = 1;
        JTextField creditField = new JTextField(10);
        creditField.setFont(REGULAR_FONT);
        dialog.add(creditField, gbc);

        // Sélection de l'enseignant
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel enseignantLabel = new JLabel("Enseignant :");
        enseignantLabel.setFont(REGULAR_FONT);
        dialog.add(enseignantLabel, gbc);
        gbc.gridx = 1;
       
        JComboBox<Enseignant> enseignantComboBox = new JComboBox<>();
        List<Enseignant> enseignants = enseignantService.getAllEnseignants();

        for (Enseignant e : enseignants) {
            enseignantComboBox.addItem(e); // Ajouter l'objet directement
        }

        // Personnaliser l'affichage avec un `ListCellRenderer`
        enseignantComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Enseignant) {
                    Enseignant enseignant = (Enseignant) value;
                    setText(enseignant.getPrenom() + " " + enseignant.getNom());
                }

                return this;
            }
        });

        dialog.add(enseignantComboBox, gbc);

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
        buttonPanel.add(enregistrerButton);
        buttonPanel.add(annulerButton);

        dialog.add(buttonPanel, gbc);

        // Action sur le bouton "Enregistrer"
        enregistrerButton.addActionListener(e -> {
            try {
                String code = codeField.getText();
                String libelle = libelleField.getText();
                int volumeHoraire = Integer.parseInt(volumeHoraireField.getText());
                int coefficient = Integer.parseInt(coefficientField.getText());
                int credit = Integer.parseInt(creditField.getText());
                Enseignant enseignant = (Enseignant) enseignantComboBox.getSelectedItem();

                // Création de l'UE
                UE ue = new UE();
                ue.setCode(code);
                ue.setLibelle(libelle);
                ue.setVolumeHoraire(volumeHoraire);
                ue.setCoefficient(coefficient);
                ue.setCredit(credit);
                ue.setEnseignant(enseignant);

                // Enregistrement via `UEController`
                String message = ueController.ajouterUE(code, libelle, credit, coefficient, volumeHoraire, enseignant);
                JOptionPane.showMessageDialog(dialog, message, "Ajout UE", JOptionPane.INFORMATION_MESSAGE);

                // Rafraîchir la liste des UEs
                chargerUEs();

                // Fermer le modal
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Veuillez entrer des valeurs numériques valides.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton "Annuler"
        annulerButton.addActionListener(e -> dialog.dispose());

        // Affichage du modal
        dialog.setVisible(true);
    }

    private void chargerUEs() {
        tableModel.setRowCount(0);
         

        // Récupération des UEs depuis le service
        List<UE> ues = ueController.listerUEs();

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
                                : "Aucun"

                });
            }
        }
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BG_COLOR);
        // Colonnes de la table
        String[] columnNames = { "ID", "Code", "Libelle", "Credit", "Coefficient", "Volume Horaire", "Enseignant" };

        // Modèle de table
        tableModel = new DefaultTableModel(columnNames, 0);

        // Création de la table
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(REGULAR_FONT);

        // Gestion de la sélection de ligne
        table.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = table.getSelectedRow();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // Action Buttons Panel
    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_COLOR);

        ImageIcon modify_icon = new ImageIcon("src/main/resources/static/img/png/edit.png");
        Image modifynImage = modify_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon modifyIcon = new ImageIcon(modifynImage);
        JButton modifyButton = new JButton(modifyIcon);
        modifyButton.setBackground(VERT_COLOR_1);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            ouvrirModalModificationUE(row);
        });

        ImageIcon delete_icon = new ImageIcon("src/main/resources/static/img/png/delete.png");
        Image deleteImage = delete_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(deleteImage);
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setBackground(RED_COLOR);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            confirmerSuppression(row);
        });

        panel.add(modifyButton);
        panel.add(deleteButton);

        return panel;
    }


    private void ouvrirModalModificationUE(int row) {
        // Récupérer l'ID de l'UE sélectionnée
        Long ueId = (Long) table.getValueAt(row, 0);
        UE ue = ueController.trouverUEParId(ueId);

        if (ue == null) {
            JOptionPane.showMessageDialog(this, "UE introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Création du JDialog
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier UE", true);
        dialog.setSize(500, 400);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Champs du formulaire avec valeurs actuelles
        JLabel codeLabel = new JLabel("Code :");
        codeLabel.setFont(REGULAR_FONT);
        dialog.add(codeLabel, gbc);
        gbc.gridx = 1;
        JTextField codeField = new JTextField(20);
        codeField.setText(ue.getCode());
        codeField.setFont(REGULAR_FONT);
        dialog.add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel libelleLabel = new JLabel("Libellé :");
        libelleLabel.setFont(REGULAR_FONT);
        dialog.add(libelleLabel, gbc);
        gbc.gridx = 1;
        JTextField libelleField = new JTextField(20);
        libelleField.setText(ue.getLibelle());
        libelleField.setFont(REGULAR_FONT);
        dialog.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel volumeHoraireLabel = new JLabel("Volume Horaire :");
        volumeHoraireLabel.setFont(REGULAR_FONT);
        dialog.add(volumeHoraireLabel, gbc);
        gbc.gridx = 1;
        JTextField volumeHoraireField = new JTextField(10);
        volumeHoraireField.setFont(REGULAR_FONT);
        volumeHoraireField.setText(String.valueOf(ue.getVolumeHoraire()));
        dialog.add(volumeHoraireField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel coefficientLabel = new JLabel("Coefficient :");
        coefficientLabel.setFont(REGULAR_FONT);
        dialog.add(coefficientLabel, gbc);
        gbc.gridx = 1;
        JTextField coefficientField = new JTextField(10);
        coefficientField.setFont(REGULAR_FONT);
        coefficientField.setText(String.valueOf(ue.getCoefficient()));
        dialog.add(coefficientField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel creditLabel = new JLabel("Crédit :");
        creditLabel.setFont(REGULAR_FONT);
        dialog.add(creditLabel, gbc);
        gbc.gridx = 1;
        JTextField creditField = new JTextField(10);
        creditField.setFont(REGULAR_FONT);
        creditField.setText(String.valueOf(ue.getCredit()));
        dialog.add(creditField, gbc);

        // Sélection de l'enseignant
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel enseignantLabel = new JLabel("Enseignant :");
        enseignantLabel.setFont(REGULAR_FONT);
        dialog.add(enseignantLabel, gbc);
        gbc.gridx = 1;

        JComboBox<Enseignant> enseignantComboBox = new JComboBox<>();
        enseignantComboBox.setFont(REGULAR_FONT);
        List<Enseignant> enseignants = enseignantService.getAllEnseignants();

        for (Enseignant e : enseignants) {
            enseignantComboBox.addItem(e);
        }

        // Préselectionner l'enseignant actuel
        enseignantComboBox.setSelectedItem(ue.getEnseignant());
        // Personnaliser l'affichage avec un `ListCellRenderer`
        enseignantComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Enseignant) {
                    Enseignant enseignant = (Enseignant) value;
                    setText(enseignant.getPrenom() + " " + enseignant.getNom() );
                }

                return this;
            }
        });
        dialog.add(enseignantComboBox, gbc);

        // Boutons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setFont(BOLD_FONT);
        enregistrerButton.setBackground(VERT_COLOR_1);
        enregistrerButton.setForeground(Color.WHITE);

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setFont(BOLD_FONT);
        annulerButton.setBackground(RED_COLOR);
        annulerButton.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(enregistrerButton);
        buttonPanel.add(annulerButton);
        dialog.add(buttonPanel, gbc);

        // Action sur "Enregistrer"
        enregistrerButton.addActionListener(e -> {
            try {
                String code = codeField.getText();
                String libelle = libelleField.getText();
                int volumeHoraire = Integer.parseInt(volumeHoraireField.getText());
                int coefficient = Integer.parseInt(coefficientField.getText());
                int credit = Integer.parseInt(creditField.getText());
                Enseignant enseignant = (Enseignant) enseignantComboBox.getSelectedItem();

                // Mise à jour de l'UE
                ue.setCode(code);
                ue.setLibelle(libelle);
                ue.setVolumeHoraire(volumeHoraire);
                ue.setCoefficient(coefficient);
                ue.setCredit(credit);
                ue.setEnseignant(enseignant);

                // Appel du contrôleur pour modifier l'UE
                String message = ueController.modifierUE(ueId, ue);
                JOptionPane.showMessageDialog(dialog, message, "Modification UE", JOptionPane.INFORMATION_MESSAGE);

                // Rafraîchir la liste des UEs
                chargerUEs();

                // Fermer le modal
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Veuillez entrer des valeurs numériques valides.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton "Annuler"
        annulerButton.addActionListener(e -> dialog.dispose());

        // Affichage du modal
        dialog.setVisible(true);
    }

    private void confirmerSuppression(int row) {
        // Vérifier si une ligne est sélectionnée
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une UE à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        // Récupérer l'ID de l'UE sélectionnée
        Long ueId = (Long) table.getValueAt(row, 0);
    
        // Demander confirmation
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Êtes-vous sûr de vouloir supprimer cette UE ?",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION
        );
    
        // Si l'utilisateur confirme, suppression de l'UE
        if (confirm == JOptionPane.YES_OPTION) {
            String message = ueController.supprimerUE(ueId);
            JOptionPane.showMessageDialog(this, message, "Suppression UE", JOptionPane.INFORMATION_MESSAGE);
    
            // Rafraîchir la liste des UEs après suppression
            chargerUEs();
        }
    }
    

     private void loadUEs(boolean ordreCroissant) {
        tableModel.setRowCount(0); // Vider la table avant de recharger

        List<UE> ues = ueController.getUEsTriesParLibelle(ordreCroissant);

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
                        : "Aucun"

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
                    tableModel.getValueAt(i, 6)
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
    
     private void filtrerUE(String recherche) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        if (recherche.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + recherche, 2));
        }
    }

}
