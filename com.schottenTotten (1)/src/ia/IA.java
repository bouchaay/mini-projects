package ia;

import model.Joueur;
import controller.JeuController;

public class IA extends Joueur {
    private Strategy strategy;

    public IA(int id, Strategy nu) {
        super("IA" + id, id, false);
        this.strategy = nu;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void jouerTour(JeuController jeuController) {
        if (strategy != null) {
            strategy.jouer(jeuController, this.getJoueur());
        } else {
            throw new IllegalStateException("Aucune stratégie définie pour l'IA !");
        }
    }
}
