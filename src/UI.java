import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class UI extends JFrame implements KeyListener {

    // Define the game's difficulty level in terms of slow down factor being applied to kart's speed vector
    // EASY - 10
    // NORMAL - 5
    // HARD - 1 (no slowdown)
    public static final Difficulty_level DIFFICULTY_LEVEL = Difficulty_level.NORMAL;

    // Declare the main game panel
    public static UI_Panel ui_panel;

    // Declare the splash screen panel
    public static JPanel splash_screen;

    public UI() {

        show_splash_screen();

        // Initialising panel before anything else, so the racetrack.size will be available
        // by the time we need it
        ui_panel = new UI_Panel();
        ui_panel.setBounds(0, 0, ui_panel.racetrack.size.x, ui_panel.racetrack.size.y + ui_panel.quit_btn.getHeight() + 30);

        // Set up application window's title
        this.setTitle("Kart Racing Game");

        // Set up application window's dimensions, based on the racetrack's size
        this.setSize(ui_panel.racetrack.size.x, ui_panel.racetrack.size.y + ui_panel.quit_btn.getHeight() + 30);

        // Display the application window in the screen's centre
        this.setLocationRelativeTo(null);

        // Prevent user from resizing the application's window
        this.setResizable(false);

        // Define what to do on closing of application's window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content_pane = getContentPane();

        // Do not use layout manager - I rather work with absolute positioning
        content_pane.setLayout(null);

        // Add the Content Pane
        content_pane.add(ui_panel);

        // Set the visibility of the game panel based on that of splash screen
        ui_panel.setVisible(!splash_screen.isVisible());

        // Add key listener to the application's window and the game panel
        // If both Components are listening, the user will not be able to accidentally unfocus the Component
        // and loose control of the cart
        this.addKeyListener(this);
        this.ui_panel.addKeyListener(this);

        setFocusable(true);

        // Prevent user from changing focused Component
        setFocusTraversalKeysEnabled(false);
    }

    public static void main(String[] args) {

        UI application_window = new UI();
        application_window.setVisible(true);

        // Play background music - MAYBE NOT - FOUND IT VERY ANNOYING
        //play_looped_audio_clip(Resources.BACKGROUND_MUSIC);
    }

    // Not currently used since forgoing background music
    // But will keep it in case I changed my mind
    static void play_looped_audio_clip(File clip) {
        // Handle exception in case audio clip is absent/corrupted
        try {
            Clip audio_clip = AudioSystem.getClip();
            audio_clip.open(AudioSystem.getAudioInputStream(clip));
            audio_clip.start();
            audio_clip.loop(-1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Plays the audio clip provided as argument
    static void play_audio_clip(File clip) {
        // Handle exceptions in case audio clip is absent/corrupted
        try {
            Clip audio_clip = AudioSystem.getClip();
            audio_clip.open(AudioSystem.getAudioInputStream(clip));
            audio_clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // KeyListener event's handler
    // Calls kart's method changing its speed / orientation in response to user's key presses
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            // Handle Blue kart's controls
            // Up
            case KeyEvent.VK_UP:
                ui_panel.blue_kart.accelerate();
                break;
            // Down
            case KeyEvent.VK_DOWN:
                ui_panel.blue_kart.brake();
                break;
            // Right
            case KeyEvent.VK_RIGHT:
                ui_panel.blue_kart.turn_right();
                break;
            // Left
            case KeyEvent.VK_LEFT:
                ui_panel.blue_kart.turn_left();
                break;

            // Handle Red kart's controls
            // Up (via W key)
            case KeyEvent.VK_W:
                ui_panel.red_kart.accelerate();
                break;
            // Down (via S key)
            case KeyEvent.VK_S:
                ui_panel.red_kart.brake();
                break;
            // Right (via D key)
            case KeyEvent.VK_D:
                ui_panel.red_kart.turn_right();
                break;
            // Left (via A key)
            case KeyEvent.VK_A:
                ui_panel.red_kart.turn_left();
                break;
        }
    }

    // Necessary to declare the two methods below due to implementing KeyListener
    // enough though they are blank
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    // Displays the splash screen with instructions and animated karts
    public void show_splash_screen() {

        splash_screen = new Splash_Screen();
        splash_screen.setBounds(10, 10, 830, 800);

        this.add(splash_screen);
    }

    // Changes the visibility of the game panel
    // while setting that of splash screen to the opposite
    public static void show_game_panel(boolean show) {
        splash_screen.setVisible(!show);
        ui_panel.setVisible(show);
    }

}
