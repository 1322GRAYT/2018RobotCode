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
	public static final int LIFT_SHIFT = 1;
	public static final int CLAW_CLOSE = 2;
	
	/**
	 * SENSORS
	 */
	//ANALOG
	
	//DIGITAL
	
	/***
	 * USB Controller
	 */
	public static final int USB_Driver = 0;
	public static final int USB_AUX = 1;
	
}
