package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/** Classe pour définir un plateau. */
public class Plateau {

    /** Toutes les cartes du plateau non distribuées. */
    private List<Carte> cartes;

    /** Constructeur de la classe Plateau. */
    public Plateau() {
        cartes = new ArrayList<Carte>();
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
        String[] couleurs = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Violet"};
        for (String couleur : couleurs) {
            for (int valeur = 1; valeur <= 9; valeur++) {
                cartes.add(new Carte(couleur, valeur));
            }
        }
    }

    /** Melanger toutes les cartes du plateau. */
    public void melangerCartes() {
        Collections.shuffle(cartes);
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

    /** Taille des cartes du plateau.
     * @return La taille des cartes du plateau. */
    public int tailleCartes() {
        return cartes.size();
    }

    /** Distribuer les 6 cartes de départ à un joueur.
     * @param joueur le joueur à qui distribuer les cartes */
    public void distribuerCartesDepart(Joueur joueur) {
        for (int i = 0; i < 6; i++) {
            joueur.getCartes().add(distribuerCarte());
        }
    }
}
