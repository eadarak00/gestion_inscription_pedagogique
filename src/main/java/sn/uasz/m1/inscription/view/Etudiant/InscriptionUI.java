package sn.uasz.m1.inscription.view.Etudiant;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import sn.uasz.m1.inscription.controller.InscriptionController;
import sn.uasz.m1.inscription.model.Etudiant;
import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.model.UE;
import sn.uasz.m1.inscription.model.Utilisateur;
import sn.uasz.m1.inscription.service.EtudiantService;
import sn.uasz.m1.inscription.service.FormationService;
import sn.uasz.m1.inscription.utils.SessionManager;
import sn.uasz.m1.inscription.view.components.NavbarPanel;
import sn.uasz.m1.inscription.view.components.StepIndicator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InscriptionUI extends JFrame {
    // Déclaration des couleurs
    private final Color VERT_COLOR_1 = new Color(0x113F36);
    private final Color VERT_COLOR_2 = new Color(0x128E64);
    private final Color BG_COLOR = new Color(0xF2F2F2);
    private final Color GRAY_COLOR = new Color(0xF1F1F1);

    // Déclaration des polices
    private static final Font BOLD_FONT_18 = new Font("Poppins", Font.BOLD, 18);
    private static final Font BOLD_FONT = new Font("Poppins", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Poppins", Font.PLAIN, 14);

    // Déclaration des composants
    private JComboBox<Formation> formationComboBox;
    private JPanel ueCheckboxPanel; // Remplacer par un JPanel contenant des JCheckBox
    private JButton suivantButton;
    private JButton precedentButton;
    private List<UE> selectedUes = new ArrayList<>();

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Ajout de step3Panel comme attribut de classe
    private JPanel step3Panel;

    // Service
    private final FormationService formationService;
    private final EtudiantService etudiantService;

    public InscriptionUI() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.formationService = new FormationService();
        this.etudiantService = new EtudiantService();

        setTitle("Inscription Étudiant");
        setSize(1500, 700);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = (JPanel) getContentPane();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(BG_COLOR);

        NavbarPanel navbar = new NavbarPanel(this);
        mainPanel.add(navbar, BorderLayout.NORTH);

        // Utilisation de CardLayout pour les différentes étapes
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Ajouter les étapes dans le CardLayout
        cardPanel.add(createStep1Panel(), "Step1");
        cardPanel.add(createStep2Panel(), "Step2");
        cardPanel.add(createStep3Panel(), "Step3");

        mainPanel.add(createRightPanel(), BorderLayout.EAST);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
    }

    // private JPanel createStep1Panel() {
    //     JPanel step1Panel = new JPanel(new GridBagLayout());
    //     step1Panel.setBackground(BG_COLOR);
    //     GridBagConstraints gbc = new GridBagConstraints();
    //     gbc.insets = new Insets(10, 10, 10, 10);

    //     // 🔹 Ajouter l'indicateur d'étape 1
    //     gbc.gridx = 0;
    //     gbc.gridy = 0;
    //     gbc.gridwidth = 2;
    //     step1Panel.add(new StepIndicator(1), gbc);

    //     // Ajouter un label pour la formation
    //     JLabel formationLabel = new JLabel("Choisir la formation");
    //     formationLabel.setFont(BOLD_FONT_18);
    //     gbc.gridy = 1;
    //     step1Panel.add(formationLabel, gbc);

    //     // Sélecteur de formation
    //     formationComboBox = new JComboBox<>();
    //     List<Formation> formations = formationService.getAllFormations();
    //     for (Formation f : formations) {
    //         formationComboBox.addItem(f);
    //     }

    //     formationComboBox.setRenderer(new DefaultListCellRenderer() {
    //         @Override
    //         public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
    //                 boolean cellHasFocus) {
    //             super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    //             if (value instanceof Formation) {
    //                 Formation formation = (Formation) value;
    //                 setText(formation.getLibelle());
    //             }
    //             return this;
    //         }
    //     });

    //     formationComboBox.setFont(REGULAR_FONT);
    //     formationComboBox.addActionListener(e -> updateUECheckboxPanel());

    //     gbc.gridy = 2;
    //     step1Panel.add(formationComboBox, gbc);

    //     // Bouton Suivant
    //     suivantButton = new JButton("Suivant ➡");
    //     suivantButton.setFont(BOLD_FONT);
    //     suivantButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));

    //     gbc.gridy = 3;
    //     step1Panel.add(suivantButton, gbc);

    //     return step1Panel;
    // }

    private JPanel createStep1Panel() {
        JPanel step1Panel = new JPanel(new GridBagLayout());
        step1Panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);  // Espacement plus large pour une meilleure lisibilité
    
        // 🔹 Ajouter l'indicateur d'étape 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        step1Panel.add(new StepIndicator(1), gbc);
    
        // Ajouter un label pour la formation
        JLabel formationLabel = new JLabel("Choisir la formation");
        formationLabel.setFont(BOLD_FONT_18);
        formationLabel.setForeground(new Color(0x113F36)); // Couleur plus attrayante pour le texte
        gbc.gridy = 1;
        step1Panel.add(formationLabel, gbc);
    
        // Sélecteur de formation
        formationComboBox = new JComboBox<>();
        List<Formation> formations = formationService.getAllFormations();
        for (Formation f : formations) {
            formationComboBox.addItem(f);
        }
    
        formationComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Formation) {
                    Formation formation = (Formation) value;
                    setText(formation.getLibelle());
                }
                return this;
            }
        });
    
        formationComboBox.setFont(REGULAR_FONT);
        formationComboBox.setBackground(new Color(0xFFFFFF)); // Fond blanc pour le combo
        formationComboBox.setForeground(new Color(0x113F36)); // Texte plus sombre pour un meilleur contraste
    
        formationComboBox.addActionListener(e -> updateUECheckboxPanel());
    
        gbc.gridy = 2;
        step1Panel.add(formationComboBox, gbc);
    
        // Bouton Suivant
        suivantButton = new JButton("Suivant ➡");
        suivantButton.setFont(new Font("Poppins", Font.BOLD, 18));
        suivantButton.setBackground(VERT_COLOR_2);
        suivantButton.setForeground(Color.WHITE); // Texte blanc
        suivantButton.setFocusPainted(false); // Retirer l'effet de focus du bouton
        suivantButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change le curseur pour indiquer une action
    
        suivantButton.addActionListener(e -> {
            // Animation de transition pour l'étape suivante
            suivantButton.setEnabled(false); // Désactiver le bouton pendant la transition
            // Optionnel : Ajouter une animation ou une transition ici
            cardLayout.show(cardPanel, "Step2");
            suivantButton.setEnabled(true); // Réactiver le bouton après la transition
        });
    
        gbc.gridy = 3;
        step1Panel.add(suivantButton, gbc);
    
        return step1Panel;
    }
    

    // private JPanel createStep2Panel() {
    //     JPanel step2Panel = new JPanel(new GridBagLayout());
    //     step2Panel.setBackground(BG_COLOR);
    //     GridBagConstraints gbc = new GridBagConstraints();
    //     gbc.insets = new Insets(10, 10, 10, 10);

    //     // 🔹 Ajouter l'indicateur d'étape 2
    //     gbc.gridx = 0;
    //     gbc.gridy = 0;
    //     gbc.gridwidth = 2;
    //     step2Panel.add(new StepIndicator(2), gbc);

    //     // Ajouter un label pour les UEs
    //     JLabel ueLabel = new JLabel("Choisir les UEs optionnelles");
    //     ueLabel.setFont(BOLD_FONT_18);
    //     gbc.gridy = 1;
    //     step2Panel.add(ueLabel, gbc);

    //     // Panel pour les checkboxes
    //     ueCheckboxPanel = new JPanel();
    //     ueCheckboxPanel.setLayout(new BoxLayout(ueCheckboxPanel, BoxLayout.Y_AXIS)); // Disposition verticale des checkboxes
    //     gbc.gridy = 2;
    //     step2Panel.add(new JScrollPane(ueCheckboxPanel), gbc); // Ajouter un JScrollPane pour le défilement

    //     // Panel pour les boutons
    //     JPanel buttonPanel = new JPanel(new BorderLayout());
    //     buttonPanel.setBackground(BG_COLOR);

    //     precedentButton = new JButton("⬅ Précédent");
    //     precedentButton.setFont(BOLD_FONT);
    //     precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step1"));

    //     suivantButton = new JButton("Suivant ➡");
    //     suivantButton.setFont(BOLD_FONT);
    //     suivantButton.addActionListener(e -> {
    //         updateStep3Panel(); // Mettre à jour les informations avant d'afficher l'étape 3
    //         cardLayout.show(cardPanel, "Step3");
    //     });

    //     // Ajouter les boutons aux extrémités du `buttonPanel`
    //     buttonPanel.add(precedentButton, BorderLayout.WEST);
    //     buttonPanel.add(suivantButton, BorderLayout.EAST);

    //     gbc.gridy = 3;
    //     gbc.fill = GridBagConstraints.HORIZONTAL;
    //     gbc.weighty = 0;
    //     step2Panel.add(buttonPanel, gbc);

    //     return step2Panel;
    // }

    private JPanel createStep2Panel() {
        JPanel step2Panel = new JPanel(new GridBagLayout());
        step2Panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);  // Espacements plus larges pour aérer l'interface
    
        // 🔹 Ajouter l'indicateur d'étape 2
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        step2Panel.add(new StepIndicator(2), gbc);
    
        // Ajouter un label pour les UEs
        JLabel ueLabel = new JLabel("Choisir les UEs optionnelles");
        ueLabel.setFont(new Font("Poppins", Font.BOLD, 24)); // Plus grand et plus moderne
        ueLabel.setForeground(new Color(0x113F36)); // Couleur plus attrayante et professionnelle
        gbc.gridy = 1;
        step2Panel.add(ueLabel, gbc);
    
        // Panel pour les checkboxes
        ueCheckboxPanel = new JPanel();
        ueCheckboxPanel.setLayout(new BoxLayout(ueCheckboxPanel, BoxLayout.Y_AXIS)); // Disposition verticale des checkboxes
        ueCheckboxPanel.setBackground(BG_COLOR);
        
        // Ajout des checkboxes (exemple, à adapter selon les données)
        
        for (int i = 1; i <= 5; i++) { // Juste un exemple, mettez vos véritables options
            JCheckBox checkBox = new JCheckBox("UE Option " + i);
            checkBox.setFont(new Font("Poppins", Font.PLAIN, 18));
            checkBox.setBackground(BG_COLOR);
            checkBox.setForeground(new Color(0x113F36));
            checkBox.setFocusPainted(false); // Enlever la bordure de focus
            // checkBox.setBorder(BorderFactory.createLineBorder(new Color(0xAAAAAA), 1)); // Bordure subtile
            checkBox.setMargin(new Insets(10, 10, 10, 10)); // Espacement interne
    
            ueCheckboxPanel.add(checkBox);
        }
    
        gbc.gridy = 2;
        JScrollPane scrollPane = new JScrollPane(ueCheckboxPanel);
        // scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x128E64), 2)); // Bordure verte
        step2Panel.add(scrollPane, gbc); // Ajouter un JScrollPane pour le défilement
    
        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(BG_COLOR);
    
        // Bouton "Précédent"
        precedentButton = new JButton("⬅ Précédent");
        precedentButton.setFont(new Font("Poppins", Font.BOLD, 18));
        precedentButton.setBackground(new Color(0xF1F1F1)); // Fond gris clair
        precedentButton.setForeground(new Color(0x113F36)); // Texte sombre
        precedentButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Curseur main
        precedentButton.setFocusPainted(false); // Désactiver focus painté
        precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step1"));
    
        // Bouton "Suivant"
        suivantButton = new JButton("Suivant ➡");
        suivantButton.setFont(new Font("Poppins", Font.BOLD, 18));
        suivantButton.setBackground(new Color(0x128E64)); // Fond vert
        suivantButton.setForeground(Color.WHITE); // Texte blanc
        suivantButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Curseur main
        suivantButton.setFocusPainted(false); // Désactiver focus painté
        suivantButton.addActionListener(e -> {
            updateStep3Panel(); // Mettre à jour les informations avant d'afficher l'étape 3
            cardLayout.show(cardPanel, "Step3");
        });
    
        // Ajouter les boutons aux extrémités du `buttonPanel`
        buttonPanel.add(precedentButton, BorderLayout.WEST);
        buttonPanel.add(suivantButton, BorderLayout.EAST);
    
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;
        step2Panel.add(buttonPanel, gbc);
    
        return step2Panel;
    }
    

    private JPanel createStep3Panel() {
    step3Panel = new JPanel(new GridBagLayout()); 
    step3Panel.setBackground(BG_COLOR);
    step3Panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2), "📝 Informations de l'Inscription",
            TitledBorder.CENTER, TitledBorder.TOP, BOLD_FONT_18, Color.BLACK));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 15, 10, 15); 
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.anchor = GridBagConstraints.CENTER;

    // 🔹 Icône informative
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    JLabel infoIcon = new JLabel(createIcon("src/main/resources/static/img/png/info-icon.png", 50, 50));
    step3Panel.add(infoIcon, gbc);

    // 🔹 Titre central
    JLabel titleLabel = new JLabel("📌 Récapitulatif de votre inscription", SwingConstants.CENTER);
    titleLabel.setFont(BOLD_FONT_18);
    gbc.gridy++;
    step3Panel.add(titleLabel, gbc);

    // 🔹 Récupération des données utilisateur
    Utilisateur user = SessionManager.getUtilisateur();
    if (user == null || user.getId() == null) {
        JOptionPane.showMessageDialog(step3Panel, "Erreur : Utilisateur non connecté.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return step3Panel;
    }

    Etudiant etudiant = etudiantService.getEtudiantById(user.getId());
    if (etudiant == null) {
        JOptionPane.showMessageDialog(step3Panel, "Erreur : Étudiant introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return step3Panel;
    }

    // 🔹 Création d'un panneau encadré pour les informations de l'étudiant
    JPanel etudiantPanel = new JPanel(new GridLayout(3, 1, 10, 10));
    etudiantPanel.setBorder(BorderFactory.createTitledBorder("👨‍🎓 Étudiant"));
    etudiantPanel.setBackground(Color.WHITE);

    etudiantPanel.add(new JLabel("Nom : " + etudiant.getPrenom() + " " + etudiant.getNom(), SwingConstants.LEFT));
    etudiantPanel.add(new JLabel("INE : " + etudiant.getIne(), SwingConstants.LEFT));

    gbc.gridy++;
    step3Panel.add(etudiantPanel, gbc);

    // 🔹 Récupération et affichage de la formation
    Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();
    if (selectedFormation == null) {
        JOptionPane.showMessageDialog(step3Panel, "Erreur : Aucune formation sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return step3Panel;
    }

    JPanel formationPanel = new JPanel(new GridLayout(1, 1, 10, 10));
    formationPanel.setBorder(BorderFactory.createTitledBorder("🎓 Formation"));
    formationPanel.setBackground(Color.WHITE);
    
    formationPanel.add(new JLabel("Formation : " + selectedFormation.getLibelle(), SwingConstants.LEFT));

    gbc.gridy++;
    step3Panel.add(formationPanel, gbc);

    // 🔹 Liste des UEs obligatoires
    List<UE> uesObligatoires = formationService.getRequiredUEs(selectedFormation.getId());
    String uesObligatoiresText = uesObligatoires.isEmpty() ? "Aucune"
            : uesObligatoires.stream().map(UE::getLibelle).collect(Collectors.joining(", "));

    JPanel ueObligPanel = new JPanel(new GridLayout(1, 1, 10, 10));
    ueObligPanel.setBorder(BorderFactory.createTitledBorder("📚 UEs obligatoires"));
    ueObligPanel.setBackground(Color.WHITE);
    ueObligPanel.add(new JLabel(uesObligatoiresText, SwingConstants.LEFT));

    gbc.gridy++;
    step3Panel.add(ueObligPanel, gbc);

    // 🔹 Liste des UEs optionnelles choisies
    String selectedUesText = selectedUes.isEmpty() ? "Aucune"
            : selectedUes.stream().map(UE::getLibelle).collect(Collectors.joining(", "));

    JPanel ueOptionPanel = new JPanel(new GridLayout(1, 1, 10, 10));
    ueOptionPanel.setBorder(BorderFactory.createTitledBorder("📝 UEs optionnelles choisies"));
    ueOptionPanel.setBackground(Color.WHITE);
    ueOptionPanel.add(new JLabel(selectedUesText, SwingConstants.LEFT));

    gbc.gridy++;
    step3Panel.add(ueOptionPanel, gbc);

    // 🔹 Boutons bien stylisés
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton precedentButton = new JButton("⬅ Précédent");
    precedentButton.setFont(BOLD_FONT);
    precedentButton.setBackground(Color.LIGHT_GRAY);
    precedentButton.setForeground(Color.BLACK);
    precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));

    JButton confirmerButton = new JButton("✅ Confirmer Inscription");
    confirmerButton.setFont(BOLD_FONT);
    confirmerButton.setBackground(new Color(46, 204, 113)); // Vert
    confirmerButton.setForeground(Color.WHITE);
    // confirmerButton.addActionListener(e -> validerInscription());

    buttonPanel.add(precedentButton);
    buttonPanel.add(confirmerButton);

    gbc.gridy++;
    step3Panel.add(buttonPanel, gbc);

    return step3Panel;
}


    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(650, 0));
        panel.setBackground(GRAY_COLOR);
        panel.setLayout(new BorderLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 40));

        JLabel imgLabel = new JLabel(createIcon("src/main/resources/static/img/png/landing_5.png", 500, 500));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(imgLabel, BorderLayout.CENTER);
        return panel;
    }

    private ImageIcon createIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // private void updateUECheckboxPanel() {
    //     // Récupérer la formation sélectionnée
    //     Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();

    //     // Vérifier si une formation a été sélectionnée
    //     if (selectedFormation != null) {
    //         // Obtenir les UEs optionnelles de la formation
    //         List<UE> uesOptionnelles = formationService.getOptionalUEs(selectedFormation.getId());

    //         // Mettre à jour le panneau de checkboxes
    //         ueCheckboxPanel.removeAll();
    //         if (uesOptionnelles.isEmpty()) {
    //             ueCheckboxPanel.add(new JLabel("Aucune UE optionnelle disponible"));
    //         } else {
    //             for (UE ue : uesOptionnelles) {
    //                 JCheckBox checkBox = new JCheckBox(ue.getLibelle());
    //                 checkBox.putClientProperty("ue", ue);
    //                 checkBox.setFont(REGULAR_FONT);
    //                 checkBox.addActionListener(e -> updateSelectedUes());
    //                 ueCheckboxPanel.add(checkBox);
    //             }
    //         }
    //         ueCheckboxPanel.revalidate();
    //         ueCheckboxPanel.repaint();
    //     }
    // }

    // private void updateSelectedUes() {
    //     selectedUes.clear();
    //     for (Component component : ueCheckboxPanel.getComponents()) {
    //         if (component instanceof JCheckBox) {
    //             JCheckBox checkBox = (JCheckBox) component;
    //             if (checkBox.isSelected()) {
    //                 selectedUes.add((UE) checkBox.getClientProperty("ue"));
    //             }
    //         }
    //     }
    // }

    private void updateUECheckboxPanel() {
        // Récupérer la formation sélectionnée
        Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();
        
    
        // Vérifier si une formation a été sélectionnée
        if (selectedFormation != null) {
            // Obtenir les UEs optionnelles de la formation
            List<UE> uesOptionnelles = formationService.getOptionalUEs(selectedFormation.getId());
    
            // Mettre à jour le panneau de checkboxes
            ueCheckboxPanel.removeAll();
            
            // Afficher un message si aucune UE n'est disponible
            if (uesOptionnelles.isEmpty()) {
                ueCheckboxPanel.add(new JLabel("Aucune UE optionnelle disponible"));
            } else {
                // Limiter le nombre d'UEs sélectionnables à la moitié des UEs disponibles
                int maxSelectableUes = uesOptionnelles.size() / 2;
                
                JLabel selectionInfoLabel = new JLabel("Sélectionnez exactement " + maxSelectableUes + " UE(s)");
                selectionInfoLabel.setFont(new Font("Poppins", Font.ITALIC, 16));
                selectionInfoLabel.setForeground(new Color(0x128E64)); // Couleur verte
                ueCheckboxPanel.add(selectionInfoLabel);
                    
                // Gérer les checkboxes
                for (UE ue : uesOptionnelles) {

                    JCheckBox checkBox = new JCheckBox(ue.getLibelle());
                    checkBox.putClientProperty("ue", ue); // Stocker l'UE associée
                    checkBox.setFont(new Font("Poppins", Font.PLAIN, 18));
                    checkBox.setBackground(new Color(0xF5F5F5)); // Fond clair pour les cases
                    checkBox.setForeground(new Color(0x113F36)); // Texte vert foncé
                    checkBox.setFocusPainted(false);
                    checkBox.setBorder(BorderFactory.createLineBorder(new Color(0x128E64), 2)); // Bordure verte subtile
                    checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Curseur main
                    checkBox.setMargin(new Insets(10, 10, 10, 10)); // Espacement interne
    
                    // Ajouter un listener pour la sélection
                    checkBox.addActionListener(e -> updateSelectedUes(maxSelectableUes));
    
                    // Ajouter chaque checkbox au panneau
                    ueCheckboxPanel.add(checkBox);
                }
            }
    
            // Rafraîchir l'affichage du panneau
            ueCheckboxPanel.revalidate();
            ueCheckboxPanel.repaint();
        }
    }
    
    /**
     * Méthode pour vérifier et gérer les sélections d'UEs
     * @param maxSelectableUes Le nombre maximum d'UEs que l'utilisateur peut sélectionner
     */
    private void updateSelectedUes(int maxSelectableUes) {
        // Récupérer toutes les checkboxes du panneau
        Component[] components = ueCheckboxPanel.getComponents();
        int selectedCount = 0;
    
        // Compter les cases sélectionnées
        for (Component comp : components) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    selectedCount++;
                }
            }
        }
    
        // Si le nombre d'UEs sélectionnées est supérieur à la limite, désactiver les autres
        if (selectedCount > maxSelectableUes) {
            JOptionPane.showMessageDialog(null, "Vous ne pouvez pas sélectionner plus de " + maxSelectableUes + " UEs.", "Limite atteinte", JOptionPane.WARNING_MESSAGE);
            // Désactiver les autres checkboxes si le maximum est atteint
            for (Component comp : components) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    if (!checkBox.isSelected()) {
                        checkBox.setEnabled(false);
                    }
                }
            }
        } else {
            // Réactiver toutes les checkboxes si le nombre sélectionné est en-dessous du maximum
            for (Component comp : components) {
                if (comp instanceof JCheckBox) {
                    JCheckBox checkBox = (JCheckBox) comp;
                    checkBox.setEnabled(true);
                }
            }
        }
    
        // Mettre à jour la liste des UEs sélectionnées
        selectedUes.clear();
        for (Component comp : components) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected()) {
                    selectedUes.add((UE) checkBox.getClientProperty("ue"));
                }
            }
        }
    }
    

    // private void updateStep3Panel() {
    //     Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();
    //     if (selectedFormation != null) {
    //         List<UE> uesObligatoires = formationService.getRequiredUEs(selectedFormation.getId());
    //         String uesObligatoiresText = uesObligatoires.isEmpty() ? "Aucune"
    //                 : uesObligatoires.stream().map(UE::getLibelle).collect(Collectors.joining(", "));
    
    //         String selectedUesText = selectedUes.isEmpty() ? "Aucune"
    //                 : selectedUes.stream().map(UE::getLibelle).collect(Collectors.joining(", "));
    
    //         Utilisateur user = SessionManager.getUtilisateur();
    //         Etudiant student = etudiantService.getEtudiantById(user.getId());
    
    //         step3Panel.removeAll();
    //         step3Panel.setLayout(new BorderLayout());
    //         step3Panel.setBackground(new Color(240, 240, 240));
    
    //         JPanel contentPanel = new JPanel();
    //         contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    //         contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    //         contentPanel.setBackground(Color.WHITE);
    //         contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
    
    //         JPanel studentPanel = createSectionPanel("👤 Informations de l'étudiant",
    //                 createStyledLabel("Nom : " + student.getPrenom() + " " + student.getNom()),
    //                 createStyledLabel("📌 INE : " + student.getIne()));
    
    //         JPanel formationPanel = createSectionPanel("🎓 Formation sélectionnée",
    //                 createStyledLabel("📖 Formation : " + selectedFormation.getLibelle()));
    
    //         JPanel uesPanel = createSectionPanel("📚 Unités d'enseignement",
    //                 createStyledLabel("UEs obligatoires : " + uesObligatoiresText),
    //                 createStyledLabel("📝 UEs optionnelles choisies : " + selectedUesText));
    
    //         JPanel recapPanel = createSectionPanel("ℹ️ Récapitulatif",
    //                 createStyledLabel("Veuillez vérifier les informations avant de valider."));
    
    //         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    //         JButton precedentButton = new JButton("⬅ Précédent");
    //         precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));
    //         JButton validerButton = new JButton("✅ Valider");
            
    //         styleButton(precedentButton, Color.LIGHT_GRAY);
    //         styleButton(validerButton, new Color(46, 204, 113));
            
    //         buttonPanel.add(precedentButton);
    //         buttonPanel.add(validerButton);
    //         buttonPanel.setBackground(Color.WHITE);
            
    //         contentPanel.add(studentPanel);
    //         contentPanel.add(formationPanel);
    //         contentPanel.add(uesPanel);
    //         contentPanel.add(recapPanel);
            
    //         step3Panel.add(contentPanel, BorderLayout.CENTER);
    //         step3Panel.add(buttonPanel, BorderLayout.SOUTH);
            
    //         step3Panel.revalidate();
    //         step3Panel.repaint();
    //     }
    // }
    

    private void updateStep3Panel() {
    Formation selectedFormation = (Formation) formationComboBox.getSelectedItem();
    if (selectedFormation != null) {
        List<UE> uesObligatoires = formationService.getRequiredUEs(selectedFormation.getId());
        String uesObligatoiresText = uesObligatoires.isEmpty() ? "Aucune"
                : uesObligatoires.stream().map(UE::getLibelle).collect(Collectors.joining(", "));

        // Vérifier si la liste `selectedUes` est initialisée
        String selectedUesText = (selectedUes != null && !selectedUes.isEmpty()) 
                ? selectedUes.stream().map(UE::getLibelle).collect(Collectors.joining(", ")) 
                : "Aucune";

        // Récupérer l'étudiant connecté
        Utilisateur user = SessionManager.getUtilisateur();
        Etudiant student = etudiantService.getEtudiantById(user.getId());
        if (student == null) {
            JOptionPane.showMessageDialog(this, "⚠ Étudiant introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        step3Panel.removeAll();
        step3Panel.setLayout(new BorderLayout());
        step3Panel.setBackground(new Color(240, 240, 240));

        // Contenu de la page
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        JPanel studentPanel = createSectionPanel("👤 Informations de l'étudiant",
                createStyledLabel("Nom : " + student.getPrenom() + " " + student.getNom()),
                createStyledLabel("📌 INE : " + student.getIne()));

        JPanel formationPanel = createSectionPanel("🎓 Formation sélectionnée",
                createStyledLabel("📖 Formation : " + selectedFormation.getLibelle()));

        JPanel uesPanel = createSectionPanel("📚 Unités d'enseignement",
                createStyledLabel("UEs obligatoires : " + uesObligatoiresText),
                createStyledLabel("📝 UEs optionnelles choisies : " + selectedUesText));

        JPanel recapPanel = createSectionPanel("ℹ️ Récapitulatif",
                createStyledLabel("Veuillez vérifier les informations avant de valider."));

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton precedentButton = new JButton("⬅ Précédent");
        precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));
        styleButton(precedentButton, Color.LIGHT_GRAY);

        JButton validerButton = new JButton("✅ Valider");
        styleButton(validerButton, new Color(46, 204, 113));

        //logger
        List<Long> ueIdsChoisies = selectedUes.stream().map(UE::getId).toList();
    
        // 🔹 Logguer les UEs sélectionnées
        System.out.println("UEs sélectionnées pour l'inscription : " + ueIdsChoisies);
    
        // 🔹 Afficher les UEs en détail dans la console
        selectedUes.forEach(ue -> System.out.println("UE : " + ue.getCode() + " - " + ue.getLibelle()));

        // Action sur le bouton "Valider"
        validerButton.addActionListener(e -> validerInscription(selectedFormation));

        buttonPanel.add(precedentButton);
        buttonPanel.add(validerButton);

        // Ajouter les sections au panel principal
        contentPanel.add(studentPanel);
        contentPanel.add(formationPanel);
        contentPanel.add(uesPanel);
        contentPanel.add(recapPanel);

        step3Panel.add(contentPanel, BorderLayout.CENTER);
        step3Panel.add(buttonPanel, BorderLayout.SOUTH);

        step3Panel.revalidate();
        step3Panel.repaint();
    }
}

    private JPanel createSectionPanel(String title, JLabel... labels) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(labels.length, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1), title,
                TitledBorder.LEFT, TitledBorder.TOP, BOLD_FONT_18, Color.BLACK));
        panel.setBackground(Color.WHITE);
        for (JLabel label : labels) {
            panel.add(label);
        }
        return panel;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(REGULAR_FONT);
        label.setForeground(Color.DARK_GRAY);
        return label;
    }
    
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(REGULAR_FONT);
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
    }
    
    

    public void afficher(){
        this.setVisible(true);
    }


    private void validerInscription(Formation selectedFormation) {
        if (selectedFormation == null) {
            JOptionPane.showMessageDialog(this, "⚠ Aucune formation sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (selectedUes == null || selectedUes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Aucune UE sélectionnée.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        selectedUes.forEach(ue -> System.out.println("UE : " + ue.getCode() + " - " + ue.getLibelle()));
    
        InscriptionController inscriptionController = new InscriptionController();
        String message = inscriptionController.inscrireEtudiant(selectedFormation.getId(), selectedUes);
    
        JOptionPane.showMessageDialog(this, message, "Inscription", JOptionPane.INFORMATION_MESSAGE);
    
        // Fermer ou rediriger après validation
        if (message.contains("réussie")) {
            navigateToHome();
        }
    }

    private void navigateToHome(){
        HomeStudentUI home = new HomeStudentUI();
        home.afficher();
        this.dispose();
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InscriptionUI inscriptionUI = new InscriptionUI();
            inscriptionUI.setVisible(true);
        });
    }
}