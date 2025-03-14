package sn.uasz.m1.inscription.view.components;
import javax.swing.*;
import java.awt.*;

public class BadgePanel extends JPanel {
    private String text;
    
    public BadgePanel(String text) {
        this.text = text;
        setPreferredSize(new Dimension(20, 20)); // Taille du badge
        setBackground(Color.RED);
        setForeground(Color.WHITE);
        setFont(new Font("Poppins", Font.BOLD, 12));
        setOpaque(false); // Important pour la transparence
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dessiner le cercle rouge du badge
        g2.setColor(getBackground());
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Dessiner le texte au centre
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(text)) / 2;
        int textY = (getHeight() + fm.getAscent()) / 2 - 2;
        g2.drawString(text, textX, textY);

        g2.dispose();
    }

    public void setText(String text) {
        this.text = text;
        repaint();
    }
}
