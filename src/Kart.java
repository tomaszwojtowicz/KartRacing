import javax.swing.ImageIcon;
import java.awt.*;

// The Kart class is declared as abstract, as it will never be directly instantiated
// with only instances of its descendants (Blue_Kart and Red_Kart) allowed
public abstract class Kart {

    // Define the size of the kart image, in pixels
    public static final Point KART_IMAGE_SIZE = new Point(50, 50);

    // Define the number of kart images to be used in its animation
    public static final byte IMAGES_PER_KART = 16;

    // Declare the array to store kart's images
    protected ImageIcon images[];

    // Declare the variable to store currently displayed image
    protected ImageIcon current_image;

    // Declare the variable to hold kart's size in pixels
    protected Point size;

    // Declare the polygon to represent kart's boundary
    protected Rectangle boundary;

    // Declare the variables to store kart's coordinates
    protected Point location;

    // Declare the constant to store kart's maximum speed
    protected static final byte MAX_SPEED = 100;

    // Declare the constants to store acceleration, deceleration and braking rate
    protected static final byte ACCELERATION_RATE = 10;
    protected static final byte BRAKING_RATE = 20;
    protected static final byte DECELERATION_RATE = 2;

    // Declare the variable to store kart's current speed
    protected byte speed = 0;

    //protected Point speed_vector;

    //Declare the variable to store kart's orientation
    protected byte orientation;

    // Returns kart's up-do-date location
    protected Point get_location() {
        return this.location;
    }

    // Sets the kart's location to the one provided as argument
    protected void set_location(Point new_location) {
        this.location = new_location;
    }

    // Returns kart's image in line with it;s orientation
    protected ImageIcon get_current_image() {
        return this.current_image;
    }

    // Not currently used, but will keep it in case kart images were to be pushed from the server
    protected void set_kart_image(ImageIcon[] images) {
        this.images = images;
    }

    // Increase the speed of the kart
    protected void accelerate() {
        if (this.speed <= (MAX_SPEED - ACCELERATION_RATE)) {
            this.speed = (byte) (this.speed + ACCELERATION_RATE);
        }
    }

    // Decrease the speed of the kart
    protected void brake() {
        // If the speed is 20 or greater, decrease
        if (this.speed >= BRAKING_RATE) {
            this.speed = (byte) (this.speed - BRAKING_RATE);
        }
    }

    // This method will decelerate the kart, so if no acceleration occurs,
    // the kart will come to stop eventually
    protected void decelerate() {
        if (this.speed >= DECELERATION_RATE) {
            this.speed = (byte) (this.speed - DECELERATION_RATE);
        }
    }

    // This method will turn the kart to the left
    // updating its orientation and current_image attributes
    protected void turn_left() {
        // Turn left, resetting orientation to allow continuous movement
        if (this.orientation > 0) {
            this.orientation = --this.orientation;
        } else {
            this.orientation = IMAGES_PER_KART - 1;
        }

        // Update the current image in line with current orientation
        this.current_image = this.images[this.orientation];
    }

    // This method will turn the kart to the right
    // updating its orientation and current_image attributes
    protected void turn_right() {
        // Turn right, resetting orientation to allow continuous movement
        if (this.orientation < IMAGES_PER_KART - 1) {
            this.orientation = ++this.orientation;
        } else {
            this.orientation = 0;
        }

        // Update the current image in line with current orientation
        this.current_image = this.images[this.orientation];
    }

    // Update the kart's location based on its speed vector
    // Update the kart's boundary in line with its new location
    protected void update_location() {

        Point speed_vector = get_speed_vector();

        this.location = new Point(this.location.x + speed_vector.x, this.location.y + speed_vector.y);

        update_boundary();
    }

    // Returns a speed vector, arrived at by processing kart's speed and orientation
    private Point get_speed_vector() {
        Point speed_vector = new Point();

        // Please note the values below were provided in the module's Canvas resources
        // https://canvas.anglia.ac.uk/courses/8971/files/758925/download?download_frd=1
        switch (this.orientation) {
            case 0:
                speed_vector.y = this.speed * -2;
                break;
            case 1:
                speed_vector.x = this.speed;
                speed_vector.y = this.speed * -2;
                break;
            case 2:
                speed_vector.x = this.speed * 2;
                speed_vector.y = this.speed * -2;
                break;
            case 3:
                speed_vector.x = this.speed * 2;
                speed_vector.y = -this.speed;
                break;
            case 4:
                speed_vector.x = this.speed * 2;
                break;
            case 5:
                speed_vector.x = this.speed * 2;
                speed_vector.y = this.speed;
                break;
            case 6:
                speed_vector.x = this.speed * 2;
                speed_vector.y = this.speed * 2;
                break;
            case 7:
                speed_vector.x = this.speed;
                speed_vector.y = this.speed * 2;
                break;
            case 8:
                speed_vector.y = this.speed * 2;
                break;
            case 9:
                speed_vector.x = -this.speed;
                speed_vector.y = this.speed * 2;
                break;
            case 10:
                speed_vector.x = this.speed * -2;
                speed_vector.y = this.speed * 2;
                break;
            case 11:
                speed_vector.x = this.speed * -2;
                speed_vector.y = this.speed;
                break;
            case 12:
                speed_vector.x = this.speed * -2;
                break;
            case 13:
                speed_vector.x = -this.speed;
                speed_vector.y = this.speed * -2;
                break;
            case 14:
                speed_vector.x = this.speed * -2;
                speed_vector.y = this.speed * -2;
                break;
            case 15:
                speed_vector.x = -this.speed;
                speed_vector.y = this.speed * -2;
                break;
        }

        // Adjust the speed vector as appropriate for the (currently hardcoded) difficulty level
        // The raw speed values were deemed too high, causing too much displacement on each iteration
        speed_vector.x = speed_vector.x / UI.DIFFICULTY_LEVEL.slow_down_factor;
        speed_vector.y = speed_vector.y / UI.DIFFICULTY_LEVEL.slow_down_factor;

        return speed_vector;
    }


    // Returns kart up-to-date boundary
    protected Rectangle get_boundary() {
        return this.boundary;
    }

    // This method updates the kart's boundary based on its orientation
    // The karts currently implemented are long and narrow,
    // in most orientations are nowhere near the 50x50 default image size
    // So in order to avoid excessive crashes, a tight boundary its calculated
    protected void update_boundary() {
        Point excess_padding = new Point();

        // Please note the values below are established by examining actual images used
        // and therefore will have to be updated if karts of different shapes were to be used
        switch (this.orientation) {
            case 0:
            case 8:
                excess_padding.x = 16;
                excess_padding.y = 0;
                break;
            case 1:
            case 7:
            case 9:
            case 15:
                excess_padding.x = 9;
                excess_padding.y = 1;
                break;
            case 3:
            case 5:
            case 11:
            case 13:
                excess_padding.x = 1;
                excess_padding.y = 9;
                break;
            case 4:
            case 12:
                excess_padding.x = 0;
                excess_padding.y = 16;
                break;
            case 2:
            case 6:
            case 10:
            case 14:
                excess_padding.x = 2;
                excess_padding.y = 2;
                break;
        }

        // Define a tightly fitting boundary with excess padding removed
        this.boundary = new Rectangle(this.location.x + excess_padding.x, this.location.y + excess_padding.y, KART_IMAGE_SIZE.x - (2 * excess_padding.x), KART_IMAGE_SIZE.y - (2 * excess_padding.y));
    }

    protected abstract void populate_image_array();
}
