package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import sn.uasz.m1.inscription.controller.FormationController;
import sn.uasz.m1.inscription.model.Formation;

public class FormationUI extends JPanel {

    // 🎨 Déclaration des couleurs
    private static final Color VERT_COLOR_1 = new Color(0x113F36);
    private static final Color VERT_COLOR_2 = new Color(0x128E64);
    private static final Color BG_COLOR = new Color(0xF5F5F0);
    private static final Color RED_COLOR = new Color(0xcc1a1a);
    private static final Color GRAY_COLOR = new Color(0xededed);

    // 🖋 Déclaration des polices
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
    private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);

    // 🏗 Composants principaux
    private JTable table;
    private DefaultTableModel tableModel;
    private final FormationController formationController;
    private final JPanel bottomPanel;
    private int selectedRow = -1;

    public FormationUI() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println("Erreur Look and Feel: " + e.getMessage());
        }

        formationController = new FormationController();
        setLayout(new GridBagLayout());
        setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 10, 5, 10);

        // Ajouter les composants à l'interface
        add(createTopPanel(), gbc);
        gbc.gridy = 1;
        add(createTablePanel(), gbc);
        gbc.gridy = 2;
        add(createActionButtonsPanel(), gbc);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bottomPanel = createBottomPanel();
        add(bottomPanel, gbc);

        // Charger les formations
        chargerFormations();
    }

    /** 🏗 Crée le panneau supérieur */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BG_COLOR);

        // outon de tri A-Z
        JButton sortAscButton = createButton("🔼 Trier (A-Z)", VERT_COLOR_2, e -> trierTable(true));

        //Bouton de tri Z-A
        JButton sortDescButton = createButton("🔽 Trier (Z-A)", GRAY_COLOR, e -> trierTable(false));
        sortDescButton.setForeground(Color.BLACK);

        // Bouton de filtre
        JButton filterButton = createIconButton("src/main/resources/static/img/png/filter.png", GRAY_COLOR,
                e -> JOptionPane.showMessageDialog(this, "Fonctionnalité de filtre à implémenter"));

        // Bouton d'ajout
        JButton addFormationButton = createButton("+ Ajouter Formation", VERT_COLOR_1,
                e -> ouvrirModalAjoutFormation());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(sortAscButton);
        rightPanel.add(sortDescButton);
        rightPanel.add(filterButton);
        rightPanel.add(addFormationButton);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(createSearchPanel());

        topPanel.add(leftPanel, BorderLayout.EAST);
        topPanel.add(rightPanel, BorderLayout.WEST);
        return topPanel;
    }

    private ImageIcon createIcon(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    /**Méthode pour trier la table */
    private void trierTable(boolean asc) {
        List<Object[]> data = new ArrayList<>();
        int rowCount = tableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            data.add(new Object[] {
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    Integer.parseInt(tableModel.getValueAt(i, 2).toString())
            });
        }

        // 🔄 Trie les formations par niveau (colonne 2)
        data.sort(Comparator.comparingInt(o -> (Integer) o[2]));
        if (!asc) {
            data.sort((o1, o2) -> Integer.compare((Integer) o2[2], (Integer) o1[2]));
        }

        // 🔄 Mise à jour du tableau
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    private JTextField createSearchField() {
        JTextField searchField = new JTextField(15);
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher");
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrerFormations(searchField.getText());
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerFormations(searchField.getText());
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerFormations(searchField.getText());
            }
        });

        return searchField;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(BG_COLOR);

        panel.add(createSearchField());

        panel.add(new JLabel(createIcon("src/main/resources/static/img/png/search.png")));

        return panel;
    }

    /**Crée le tableau */
    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        tableModel = new DefaultTableModel(new String[] { "ID", "Libelle", "Niveau" }, 0);
        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(REGULAR_FONT);

        table.getSelectionModel().addListSelectionListener(e -> {
            selectedRow = table.getSelectedRow();
            bottomPanel.setVisible(selectedRow != -1);
        });

        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        return tablePanel;
    }

    /**Crée les boutons d'action (Modifier, Supprimer) */
    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_COLOR);
        JButton modifyButton = createIconButton("src/main/resources/static/img/png/edit.png", VERT_COLOR_1,
                e -> ouvrirModalModificationFormation(selectedRow));
        JButton deleteButton = createIconButton("src/main/resources/static/img/png/delete.png", RED_COLOR,
                e -> confirmerSuppression(selectedRow));
        panel.add(modifyButton);
        panel.add(deleteButton);
        return panel;
    }

    /**Charge les formations */
    private void chargerFormations() {
        tableModel.setRowCount(0);
        List<Formation> formations = formationController.listerFormationsResponsable();
        for (Formation formation : formations) {
            tableModel.addRow(new Object[] { formation.getId(), formation.getLibelle(), formation.getNiveau() });
        }
    }

    /**Crée un bouton générique */
    private JButton createButton(String text, Color bgColor, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(BOLD_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        return button;
    }

    /**rée un bouton avec icône */
    private JButton createIconButton(String iconPath, Color bgColor, java.awt.event.ActionListener action) {
        ImageIcon icon = new ImageIcon(
                new ImageIcon(iconPath).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        JButton button = new JButton(icon);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        return button;
    }

    /**Filtrer les formations */
    private void filtrerFormations(String recherche) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        sorter.setRowFilter(recherche.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + recherche, 1));
    }

    /**Crée le panneau bas */
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(BG_COLOR);
        panel.setVisible(false);
        JButton listGroupsButton = createButton("Voir Groupes", GRAY_COLOR, e -> ouvrirPanelGroupes(selectedRow));
        JButton addUE = createButton("Voir UEs", GRAY_COLOR, e -> ouvrirPanelUEs(selectedRow));
        listGroupsButton.setForeground(Color.BLACK);
        addUE.setForeground(Color.BLACK);
        panel.add(listGroupsButton);
        panel.add(addUE);
        return panel;
    }

    /** Ouvrir le panel des groupes */
    private void ouvrirPanelGroupes(int row) {
        if (row == -1)
            return;
        Long formationId = (Long) table.getValueAt(row, 0);
        Formation formation = formationController.trouverFormationParId(formationId);
        if (formation != null) {
            new FormationGroupeUI(formation).afficher();
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
        }
    }

    /** ouvrir panel des Ues */
    private void ouvrirPanelUEs(int row){
        if(row == -1)
            return;
        Long formationId = (Long) table.getValueAt(row, 0);
        Formation formation = formationController.trouverFormationParId(formationId);
        if (formation != null) {
            new FormationUEUI(formation).afficher();
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
        }
    }

    /** 🗑 Confirmer la suppression */
    private void confirmerSuppression(int row) {
        if (row == -1)
            return;
        Long formationId = (Long) table.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Confirmer suppression ?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            formationController.supprimerFormation(formationId);
            chargerFormations();
        }
    }

    /** Modal de modification */
    private void ouvrirModalModificationFormation(int row) {
        // Récupérer l'ID de la formation à partir de la ligne sélectionnée
        Long formationId = (Long) table.getValueAt(row, 0); // La première colonne contient l'ID de la formation

        // Récupérer la formation à partir de son ID
        Formation formation = formationController.trouverFormationParId(formationId);

        // Créer le modal pour modifier la formation
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier une Formation", true);
        dialog.setSize(600, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(this);

        // Définir le style et les contraintes du layout

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Champs du formulaire
        JLabel libelleLabel = new JLabel("Libellé de la Formation :");
        libelleLabel.setFont(REGULAR_FONT);
        dialog.add(libelleLabel, gbc);
        gbc.gridx = 1;
        JTextField libelleField = new JTextField(20);
        libelleField.setFont(REGULAR_FONT);
        libelleField.setText(formation.getLibelle());
        dialog.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel nivLabel = new JLabel("Niveau :");
        nivLabel.setFont(REGULAR_FONT);
        dialog.add(nivLabel, gbc);
        gbc.gridx = 1;
        JTextField niveauField = new JTextField(20);
        niveauField.setFont(REGULAR_FONT);
        niveauField.setText(String.valueOf(formation.getNiveau()));
        dialog.add(niveauField, gbc);

        // Boutons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setBackground(VERT_COLOR_1);
        enregistrerButton.setForeground(Color.WHITE);
        enregistrerButton.setFont(REGULAR_FONT);

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(RED_COLOR);
        annulerButton.setForeground(Color.WHITE);
        annulerButton.setFont(REGULAR_FONT);

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

    /** Modal pour ajouter une formation */
    private void ouvrirModalAjoutFormation() {
        // Création du `JDialog` pour l'ajout de formation
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter une Formation", true);
        dialog.setSize(600, 300);
        dialog.setLayout(new GridBagLayout());
        dialog.setUndecorated(true);

        dialog.setLocationRelativeTo(this);

        // Définition du style

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Champs du formulaire
        JLabel libelleLabel = new JLabel("Libellé de la Formation :");
        libelleLabel.setFont(REGULAR_FONT);
        dialog.add(libelleLabel, gbc);
        gbc.gridx = 1;
        JTextField libelleField = new JTextField(20);
        libelleField.setFont(REGULAR_FONT);
        dialog.add(libelleField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel nivLabel = new JLabel("Niveau :");
        nivLabel.setFont(REGULAR_FONT);
        dialog.add(nivLabel, gbc);
        gbc.gridx = 1;
        JTextField niveauField = new JTextField(20); // Utilisation d'un `JTextField` pour stocker des nombres
        niveauField.setFont(REGULAR_FONT);
        dialog.add(niveauField, gbc);

        // Boutons
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton enregistrerButton = new JButton("Enregistrer");
        enregistrerButton.setBackground(VERT_COLOR_1);
        enregistrerButton.setForeground(Color.WHITE);
        enregistrerButton.setFont(REGULAR_FONT);

        JButton annulerButton = new JButton("Annuler");
        annulerButton.setBackground(RED_COLOR);
        annulerButton.setForeground(Color.WHITE);
        annulerButton.setFont(REGULAR_FONT);

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
}
