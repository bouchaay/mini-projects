package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class CardSlotPanel extends JPanel {
    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 200;
    private static final int VISIBLE_HEIGHT = CARD_HEIGHT / 4; // Décalage visible entre les cartes
    private static final int MAX_CARDS = 3;
    private Borne borne;
    private int joueurId;
    

    private final List<JLabel> cards; // Stocke les cartes affichées

    public CardSlotPanel(Borne borne, int joueurId) {
        this.cards = new ArrayList<>();
        this.borne = borne;
        this.joueurId = joueurId;
        setLayout(null); // Permet un positionnement absolu
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // Méthode pour ajouter une carte au slot
    public void addCard(ImageIcon cardIcon) {
        if (cards.size() < MAX_CARDS) {
            JLabel cardLabel = new JLabel(cardIcon);
            int yPosition = cards.size() * VISIBLE_HEIGHT; // Décalage vertical pour empilement
            cardLabel.setBounds(0, yPosition, CARD_WIDTH, CARD_HEIGHT); // Position absolue

            cards.add(0, cardLabel); // Ajouter la carte en début de la liste (dernier en avant)
            add(cardLabel, 0); // Ajouter la carte à la position 0 pour qu'elle soit dessinée en premier

            revalidate();
            repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Le slot est plein (maximum " + MAX_CARDS + " cartes) !");
        }
    }

    public void updateSlot() {
        removeAll();
        cards.clear();
        if (joueurId == 1) {
            for (Carte carte : borne.getCartesJoueur1()) {
                String imagePath = "image/cartes/" + carte.getCouleur().substring(0, 2) + carte.getValeur() + ".png";
                ImageIcon cardIcon = loadAndResizeImage(imagePath, CARD_WIDTH, CARD_HEIGHT);
                addCard(cardIcon);
            }
        } else if (joueurId == 2) {
            for (Carte carte : borne.getCartesJoueur2()) {
                String imagePath = "image/cartes/" + carte.getCouleur().substring(0, 2) + carte.getValeur() + ".png";
                ImageIcon cardIcon = loadAndResizeImage(imagePath, CARD_WIDTH, CARD_HEIGHT);
                addCard(cardIcon);
            }
        }
        revalidate();
        repaint();
    }

    // Méthode pour réinitialiser le slot
    public void clearCards() {
        cards.clear();
        removeAll();
        revalidate();
        repaint();
    }

    private ImageIcon loadAndResizeImage(String imagePath, int width, int height) {
        try {
            Image img = ImageIO.read(new File(imagePath));
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException e) {
            return null;
        }
    }
}
