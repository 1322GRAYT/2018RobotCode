package org.usfirst.frc.team1322.robot.calibrations;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: K_ClawCal - Contains the Control Calibrations for the
 * the Claw SubSystem.
 */
public class K_ClawCal extends Subsystem {

	  /* KCLW_t_AutoBlckGrbTmOut: The maximum amount of time to run the claw
	   * wheel motors when trying to grab/suck-in a PwrCube in Autonomous Mode
	   * (seconds). */
	  public static final float KCLW_t_AutoBlckGrbTmOut = (float) 1.0;    // seconds

	  /* KCLW_t_AutoBlckEjctTmMin:: The minimum amount of time to run the claw
	   * wheel motors when attempting to eject/throw a PwrCube in Autonomous Mode
	   * (seconds). */
	  public static final float KCLW_t_AutoBlckEjctTmMin = (float) 1.0;   // seconds
	  
	  /* KCLW_t_AutoBlckEjctTmOut: The maximum amount of time to run the claw
	   * wheel motors when trying to eject/throw a PwrCube in Autonomous Mode
	   * (seconds). */  
	  public static final float KCLW_t_AutoBlckEjctTmOut = (float) 2.5;   // seconds

	  /* KCLW_t_TeleBlckDtctCloseTrig: The minimum amount of time that the block
	   * must be detected before triggering the claw to automatically close if
	   * the claw wheels are running in during Tele-Op. (seconds). */  
	  public static final float KCLW_t_TeleBlckDtctCloseTrig = (float) 0.200;   // seconds	  

	  
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

