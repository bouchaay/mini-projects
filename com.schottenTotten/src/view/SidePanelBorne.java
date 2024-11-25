package view;

import controller.JeuController;
import model.Borne;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * SidePanelBorne est le panneau latéral qui affiche les bornes revendiquées par les joueurs.
 */
public class SidePanelBorne extends JPanel {

    /** Joueur 1. */
    private Joueur joueur1;
    /** Joueur 2. */
    private Joueur joueur2;
    /** Label pour afficher les bornes revendiquées par le joueur 1. */
    private JLabel bornesJoueur1Label;
    /** Label pour afficher les bornes revendiquées par le joueur 2. */
    private JLabel bornesJoueur2Label;
    /** Label pour afficher les bornes avec la carte "Boue". */
    private JLabel boueLabel;
    /** Label pour afficher les bornes avec la carte "Colin-Maillard". */
    private JLabel colinLabel;

    /**
     * Constructeur de la classe SidePanelBorne.
     * @param j1 Joueur 1.
     * @param j2 Joueur 2.
     * @param jeuController le contrôleur du jeu
     */
    public SidePanelBorne(Joueur j1, Joueur j2, JeuController jeuController) {
        this.joueur1 = j1;
        this.joueur2 = j2;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Labels pour afficher les bornes tactiques
        boueLabel = new JLabel("Boue : ");
        boueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(boueLabel);

        colinLabel = new JLabel("Colin-Maillard : ");
        colinLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(colinLabel);

        // Labels pour afficher les bornes revendiquées par les joueurs
        bornesJoueur1Label = new JLabel(joueur1.getNom() + " : ");
        bornesJoueur1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bornesJoueur1Label);

        bornesJoueur2Label = new JLabel(joueur2.getNom() + " : ");
        bornesJoueur2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bornesJoueur2Label);

        // Mettre à jour les bornes au début
        updateBornesRevendiquees(jeuController);

        // Écouteur pour mettre à jour les bornes revendiquées lorsque le tour change
        jeuController.getTourManager().ajouterListener(joueur -> updateBornesRevendiquees(jeuController));
    }

    /**
     * Mettre à jour les bornes revendiquées par les joueurs.
     * @param jeuController le contrôleur du jeu
     */
    private void updateBornesRevendiquees(JeuController jeuController) {
        // Récupérer toutes les bornes du plateau via le contrôleur
        List<Borne> bornes = jeuController.getBornes();

        // Récupérer les IDs des bornes ayant les cartes tactiques
        StringBuilder boueBornes = new StringBuilder();
        StringBuilder colinBornes = new StringBuilder();

        for (Borne borne : bornes) {
            if (borne.isBoueJoue()) {
                boueBornes.append(borne.getId()).append(" ");
            }
            if (borne.isColinJoue()) {
                colinBornes.append(borne.getId()).append(" ");
            }
        }

        // Mettre à jour les labels des cartes tactiques
        boueLabel.setText("Boue : " + boueBornes.toString().trim());
        colinLabel.setText("Colin-Maillard : " + colinBornes.toString().trim());

        // Mettre à jour les bornes revendiquées par chaque joueur
        List<Integer> bornesJoueur1 = joueur1.getBornesRevendiquees();
        List<Integer> bornesJoueur2 = joueur2.getBornesRevendiquees();

        StringBuilder textJoueur1 = new StringBuilder();
        for (Integer borne : bornesJoueur1) {
            textJoueur1.append(borne).append(" ");
        }
        bornesJoueur1Label.setText(joueur1.getNom() + " : " + textJoueur1.toString().trim());

        StringBuilder textJoueur2 = new StringBuilder();
        for (Integer borne : bornesJoueur2) {
            textJoueur2.append(borne).append(" ");
        }
        bornesJoueur2Label.setText(joueur2.getNom() + " : " + textJoueur2.toString().trim());
    }
}
