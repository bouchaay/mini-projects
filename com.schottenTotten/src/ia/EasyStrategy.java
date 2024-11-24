package ia;

import java.util.*;
import model.Borne;


import controller.JeuController;

public class EasyStrategy implements Strategy {
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
        // Choisir la première carte du joueur
        int idCarte = 1;

        jeuController.jouerTour(idBorne, idCarte);
        System.out.println("IA (facile) a joué une carte.");
    }
}
