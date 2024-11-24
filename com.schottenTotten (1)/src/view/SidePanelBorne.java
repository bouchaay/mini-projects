package view;

import controller.JeuController;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SidePanelBorne extends JPanel {
    private Joueur joueur1;
    private Joueur joueur2;
    private JLabel bornesJoueur1Label;   // Affiche les bornes revendiquées par le joueur 1
    private JLabel bornesJoueur2Label;   // Affiche les bornes revendiquées par le joueur 2

    public SidePanelBorne(Joueur j1, Joueur j2, JeuController jeuController) {
        this.joueur1 = j1;  // Récupérer le joueur 1
        this.joueur2 = j2;  // Récupérer le joueur 2

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        bornesJoueur1Label = new JLabel(joueur1.getNom() + " : ");
        bornesJoueur1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bornesJoueur1Label);

        bornesJoueur2Label = new JLabel(joueur2.getNom() + " : ");
        bornesJoueur2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bornesJoueur2Label);

        // Mettre à jour les bornes au début
        updateBornesRevendiquees();

        // Écouteur pour mettre à jour les bornes revendiquées lorsque le tour change
        jeuController.getTourManager().ajouterListener(joueur -> updateBornesRevendiquees());
    }

    // Méthode pour mettre à jour l'affichage des bornes revendiquées
    private void updateBornesRevendiquees() {
        // Récupérer les bornes revendiquées par le joueur 1
        List<Integer> bornesJoueur1 = joueur1.getBornesRevendiquees();
        // Récupérer les bornes revendiquées par le joueur 2
        List<Integer> bornesJoueur2 = joueur2.getBornesRevendiquees();
        // Créer une chaîne de texte pour afficher les bornes revendiquées
        StringBuilder textJoueur1 = new StringBuilder();
        for (Integer borne : bornesJoueur1) {
            textJoueur1.append(borne).append(" ");
        }
        bornesJoueur1Label.setText(joueur1.getNom() + " : " + textJoueur1.toString());

        StringBuilder textJoueur2 = new StringBuilder();
        for (Integer borne : bornesJoueur2) {
            textJoueur2.append(borne).append(" ");
        }
        bornesJoueur2Label.setText(joueur2.getNom() + " : " + textJoueur2.toString());
    }
}
