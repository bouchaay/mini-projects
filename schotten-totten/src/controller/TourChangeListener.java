package controller;

import model.Joueur;

/** Interface pour écouter les changements de tour */
public interface TourChangeListener {
    /**
     * Méthode appelée lorsqu'un changement de tour se produit.
     * @param joueur le nouveau joueur courant
     */
    void onTourChange(Joueur joueur);
}
