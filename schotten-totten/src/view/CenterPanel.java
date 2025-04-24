package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import model.*;

/** Classe pour définir un panel de slot de carte des deux joueurs et le chemin. */
public class CenterPanel extends JPanel {

    /** Largeur d'une borne. */
    private static final int BORNE_WIDTH = 192;
    /** Hauteur d'une borne. */
    private static final int BORNE_HEIGHT = 108;
    /** Liste des slots du joueur 1. */
    private List<CardSlotPanel> slotsJoueur1;
    /** Liste des slots du joueur 2. */
    private List<CardSlotPanel> slotsJoueur2;
    /** Joueur 1. */
    private Joueur joueur1;
    /** Joueur 2. */
    private Joueur joueur2;
    /** Liste des bornes. */
    private List<Borne> bornes;

    /**
     * Constructeur de la classe CenterPanel.
     * @param joueur1 Joueur 1.
     * @param joueur2 Joueur 2.
     * @param bornes  Liste des bornes.
     */
    public CenterPanel(Joueur joueur1, Joueur joueur2, List<Borne> bornes) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.bornes = bornes;
        this.slotsJoueur1 = new ArrayList<>();
        this.slotsJoueur2 = new ArrayList<>();
        setLayout(new GridLayout(3, 9, 1, 0));

        // Ajouter zones Joueur 1
        for (int i = 0; i < 9; i++) {
            CardSlotPanel slot = new CardSlotPanel(bornes.get(i), 1);
            slotsJoueur1.add(slot);
            slot.updateSlot();
            add(slot);
        }

        // Ajouter bornes
        for (int i = 0; i < 9; i++) {
            add(createBorneLabel("image/bornes/b" + (i + 1) + ".png"));
        }

        // Ajouter zones Joueur 2
        for (int i = 0; i < 9; i++) {
            CardSlotPanel slot = new CardSlotPanel(bornes.get(i), 2);
            slotsJoueur2.add(slot);
            slot.updateSlot();
            add(slot);
        }
    }

    /**
     * Méthode pour créer un label de borne.
     * @param imagePath Chemin de l'image.
     * @return JLabel
     */
    private JLabel createBorneLabel(String imagePath) {
        JLabel label = new JLabel();
        ImageIcon icon = loadAndResizeImage(imagePath, BORNE_WIDTH, BORNE_HEIGHT);
        if (icon != null) {
            label.setIcon(icon);
        } else {
            label.setText("Borne");
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return label;
    }

    /**
     * Méthode pour charger et redimensionner une image.
     * @param imagePath Chemin de l'image.
     * @param width     Largeur de l'image.
     * @param height    Hauteur de l'image.
     * @return ImageIcon
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

    /** Mise à jour de tous les slots. */
    public void updateSlots() {
        for (Component component : getComponents()) {
            if (component instanceof CardSlotPanel) {
                CardSlotPanel slot = (CardSlotPanel) component;
                slot.updateSlot();
            }
        }
    }

    /** Getter de slotsJoueur1.
     * @return List<CardSlotPanel> */
    public List<CardSlotPanel> getSlotsJoueur1() {
        return slotsJoueur1;
    }

    /** Getter de slotsJoueur2.
     * @return List<CardSlotPanel> */
    public List<CardSlotPanel> getSlotsJoueur2() {
        return slotsJoueur2;
    }

    /** Getter de joueur1.
     * @return Joueur */
    public Joueur getJoueur1() {
        return joueur1;
    }

    /** Getter de joueur2.
     * @return Joueur */
    public Joueur getJoueur2() {
        return joueur2;
    }

    /** Getter de bornes.
     * @return List<Borne> */
    public List<Borne> getBornes() {
        return bornes;
    }

    /** Setter de joueur1.
     * @param joueur1 Joueur */
    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    /** Setter de joueur2.
     * @param joueur2 Joueur */
    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }

    /** Setter de bornes.
     * @param bornes List<Borne> */
    public void setBornes(List<Borne> bornes) {
        this.bornes = bornes;
    }

    /** Setter de slotsJoueur1.
     * @param slotsJoueur1 List<CardSlotPanel> */
    public void setSlotsJoueur1(List<CardSlotPanel> slotsJoueur1) {
        this.slotsJoueur1 = slotsJoueur1;
    }

    /** Setter de slotsJoueur2.
     * @param slotsJoueur2 List<CardSlotPanel> */
    public void setSlotsJoueur2(List<CardSlotPanel> slotsJoueur2) {
        this.slotsJoueur2 = slotsJoueur2;
    }
}