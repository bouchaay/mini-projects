package ia;

import java.util.*;
import model.Borne;
import controller.JeuController;

/**
 * Stratégie facile pour l'IA.
 * Cette stratégie permet à l'IA de jouer la première carte sur la première borne non remplie.
 */
public class EasyStrategy implements Strategy {
    /**
     * Jouer un tour pour l'IA.
     * @param jeuController le contrôleur du jeu
     * @param idJoueur l'identifiant du joueur
     */
    @Override
    public void jouer(JeuController jeuController, int idJoueur) {
    	int idBorne = 0;
        // Choisir la première borne non remplie
        List<Borne> bornes = jeuController.getBornes();
        for (Borne borne : bornes) {
        	if (idJoueur == 1) {
        		if (borne.getCartesJoueur1().size() < 3) {
        			idBorne = borne.getId();
        			break;
        		}
            } else {
            	if (borne.getCartesJoueur2().size() < 3) {
                    idBorne = borne.getId();
                    break;
            	}
            }
        }
        // Choisir la première carte du joueur
        int idCarte = 1;

        jeuController.jouerTour(idBorne, idCarte, false);
        System.out.println("IA (facile) a joué une carte.");
    }
}
