package controller;
import model.*;

/** Classe principale du jeu. */
public class MainJeu {

    public static void main(String[] args) {
        // Créer les joueurs
        Joueur joueur1 = new Joueur("Ayoub", 1, false);
        Joueur joueur2 = new Joueur("IA", 2, false);

        // Créer le jeu
        JeuController jeu = new JeuController(joueur1, joueur2);

        // Commencer la partie
        jeu.commencerPartie();
    }
}
