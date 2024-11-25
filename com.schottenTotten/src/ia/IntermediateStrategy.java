package ia;

import java.util.*;
import model.Borne;
import model.Carte;
import controller.JeuController;

/**
 * Stratégie intermédiaire pour l'IA.
 * Cette stratégie permet à l'IA de jouer une carte avec la plus grande valeur sur la première borne non remplie.
 */
public class IntermediateStrategy implements Strategy {
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
        // Choisir la première carte avec la plus grande valeur
        int idCarte = 0;
        int max = 0;
        List<Carte> cartes = jeuController.getTourManager().getJoueurCourant().getCartes();
        for (int i = 0; i < cartes.size(); i++) {
            if (cartes.get(i).getValeur() > max) {
                max = cartes.get(i).getValeur();
                idCarte = i + 1;
            }
        }

        if(jeuController.jouerTour(idBorne, idCarte, false)) {
        	System.out.println("IA (intermédiare) a joué une carte.");
        } else {
        	System.out.println("IA (intermédiare) a joué incorrectement, elle va réessayer ! borne:" + idBorne + "carte: idCarte");
        }
    }
}
