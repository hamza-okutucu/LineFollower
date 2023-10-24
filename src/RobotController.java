import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;

/**
 * This class controls the robot's hardware components, such as motors and color sensors, and provides methods
 * for motor control and sensor data retrieval.
 *
 * @author hamza-okutucu
 */
public class RobotController {
	
    /**
     * The color sensor used for detecting colors.
     */
    private static EV3ColorSensor colorSensor;
    
    /**
     * The left motor of the robot.
     */
    private static EV3LargeRegulatedMotor leftMotor;
    
    /**
     * The right motor of the robot.
     */
    private static EV3LargeRegulatedMotor rightMotor;

    /**
     * Initializes the color sensor and motors.
     */
    static {
        colorSensor = new EV3ColorSensor(SensorPort.S2);
        leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        rightMotor = new EV3LargeRegulatedMotor(MotorPort.D);
    }

    /**
     * Controls the robot's motors with specified speeds for left and right wheels.
     *
     * @param leftSpeed  The speed for the left motor.
     * @param rightSpeed The speed for the right motor.
     */
    public static void controlMotors(float leftSpeed, float rightSpeed) {
        setMotorSpeed(leftMotor, leftSpeed);
        setMotorSpeed(rightMotor, rightSpeed);
    }

    /**
     * Sets the speed and direction of a motor based on the provided speed value.
     *
     * @param motor The motor to control.
     * @param speed The motor speed (positive for forward, negative for backward).
     */
    private static void setMotorSpeed(EV3LargeRegulatedMotor motor, float speed) {
        motor.setSpeed(Math.abs(speed));

        if (speed > 0) motor.forward();
        else if (speed < 0) motor.backward();
        else motor.stop(true);
    }

    /**
     * Retrieves color data from a color sensor in red mode.
     *
     * @param log If true, displays sensor data on the LCD screen.
     * @return The red color intensity value.
     */
    public static float pollRedColorSensor(boolean log) {
        float[] redsample = new float[colorSensor.sampleSize()];
        
        colorSensor.getRedMode().fetchSample(redsample, 0);
        
        if (log) {
            LcdController.clearScreen();
            LcdController.printLine(3, "sensor: %f", redsample[0]);
        }
        
        return redsample[0];
    }

    /**
     * Retrieves color data from a color sensor in RGB mode.
     *
     * @param log If true, displays sensor data on the LCD screen.
     * @return The mean value of red, green, and blue color intensities.
     */
    public static float pollRGBColorSensor(boolean log) {
        colorSensor.setCurrentMode("RGB");

        float[] sample = new float[colorSensor.sampleSize()];

        colorSensor.setFloodlight(true);
        colorSensor.getRGBMode().fetchSample(sample, 0);

        if (log) displaySensorData(sample);

        return calculateMeanRGB(sample);
    }

    /**
     * Displays color data on the LCD screen.
     *
     * @param sample The color data to display.
     */
    private static void displaySensorData(float[] sample) {
        LcdController.clearScreen();
        LcdController.printLine(3, "R: %d", (int) (sample[0] * 255));
        LcdController.printLine(4, "G: %d", (int) (sample[1] * 255));
        LcdController.printLine(5, "B: %d", (int) (sample[2] * 255));
        LcdController.printLine(6, "Mean: %f", calculateMeanRGB(sample));
    }

    /**
     * Calculates the mean value of red, green, and blue color intensities.
     *
     * @param sample The color data to calculate the mean from.
     * @return The mean value of color intensities.
     */
    private static float calculateMeanRGB(float[] sample) {
        return (sample[0] + sample[1] + sample[2]) / 3;
    }
}