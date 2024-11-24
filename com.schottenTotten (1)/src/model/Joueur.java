package model;

import java.util.ArrayList;
import java.util.List;

/** Classe pour définir un joueur. */
public class Joueur {

    /** Nom du joueur */
    private String nom;
    /** Les cartes du joueur */
    private List<Carte> cartes;
    /** Les id des bornes revendiquées par le joueur */
    private List<Integer> bornesRevendiquees;
    /* Jouer 1 ou 2 */
    private int joueurId;
    /* Joueur humain ou ordinateur */
    private boolean humain;

    /**
     * Constructeur de la classe Joueur.
     * 
     * @param nom    Nom du joueur.
     * @param cartes Les cartes du joueur.
     * @param joueur Joueur 1 ou 2
     */
    public Joueur(String nom, int joueur, boolean humain) {
        this.nom = nom;
        this.cartes = new ArrayList<>();
        this.bornesRevendiquees = new ArrayList<>();
        this.joueurId = joueur;
        this.humain = humain;
    }

    /**
     * Getter du nom du joueur.
     * 
     * @return Nom du joueur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter des cartes du joueur.
     * 
     * @return Les cartes du joueur.
     */
    public List<Carte> getCartes() {
        return cartes;
    }

    /**
     * Getter des bornes revendiquées par le joueur.
     * 
     * @return Les id des bornes revendiquées par le joueur.
     */
    public List<Integer> getBornesRevendiquees() {
        return bornesRevendiquees;
    }

    /**
     * Getter du joueur.
     * 
     * @return Joueur 1 ou 2
     */
    public int getJoueur() {
        return joueurId;
    }

    public boolean isHumain() {
        return humain;
    }

    /**
     * Setter du nom du joueur.
     * 
     * @param nom Nom du joueur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Setter des cartes du joueur.
     * 
     * @param cartes Les cartes du joueur.
     */
    public void setCartes(List<Carte> cartes) {
        this.cartes = cartes;
    }

    /**
     * Setter du joueur.
     * 
     * @param joueur Joueur 1 ou 2
     */
    public void setJoueur(int joueur) {
        this.joueurId = joueur;
    }

    /**
     * Setter des bornes revendiquées par le joueur.
     * 
     * @param bornesRevendiquees Les id des bornes revendiquées par le joueur.
     */
    public void setBornesRevendiquees(List<Integer> bornesRevendiquees) {
        this.bornesRevendiquees = bornesRevendiquees;
    }

    /** Setter du joueur humain ou ordinateur */
    public void setHumain(boolean humain) {
        this.humain = humain;
    }

    /** Retourner si le joueur est humain ou ordinateur */
    public boolean estHumain() {
        return humain;
    }

    /**
     * Ajouter une borne revendiquée par le joueur.
     * 
     * @param idBorne L'id de la borne revendiquée.
     */
    public void ajouterBorneRevendiquee(int idBorne) {
        bornesRevendiquees.add(idBorne);
    }

    /**
     * Vérifier si le joueur est gagnant.
     * 
     * @return true si le joueur est gagnant, false sinon.
     */
    public boolean estGagnant() {
        // Si le joueur a revendiqué 5 bornes, il est gagnant
        if (bornesRevendiquees.size() >= 5) {
            return true;
        }

        // si le joeuru revendique 3 bornes consécutives, il est gagnant
        if (bornesRevendiquees.size() >= 3) {
            // On trie les bornes revendiquées
            bornesRevendiquees.sort(null);

            // On vérifie si les bornes sont consécutives
            int bornePrecedente = bornesRevendiquees.get(0);
            int consecutives = 1;
            for (int i = 1; i < bornesRevendiquees.size(); i++) {
                if (bornesRevendiquees.get(i) == bornePrecedente + 1) {
                    consecutives++;
                } else {
                    consecutives = 1;
                }
                bornePrecedente = bornesRevendiquees.get(i);
            }

            if (consecutives >= 3) {
                return true;
            }
        }

        return false;
    }

    /**
     * Récupérer une carte par index.
     * 
     * @param index L'index de la carte.
     * @return La carte récupérée.
     */
    public Carte getCarte(int index) {
        return cartes.get(index);
    }

    /**
     * Méthode pour ajouter une carte au joueur.
     * 
     * @param carte La carte à ajouter.
     */
    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }

    /**
     * Méthode pour retirer une carte au joueur.
     * 
     * @param carte La carte à retirer.
     */
    public void retirerCarte(Carte carte) {
        cartes.remove(carte);
    }

    /** Méthode pour afficher les cartes du joueur. */
    public void afficherCartes() {
        for (int i = 0; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            System.out.print(carte.toString() + " ");
        }
        System.out.println();
    }

    @Override
    public String toString() {
        // id des bornes revendiquées
        List<String> bornesRevendiqueesStr = new ArrayList<>();
        for (int idBorne : bornesRevendiquees) {
            bornesRevendiqueesStr.add(String.valueOf(idBorne));
        }
        return "Joueur{" +
                "nom='" + nom + '\'' +
                ", joueur=" + joueurId +
                ", bornesRevendiquees=" + bornesRevendiqueesStr +
                '}';
    }
}
