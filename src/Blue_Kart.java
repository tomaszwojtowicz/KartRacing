import javax.swing.*;
import java.awt.*;

public class Blue_Kart extends Kart {

    // Set up the starting position and orientation of the kart
    public static final Point BLUE_KART_STARTING_POINT = new Point(Resources.RACETRACK.getIconWidth() / 2 - 50, Resources.RACETRACK.getIconHeight() - 60);
    public static final byte BLUE_KART_STARTING_IMAGE = 4;

    public Blue_Kart() {

        this.location = BLUE_KART_STARTING_POINT;

        // This will ensure tight-fitting kart's boundary
        update_boundary();

        // Populate the array with kart's images
        populate_image_array();

        // Set the starting image/orientation
        this.current_image = this.images[BLUE_KART_STARTING_IMAGE];
        this.orientation = BLUE_KART_STARTING_IMAGE;
    }

    // This method will iterate over the image array, populating the elements
    // from relevant files
    protected void populate_image_array() {
        // Initialise array which will hold kart's images
        this.images = new ImageIcon[IMAGES_PER_KART];

        for (int i = 0; i < this.images.length; i++) {
            // Wrapping it in try/catch statement in case file is not present/corrupted
            try {
                this.images[i] = new ImageIcon("Resources/Graphics/Kart_blue/kart_blue_" + i + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
