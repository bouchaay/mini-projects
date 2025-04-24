package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import model.*;

public class HandPanel extends JPanel {
    private static final int CARD_WIDTH = 143;
    private static final int CARD_HEIGHT = 200;
    private Joueur joueur;
 

    public HandPanel(String title, boolean isPlayerOne, Joueur joueur) {
        this.joueur = joueur;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBorder(BorderFactory.createTitledBorder(title));

        // Afficher les cartes du joueur
        afficherCartes();
    }

    // Méthode pour afficher les cartes du joueur
    public void afficherCartes() {
        removeAll();  // Effacer les cartes existantes avant d'ajouter les nouvelles
        List<Carte> cartes = joueur.getCartes();  // Récupérer les cartes du joueur

        String imagePath = "";
        // Ajouter une icône pour chaque carte
        for (int i = 0; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            // Si la carte n'est pas une carte tactique, afficher l'image de la carte
            if (carte instanceof CarteTactic) {
                CarteTactic carteTactic = (CarteTactic) carte;
                imagePath = carteTactic.getCheminImage();
            } else {
                imagePath = "image/cartes/" + cartes.get(i).getCouleur().substring(0, 2) + cartes.get(i).getValeur() + ".png";
            }
            JLabel cardLabel = createCardLabel(imagePath);
            add(cardLabel);
        }

        revalidate();
        repaint();
    }

    private JLabel createCardLabel(String imagePath) {
        JLabel label = new JLabel();
        ImageIcon icon = loadAndResizeImage(imagePath, CARD_WIDTH, CARD_HEIGHT);
        if (icon != null) {
            label.setIcon(icon);
        } else {
            label.setText("Carte");
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return label;
    }

    private ImageIcon loadAndResizeImage(String imagePath, int width, int height) {
        try {
            Image img = ImageIO.read(new File(imagePath));
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace(); // Afficher l'erreur s'il y a un problème avec l'image
            return null;
        }
    }

    // Méthode pour mettre à jour la main du joueur (exécutée lors du changement de cartes)
    public void miseAJourCartes(Joueur joueur) {
        this.joueur = joueur;
        afficherCartes();  // Redessiner les cartes du joueur
    }
}
