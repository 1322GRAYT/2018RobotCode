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
	public static final int Lift_1 = 8;
	public static final int Lift_2 = 9;
	public static final int L_Claw = 10;
	public static final int R_Claw = 11;
	
	/**
	 * COMPRESSOR
	 */
	public static final int Compressor = 0;
	
	/**
	 * SOLENOIDS
	 */
	public static final int Lift_Shift = 1;
	public static final int Claw = 2;
	
	/**
	 * SENSORS
	 */
	//ANALOG
	
	//DIGITAL
	
	/**
	 * USB Controllers
	 */
	public static final int USB_Driver = 0;
	public static final int USB_AUX = 1;

}
