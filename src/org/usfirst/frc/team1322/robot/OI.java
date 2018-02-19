/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_RaiseToMid;
import org.usfirst.frc.team1322.robot.commands.BM_LowerToLow;
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
	
	private Button  shiftLiftHigh,
					shiftLiftLow,
					openClaw,
					closeClaw,
					tiltClawUp,
					tiltClawDown,
					runBlockOut,
					runBlockIn,
					runBlockLeft,
					runBlockRight;
	
	private DpadDown DpadDown;
	private DpadUp  DpadUp;
	private LeftStickDown LeftStickDown;
	private LeftStickUp LeftStickUp;
	private RTrigger RTrigger;
	private LTrigger LTrigger;
	
	public OI(){		
		
		openClaw = 			new JoystickButton(AuxStick,6);//RightBumper
		closeClaw = 		new JoystickButton(AuxStick,5);//LeftBumper
		runBlockOut = 		new JoystickButton(AuxStick, 1);// A
		runBlockIn = 		new JoystickButton(AuxStick, 4);// Y
		runBlockRight = 	new JoystickButton(AuxStick, 2);// B
		runBlockLeft = 		new JoystickButton(AuxStick, 3);// X
		DpadUp = 			new DpadUp();					//Dpad Up
		DpadDown = 			new DpadDown();					//Dpad Down
		LeftStickDown = 	new LeftStickDown();			//Left Stick Down
		LeftStickUp = 		new LeftStickUp();				//Left Stick Up
		RTrigger = 			new RTrigger();					//Right Trigger
		LTrigger = 			new LTrigger();					//Left Trigger
		
		//Actions
		DpadUp.whenActive(new BM_ShiftLift(true));
		DpadDown.whenActive(new BM_ShiftLift(false));
		LeftStickDown.whenActive(new BM_LiftClaw(true));
		LeftStickUp.whenActive(new BM_LiftClaw(false));
		openClaw.whenPressed(new BM_OpenClaw(true));
		closeClaw.whenPressed(new BM_OpenClaw(false));
		runBlockOut.whileActive(new TC_RunWheelsInOut(false));
		runBlockIn.whileActive(new TC_RunWheelsInOut(true));
		runBlockRight.whileActive(new TC_RunWheelsRotate(true));
		runBlockLeft.whileActive(new TC_RunWheelsRotate(false));
		RTrigger.toggleWhenActive(new BM_RaiseToMid());
		//RTrigger.whileActive(new BM_LowerToLow());  TODO: Assign to Controller Trigger
		
	}
	
	
	
	
}
