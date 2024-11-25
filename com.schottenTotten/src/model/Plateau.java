package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import java.util.Collections;

/** Classe pour définir un plateau. */
public class Plateau {

    /** Toutes les cartes du plateau non distribuées. */
    private List<Carte> cartes;
    /** Cartes tactiques du plateau. */
    private List<CarteTactic> cartesTactiques;
    /** Nombres de cartes à distribuer à un joueur. */
    private int nbCartes;
    /** Boolean pour variante tactique. */
    private boolean varianteTactique;

    /** Constructeur de la classe Plateau. */
    public Plateau(int nbCartes, boolean varianteTactique) {
        this.nbCartes = nbCartes;
        this.varianteTactique = varianteTactique;
        cartes = new ArrayList<Carte>();
        cartesTactiques = new ArrayList<CarteTactic>();
        initialiserCartes();
        melangerCartes();
    }

    /** Getter de toutes les cartes du plateau.
     * @return Toutes les cartes du plateau. */
    public List<Carte> getCartes() {
        return cartes;
    }

    /** Setter de toutes les cartes du plateau.
     * @param cartes Toutes les cartes du plateau. */
    public void setCartes(List<Carte> cartes) {
        this.cartes = cartes;
    }
    
    /** Initialsier toutes les cartes du plateau. */
    public void initialiserCartes() {
        // Initialiser les cartes de base
        String[] couleurs = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Violet"};
        for (String couleur : couleurs) {
            for (int valeur = 1; valeur <= 9; valeur++) {
                cartes.add(new Carte(couleur, valeur));
            }
        }

        // Initialiser les cartes tactiques
        // 2 cartes tactiques de type "Joker"
        for (int i = 0; i < 2; i++) {
            cartesTactiques.add(new CarteTactic("joker"));
        }
        // Une carte tactique de type "Espion"
        cartesTactiques.add(new CarteTactic("espion"));
        // Une carte tactique de type "Bouclier"
        cartesTactiques.add(new CarteTactic("bouclier"));
        // Une carte tactique de type "Colin"
        cartesTactiques.add(new CarteTactic("colin"));
        // Une carte tactique de type "Combat"
        cartesTactiques.add(new CarteTactic("boue"));
        // Une carte tactique de type "chasseur"
        cartesTactiques.add(new CarteTactic("chasseur"));
        // Une carte tactique de type "stratege"
        cartesTactiques.add(new CarteTactic("stratege"));
        // Une carte tactique de type "banshee"
        cartesTactiques.add(new CarteTactic("banshee"));
        // Une carte tactique de type "traitre"
        cartesTactiques.add(new CarteTactic("traitre"));
    }

    /** Melanger toutes les cartes du plateau. */
    public void melangerCartes() {
        Collections.shuffle(cartes);
        Collections.shuffle(cartesTactiques);
    }

    /** Distribuer une carte.
     * @return La carte distribuée. */
    public Carte distribuerCarte() {
        if (cartes.size() > 0) {
            return cartes.remove(0);
        } else {
            return null;
        }
    }

    /** distribuer une carte tactique à un joueur. */
    public Carte distribuerCarteTactique() {
        if (cartesTactiques.size() > 0) {
            return cartesTactiques.remove(0);
        } else {
            System.out.println("Il n'y a plus de cartes tactiques.");
            // Afficher un jpanel pour dire qu'il n'y a plus de cartes tactiques
            JOptionPane.showMessageDialog(null, "Il n'y a plus de cartes tactiques. Vous allez recevoir une carte normale.");
            return distribuerCarte();
        }
    }

    /** Taille des cartes du plateau.
     * @return La taille des cartes du plateau. */
    public int tailleCartes() {
        return cartes.size();
    }

    /** Distribuer les 6 cartes de départ à un joueur.
     * @param joueur le joueur à qui distribuer les cartes */
    public void distribuerCartesDepart(Joueur joueur) {
        for (int i = 0; i < nbCartes; i++) {
            joueur.getCartes().add(distribuerCarte());
        }
    }

    /** Getter du nombre de cartes à distribuer.
     * @return Le nombre de cartes à distribuer. */
    public int getNbCartes() {
        return nbCartes;
    }

    /** Setter du nombre de cartes à distribuer.
     * @param nbCartes Le nombre de cartes à distribuer. */
    public void setNbCartes(int nbCartes) {
        this.nbCartes = nbCartes;
    }

    /** Getter de la variante tactique.
     * @return La variante tactique. */
    public boolean getVarianteTactique() {
        return varianteTactique;
    }

    /** Setter de la variante tactique.
     * @param varianteTactique La variante tactique. */
    public void setVarianteTactique(boolean varianteTactique) {
        this.varianteTactique = varianteTactique;
    }

    /** Ajouter une carte au selon le type de carte (normal ou tactique).
     * @param carte La carte à ajouter. */
    public void ajouterCarte(Carte carte) {
        if (carte instanceof CarteTactic) {
            cartesTactiques.add((CarteTactic) carte);
        } else {
            cartes.add(carte);
        }
    }
}
