package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.JeuController;
import ia.EasyStrategy;
import ia.HardStrategy;
import ia.IA;
import ia.IntermediateStrategy;
import ia.Strategy;
import model.Joueur;

public class ConfigurationFrame extends JFrame {
    private JComboBox<String> joueur1Type;
    private JTextField joueur1Nom;
    private JComboBox<String> iaStrategy1;

    private JComboBox<String> joueur2Type;
    private JTextField joueur2Nom;
    private JComboBox<String> iaStrategy2;

    private JButton commencerPartie;

    public ConfigurationFrame() {
        setTitle("Configuration des Joueurs");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal pour les deux grilles
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // Deux colonnes côte à côte

        // Grille pour Joueur 1
        JPanel joueur1Panel = new JPanel(new GridLayout(3, 2, 5, 5));
        joueur1Panel.setBorder(BorderFactory.createTitledBorder("Joueur 1"));

        joueur1Panel.add(new JLabel("Type du Joueur :"));
        joueur1Type = new JComboBox<>(new String[]{"Humain", "IA"});
        joueur1Panel.add(joueur1Type);

        joueur1Panel.add(new JLabel("Nom du Joueur :"));
        joueur1Nom = new JTextField("Joueur 1");
        joueur1Panel.add(joueur1Nom);

        joueur1Panel.add(new JLabel("Stratégie pour IA :"));
        iaStrategy1 = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        iaStrategy1.setEnabled(false);
        joueur1Panel.add(iaStrategy1);

        // Grille pour Joueur 2
        JPanel joueur2Panel = new JPanel(new GridLayout(3, 2, 5, 5));
        joueur2Panel.setBorder(BorderFactory.createTitledBorder("Joueur 2"));

        joueur2Panel.add(new JLabel("Type du Joueur :"));
        joueur2Type = new JComboBox<>(new String[]{"Humain", "IA"});
        joueur2Panel.add(joueur2Type);

        joueur2Panel.add(new JLabel("Nom du Joueur :"));
        joueur2Nom = new JTextField("Joueur 2");
        joueur2Panel.add(joueur2Nom);

        joueur2Panel.add(new JLabel("Stratégie pour IA :"));
        iaStrategy2 = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        iaStrategy2.setEnabled(false);
        joueur2Panel.add(iaStrategy2);

        // Ajouter les grilles au panel principal
        mainPanel.add(joueur1Panel);
        mainPanel.add(joueur2Panel);

        // Bouton "Commencer la Partie"
        commencerPartie = new JButton("Commencer la Partie");

        // Listener pour désactiver/activer les champs selon le type
        joueur1Type.addActionListener(e -> {
            boolean isIA = joueur1Type.getSelectedItem().equals("IA");
            iaStrategy1.setEnabled(isIA);
            joueur1Nom.setEnabled(!isIA);
        });

        joueur2Type.addActionListener(e -> {
            boolean isIA = joueur2Type.getSelectedItem().equals("IA");
            iaStrategy2.setEnabled(isIA);
            joueur2Nom.setEnabled(!isIA);
        });

        // Action au clic sur "Commencer la Partie"
        commencerPartie.addActionListener(e -> lancerJeu());

        // Ajouter les composants au frame
        add(mainPanel, BorderLayout.CENTER);
        add(commencerPartie, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void lancerJeu() {
    	// Configuration des joueurs
        Joueur joueur1 = null;
        Joueur joueur2 = null;
        if (joueur1Type.getSelectedItem().equals("IA")) {
            Strategy strategie1 = creerStrategie((String) iaStrategy1.getSelectedItem());
            joueur1 = new IA(1, strategie1);
        } else {
            joueur1 = new Joueur(joueur1Nom.getText(), 1, true);
        }

        if (joueur2Type.getSelectedItem().equals("IA")) {
            Strategy strategie2 = creerStrategie((String) iaStrategy2.getSelectedItem());
            joueur2 = new IA(2, strategie2);
        } else {
            joueur2 = new Joueur(joueur2Nom.getText(), 2, true);
        }

        // Créer le contrôleur de jeu avec les joueurs configurés
        JeuController jeuController = new JeuController(joueur1, joueur2);

        // Lancer l'interface principale
        new ShottenTottenSwing(jeuController).createAndShowGUI();

        // Fermer la fenêtre de configuration
        dispose();
    }

    private Strategy creerStrategie(String nomStrategie) {
        switch (nomStrategie) {
            case "Hard":
                return new HardStrategy();
            case "Medium":
                return new IntermediateStrategy();
            case "Easy":
                return new EasyStrategy();
            default:
                return new EasyStrategy();
        }
    }
}
