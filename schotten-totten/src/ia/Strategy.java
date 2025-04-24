package ia;

import controller.JeuController;

/**
 * Interface Strategy pour les stratégies de l'IA.
 */
public interface Strategy {
    void jouer(JeuController jeuController, int idJoueur);
}
