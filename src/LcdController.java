import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.TextLCD;

/**
 * This class manages the LCD screen of the EV3 device for displaying information.
 *
 * @author hamza-okutucu
 */
public class LcdController {
	
    /**
     * The LCD screen of the EV3 device.
     */
    private static TextLCD lcdScreen;

    /**
     * Initializes the LCD screen.
     */
    static {
        Brick brick = BrickFinder.getLocal();
        lcdScreen = brick.getTextLCD(Font.getFont(0, 0, Font.SIZE_MEDIUM));
    }

    /**
     * Clears the LCD screen and refreshes the display.
     */
    public static void clearScreen() {
        lcdScreen.clear();
        lcdScreen.refresh();
    }

    /**
     * Displays a message on a specific line of the LCD screen.
     *
     * @param line     The line number where to display the message (starts at 1).
     * @param message  The message to display, possibly formatted with parameters.
     * @param params   The parameters for the formatted message.
     */
    public static void printLine(int line, String message, Object... params) {
        lcdScreen.drawString(String.format(message, params), 0, line - 1);
        lcdScreen.refresh();
    }
}