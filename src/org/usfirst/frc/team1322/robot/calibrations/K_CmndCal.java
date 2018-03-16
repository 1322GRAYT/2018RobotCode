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
	 public static final boolean KCMD_b_DebugEnbl = false;

    /** KCMD_t_FieldDataTmOut: Maximum Safety Time Out if the Field Data
      * is not being received from the Field Management System Properly.
      * will assume a drive straight command.  */
	 public static final float KCMD_t_FieldDataTmOut = (float)0.200; // sec	 
	 
	/** KCMD_t_PostMoveDly: Time Delay Movement prior to Starting another
	  * Drive or Rotate Movement to allow the robot and sensors/encoders
	  * to stabilize when critical, e.g. stable encoder reading. (sec). */
	 public static final float KCMD_t_PostMoveDly = (float)0.250; // sec
	
	/** KCMD_t_EncdrRstDly: Time Delay after Encoder Counter Reset to
	  * allow for CAN latency of the request . (sec). */
	 public static final double KCMD_t_EncdrRstDly = 0.250; // sec



    /**************************/
	/*    Rotation Control    */
	/**************************/	 
	 
	/** KCMD_b_RotRstGyroOnInit: Enable the the Reset the Gyro at
      * the initialization at each Turn by Gryo Step. */
	public static final boolean KCMD_b_RotRstGyroOnInit = false; 
	
	/** KCMD_t_RotSafetyTmOut: Amount of Time that must elapse before
     * a rotate command will cancel out due to taking too long to reach
     * the target angle due to some system loss. */
	public static final float KCMD_t_RotSafetyTmOut = (float)1.0; // sec 	 
		
    /** KCMD_t_RotSafetyTmOutPI: Amount of Time that must elapse before
     * a P-I Controlled rotate command will cancel out due to taking too
     * long to reach the target angle due to some system loss. */
	public static final float KCMD_t_RotSafetyTmOutPI = (float)1.5; // sec 

	
	/**************************/
	/*  Sideways Arc Control  */
	/**************************/
	
	/** KCMD_r_SideArcStrfPwr: Strafe Motor Normalized Power (Abs Value) for
	  * a Sideways Arc for a Strafe and Rotate Maneuver. */
	public static final float KCMD_r_SideArcStrfPwr = (float)0.6; // percent	
	
	/** KCMD_r_SideArcRotPwrRight: Rotate Motor Normalized Power (Abs Value) for
	  * a Right Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Left, Rotate Right). */
	public static final float KCMD_r_SideArcRotPwrRight = (float)0.6; // percent

	/** KCMD_r_SideArcRotPwrLeft: Rotate Motor Normalized Power (Abs Value) for
	  * a Left Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Right, Rotate Left). */
	public static final float KCMD_r_SideArcRotPwrLeft = (float)0.4; // percent
	
	/** KCMD_r_SideArcDrvPwrRight: Drive Correction Motor Normalized Power (Signed
	  * Value) for a Right Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Left, Rotate Right). */
	public static final float KCMD_r_SideArcDrvPwrRight = (float)0.00; // percent

	/** KCMD_r_SideArcDrvPwrLeft: Drive Correction Motor Normalized Power (Signed
	  * Value) for a Left Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Right, Rotate Left). */
	public static final float KCMD_r_SideArcDrvPwrLeft = (float)0.05; // percent

	/** KCMD_t_SideArcSafetyTmOut: Amount of Time that must elapse before
     * a strafe and rotate command will cancel out due to taking too long to
     * reach the target angle due to some system loss. */
	public static final float KCMD_t_SideArcSafetyTmOut = (float)1.5; // sec	 
	
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

