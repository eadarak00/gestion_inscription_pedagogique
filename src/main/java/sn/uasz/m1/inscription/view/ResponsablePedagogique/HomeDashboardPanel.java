package sn.uasz.m1.inscription.view.ResponsablePedagogique;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import sn.uasz.m1.inscription.model.Formation;
import sn.uasz.m1.inscription.service.EtudiantService;
import sn.uasz.m1.inscription.service.FormationService;
import sn.uasz.m1.inscription.service.InscriptionService;
import sn.uasz.m1.inscription.service.UEService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HomeDashboardPanel extends JPanel {
    private FormationService formationService;
    private EtudiantService etudiantService;
    private UEService ueService;
    private InscriptionService inscriptionService;

    // Couleurs du thème
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color ACCENT_COLOR = new Color(142, 68, 173);
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private final Color WARNING_COLOR = new Color(241, 196, 15);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color LIGHT_BG = new Color(250, 250, 255);

    public HomeDashboardPanel() {
        this.formationService = new FormationService();
        this.etudiantService = new EtudiantService();
        this.ueService =  new UEService();
        this.inscriptionService = new InscriptionService();

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(LIGHT_BG);

        // Panel principal avec effet de dégradé
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int w = getWidth(), h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(245, 247, 250),
                        w, h, new Color(232, 236, 241));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Panel pour le contenu principal avec scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Titre de la page
        JPanel headerPanel = createHeaderPanel("Tableau de Bord Responsable");
        contentPanel.add(headerPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Cartes de statistiques
        JPanel statsCardsPanel = createStatsCardsPanel();
        contentPanel.add(statsCardsPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Graphiques
        JPanel chartsPanel = createChartsPanel();
        contentPanel.add(chartsPanel);

        // Scroll pane pour le contenu
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Ligne décorative
        JPanel linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR,
                        getWidth(), 0, ACCENT_COLOR);
                g2d.setPaint(gp);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(0, 0, getWidth() / 2, 0);
            }
        };
        linePanel.setOpaque(false);
        linePanel.setMaximumSize(new Dimension(600, 3));
        linePanel.setPreferredSize(new Dimension(200, 3));
        linePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Date et heure
        JLabel dateLabel = new JLabel("Aujourd'hui: " + java.time.LocalDate.now().toString());
        dateLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        dateLabel.setForeground(new Color(127, 140, 141));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(linePanel);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        panel.add(dateLabel);

        return panel;
    }

    private JPanel createStatsCardsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 20, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

        // Récupération des statistiques
        int nbFormations = formationService.getFormationsByResponsable().size();
        int nbEtudiants = etudiantService.getAllEtudiants().size();
        int nbUEs = ueService.getUEsByResponsable().size();
        int nbInscriptions = inscriptionService.getInscriptionsByResponsable().size();

        // Création des cartes de statistiques
        panel.add(createStatCard("Formations", nbFormations, PRIMARY_COLOR, "📚"));
        panel.add(createStatCard("Étudiants", nbEtudiants, SUCCESS_COLOR, "👨‍🎓"));
        panel.add(createStatCard("UEs", nbUEs, WARNING_COLOR, "📝"));
        panel.add(createStatCard("Inscriptions", nbInscriptions, DANGER_COLOR, "✅"));

        return panel;
    }

    private JPanel createStatCard(String label, int value, Color color, String icon) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fond blanc légèrement transparent
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Bordure avec couleur thématique
                g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 80));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                // Barre de couleur sur le dessus
                g2d.setColor(color);
                g2d.fillRoundRect(0, 0, getWidth(), 5, 5, 5);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);

        // Icône
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        iconLabel.setForeground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 200));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 0));

        // Valeur
        JLabel valueLabel = new JLabel(String.valueOf(value));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(new Color(44, 62, 80));
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 15));

        // Titre
        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(new Color(127, 140, 141));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 0));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(iconLabel, BorderLayout.WEST);
        topPanel.add(valueLabel, BorderLayout.EAST);

        card.add(topPanel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createChartsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));

        // Création des graphiques
        panel.add(createPieChartPanel());
        panel.add(createBarChartPanel());

        return panel;
    }

    private JPanel createPieChartPanel() {
        JPanel panel = createGlassPanel("Répartition des Étudiants par Formation");

        // Données pour le graphique camembert
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<Formation> formations = formationService.getAllFormations();

        for (Formation formation : formations) {
            int nbEtudiants = formationService.getEtudiantsByFormation(formation.getId()).size();
            if (nbEtudiants > 0) {
                dataset.setValue(formation.getLibelle(), nbEtudiants);
            }
        }

        // Création du graphique
        JFreeChart chart = ChartFactory.createPieChart(
                null, // Titre (déjà dans le panel)
                dataset,
                true, // Légende
                true, // Tooltips
                false // URLs
        );

        // Personnalisation du graphique
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setBackgroundPaint(null);
        plot.setOutlinePaint(null);
        plot.setShadowPaint(null);
        plot.setLabelGenerator(null); // Désactiver les labels sur les sections
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0} ({1})"));

        // Couleurs des sections
        plot.setSectionPaint(0, PRIMARY_COLOR);
        plot.setSectionPaint(1, SECONDARY_COLOR);
        plot.setSectionPaint(2, ACCENT_COLOR);
        plot.setSectionPaint(3, SUCCESS_COLOR);
        plot.setSectionPaint(4, WARNING_COLOR);

        // Légende
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.BOTTOM);
        legend.setItemFont(new Font("Segoe UI", Font.PLAIN, 12));
        legend.setBackgroundPaint(null);
        legend.setBorder(0, 0, 0, 0);

        // Affichage du graphique dans un ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false);
        chartPanel.setBackground(new Color(0, 0, 0, 0));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBarChartPanel() {
        JPanel panel = createGlassPanel("Nombre d'Inscriptions par Formation");

        // Données pour le graphique en barres
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<Formation> formations = formationService.getAllFormations();

        for (Formation formation : formations) {
            int nbInscriptions = inscriptionService.countInscriptionsByFormation(formation.getId());
            dataset.addValue(nbInscriptions, "Inscriptions", formation.getLibelle());
        }

        // Création du graphique
        JFreeChart chart = ChartFactory.createBarChart(
                null, // Titre (déjà dans le panel)
                "Formations", // Axe X
                "Nombre d'Inscriptions", // Axe Y
                dataset,
                PlotOrientation.VERTICAL,
                true, // Légende
                true, // Tooltips
                false // URLs
        );

        // Personnalisation du graphique
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(null);
        plot.setOutlinePaint(null);
        plot.setRangeGridlinePaint(new Color(200, 200, 200, 100));
        plot.setDomainGridlinePaint(new Color(200, 200, 200, 100));

        // Barres
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, PRIMARY_COLOR);
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);
        renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setDefaultItemLabelsVisible(true);
        renderer.setDefaultItemLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        renderer.setDefaultItemLabelPaint(new Color(44, 62, 80));

        // Axes
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        plot.getDomainAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.getDomainAxis().setTickLabelPaint(new Color(127, 140, 141));
        plot.getRangeAxis().setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 12));
        plot.getRangeAxis().setTickLabelPaint(new Color(127, 140, 141));

        // Légende
        LegendTitle legend = chart.getLegend();
        legend.setPosition(RectangleEdge.BOTTOM);
        legend.setItemFont(new Font("Segoe UI", Font.PLAIN, 12));
        legend.setBackgroundPaint(null);
        legend.setBorder(0, 0, 0, 0);

        // Affichage du graphique dans un ChartPanel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setOpaque(false);
        chartPanel.setBackground(new Color(0, 0, 0, 0));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(chartPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createGlassPanel(String title) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fond blanc légèrement transparent
                g2d.setColor(new Color(255, 255, 255, 240));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Bordure avec couleur thématique
                g2d.setColor(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 80));
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titre du panel
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        panel.add(titleLabel, BorderLayout.NORTH);
        return panel;
    }
}