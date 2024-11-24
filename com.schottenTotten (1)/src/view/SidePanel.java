package view;

import controller.JeuController;
import model.Borne;
import model.Joueur;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SidePanel extends JPanel {
    private JLabel turnLabel;             // Affiche le joueur courant
    private JComboBox<Integer> borneComboBox; // Liste des bornes
    private JComboBox<Integer> carteComboBox; // Liste des cartes
    private JButton playButton;          // Bouton pour jouer une carte
    private JeuController jeuController; // Référence au contrôleur

    public SidePanel(JeuController jeuController) {
        this.jeuController = jeuController;

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
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("Choisir une borne :"));
        add(borneComboBox);

        // Ajouter un combo box pour sélectionner une carte
        carteComboBox = new JComboBox<>();
        updateCarteComboBox(jeuController.getTourManager().getJoueurCourant().getCartes()); // Initialiser avec les cartes du joueur courant
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("Choisir une carte :"));
        add(carteComboBox);

        // Ajouter le bouton pour jouer une carte
        playButton = new JButton("Jouer une carte");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.addActionListener(e -> jouerCarte());
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(playButton);

        // Ajouter un écouteur pour mettre à jour le label lorsque le tour change
        jeuController.getTourManager().ajouterListener(joueur -> {
            turnLabel.setText("Tour de : " + joueur.getNom());
            updateCarteComboBox(joueur.getCartes()); // Mettre à jour les cartes disponibles
        });
    }

    // Méthode pour mettre à jour les cartes disponibles dans le combo box
    private void updateCarteComboBox(List<model.Carte> cartes) {
        carteComboBox.removeAllItems();
        for (int i = 0; i < cartes.size(); i++) {
            carteComboBox.addItem(i + 1); // Ajouter les indices des cartes
        }
    }

    // Méthode appelée lorsque le bouton "Jouer une carte" est cliqué
    private void jouerCarte() {
        int idBorne = (int) borneComboBox.getSelectedItem(); // Récupérer la borne sélectionnée
        int idCarte = (int) carteComboBox.getSelectedItem(); // Récupérer la carte sélectionnée

        boolean tourJoue = jeuController.jouerTour(idBorne, idCarte);
        if (tourJoue) {
            JOptionPane.showMessageDialog(this, "Carte jouée sur la borne " + idBorne + " !");
        } else {
            JOptionPane.showMessageDialog(this, "Action invalide. Réessayez.");
        }
    }
}
