package controller;

import model.Joueur;

import java.util.ArrayList;
import java.util.List;

/** TourManager est la classe qui gère les tours des joueurs. */
public class TourManager {

    /** Le premier joueur */
    private Joueur joueur1;
    /** Le deuxième joueur */
    private Joueur joueur2;
    /** Le joueur courant */
    private Joueur joueurCourant;
    /** Liste des listeners qui écoutent les changements de tour */
    private List<TourChangeListener> listeners;

    /**
     * Constructeur de TourManager.
     * @param joueur1 le premier joueur
     * @param joueur2 le deuxième joueur
     */
    public TourManager(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.joueurCourant = joueur1;
        this.listeners = new ArrayList<>();
    }

    /**
     * Permet de passer au tour suivant.
     */
    public void tourSuivant() {
        if (this.joueurCourant == this.joueur1) {
            this.joueurCourant = this.joueur2;
        } else {
            this.joueurCourant = this.joueur1;
        }
        notifierListeners(); // Notifie tous les listeners du changement
    }

    /**
     * Permet de récupérer le joueur courant.
     * @return le joueur courant
     */
    public Joueur getJoueurCourant() {
        return this.joueurCourant;
    }

    /** Permet de récupérer le joueur suivant.
     * @return le joueur suivant */
    public Joueur getJoueurSuivant() {
        if (this.joueurCourant == this.joueur1) {
            return this.joueur2;
        } else {
            return this.joueur1;
        }
    }

    /**
     * Ajoute un listener qui sera notifié des changements de tour.
     * @param listener le listener à ajouter
     */
    public void ajouterListener(TourChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifie tous les listeners qu'un changement de tour a eu lieu.
     */
    private void notifierListeners() {
        for (TourChangeListener listener : listeners) {
            listener.onTourChange(joueurCourant);
        }
    }
}
