package controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.*;

/**
 * Regle est la classe qui gère les règles du jeu.
 */
public class Regle {

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

    /** Vérifier si la borne est controlée.
     * @param borne la borne à vérifier
     * @param joueur1 le premier joueur
     * @param joueur2 le deuxième joueur
     * @param cartesNonDistribuees les cartes non distribuées
     */
    public static void verifierControleBorne(Borne borne, Joueur joueur1, Joueur joueur2, List<Carte> cartesNonDistribuees) {
        // On récupère les cartes des joueurs
        List<Carte> cartesJ1 = borne.getCartesJoueur1();
        List<Carte> cartesJ2 = borne.getCartesJoueur2();

        // Si le sdeux joueurs ont joué 3 cartes chacun sur la borne
        if (cartesJ1.size() == 3 && cartesJ2.size() == 3) {

            // On évalue les combinaisons des joueurs
            int scoreJ1 = evaluerCombinaison(cartesJ1);
            int scoreJ2 = evaluerCombinaison(cartesJ2);

            // On vérifie qui a la meilleure combinaison
            if (scoreJ1 > scoreJ2) {
                // Le joueur 1 contrôle la borne
                borne.setJoueurControle(joueur1);
                borne.setControlee(true);
                joueur1.ajouterBorneRevendiquee(borne.getId());
                System.out.println("Le joueur " + joueur1.getNom() + " a revendiqué la borne " + borne.getId());
                if (joueur2.isHumain()) {
                	JOptionPane.showMessageDialog(null, "Le joueur " + joueur1.getNom() + " a revendiqué la borne " + borne.getId());
                }
                return;
            } else if (scoreJ1 < scoreJ2) {
                // Le joueur 2 contrôle la borne
                borne.setJoueurControle(joueur2);
                joueur2.ajouterBorneRevendiquee(borne.getId());
                borne.setControlee(true);
                System.out.println("Le joueur " + joueur2.getNom() + " a revendiqué la borne " + borne.getId());
                if (joueur1.isHumain()) {
                	JOptionPane.showMessageDialog(null, "Le joueur " + joueur2.getNom() + " a revendiqué la borne " + borne.getId());
                }
                return;
            } else {
       
                // Si les deux joueurs ont la même combinaison
                System.out.println("Egalité, la borne" + borne.getId() + " n'est pas controlée.");
                if (joueur1.isHumain() || joueur2.isHumain()) {
                	JOptionPane.showMessageDialog(null, "Egalité, la borne " + borne.getId() + " n'est pas controlée.");
                }
                return;
            }
        }

        // Si un joueur 1 peut prouver que l'autre ne peut plus le battre
        if (peutRevendiquer(borne, joueur1, joueur2, cartesNonDistribuees)) {
            borne.setJoueurControle(joueur1);
            joueur1.ajouterBorneRevendiquee(borne.getId());
            borne.setControlee(true);
            System.out.println("Le joueur " + joueur1.getNom() + " a revendiqué la borne " + borne.getId());
            if (joueur2.isHumain()) {
            	JOptionPane.showMessageDialog(null, "Le joueur " + joueur1.getNom() + " a revendiqué la borne " + borne.getId());
            }
            return;
        // Si un joueur 2 peut prouver que l'autre ne peut plus le battre
        } else if (peutRevendiquer(borne, joueur2, joueur1, cartesNonDistribuees)) {
            borne.setJoueurControle(joueur2);
            joueur2.ajouterBorneRevendiquee(borne.getId());
            borne.setControlee(true);
            System.out.println("Le joueur " + joueur2.getNom() + " a revendiqué la borne " + borne.getId());
            if (joueur1.isHumain()) {
            	JOptionPane.showMessageDialog(null, "Le joueur " + joueur2.getNom() + " a revendiqué la borne " + borne.getId());
            }
            return;
        }
    }

    /** Evaluer la combinaison des cartes d'un joueur.
     * @param cartes les cartes du joueur
     * @return le score de la combinaison */
    public static int evaluerCombinaison(List<Carte> cartes) {

        // Si la liste des cartes n'est pas de taille 3
        if (cartes.size() != 3) {
            return 0;
        }

        // On trie les cartes
        cartes.sort((c1, c2) -> Integer.compare(c1.getValeur(), c2.getValeur()));

        // Vérifier si les cartes sont de la même couleur
        boolean memeCouleur = cartes.get(0).getCouleur().equals(cartes.get(1).getCouleur()) &&
                             cartes.get(1).getCouleur().equals(cartes.get(2).getCouleur());

        // Vérifier si les cartes sont consécutives
        boolean consecutives = cartes.get(0).getValeur() == cartes.get(1).getValeur() - 1 &&
                               cartes.get(1).getValeur() == cartes.get(2).getValeur() - 1;

        // Vérifier si les cartes sont de meme rang
        boolean memeRang = cartes.get(0).getValeur() == cartes.get(1).getValeur() &&
                          cartes.get(1).getValeur() == cartes.get(2).getValeur();

        // Valeur totale de la combinaison
        int valTotal = cartes.get(0).getValeur() + cartes.get(1).getValeur() + cartes.get(2).getValeur();

        // Si les cartes sont de la même couleur et consécutives
        if (memeCouleur && consecutives) {
            return SCORE_ENTRE_NIVEAU * SUITE_COULEUR + valTotal;
        } else if (memeRang) {
            // Si les cartes sont de même rang
            return SCORE_ENTRE_NIVEAU * BRELAN + valTotal;
        } else if (memeCouleur) {
            // Si les cartes sont de la même couleur
            return SCORE_ENTRE_NIVEAU * COULEUR + valTotal;
        } else if (consecutives) {
            // Si les cartes sont consécutives
            return SCORE_ENTRE_NIVEAU * SUITE + valTotal;
        } else {
            // Si les cartes n'ont pas de combinaison particulière
            return SCORE_ENTRE_NIVEAU * SOMME + valTotal;
        }
    }

    /** Vérifier si un joueur peut revendiquer une borne.
     * @param borne la borne à vérifier
     * @param joueur le joueur qui peut revendiquer
     * @param adversaire l'adversaire du joueur
     * @param cartesNonDistribuees les cartes non distribuées
     * @return true si le joueur peut revendiquer, false sinon */
    public static boolean peutRevendiquer(Borne borne, Joueur joueur, Joueur adversaire, List<Carte> cartesNonDistribuees) {
        // On récupère les cartes des joueurs
        List<Carte> cartesJ = borne.getCartesJoueur1();
        List<Carte> cartesA = borne.getCartesJoueur2();

        // Si le joueur n'a pas joué 3 cartes
        if (cartesJ.size() != 3) {
            return false;
        }

        // On évalue les combinaisons du joeuur qui peut revendiquer
        int scoreJ = evaluerCombinaison(cartesJ);

        if (cartesA.size() == 3) {
            // On évalue les combinaisons de l'adversaire
            int scoreA = evaluerCombinaison(cartesA);
            return scoreJ > scoreA;
        }

        // On récupère toutes les cartes que l'adversaire peut avoir
        int cartesManquantes = 3 - cartesA.size();
        List<Carte> cartesPossiblesAdversaire = new ArrayList<>(adversaire.getCartes());
        cartesPossiblesAdversaire.addAll(cartesNonDistribuees);

        // On récupère toutes les combinaisons possibles de l'adversaire
        List<List<Carte>> combinaisons = genererCombinaisons(cartesPossiblesAdversaire, cartesManquantes);

        // On évalue les combinaisons possibles de l'adversaire
        for (List<Carte> combinaison : combinaisons) {
            combinaison.addAll(cartesA);
            int scoreA = evaluerCombinaison(combinaison);
            if (scoreJ <= scoreA) {
                return false;
            }
        }
        System.out.println("Le joueur " + joueur.getNom() + " peut revendiquer la borne " + borne.getId());
        return true;
    }

    /** Générer toutes les combinaisons possibles de cartes.
     * @param cartes les cartes à combiner
     * @param n le nombre de cartes à combiner
     * @return la liste des combinaisons */
    public static List<List<Carte>> genererCombinaisons(List<Carte> cartes, int n) {
        List<List<Carte>> combinaisons = new ArrayList<>();
        genererCombinaisonsRecursif(cartes, n, 0, new ArrayList<>(), combinaisons);
        return combinaisons;
    }

    /** Générer toutes les combinaisons possibles de cartes.
     * @param cartes les cartes à combiner
     * @param n le nombre de cartes à combiner
     * @param index l'index de la carte
     * @param combinaison la combinaison actuelle
     * @param combinaisons la liste des combinaisons */
    public static void genererCombinaisonsRecursif(List<Carte> cartes, int n, int index, List<Carte> combinaison, List<List<Carte>> combinaisons) {
        if (combinaison.size() == n) {
            combinaisons.add(new ArrayList<>(combinaison));
            return;
        }

        for (int i = index; i < cartes.size(); i++) {
            combinaison.add(cartes.get(i));
            genererCombinaisonsRecursif(cartes, n, i + 1, combinaison, combinaisons);
            combinaison.remove(combinaison.size() - 1);
        }
    }
}


            
            
        
