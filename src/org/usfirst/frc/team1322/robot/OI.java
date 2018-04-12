/*----------------------------------------------------------------------------*/

/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.commands.BM_LiftShiftHiGr;
import org.usfirst.frc.team1322.robot.commands.BM_LiftShiftLoGr;
import org.usfirst.frc.team1322.robot.commands.BM_LiftRaiseToMid;
import org.usfirst.frc.team1322.robot.commands.BM_JammerEngage;
import org.usfirst.frc.team1322.robot.commands.BM_JammerDisengage;
import org.usfirst.frc.team1322.robot.commands.BM_ClawTiltDown;
import org.usfirst.frc.team1322.robot.commands.BM_ClawTiltUp;
import org.usfirst.frc.team1322.robot.commands.BM_ClawClose;
import org.usfirst.frc.team1322.robot.commands.BM_ClawOpen;
import org.usfirst.frc.team1322.robot.commands.BM_StrafeAndRotate;
import org.usfirst.frc.team1322.robot.commands.BM_ShootInBlock;
import org.usfirst.frc.team1322.robot.commands.BM_ShootOutBlock;
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
	public  RTrigger auxRTrigger;
	public  LTrigger auxLTrigger;
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
		auxDpadUp = 		new DpadUp();				     //Dpad Up
		auxDpadDown = 		new DpadDown();				     //Dpad Down
		auxRTrigger = 		new RTrigger();				     //Right Trigger
		auxLTrigger = 		new LTrigger();				     //Left Trigger
		drvRTrigger = 		new DrvRTrigger();			     //Driver Right Trigger
		drvLTrigger = 		new DrvLTrigger();			     //Driver Left Trigger

		
		
		//Assign Actions
		auxDpadUp.whenActive(new BM_LiftShiftHiGr()); 					//Shift Lift High gear
		auxDpadDown.whenActive(new BM_LiftShiftLoGr());				    //Shift lift Low gear
		//auxLeftStickDown.whileActive(new BM_ShootOutBlock()); 		//Run Block Out
		//auxLeftStickUp.whileActive(new BM_ShootInBlock());  		    //Run Block In
		//auxLeftStickLeft.whileActive(new TC_RunWheelsRotate(true));	//Rotate Block 
		//auxLeftStickRight.whileActive(new TC_RunWheelsRotate(false)); //Rotate Block
		auxRTrigger.whileActive(new BM_ClawClose());				    //Close Claw
		auxLTrigger.whileActive(new BM_ClawOpen());					    //Open Claw
		auxA.toggleWhenActive(new BM_ClawTiltDown());					//Tilt Claw Down
		auxY.toggleWhenActive(new BM_ClawTiltUp());					    //Tilt Claw Up
		auxRightBumper.toggleWhenActive(new BM_LiftRaiseToMid());		//Jump To Mid
		auxStart.whenPressed(new BM_JammerEngage());			        //Engage Lift Jammer
		auxSelect.whenPressed(new BM_JammerDisengage());		        //Disengage Lift Jammer
		drvRTrigger.whileActive(new BM_StrafeAndRotate(false, true,     //Sideways Arc: Strafe Left, Rotate Right
				                                       (float)90.0,
				                                       false));
		drvLTrigger.whileActive(new BM_StrafeAndRotate(false, false,    //Sideways Arc: Strafe Right, Rotate Left
                                                       (float)-90.0,
                                                       false));

	}
	
	
}
