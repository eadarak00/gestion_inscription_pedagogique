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
import sn.uasz.m1.inscription.view.components.NavbarStudent;
import sn.uasz.m1.inscription.view.components.StepIndicator;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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

        NavbarStudent navbar = new NavbarStudent(this);
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

    private JPanel createStep1Panel() {
        JPanel step1Panel = new JPanel(new GridBagLayout());
        step1Panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Espacement plus large pour une meilleure lisibilité

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
        suivantButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change le curseur pour indiquer une
                                                                                 // action

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

    private JPanel createStep2Panel() {
        JPanel step2Panel = new JPanel(new GridBagLayout());
        step2Panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // 🔹 Ajouter l'indicateur d'étape 2
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        step2Panel.add(new StepIndicator(2), gbc);

        // Ajouter un label pour les UEs
        JLabel ueLabel = new JLabel("Choisir les UEs optionnelles");
        ueLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        ueLabel.setForeground(new Color(0x113F36));
        gbc.gridy = 1;
        step2Panel.add(ueLabel, gbc);

        // Panel pour les checkboxes
        ueCheckboxPanel = new JPanel();
        ueCheckboxPanel.setLayout(new BoxLayout(ueCheckboxPanel, BoxLayout.Y_AXIS));
        ueCheckboxPanel.setBackground(BG_COLOR);

        for (int i = 1; i <= 5; i++) { // Juste un exemple, mettez vos véritables options
            JCheckBox checkBox = new JCheckBox("UE Option " + i);
            checkBox.setFont(new Font("Poppins", Font.PLAIN, 18));
            checkBox.setBackground(BG_COLOR);
            checkBox.setForeground(new Color(0x113F36));
            checkBox.setFocusPainted(false); // Enlever la bordure de focus
            // checkBox.setBorder(BorderFactory.createLineBorder(new Color(0xAAAAAA), 1));
            // // Bordure subtile
            checkBox.setMargin(new Insets(10, 10, 10, 10)); // Espacement interne

            ueCheckboxPanel.add(checkBox);
        }

        gbc.gridy = 2;
        JScrollPane scrollPane = new JScrollPane(ueCheckboxPanel);
        // scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x128E64), 2));
        // // Bordure verte
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
            JOptionPane.showMessageDialog(step3Panel, "Erreur : Utilisateur non connecté.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return step3Panel;
        }

        Etudiant etudiant = etudiantService.getEtudiantById(user.getId());
        if (etudiant == null) {
            JOptionPane.showMessageDialog(step3Panel, "Erreur : Étudiant introuvable.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
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
            JOptionPane.showMessageDialog(step3Panel, "Erreur : Aucune formation sélectionnée.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
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
     * 
     * @param maxSelectableUes Le nombre maximum d'UEs que l'utilisateur peut
     *                         sélectionner
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

        // Si le nombre d'UEs sélectionnées est supérieur à la limite, désactiver les
        // autres
        if (selectedCount > maxSelectableUes) {
            JOptionPane.showMessageDialog(null, "Vous ne pouvez pas sélectionner plus de " + maxSelectableUes + " UEs.",
                    "Limite atteinte", JOptionPane.WARNING_MESSAGE);
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
            // Réactiver toutes les checkboxes si le nombre sélectionné est en-dessous du
            // maximum
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

    //         // Vérifier si la liste `selectedUes` est initialisée
    //         String selectedUesText = (selectedUes != null && !selectedUes.isEmpty())
    //                 ? selectedUes.stream().map(UE::getLibelle).collect(Collectors.joining(", "))
    //                 : "Aucune";

    //         // Récupérer l'étudiant connecté
    //         Utilisateur user = SessionManager.getUtilisateur();
    //         Etudiant student = etudiantService.getEtudiantById(user.getId());
    //         if (student == null) {
    //             JOptionPane.showMessageDialog(this, "⚠ Étudiant introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
    //             return;
    //         }

    //         step3Panel.removeAll();
    //         step3Panel.setLayout(new BorderLayout());
    //         step3Panel.setBackground(new Color(240, 240, 240));

    //         // Contenu de la page
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

    //         // Panneau des boutons
    //         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    //         buttonPanel.setBackground(Color.WHITE);

    //         JButton precedentButton = new JButton("⬅ Précédent");
    //         precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));
    //         styleButton(precedentButton, Color.LIGHT_GRAY);

    //         JButton validerButton = new JButton("✅ Valider");
    //         styleButton(validerButton, new Color(46, 204, 113));

    //         // logger
    //         List<Long> ueIdsChoisies = selectedUes.stream().map(UE::getId).toList();

    //         // 🔹 Logguer les UEs sélectionnées
    //         System.out.println("UEs sélectionnées pour l'inscription : " + ueIdsChoisies);

    //         // 🔹 Afficher les UEs en détail dans la console
    //         selectedUes.forEach(ue -> System.out.println("UE : " + ue.getCode() + " - " + ue.getLibelle()));

    //         // Action sur le bouton "Valider"
    //         validerButton.addActionListener(e -> validerInscription(selectedFormation));

    //         buttonPanel.add(precedentButton);
    //         buttonPanel.add(validerButton);

    //         // Ajouter les sections au panel principal
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
                    : uesObligatoires.stream().map(UE::getLibelle).collect(Collectors.joining("\n• "));
            
            if (!uesObligatoires.isEmpty()) {
                uesObligatoiresText = "• " + uesObligatoiresText;
            }
    
            // Vérifier si la liste `selectedUes` est initialisée
            String selectedUesText = "Aucune";
            if (selectedUes != null && !selectedUes.isEmpty()) {
                selectedUesText = selectedUes.stream().map(UE::getLibelle).collect(Collectors.joining("\n• "));
                selectedUesText = "• " + selectedUesText;
            }
    
            // Récupérer l'étudiant connecté
            Utilisateur user = SessionManager.getUtilisateur();
            Etudiant student = etudiantService.getEtudiantById(user.getId());
            if (student == null) {
                JOptionPane.showMessageDialog(this, "⚠ Étudiant introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            step3Panel.removeAll();
            step3Panel.setLayout(new BorderLayout(10, 10));
            step3Panel.setBackground(new Color(245, 245, 250));
            step3Panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
            // Panel principal avec un scroll
            JPanel mainPanel = new JPanel(new BorderLayout());
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    
            // Contenu de la page
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
            contentPanel.setBackground(Color.WHITE);
            
            // Titre principal
            JLabel titleLabel = new JLabel("Confirmation d'inscription");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel.setForeground(new Color(44, 62, 80));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
            
            // Création des panels d'information avec des marges entre eux
            JPanel studentPanel = createInfoPanel("👤 Informations de l'étudiant", 
                    Map.of("Nom", student.getPrenom() + " " + student.getNom(),
                          "INE", student.getIne()));
            
            JPanel formationPanel = createInfoPanel("🎓 Formation sélectionnée", 
                    Map.of("Formation", selectedFormation.getLibelle()));
            
            // Panel pour les UEs avec un affichage amélioré
            JPanel uesPanel = new JPanel();
            uesPanel.setLayout(new BoxLayout(uesPanel, BoxLayout.Y_AXIS));
            uesPanel.setBackground(new Color(250, 250, 255));
            uesPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            uesPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, uesPanel.getMaximumSize().height));
            
            JLabel uesTitleLabel = new JLabel("📚 Unités d'enseignement");
            uesTitleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            uesTitleLabel.setForeground(new Color(44, 62, 80));
            uesTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            // UEs obligatoires avec style
            JPanel obligatoiresPanel = new JPanel();
            obligatoiresPanel.setLayout(new BorderLayout());
            obligatoiresPanel.setBackground(uesPanel.getBackground());
            obligatoiresPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            
            JLabel obligatoiresTitleLabel = new JLabel("UEs obligatoires:");
            obligatoiresTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            obligatoiresTitleLabel.setForeground(new Color(52, 73, 94));
            
            JTextArea obligatoiresArea = new JTextArea(uesObligatoiresText);
            obligatoiresArea.setEditable(false);
            obligatoiresArea.setBackground(uesPanel.getBackground());
            obligatoiresArea.setFont(new Font("Arial", Font.PLAIN, 14));
            obligatoiresArea.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
            obligatoiresArea.setLineWrap(true);
            obligatoiresArea.setWrapStyleWord(true);
            
            obligatoiresPanel.add(obligatoiresTitleLabel, BorderLayout.NORTH);
            obligatoiresPanel.add(obligatoiresArea, BorderLayout.CENTER);
            
            // UEs optionnelles avec style
            JPanel optionnellesPanel = new JPanel();
            optionnellesPanel.setLayout(new BorderLayout());
            optionnellesPanel.setBackground(uesPanel.getBackground());
            optionnellesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
            
            JLabel optionnellesTitleLabel = new JLabel("📝 UEs optionnelles choisies:");
            optionnellesTitleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            optionnellesTitleLabel.setForeground(new Color(52, 73, 94));
            
            JTextArea optionnellesArea = new JTextArea(selectedUesText);
            optionnellesArea.setEditable(false);
            optionnellesArea.setBackground(uesPanel.getBackground());
            optionnellesArea.setFont(new Font("Arial", Font.PLAIN, 14));
            optionnellesArea.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 0));
            optionnellesArea.setLineWrap(true);
            optionnellesArea.setWrapStyleWord(true);
            
            optionnellesPanel.add(optionnellesTitleLabel, BorderLayout.NORTH);
            optionnellesPanel.add(optionnellesArea, BorderLayout.CENTER);
            
            uesPanel.add(uesTitleLabel);
            uesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            uesPanel.add(obligatoiresPanel);
            uesPanel.add(optionnellesPanel);
            
            // Panel récapitulatif avec une note importante
            JPanel recapPanel = new JPanel();
            recapPanel.setLayout(new BorderLayout());
            recapPanel.setBackground(new Color(253, 245, 230));
            recapPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 180, 140), 1),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)));
            recapPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, recapPanel.getMaximumSize().height));
            
            JLabel recapTitle = new JLabel("ℹ️ Récapitulatif");
            recapTitle.setFont(new Font("Arial", Font.BOLD, 16));
            recapTitle.setForeground(new Color(139, 69, 19));
            
            JTextArea recapText = new JTextArea("Veuillez vérifier attentivement les informations ci-dessus avant de valider votre inscription. " +
                    "Une fois validée, l'inscription sera définitive pour le semestre en cours.");
            recapText.setEditable(false);
            recapText.setBackground(recapPanel.getBackground());
            recapText.setFont(new Font("Arial", Font.PLAIN, 14));
            recapText.setLineWrap(true);
            recapText.setWrapStyleWord(true);
            recapText.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            
            recapPanel.add(recapTitle, BorderLayout.NORTH);
            recapPanel.add(recapText, BorderLayout.CENTER);
            
            // Ajouter tous les composants au panel de contenu
            contentPanel.add(titleLabel);
            contentPanel.add(studentPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPanel.add(formationPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPanel.add(uesPanel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            contentPanel.add(recapPanel);
            
            // Panneau des boutons avec un style amélioré
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
            buttonPanel.setBackground(new Color(245, 245, 250));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
            
            JButton precedentButton = createStyledButton("⬅ Précédent", new Color(108, 117, 125));
            precedentButton.addActionListener(e -> cardLayout.show(cardPanel, "Step2"));
            
            JButton validerButton = createStyledButton("✅ Valider l'inscription", new Color(46, 204, 113));
            
            // Logger les UEs sélectionnées
            List<Long> ueIdsChoisies = selectedUes != null ? 
                    selectedUes.stream().map(UE::getId).toList() : 
                    Collections.emptyList();
            
            System.out.println("UEs sélectionnées pour l'inscription : " + ueIdsChoisies);
            
            if (selectedUes != null) {
                selectedUes.forEach(ue -> System.out.println("UE : " + ue.getCode() + " - " + ue.getLibelle()));
            }
            
            // Action sur le bouton "Valider"
            validerButton.addActionListener(e -> validerInscription(selectedFormation));
            
            buttonPanel.add(precedentButton);
            buttonPanel.add(validerButton);
            
            // Assemblage final
            // scrollPane.setViewportView(contentPanel);
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            
            step3Panel.add(mainPanel, BorderLayout.CENTER);
            step3Panel.add(buttonPanel, BorderLayout.SOUTH);
            
            step3Panel.revalidate();
            step3Panel.repaint();
        }
    }
    
    // Méthode auxiliaire pour créer un panel d'information
    private JPanel createInfoPanel(String title, Map<String, String> fields) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(250, 250, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getMaximumSize().height));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
            fieldPanel.setBackground(panel.getBackground());
            fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel keyLabel = new JLabel(entry.getKey() + " : ");
            keyLabel.setFont(new Font("Arial", Font.BOLD, 14));
            keyLabel.setForeground(new Color(52, 73, 94));
            
            JLabel valueLabel = new JLabel(entry.getValue());
            valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            
            fieldPanel.add(keyLabel);
            fieldPanel.add(valueLabel);
            panel.add(fieldPanel);
        }
        
        return panel;
    }
    
    // Méthode pour créer un bouton stylisé
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Appliquer un effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(
                    Math.max((int)(backgroundColor.getRed() * 0.9), 0),
                    Math.max((int)(backgroundColor.getGreen() * 0.9), 0),
                    Math.max((int)(backgroundColor.getBlue() * 0.9), 0)
                ));
            }
    
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });
        
        return button;
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

    public void afficher() {
        this.setVisible(true);
    }

    private void validerInscription(Formation selectedFormation) {
        if (selectedFormation == null) {
            JOptionPane.showMessageDialog(this, "⚠ Aucune formation sélectionnée.", "Erreur",
                    JOptionPane.ERROR_MESSAGE);
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

    private void navigateToHome() {
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