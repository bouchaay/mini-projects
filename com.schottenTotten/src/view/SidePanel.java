package view;

import controller.JeuController;
import controller.Regle;
import ia.IA;
import model.Carte;
import model.CarteTactic;
import model.Joueur;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour définir un panel latéral pour les actions du joueur.
 */
public class SidePanel extends JPanel {

    /** Label pour afficher le joueur courant. */
    private JLabel turnLabel;
    /** Combo box pour sélectionner une borne. */
    private JComboBox<Integer> borneComboBox;
    /** Combo box pour sélectionner une carte. */
    private JComboBox<Integer> carteComboBox;
    /** Bouton pour jouer une carte ou pour commencer le jeu si joueur1 est une IA. */
    private JButton playButton;
    /** Référence au contrôleur. */
    private JeuController jeuController;
    /** Largeur des combo box. */
    private static final int COMBOBOX_WIDTH = 150;
    /** Hauteur des combo box. */
    private static final int COMBOBOX_HEIGHT = 30;
    /** Référence à la fenêtre principale. */
    private ShottenTottenSwing frame;
    private static final String[] COULEURS = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Violet"};

    /**
     * Constructeur de la classe SidePanel.
     * @param jeuController le contrôleur du jeu
     * @param frame la fenêtre principale
     */
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

    /**
     * Mettre à jour la combo box des cartes.
     * @param cartes la liste des cartes
     */
    private void updateCarteComboBox(List<model.Carte> cartes) {
        carteComboBox.removeAllItems();
        for (int i = 0; i < cartes.size(); i++) {
            carteComboBox.addItem(i + 1); // Ajouter les indices des cartes
        }
    }

    /**
     * Méthode pour l'action du bouton pour jouer une carte.
     */
    private void jouerCarte() {

        // Si le joueur courant est un humain, jouer son tour
        boolean carteAvecTourConcret = true;
        if (jeuController.getTourManager().getJoueurCourant().isHumain()) {
            int idBorne = (int) borneComboBox.getSelectedItem(); // Récupérer la borne sélectionnée
            int idCarte = (int) carteComboBox.getSelectedItem(); // Récupérer la carte sélectionnée
            Carte carte = jeuController.getTourManager().getJoueurCourant().getCartes().get(idCarte - 1);
            if (carte instanceof CarteTactic) {
                if (verifierCarteElite((CarteTactic) carte)) {
                    boolean tacticApplique = carteTacticPlayElite((CarteTactic) carte, idBorne);
                    if (!tacticApplique) {
                        return;
                    }
                    carteAvecTourConcret = true;
                } else if (verifierCarteCombat((CarteTactic) carte)) {
                    // appliquer la tactique et passer au joueur suivant
                    boolean tacticApplique = carteTacticPlayCombat((CarteTactic) carte, idBorne);
                    if (!tacticApplique) {
                        return;
                    }
                    carteAvecTourConcret = false;
                } else if (verifierCarteChasseur((CarteTactic) carte)) {
                    // appliquer la tactique et passer au joueur suivant
                    carteTacticPlayChasseur((CarteTactic) carte);
                    return;
                } else if (verifierCarteStratege((CarteTactic) carte)) {
                    // appliquer la tactique et passer au joueur suivant
                    boolean tacticApplique = carteTacticPlayStratege((CarteTactic) carte);
                    if (!tacticApplique) {
                        return;
                    }
                    carteAvecTourConcret = false;
                } else if (verifierCarteBanshee((CarteTactic) carte)) {
                    // appliquer la tactique et passer au joueur suivant
                    boolean tacticApplique = carteTacticPlayBanshee((CarteTactic) carte);
                    if (!tacticApplique) {
                        return;
                    }
                    carteAvecTourConcret = false;
                } else if (verifierCarteTraitre((CarteTactic) carte)) {
                    // appliquer la tactique et passer au joueur suivant
                    boolean tacticApplique = carteTacticPlayTraitre((CarteTactic) carte);
                    if (!tacticApplique) {
                        return;
                    }
                    carteAvecTourConcret = false;
                } else {
                    JOptionPane.showMessageDialog(this, "Action invalide. Réessayez.");
                    return;
                }
            }
            // Mode de jeu tactique
            boolean isTactique = jeuController.getPlateau().getVarianteTactique();
            // Demander si la pioche suivante est une carte tactique si la variante est activée
            if (isTactique) {
                int choix = JOptionPane.showConfirmDialog(this, "La pioche suivante est-elle une carte tactique ?", "Carte tactique",
                    JOptionPane.YES_NO_OPTION);
                isTactique = choix == JOptionPane.YES_OPTION;
            }

            if (carteAvecTourConcret) {
                // Jouer le tour
                jeuController.jouerTour(idBorne, idCarte, isTactique);
            } else {
                // Passer au joueur suivant
                jeuController.jouerTourNonConcret(idCarte, isTactique);
            }
            this.frame.updateAll();
            if (jeuController.verifierGagnant()) {
                // le joueur suivant est le gagnant
                messageGagnant(jeuController.getTourManager().getJoueurSuivant());
                // Demander à rejouer ou quitter
                demandeRejouer();
                return;
            }
        }

        // Si le joueur suivant est une IA, jouer son tour (Valable pour IA contre IA)
        while (!jeuController.getTourManager().getJoueurCourant().isHumain()) {
            Joueur joueurCourant = jeuController.getTourManager().getJoueurCourant();
            // Jouer le tour de l'IA
            if (joueurCourant instanceof IA) {
                ((IA) joueurCourant).jouerTour(jeuController, joueurCourant.getJoueur());
                this.frame.updateAll();
                if (jeuController.verifierGagnant()) {
                    // le joueur suivant est le gagnant car on a changé de joueur après le jeu
                    messageGagnant(jeuController.getTourManager().getJoueurSuivant());
                    // Demander à rejouer ou quitter
                    demandeRejouer();
                    return;
                }
            } else {
                System.err.println("Erreur : Le joueur courant n'est pas une IA.");
            }
        }
    }

    /**
     * Afficher un message pour le gagnant.
     * @param joueur le joueur gagnant
     */
    public void messageGagnant(Joueur joueur) {
        JOptionPane.showMessageDialog(null, "Le joueur " + joueur.getNom() + " a gagné !");
    }

    /**
     * Demander si les joueurs veulent rejouer.
     */
    public void demandeRejouer() {
        int choix = JOptionPane.showConfirmDialog(null, "Voulez-vous rejouer ?", "Fin de la partie",
                JOptionPane.YES_NO_OPTION);
        if (choix == JOptionPane.YES_OPTION) {
            this.frame.recommencerPartie(); // Recommencer la partie
        } else {
            System.exit(0); // Quitter le jeu
        }
    }

    /**
     * Appliquer une taille personnalisée pour les combo box.
     * @param comboBox la combo box
     */
    public static void setComboBoxSize(JComboBox<?> comboBox) {
        Dimension dimension = new Dimension(COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        comboBox.setPreferredSize(dimension);
        comboBox.setMaximumSize(dimension);
        comboBox.setMinimumSize(dimension);
    }

    /** Méthode pour jouer une carte tactique Elite.
     * @param carte la carte tactique
     * @param idBorne l'identifiant de la borne
     * @return true si la carte a été jouée, false sinon */
    public boolean carteTacticPlayElite(CarteTactic carte, int idBorne) {
        // Vérifier que le joueur n'a pas un jeu de carte tactique de plus que l'adversaire
        if (!elligibleCarteTactique()) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas jouer une carte tactique. Choisissez une autre carte.");
            return false;
        } else {
            // Convertir la carte en carte tactique
            CarteTactic carteTactic = (CarteTactic) carte;
            // Mettre à jour le joueur et la borne de la carte tactique
            carteTactic.setJoueur(jeuController.getTourManager().getJoueurCourant());
            carteTactic.setBorne(jeuController.getBornes().get(idBorne - 1));
            // Appliquer la tactique de la carte
            String tactic = ((CarteTactic) carte).getTactique();

            // Paramétrer la carte tactique
            if (tactic.equals("joker")) {
                carteTactic.appliquerTactiqueElite(demanderCouleur(), demanderValeur(1, 9));
                // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
                if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                    jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
                }
                return true;
            } else if (tactic.equals("espion")) {
                carteTactic.appliquerTactiqueElite(demanderCouleur(), 0);
                // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
                if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                    jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
                }
                return true;
            } else if (tactic.equals("bouclier")) {
                carteTactic.appliquerTactiqueElite(demanderCouleur(), demanderValeur(1, 3));
                // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
                if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                    jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
                }
                return true;
            }
        }
        return false;
    }

    /** Méthode pour jouer une carte tactique Combat.
     * @param carte la carte tactique
     * @param idBorne l'identifiant de la borne
     * @return true si la carte a été jouée, false sinon */
    public boolean carteTacticPlayCombat(CarteTactic carte, int idBorne) {
        // Vérifier que le joueur n'a pas un jeu de carte tactique de plus que l'adversaire
        if (!elligibleCarteTactique()) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas jouer une carte tactique. Choisissez une autre carte.");
            return false;
        } else {
            // Convertir la carte en carte tactique
            CarteTactic carteTactic = (CarteTactic) carte;
            // Mettre à jour le joueur et la borne de la carte tactique
            carteTactic.setJoueur(jeuController.getTourManager().getJoueurCourant());
            carteTactic.setBorne(jeuController.getBornes().get(idBorne - 1));
            // Appliquer la tactique de la carte
            String tactic = ((CarteTactic) carte).getTactique();

            // Paramétrer la carte tactique
            if (tactic.equals("colin") || tactic.equals("boue")) {
                carteTactic.appliquerTactiqueCombat();
                // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
                if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                    jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
                }

                // Supprimer la borne revendiquée par le joueur
                jeuController.getTourManager().getJoueurCourant().supprimerBorneRevendiquee(idBorne);

                // Revirifier la revendication de la borne
                Regle.verifierControleBorne(jeuController.getBornes().get(idBorne - 1), jeuController.getTourManager().getJoueurCourant(),
                        jeuController.getTourManager().getJoueurSuivant(), jeuController.getPlateau().getCartes());
                // passer au joueur suivant
                return true;
            }
        }
        return false;
    }

    /** Méthode pour jouer une carte tactique Chasseur.
     * @param carte la carte tactique
     * @return true si la carte a été jouée, false sinon */
    public boolean carteTacticPlayChasseur(CarteTactic carte) {
        // Vérifier que le joueur n'a pas un jeu de carte tactique de plus que l'adversaire
        if (!elligibleCarteTactique()) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas jouer une carte tactique. Choisissez une autre carte.");
            return false;
        } else {
            List<Carte> cartesPioché = new ArrayList<>();
            carte.setJoueur(jeuController.getTourManager().getJoueurCourant());
            // Demander l'id des cartes à supprimer de ses cartes
            int idCarte1 = 0;
            int idCarte2 = 0;
            idCarte1 = demanderValeurMessage(1, jeuController.getTourManager().getJoueurCourant().getCartes().size(), "la première carte à remettre dans la pioche");
            do {
                idCarte2 = demanderValeurMessage(1, jeuController.getTourManager().getJoueurCourant().getCartes().size(), "la deuxième carte à remettre dans la pioche");
            } while (idCarte1 == idCarte2);

            // Demander le nombre de carte tactique à piocher parmi les 3 cartes qu'il va prendre
            int nbCarteTactique = demanderValeurMessage(1, 3, "le nombre de carte tactique à piocher parmi les 3 cartes dont vous avez droit");

            // Piocher le nombre de carte tactique demandé
            for (int i = 0; i < nbCarteTactique; i++) {
                Carte newCarte = jeuController.getPlateau().distribuerCarteTactique();
                cartesPioché.add(newCarte);
            }

            // Piocher le reste des cartes
            for (int i = 0; i < 3 - nbCarteTactique; i++) {
                Carte newCarte = jeuController.getPlateau().distribuerCarte();
                cartesPioché.add(newCarte);
            }

            // Appliquer la strategy de la carte
            carte.appliquerTactiqueChasseur(jeuController, idCarte1, idCarte2, cartesPioché.get(0), cartesPioché.get(1), cartesPioché.get(2));

            // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
            if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
            }

            // Passer au joueur suivant
            return true;
        }
    }

    /** Méthode pour jouer une carte tactique Stratege.
     * @param carte la carte tactique
     * @return true si la carte a été jouée, false sinon */
    public boolean carteTacticPlayStratege(CarteTactic carte) {
        // Vérifier que le joueur n'a pas un jeu de carte tactique de plus que l'adversaire
        if (!elligibleCarteTactique()) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas jouer une carte tactique. Choisissez une autre carte.");
            return false;
        } else {
            // Convertir la carte en carte tactique
            CarteTactic carteTactic = (CarteTactic) carte;
            // Mettre à jour le joueur et la borne de la carte tactique
            carteTactic.setJoueur(jeuController.getTourManager().getJoueurCourant());
            
            // Demander l'id de la borne d'où il veut piocher
            boolean tourJoue = false;
            int idBorneExp = demanderValeurMessage(1, jeuController.getBornes().size(), "la borne d'où vous voulez piocher");
            int idCarte = 0;
            int idJoueur = jeuController.getTourManager().getJoueurCourant().getJoueur();
            switch (idJoueur) {
                case 1:
                    idCarte = demanderValeurMessage(1, jeuController.getBornes().get(idBorneExp-1).getCartesJoueur1().size(), "la carte que vous voulez prendre");
                    break;
                case 2:
                    idCarte = demanderValeurMessage(1, jeuController.getBornes().get(idBorneExp-1).getCartesJoueur2().size(), "la carte que vous voulez prendre");
                    break;
                default:
                    break;
            }
            // Demander S'il veut defausser la carte
            int choix = JOptionPane.showConfirmDialog(this, "Voulez-vous défausser la carte ?", "Défausser la carte",
                JOptionPane.YES_NO_OPTION);
            boolean defausser = choix == JOptionPane.YES_OPTION;
            // Appliquer la tactique de la carte
            if (defausser) {
                tourJoue = carteTactic.appliquerTactiqueStratege(jeuController.getBornes().get(idBorneExp-1), jeuController.getBornes().get(idBorneExp-1), idCarte, true);
            } else {
                int idBorneDest = demanderValeurMessage(1, jeuController.getBornes().size(), "la borne où vous voulez mettre la carte");
                tourJoue = carteTactic.appliquerTactiqueStratege(jeuController.getBornes().get(idBorneExp-1), jeuController.getBornes().get(idBorneDest-1), idCarte, false);
                // Verifier si la borneReceptrice est controlée par le joueur
                Regle.verifierControleBorne(jeuController.getBornes().get(idBorneExp-1), jeuController.getTourManager().getJoueurCourant(),
                jeuController.getTourManager().getJoueurSuivant(), jeuController.getPlateau().getCartes());
            }

            // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
            if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
            }

            // Retourner si le tour a été joué
            return tourJoue;
        }
    }

    /** Méthode pour jouer une carte tactique Banshee.
     * @param carte la carte tactique
     * @return true si la carte a été jouée, false sinon */
    public boolean carteTacticPlayBanshee(CarteTactic carte) {
        // Vérifier que le joueur n'a pas un jeu de carte tactique de plus que l'adversaire
        if (!elligibleCarteTactique()) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas jouer une carte tactique. Choisissez une autre carte.");
            return false;
        } else {
            // Convertir la carte en carte tactique
            CarteTactic carteTactic = (CarteTactic) carte;
            // Mettre à jour le joueur et la borne de la carte tactique
            carteTactic.setJoueur(jeuController.getTourManager().getJoueurCourant());
            // Demander l'id de la borne adverse d'où il veut piocher
            int idBorneExp = demanderValeurMessage(1, jeuController.getBornes().size(), "la borne adverse d'où vous voulez piocher");
            // Demander l'id de la carte qu'il veut defausser
            int idJoueur = jeuController.getTourManager().getJoueurCourant().getJoueur();
            int idCarte = 0;
            switch (idJoueur) {
                case 1:
                    idCarte = demanderValeurMessage(1, jeuController.getBornes().get(idBorneExp-1).getCartesJoueur2().size(), "la carte que vous voulez prendre");
                    break;
                case 2:
                    idCarte = demanderValeurMessage(1, jeuController.getBornes().get(idBorneExp-1).getCartesJoueur1().size(), "la carte que vous voulez prendre");
                    break;
                default:
                    break;
            }
            // Appliquer la tactique de la carte
            boolean tourJoue = carteTactic.appliquerTactiqueBanshee(jeuController.getBornes().get(idBorneExp-1), idCarte);
            // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
            if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
            }
            return tourJoue;
        }
    }

    /** Méthode pour jouer une carte tactique Traitre.
     * @param carte la carte tactique
     * @return true si la carte a été jouée, false sinon */
    public boolean carteTacticPlayTraitre(CarteTactic carte) {
        // Vérifier que le joueur n'a pas un jeu de carte tactique de plus que l'adversaire
        if (!elligibleCarteTactique()) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas jouer une carte tactique. Choisissez une autre carte.");
            return false;
        } else {
            // Convertir la carte en carte tactique
            CarteTactic carteTactic = (CarteTactic) carte;
            // Mettre à jour le joueur et la borne de la carte tactique
            carteTactic.setJoueur(jeuController.getTourManager().getJoueurCourant());
            // Demander l'id de la borne adverse d'où il veut piocher
            int idBorneAdv = demanderValeurMessage(1, jeuController.getBornes().size(), "la borne adverse d'où vous voulez piocher");
            // Demander l'id de la carte qu'il veut defausser
            int idCarte = 0;
            int idJoueur = jeuController.getTourManager().getJoueurCourant().getJoueur();
            switch (idJoueur) {
                case 1:
                    idCarte = demanderValeurMessage(1, jeuController.getBornes().get(idBorneAdv-1).getCartesJoueur2().size(), "la carte que vous voulez prendre");
                    break;
                case 2:
                    idCarte = demanderValeurMessage(1, jeuController.getBornes().get(idBorneAdv-1).getCartesJoueur1().size(), "la carte que vous voulez prendre");
                    break;
                default:
                    break;
            }
            // Demander l'id de la borne où il veut mettre la carte
            int idBorneJoueur = demanderValeurMessage(1, jeuController.getBornes().size(), "la borne où vous voulez mettre la carte");
            // Appliquer la tactique de la carte
            boolean tourJoue = carteTactic.appliquerTactiqueTraitre(jeuController.getBornes().get(idBorneJoueur-1), jeuController.getBornes().get(idBorneAdv-1), idCarte);
            // Vérifier si la borneReceptrice est controlée par le joueur
            Regle.verifierControleBorne(jeuController.getBornes().get(idBorneJoueur-1), jeuController.getTourManager().getJoueurCourant(),
                jeuController.getTourManager().getJoueurSuivant(), jeuController.getPlateau().getCartes());
            // SI le deuxieme joueur n'est pas une IA, on augmente le nombre de carte tactique jouée
            if (!jeuController.getTourManager().getJoueurSuivant().isHumain()) {
                jeuController.getTourManager().getJoueurCourant().aJoueCarteTactique();
            }
            return tourJoue;
        }
    }


    /** Méthode pour demander une valeur entre min et max.
     * @param min la valeur minimale
     * @param max la valeur maximale
     * @return la valeur choisie */ 
    public int demanderValeur(int min, int max) {
        int valeur = 0;
        do {
            valeur = Integer.parseInt(JOptionPane.showInputDialog(this, "Choisir une valeur (" + min + "-" + max + ") :"));
        } while (valeur < min || valeur > max);
        return valeur;
    }

    /** Méthode pour demander une valeur entre min et max avec un message.
     * @param min la valeur minimale
     * @param max la valeur maximale
     * @param message le message à afficher
     * @return la valeur choisie */
    public int demanderValeurMessage(int min, int max, String message) {
        int valeur = 0;
        do {
            valeur = Integer.parseInt(JOptionPane.showInputDialog(this, "Choisir une valeur (" + min + "-" + max + ") pour " + message + " :"));
        } while (valeur < min || valeur > max);
        return valeur;
    }

    /** Méthode pour demander une couleur.
     * @return la couleur choisie */
    public String demanderCouleur() {
        return (String) JOptionPane.showInputDialog(this, "Choisir une couleur :", "Joker", JOptionPane.QUESTION_MESSAGE,
                null, COULEURS, COULEURS[0]);
    }

    /** Méthode pour vérifier si le joueur courant est éligible à jouer une carte tactique.
     * @return true si le joueur courant est éligible, false sinon */
    public boolean elligibleCarteTactique() {
        return (jeuController.getTourManager().getJoueurCourant().getNbCarteTactique()) <= (jeuController.getTourManager().getJoueurSuivant().getNbCarteTactique());
    }

    /** Méthode pour vérifier si la carte est une carte Elite.
     * @param carte la carte tactique
     * @return true si la carte est une carte Elite, false sinon */
    public boolean verifierCarteElite(CarteTactic carte) {
        return carte.getTactique().equals("joker") || carte.getTactique().equals("espion") || carte.getTactique().equals("bouclier");
    }

    /** Méthode pour vérifier si la carte est une carte Combat.
     * @param carte la carte tactique
     * @return true si la carte est une carte Combat, false sinon */
    public boolean verifierCarteCombat(CarteTactic carte) {
        return carte.getTactique().equals("colin") || carte.getTactique().equals("boue");
    }

    /** Méthode pour vérifier si la carte est une carte Chasseur.
     * @param carte la carte tactique
     * @return true si la carte est une carte Chasseur, false sinon */
    public boolean verifierCarteChasseur(CarteTactic carte) {
        return carte.getTactique().equals("chasseur");
    }

    /** Méthode pour vérifier si la carte est une carte Stratege.
     * @param carte la carte tactique
     * @return true si la carte est une carte Stratege, false sinon */
    public boolean verifierCarteStratege(CarteTactic carte) {
        return carte.getTactique().equals("stratege");
    }

    /** Méthode pour vérifier si la carte est une carte Banshee.
     * @param carte la carte tactique
     * @return true si la carte est une carte Banshee, false sinon */
    public boolean verifierCarteBanshee(CarteTactic carte) {
        return carte.getTactique().equals("banshee");
    }

    /** Méthode pour vérifier si la carte est une carte Traitre.
     * @param carte la carte tactique
     * @return true si la carte est une carte Traitre, false sinon */
    public boolean verifierCarteTraitre(CarteTactic carte) {
        return carte.getTactique().equals("traitre");
    }
}
