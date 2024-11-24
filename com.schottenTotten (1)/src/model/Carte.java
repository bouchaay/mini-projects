package model;

/** Classe pour définir une carte. */
public class Carte {

    /** Couleur de la carte. */
    private String couleur;
    /** Valeur de la carte. */
    private int valeur;

    /** Constructeur de la classe Carte.
     * @param couleur Couleur de la carte.
     * @param valeur Valeur de la carte. */
    public Carte(String couleur, int valeur) {
        this.couleur = couleur;
        this.valeur = valeur;
    }

    /** Getter de la couleur de la carte.
     * @return Couleur de la carte. */
    public String getCouleur() {
        return couleur;
    }

    /** Getter de la valeur de la carte.
     * @return Valeur de la carte. */
    public int getValeur() {
        return valeur;
    }

    /** Setter de la couleur de la carte.
     * @param couleur Couleur de la carte. */
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    /** Setter de la valeur de la carte.
     * @param valeur Valeur de la carte. */
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    /** afficher une carte */
    public String toString() {
        return this.couleur.substring(0, 2) + this.valeur;
    }
}
