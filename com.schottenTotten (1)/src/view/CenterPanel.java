package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.List;

import javax.imageio.ImageIO;
import model.*;

public class CenterPanel extends JPanel {
    private static final int CARD_WIDTH = 143;
    private static final int CARD_HEIGHT = 200;
    private static final int BORNE_WIDTH = 192;
    private static final int BORNE_HEIGHT = 108;
    private Joueur joueur1;
    private Joueur joueur2;
    private List<Borne> bornes;

    public CenterPanel(Joueur joueur1, Joueur joueur2, List<Borne> bornes) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.bornes = bornes;
        setLayout(new GridLayout(3, 9, 1, 0));

        // Ajouter zones Joueur 1
        for (int i = 0; i < 9; i++) {
            CardSlotPanel slot = new CardSlotPanel(bornes.get(i), 1);
            slot.updateSlot();
            add(slot);
        }

        // Ajouter bornes
        for (int i = 0; i < 9; i++) {
            add(createBorneLabel("image/bornes/b" + (i + 1) + ".png"));
        }

        // Ajouter zones Joueur 2
        for (int i = 0; i < 9; i++) {
            CardSlotPanel slot = new CardSlotPanel(bornes.get(i), 2);
            slot.updateSlot();
            add(slot);
        }
    }

    private JLabel createBorneLabel(String imagePath) {
        JLabel label = new JLabel();
        ImageIcon icon = loadAndResizeImage(imagePath, BORNE_WIDTH, BORNE_HEIGHT);
        if (icon != null) {
            label.setIcon(icon);
        } else {
            label.setText("Borne");
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }
        return label;
    }

    private ImageIcon loadAndResizeImage(String imagePath, int width, int height) {
        try {
            Image img = ImageIO.read(new File(imagePath));
            img = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (IOException e) {
            return null;
        }
    }

    private void fillSlotWithRandomCards(CardSlotPanel slot, String cardFolderPath) {
        File folder = new File(cardFolderPath);
        File[] cardFiles = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));

        if (cardFiles != null && cardFiles.length > 0) {
            Random random = new Random();
            for (int i = 0; i < 2; i++) { // Ajouter jusqu'à 3 cartes aléatoires
                File cardFile = cardFiles[random.nextInt(cardFiles.length)];
                ImageIcon cardIcon = loadAndResizeImage(cardFile.getPath(), CARD_WIDTH, CARD_HEIGHT);
                if (cardIcon != null) {
                    slot.addCard(cardIcon);
                }
            }
        }
    }

    public void updateSlots() {
        for (Component component : getComponents()) {
            if (component instanceof CardSlotPanel) {
                CardSlotPanel slot = (CardSlotPanel) component;
                slot.updateSlot();
            }
        }
    }
}