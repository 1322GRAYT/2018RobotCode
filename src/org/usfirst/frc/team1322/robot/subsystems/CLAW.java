package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.TM_VariableClaw;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Claw Subsystem
 */
public class CLAW extends Subsystem {

	//Create New Instances of required things
	private Solenoid clawPneO = new Solenoid(RobotMap.CLAW_CLOSE_O);				//Claw Close Solenoid Valve 1
	private Solenoid clawPneC = new Solenoid(RobotMap.CLAW_CLOSE_C);				//Claw Close Solenoid Valve 2
	private Solenoid clawLiftO = new Solenoid(RobotMap.CLAW_LIFT_O);				//Claw Lift Solenoid Valve 1
	private Solenoid clawLiftC = new Solenoid(RobotMap.CLAW_LIFT_C);				//Claw Lift Solenoid Valve 2

    public CLAW() {
    	
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
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());    	
    }
}

