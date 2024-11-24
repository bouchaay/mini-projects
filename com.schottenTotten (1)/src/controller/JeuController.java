package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.*;

/**
 * JeuController est la classe qui gère les actions de l'utilisateur sur le jeu.
 * Elle permet de gérer les actions de l'utilisateur sur le jeu.
 */
public class JeuController {

    /** Les borrnes du jeu */
    private List<Borne> bornes;
    /** Le plateau du jeu */
    private Plateau plateau;
    /** Le premier joueur */
    private Joueur joueur1;
    /** Le deuxième joueur */
    private Joueur joueur2;
    /** Le tour manager */
    private TourManager tourManager;
    /** Scanner pour lire les entrées de l'utilisateur */
    private Scanner scanner = new Scanner(System.in);

    /**
     * Constructeur de JeuController.
     * @param bornes les bornes du jeu
     * @param plateau le plateau du jeu
     * @param joueur1 le premier joueur
     * @param joueur2 le deuxième joueur
     */
    public JeuController(Joueur joueur1, Joueur joueur2) {
        // Créer les bornes
        this.bornes = new ArrayList<Borne>();
        for (int i = 1; i <= 9; i++) {
            bornes.add(new Borne(i, new ArrayList<Carte>(), new ArrayList<Carte>(), false, null));
        }

        // Créer le plateau
        this.plateau = new Plateau();

        // Les joueurs
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;

        // Le tour manager
        this.tourManager = new TourManager(joueur1, joueur2);
    }

    public void commencerPartie() {
        // On distribue les cartes
        plateau.distribuerCartesDepart(joueur1);
        plateau.distribuerCartesDepart(joueur2);

        // On démarre le jeu
        while (joueur1.getCartes().size() > 0 && joueur2.getCartes().size() > 0) {

            // On récupère le joueur courant
            Joueur joueurCourant = tourManager.getJoueurCourant();

            // on affiche le plateau si le joueur courant est humain
            if (joueurCourant.estHumain()) {
                afficherPlateau();
                // On affiche le joueur courant
                System.out.println("C'est au tour de " + joueurCourant.getNom());

                // On joue le tour
                boolean tourJoue;
                int idBorne = 0;
                int idCarte = 0;

                // Demander si le joueur veyx revendiquer une borne
                System.out.println("Choisissez une borne (1-9) : ");
                idBorne = scanner.nextInt();
                System.out.println("Choisissez une carte (1-6) : ");
                idCarte = scanner.nextInt();

                // Jouer le tour
                tourJoue = jouerTour(idBorne, idCarte);

                // Si le tour n'a pas été joué, on redemande à l'utilisateur
                while (!tourJoue) {
                    System.out.println("Tour non joué. Réessayez.");
                    // Demander si le joueur veyx revendiquer une borne
                    System.out.println("Choisissez une borne (1-9) : ");
                    idBorne = scanner.nextInt();
                    System.out.println("Choisissez une carte (1-6) : ");
                    idCarte = scanner.nextInt();
                }
            } else {
                int idBorne = 0;
                int idCarte = 0;
                do {
                    // Borne et idCarte choisis par l'ordinateur aléatoirement avec Random
                    idBorne = (int) (Math.random() * 9) + 1;
                    idCarte = (int) (Math.random() * joueurCourant.getCartes().size()) + 1;
                } while (!jouerTour(idBorne, idCarte));

                System.out.println(joueurCourant.getNom() + " a joué la borne " + idBorne + " avec la carte " + idCarte);
            }

            // On passe au tour suivant
            tourManager.tourSuivant();

            // On vérifie si un joueur a gagné
            if (verifierGagnant()) {
                afficherPlateau();
                break;
            }
        }        
    }

    /** Méthode pour jouer un tour
     * @param idBorne l'identifiant de la borne
     * @param idCarte l'identifiant de la carte
     * @return true si le tour a été joué, false sinon */
    public boolean jouerTour(int idBorne, int idCarte) {
        Joueur joueurCourant = tourManager.getJoueurCourant();
        Borne borne = bornes.get(idBorne - 1);
        Carte carte = joueurCourant.getCarte(idCarte - 1);
        return jouerCarte(borne, carte, joueurCourant);
    }

    /** Vérifier s'il y a un gagnant.
     * @return true s'il y a un gagnant, false sinon */
    public boolean verifierGagnant() {
        if (joueur1.estGagnant()) {
            System.out.println(joueur1.getNom() + " a gagné !");
            joueur1.toString();
            return true;
        } else if (joueur2.estGagnant()) {
            System.out.println(joueur2.getNom() + " a gagné !");
            joueur2.toString();
            return true;
        }
        return false;
    }


    /**
     * Permet de jouer une carte sur une borne.
     * @param borne la borne sur laquelle jouer
     * @param carte la carte à jouer
     * @param joueur le joueur qui joue
     * @return true si la carte a été jouée, false sinon
     */
    public boolean jouerCarte(Borne borne, Carte carte, Joueur joueur) {
        if (!borne.isControlee()) {
            int idJoueur = joueur.getJoueur();
            switch (idJoueur) {
                case 1:
                    if (borne.getCartesJoueur1().size() < 3) {
                        borne.getCartesJoueur1().add(carte);
                    } else {
                        if (joueur.estHumain()) {
                            System.out.println("Borne déjà remplie.");
                        }
                        return false;
                    }
                    break;
                case 2:
                    if (borne.getCartesJoueur2().size() < 3) {
                        borne.getCartesJoueur2().add(carte);
                    } else {
                        if (joueur.estHumain()) {
                            System.out.println("Borne déjà remplie.");
                        }
                        return false;
                    }
                    break;
                default:
                    break;
            }

            // On retire la carte du joueur
            joueur.getCartes().remove(carte);

            // Lui distribuer une nouvelle carte
            Carte nouvelleCarte = plateau.distribuerCarte();
            if (nouvelleCarte != null) {
                joueur.getCartes().add(nouvelleCarte);
            }

            // On vérifie si la borne est contrôlée
            Regle.verifierControleBorne(borne, joueur1, joueur2, plateau.getCartes());
            if (joueur.estHumain()) {
                System.out.println("La carte a été jouée.");
            }
            return true;
        } else {
            if (joueur.estHumain()) {
                System.out.println("La borne est déjà contrôlée.");
            }
            return false;
        }
    }

    /**
     * Afficher le plateau du jeu.
     */
    public void afficherPlateau() {
        // Afficher les cartes du premier joueur
        System.out.println("J1 : " + joueur1.getNom());
        joueur1.afficherCartes();
        for (Borne borne : bornes) {
            borne.afficher();
        }
        System.out.println("J2 : " + joueur2.getNom());
        joueur2.afficherCartes();
    }

    /**
     * Getter des bornes du jeu.
     * @return les bornes du jeu
     */
    public List<Borne> getBornes() {
        return bornes;
    }

    /**
     * Setter des bornes du jeu.
     * @param bornes les bornes du jeu
     */
    public void setBornes(List<Borne> bornes) {
        this.bornes = bornes;
    }

    /**
     * Getter du plateau du jeu.
     * @return le plateau du jeu
     */
    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Setter du plateau du jeu.
     * @param plateau le plateau du jeu
     */
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    /**
     * Getter du premier joueur.
     * @return le premier joueur
     */
    public Joueur getJoueur1() {
        return joueur1;
    }

    /**
     * Setter du premier joueur.
     * @param joueur1 le premier joueur
     */
    public void setJoueur1(Joueur joueur1) {
        this.joueur1 = joueur1;
    }

    /**
     * Getter du deuxième joueur.
     * @return le deuxième joueur
     */
    public Joueur getJoueur2() {
        return joueur2;
    }

    /**
     * Setter du deuxième joueur.
     * @param joueur2 le deuxième joueur
     */
    public void setJoueur2(Joueur joueur2) {
        this.joueur2 = joueur2;
    }
    
    public TourManager getTourManager() {
    	return this.tourManager;
    }
}
