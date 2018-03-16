package org.usfirst.frc.team1322.robot.calibrations;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: K_CmndCal - Contains Common Control Calibrations for the
 * the Autonomous and Tele-Operation Command and Command Groups.
 */
public class K_CmndCal extends Subsystem {

	
	/** KCMD_b_DebugEnbl: If True the Debug Smart Dash Display Variables
	 * Are Broadcast and Updated, if False they are turned off to maximize
	 * thru-put for controls. */
	 public static final boolean KCMD_b_DebugEnbl = true;
	
	/** KCMD_t_PostMoveDly: Time Delay Movement prior to Starting another
	  * Drive or Rotate Movement to allow the robot and sensors/encoders
	  * to stabilize when critical, e.g. stable encoder reading. (sec). */
	 public static final float KCMD_t_PostMoveDly = (float)0.250; // sec
	
	/** KCMD_t_EncdrRstDly: Time Delay after Encoder Counter Reset to
	  * allow for CAN latency of the request . (sec). */
	 public static final double KCMD_t_EncdrRstDly = 0.250; // sec

	 
	/** KCMD_b_RotRstGyroOnInit: Enable the the Reset the Gyro at
      * the initialization at each Turn by Gryo Step. */
	public static final boolean KCMD_b_RotRstGyroOnInit = false; 
	
	/** KCMD_t_RotSafetyTmOut: Amount of Time that must elapse before
     * a rotate command will cancel out due to taking too long to reach
     * the target angle due to some system loss. */
	public static final float KCMD_t_RotSafetyTmOut = (float)1.0; // sec 	 
	 
	/** KCMD_t_SideArcSafetyTmOut: Amount of Time that must elapse before
     * a strafe and rotate command will cancel out due to taking too long to
     * reach the target angle due to some system loss. */
	public static final float KCMD_t_SideArcSafetyTmOut = (float)1.5; // sec	 
	 
	/** KCMD_Pct_SideArcRot2StrfRatTight: Percentage of Rotate Motor Power
	 * Compared to Strafe Motor Power when performing a Tight Radius Sideways
	 * Arc. */
	public static final float KCMD_Pct_SideArcRot2StrfRatTight = (float)75.0; // percent

	/** KCMD_Pct_SideArcRot2StrfRatWide: Percentage of Rotate Motor Power
	 * Compared to Strafe Motor Power when performing a Wide Radius Sideways
	 * Arc. */
	public static final float KCMD_Pct_SideArcRot2StrfRatWide = (float)50.0; // percent
	
	
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

