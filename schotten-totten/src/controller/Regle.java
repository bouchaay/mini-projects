package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JOptionPane;
import model.*;

/**
 * Regle est la classe qui gère les règles du jeu.
 */
public class Regle {

    /** Score entre chaque niveau. */
    private static final int SCORE_ENTRE_NIVEAU = 30;
    /** Multiplicateurs pour les différentes combinaisons. */
    private static final int SUITE_COULEUR = 5;
    private static final int BRELAN = 4;
    private static final int COULEUR = 3;
    private static final int SUITE = 2;
    private static final int SOMME = 1;
    /** Nombre de threads pour la génération des combinaisons. */
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();

    /** Vérifier si la borne est contrôlée.
     * @param borne la borne à vérifier
     * @param joueur1 le premier joueur
     * @param joueur2 le deuxième joueur
     * @param cartesNonDistribuees les cartes non distribuées */
    public static void verifierControleBorne(Borne borne, Joueur joueur1, Joueur joueur2, List<Carte> cartesNonDistribuees) {
        int borneSize = borne.getTaille();
        boolean colinJoue = borne.isColinJoue();
        List<Carte> cartesJ1 = borne.getCartesJoueur1();
        List<Carte> cartesJ2 = borne.getCartesJoueur2();

        if (cartesJ1.size() == borneSize && cartesJ2.size() == borneSize) {
            int scoreJ1 = evaluerCombinaison(cartesJ1, borneSize, colinJoue);
            int scoreJ2 = evaluerCombinaison(cartesJ2, borneSize, colinJoue);

            if (scoreJ1 > scoreJ2) {
                revendiquerBorne(borne, joueur1, joueur2);
                return;
            } else if (scoreJ1 < scoreJ2) {
                revendiquerBorne(borne, joueur2, joueur1);
                return;
            } else {
                afficherMessageEgalite(borne, joueur1, joueur2);
                return;
            }
        }

        if (peutRevendiquer(borne, joueur1, joueur2, cartesNonDistribuees)) {
            revendiquerBorne(borne, joueur1, joueur2);
        } else if (peutRevendiquer(borne, joueur2, joueur1, cartesNonDistribuees)) {
            revendiquerBorne(borne, joueur2, joueur1);
        }
    }

    /** Revendiquer une borne pour un joueur.
     * @param borne la borne à revendiquer
     * @param gagnant le joueur gagnant
     * @param perdant le joueur perdant */
    private static void revendiquerBorne(Borne borne, Joueur gagnant, Joueur perdant) {
        borne.setJoueurControle(gagnant);
        gagnant.ajouterBorneRevendiquee(borne.getId());
        borne.setControlee(true);
        System.out.println("Le joueur " + gagnant.getNom() + " a revendiqué la borne " + borne.getId());
        if (perdant.isHumain()) {
            JOptionPane.showMessageDialog(null, "Le joueur " + gagnant.getNom() + " a revendiqué la borne " + borne.getId());
        }
    }

    /** Afficher un message d'égalité.
     * @param borne la borne concernée
     * @param joueur1 le premier joueur
     * @param joueur2 le deuxième joueur */
    private static void afficherMessageEgalite(Borne borne, Joueur joueur1, Joueur joueur2) {
        System.out.println("Égalité, la borne " + borne.getId() + " n'est pas contrôlée.");
        if (joueur1.isHumain() || joueur2.isHumain()) {
            JOptionPane.showMessageDialog(null, "Égalité, la borne " + borne.getId() + " n'est pas contrôlée.");
        }
    }

    /** Évaluer la combinaison de cartes.
     * @param cartes la liste de cartes à évaluer
     * @return le score de la combinaison */
    public static int evaluerCombinaison(List<Carte> cartes, int borneSize, boolean colinJoue) {
        if (cartes.size() != borneSize) return 0;
        cartes.sort((c1, c2) -> Integer.compare(c1.getValeur(), c2.getValeur()));

        boolean memeCouleur = cartes.get(0).getCouleur().equals(cartes.get(1).getCouleur()) &&
                              cartes.get(1).getCouleur().equals(cartes.get(2).getCouleur());
        boolean consecutives = cartes.get(0).getValeur() == cartes.get(1).getValeur() - 1 &&
                               cartes.get(1).getValeur() == cartes.get(2).getValeur() - 1;
        boolean memeRang = cartes.get(0).getValeur() == cartes.get(1).getValeur() &&
                           cartes.get(1).getValeur() == cartes.get(2).getValeur();
        int valTotal = cartes.get(0).getValeur() + cartes.get(1).getValeur() + cartes.get(2).getValeur();

        if (colinJoue) {
            return valTotal;
        } else
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

    /** Vérifier si un joueur peut revendiquer une borne.
     * @param borne la borne à vérifier
     * @param joueur le joueur qui veut revendiquer
     * @param adversaire le joueur adversaire
     * @param cartesNonDistribuees les cartes non distribuées
     * @return true si le joueur peut revendiquer, false sinon */
    public static boolean peutRevendiquer(Borne borne, Joueur joueur, Joueur adversaire, List<Carte> cartesNonDistribuees) {
        int borneSize = borne.getTaille();
        boolean colinJoue = borne.isColinJoue();
        List<Carte> cartesJ = borne.getCartesJoueur1();
        List<Carte> cartesA = borne.getCartesJoueur2();
        if (cartesJ.size() != borneSize) return false;

        int scoreJ = evaluerCombinaison(cartesJ, borneSize, colinJoue);
        if (cartesA.size() == borneSize) {
            int scoreA = evaluerCombinaison(cartesA, borneSize, colinJoue);
            return scoreJ > scoreA;
        }

        int cartesManquantes = borneSize - cartesA.size();
        List<Carte> cartesPossiblesAdversaire = new ArrayList<>(adversaire.getCartes());
        cartesPossiblesAdversaire.addAll(cartesNonDistribuees);

        List<List<Carte>> combinaisons = genererCombinaisons(cartesPossiblesAdversaire, cartesManquantes);

        for (List<Carte> combinaison : combinaisons) {
            combinaison.addAll(cartesA);
            int scoreA = evaluerCombinaison(combinaison, borneSize, colinJoue);
            if (scoreJ <= scoreA) {
                return false;
            }
        }
        System.out.println("Le joueur " + joueur.getNom() + " peut revendiquer la borne " + borne.getId());
        return true;
    }

    /** Générer toutes les combinaisons possibles de cartes en parallèle.
     * @param cartes la liste de cartes
     * @param n le nombre de cartes à combiner
     * @return la liste des combinaisons */
    public static List<List<Carte>> genererCombinaisons(List<Carte> cartes, int n) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<List<List<Carte>>>> futures = new ArrayList<>();
        List<List<Carte>> result = new ArrayList<>();

        int batchSize = cartes.size() / NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            final int start = i * batchSize;
            final int end = (i == NUM_THREADS - 1) ? cartes.size() : start + batchSize;
            List<Carte> subList = cartes.subList(start, end);

            Callable<List<List<Carte>>> task = () -> genererCombinaisonsRecursif(subList, n);
            futures.add(executor.submit(task));
        }

        for (Future<List<List<Carte>>> future : futures) {
            try {
                result.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return result;
    }

    /** Méthode récursive pour générer des combinaisons.
     * @param cartes la liste de cartes
     * @param n le nombre de cartes à combiner
     * @return la liste des combinaisons */
    private static List<List<Carte>> genererCombinaisonsRecursif(List<Carte> cartes, int n) {
        List<List<Carte>> combinaisons = new ArrayList<>();
        genererCombinaisonsHelper(cartes, n, 0, new ArrayList<>(), combinaisons);
        return combinaisons;
    }

    /** Méthode auxiliaire pour générer des combinaisons.
     * @param cartes la liste de cartes
     * @param n le nombre de cartes à combiner
     * @param index l'index de départ
     * @param combinaison la combinaison actuelle
     * @param combinaisons la liste des combinaisons */
    private static void genererCombinaisonsHelper(List<Carte> cartes, int n, int index, List<Carte> combinaison, List<List<Carte>> combinaisons) {
        if (combinaison.size() == n) {
            combinaisons.add(new ArrayList<>(combinaison));
            return;
        }

        for (int i = index; i < cartes.size(); i++) {
            combinaison.add(cartes.get(i));
            genererCombinaisonsHelper(cartes, n, i + 1, combinaison, combinaisons);
            combinaison.remove(combinaison.size() - 1);
        }
    }
}
