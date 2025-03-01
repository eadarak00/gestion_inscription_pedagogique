package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import sn.uasz.m1.inscription.controller.FormationController;
import sn.uasz.m1.inscription.model.Formation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormationUI extends JPanel {

    private Color vertColor1 = new Color(0x113F36);
    private Color vertColor2 = new Color(0x128E64);
    private Color bColor = new Color(0x575757);
    private Color redColor = new Color(0xcc1a1a);

    // Déclaration du panneau de la table
    private JPanel tablePanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private FormationController formationController;
    private int selectedRow = -1; // Pour suivre la ligne sélectionnée

    public FormationUI() {
        formationController = new FormationController();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Ajouter le panneau supérieur
        add(createTopPanel(), gbc);

        // Ajouter le panneau contenant la table
        tablePanel = createTablePanel();
        gbc.gridy = 1;
        add(tablePanel, gbc);

        // Ajouter les boutons Modifier et Supprimer après la table
        gbc.gridy = 2;
        add(createActionButtonsPanel(), gbc);

        // Charger les formations dans la table
        chargerFormations();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        // Panneau à droite pour les filtres et l'ajout d'utilisateur
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        ImageIcon filter_icon = new ImageIcon("src/main/resources/static/img/png/filter.png");
        Image filterImage = filter_icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon filterIcon = new ImageIcon(filterImage);
        JButton filterButton = new JButton(filterIcon);
        filterButton.addActionListener(e -> trierTable());

        filterButton.setFont(new Font("Poppins", Font.PLAIN, 14));

        JButton addFormationButton = new JButton("+ Ajouter Formation");
        addFormationButton.setFont(new Font("Poppins", Font.BOLD, 14));
        addFormationButton.setBackground(vertColor1);
        addFormationButton.setForeground(Color.WHITE);

        addFormationButton.addActionListener(e -> ouvrirModalAjoutFormation());

        rightPanel.add(filterButton);
        rightPanel.add(addFormationButton);

        // Panneau à gauche pour le champ de recherche
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JTextField searchField = new JTextField(15);
        searchField.setPreferredSize(new Dimension(150, 25));
        searchField.putClientProperty("JTextField.placeholderText", "Search");

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrerFormations(searchField.getText());
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerFormations(searchField.getText());
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerFormations(searchField.getText());
            }
        });

        leftPanel.add(searchField);

        // Ajouter les panneaux à la zone respective dans le BorderLayout
        topPanel.add(rightPanel, BorderLayout.WEST);
        topPanel.add(leftPanel, BorderLayout.EAST);

        return topPanel;
    }

    // Panneau contenant la table
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Colonnes de la table
        String[] columnNames = { "ID", "Libelle", "Niveau" };

        // Modèle de table
        tableModel = new DefaultTableModel(columnNames, 0);
        // tableModel = new DefaultTableModel(columnNames, 0) {
        // @Override
        // public boolean isCellEditable(int row, int column) {
        // return false; // Toutes les colonnes sont non éditables
        // }
        // };

        // Création de la table
        table = new JTable(tableModel);
        table.setRowHeight(30);

        // Gestion de la sélection de ligne
        table.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = table.getSelectedRow(); // Mémorise la ligne sélectionnée
        });

        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    // Panneau contenant les boutons Modifier et Supprimer après la table
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
                ouvrirModalModificationFormation(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une formation.", "Erreur",
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
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une formation.", "Erreur",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        actionPanel.add(modifyButton);
        actionPanel.add(deleteButton);

        return actionPanel;
    }

    private void chargerFormations() {
        tableModel.setRowCount(0); // Vider la table avant de recharger
        List<Formation> formations = formationController.listerFormationsResponsable();

        for (Formation formation : formations) {
            tableModel.addRow(new Object[] {
                    formation.getId(),
                    formation.getLibelle(),
                    formation.getNiveau()
            });
        }
    }

    private void ouvrirModalAjoutFormation() {
        // Création du `JDialog` pour l'ajout de formation
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter une Formation", true);
        dialog.setSize(600, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setLocationRelativeTo(this);

        // Définition du style
        Font font = new Font("Poppins", Font.PLAIN, 14);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Champs du formulaire
        JLabel libelleLabel = new JLabel("Libellé de la Formation :");
        libelleLabel.setFont(font);
        dialog.add(libelleLabel, gbc);
        gbc.gridx = 1;
        JTextField libelleField = new JTextField(20);
        libelleField.setFont(font);
        dialog.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel nivLabel = new JLabel("Niveau :");
        nivLabel.setFont(font);
        dialog.add(nivLabel, gbc);
        gbc.gridx = 1;
        JTextField niveauField = new JTextField(20); // Utilisation d'un `JTextField` pour stocker des nombres
        niveauField.setFont(font);
        dialog.add(niveauField, gbc);

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
            String libelle = libelleField.getText();
            String niveauText = niveauField.getText();

            // Validation des champs
            if (libelle.isEmpty() || niveauText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int niveau;
            try {
                niveau = Integer.parseInt(niveauText);
                if (niveau <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Le niveau doit être un entier supérieur à 0.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ajout de la formation via `FormationController`
            String message = formationController.ajouterFormation(libelle, niveau);
            JOptionPane.showMessageDialog(dialog, message, "Ajout Formation", JOptionPane.INFORMATION_MESSAGE);

            // Rafraîchir la liste des formations
            chargerFormations();

            // Fermer le modal
            dialog.dispose();
        });

        // Bouton "Annuler"
        annulerButton.addActionListener(e -> dialog.dispose());

        // Affichage du modal
        dialog.setVisible(true);
    }

    private void ouvrirModalModificationFormation(int row) {
        // Récupérer l'ID de la formation à partir de la ligne sélectionnée
        Long formationId = (Long) table.getValueAt(row, 0); // La première colonne contient l'ID de la formation

        // Récupérer la formation à partir de son ID
        Formation formation = formationController.trouverFormationParId(formationId);

        // Créer le modal pour modifier la formation
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier une Formation", true);
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
        JLabel libelleLabel = new JLabel("Libellé de la Formation :");
        libelleLabel.setFont(font);
        dialog.add(libelleLabel, gbc);
        gbc.gridx = 1;
        JTextField libelleField = new JTextField(20);
        libelleField.setFont(font);
        libelleField.setText(formation.getLibelle());
        dialog.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel nivLabel = new JLabel("Niveau :");
        nivLabel.setFont(font);
        dialog.add(nivLabel, gbc);
        gbc.gridx = 1;
        JTextField niveauField = new JTextField(20);
        niveauField.setFont(font);
        niveauField.setText(String.valueOf(formation.getNiveau()));
        dialog.add(niveauField, gbc);

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
            String libelle = libelleField.getText();
            String niveauText = niveauField.getText();

            // Validation des champs
            if (libelle.isEmpty() || niveauText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int niveau;
            try {
                niveau = Integer.parseInt(niveauText);
                if (niveau <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Le niveau doit être un entier supérieur à 0.", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String message = formationController.modifierFormation(formationId, libelle, niveau);
            JOptionPane.showMessageDialog(dialog, message, "Modification Formation", JOptionPane.INFORMATION_MESSAGE);

            // Rafraîchir la liste des formations
            chargerFormations();

            // Fermer le modal
            dialog.dispose();
        });

        // Bouton "Annuler"
        annulerButton.addActionListener(e -> dialog.dispose());

        // Affichage du modal
        dialog.setVisible(true);
    }

    private void confirmerSuppression(int row) {
        Long formationId = (Long) table.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir supprimer cette formation ?",
                "Confirmation Suppression", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String message = formationController.supprimerFormation(formationId);
            JOptionPane.showMessageDialog(this, message, "Suppression Formation", JOptionPane.INFORMATION_MESSAGE);

            // Rafraîchir la liste des formations
            chargerFormations();
        }
    }

    private void filtrerFormations(String recherche) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        if (recherche.trim().length() == 0) {
            sorter.setRowFilter(null); // Afficher toutes les formations si la recherche est vide
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + recherche, 1)); // Filtrer par colonne "Libelle"
        }
    }

    private void trierTable() {
    // Récupérer les données actuelles de la table
    int rowCount = tableModel.getRowCount();
    List<Object[]> data = new ArrayList<>();

    for (int i = 0; i < rowCount; i++) {
        data.add(new Object[]{
                tableModel.getValueAt(i, 0),  // ID
                tableModel.getValueAt(i, 1),  // Libellé
                tableModel.getValueAt(i, 2)   // Niveau
        });
    }

    // Trier les données par nive
    data.sort(Comparator.comparingInt(o -> Integer.parseInt(o[2].toString()))); 

    // Réinsérer les données triées dans le modèle
    tableModel.setRowCount(0);
    for (Object[] row : data) {
        tableModel.addRow(row);
    }
}


}
