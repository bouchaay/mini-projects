package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controller.JeuController;

public class CarteTactic extends Carte {

    /** Chemin du dossier des images. */
    public static final String CHEMIN_IMAGES = "image/CarteTactique/";
    /** Tactique de la carte. */
    private String tactique;
    /** Joueur qui a posé la carte. */
    private Joueur joueur;
    /** La borne sur laquelle la carte a été posée. */
    private Borne borne;
    /** Chemin de l'image de la carte. */
    private String cheminImage;
    /** Liste des cartes tactiques jouées. */
    private List<CarteTactic> defausseTactique;

    /**
     * Constructeur de CarteTactic.
     * 
     * @param couleur  la couleur de la carte
     * @param valeur   la valeur de la carte
     * @param tactique la tactique de la carte
     * @param joueur   le joueur qui a posé la carte
     * @param borne    la borne sur laquelle la carte a été posée
     */
    public CarteTactic(String tactique) {
        super("blanc", 0);
        this.defausseTactique = new ArrayList<>();
        this.tactique = tactique;
        this.joueur = null;
        this.borne = null;
        this.cheminImage = CHEMIN_IMAGES + tactique + ".png";
    }

    /**
     * Getter de la tactique.
     * 
     * @return la tactique de la carte
     */
    public String getTactique() {
        return tactique;
    }

    /**
     * Setter de la tactique.
     * 
     * @param tactique la tactique de la carte
     */
    public void setTactique(String tactique) {
        this.tactique = tactique;
    }

    /**
     * Getter du joueur.
     * 
     * @return le joueur qui a posé la carte
     */
    public Joueur getJoueur() {
        return joueur;
    }

    /**
     * Setter du joueur.
     * 
     * @param joueur le joueur qui a posé la carte
     */
    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    /**
     * Getter de la borne.
     * 
     * @return la borne sur laquelle la carte a été posée
     */
    public Borne getBorne() {
        return borne;
    }

    /**
     * Setter de la borne.
     * 
     * @param borne la borne sur laquelle la carte a été posée
     */
    public void setBorne(Borne borne) {
        this.borne = borne;
    }

    /**
     * Getter du chemin de l'image.
     * 
     * @return le chemin de l'image de la carte
     */
    public String getCheminImage() {
        return cheminImage;
    }

    /**
     * Méthode pour afficher la carte tactique.
     * 
     * @return la carte tactique
     */
    @Override
    public String toString() {
        return super.toString() + " " + tactique;
    }

    /**
     * Appliquer la tactique de la carte.
     * 
     * @param couleur la couleur de la carte
     * @param valeur  la valeur de la carte
     */
    public void appliquerTactiqueElite(String couleur, int valeur) {
        switch (tactique) {
            case "joker":
                // SI le joueur n'a pas déjà joué un joker
                if (!joueur.aJoueJoker()) {
                    // Modifier les valeurs de la carte
                    this.setCouleur(couleur);
                    this.setValeur(valeur);
                    // Le joueur joue un joker
                    joueur.setJokerJoue(true);
                }
                break;
            case "espion":
                // Si la borne n'est pas déjà revendiquée par le joueur
                this.setCouleur(couleur);
                this.setValeur(7);
                break;
            case "bouclier":
                // Si la borne n'est pas déjà revendiquée par le joueur
                this.setValeur(valeur);
                this.setCouleur(couleur);
                break;
            default:
                break;
        }
    }

    /**
     * Appliquer la tactique de la carte de type "Combat".
     */
    public void appliquerTactiqueCombat() {
        switch (tactique) {
            case "colin":
                borne.setColinJoue(true);
                defausseTactique.add(this);
                break;
            case "boue":
                borne.setBoueJoue(true);
                borne.setTaille(4);
                borne.setJoueurControle(null);
                borne.setControlee(false);
                defausseTactique.add(this);
                break;
            default:
                break;
        }
    }

    /**
     * Appliquer la tactique de la carte de type "Chasseur".
     * 
     * @param jeuController le jeuController
     * @param idCarteRemove1 l'id de la première carte à supprimer
     * @param idCarteRemove2 l'id de la deuxième carte à supprimer
     * @param carte1         la première carte à ajouter
     * @param carte2         la deuxième carte à ajouter
     * @param carte3         la troisième carte à ajouter
     */
    public void appliquerTactiqueChasseur(JeuController jeuController, int idCarteRemove1, int idCarteRemove2, Carte carte1, Carte carte2, Carte carte3) {
        Carte carteR1 = joueur.getCarte(idCarteRemove1);
        joueur.supprimerCarte(idCarteRemove1);
        jeuController.getPlateau().ajouterCarte(carteR1);
        // Pour la deuxième carte
        Carte carteR2 = joueur.getCarte(idCarteRemove2);
        joueur.supprimerCarte(idCarteRemove2);
        jeuController.getPlateau().ajouterCarte(carteR2);
        // Ajout des nouvelles cartes
        joueur.ajouterCarte(carte1);
        joueur.ajouterCarte(carte2);
        joueur.ajouterCarte(carte3);
        defausseTactique.add(this);
        joueur.retirerCarte(this);
        jeuController.getTourManager().tourSuivant();
    }

    /**
     * Appliquer la tactique de la carte de type "Stratege".
     * 
     * @param borneExpediteur la borne expéditrice
     * @param borneRecepteur  la borne réceptrice
     * @param idCarteBorne    l'id de la carte à ajouter
     * @param defausser       true si la carte doit être défaussée, false sinon
     * @return true si la tactique a été appliquée, false sinon
     */
    public boolean appliquerTactiqueStratege(Borne borneExpediteur, Borne borneRecepteur, int idCarteBorne, boolean defausser) {
        if (!defausser && borneRecepteur == null) {
            System.out.println("La borne réceptrice n'existe pas.");
            JOptionPane.showMessageDialog(null, "La borne réceptrice n'existe pas.");
            return false;
        }

        if (borneExpediteur.isControlee() || (borneRecepteur.isControlee()) || borneExpediteur.sizeBorneJoueur(joueur.getJoueur()) == 0 || borneRecepteur.sizeBorneJoueur(joueur.getJoueur()) >= borneRecepteur.getTaille()) {
            System.out.println("L'une des bornes est déjà contrôlée.");
            JOptionPane.showMessageDialog(null, "L'une des bornes est déjà contrôlée ou la borne réceptrice est pleine ou la borne expéditrice est vide.");
            return false;
        }

        int idJoueur = joueur.getJoueur();
        switch (idJoueur) {
            case 1:
                List<Carte> cartes1 = borneExpediteur.getCartesJoueur1();
                Carte carte1 = cartes1.get(idCarteBorne - 1);
                if (!defausser) {
                    borneRecepteur.ajouterCarteJoueur1(carte1);
                }
                borneExpediteur.supprimerCarteJoueur1(carte1);
                defausseTactique.add(this);
                return true;
            case 2:
                List<Carte> cartes2 = borneExpediteur.getCartesJoueur2();
                Carte carte2 = cartes2.get(idCarteBorne - 1);
                if (!defausser) {
                    borneRecepteur.ajouterCarteJoueur2(carte2);
                }
                borneExpediteur.supprimerCarteJoueur2(carte2);
                defausseTactique.add(this);
                return true;
            default:
                return false;
        }
    }

    /**
     * Appliquer la tactique de la carte de type "Banshee".
     * Supprimer une carte de la borne adverse non contrôlée.
     * @param borne        la borne
     * @param idCarteBorne l'id de la carte à supprimer
     * @return true si la tactique a été appliquée, false sinon
     */
    public boolean appliquerTactiqueBanshee(Borne borne, int idCarteBorne) {
        if (borne.isControlee() || borne.sizeBorneJoueur(joueur.getJoueur() == 1 ? 2 : 1) == 0) {
            System.out.println("La borne est déjà contrôlée.");
            JOptionPane.showMessageDialog(null, "La borne est déjà contrôlée ou vide.");
            return false;
        }

        int idJoueur = joueur.getJoueur();
        switch (idJoueur) {
            case 1:
                List<Carte> cartes2 = borne.getCartesJoueur2();
                Carte carte2 = cartes2.get(idCarteBorne - 1);
                borne.supprimerCarteJoueur2(carte2);
                defausseTactique.add(this);
                return true;
            case 2:
                List<Carte> cartes1 = borne.getCartesJoueur1();
                Carte carte1 = cartes1.get(idCarteBorne - 1);
                borne.supprimerCarteJoueur1(carte1);
                defausseTactique.add(this);
                return true;
            default:
                return false;
        }
    }

    /**
     * Appliquer la tactique de la carte de type "Traitre".
     * Echanger une carte de la borne adverse non contrôlée.
     * @param borneJoueur la borne du joueur
     * @param borneAdv    la borne adverse
     * @param idCarteBorne l'id de la carte à échanger
     * @return true si la tactique a été appliquée, false sinon
     */
    public boolean appliquerTactiqueTraitre(Borne borneJoueur, Borne borneAdv, int idCarteBorne) {
        if (borneJoueur.isControlee() || borneAdv.isControlee() || borneJoueur.sizeBorneJoueur(joueur.getJoueur()) >= borneJoueur.getTaille() || (borneAdv.sizeBorneJoueur(joueur.getJoueur() == 1 ? 2 : 1)) == 0) {
            System.out.println("L'une des bornes est déjà contrôlée.");
            JOptionPane.showMessageDialog(null, "L'une des bornes est déjà contrôlée ou la borne adverse est vide ou la borne du joueur est pleine.");
            return false;
        }

        int idJoueur = joueur.getJoueur();
        switch (idJoueur) {
            case 1:
                List<Carte> cartes2 = borneAdv.getCartesJoueur2();
                Carte carte2 = cartes2.get(idCarteBorne - 1);
                borneJoueur.ajouterCarteJoueur1(carte2);
                borneAdv.supprimerCarteJoueur2(carte2);
                defausseTactique.add(this);
                return true;
            case 2:
                List<Carte> cartes1 = borneAdv.getCartesJoueur1();
                Carte carte1 = cartes1.get(idCarteBorne - 1);
                borneJoueur.ajouterCarteJoueur2(carte1);
                borneAdv.supprimerCarteJoueur1(carte1);
                defausseTactique.add(this);
                return true;
            default:
                return false;
        }
    }
}
