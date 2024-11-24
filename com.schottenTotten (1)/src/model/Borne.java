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

    /** Afficher la borne. */
    public void afficher() {
        String cJ1 = "";
        String cJ2 = "";
        System.out.print("B" + id + " : ");
        for (Carte carte : cartesJoueur1) {
            cJ1 += carte.toString() + " ";
        }

        for (Carte carte : cartesJoueur2) {
            cJ2 += carte.toString() + " ";
        }
        System.out.print("J1 : " + cJ1 + " J2 : " + cJ2);
        if (controlee) {
            System.out.println(" Contrôlée par " + joueurControle.getNom());
        }
    }
}
