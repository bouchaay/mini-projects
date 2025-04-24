package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.*;

/** Classe pour définir un panel de slot de carte. */
public class CardSlotPanel extends JPanel {

    /** Largeur d'une carte. */
    private static final int CARD_WIDTH = 200;
    /** Hauteur d'une carte. */
    private static final int CARD_HEIGHT = 200;
    /** Hauteur visible entre les cartes. */
    private static final int VISIBLE_HEIGHT = CARD_HEIGHT / 4; // Décalage visible entre les cartes
    /** Nombre maximum de cartes affichées. */
    private static final int MAX_CARDS = 4;
    /** Bornes du jeu. */
    private Borne borne;
    /** Id du joueur. */
    private int joueurId;
    /** Liste des cartes affichées. */
    private final List<JLabel> cards; // Stocke les cartes affichées

    /**
     * Constructeur de la classe CardSlotPanel.
     * @param borne    Borne du jeu.
     * @param joueurId Id du joueur.
     */
    public CardSlotPanel(Borne borne, int joueurId) {
        this.cards = new ArrayList<>();
        this.borne = borne;
        this.joueurId = joueurId;
        setLayout(null); // Permet un positionnement absolu
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    /**
     * Méthode pour ajouter une carte au slot.
     * @param cardIcon Image de la carte.
     */
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

    /**
     * Méthode pour mettre à jour le slot.
     */
    public void updateSlot() {
        removeAll();
        cards.clear();
        String imagePath = "";
        if (joueurId == 1) {
            for (Carte carte : borne.getCartesJoueur1()) {
                // Si la carte n'est pas une carte tactique, afficher l'image de la carte
                if (carte instanceof CarteTactic) {
                    CarteTactic carteTactic = (CarteTactic) carte;
                    imagePath = carteTactic.getCheminImage();
                } else {
                    imagePath = "image/cartes/" + carte.getCouleur().substring(0, 2) + carte.getValeur() + ".png";
                }
                ImageIcon cardIcon = loadAndResizeImage(imagePath, CARD_WIDTH, CARD_HEIGHT);
                addCard(cardIcon);
            }
        } else if (joueurId == 2) {
            for (Carte carte : borne.getCartesJoueur2()) {
                // Si la carte n'est pas une carte tactique, afficher l'image de la carte
                if (carte instanceof CarteTactic) {
                    CarteTactic carteTactic = (CarteTactic) carte;
                    imagePath = carteTactic.getCheminImage();
                } else {
                    imagePath = "image/cartes/" + carte.getCouleur().substring(0, 2) + carte.getValeur() + ".png";
                }
                ImageIcon cardIcon = loadAndResizeImage(imagePath, CARD_WIDTH, CARD_HEIGHT);
                addCard(cardIcon);
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Méthode pour effacer les cartes.
     */
    public void clearCards() {
        cards.clear();
        removeAll();
        revalidate();
        repaint();
    }

    /**
     * Méthode pour charger et redimensionner une image.
     * @param imagePath Chemin de l'image.
     * @param width     Largeur de l'image.
     * @param height    Hauteur de l'image.
     * @return ImageIcon de l'image chargée.
     */
    private ImageIcon loadAndResizeImage(String imagePath, int width, int height) {
        try {
            Image img = ImageIO.read(new File(imagePath));
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Méthode pour mettre en surbrillance le slot.
     */
    public void highlight() {
        setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }
}
