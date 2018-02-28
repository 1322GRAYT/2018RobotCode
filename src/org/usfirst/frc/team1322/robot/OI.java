/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_RaiseToMid;
import org.usfirst.frc.team1322.robot.commands.BM_EngageJammer;
import org.usfirst.frc.team1322.robot.commands.BM_LiftClaw;
import org.usfirst.frc.team1322.robot.commands.BM_OpenClaw;
import org.usfirst.frc.team1322.robot.commands.BM_ShiftLift;
import org.usfirst.frc.team1322.robot.commands.TC_RunWheelsInOut;
import org.usfirst.frc.team1322.robot.commands.TC_RunWheelsRotate;
import org.usfirst.frc.team1322.robot.triggers.*;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * This is also the class that gets blamed for a lot of things breaking, although it usually isn't the problema
 */
public class OI {
	//Create Joysticks
	public final static XboxController DriverStick = new XboxController(RobotMap.USB_Driver),
			  						   AuxStick    = new XboxController(RobotMap.USB_AUX);
	//Create Buttons
	private Button
					auxRightBumper,
					auxA,
					auxY,
					auxStart,
					auxSelect;
	
	//Create Triggers
	private DpadDown auxDpadDown;
	private DpadUp  auxDpadUp;
	private LeftStickDown auxLeftStickDown;
	private LeftStickUp auxLeftStickUp;
	private LeftStickLeft auxLeftStickLeft;
	private LeftStickRight auxLeftStickRight;
	private RTrigger auxRTrigger;
	private LTrigger auxLTrigger;
	
	public OI(){		
		//Assign Buttons
		auxRightBumper = 	new JoystickButton(AuxStick,6);	 //RightBumper
		auxA = 				new JoystickButton(AuxStick, 1); //A
		auxY = 				new JoystickButton(AuxStick, 4); //Y
		auxStart = 			new JoystickButton(AuxStick, 8); //Start
		auxSelect = 		new JoystickButton(AuxStick, 7); //Select
		auxDpadUp = 			new DpadUp();				 //Dpad Up
		auxDpadDown = 			new DpadDown();				 //Dpad Down
		auxLeftStickDown = 	new LeftStickDown();			 //Left Stick Down
		auxLeftStickUp = 		new LeftStickUp();			 //Left Stick Up
		auxLeftStickLeft = 	new LeftStickLeft();			 //Left Stick Right
		auxLeftStickRight = 	new LeftStickRight();		 //Left Stick Left
		auxRTrigger = 			new RTrigger();				 //Right Trigger
		auxLTrigger = 			new LTrigger();				 //Left Trigger
		
		//Assign Actions
		auxDpadUp.whenActive(new BM_ShiftLift(true)); 					//Shift Lift high gear
		auxDpadDown.whenActive(new BM_ShiftLift(false));				//Shift lift low gear
		auxLeftStickDown.whileActive(new TC_RunWheelsInOut(true)); 		//Run Block Out
		auxLeftStickUp.whileActive(new TC_RunWheelsInOut(false));  		//Run Block In
		auxLeftStickLeft.whileActive(new TC_RunWheelsRotate(true));		//Rotate Block 
		auxLeftStickRight.whileActive(new TC_RunWheelsRotate(false));  	//Rotate Block
		auxRTrigger.toggleWhenActive(new BM_OpenClaw(true));			//Open Claw
		auxLTrigger.toggleWhenActive(new BM_OpenClaw(false));			//Close Claw
		auxA.whileActive(new BM_LiftClaw(true));						//Tilt Claw Up
		auxY.whileActive(new BM_LiftClaw(false));						//Tilt Claw Down
		auxRightBumper.toggleWhenActive(new BM_RaiseToMid());			//Jump To Mid
		auxStart.toggleWhenActive(new BM_EngageJammer(true));			//Engage Lift Jammer
		auxSelect.toggleWhenActive(new BM_EngageJammer(false));			//Disengage Lift Jammer
	}
	
	
	
	
}
