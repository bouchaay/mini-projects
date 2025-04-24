package ia;

import java.util.*;
import model.Borne;
import model.Carte;
import controller.JeuController;

/**
 * Stratégie difficile pour l'IA.
 * Cette stratégie permet à l'IA de jouer une carte avec le score maximal sur la première borne non remplie.
 */
public class HardStrategy implements Strategy {

        /** Score entre chaque niveau. */
        private static final int SCORE_ENTRE_NIVEAU = 30;
        /** Multiplicateur pour la suite couleur. */
        private static final int SUITE_COULEUR = 5;
        /** Multiplicateur pour le brelan. */
        private static final int BRELAN = 4;
        /** Multiplicateur pour la couleur. */
        private static final int COULEUR = 3;
        /** Multiplicateur pour la suite. */
        private static final int SUITE = 2;
        /** Multiplicateur pour la somme. */
        private static final int SOMME = 1;

    /** 
     * Jouer un tour pour l'IA.
     * @param jeuController le contrôleur du jeu
     * @param idJoueur l'identifiant du joueur
     */
    @Override
    public void jouer(JeuController jeuController, int idJoueur) {
        List<Borne> bornes = jeuController.getBornes();
        List<Carte> cartesDisponibles = jeuController.getTourManager().getJoueurCourant().getCartes();

        int meilleurScore = -1;
        int idBorneChoisie = -1;
        Carte carteChoisie = null;

        // Étape 1 : Parcourir chaque borne non remplie
        for (Borne borne : bornes) {
            List<Carte> cartesSurBorne = new ArrayList<>();
            
            if (!borne.isControlee()) {
                if (idJoueur == 1) {
                    cartesSurBorne = borne.getCartesJoueur1();
                } else {
                    cartesSurBorne = borne.getCartesJoueur2();
                }

                // Étape 2 : Tester chaque carte disponible pour maximiser le score de la combinaison
                for (Carte carte : cartesDisponibles) {
                    List<Carte> combinaisonTest = new ArrayList<>(cartesSurBorne);
                    combinaisonTest.add(carte);

                    if (combinaisonTest.size() <= 3) {
                        int score = evaluerCombinaison(combinaisonTest);
                        if (score > meilleurScore) {
                            meilleurScore = score;
                            idBorneChoisie = borne.getId();
                            carteChoisie = carte;
                        }
                    }
                }
            }
        }
        
        // Étape 3 : Jouer la meilleure carte sur la borne choisie
        if (idBorneChoisie != -1 && carteChoisie != null) {
            int idCarte = cartesDisponibles.indexOf(carteChoisie) + 1; // +1 car les indices des cartes commencent à 1
            jeuController.jouerTour(idBorneChoisie, idCarte, false);
            System.out.println("IA (difficile) a joué une carte avec score maximal sur la borne " + idBorneChoisie);
        } else {
            System.out.println("IA (difficile) n'a pas pu trouver une action optimale.");
        }
    }

    /**
     * Évaluer une combinaison de cartes.
     * @param cartes la combinaison de cartes
     * @return le score de la combinaison
     */
    public static int evaluerCombinaison(List<Carte> cartes) {
        // Si aucune carte n'est présente, le score est nul
        if (cartes.isEmpty()) {
            return 0;
        }
    
        // Trier les cartes par valeur
        cartes.sort((c1, c2) -> Integer.compare(c1.getValeur(), c2.getValeur()));
    
        int valTotal = cartes.stream().mapToInt(Carte::getValeur).sum();
    
        if (cartes.size() == 1) {
            // Une seule carte : retour de sa valeur brute (pondérée si besoin)
            return valTotal;
        }
    
        if (cartes.size() == 2) {
            // Deux cartes : vérifier les débuts de combinaisons
            boolean memeCouleur = cartes.get(0).getCouleur().equals(cartes.get(1).getCouleur());
            boolean consecutives = cartes.get(0).getValeur() == cartes.get(1).getValeur() - 1;
            boolean memeRang = cartes.get(0).getValeur() == cartes.get(1).getValeur();
    
            if (memeCouleur && consecutives) {
                return SCORE_ENTRE_NIVEAU * SUITE_COULEUR + valTotal;
            } else if (memeRang) {
                return SCORE_ENTRE_NIVEAU * BRELAN + valTotal; // Brelan partiel
            } else if (memeCouleur) {
                return SCORE_ENTRE_NIVEAU * COULEUR + valTotal;
            } else if (consecutives) {
                return SCORE_ENTRE_NIVEAU * SUITE + valTotal;
            } else {
                return SCORE_ENTRE_NIVEAU * SOMME + valTotal;
            }
        }
    
        // Cas normal pour 3 cartes (inchangé)
        boolean memeCouleur = cartes.get(0).getCouleur().equals(cartes.get(1).getCouleur()) &&
                              cartes.get(1).getCouleur().equals(cartes.get(2).getCouleur());
        boolean consecutives = cartes.get(0).getValeur() == cartes.get(1).getValeur() - 1 &&
                               cartes.get(1).getValeur() == cartes.get(2).getValeur() - 1;
        boolean memeRang = cartes.get(0).getValeur() == cartes.get(1).getValeur() &&
                           cartes.get(1).getValeur() == cartes.get(2).getValeur();
    
        if (memeCouleur && consecutives) {
            return SCORE_ENTRE_NIVEAU * SUITE_COULEUR + valTotal;
        } else if (memeRang) {
            return SCORE_ENTRE_NIVEAU * BRELAN + valTotal;
        } else if (memeCouleur) {
            return SCORE_ENTRE_NIVEAU * COULEUR + valTotal;
        } else if (consecutives) {
            return SCORE_ENTRE_NIVEAU * SUITE + valTotal;
        } else {
            return SCORE_ENTRE_NIVEAU * SOMME + valTotal;
        }
    }
    
}
