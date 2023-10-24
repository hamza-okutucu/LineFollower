/**
 * This class implements a PID (Proportional-Integral-Derivative) controller for motor speed control based on sensor readings.
 *
 * @author hamza-okutucu
 */
public class PIDController {
	
    /**
     * The proportional constant (P) for the PID controller.
     */
    private static float proportionalConstant = 1900;
    
    /**
     * The integral constant (I) for the PID controller.
     */
    private static float integralConstant = 48568;
    
    /**
     * The derivative constant (D) for the PID controller.
     */
    private static float derivativeConstant = 0.6f;
    
    /**
     * The time interval for control loop execution.
     */
    private static float deltaTime = 0.00125f;
    
    /**
     * The target speed for motor control.
     */
    private static float targetSpeed = 200;
    
    /**
     * The target value to achieve through control.
     */
    private static float targetValue = 0;
    
    /**
     * The left motor speed.
     */
    private static float leftMotorSpeed;
    
    /**
     * The right motor speed.
     */
    private static float rightMotorSpeed;
    
    /**
     * The integral term of the PID controller.
     */
    private static float integralTerm = 0;
    
    /**
     * The last error term for derivative control.
     */
    private static float lastError = 0;
    
    /**
     * The derivative term of the PID controller.
     */
    private static float derivativeTerm = 0;

    /**
     * Sets the target value to achieve through control.
     *
     * @param target The desired target value.
     */
    public static void setTargetValue(float target) {
        targetValue = target;
    }

    /**
     * Runs the control loop to adjust motor speeds based on sensor readings and the PID controller.
     */
    public static void runControlLoop() {
        float sensorReading = RobotController.pollRGBColorSensor(false);
        float error = targetValue - sensorReading;

        integralTerm += error * deltaTime;
        derivativeTerm = (error - lastError) / deltaTime;
        lastError = error;

        calculateMotorSpeeds(error);
        
        RobotController.controlMotors(leftMotorSpeed, rightMotorSpeed);
    }

    /**
     * Calculates the motor speeds based on the PID controller and error values.
     *
     * @param error The error value used for control.
     */
    private static void calculateMotorSpeeds(float error) {
        leftMotorSpeed = targetSpeed + proportionalConstant * error + integralConstant * integralTerm + derivativeConstant * derivativeTerm;
        rightMotorSpeed = targetSpeed - proportionalConstant * error + integralConstant * integralTerm + derivativeConstant * derivativeTerm;
    }
}