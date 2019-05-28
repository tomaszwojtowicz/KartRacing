import javax.swing.*;
import java.awt.*;

public class Racetrack {

    // Declare and initialise constant to hold racetrack's size
    protected static final Point size = new Point(Resources.RACETRACK.getIconWidth(), Resources.RACETRACK.getIconHeight());

    // Declare and initialise racetrack's background image
    protected static final ImageIcon background = Resources.RACETRACK;

    // Define the boundary of the racetrack central reservation area (grass)
    protected static final Rectangle central_reservation = new Rectangle(150, 150, 540, 350);
}
