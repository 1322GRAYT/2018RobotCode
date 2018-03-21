/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_RaiseToMid;
import org.usfirst.frc.team1322.robot.commands.BM_EngageJammer;
import org.usfirst.frc.team1322.robot.commands.BM_DisengageJammer;
import org.usfirst.frc.team1322.robot.commands.BM_LiftClaw;
import org.usfirst.frc.team1322.robot.commands.BM_OpenClaw;
import org.usfirst.frc.team1322.robot.commands.BM_ShiftLift;
import org.usfirst.frc.team1322.robot.commands.BM_StrafeAndRotate;
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
					auxLeftBumper,
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
	private DrvRTrigger drvRTrigger;
	private DrvLTrigger drvLTrigger;

	
	public OI(){		
		//Assign Buttons
		auxLeftBumper = 	new JoystickButton(AuxStick, 5); //LeftBumper
		auxRightBumper = 	new JoystickButton(AuxStick, 6); //RightBumper
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
		drvRTrigger = 			new DrvRTrigger();			 //Driver Right Trigger
		drvLTrigger = 			new DrvLTrigger();			 //Driver Left Trigger

		
		
		//Assign Actions
		auxDpadUp.whenActive(new BM_ShiftLift(true)); 					//Shift Lift high gear
		auxDpadDown.whenActive(new BM_ShiftLift(false));				//Shift lift low gear
		//auxLeftStickDown.whileActive(new TC_RunWheelsInOut(true)); 		//Run Block Out
		//auxLeftStickUp.whileActive(new TC_RunWheelsInOut(false));  		//Run Block In
		//auxLeftStickLeft.whileActive(new TC_RunWheelsRotate(true));		//Rotate Block 
		//auxLeftStickRight.whileActive(new TC_RunWheelsRotate(false));  	//Rotate Block
		auxRTrigger.whileActive(new BM_OpenClaw(false));				//Open Claw
		auxLTrigger.whileActive(new BM_OpenClaw(true));					//Close Claw
		auxA.toggleWhenActive(new BM_LiftClaw(false));					//Tilt Claw Up
		auxY.toggleWhenActive(new BM_LiftClaw(true));					//Tilt Claw Down
		auxRightBumper.toggleWhenActive(new BM_RaiseToMid());			//Jump To Mid
		auxStart.whenPressed(new BM_EngageJammer());			        //Engage Lift Jammer
		auxSelect.whenPressed(new BM_DisengageJammer());		        //Disengage Lift Jammer
		drvRTrigger.whileActive(new BM_StrafeAndRotate(false,           //Sideways Arc: Strafe Left, Rotate Right
				                                       true,
				                                       (float)90.0,
                                                       (float)K_CmndCal.KCMD_r_SideArcRotPwrRight,
                                                       (float)-(K_CmndCal.KCMD_r_SideArcStrfPwr),
                                                       (float)K_CmndCal.KCMD_r_SideArcDrvPwrRight,
                                                       false));
		drvLTrigger.whileActive(new BM_StrafeAndRotate(false,           //Sideways Arc: Strafe Right, Rotate Left
                                                       false,
                                                       (float)-90.0,
		                                               (float)K_CmndCal.KCMD_r_SideArcRotPwrLeft,
				                                       (float)K_CmndCal.KCMD_r_SideArcStrfPwr,
				                                       (float)K_CmndCal.KCMD_r_SideArcDrvPwrLeft,
                                                       false));
		
	}
	
	
}
