package ia;

import model.Joueur;
import controller.JeuController;

/**
 * IA est la classe qui représente un joueur IA.
 */
public class IA extends Joueur {

    /** La stratégie de l'IA. */
    private Strategy strategy;

    /**
     * Constructeur de IA.
     * @param idJoueur l'identifiant du joueur
     * @param nu la stratégie de l'IA
     */
    public IA(int idJoueur, Strategy strategie) {
        super("IA" + idJoueur, idJoueur, false);
        this.strategy = strategie;
    }

    /**
     * Modifier la stratégie de l'IA.
     * @param strategy la nouvelle stratégie de l'IA
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /** Obtenir la stratégie de l'IA.
     * @return la stratégie de l'IA */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * Jouer un tour pour l'IA.
     * @param jeuController le contrôleur du jeu
     * @param idJoueur l'identifiant du joueur
     */
    public void jouerTour(JeuController jeuController, int idJoueur) {
        if (strategy != null) {
            strategy.jouer(jeuController, idJoueur);
        } else {
            throw new IllegalStateException("Aucune stratégie définie pour l'IA !");
        }
    }
}
