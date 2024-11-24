package view;

import controller.JeuController;
import ia.IA;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SidePanel extends JPanel {
    private JLabel turnLabel; // Affiche le joueur courant
    private JComboBox<Integer> borneComboBox; // Liste des bornes
    private JComboBox<Integer> carteComboBox; // Liste des cartes
    private JButton playButton; // Bouton pour jouer une carte
    private JButton startButton;
    private JeuController jeuController; // Référence au contrôleur
    private static final int COMBOBOX_WIDTH = 150; // Largeur des combo box
    private static final int COMBOBOX_HEIGHT = 30; // Hauteur des combo box
    private ShottenTottenSwing frame;

    public SidePanel(JeuController jeuController, ShottenTottenSwing frame) {
        this.jeuController = jeuController;
        this.frame = frame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialiser le label pour afficher le joueur courant
        turnLabel = new JLabel("Tour de : " + jeuController.getTourManager().getJoueurCourant().getNom());
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(turnLabel);

        // Ajouter un combo box pour sélectionner une borne
        borneComboBox = new JComboBox<>();
        for (int i = 1; i <= jeuController.getBornes().size(); i++) {
            borneComboBox.addItem(i); // Ajouter les bornes disponibles
        }
        setComboBoxSize(borneComboBox); // Appliquer la taille personnalisée
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("Choisir une borne :"));
        add(borneComboBox);

        // Ajouter un combo box pour sélectionner une carte
        carteComboBox = new JComboBox<>();
        updateCarteComboBox(jeuController.getTourManager().getJoueurCourant().getCartes()); // Initialiser avec les
                                                                                            // cartes du joueur courant
        setComboBoxSize(carteComboBox); // Appliquer la taille personnalisée
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("Choisir une carte :"));
        add(carteComboBox);

        // Ajouter le bouton pour jouer une carte
        playButton = new JButton("Start/Jouer");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.addActionListener(e -> jouerCarte());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(playButton);

        // Ajouter un écouteur pour mettre à jour le label lorsque le tour change
        jeuController.getTourManager().ajouterListener(joueur -> {
            turnLabel.setText("Tour de : " + joueur.getNom());
            updateCarteComboBox(joueur.getCartes()); // Mettre à jour les cartes disponibles
            this.frame.updateAll();
        });
    }

    // Méthode pour mettre à jour les cartes disponibles dans le combo box
    private void updateCarteComboBox(List<model.Carte> cartes) {
        carteComboBox.removeAllItems();
        for (int i = 0; i < cartes.size(); i++) {
            carteComboBox.addItem(i + 1); // Ajouter les indices des cartes
        }
    }

    private void jouerCarte() {
        int idBorne = (int) borneComboBox.getSelectedItem(); // Récupérer la borne sélectionnée
        int idCarte = (int) carteComboBox.getSelectedItem(); // Récupérer la carte sélectionnée

        boolean tourJoue = jeuController.jouerTour(idBorne, idCarte);
        if (tourJoue) {
            this.frame.updateAll();
            if (jeuController.verifierGagnant()) {
                // le joueur suivant est le gagnant
                JOptionPane.showMessageDialog(this,
                        "Le joueur " + jeuController.getTourManager().getJoueurSuivant().getNom() + " a gagné !");
                return; // Arrêter si le jeu est terminé
            }
        } else {
            JOptionPane.showMessageDialog(this, "Action invalide. Réessayez.");
            return; // Arrêter en cas d'action invalide
        }

        // Si le joueur suivant est une IA, jouer son tour
        while (!jeuController.getTourManager().getJoueurCourant().isHumain()) {
            Joueur joueurCourant = jeuController.getTourManager().getJoueurCourant();

            // Vérification et casting à IA
            if (joueurCourant instanceof IA) {
                ((IA) joueurCourant).jouerTour(jeuController); // Appel de la méthode spécifique à l'IA
                this.frame.updateAll(); // Mettre à jour l'interface après le tour de l'IA
            } else {
                System.err.println("Erreur : Le joueur courant n'est pas une IA.");
            }
        }
    }


    // Méthode utilitaire pour configurer la taille des combo box
    private void setComboBoxSize(JComboBox<Integer> comboBox) {
        Dimension dimension = new Dimension(COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        comboBox.setPreferredSize(dimension);
        comboBox.setMaximumSize(dimension);
        comboBox.setMinimumSize(dimension);
    }
}
