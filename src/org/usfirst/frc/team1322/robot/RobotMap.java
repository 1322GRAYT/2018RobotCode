/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	/**
	 * CAN BUS DEVICES
	 */
	public static final int RR_MECH_1 = 0;
	public static final int RR_MECH_2 = 1;
	public static final int RF_MECH_1 = 2;
	public static final int RF_MECH_2 = 3;
	public static final int LR_MECH_1 = 4;
	public static final int LR_MECH_2 = 5;
	public static final int LF_MECH_1 = 6;
	public static final int LF_MECH_2 = 7;
	public static final int LIFT_1    = 8;
	public static final int LIFT_2    = 9;
	public static final int CLAW_L    = 10;
	public static final int CLAW_R    = 11;
	
	/**
	 * COMPRESSORS
	 */
	public static final int COMPRESSOR = 0;
	
	/**
	 * SOLENOIDS
	 */
	public static final int LIFT_SHIFT_O = 1;
	public static final int LIFT_SHIFT_C = 2;
	public static final int CLAW_CLOSE_O = 3;
	public static final int CLAW_CLOSE_C = 4;
	public static final int CLAW_LIFT_O  = 5;
	public static final int CLAW_LIFT_C  = 6;
	public static final int LIFT_JAM  = 7;
	
	/**
	 * SENSORS
	 */
	//ANALOG
	public static final int REAR_US = 0;
	public static final int LEFT_US = 3;
	public static final int RIGHT_US = 2;
	
	//DIGITAL
	public static final int BLOCK_DETECTOR = 0;
	public static final int LOW_LIFT = 2;
	public static final int MID_LIFT = 3;
	public static final int HIGH_LIFT = 4;
	
	/***
	 * USB Controller
	 */
	public static final int USB_Driver = 0;
	public static final int USB_AUX = 1;
	
	/**
	 * PDP Inputs
	 */
	public static final int pdpLift2 		= 0;
	public static final int pdpLift1 		= 1;
	public static final int pdpRRDrive2 	= 2;
	public static final int pdpRRDrive1 	= 3;
	public static final int pdpRFDrive2 	= 4;
	public static final int pdpRFDrive1 	= 5;
	public static final int pdpNA1 			= 6;
	public static final int pdpNA2 			= 7;
	public static final int pdpBlockSensor  = 8;
	public static final int pdpNA3 			= 9;
	public static final int pdpLRDrive1 	= 10;
	public static final int pdpClawR 		= 11;
	public static final int pdpClawL 		= 12;
	public static final int pdpLFDrive2 	= 13;
	public static final int pdpLRDrive2 	= 14;
	public static final int pdpLFDrive1 	= 15;
	
	/**
	 * Others
	 */
	
	public static final double deadzone = 0.3;
	public static final double lowDeadzone = 0.1;
	public static final double autonDriveCorrectionSpeed = 0.3;
	//How Many degrees of give we want to give before corection is reqired
	public static final double autonDriveLeeway = 10;
	
}
