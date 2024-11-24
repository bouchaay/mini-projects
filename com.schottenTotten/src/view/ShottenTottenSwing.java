package view;

import javax.swing.*;
import controller.JeuController;
import model.Joueur;
import java.awt.*;

public class ShottenTottenSwing {
    private JFrame frame;
    private JeuController jeuController;
    private static HandPanel handPanel1;
    private static HandPanel handPanel2;
    private static CenterPanel centerPanel;
    private static SidePanelBorne bornesScore;
    private SidePanel sidePanel;

    public ShottenTottenSwing(JeuController jeuController) {
    	this.jeuController = jeuController;
        jeuController.commencerPartie();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConfigurationFrame());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Shotten Totten");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

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

    public void updateAll() {
        handPanel1.miseAJourCartes(jeuController.getJoueur1());
        handPanel2.miseAJourCartes(jeuController.getJoueur2());
        centerPanel.updateSlots();
    }
}
