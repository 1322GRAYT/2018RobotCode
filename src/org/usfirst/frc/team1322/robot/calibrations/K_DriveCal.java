package org.usfirst.frc.team1322.robot.calibrations;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: K_DriveCal - Contains Common Control Calibrations for the
 * the Drive Control Group.
 */
public class K_DriveCal extends Subsystem {

	
	/** KDRV_r_StrfPwrDeltIncLimMax: Maximum about of Delta Normalized Power
	 * that is allowed per-loop to prevent Over-Loading the Motor Drivers
	 * during Strafe Motor Control (Brown-Out Prevention). */
	public static final float KDRV_r_StrfPwrDeltIncLimMax = (float)0.15; // normalized power delta per loop	
	
	/** KDRV_r_DrvPwrDeltIncLimMax: Maximum about of Delta Normalized Power
	 * that is allowed per-loop to prevent Over-Loading the Motor Drivers
	 * during Drive Motor Control (Brown-Out Prevention). */
	public static final float KDRV_r_DrvPwrDeltIncLimMax = (float)0.25; // normalized power delta per loop	

	/** KDRV_r_RotPwrDeltIncLimMax: Maximum about of Delta Normalized Power
	 * that is allowed per-loop to prevent Over-Loading the Motor Drivers
	 * during Rotate Motor Control (Brown-Out Prevention). */
	public static final float KDRV_r_RotPwrDeltIncLimMax = (float)0.25; // normalized power delta per loop	
		
	  
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

