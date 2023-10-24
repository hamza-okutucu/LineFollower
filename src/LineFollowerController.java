import lejos.hardware.Button;

import java.lang.Math;

/**
 * This class controls a line-following robot using color sensors and a PID controller.
 *
 * @author hamza-okutucu
 */
public class LineFollowerController {
	
    /**
     * An array to store line sensor readings.
     */
    private static float[] lineSensorReadings = new float[3];
    
    /**
     * An array to store background sensor readings.
     */
    private static float[] backgroundSensorReadings = new float[3];
    
    /**
     * The target value for line following control.
     */
    private static float targetValue = 0f;

    /**
     * Calibrates the color sensors by taking sensor readings for the line and background.
     */
    public static void calibrateColorSensors() {
        for (int i = 0; i < 6; i++) {
            Button.waitForAnyPress();
            if (i < 3) {
                backgroundSensorReadings[i] = RobotController.pollRGBColorSensor(true);
            } else {
                lineSensorReadings[i - 3] = RobotController.pollRGBColorSensor(true);
            }
        }
    }

    /**
     * Calculates the target value for line following control.
     */
    public static void calculateTargetValue() {
        float meanLineReading = calculateMean(lineSensorReadings);
        float meanBackgroundReading = calculateMean(backgroundSensorReadings);
        targetValue = Math.abs((meanLineReading + meanBackgroundReading) / 6);
    }

    /**
     * Starts the line following behavior after calibration.
     */
    public static void startLineFollowing() {
        calibrateColorSensors();
        calculateTargetValue();
        
        PIDController.setTargetValue(targetValue);
        
        Button.waitForAnyPress();
        
        while (true) {
            if (Button.ESCAPE.isDown()) break;
            PIDController.runControlLoop();
        }
    }

    /**
     * Calculates the mean value of an array of sensor readings.
     *
     * @param values The array of sensor readings.
     * @return The mean value of the sensor readings.
     */
    private static float calculateMean(float[] values) {
        float sum = 0;
        for (float value : values) {
            sum += value;
        }
        return sum / values.length;
    }
}