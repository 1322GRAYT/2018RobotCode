package org.usfirst.frc.team1322.robot.calibrations;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: K_LiftCal - Contains the Control Calibrations for the
 * the Lift SubSystem.
 */
public class K_LiftCal extends Subsystem {

	
	/** KLFT_r_LiftMtrHldPwr: Normalized Power Command to Hold/Return
	 * the Lift Motor to the Middle or High position once it drifts
	 * down due to the weight of the Arm and PwrCube that may be present.
	 * (double: normalized power). */
	public static final double KLFT_r_LiftMtrHldPwr = 0.10; // Norm Pwr
	
	
	  
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

