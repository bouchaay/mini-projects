package view;

import javax.swing.*;
import controller.JeuController;
import ia.IA;
import model.Joueur;
import java.awt.*;

/**
 * ShottenTottenSwing est la classe principale pour l'interface graphique du jeu Shotten Totten.
 * Auteur : Zineb Mountich
 */
public class ShottenTottenSwing {

    /** Fenêtre principale. */
    private JFrame frame;
    /** Contrôleur du jeu. */
    private JeuController jeuController;
    /** Panel pour la main du joueur 1. */
    private static HandPanel handPanel1;
    /** Panel pour la main du joueur 2. */
    private static HandPanel handPanel2;
    /** Panel pour le centre. */
    private static CenterPanel centerPanel;
    /** Panel pour afficher les bornes revendiquées. */
    private static SidePanelBorne bornesScore;
    /** Panel pour les actions. */
    private SidePanel sidePanel;
    /** Largeur de la fenêtre. */
    private static final int WINDOW_WIDTH = 1920;
    /** Hauteur de la fenêtre. */
    private static final int WINDOW_HEIGHT = 1080;

    /**
     * Constructeur de ShottenTottenSwing.
     * @param jeuController le contrôleur du jeu
     */
    public ShottenTottenSwing(JeuController jeuController) {
    	this.jeuController = jeuController;
        jeuController.commencerPartie();
    }
    
    /**
     * Méthode principale pour lancer l'interface graphique.
     * @param args les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfigurationFrame());
    }

    /**
     * Créer et afficher l'interface graphique.
     */
    public void createAndShowGUI() {
        frame = new JFrame("Shotten Totten");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Ajout des panels personnalisés
        handPanel1 = new HandPanel(jeuController.getJoueur1().getNom(), true, jeuController.getJoueur1());
        handPanel2 = new HandPanel(jeuController.getJoueur2().getNom(), false, jeuController.getJoueur2());
        centerPanel = new CenterPanel(jeuController.getJoueur1(), jeuController.getJoueur2(), jeuController.getBornes());
        sidePanel = new SidePanel(jeuController, this);
        bornesScore = new SidePanelBorne(jeuController.getJoueur1(), jeuController.getJoueur2(), jeuController);
        mainPanel.add(handPanel1, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(handPanel2, BorderLayout.SOUTH);
        mainPanel.add(sidePanel, BorderLayout.EAST);
        mainPanel.add(bornesScore, BorderLayout.WEST);

        // Ajouter tout au frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Mettre à jour tous les panels.
     */
    public void updateAll() {
        handPanel1.miseAJourCartes(jeuController.getJoueur1());
        handPanel2.miseAJourCartes(jeuController.getJoueur2());
        centerPanel.updateSlots();
    }

    /**
     * Méthode pour recommencer une partie.
     */
    public void recommencerPartie() {
        // fermer la fenêtre actuelle et ouvrir une nouvelle
        frame.dispose();

        // Créer les joueurs
        Joueur joueur1 = null;
        Joueur joueur2 = null;
        if (jeuController.getJoueur1() instanceof IA) {
            joueur1 = new IA(1, ((IA) jeuController.getJoueur1()).getStrategy());
        } else {
            joueur1 = new Joueur(jeuController.getJoueur1().getNom(), 1, false);
        }
        
        if (jeuController.getJoueur2() instanceof IA) {
            joueur2 = new IA(2, ((IA) jeuController.getJoueur2()).getStrategy());
        } else {
            joueur2 = new Joueur(jeuController.getJoueur2().getNom(), 2, false);
        }

        JeuController jeuControllerNew = new JeuController(joueur1, joueur2, jeuController.getNbCartes(), jeuController.getVarianteTactique());
        ShottenTottenSwing shottenTottenSwing = new ShottenTottenSwing(jeuControllerNew);
        shottenTottenSwing.createAndShowGUI();
    }
}
