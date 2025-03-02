package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import sn.uasz.m1.inscription.controller.FormationController;
import sn.uasz.m1.inscription.controller.GroupeController;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.model.enumeration.TypeGroupe;

public class GroupeUI extends JPanel {

    private Color vertColor1 = new Color(0x113F36);
    private Color vertColor2 = new Color(0x128E64);
    private Color bColor = new Color(0x575757);
    private Color redColor = new Color(0xcc1a1a);

    private JPanel tablePanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private GroupeController groupeController;
    private FormationController formationController;
    private int selectedRow = -1;

    public GroupeUI() {
        groupeController = new GroupeController();
        formationController = new FormationController();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Panneau supérieur
        add(createTopPanel(), gbc);

        // Panneau de la table
        tablePanel = createTablePanel();
        gbc.gridy = 1;
        add(tablePanel, gbc);

        // Boutons Modifier et Supprimer
        gbc.gridy = 2;
        add(createActionButtonsPanel(), gbc);

        // Charger les groupes
        chargerGroupes();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        ImageIcon filter_icon = new ImageIcon("src/main/resources/static/img/png/filter.png");
        Image filterImage = filter_icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon filterIcon = new ImageIcon(filterImage);
        JButton filterButton = new JButton(filterIcon);

        filterButton.addActionListener(e -> trierTable());

        JButton addGroupeButton = new JButton("+ Ajouter Groupe");
        addGroupeButton.setFont(new Font("Poppins", Font.BOLD, 14));
        addGroupeButton.setBackground(vertColor1);
        addGroupeButton.setForeground(Color.WHITE);

        addGroupeButton.addActionListener(e -> ouvrirModalAjoutGroupe());
        JButton sortAscButton = new JButton("🔼 Trier (A-Z)");
        JButton sortDescButton = new JButton("🔽 Trier (Z-A)");

        sortAscButton.setFont(new Font("Poppins", Font.BOLD, 14));
        sortDescButton.setFont(new Font("Poppins", Font.BOLD, 14));

        sortAscButton.setBackground(vertColor2);
        sortAscButton.setForeground(Color.WHITE);
        sortDescButton.setBackground(bColor);
        sortDescButton.setForeground(Color.WHITE);

        sortAscButton.addActionListener(e -> chargerGroupes(true));
        sortDescButton.addActionListener(e -> chargerGroupes(false));

        rightPanel.add(sortAscButton);
        rightPanel.add(sortDescButton);

        rightPanel.add(filterButton);
        rightPanel.add(addGroupeButton);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 25));
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher");

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrerGroupes(searchField.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerGroupes(searchField.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerGroupes(searchField.getText());
            }
        });

        leftPanel.add(searchField);

        topPanel.add(rightPanel, BorderLayout.WEST);
        topPanel.add(leftPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        String[] columnNames = { "ID", "Capacité", "Type", "Formation" };

        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);

        table.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = table.getSelectedRow();
        });

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        ImageIcon modify_icon = new ImageIcon("src/main/resources/static/img/png/edit.png");
        Image modifynImage = modify_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon modifyIcon = new ImageIcon(modifynImage);
        JButton modifyButton = new JButton(modifyIcon);
        modifyButton.setBackground(vertColor1);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.addActionListener(e -> {
            if (selectedRow != -1) {
                ouvrirModalModificationGroupe(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        ImageIcon delete_icon = new ImageIcon("src/main/resources/static/img/png/delete.png");
        Image deleteImage = delete_icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(deleteImage);
        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setBackground(redColor);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            if (selectedRow != -1) {
                confirmerSuppression(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un groupe.", "Erreur",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        actionPanel.add(modifyButton);
        actionPanel.add(deleteButton);

        return actionPanel;
    }

    private void chargerGroupes() {
        tableModel.setRowCount(0);
        List<Groupe> groupes = groupeController.listerGroupesResponsable();

        for (Groupe groupe : groupes) {
            tableModel.addRow(new Object[] {
                    groupe.getId(),
                    groupe.getCapacite(),
                    groupe.getType(),
                    groupe.getFormation().getLibelle()
            });
        }
    }

    private void ouvrirModalAjoutGroupe() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter un Groupe", true);
        dialog.setSize(600, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        Font font = new Font("Poppins", Font.PLAIN, 14);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel capaciteLabel = new JLabel("Capacité :");
        capaciteLabel.setFont(font);
        dialog.add(capaciteLabel, gbc);
        gbc.gridx = 1;
        JTextField capaciteField = new JTextField(10);
        capaciteField.setFont(font);
        dialog.add(capaciteField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel typeLabel = new JLabel("Type :");
        typeLabel.setFont(font);
        dialog.add(typeLabel, gbc);
        gbc.gridx = 1;
        JComboBox<TypeGroupe> typeComboBox = new JComboBox<>(TypeGroupe.values());
        dialog.add(typeComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel formationLabel = new JLabel("Formation :");
        formationLabel.setFont(font);
        dialog.add(formationLabel, gbc);
        gbc.gridx = 1;

        // Récupération des formations du responsable connecté
        List<Formation> formations = formationController.listerFormationsResponsable();

        JComboBox<String> formationComboBox = new JComboBox<>();
        formationComboBox.setFont(font);

        if (formations != null && !formations.isEmpty()) {
            // Création d'une liste des libellés de formations
            List<String> libelles = formations.stream()
                    .map(Formation::getLibelle) // Assurez-vous que getLibelle() existe
                    .collect(Collectors.toList());

            // Mettre à jour le JComboBox avec les libellés
            formationComboBox.setModel(new DefaultComboBoxModel<>(libelles.toArray(new String[0])));
        } else {
            JLabel noFormationLabel = new JLabel("Aucune Formation disponible");
            dialog.add(noFormationLabel, gbc);
            gbc.gridx = 1;
            formationComboBox.setEnabled(false); // Disable combo if no formations available
        }

        dialog.add(formationComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setBackground(vertColor1);
        enregistrerButton.setForeground(Color.WHITE);
        enregistrerButton.setFont(new Font("Poppins", Font.BOLD, 14));

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(redColor);
        annulerButton.setForeground(Color.WHITE);
        annulerButton.setFont(new Font("Poppins", Font.BOLD, 14));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(enregistrerButton);
        buttonPanel.add(annulerButton);

        dialog.add(buttonPanel, gbc);

        enregistrerButton.addActionListener(e -> {
            try {
                int capacite = Integer.parseInt(capaciteField.getText());
                TypeGroupe type = (TypeGroupe) typeComboBox.getSelectedItem();
                String selectedFormation = (String) formationComboBox.getSelectedItem();

                Formation formation = formationController.trouverFormationParLibelle(selectedFormation);

                if (formation == null) {
                    JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner une formation valide.", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String message = groupeController.ajouterGroupe(capacite, type, formation);
                JOptionPane.showMessageDialog(dialog, message, "Ajout Groupe", JOptionPane.INFORMATION_MESSAGE);
                chargerGroupes();
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "La capacité doit être un entier valide.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        annulerButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void ouvrirModalModificationGroupe(int row) {
        // Récupérer l'ID du groupe à partir de la ligne sélectionnée
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
        enregistrerButton.setBackground(vertColor1);
        enregistrerButton.setForeground(Color.WHITE);
        enregistrerButton.setFont(new Font("Poppins", Font.BOLD, 14));

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(redColor);
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
    }

    private void confirmerSuppression(int row) {
        Long groupeId = (Long) table.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Confirmer la suppression ?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            groupeController.supprimerGroupe(groupeId);
            chargerGroupes();
        }
    }

    private void filtrerGroupes(String recherche) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        if (recherche.trim().length() == 0) {
            sorter.setRowFilter(null); // Afficher toutes les formations si la recherche est vide
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + recherche, 3));
        }
    }

    private void trierTable() {
        // Récupérer les données actuelles de la table
        int rowCount = tableModel.getRowCount();
        List<Object[]> data = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            data.add(new Object[] {
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
                    tableModel.getValueAt(i, 3),
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

    private void chargerGroupes(boolean ordreCroissant) {
        tableModel.setRowCount(0); // Vider la table avant de recharger

        List<Groupe> groupes = groupeController.getGroupesTriesParType(ordreCroissant);

        for (Groupe groupe : groupes) {
            tableModel.addRow(new Object[] {
                    groupe.getId(),
                    groupe.getCapacite(),
                    groupe.getType(),
                    groupe.getFormation().getLibelle()
            });
        }
    }

}
