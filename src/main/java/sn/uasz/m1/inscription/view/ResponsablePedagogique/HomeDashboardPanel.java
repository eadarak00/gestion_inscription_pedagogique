// package sn.uasz.m1.inscription.view.ResponsablePedagogique;

// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartPanel;
// import org.jfree.chart.JFreeChart;
// import org.jfree.chart.plot.PiePlot;
// import org.jfree.chart.plot.CategoryPlot;
// import org.jfree.data.general.DefaultPieDataset;
// import org.jfree.data.category.DefaultCategoryDataset;
// import javax.swing.*;
// import java.awt.*;

// public class HomeDashboardPanel extends JPanel {

//     public HomeDashboardPanel() {
//         setLayout(new GridLayout(2, 2, 20, 20)); // 2 lignes, 2 colonnes, espacement de 20px
//         setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marge extérieure
//         setBackground(new Color(240, 240, 240)); // Couleur de fond du panel

//         // Ajouter des cartes de statistiques
//         add(createStatCard("📊 Nombre d'étudiants", "1,234", new Color(52, 152, 219)));
//         add(createStatCard("🎓 Formations actives", "45", new Color(46, 204, 113)));

//         // Ajouter des graphiques
//         add(createPieChart());
//         add(createBarChart());
//     }

//     /**
//      * Crée une carte de statistique avec un titre, une valeur et une couleur.
//      */
//     private JPanel createStatCard(String title, String value, Color color) {
//         JPanel card = new JPanel();
//         card.setLayout(new BorderLayout());
//         card.setBackground(color);
//         card.setBorder(BorderFactory.createCompoundBorder(
//                 BorderFactory.createLineBorder(color.darker(), 1),
//                 BorderFactory.createEmptyBorder(20, 20, 20, 20)
//         ));
//         card.setCursor(new Cursor(Cursor.HAND_CURSOR));

//         // Titre de la carte
//         JLabel titleLabel = new JLabel(title);
//         titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
//         titleLabel.setForeground(Color.WHITE);
//         titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//         card.add(titleLabel, BorderLayout.NORTH);

//         // Valeur de la carte
//         JLabel valueLabel = new JLabel(value);
//         valueLabel.setFont(new Font("Arial", Font.BOLD, 36));
//         valueLabel.setForeground(Color.WHITE);
//         valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
//         card.add(valueLabel, BorderLayout.CENTER);

//         return card;
//     }

//     /**
//      * Crée un graphique camembert (Pie Chart).
//      */
//     private JPanel createPieChart() {
//         // Données pour le camembert
//         DefaultPieDataset dataset = new DefaultPieDataset();
//         dataset.setValue("UEs Obligatoires", 60);
//         dataset.setValue("UEs Optionnelles", 40);

//         // Créer le graphique
//         JFreeChart pieChart = ChartFactory.createPieChart(
//                 "Répartition des UEs", // Titre du graphique
//                 dataset, // Données
//                 true, // Légende
//                 true, // Infobulles
//                 false
//         );

//         // Personnaliser le graphique
//         PiePlot plot = (PiePlot) pieChart.getPlot();
//         plot.setSectionPaint("UEs Obligatoires", new Color(46, 204, 113));
//         plot.setSectionPaint("UEs Optionnelles", new Color(52, 152, 219));

//         // Intégrer le graphique dans un JPanel
//         ChartPanel chartPanel = new ChartPanel(pieChart);
//         chartPanel.setPreferredSize(new Dimension(400, 300));
//         return chartPanel;
//     }

//     /**
//      * Crée un histogramme (Bar Chart).
//      */
//     private JPanel createBarChart() {
//         // Données pour l'histogramme
//         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//         dataset.addValue(120, "UEs", "Disponibles");
//         dataset.addValue(80, "UEs", "Validées");
//         dataset.addValue(40, "UEs", "En attente");

//         // Créer le graphique
//         JFreeChart barChart = ChartFactory.createBarChart(
//                 "Statistiques des UEs", // Titre du graphique
//                 "Catégorie", // Axe X
//                 "Nombre", // Axe Y
//                 dataset // Données
//         );

//         // Personnaliser le graphique
//         CategoryPlot plot = barChart.getCategoryPlot();
//         plot.getRenderer().setSeriesPaint(0, new Color(155, 89, 182));

//         // Intégrer le graphique dans un JPanel
//         ChartPanel chartPanel = new ChartPanel(barChart);
//         chartPanel.setPreferredSize(new Dimension(400, 300));
//         return chartPanel;
//     }

//     public static void main(String[] args) {
//         JFrame frame = new JFrame("Tableau de bord Administrateur");
//         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         frame.setSize(1000, 800);
//         frame.setLocationRelativeTo(null);

//         AdminDashboardPanel dashboardPanel = new AdminDashboardPanel();
//         frame.add(dashboardPanel);

//         frame.setVisible(true);
//     }
// }