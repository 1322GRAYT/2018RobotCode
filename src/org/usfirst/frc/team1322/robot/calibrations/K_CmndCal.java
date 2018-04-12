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
	 
	/** KCMD_t_LoopRt: Execution Loop Rate Period for the Autonomous
	 * Command Group Controls (sec). */
	 public static final float KCMD_t_LoopRt = (float)0.020; // sec 

    /** KCMD_t_FieldDataTmOut: Maximum Safety Time Out if the Field Data
      * is not being received from the Field Management System Properly.
      * will assume a drive straight command.  */
	 public static final float KCMD_t_FieldDataTmOut = (float)0.200; // sec	  
	 

	    /**************************/
		/*    Drive Motor Control    */
		/**************************/	 
	
	/** KCMD_t_PostMoveDly: Time Delay Movement prior to Starting another
	  * Drive or Rotate Movement to allow the robot and sensors/encoders
	  * to stabilize when critical, e.g. stable encoder reading. (sec). */
	 public static final float KCMD_t_PostMoveDly = (float)0.250; // sec
	
	/** KCMD_t_EncdrRstDly: Time Delay after Encoder Counter Reset to
	  * allow for CAN latency of the request . (sec). */
	 public static final double KCMD_t_EncdrRstDly = 0.250; // sec
	 
	/** KCMD_t_RotSafetyTmOut: Min Wheel Motor Encoder Speed that
	 * must be maintained before the Drive by Encoder counts
	 * Safety Time-Out Timer starts counting up to time out of the
	 * Drive Command because an obstacle must of been encountered
	 * that is impeded progress. (counts/sec) */
	public static final float KCMD_v_DrvSafetyEncrSpdMin = (float)1000; // counts/sec 	 
		
    /** KCMD_t_DrvSafetyTmOut: Amount of Time that must elapse with 
     * the Drive Encoder Count Velocity below a threshold before
     * the the Drive by Encoder Counts command will time out because
     * it is not making significant progress (Hit and Obstacle and has
     * traction, give up). */
	public static final float KCMD_t_DrvSafetyTmOut = (float)1.0; // sec 
	 
	 

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
	public static final float KCMD_r_SideArcStrfPwr = (float)0.7; // percent	
	
	/** KCMD_r_SideArcRotPwrRight: Rotate Motor Normalized Power (Abs Value) for
	  * a Right Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Left, Rotate Right). */
	public static final float KCMD_r_SideArcRotPwrRight = (float)0.7; // percent

	/** KCMD_r_SideArcRotPwrLeft: Rotate Motor Normalized Power (Abs Value) for
	  * a Left Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Right, Rotate Left). */
	public static final float KCMD_r_SideArcRotPwrLeft = (float)0.7; // percent
	
	/** KCMD_r_SideArcDrvPwrRight: Drive Correction Motor Normalized Power (Signed
	  * Value) for a Right Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Left, Rotate Right). */
	public static final float KCMD_r_SideArcDrvPwrRight = (float)0.0; // percent

	/** KCMD_r_SideArcDrvPwrLeft: Drive Correction Motor Normalized Power (Signed
	  * Value) for a Left Sideways Arc for a Strafe and Rotate Maneuver
	  * (Strafe Right, Rotate Left). */
	public static final float KCMD_r_SideArcDrvPwrLeft = (float)0.0; // percent

	/** KCMD_t_SideArcSafetyTmOut: Amount of Time that must elapse before
     * a strafe and rotate command will cancel out due to taking too long to
     * reach the target angle due to some system loss. */
	public static final float KCMD_t_SideArcSafetyTmOut = (float)1.5; // sec	 
	

	/**************************/
	/*    Shooter Control     */
	/**************************/
	
	/** KCMD_t_ShootPrdOut: Amount of Time that the motors will run
	  * when shooting out the PwrCube. */
	public static final float KCMD_t_ShootPrdOut = (float)1.5; // sec	 
		
	/** KCMD_t_ShootPrdIn: Amount of Time that the motors will run
	  * when grabbing/shooting in the PwrCube. */
	public static final float KCMD_t_ShootPrdIn = (float)2.0; // sec	 
	
	

	/**************************/
	/*    Lift Control     */
	/**************************/
	
	/** KCMD_r_LiftPwrRaiseToMid: Lift Motor Power Commanded when the
	 * Raise to Mid Position Command is executing. */
	public static final float KCMD_r_LiftPwrRaiseToMid = (float)0.95; // Norm Power	 

	/** KCMD_r_LiftPwrRaiseToHigh: Lift Motor Power Commanded when the
	 * Raise to High Position Command is executing. */
	public static final float KCMD_r_LiftPwrRaiseToHigh = (float)0.8; // Norm Power	 
	
	/** KCMD_r_LiftPwrLowerToMid: Lift Motor Power Commanded when the
	 * Lower to Mid Position Command is executing. */
	public static final float KCMD_r_LiftPwrLowerToMid = (float)0.95; // Norm Power	 

	/** KCMD_r_LiftPwrLowerToLow: Lift Motor Power Commanded when the
	 * Lower to Low Position Command is executing. */
	public static final float KCMD_r_LiftPwrLowerToLow = (float)0.95; // Norm Power	 
	
	

	/**************************/
	/*    Claw Control     */
	/**************************/
	
	/** KCMD_t_ClawPrdClose: Amount of Time Duration for
	  * the pneumatic command to Close the Claw. */
	public static final float KCMD_t_ClawPrdClose = (float)0.060; // sec	 
	
	/** KCMD_t_ClawPrdOpen: Amount of Time Duration for
	  * the pneumatic command to Open the Claw. */
	public static final float KCMD_t_ClawPrdOpen = (float)0.060; // sec	 
	
	/** KCMD_t_ClawPrdTiltDwn: Amount of Time Duration for
	  * the pneumatic command to Tilt Down the Claw. */
	public static final float KCMD_t_ClawPrdTiltDwn = (float)0.060; // sec	 
	
	/** KCMD_t_ClawPrdTiltUp: Amount of Time Duration for
	  * the pneumatic command to Tilt Up the Claw. */
	public static final float KCMD_t_ClawPrdTiltUp = (float)0.060; // sec	 
		
	
	
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

