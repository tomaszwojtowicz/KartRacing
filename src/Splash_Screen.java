import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Splash_Screen extends JPanel implements ActionListener {
    // Declare the two karts
    Kart blue_kart, red_kart;

    // Initialise the two karts and store them in the array
    // this should help reduce duplicated code when iterating over the karts
    Kart[] karts = new Kart[]{blue_kart = new Blue_Kart(), red_kart = new Red_Kart()};

    protected JButton start_btn;

    // Declaring and initialising the timer, required to refresh the screen via actionPerformed()
    Timer timer = new Timer(100, this::actionPerformed);

    public Splash_Screen() {

         // Play background music
        UI.play_looped_audio_clip(Resources.BACKGROUND_MUSIC);

        // Do not use layout manager - I rather work with absolute positioning
        this.setLayout(null);

        JLabel header_lbl = new JLabel("SID 1542172 presents");
        header_lbl.setFont(new Font("TimesRoman", Font.BOLD, 20));
        header_lbl.setBounds(0, 20, 840, 30);
        header_lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(header_lbl);

        JLabel game_title_lbl = new JLabel("Kart Racing Game");
        game_title_lbl.setFont(new Font("TimesRoman", Font.BOLD, 32));
        game_title_lbl.setBounds(0, 60, 840, 40);
        game_title_lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(game_title_lbl);

        JLabel instructions_1_lbl = new JLabel("Race the karts around the track using the keys shown below");
        instructions_1_lbl.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        instructions_1_lbl.setBounds(0, 200, 840, 50);
        instructions_1_lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(instructions_1_lbl);


        JLabel instructions_2_lbl = new JLabel("Avoid hitting the track's boundary, or driving on the grassy area in the centre, as this will slow you down");
        instructions_2_lbl.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        instructions_2_lbl.setBounds(0, 230, 840, 50);
        instructions_2_lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(instructions_2_lbl);

        JLabel instructions_3_lbl = new JLabel("But above all, do not crash with the other kart, unless you want to die in a ball of fire!");
        instructions_3_lbl.setFont(new Font("TimesRoman", Font.BOLD, 18));
        instructions_3_lbl.setBounds(0, 260, 840, 50);
        instructions_3_lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(instructions_3_lbl);

        // Configure and add the Start button
        start_btn = new JButton("Start the race!");
        start_btn.setFont(new Font("TimesRoman", Font.BOLD, 18));
        start_btn.setBounds(320, 600, 200, 50);
        start_btn.addActionListener(this);
        this.add(start_btn);

        // Start the timer
        timer.start();
    }

    public void paintComponent(Graphics drawing_surface) {

        // Render the karts at their respective locations
        for (Kart kart : karts) {
            Point location = new Point();

            if (kart.getClass() == Red_Kart.class) {
                location.x = 228;
                location.y = 350;
            } else {
                location.x = this.getWidth() - 255;
                location.y = 350;
            }

            kart.get_current_image().paintIcon(this, drawing_surface, location.x, location.y);
        }

        Resources.KEYS_ASDW.paintIcon(this, drawing_surface, 150, 430);
        Resources.KEYS_ARROWS.paintIcon(this, drawing_surface, 500, 430);
    }

    // This method will be triggered on on timer's tick
    public void actionPerformed(ActionEvent e) {
        // If we got here because Quit button have been pressed, stop timer and display confirmation dialog
        if (e.getSource() == start_btn) {

            // STOP THE MUSIC

            UI.show_game_panel(true);
        }

        for (Kart kart : karts) {
            if (kart.getClass() == Blue_Kart.class) {
                kart.turn_left();
            } else {
                kart.turn_right();
            }
        }

        repaint();
    }

}
