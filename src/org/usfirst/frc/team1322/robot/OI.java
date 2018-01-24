/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	public final static XboxController DriverStick = new XboxController(RobotMap.USB_Driver),
			  						   AuxStick    = new XboxController(RobotMap.USB_AUX);
	
	private Button ShiftLiftUp,
					ShiftLiftDown;
	
	public OI()
	{
		ShiftLiftUp = new JoystickButton(AuxStick,12);
		ShiftLiftDown = new JoystickButton(AuxStick,11);
		
		
	}
	
	
	
	
}
