package view;

import javax.swing.*;
import java.awt.*;
import controller.JeuController;
import ia.EasyStrategy;
import ia.HardStrategy;
import ia.IA;
import ia.IntermediateStrategy;
import ia.Strategy;
import model.Joueur;

/**
 * ConfigurationFrame est la fenêtre de configuration des joueurs.
 */
public class ConfigurationFrame extends JFrame {

    /** type de joueur 1. */
    private JComboBox<String> joueur1Type;
    /** Nom du joueur 1. */
    private JTextField joueur1Nom;
    /** Stratégie de l'IA 1. */
    private JComboBox<String> iaStrategy1;
    /** Type de joueur 2. */
    private JComboBox<String> joueur2Type;
    /** Nom du joueur 2. */
    private JTextField joueur2Nom;
    /** Stratégie de l'IA 2. */
    private JComboBox<String> iaStrategy2;
    /** Bouton pour commencer la partie. */
    private JButton commencerPartie;
    /** Variante du jeu. */
    private JComboBox<String> variante;

    /**
     * Constructeur de ConfigurationFrame.
     */
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
        SidePanel.setComboBoxSize(joueur1Type);
        joueur1Panel.add(joueur1Type);

        joueur1Panel.add(new JLabel("Nom du Joueur :"));
        joueur1Nom = new JTextField("Joueur 1");
        joueur1Panel.add(joueur1Nom);

        joueur1Panel.add(new JLabel("Stratégie pour IA :"));
        iaStrategy1 = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        SidePanel.setComboBoxSize(iaStrategy1);
        iaStrategy1.setEnabled(false);
        joueur1Panel.add(iaStrategy1);

        // Grille pour Joueur 2
        JPanel joueur2Panel = new JPanel(new GridLayout(3, 2, 5, 5));
        joueur2Panel.setBorder(BorderFactory.createTitledBorder("Joueur 2"));

        joueur2Panel.add(new JLabel("Type du Joueur :"));
        joueur2Type = new JComboBox<>(new String[]{"Humain", "IA"});
        SidePanel.setComboBoxSize(joueur2Type);
        joueur2Panel.add(joueur2Type);

        joueur2Panel.add(new JLabel("Nom du Joueur :"));
        joueur2Nom = new JTextField("Joueur 2");
        joueur2Panel.add(joueur2Nom);

        joueur2Panel.add(new JLabel("Stratégie pour IA :"));
        iaStrategy2 = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        SidePanel.setComboBoxSize(iaStrategy2);
        iaStrategy2.setEnabled(false);
        joueur2Panel.add(iaStrategy2);

        // Ajouter les grilles au panel principal
        mainPanel.add(joueur1Panel);
        mainPanel.add(joueur2Panel);

        // Variante du jeu
        JPanel variantePanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JLabel labelVariante = new JLabel("Variante du jeu :");
        variante = new JComboBox<>(new String[]{"Base", "Tactique"});
        variantePanel.add(labelVariante);
        variantePanel.add(variante);

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
        add(variantePanel, BorderLayout.NORTH);
        add(commencerPartie, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Méthode pour lancer le jeu.
     */
    private void lancerJeu() {
    	// Configuration des joueurs
        Joueur joueur1 = null;
        Joueur joueur2 = null;
        int nbCartes = variante.getSelectedItem().equals("Base") ? 6 : 7;
        boolean varianteTactique = variante.getSelectedItem().equals("Tactique");
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
        JeuController jeuController = new JeuController(joueur1, joueur2, nbCartes, varianteTactique);

        // Lancer l'interface principale
        ShottenTottenSwing mainFrame = new ShottenTottenSwing(jeuController);
        mainFrame.createAndShowGUI();
        

        // Fermer la fenêtre de configuration
        dispose();
    }

    /**
     * Méthode pour créer une stratégie.
     * @param nomStrategie Nom de la stratégie.
     * @return Strategy
     */
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
