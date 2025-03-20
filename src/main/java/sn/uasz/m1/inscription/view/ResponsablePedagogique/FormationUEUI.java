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

import sn.uasz.m1.inscription.controller.UEController;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.utils.PDFExporter;
import sn.uasz.m1.inscription.view.components.IconUI;
import sn.uasz.m1.inscription.view.components.Navbar;

import java.awt.*;
import java.awt.event.*;

public class FormationUEUI extends JFrame {
    // 🎨 Déclaration des couleurs (conservées de l'original)
    private static final Color VERT_COLOR_1 = new Color(0x113F36);
    private static final Color VERT_COLOR_2 = new Color(0x128E64);
    private static final Color VERT_3 = new Color(0x0B7968);
    private static final Color BLA_COLOR = new Color(0x151d21);
    private static final Color BG_COLOR = new Color(0xF2F2F2);
    private static final Color RED_COLOR = new Color(0xcc1a1a);
    private static final Color GRAY_COLOR = new Color(0xC6BFBF);
    private static final Color RED_S_COLOR = new Color(0xb30404);

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
    private final UEController ueController;
    private final JPanel bottomPanel;

    public FormationUEUI(Formation formation) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.formation = formation;
        this.ueController = new UEController();

        setTitle("Lister UEs");
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

        chargerUEs();

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

        JLabel titleLabel = new JLabel("Gestion des UEs");
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

        JButton addUEButton = createButton("+ Ajouter UE", VERT_COLOR_1, Color.WHITE,
                e -> ajouterUE());

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
        searchField.putClientProperty("JTextField.placeholderText", "Rechercher un etudiant...");
        searchField.setFont(REGULAR_FONT);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(8, 12, 8, 12)));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtrerUEs(searchField.getText());
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerUEs(searchField.getText());
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerUEs(searchField.getText());
            }
        });

        JPanel searchContainer = new JPanel(new BorderLayout());
        searchContainer.setOpaque(false);
        searchContainer.add(searchField, BorderLayout.CENTER);

        // Icône de recherche
        JLabel searchIcon = new JLabel(IconUI.createIcon("static/img/png/search.png", 25,
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
                IconUI.createIcon("static/img/png/refresh.png", 20, 20),
                GRAY_COLOR, TEXT_COLOR, e -> chargerUEs());
        JButton returnButton = createIconTextButton("Return To Dashboard", null, BLA_COLOR, Color.WHITE,
                e -> navigateToDashboard());
        JButton pdfButton = new JButton(IconUI.createIcon("static/img/png/pdf.png", 30, 30));
        pdfButton.setBackground(RED_S_COLOR);
        pdfButton.addActionListener(e -> exporterPDF());

        panel.add(pdfButton);

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
                IconUI.createIcon("static/img/png/filter.png", 20, 20),
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
                new String[] { "ID", "Code", "Libelle", "Credit", "Coefficient", "Volume Horaire", "Enseignant",
                        "OPTION" },
                0) {
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
                    statusLabel.setText("UE sélectionnée: " + table.getValueAt(selectedRow, 1));
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

        JLabel emptyLabel = new JLabel("Aucune UE disponible", JLabel.CENTER);
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
        sorter.setSortKeys(List.of(new RowSorter.SortKey(1, SortOrder.ASCENDING)));

        statusLabel.setText("UEs triées par ordre alphabétique.");
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

        JLabel titleLabel = new JLabel("UE sélectionnée");
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(VERT_COLOR_1);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);

        JButton editButton = createIconTextButton("Modifier",
                IconUI.createIcon("static/img/png/edit.png", 20, 20),
                VERT_COLOR_2, Color.WHITE, e -> modifierUE());

        JButton deleteButton = createIconTextButton("Supprimer",
                IconUI.createIcon("static/img/png/delete.png", 20, 20),
                RED_COLOR, Color.WHITE, e -> supprimerUE());

        JButton studentsButton = createIconTextButton("Voir Etudiants",
                IconUI.createIcon("static/img/png/group.png", 20, 20),
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
        Long ueId = (Long) table.getValueAt(selectedRow, 0);
        UE ue = ueController.trouverUEParId(ueId);
        if (ue != null) {
            new StudentUE(ue).afficher();
            ((JFrame) SwingUtilities.getWindowAncestor(this)).dispose();
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
                    tableModel.getValueAt(i, 3),
                    tableModel.getValueAt(i, 4),
                    tableModel.getValueAt(i, 5),
                    tableModel.getValueAt(i, 6),

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

        statusLabel.setText("UE triées " + (asc ? "croissant" : "décroissant"));
    }

    /** 🏗 Crée un bouton moderne */
    private JButton createButton(String text, Color bgColor, Color fgColor, java.awt.event.ActionListener listener) {
        return createIconTextButton(text, null, bgColor, fgColor, listener);
    }

    /** Filtre les formations selon le texte recherché */
    private void filtrerUEs(String searchText) {
        if (searchText == null || searchText.isEmpty()) {
            chargerUEs();
            return;
        }

        searchText = searchText.toLowerCase();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));

        statusLabel.setText(sorter.getViewRowCount() + " UE(s) trouvée(s)");
    }

    /** Charge les formations depuis le contrôleur */
    private void chargerUEs() {
        tableModel.setRowCount(0);
        List<UE> ues = ueController.getUEsByFormation(formation.getId());
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

        statusLabel.setText(tableModel.getRowCount() + " ue(s) chargée(s)");

        // En-tête du tableau
        JTableHeader header = table.getTableHeader();
        header.setBackground(BLA_COLOR);

        // Réinitialiser le filtre
        table.setRowSorter(null);
    }

    private void ajouterUE() {
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
        saveButton.setFont(BUTTON_FONT);
        saveButton.setBackground(VERT_COLOR_1);
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveGroup(ueComboBox, obligatoireCheckBox, dialog));

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setFont(BUTTON_FONT);
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

    private void modifierUE() {
        if (selectedRow != -1) {
            Long ueId = (Long) table.getValueAt(selectedRow, 0);

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
            enregistrerButton.setFont(BUTTON_FONT);

            JButton annulerButton = new JButton("Annuler");
            annulerButton.setBackground(RED_COLOR);
            annulerButton.setForeground(Color.WHITE);
            annulerButton.setFont(BUTTON_FONT);

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

    private void supprimerUE() {
        // Vérifie si une ligne est sélectionnée
        if (selectedRow != -1) {
            // Récupère l'ID de l'UE à partir de la ligne sélectionnée
            Long ueID = (Long) table.getValueAt(selectedRow, 0);

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

    private void exporterPDF() {
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner une UE.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Long ueId = (Long) table.getValueAt(selectedRow, 0);
        UE ue = ueController.trouverUEParId(ueId);

        PDFExporter pdfExporter = new PDFExporter();
        pdfExporter.exporterListeEtudiantsUE(ue);
    }

}
