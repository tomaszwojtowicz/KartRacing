import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class UI_Panel extends JPanel implements ActionListener {

    protected UI UI_frame;
    protected JButton quit_btn;

    Racetrack racetrack = new Racetrack();

    // Declare the two karts
    Kart blue_kart, red_kart;

    // Initialise the two karts and store them in the array
    // this should help reduce duplicated code when iterating over the karts
    Kart[] karts = new Kart[]{blue_kart = new Blue_Kart(), red_kart = new Red_Kart()};

    // Declaring and initialising the timer, required to refresh the screen via actionPerformed()
    Timer timer = new Timer(100, this::actionPerformed);

    // Will store flag indicating that a crash occurred
    boolean karts_crashed = false;

    public UI_Panel() {

        // Do not use layout manager - I rather work with absolute positioning
        this.setLayout(null);

        // Configure and add the Quit button
        quit_btn = new JButton("Quit");
        quit_btn.setFont(new Font("TimesRoman", Font.BOLD, 18));
        quit_btn.setBounds(320, 655, 200, 50);
        //quit_btn.setBounds((racetrack.size.x - 100) / 2, racetrack.size.y + 10, 100, 50);
        quit_btn.addActionListener(this);
        this.add(quit_btn);

        // Start the timer
        timer.start();
    }

    public void paintComponent(Graphics drawing_surface) {
        // A workaround to encompassing method being called one time too many after crash
        if (karts_crashed) return;

        // Draw the racetrack's background image
        racetrack.background.paintIcon(this, drawing_surface, 0, 0);

        // Draw the rectangle to emphasize racetrack's boundary
        drawing_surface.drawRect(0, 0, racetrack.size.x, racetrack.size.y);

        // Detect collisions
        // Check karts' location against racetrack boundaries and contain the karts within those boundaries
        for (Kart kart : karts) {

            // Declare and initialise variable to store racetrack boundary minus the size of the kart
            // it should make the code below more readable
            Point relative_boundary = new Point(racetrack.size.x - kart.get_boundary().width, racetrack.size.y - kart.get_boundary().height);

            if (kart.get_location().x < 0) {
                kart.set_location(new Point(0, kart.get_location().y));

                // Play the thud clip and slow down the offending kart
                UI_frame.play_audio_clip(Resources.THUD);
                kart.brake();
            }

            if (kart.get_location().x > relative_boundary.x) {
                kart.set_location(new Point(relative_boundary.x, kart.get_location().y));

                // Play the thud clip and slow down the offending kart
                UI_frame.play_audio_clip(Resources.THUD);
                kart.brake();
            }
            if (kart.get_location().y < 0) {
                kart.set_location(new Point(kart.get_location().x, 0));

                // Play the thud clip and slow down the offending kart
                UI_frame.play_audio_clip(Resources.THUD);
                kart.brake();

            }
            if (kart.get_location().y > relative_boundary.y) {
                kart.set_location(new Point(kart.get_location().x, relative_boundary.y));

                // Play the thud clip and slow down the offending kart
                UI_frame.play_audio_clip(Resources.THUD);
                kart.brake();
            }

            // Render the karts at their respective locations
            kart.get_current_image().paintIcon(this, drawing_surface, kart.get_location().x, kart.get_location().y);
        }


        // Detect collision between karts
        if (blue_kart.get_boundary().intersects(red_kart.get_boundary())) {

            karts_crashed = true;

            timer.stop();
            UI_frame.play_audio_clip(Resources.KART_CRASH);

            // Doesn't seem to work!
            for (Kart kart : karts) {
                Resources.EXPLOSION.paintIcon(this, drawing_surface, kart.location.x, kart.location.y);
            }

            JOptionPane.showMessageDialog(null, "You died a ball of fire", "GAME OVER!", -1);

            //UI.show_game_panel(false);

            System.exit(1);
        }

        // Detect karts' collisions with the racetrack centre boundary
        for (Kart kart : karts) {
            if (kart.boundary.intersects(racetrack.central_reservation)) {
                kart.brake();
            }
        }
    }

    // This method will be triggered on key pressed, as well as on timer's tick
    public void actionPerformed(ActionEvent e) {
        // If we got here because Quit button have been pressed, stop timer and display confirmation dialog
        if (e.getSource() == quit_btn) {

            timer.stop();

            int dialog_button = JOptionPane.YES_NO_OPTION;
            int dialog_result = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit?", dialog_button);

            if (dialog_result == 0) {
                System.exit(0);
            } else {
                timer.start();
            }
        }

        for (Kart kart : karts) {
            kart.update_location();

            // Decelerate the kart slightly (2% of maximum speed per iteration)
            // This should not be noticeable while kart are being accelerated,
            // but once acceleration is off, the kart should eventually come to full stop
            kart.decelerate();
        }

        repaint();
    }
}