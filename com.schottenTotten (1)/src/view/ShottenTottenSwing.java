package view;

import javax.swing.*;

import controller.JeuController;
import model.Joueur;

import java.awt.*;

public class ShottenTottenSwing {
    private JFrame frame;
    private JeuController jeuController;

    public ShottenTottenSwing() {
        // Créer les joueurs
        Joueur joueur1 = new Joueur("Ayoub", 1, true);
        Joueur joueur2 = new Joueur("IA", 2, true);

        // Créer le jeu
        this.jeuController = new JeuController(joueur1, joueur2);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ShottenTottenSwing().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Shotten Totten");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Ajout des panels personnalisés
        mainPanel.add(new HandPanel(jeuController.getJoueur1().getNom(), true, jeuController.getJoueur1()), BorderLayout.NORTH);
        mainPanel.add(new CenterPanel(jeuController.getJoueur1(), jeuController.getJoueur2(), jeuController.getBornes()), BorderLayout.CENTER);
        mainPanel.add(new HandPanel(jeuController.getJoueur2().getNom(), false, jeuController.getJoueur2()), BorderLayout.SOUTH);
        mainPanel.add(new SidePanel(jeuController), BorderLayout.EAST);

        // Ajouter tout au frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
