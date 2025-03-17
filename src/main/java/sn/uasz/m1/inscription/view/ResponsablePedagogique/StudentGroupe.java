package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;



import sn.uasz.m1.inscription.controller.FormationController;
import sn.uasz.m1.inscription.controller.GroupeController;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.Groupe;
import sn.uasz.m1.inscription.view.components.IconUI;
import sn.uasz.m1.inscription.view.components.Navbar;

import java.awt.*;
import java.awt.event.*;

public class StudentGroupe extends JFrame {
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

    //Controller et Service
    private final GroupeController groupeController;
    private Groupe groupe;

    public StudentGroupe(Groupe groupe){
         try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        this.groupeController = new GroupeController();
        this.groupe = groupe;

        setTitle("Lister Etudiants");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);

        Navbar navbar = new Navbar(this);

        add(navbar, BorderLayout.NORTH);
        add(createPrincipalPanel(), BorderLayout.CENTER);

        chargerEtudiants();

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

    private JPanel createPrincipalPanel(){
        JPanel main = new JPanel(new BorderLayout(0, 15));
        main.setBackground(BG_COLOR);
        main.setBorder(new EmptyBorder(20, 20, 20, 20));

       main.add(createHeader(), BorderLayout.NORTH);
       main.add(createMainContent(), BorderLayout.CENTER);
       main.add(createActionButtonsPanel(), BorderLayout.SOUTH);

       return main;
    }
    /** 🏗 Crée l'en-tête de l'application */
    private JPanel createHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Gestion des Etudiants");
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

        return mainPanel;
    }

     /** 🏗 Crée le panneau supérieur avec recherche et boutons */
     private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Deuxième ligne: recherche et filtres
        JPanel controlsRow = new JPanel(new BorderLayout(10, 0));
        controlsRow.setOpaque(false);
        // controlsRow.setBorder(new EmptyBorder(10, 0, 10, 0));

        JPanel searchPanel = createSearchPanel();
        JPanel filtersPanel = createFiltersPanel();

        controlsRow.add(searchPanel, BorderLayout.WEST);
        controlsRow.add(filtersPanel, BorderLayout.EAST);

        // topPanel.add(headerRow);
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
                filtrerEtudiants(searchField.getText());
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtrerEtudiants(searchField.getText());
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtrerEtudiants(searchField.getText());
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


    // /** 🏗 Crée un panneau de filtres */
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
                e -> filtrerParEmail());

        panel.add(sortAscButton);
        panel.add(sortDescButton);
        panel.add(filterButton);

        return panel;
    }

     private void filtrerParEmail() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Trier par colonne 0 (nom ou ID) en ordre alphabétique
        sorter.setSortKeys(List.of(new RowSorter.SortKey(4, SortOrder.ASCENDING)));

        statusLabel.setText("Etudiants triés par ordre alphabétique.");
    }

    // /** 🏗 Crée un tableau moderne avec les couleurs d'origine */
    private JPanel createTablePanel() {
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(15, 15, 15, 15)));

        tableModel = new DefaultTableModel(
                new String[] { "ID", "INE", "Nom", "Prenom", "Email"}, 0) {
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
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected,
                        hasFocus, row, column);
                setBorder(new EmptyBorder(5, 15, 5, 15));

                if (isSelected) {
                    c.setBackground(
                            new Color(VERT_COLOR_2.getRed(), VERT_COLOR_2.getGreen(),
                                    VERT_COLOR_2.getBlue(), 40));
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
                if (selectedRow != -1) {
                    statusLabel.setText("Etudiant sélectionné: " +
                            table.getValueAt(selectedRow, 1));
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

        JLabel emptyLabel = new JLabel("Aucun Etudiant disponible", JLabel.CENTER);
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

    private void trierTable(boolean asc) {
        List<Object[]> data = new ArrayList<>();
        int rowCount = tableModel.getRowCount();

        // Récupération des données actuelles du tableau
        for (int i = 0; i < rowCount; i++) {
            data.add(new Object[] {
                    tableModel.getValueAt(i, 0),
                    tableModel.getValueAt(i, 1),
                    tableModel.getValueAt(i, 2),
                    tableModel.getValueAt(i, 3),
                    tableModel.getValueAt(i, 4),
            });
        }

        // 🔄 Trie les formations par niveau (colonne 2)
        data.sort(Comparator.comparing(o -> (String) o[1]));
        // Si tri décroissant, on inverse
        if (!asc) {
            data.sort((o1, o2) -> ((String) o2[1]).compareTo((String) o1[1]));
        }

        // 🔄 Mise à jour du tableau
        tableModel.setRowCount(0);
        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        // Mise à jour du statut
        statusLabel.setText("Etudiants triées par niveau en ordre " + (asc ? "croissant" : "décroissant"));
    }

    private void filtrerEtudiants(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            chargerEtudiants();
            return;
        }

        searchText = searchText.toLowerCase().trim();

        // Création du sorter pour gérer le filtrage des données
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        try {
            // 🔍 Filtrer uniquement sur la colonne 2
            // sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(searchText), 3));
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(searchText)));
            statusLabel.setText(sorter.getViewRowCount() + " Etudiant(s) trouvée(s)");
        } catch (PatternSyntaxException e) {
            statusLabel.setText("Erreur de filtre : expression invalide");
        }
    }

    /** Charge les formations depuis le contrôleur */
    private void chargerEtudiants() {
        tableModel.setRowCount(0);
        List<Etudiant> etudiants = groupeController.listerEtudiantsParGroupe(groupe.getId());
        for (Etudiant etudiant : etudiants) {

            tableModel.addRow(new Object[] {
                    etudiant.getId(),
                    etudiant.getIne(),
                    etudiant.getNom(),
                    etudiant.getPrenom(),
                    etudiant.getEmail()
            });
        }

        statusLabel.setText(tableModel.getRowCount() + " Etudiant(s) chargé(s)");

        // En-tête du tableau
        JTableHeader header = table.getTableHeader();
        header.setBackground(BLA_COLOR);

        // Réinitialiser le filtre
        table.setRowSorter(null);
    }

    private JPanel createActionButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setOpaque(false);

        JButton refreshButton = createIconTextButton("Actualiser",
                IconUI.createIcon("src/main/resources/static/img/png/refresh.png", 20, 20),
                GRAY_COLOR, TEXT_COLOR, e -> chargerEtudiants());
        JButton returnButton = createIconTextButton("Return To Dashboard", null, BLA_COLOR, Color.WHITE, e -> navigateToDashboard());

        panel.add(refreshButton);
        panel.add(returnButton);

        return panel;
    }

    public void afficher(){
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
