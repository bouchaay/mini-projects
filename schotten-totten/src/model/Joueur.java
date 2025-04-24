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
    /** Nombre de carte tactique jouée */
    private int nbCarteTactique;
    /** Si il a déjà joué un joker */
    private boolean jokerJoue;

    /**
     * Constructeur de la classe Joueur.
     * 
     * @param nom    Nom du joueur.
     * @param cartes Les cartes du joueur.
     * @param joueur Joueur 1 ou 2
     */
    public Joueur(String nom, int joueur, boolean humain) {
        this.nbCarteTactique = 0;
        this.nom = nom;
        this.cartes = new ArrayList<>();
        this.bornesRevendiquees = new ArrayList<>();
        this.joueurId = joueur;
        this.humain = humain;
        this.jokerJoue = false;
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

    /** Supprimer une borne revendiquée par le joueur.
     * @param idBorne L'id de la borne revendiquée. */
    public void supprimerBorneRevendiquee(int idBorne) {
        bornesRevendiquees.remove(Integer.valueOf(idBorne));
    }

    /**
     * Ajouter une carte tactique jouée par le joueur.
     */
    public void aJoueCarteTactique() {
        nbCarteTactique++;
    }

    /**
     * Retourner le nombre de carte tactique jouée par le joueur.
     * 
     * @return Le nombre de carte tactique jouée par le joueur.
     */
    public int getNbCarteTactique() {
        return nbCarteTactique;
    }

    /**
     * Retourner si le joueur a déjà joué un joker.
     * 
     * @return true si le joueur a déjà joué un joker, false sinon.
     */
    public boolean aJoueJoker() {
        return jokerJoue;
    }

    /** Setter si le joueur a déjà joué un joker
     * @param jokerJoue true si le joueur a déjà joué un joker, false sinon */
    public void setJokerJoue(boolean jokerJoue) {
        this.jokerJoue = jokerJoue;
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

    /** Supprimer une carte by id de la carte cad supprimer la carte i - 1
     * @param idCarte l'id de la carte */
    public void supprimerCarte(int idCarte) {
        cartes.remove(idCarte - 1);
    }

    /** Méthode pour afficher le joueur. */
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
