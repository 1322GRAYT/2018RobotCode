package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;
import org.usfirst.frc.team1322.robot.commands.TC_Drive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drive Subsystem
 */
public class DRIVE extends Subsystem {

	private TalonSRX lF_Drive_1= new TalonSRX(RobotMap.LF_MECH_1);
	private TalonSRX lF_Drive_2= new TalonSRX(RobotMap.LF_MECH_2);
	private TalonSRX rF_Drive_1= new TalonSRX(RobotMap.RF_MECH_1);
	private TalonSRX rF_Drive_2= new TalonSRX(RobotMap.RF_MECH_2);
	private TalonSRX lR_Drive_1= new TalonSRX(RobotMap.LR_MECH_1);
	private TalonSRX lR_Drive_2= new TalonSRX(RobotMap.LR_MECH_2);
	private TalonSRX rR_Drive_1= new TalonSRX(RobotMap.RR_MECH_1);
	private TalonSRX rR_Drive_2= new TalonSRX(RobotMap.RR_MECH_2);
    private static final int NUMBER_OF_MOTORS = 4; 
    private final int LEFT_FRONT = 0; 
    private final int RIGHT_FRONT = 1; 
    private final int LEFT_REAR = 2; 
    private final int RIGHT_REAR = 3; 
    private final double OUTPUT_SCALE_FACTOR = 1.0; 


    /**
     * Cartesian drive method that specifies speeds in terms of the field longitudinal and lateral directions, using the drive's 
     * angle sensor to automatically determine the robot's orientation relative to the field. 
     * <p> 
     * Using this method, the robot will move away from the drivers when the joystick is pushed forwards, and towards the 
     * drivers when it is pulled towards them - regardless of what direction the robot is facing. 
     * 
     * @param x The speed that the robot should drive in the X direction. [-1.0..1.0] 
     * @param y The speed that the robot should drive in the Y direction. This input is inverted to match the forward == -1.0 
     *        that joysticks produce. [-1.0..1.0] 
     * @param rotation The rate of rotation for the robot that is completely independent of the translation. [-1.0..1.0] 
     */ 
    public void mechDrive(double x, double y, double rotation) { 
        double xIn = dzify(x); 
        double yIn = dzify(y); 
        rotation = dzify(rotation);
        // Negate y for the joystick. 
        yIn = -yIn; 
        // Compensate for gyro angle. 
        double rotated[] = rotateVector(xIn, yIn, 0); 
        xIn = rotated[0]; 
        yIn = rotated[1]; 
 
        double wheelSpeeds[] = new double[4]; 
        wheelSpeeds[0] = xIn + yIn + rotation; 
        wheelSpeeds[1] = -xIn + yIn - rotation; 
        wheelSpeeds[2] = -xIn + yIn + rotation; 
        wheelSpeeds[3] = xIn + yIn - rotation; 
 
        normalize(wheelSpeeds); 
        scale(wheelSpeeds, OUTPUT_SCALE_FACTOR); 
        lF_Drive_1.set(ControlMode.PercentOutput, wheelSpeeds[LEFT_FRONT]); 
        lF_Drive_2.set(ControlMode.PercentOutput, wheelSpeeds[LEFT_FRONT]); 
        lR_Drive_1.set(ControlMode.PercentOutput, wheelSpeeds[LEFT_REAR]); 
        lR_Drive_2.set(ControlMode.PercentOutput, wheelSpeeds[LEFT_REAR]); 
        rF_Drive_1.set(ControlMode.PercentOutput, wheelSpeeds[RIGHT_FRONT]); 
        rF_Drive_2.set(ControlMode.PercentOutput, wheelSpeeds[RIGHT_FRONT]); 
        rR_Drive_1.set(ControlMode.PercentOutput, wheelSpeeds[RIGHT_REAR]); 
        rR_Drive_2.set(ControlMode.PercentOutput, wheelSpeeds[RIGHT_REAR]); 
    } 
 
    /**
     * Normalize all wheel speeds if the magnitude of any wheel is greater than 1.0. 
     * @param wheelSpeeds the speed of each motor 
     */ 
    protected static void normalize(double wheelSpeeds[]) { 
        double maxMagnitude = Math.abs(wheelSpeeds[0]); 
        for (int i = 1; i < NUMBER_OF_MOTORS; i++) { 
            double temp = Math.abs(wheelSpeeds[i]); 
            if (maxMagnitude < temp) maxMagnitude = temp; 
        } 
        if (maxMagnitude > 1.0) { 
            for (int i = 0; i < NUMBER_OF_MOTORS; i++) { 
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude; 
            } 
        } 
    } 
 
    /**
     * Scale all speeds. 
     * @param wheelSpeeds the speed of each motor 
     * @param scaleFactor the scale factor to apply to the motor speeds 
     */ 
    protected static void scale(double wheelSpeeds[], double scaleFactor) { 
        for (int i = 1; i < NUMBER_OF_MOTORS; i++) { 
            wheelSpeeds[i] = wheelSpeeds[i] * scaleFactor; 
        } 
    } 
    
    /**
     * Rotate a vector in Cartesian space. 
     * @param x the x value of the vector 
     * @param y the y value of the vector 
     * @param angle the angle to rotate 
     * @return the vector of x and y values 
     */ 
    protected static double[] rotateVector(double x, double y, double angle) { 
        double angleInRadians = Math.toRadians(angle); 
        double cosA = Math.cos(angleInRadians); 
        double sinA = Math.sin(angleInRadians); 
        double out[] = new double[2]; 
        out[0] = x * cosA - y * sinA; 
        out[1] = x * sinA + y * cosA; 
        return out; 
    } 
	
    /**
     * Disable all drive motors
     */

	public void disable() {
		lF_Drive_1.set(ControlMode.Disabled, 0);
		lF_Drive_2.set(ControlMode.Disabled, 0);
		rF_Drive_1.set(ControlMode.Disabled, 0);
		rF_Drive_2.set(ControlMode.Disabled, 0);
		lR_Drive_1.set(ControlMode.Disabled, 0);
		lR_Drive_2.set(ControlMode.Disabled, 0);
		rR_Drive_1.set(ControlMode.Disabled, 0);
		rR_Drive_2.set(ControlMode.Disabled, 0);
	}
	
	/**
	 * Enables all drive motors
	 */
	public void enable() {
		lF_Drive_1.set(ControlMode.PercentOutput, 0);
		lF_Drive_2.set(ControlMode.PercentOutput, 0);
		rF_Drive_1.set(ControlMode.PercentOutput, 0);
		rF_Drive_2.set(ControlMode.PercentOutput, 0);
		lR_Drive_1.set(ControlMode.PercentOutput, 0);
		lR_Drive_2.set(ControlMode.PercentOutput, 0);
		rR_Drive_1.set(ControlMode.PercentOutput, 0);
		rR_Drive_2.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * 	Deadzonifies a double to the set deadzone in RobotMap
	 * @param A double between -1 and 1
	 * @return A double that is now been deadzoned
	 */
	private double dzify(double value) {
		double deadzone = RobotMap.deadzone;
		if(value > deadzone || value < -deadzone) {
			return value;
		}
		return 0.0;
	}

    /**
     * Gets the encoder values
     * @return RR, RF, LF, LR Encoder Values
     */
	public double[] getEncoders(){ 
		double[] encoders = {
				rR_Drive_1.getSensorCollection().getQuadraturePosition(), 
				rF_Drive_2.getSensorCollection().getQuadraturePosition(), 
				lF_Drive_2.getSensorCollection().getQuadraturePosition(),
				lR_Drive_1.getSensorCollection().getQuadraturePosition()
				};
		return encoders;
	}
	
	public void resetEncoders() {
		rR_Drive_1.getSensorCollection().setQuadraturePosition(0, 0);
		rF_Drive_2.getSensorCollection().setQuadraturePosition(0, 0); 
		lF_Drive_2.getSensorCollection().setQuadraturePosition(0, 0);
		lR_Drive_1.getSensorCollection().setQuadraturePosition(0, 0);
	}
	
	/**
	 * Sets the default command so that it is ALWAYS running throughout teleop
	 */
    public void initDefaultCommand() {
        setDefaultCommand(new TC_Drive());
    }
}

