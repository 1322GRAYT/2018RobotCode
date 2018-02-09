package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.RobotMap;
import org.usfirst.frc.team1322.robot.commands.TC_Drive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drive Subsystem
 */
public class DRIVE extends Subsystem {

	//Create New Instances of our Motors
	private TalonSRX lF_Drive_1= new TalonSRX(RobotMap.LF_MECH_1); //Left Front Drive Front Motor
	private TalonSRX lF_Drive_2= new TalonSRX(RobotMap.LF_MECH_2); //Left Front Drive Rear Motor
	private TalonSRX rF_Drive_1= new TalonSRX(RobotMap.RF_MECH_1); //Right Front Drive Front Motor
	private TalonSRX rF_Drive_2= new TalonSRX(RobotMap.RF_MECH_2); //Right Front Drive Rear Motor
	private TalonSRX lR_Drive_1= new TalonSRX(RobotMap.LR_MECH_1); //Left Rear Drive Front Motor
	private TalonSRX lR_Drive_2= new TalonSRX(RobotMap.LR_MECH_2); //Left Rear Drive Rear Motor
	private TalonSRX rR_Drive_1= new TalonSRX(RobotMap.RR_MECH_1); //Right Rear Drive Front Motor
	private TalonSRX rR_Drive_2= new TalonSRX(RobotMap.RR_MECH_2); //Right Rear Drive Rear Motor
	

	/**
	 * Creates our own custom mechanim drive
	 * @param forwardSpeed Speed for going forward
	 * @param strafeSpeed Speed for going sideways
	 * @param turnSpeed Speed for rotating
	 */
	public void mechDrive(double forwardSpeed, double strafeSpeed, double turnSpeed) {
		//Do Math
		double r = Math.hypot(dzify(strafeSpeed), dzify(-forwardSpeed));
		double robotAngle = Math.atan2(dzify(-forwardSpeed), dzify(strafeSpeed)) - Math.PI / 4;
		final double v1 = r * Math.cos(robotAngle) + turnSpeed;
		final double v2 = r * Math.sin(robotAngle) - turnSpeed;
		final double v3 = r * Math.sin(robotAngle) + turnSpeed;
		final double v4 = r * Math.cos(robotAngle) - turnSpeed;

		//Set Motor Speeds
		lF_Drive_1.set(ControlMode.PercentOutput, v1);
		lF_Drive_2.set(ControlMode.PercentOutput, v1);
		rF_Drive_1.set(ControlMode.PercentOutput, v2);
		rF_Drive_2.set(ControlMode.PercentOutput, v2);
		lR_Drive_1.set(ControlMode.PercentOutput, v3);
		lR_Drive_2.set(ControlMode.PercentOutput, v3);
		rR_Drive_1.set(ControlMode.PercentOutput, v4);
		rR_Drive_2.set(ControlMode.PercentOutput, v4);
	}
	
	/**
	 * Disable All Motors
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
	 * Enables All Motors
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
     * @return RR, RF, LR, LF Encoder Values
     */
	public double[] getEncoders(){ 
		double[] encoders = {
				rR_Drive_1.getSensorCollection().getAnalogIn(), 
				rF_Drive_2.getSensorCollection().getAnalogIn(), 
				lF_Drive_1.getSensorCollection().getAnalogIn(),
				lR_Drive_2.getSensorCollection().getAnalogIn()
				};
		return encoders;
	}
	
	/**
	 * Sets the default command so that it is ALWAYS running throughout teleop
	 */
    public void initDefaultCommand() {
        setDefaultCommand(new TC_Drive());
    }
}

