// // package sn.uasz.m1.inscription.view.ResponsablePedagogique;

// // import javax.swing.*;
// // import javax.swing.plaf.nimbus.NimbusLookAndFeel;
// // import javax.swing.table.DefaultTableModel;
// // import javax.swing.table.TableRowSorter;
// // import java.awt.*;
// // import java.util.List;
// // import java.util.ArrayList;
// // import java.util.Comparator;
// // import sn.uasz.m1.inscription.controller.FormationController;
// // import sn.uasz.m1.inscription.model.Formation;

// // public class FormationUI extends JPanel {

// //     // 🎨 Déclaration des couleurs
// //     private static final Color VERT_COLOR_1 = new Color(0x113F36);
// //     private static final Color VERT_COLOR_2 = new Color(0x128E64);
// //     private static final Color BG_COLOR = new Color(0xF5F5F0);
// //     private static final Color RED_COLOR = new Color(0xcc1a1a);
// //     private static final Color GRAY_COLOR = new Color(0xededed);

// //     // 🖋 Déclaration des polices
// //     private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
// //     private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);

// //     // 🏗 Composants principaux
// //     private JTable table;
// //     private DefaultTableModel tableModel;
// //     private final FormationController formationController;
// //     private final JPanel bottomPanel;
// //     private int selectedRow = -1;

// //     public FormationUI() {
// //         try {
// //             UIManager.setLookAndFeel(new NimbusLookAndFeel());
// //         } catch (Exception e) {
// //             System.out.println("Erreur Look and Feel: " + e.getMessage());
// //         }

// //         formationController = new FormationController();
// //         setLayout(new GridBagLayout());
// //         setBackground(BG_COLOR);
// //         GridBagConstraints gbc = new GridBagConstraints();
// //         gbc.fill = GridBagConstraints.HORIZONTAL;
// //         gbc.gridx = 0;
// //         gbc.weightx = 1.0;
// //         gbc.insets = new Insets(5, 10, 5, 10);

// //         // Ajouter les composants à l'interface
// //         add(createTopPanel(), gbc);
// //         gbc.gridy = 1;
// //         add(createTablePanel(), gbc);
// //         gbc.gridy = 2;
// //         add(createActionButtonsPanel(), gbc);
// //         gbc.gridy = 3;
// //         gbc.fill = GridBagConstraints.HORIZONTAL;
// //         bottomPanel = createBottomPanel();
// //         add(bottomPanel, gbc);

// //         // Charger les formations
// //         chargerFormations();
// //     }

// //     /** 🏗 Crée le panneau supérieur */
// //     private JPanel createTopPanel() {
// //         JPanel topPanel = new JPanel(new BorderLayout());
// //         topPanel.setBackground(BG_COLOR);

// //         // outon de tri A-Z
// //         JButton sortAscButton = createButton("🔼 Trier (A-Z)", VERT_COLOR_2, e -> trierTable(true));

// //         //Bouton de tri Z-A
// //         JButton sortDescButton = createButton("🔽 Trier (Z-A)", GRAY_COLOR, e -> trierTable(false));
// //         sortDescButton.setForeground(Color.BLACK);

// //         // Bouton de filtre
// //         JButton filterButton = createIconButton("src/main/resources/static/img/png/filter.png", GRAY_COLOR,
// //                 e -> JOptionPane.showMessageDialog(this, "Fonctionnalité de filtre à implémenter"));

// //         // Bouton d'ajout
// //         JButton addFormationButton = createButton("+ Ajouter Formation", VERT_COLOR_1,
// //                 e -> ouvrirModalAjoutFormation());

// //         JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
// //         rightPanel.add(sortAscButton);
// //         rightPanel.add(sortDescButton);
// //         rightPanel.add(filterButton);
// //         rightPanel.add(addFormationButton);

// //         JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
// //         leftPanel.add(createSearchPanel());

// //         topPanel.add(leftPanel, BorderLayout.EAST);
// //         topPanel.add(rightPanel, BorderLayout.WEST);
// //         return topPanel;
// //     }

// //     private ImageIcon createIcon(String path) {
// //         ImageIcon icon = new ImageIcon(path);
// //         Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
// //         return new ImageIcon(img);
// //     }

// //     /**Méthode pour trier la table */
// //     private void trierTable(boolean asc) {
// //         List<Object[]> data = new ArrayList<>();
// //         int rowCount = tableModel.getRowCount();

// //         for (int i = 0; i < rowCount; i++) {
// //             data.add(new Object[] {
// //                     tableModel.getValueAt(i, 0),
// //                     tableModel.getValueAt(i, 1),
// //                     Integer.parseInt(tableModel.getValueAt(i, 2).toString())
// //             });
// //         }

// //         // 🔄 Trie les formations par niveau (colonne 2)
// //         data.sort(Comparator.comparingInt(o -> (Integer) o[2]));
// //         if (!asc) {
// //             data.sort((o1, o2) -> Integer.compare((Integer) o2[2], (Integer) o1[2]));
// //         }

// //         // 🔄 Mise à jour du tableau
// //         tableModel.setRowCount(0);
// //         for (Object[] row : data) {
// //             tableModel.addRow(row);
// //         }
// //     }

// //     private JTextField createSearchField() {
// //         JTextField searchField = new JTextField(15);
// //         searchField.putClientProperty("JTextField.placeholderText", "Rechercher");
// //         searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
// //             public void insertUpdate(javax.swing.event.DocumentEvent e) {
// //                 filtrerFormations(searchField.getText());
// //             }

// //             public void removeUpdate(javax.swing.event.DocumentEvent e) {
// //                 filtrerFormations(searchField.getText());
// //             }

// //             public void changedUpdate(javax.swing.event.DocumentEvent e) {
// //                 filtrerFormations(searchField.getText());
// //             }
// //         });

// //         return searchField;
// //     }

// //     private JPanel createSearchPanel() {
// //         JPanel panel = new JPanel();
// //         panel.setBackground(BG_COLOR);

// //         panel.add(createSearchField());

// //         panel.add(new JLabel(createIcon("src/main/resources/static/img/png/search.png")));

// //         return panel;
// //     }

// //     /**Crée le tableau */
// //     private JPanel createTablePanel() {
// //         JPanel tablePanel = new JPanel(new BorderLayout());

// //         tableModel = new DefaultTableModel(new String[] { "ID", "Libelle", "Niveau" }, 0);
// //         table = new JTable(tableModel);
// //         table.setRowHeight(30);
// //         table.setFont(REGULAR_FONT);

// //         table.getSelectionModel().addListSelectionListener(e -> {
// //             selectedRow = table.getSelectedRow();
// //             bottomPanel.setVisible(selectedRow != -1);
// //         });

// //         tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
// //         return tablePanel;
// //     }

//     /**Crée les boutons d'action (Modifier, Supprimer) */
//     private JPanel createActionButtonsPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         panel.setBackground(BG_COLOR);
//         JButton modifyButton = createIconButton("src/main/resources/static/img/png/edit.png", VERT_COLOR_1,
//                 e -> ouvrirModalModificationFormation(selectedRow));
//         JButton deleteButton = createIconButton("src/main/resources/static/img/png/delete.png", RED_COLOR,
//                 e -> confirmerSuppression(selectedRow));
//         panel.add(modifyButton);
//         panel.add(deleteButton);
//         return panel;
//     }

//     /**Charge les formations */
//     private void chargerFormations() {
//         tableModel.setRowCount(0);
//         List<Formation> formations = formationController.listerFormationsResponsable();
//         for (Formation formation : formations) {
//             tableModel.addRow(new Object[] { formation.getId(), formation.getLibelle(), formation.getNiveau() });
//         }
//     }

//     /**Crée un bouton générique */
//     private JButton createButton(String text, Color bgColor, java.awt.event.ActionListener action) {
//         JButton button = new JButton(text);
//         button.setFont(BOLD_FONT);
//         button.setBackground(bgColor);
//         button.setForeground(Color.WHITE);
//         button.addActionListener(action);
//         return button;
//     }

//     /**rée un bouton avec icône */
//     private JButton createIconButton(String iconPath, Color bgColor, java.awt.event.ActionListener action) {
//         ImageIcon icon = new ImageIcon(
//                 new ImageIcon(iconPath).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
//         JButton button = new JButton(icon);
//         button.setBackground(bgColor);
//         button.setForeground(Color.WHITE);
//         button.addActionListener(action);
//         return button;
//     }

//     /**Filtrer les formations */
//     private void filtrerFormations(String recherche) {
//         TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
//         table.setRowSorter(sorter);
//         sorter.setRowFilter(recherche.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + recherche, 1));
//     }

//     /**Crée le panneau bas */
//     private JPanel createBottomPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         panel.setBackground(BG_COLOR);
//         panel.setVisible(false);
//         JButton listGroupsButton = createButton("Voir Groupes", GRAY_COLOR, e -> ouvrirPanelGroupes(selectedRow));
//         JButton addUE = createButton("Voir UEs", GRAY_COLOR, e -> ouvrirPanelUEs(selectedRow));
//         listGroupsButton.setForeground(Color.BLACK);
//         addUE.setForeground(Color.BLACK);
//         panel.add(listGroupsButton);
//         panel.add(addUE);
//         return panel;
//     }

//     /** Ouvrir le panel des groupes */
//     private void ouvrirPanelGroupes(int row) {
//         if (row == -1)
//             return;
//         Long formationId = (Long) table.getValueAt(row, 0);
//         Formation formation = formationController.trouverFormationParId(formationId);
//         if (formation != null) {
//             new FormationGroupeUI(formation).afficher();
//             ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
//         }
//     }

//     /** ouvrir panel des Ues */
//     private void ouvrirPanelUEs(int row){
//         if(row == -1)
//             return;
//         Long formationId = (Long) table.getValueAt(row, 0);
//         Formation formation = formationController.trouverFormationParId(formationId);
//         if (formation != null) {
//             new FormationUEUI(formation).afficher();
//             ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
//         }
//     }

//     /** 🗑 Confirmer la suppression */
//     private void confirmerSuppression(int row) {
//         if (row == -1)
//             return;
//         Long formationId = (Long) table.getValueAt(row, 0);
//         if (JOptionPane.showConfirmDialog(this, "Confirmer suppression ?", "Confirmation",
//                 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//             formationController.supprimerFormation(formationId);
//             chargerFormations();
//         }
//     }

//     /** Modal de modification */
//     private void ouvrirModalModificationFormation(int row) {
//         // Récupérer l'ID de la formation à partir de la ligne sélectionnée
//         Long formationId = (Long) table.getValueAt(row, 0); // La première colonne contient l'ID de la formation

//         // Récupérer la formation à partir de son ID
//         Formation formation = formationController.trouverFormationParId(formationId);

//         // Créer le modal pour modifier la formation
//         JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier une Formation", true);
//         dialog.setSize(600, 300);
//         dialog.setLayout(new GridBagLayout());
//         dialog.setUndecorated(true);
//         dialog.setLocationRelativeTo(this);

//         // Définir le style et les contraintes du layout

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.anchor = GridBagConstraints.WEST;
//         gbc.gridx = 0;
//         gbc.gridy = 0;

//         // Champs du formulaire
//         JLabel libelleLabel = new JLabel("Libellé de la Formation :");
//         libelleLabel.setFont(REGULAR_FONT);
//         dialog.add(libelleLabel, gbc);
//         gbc.gridx = 1;
//         JTextField libelleField = new JTextField(20);
//         libelleField.setFont(REGULAR_FONT);
//         libelleField.setText(formation.getLibelle());
//         dialog.add(libelleField, gbc);

//         gbc.gridx = 0;
//         gbc.gridy++;
//         JLabel nivLabel = new JLabel("Niveau :");
//         nivLabel.setFont(REGULAR_FONT);
//         dialog.add(nivLabel, gbc);
//         gbc.gridx = 1;
//         JTextField niveauField = new JTextField(20);
//         niveauField.setFont(REGULAR_FONT);
//         niveauField.setText(String.valueOf(formation.getNiveau()));
//         dialog.add(niveauField, gbc);

//         // Boutons
//         gbc.gridx = 0;
//         gbc.gridy++;
//         gbc.gridwidth = 2;
//         gbc.anchor = GridBagConstraints.CENTER;

//         JButton enregistrerButton = new JButton("Enregistrer");
//         enregistrerButton.setBackground(VERT_COLOR_1);
//         enregistrerButton.setForeground(Color.WHITE);
//         enregistrerButton.setFont(REGULAR_FONT);

//         JButton annulerButton = new JButton("Annuler");
//         annulerButton.setBackground(RED_COLOR);
//         annulerButton.setForeground(Color.WHITE);
//         annulerButton.setFont(REGULAR_FONT);

//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(enregistrerButton);
//         buttonPanel.add(annulerButton);

//         dialog.add(buttonPanel, gbc);

//         // Action sur le bouton "Enregistrer"
//         enregistrerButton.addActionListener(e -> {
//             String libelle = libelleField.getText();
//             String niveauText = niveauField.getText();

//             // Validation des champs
//             if (libelle.isEmpty() || niveauText.isEmpty()) {
//                 JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             int niveau;
//             try {
//                 niveau = Integer.parseInt(niveauText);
//                 if (niveau <= 0) {
//                     throw new NumberFormatException();
//                 }
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Le niveau doit être un entier supérieur à 0.", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             String message = formationController.modifierFormation(formationId, libelle, niveau);
//             JOptionPane.showMessageDialog(dialog, message, "Modification Formation", JOptionPane.INFORMATION_MESSAGE);

//             // Rafraîchir la liste des formations
//             chargerFormations();

//             // Fermer le modal
//             dialog.dispose();
//         });

//         // Bouton "Annuler"
//         annulerButton.addActionListener(e -> dialog.dispose());

//         // Affichage du modal
//         dialog.setVisible(true);
//     }

//     /** Modal pour ajouter une formation */
//     private void ouvrirModalAjoutFormation() {
//         // Création du `JDialog` pour l'ajout de formation
//         JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter une Formation", true);
//         dialog.setSize(600, 300);
//         dialog.setLayout(new GridBagLayout());
//         dialog.setUndecorated(true);

//         dialog.setLocationRelativeTo(this);

//         // Définition du style

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.anchor = GridBagConstraints.WEST;
//         gbc.gridx = 0;
//         gbc.gridy = 0;

//         // Champs du formulaire
//         JLabel libelleLabel = new JLabel("Libellé de la Formation :");
//         libelleLabel.setFont(REGULAR_FONT);
//         dialog.add(libelleLabel, gbc);
//         gbc.gridx = 1;
//         JTextField libelleField = new JTextField(20);
//         libelleField.setFont(REGULAR_FONT);
//         dialog.add(libelleField, gbc);

//         gbc.gridx = 0;
//         gbc.gridy++;
//         JLabel nivLabel = new JLabel("Niveau :");
//         nivLabel.setFont(REGULAR_FONT);
//         dialog.add(nivLabel, gbc);
//         gbc.gridx = 1;
//         JTextField niveauField = new JTextField(20); // Utilisation d'un `JTextField` pour stocker des nombres
//         niveauField.setFont(REGULAR_FONT);
//         dialog.add(niveauField, gbc);

//         // Boutons
//         gbc.gridx = 0;
//         gbc.gridy++;
//         gbc.gridwidth = 2;
//         gbc.anchor = GridBagConstraints.CENTER;

//         JButton enregistrerButton = new JButton("Enregistrer");
//         enregistrerButton.setBackground(VERT_COLOR_1);
//         enregistrerButton.setForeground(Color.WHITE);
//         enregistrerButton.setFont(REGULAR_FONT);

//         JButton annulerButton = new JButton("Annuler");
//         annulerButton.setBackground(RED_COLOR);
//         annulerButton.setForeground(Color.WHITE);
//         annulerButton.setFont(REGULAR_FONT);

//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(enregistrerButton);
//         buttonPanel.add(annulerButton);

//         dialog.add(buttonPanel, gbc);

//         // Action sur le bouton "Enregistrer"
//         enregistrerButton.addActionListener(e -> {
//             String libelle = libelleField.getText();
//             String niveauText = niveauField.getText();

//             // Validation des champs
//             if (libelle.isEmpty() || niveauText.isEmpty()) {
//                 JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             int niveau;
//             try {
//                 niveau = Integer.parseInt(niveauText);
//                 if (niveau <= 0) {
//                     throw new NumberFormatException();
//                 }
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Le niveau doit être un entier supérieur à 0.", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             // Ajout de la formation via `FormationController`
//             String message = formationController.ajouterFormation(libelle, niveau);
//             JOptionPane.showMessageDialog(dialog, message, "Ajout Formation", JOptionPane.INFORMATION_MESSAGE);

//             // Rafraîchir la liste des formations
//             chargerFormations();

//             // Fermer le modal
//             dialog.dispose();
//         });

//         // Bouton "Annuler"
//         annulerButton.addActionListener(e -> dialog.dispose());

//         // Affichage du modal// package sn.uasz.m1.inscription.view.ResponsablePedagogique;

// // import javax.swing.*;
// // import javax.swing.plaf.nimbus.NimbusLookAndFeel;
// // import javax.swing.table.DefaultTableModel;
// // import javax.swing.table.TableRowSorter;
// // import java.awt.*;
// // import java.util.List;
// // import java.util.ArrayList;
// // import java.util.Comparator;
// // import sn.uasz.m1.inscription.controller.FormationController;
// // import sn.uasz.m1.inscription.model.Formation;

// // public class FormationUI extends JPanel {

// //     // 🎨 Déclaration des couleurs
// //     private static final Color VERT_COLOR_1 = new Color(0x113F36);
// //     private static final Color VERT_COLOR_2 = new Color(0x128E64);
// //     private static final Color BG_COLOR = new Color(0xF5F5F0);
// //     private static final Color RED_COLOR = new Color(0xcc1a1a);
// //     private static final Color GRAY_COLOR = new Color(0xededed);

// //     // 🖋 Déclaration des polices
// //     private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);
// //     private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);

// //     // 🏗 Composants principaux
// //     private JTable table;
// //     private DefaultTableModel tableModel;
// //     private final FormationController formationController;
// //     private final JPanel bottomPanel;
// //     private int selectedRow = -1;

// //     public FormationUI() {
// //         try {
// //             UIManager.setLookAndFeel(new NimbusLookAndFeel());
// //         } catch (Exception e) {
// //             System.out.println("Erreur Look and Feel: " + e.getMessage());
// //         }

// //         formationController = new FormationController();
// //         setLayout(new GridBagLayout());
// //         setBackground(BG_COLOR);
// //         GridBagConstraints gbc = new GridBagConstraints();
// //         gbc.fill = GridBagConstraints.HORIZONTAL;
// //         gbc.gridx = 0;
// //         gbc.weightx = 1.0;
// //         gbc.insets = new Insets(5, 10, 5, 10);

// //         // Ajouter les composants à l'interface
// //         add(createTopPanel(), gbc);
// //         gbc.gridy = 1;
// //         add(createTablePanel(), gbc);
// //         gbc.gridy = 2;
// //         add(createActionButtonsPanel(), gbc);
// //         gbc.gridy = 3;
// //         gbc.fill = GridBagConstraints.HORIZONTAL;
// //         bottomPanel = createBottomPanel();
// //         add(bottomPanel, gbc);

// //         // Charger les formations
// //         chargerFormations();
// //     }

// //     /** 🏗 Crée le panneau supérieur */
// //     private JPanel createTopPanel() {
// //         JPanel topPanel = new JPanel(new BorderLayout());
// //         topPanel.setBackground(BG_COLOR);

// //         // outon de tri A-Z
// //         JButton sortAscButton = createButton("🔼 Trier (A-Z)", VERT_COLOR_2, e -> trierTable(true));

// //         //Bouton de tri Z-A
// //         JButton sortDescButton = createButton("🔽 Trier (Z-A)", GRAY_COLOR, e -> trierTable(false));
// //         sortDescButton.setForeground(Color.BLACK);

// //         // Bouton de filtre
// //         JButton filterButton = createIconButton("src/main/resources/static/img/png/filter.png", GRAY_COLOR,
// //                 e -> JOptionPane.showMessageDialog(this, "Fonctionnalité de filtre à implémenter"));

// //         // Bouton d'ajout
// //         JButton addFormationButton = createButton("+ Ajouter Formation", VERT_COLOR_1,
// //                 e -> ouvrirModalAjoutFormation());

// //         JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
// //         rightPanel.add(sortAscButton);
// //         rightPanel.add(sortDescButton);
// //         rightPanel.add(filterButton);
// //         rightPanel.add(addFormationButton);

// //         JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
// //         leftPanel.add(createSearchPanel());

// //         topPanel.add(leftPanel, BorderLayout.EAST);
// //         topPanel.add(rightPanel, BorderLayout.WEST);
// //         return topPanel;
// //     }

// //     private ImageIcon createIcon(String path) {
// //         ImageIcon icon = new ImageIcon(path);
// //         Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
// //         return new ImageIcon(img);
// //     }

// //     /**Méthode pour trier la table */
// //     private void trierTable(boolean asc) {
// //         List<Object[]> data = new ArrayList<>();
// //         int rowCount = tableModel.getRowCount();

// //         for (int i = 0; i < rowCount; i++) {
// //             data.add(new Object[] {
// //                     tableModel.getValueAt(i, 0),
// //                     tableModel.getValueAt(i, 1),
// //                     Integer.parseInt(tableModel.getValueAt(i, 2).toString())
// //             });
// //         }

// //         // 🔄 Trie les formations par niveau (colonne 2)
// //         data.sort(Comparator.comparingInt(o -> (Integer) o[2]));
// //         if (!asc) {
// //             data.sort((o1, o2) -> Integer.compare((Integer) o2[2], (Integer) o1[2]));
// //         }

// //         // 🔄 Mise à jour du tableau
// //         tableModel.setRowCount(0);
// //         for (Object[] row : data) {
// //             tableModel.addRow(row);
// //         }
// //     }

// //     private JTextField createSearchField() {
// //         JTextField searchField = new JTextField(15);
// //         searchField.putClientProperty("JTextField.placeholderText", "Rechercher");
// //         searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
// //             public void insertUpdate(javax.swing.event.DocumentEvent e) {
// //                 filtrerFormations(searchField.getText());
// //             }

// //             public void removeUpdate(javax.swing.event.DocumentEvent e) {
// //                 filtrerFormations(searchField.getText());
// //             }

// //             public void changedUpdate(javax.swing.event.DocumentEvent e) {
// //                 filtrerFormations(searchField.getText());
// //             }
// //         });

// //         return searchField;
// //     }

// //     private JPanel createSearchPanel() {
// //         JPanel panel = new JPanel();
// //         panel.setBackground(BG_COLOR);

// //         panel.add(createSearchField());

// //         panel.add(new JLabel(createIcon("src/main/resources/static/img/png/search.png")));

// //         return panel;
// //     }

// //     /**Crée le tableau */
// //     private JPanel createTablePanel() {
// //         JPanel tablePanel = new JPanel(new BorderLayout());

// //         tableModel = new DefaultTableModel(new String[] { "ID", "Libelle", "Niveau" }, 0);
// //         table = new JTable(tableModel);
// //         table.setRowHeight(30);
// //         table.setFont(REGULAR_FONT);

// //         table.getSelectionModel().addListSelectionListener(e -> {
// //             selectedRow = table.getSelectedRow();
// //             bottomPanel.setVisible(selectedRow != -1);
// //         });

// //         tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
// //         return tablePanel;
// //     }

//     /**Crée les boutons d'action (Modifier, Supprimer) */
//     private JPanel createActionButtonsPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         panel.setBackground(BG_COLOR);
//         JButton modifyButton = createIconButton("src/main/resources/static/img/png/edit.png", VERT_COLOR_1,
//                 e -> ouvrirModalModificationFormation(selectedRow));
//         JButton deleteButton = createIconButton("src/main/resources/static/img/png/delete.png", RED_COLOR,
//                 e -> confirmerSuppression(selectedRow));
//         panel.add(modifyButton);
//         panel.add(deleteButton);
//         return panel;
//     }

//     /**Charge les formations */
//     private void chargerFormations() {
//         tableModel.setRowCount(0);
//         List<Formation> formations = formationController.listerFormationsResponsable();
//         for (Formation formation : formations) {
//             tableModel.addRow(new Object[] { formation.getId(), formation.getLibelle(), formation.getNiveau() });
//         }
//     }

//     /**Crée un bouton générique */
//     private JButton createButton(String text, Color bgColor, java.awt.event.ActionListener action) {
//         JButton button = new JButton(text);
//         button.setFont(BOLD_FONT);
//         button.setBackground(bgColor);
//         button.setForeground(Color.WHITE);
//         button.addActionListener(action);
//         return button;
//     }

//     /**rée un bouton avec icône */
//     private JButton createIconButton(String iconPath, Color bgColor, java.awt.event.ActionListener action) {
//         ImageIcon icon = new ImageIcon(
//                 new ImageIcon(iconPath).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
//         JButton button = new JButton(icon);
//         button.setBackground(bgColor);
//         button.setForeground(Color.WHITE);
//         button.addActionListener(action);
//         return button;
//     }

//     /**Filtrer les formations */
//     private void filtrerFormations(String recherche) {
//         TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
//         table.setRowSorter(sorter);
//         sorter.setRowFilter(recherche.trim().isEmpty() ? null : RowFilter.regexFilter("(?i)" + recherche, 1));
//     }

//     /**Crée le panneau bas */
//     private JPanel createBottomPanel() {
//         JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//         panel.setBackground(BG_COLOR);
//         panel.setVisible(false);
//         JButton listGroupsButton = createButton("Voir Groupes", GRAY_COLOR, e -> ouvrirPanelGroupes(selectedRow));
//         JButton addUE = createButton("Voir UEs", GRAY_COLOR, e -> ouvrirPanelUEs(selectedRow));
//         listGroupsButton.setForeground(Color.BLACK);
//         addUE.setForeground(Color.BLACK);
//         panel.add(listGroupsButton);
//         panel.add(addUE);
//         return panel;
//     }

//     /** Ouvrir le panel des groupes */
//     private void ouvrirPanelGroupes(int row) {
//         if (row == -1)
//             return;
//         Long formationId = (Long) table.getValueAt(row, 0);
//         Formation formation = formationController.trouverFormationParId(formationId);
//         if (formation != null) {
//             new FormationGroupeUI(formation).afficher();
//             ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
//         }
//     }

//     /** ouvrir panel des Ues */
//     private void ouvrirPanelUEs(int row){
//         if(row == -1)
//             return;
//         Long formationId = (Long) table.getValueAt(row, 0);
//         Formation formation = formationController.trouverFormationParId(formationId);
//         if (formation != null) {
//             new FormationUEUI(formation).afficher();
//             ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
//         }
//     }

//     /** 🗑 Confirmer la suppression */
//     private void confirmerSuppression(int row) {
//         if (row == -1)
//             return;
//         Long formationId = (Long) table.getValueAt(row, 0);
//         if (JOptionPane.showConfirmDialog(this, "Confirmer suppression ?", "Confirmation",
//                 JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
//             formationController.supprimerFormation(formationId);
//             chargerFormations();
//         }
//     }

//     /** Modal de modification */
//     private void ouvrirModalModificationFormation(int row) {
//         // Récupérer l'ID de la formation à partir de la ligne sélectionnée
//         Long formationId = (Long) table.getValueAt(row, 0); // La première colonne contient l'ID de la formation

//         // Récupérer la formation à partir de son ID
//         Formation formation = formationController.trouverFormationParId(formationId);

//         // Créer le modal pour modifier la formation
//         JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Modifier une Formation", true);
//         dialog.setSize(600, 300);
//         dialog.setLayout(new GridBagLayout());
//         dialog.setUndecorated(true);
//         dialog.setLocationRelativeTo(this);

//         // Définir le style et les contraintes du layout

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.anchor = GridBagConstraints.WEST;
//         gbc.gridx = 0;
//         gbc.gridy = 0;

//         // Champs du formulaire
//         JLabel libelleLabel = new JLabel("Libellé de la Formation :");
//         libelleLabel.setFont(REGULAR_FONT);
//         dialog.add(libelleLabel, gbc);
//         gbc.gridx = 1;
//         JTextField libelleField = new JTextField(20);
//         libelleField.setFont(REGULAR_FONT);
//         libelleField.setText(formation.getLibelle());
//         dialog.add(libelleField, gbc);

//         gbc.gridx = 0;
//         gbc.gridy++;
//         JLabel nivLabel = new JLabel("Niveau :");
//         nivLabel.setFont(REGULAR_FONT);
//         dialog.add(nivLabel, gbc);
//         gbc.gridx = 1;
//         JTextField niveauField = new JTextField(20);
//         niveauField.setFont(REGULAR_FONT);
//         niveauField.setText(String.valueOf(formation.getNiveau()));
//         dialog.add(niveauField, gbc);

//         // Boutons
//         gbc.gridx = 0;
//         gbc.gridy++;
//         gbc.gridwidth = 2;
//         gbc.anchor = GridBagConstraints.CENTER;

//         JButton enregistrerButton = new JButton("Enregistrer");
//         enregistrerButton.setBackground(VERT_COLOR_1);
//         enregistrerButton.setForeground(Color.WHITE);
//         enregistrerButton.setFont(REGULAR_FONT);

//         JButton annulerButton = new JButton("Annuler");
//         annulerButton.setBackground(RED_COLOR);
//         annulerButton.setForeground(Color.WHITE);
//         annulerButton.setFont(REGULAR_FONT);

//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(enregistrerButton);
//         buttonPanel.add(annulerButton);

//         dialog.add(buttonPanel, gbc);

//         // Action sur le bouton "Enregistrer"
//         enregistrerButton.addActionListener(e -> {
//             String libelle = libelleField.getText();
//             String niveauText = niveauField.getText();

//             // Validation des champs
//             if (libelle.isEmpty() || niveauText.isEmpty()) {
//                 JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             int niveau;
//             try {
//                 niveau = Integer.parseInt(niveauText);
//                 if (niveau <= 0) {
//                     throw new NumberFormatException();
//                 }
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Le niveau doit être un entier supérieur à 0.", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             String message = formationController.modifierFormation(formationId, libelle, niveau);
//             JOptionPane.showMessageDialog(dialog, message, "Modification Formation", JOptionPane.INFORMATION_MESSAGE);

//             // Rafraîchir la liste des formations
//             chargerFormations();

//             // Fermer le modal
//             dialog.dispose();
//         });

//         // Bouton "Annuler"
//         annulerButton.addActionListener(e -> dialog.dispose());

//         // Affichage du modal
//         dialog.setVisible(true);
//     }

//     /** Modal pour ajouter une formation */
//     private void ouvrirModalAjoutFormation() {
//         // Création du `JDialog` pour l'ajout de formation
//         JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ajouter une Formation", true);
//         dialog.setSize(600, 300);
//         dialog.setLayout(new GridBagLayout());
//         dialog.setUndecorated(true);

//         dialog.setLocationRelativeTo(this);

//         // Définition du style

//         GridBagConstraints gbc = new GridBagConstraints();
//         gbc.insets = new Insets(10, 10, 10, 10);
//         gbc.anchor = GridBagConstraints.WEST;
//         gbc.gridx = 0;
//         gbc.gridy = 0;

//         // Champs du formulaire
//         JLabel libelleLabel = new JLabel("Libellé de la Formation :");
//         libelleLabel.setFont(REGULAR_FONT);
//         dialog.add(libelleLabel, gbc);
//         gbc.gridx = 1;
//         JTextField libelleField = new JTextField(20);
//         libelleField.setFont(REGULAR_FONT);
//         dialog.add(libelleField, gbc);

//         gbc.gridx = 0;
//         gbc.gridy++;
//         JLabel nivLabel = new JLabel("Niveau :");
//         nivLabel.setFont(REGULAR_FONT);
//         dialog.add(nivLabel, gbc);
//         gbc.gridx = 1;
//         JTextField niveauField = new JTextField(20); // Utilisation d'un `JTextField` pour stocker des nombres
//         niveauField.setFont(REGULAR_FONT);
//         dialog.add(niveauField, gbc);

//         // Boutons
//         gbc.gridx = 0;
//         gbc.gridy++;
//         gbc.gridwidth = 2;
//         gbc.anchor = GridBagConstraints.CENTER;

//         JButton enregistrerButton = new JButton("Enregistrer");
//         enregistrerButton.setBackground(VERT_COLOR_1);
//         enregistrerButton.setForeground(Color.WHITE);
//         enregistrerButton.setFont(REGULAR_FONT);

//         JButton annulerButton = new JButton("Annuler");
//         annulerButton.setBackground(RED_COLOR);
//         annulerButton.setForeground(Color.WHITE);
//         annulerButton.setFont(REGULAR_FONT);

//         JPanel buttonPanel = new JPanel();
//         buttonPanel.add(enregistrerButton);
//         buttonPanel.add(annulerButton);

//         dialog.add(buttonPanel, gbc);

//         // Action sur le bouton "Enregistrer"
//         enregistrerButton.addActionListener(e -> {
//             String libelle = libelleField.getText();
//             String niveauText = niveauField.getText();

//             // Validation des champs
//             if (libelle.isEmpty() || niveauText.isEmpty()) {
//                 JOptionPane.showMessageDialog(dialog, "Tous les champs sont obligatoires !", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             int niveau;
//             try {
//                 niveau = Integer.parseInt(niveauText);
//                 if (niveau <= 0) {
//                     throw new NumberFormatException();
//                 }
//             } catch (NumberFormatException ex) {
//                 JOptionPane.showMessageDialog(dialog, "Le niveau doit être un entier supérieur à 0.", "Erreur",
//                         JOptionPane.ERROR_MESSAGE);
//                 return;
//             }

//             // Ajout de la formation via `FormationController`
//             String message = formationController.ajouterFormation(libelle, niveau);
//             JOptionPane.showMessageDialog(dialog, message, "Ajout Formation", JOptionPane.INFORMATION_MESSAGE);

//             // Rafraîchir la liste des formations
//             chargerFormations();

//             // Fermer le modal
//             dialog.dispose();
//         });

//         // Bouton "Annuler"
//         annulerButton.addActionListener(e -> dialog.dispose());

//         // Affichage du modal
//         dialog.setVisible(true);
//     }
// }

//         dialog.setVisible(true);
//     }
// }

package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import sn.uasz.m1.inscription.controller.FormationController;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.view.components.IconUI;

public class FormationUI extends JPanel {

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
    private final FormationController formationController;
    private final JPanel bottomPanel;
    private int selectedRow = -1;
    private JTextField searchField;
    private JLabel statusLabel;

    public FormationUI() {

        formationController = new FormationController();
        setLayout(new BorderLayout(0, 15));
        setBackground(BG_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Ajouter les composants à l'interface
        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        bottomPanel = createBottomPanel();
        bottomPanel.setVisible(false);
        add(bottomPanel, BorderLayout.SOUTH);

        // Charger les formations
        chargerFormations();
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

    /** 🏗 Crée l'en-tête de l'application */
    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Gestion des Formations");
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

        JButton addFormationButton = createButton("+ Ajouter Formation", VERT_COLOR_1, Color.WHITE,
                e -> ouvrirModalAjoutFormation());

        headerRow.add(addFormationButton, BorderLayout.EAST);

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
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher une formation...");
        searchField.setFont(REGULAR_FONT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
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

        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchField, BorderLayout.CENTER);

        // Icône de recherche
        JLabel searchIcon = new JLabel(IconUI.createIcon("src/main/resources/static/img/png/search.png", 35, 35));
        searchIcon.setBorder(new EmptyBorder(0, 0, 0, 10));
        searchContainer.add(searchIcon, BorderLayout.EAST);

        panel.add(searchContainer);
        return panel;
    }

    /** 🏗 Crée un panneau de filtres */
    private JPanel createFiltersPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        panel.setOpaque(false);

        // Bouton de tri A-Z
        JButton sortAscButton = createIconTextButton("🔼 Trier (A-Z)", null, VERT_COLOR_2, Color.WHITE,
                e -> trierTable(true));

        // Bouton de tri Z-A
        JButton sortDescButton = createIconTextButton("🔽 Trier (Z-A)", null, GRAY_COLOR, TEXT_COLOR,
                e -> trierTable(false));

        // Bouton de filtre
        JButton filterButton = createIconTextButton("Filtrer",
                IconUI.createIcon("src/main/resources/static/img/png/filter.png", 20, 20),
                GRAY_COLOR, TEXT_COLOR,
                e -> JOptionPane.showMessageDialog(this, "Fonctionnalité de filtre à implémenter"));

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

        tableModel = new DefaultTableModel(new String[] { "ID", "Libellé", "Niveau" }, 0) {
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
                    statusLabel.setText("Formation sélectionnée: " + table.getValueAt(selectedRow, 1));
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

        JLabel emptyLabel = new JLabel("Aucune formation disponible", JLabel.CENTER);
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

    /** 🏗 Crée le panneau des boutons d'action */
    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setOpaque(false);

        JButton refreshButton = createIconTextButton("Actualiser",
                IconUI.createIcon("src/main/resources/static/img/png/refresh.png", 20, 20),
                GRAY_COLOR, TEXT_COLOR, e -> chargerFormations());

        panel.add(refreshButton);

        return panel;
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

        JLabel titleLabel = new JLabel("Formation sélectionnée");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(VERT_COLOR_1);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton editButton = createIconTextButton("Modifier",
                IconUI.createIcon("src/main/resources/static/img/png/edit.png", 20, 20),
                VERT_COLOR_2, Color.WHITE, e -> modifierFormation());

        JButton deleteButton = createIconTextButton("Supprimer",
                IconUI.createIcon("src/main/resources/static/img/png/delete.png", 20, 20),
                RED_COLOR, Color.WHITE, e -> supprimerFormation());

        JButton groupsButton = createIconTextButton("Voir Groupes",
                IconUI.createIcon("src/main/resources/static/img/png/group.png", 20, 20),
                BLA_COLOR, Color.WHITE, e -> voirGroupes());

        JButton uesButton = createIconTextButton("Voir UEs",
                IconUI.createIcon("src/main/resources/static/img/png/group.png", 20, 20),
                BLA_COLOR, Color.WHITE, e -> voirUEs());

        buttonPanel.add(uesButton);
        buttonPanel.add(groupsButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private void voirGroupes() {
        if (selectedRow == -1)
            return;
        Long formationId = (Long) table.getValueAt(selectedRow, 0);
        Formation formation = formationController.trouverFormationParId(formationId);
        if (formation != null) {
            new FormationGroupeUI(formation).afficher();
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
        }
    }

    private void voirUEs() {
        if (selectedRow == -1)
            return;
        Long formationId = (Long) table.getValueAt(selectedRow, 0);
        Formation formation = formationController.trouverFormationParId(formationId);
        if (formation != null) {
            new FormationUEUI(formation).afficher();
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
        }
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

    /** 🏗 Crée un bouton moderne */
    private JButton createButton(String text, Color bgColor, Color fgColor, java.awt.event.ActionListener listener) {
        return createIconTextButton(text, null, bgColor, fgColor, listener);
    }

    /** 🏗 Crée un bouton avec icône */
    private JButton createIconButton(String iconPath, Color bgColor, java.awt.event.ActionListener listener) {
        JButton button = createIconTextButton("", IconUI.createIcon(iconPath, 20, 20), bgColor, TEXT_COLOR, listener);
        button.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(bgColor.darker(), 1, true),
                new EmptyBorder(8, 8, 8, 8)));
        return button;
    }

    /** Méthode pour trier la table */
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

        statusLabel.setText("Formations triées " + (asc ? "croissant" : "décroissant"));
    }

    /** Filtre les formations selon le texte recherché */
    private void filtrerFormations(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            chargerFormations();
            return;
        }

        searchText = searchText.toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));

        statusLabel.setText(sorter.getViewRowCount() + " formation(s) trouvée(s)");
    }

    /** Charge les formations depuis le contrôleur */
    private void chargerFormations() {
        tableModel.setRowCount(0);
        List<Formation> formations = formationController.listerFormationsResponsable();
        for (Formation formation : formations) {
            tableModel.addRow(new Object[] { formation.getId(), formation.getLibelle(), formation.getNiveau() });
        }

        statusLabel.setText(tableModel.getRowCount() + " formation(s) chargée(s)");

        // En-tête du tableau
        JTableHeader header = table.getTableHeader();
        header.setBackground(BLA_COLOR);

        // Réinitialiser le filtre
        table.setRowSorter(null);
    }

    private void supprimerFormation() {
        if (selectedRow == -1)
            return;
        Long formationId = (Long) table.getValueAt(selectedRow, 0);
        if (JOptionPane.showConfirmDialog(this, "Confirmer suppression ?", "Confirmation",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            formationController.supprimerFormation(formationId);
            chargerFormations();
        }
    }

    /** Modal de modification */
    private void modifierFormation() {
        // Récupérer l'ID de la formation à partir de la ligne sélectionnée
        Long formationId = (Long) table.getValueAt(selectedRow, 0); // La première colonne contient l'ID de la formation

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
