package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_SensorUpdate;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Claw Subsystem
 */
public class CLAW extends Subsystem {

	//Create New Instances of required things
	private TalonSRX clawL = new TalonSRX(RobotMap.CLAW_L);							//Claw Motor Left
	private TalonSRX clawR = new TalonSRX(RobotMap.CLAW_R);							//Claw Motor Right
	private Solenoid clawPneO = new Solenoid(RobotMap.CLAW_CLOSE_O);				//Claw Close Solenoid Valve 1
	private Solenoid clawPneC = new Solenoid(RobotMap.CLAW_CLOSE_C);				//Claw Close Solenoid Valve 2
	private Solenoid clawLiftO = new Solenoid(RobotMap.CLAW_LIFT_O);				//Claw Lift Solenoid Valve 1
	private Solenoid clawLiftC = new Solenoid(RobotMap.CLAW_LIFT_C);				//Claw Lift Solenoid Valve 2
	
	/**
	 * Set the in/out speed of the claw motors
	 * @param speed Speed at which the claw motors should run in/out
	 */
	public void clawSpeedInOut(double speed) {
		clawL.set(ControlMode.PercentOutput, speed);
		clawR.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Set the rotation speed of the claw motors
	 * @param speed Speed at which the claw motors should spin the block
	 */
	public void clawSpeedRotate(double speed) {
		clawL.set(ControlMode.PercentOutput, -speed);
		clawR.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Opens the claw
	 */
	public void openClaw() {
		clawPneO.set(true);
		clawPneC.set(false);
	}
	
	/**
	 * Closes the claw
	 */
	public void closeClaw() {
		clawPneO.set(false);
		clawPneC.set(true);
	}
	
	/**
	 * Tilt the claw up
	 */
	public void liftClaw() {
		clawLiftO.set(true);
		clawLiftC.set(false);
	}
	
	/**
	 * Tilt the claw down
	 */
	public void lowerClaw() {
		clawLiftO.set(false);
		clawLiftC.set(true);
	}
	
	/**
	 * Sets the default command so that it is ALWAYS running throughout teleop
	 */
    public void initDefaultCommand() {
        
    }
}

