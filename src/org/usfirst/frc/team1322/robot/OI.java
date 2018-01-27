/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.commands.BM_LiftClaw;
import org.usfirst.frc.team1322.robot.commands.BM_OpenClaw;
import org.usfirst.frc.team1322.robot.commands.BM_ShiftLift;
import org.usfirst.frc.team1322.robot.commands.TC_RunWheels;
import org.usfirst.frc.team1322.robot.triggers.*;
import edu.wpi.first.wpilibj.GenericHID.Hand;
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
	
	private Button shiftLiftHigh,
					shiftLiftLow,
					openClaw,
					closeClaw,
					tiltClawUp,
					tiltClawDown,
					runBlockOut,
					runBlockIn;
	
	private DpadDown DpadDown;
	private DpadUp  DpadUp;
	private LeftStickDown LeftStickDown;
	private LeftStickUp LeftStickUp;
	
	public OI(){		
		
		openClaw = 			new JoystickButton(AuxStick,11);//RightBumper
		closeClaw = 		new JoystickButton(AuxStick,10);//LeftBumper
		runBlockOut = 		new JoystickButton(AuxStick, 6);// A
		runBlockIn = 		new JoystickButton(AuxStick, 9);// Y
		DpadUp = 			new DpadUp();					//Dpad Up
		DpadDown = 			new DpadDown();					//Dpad Down
		LeftStickDown = 	new LeftStickDown();			//Left Stick Down
		LeftStickUp = 		new LeftStickUp();				//Left Stick Up
		
		//Actions
		DpadUp.whenActive(new BM_ShiftLift(true));
		DpadDown.whenActive(new BM_ShiftLift(false));
		LeftStickDown.whenActive(new BM_LiftClaw(false));
		LeftStickUp.whenActive(new BM_LiftClaw(true));
		openClaw.whenPressed(new BM_OpenClaw(true));
		closeClaw.whenPressed(new BM_OpenClaw(false));
		runBlockOut.whenPressed(new TC_RunWheels(false));
		runBlockIn.whenPressed(new TC_RunWheels(true));
		
	}
	
	
	
	
}
