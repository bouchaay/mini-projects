package ia;

import java.util.*;
import model.Borne;
import model.Carte;
import controller.JeuController;

public class IntermediateStrategy implements Strategy {
    @Override
    public void jouer(JeuController jeuController, int idJoueur) {
        int idBorne = 0;
        // Choisir la première borne non revendiquée
        List<Borne> bornes = jeuController.getBornes();
        for (Borne borne : bornes) {
            if (!borne.isControlee()) {
                idBorne = borne.getId();
                break;
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

        jeuController.jouerTour(idBorne, idCarte);
        System.out.println("IA (intermédiare) a joué une carte.");
    }
}
