package model;

import java.util.List;

/** Classe pour définir une borne. */
public class Borne {

    /** Identifiant de la borne. */
    private int id;
    /** Liste des cartes du premier joueur. */
    private List<Carte> cartesJoueur1;
    /** Liste des cartes du deuxième joueur. */
    private List<Carte> cartesJoueur2;
    /** Indique si une borne est controlée par un joueur. */
    private boolean controlee;
    /** Joueur qui contrôle la borne. */
    private Joueur joueurControle;
    /** Taille de la borne. */
    private int taille;
    /** Colin-Maillard a été joué. */
    private boolean colinJoue;
    /** Combat de Boue a été joué */
    private boolean boueJoue;

    /** Constructeur de la classe Borne.
     * @param id Identifiant de la borne.
     * @param cartesJoueur1 Liste des cartes du premier joueur.
     * @param cartesJoueur2 Liste des cartes du deuxième joueur.
     * @param controlee Indique si une borne est controlée par un joueur.
     * @param joueurControle Joueur qui contrôle la borne. */
    public Borne(int id, List<Carte> cartesJoueur1, List<Carte> cartesJoueur2, boolean controlee, Joueur joueurControle) {
        this.id = id;
        this.cartesJoueur1 = cartesJoueur1;
        this.cartesJoueur2 = cartesJoueur2;
        this.controlee = controlee;
        this.joueurControle = joueurControle;
        this.taille = 3;
        this.colinJoue = false;
        this.boueJoue = false;
    }

    /** Getter de l'identifiant de la borne.
     * @return Identifiant de la borne. */
    public int getId() {
        return id;
    }

    /** Getter de la liste des cartes du premier joueur.
     * @return Liste des cartes du premier joueur. */
    public List<Carte> getCartesJoueur1() {
        return cartesJoueur1;
    }

    /** Getter de la liste des cartes du deuxième joueur.
     * @return Liste des cartes du deuxième joueur. */
    public List<Carte> getCartesJoueur2() {
        return cartesJoueur2;
    }

    /** Getter de l'indicateur de contrôle de la borne.
     * @return Indique si une borne est controlée par un joueur. */
    public boolean isControlee() {
        return controlee;
    }

    /** Getter du joueur qui contrôle la borne.
     * @return Joueur qui contrôle la borne. */
    public Joueur getJoueurControle() {
        return joueurControle;
    }

    /** Setter de l'identifiant de la borne.
     * @param id Identifiant de la borne. */
    public void setId(int id) {
        this.id = id;
    }

    /** Setter de la liste des cartes du premier joueur.
     * @param cartesJoueur1 Liste des cartes du premier joueur. */
    public void setCartesJoueur1(List<Carte> cartesJoueur1) {
        this.cartesJoueur1 = cartesJoueur1;
    }

    /** Setter de la liste des cartes du deuxième joueur.
     * @param cartesJoueur2 Liste des cartes du deuxième joueur. */
    public void setCartesJoueur2(List<Carte> cartesJoueur2) {
        this.cartesJoueur2 = cartesJoueur2;
    }

    /** Setter de l'indicateur de contrôle de la borne.
     * @param controlee Indique si une borne est controlée par un joueur. */
    public void setControlee(boolean controlee) {
        this.controlee = controlee;
    }

    /** Setter du joueur qui contrôle la borne.
     * @param joueurControle Joueur qui contrôle la borne. */
    public void setJoueurControle(Joueur joueurControle) {
        this.joueurControle = joueurControle;
    }

    /** Ajouter une carte par le premier joueur.
     * @param carte Carte à ajouter. */
    public void ajouterCarteJoueur1(Carte carte) {
        cartesJoueur1.add(carte);
    }

    /** Ajouter une carte par le deuxième joueur.
     * @param carte Carte à ajouter. */
    public void ajouterCarteJoueur2(Carte carte) {
        cartesJoueur2.add(carte);
    }

    /** Getter de la taille de la borne.
     * @return Taille de la borne. */
    public int getTaille() {
        return taille;
    }

    /** Setter de la taille de la borne.
     * @param taille Taille de la borne. */
    public void setTaille(int taille) {
        this.taille = taille;
    }
    

    /** Getter de l'indicateur de Colin-Maillard.
     * @return Indicateur de Colin-Maillard. */
    public boolean isColinJoue() {
        return colinJoue;
    }

    /** Setter de l'indicateur de Colin-Maillard.
     * @param colinJoue Indicateur de Colin-Maillard. */
    public void setColinJoue(boolean colinJoue) {
        this.colinJoue = colinJoue;
    }

    /** Getter de l'indicateur de Combat de Boue.
     * @return Indicateur de Combat de Boue. */
    public boolean isBoueJoue() {
        return boueJoue;
    }

    /** Setter de l'indicateur de Combat de Boue.
     * @param combatJoue Indicateur de Combat de Boue. */
    public void setBoueJoue(boolean combatJoue) {
        this.boueJoue = combatJoue;
    }

    /** Supprimer une carte par le premier joueur.
     * @param carte Carte à supprimer. */
    public void supprimerCarteJoueur1(Carte carte) {
        cartesJoueur1.remove(carte);
    }

    /** Supprimer une carte par le deuxième joueur.
     * @param carte Carte à supprimer. */
    public void supprimerCarteJoueur2(Carte carte) {
        cartesJoueur2.remove(carte);
    }

    /** Taille des cartes d'un joueur.
     * @param idJoueur Identifiant du joueur.
     * @return Taille des cartes du joueur. */
    public int sizeBorneJoueur(int idJoueur) {
        if (idJoueur == 1) {
            return cartesJoueur1.size();
        } else {
            return cartesJoueur2.size();
        }
    }
}
