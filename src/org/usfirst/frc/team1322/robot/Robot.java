/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1322.robot;

import org.usfirst.frc.team1322.robot.commands.AM_DriveStraightCrossLine;
import org.usfirst.frc.team1322.robot.commands.AutoSlct_LeftSide;
import org.usfirst.frc.team1322.robot.commands.AutoSlct_RightSide;
import org.usfirst.frc.team1322.robot.commands.AutoSlct_Center;
import org.usfirst.frc.team1322.robot.commands.AM_Test_RotPI_Swch1322;
import org.usfirst.frc.team1322.robot.subsystems.AUTON;
import org.usfirst.frc.team1322.robot.subsystems.CLAW;
import org.usfirst.frc.team1322.robot.subsystems.DRIVE;
import org.usfirst.frc.team1322.robot.subsystems.LIFT;
import org.usfirst.frc.team1322.robot.subsystems.PID;
import org.usfirst.frc.team1322.robot.subsystems.SENSORS;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	public static final AUTON kAUTON = new AUTON();
	public static final LIFT kLIFT = new LIFT();
	public static final CLAW kCLAW = new CLAW();
	public static final DRIVE kDRIVE = new DRIVE();
	public static final SENSORS kSENSORS = new SENSORS();
	public static final PID kPID = new PID();
	public static final USERLIB kTBLLOOKUP = new USERLIB();
	
	public static String fieldString;
	
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Cross Line Only: Robot L/R-Side", new AM_DriveStraightCrossLine());
		m_chooser.addObject("Start Robot L-Side", new AutoSlct_LeftSide());
		m_chooser.addObject("Start Robot R-Side", new AutoSlct_RightSide());
        m_chooser.addObject("Start Robot Center", new AutoSlct_Center());
		m_chooser.addObject("DO NOT RUN (TEST ONLY)", new AM_Test_RotPI_Swch1322());
		SmartDashboard.putData("Auto mode", m_chooser);
		kSENSORS.calibrateGyro();
		kSENSORS.setGlobalBaud();
		kPID.resetPIDRot();
		System.out.println("Gyro Calibrated, Analog Baud Rate Set");
		CameraServer.getInstance().startAutomaticCapture().setResolution(640, 320);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		kLIFT.engageJammer();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		kAUTON.resetFieldDataCapture();
		kAUTON.updateAllianceColor();
		kAUTON.updateFieldData();
		kLIFT.disengageJammer();

		fieldString = DriverStation.getInstance().getGameSpecificMessage();
		m_autonomousCommand = m_chooser.getSelected();
		

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		kSENSORS.updateSensorData();
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		kLIFT.disengageJammer();
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		kPID.resetPIDRot();
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		kSENSORS.updateSensorData();
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {

	}
}
