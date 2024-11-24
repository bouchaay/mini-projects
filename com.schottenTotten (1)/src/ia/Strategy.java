package ia;

import controller.JeuController;

public interface Strategy {
    void jouer(JeuController jeuController, int idJoueur);
}
