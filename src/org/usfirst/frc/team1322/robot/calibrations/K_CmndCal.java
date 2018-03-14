package org.usfirst.frc.team1322.robot.calibrations;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: K_CmndCal - Contains Common Control Calibrations for the
 * the Autonomous and Tele-Operation Command and Command Groups.
 */
public class K_CmndCal extends Subsystem {

	
	/** KCMD_t_PostMoveDly: Time Delay Movement prior to Starting another
	  * Drive or Rotate Movement to allow the robot and sensors/encoders
	  * to stabilize when critical, e.g. stable encoder reading. (sec). */
	 public static final float KCMD_t_PostMoveDly = (float)0.250; // sec
	
	/** KCMD_t_EncdrRstDly: Time Delay after Encoder Counter Reset to
	  * allow for CAN latency of the request . (sec). */
	 public static final double KCMD_t_EncdrRstDly = 0.250; // sec

	 
	  
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

