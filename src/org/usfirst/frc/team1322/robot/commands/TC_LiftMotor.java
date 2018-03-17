package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.OI;
import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_LiftMotor extends Command {

    public TC_LiftMotor() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//When Robot starts, disengage the jammer
    	//Robot.kLIFT.disengageJammer(); //TODO:  Do not do - In Tele-Op TC_LiftMotor() Runs Every-Loop
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Set speed of lift motors
    	Robot.kLIFT.setSpeed(-OI.AuxStick.getY(Hand.kRight));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
