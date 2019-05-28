import javax.swing.ImageIcon;
import java.io.File;

public class Resources {

    // Define the audio clips, using royalty free clips from Youtube
    // Background music
    // https://www.youtube.com/audiolibrary_download?vid=8ba5e7947e540d42
    public static final  File BACKGROUND_MUSIC = new File("Resources/Audio/background_music.wav");

    // Karts crashing sound
    // https://www.youtube.com/audiolibrary_download?vid=bd01b0424f9ff7d2
    public static final File KART_CRASH = new File("Resources/Audio/crash.wav");

    // Metal scraping sound
    // https://www.youtube.com/audiolibrary_download?vid=f41470d75a3adf4a
    public static final File KART_SCRAPING = new File("Resources/Audio/metal_scraping.wav");

    // Metal thud
    // https://www.youtube.com/audiolibrary_download?vid=9c291d111532d336
    public static final File THUD = new File("Resources/Audio/thud.wav");

    // Define various graphical assets, again using free to use assets from various sources
    // Explosion image
    // https://image.ibb.co/nCbmxq/explo03.png
    public static final ImageIcon EXPLOSION = new ImageIcon("Resources/Graphics/explosion.png");

    // Racetrack image
    // https://thumbs.dreamstime.com/z/top-view-race-circuit-competition-board-69400899.jpg
    public static final ImageIcon RACETRACK = new ImageIcon("Resources/Graphics/racetrack.png");

    // Key images
    // https://cdn.shopify.com/s/files/1/1473/3902/products/8c15d41b9b829464843b5aa4449a28f3_0320e753-9385-4fb6-ae32-a92bc48c50cf_1800x1800.jpg?v=1536244691
    public static final ImageIcon KEYS_ASDW = new ImageIcon("Resources/Graphics/keys_asdw.png");
    public static final ImageIcon KEYS_ARROWS = new ImageIcon("Resources/Graphics/keys_arrows.png");
}
