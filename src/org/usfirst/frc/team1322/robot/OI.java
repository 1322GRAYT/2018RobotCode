/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.commands.BM_JumpToMid;
import org.usfirst.frc.team1322.robot.commands.BM_LiftClaw;
import org.usfirst.frc.team1322.robot.commands.BM_OpenClaw;
import org.usfirst.frc.team1322.robot.commands.BM_ShiftLift;
import org.usfirst.frc.team1322.robot.commands.TC_RunWheelsInOut;
import org.usfirst.frc.team1322.robot.commands.TC_RunWheelsRotate;
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
	
	private Button
					jumpToMid,
					closeClaw,
					lowerClaw,
					liftClaw;
	
	private DpadDown DpadDown;
	private DpadUp  DpadUp;
	private LeftStickDown LeftStickDown;
	private LeftStickUp LeftStickUp;
	private LeftStickLeft LeftStickLeft;
	private LeftStickRight LeftStickRight;
	private RTrigger RTrigger;
	private LTrigger LTrigger;
	
	public OI(){		
		
		jumpToMid = 			new JoystickButton(AuxStick,6);//RightBumper
		closeClaw = 		new JoystickButton(AuxStick,5);//LeftBumper
		lowerClaw = 		new JoystickButton(AuxStick, 1);// A
		liftClaw = 		new JoystickButton(AuxStick, 4);// Y
		//runBlockRight = 	new JoystickButton(AuxStick, 2);// B
		//runBlockLeft = 		new JoystickButton(AuxStick, 3);// X
		DpadUp = 			new DpadUp();					//Dpad Up
		DpadDown = 			new DpadDown();					//Dpad Down
		LeftStickDown = 	new LeftStickDown();			//Left Stick Down
		LeftStickUp = 		new LeftStickUp();				//Left Stick Up
		LeftStickLeft = 	new LeftStickLeft();			//Left Stick Right
		LeftStickRight = 	new LeftStickRight();			//Left Stick Left
		RTrigger = 			new RTrigger();					//Right Trigger
		LTrigger = 			new LTrigger();					//Left Trigger
		
		//Actions
		DpadUp.whenActive(new BM_ShiftLift(true));
		DpadDown.whenActive(new BM_ShiftLift(false));
		LeftStickDown.whenActive(new TC_RunWheelsInOut(true));
		LeftStickUp.whenActive(new TC_RunWheelsInOut(false));
		LeftStickLeft.whenActive(new TC_RunWheelsRotate(true));
		LeftStickRight.whenActive(new TC_RunWheelsRotate(false));
		RTrigger.toggleWhenActive(new BM_OpenClaw(true));
		LTrigger.toggleWhenActive(new BM_OpenClaw(false));
		lowerClaw.whileActive(new BM_LiftClaw(true));
		liftClaw.whileActive(new BM_LiftClaw(false));
		jumpToMid.toggleWhenActive(new BM_JumpToMid());
		//closeClaw.toggleWhenActive(new BM_JumpToLow());
		
	}
	
	
	
	
}
