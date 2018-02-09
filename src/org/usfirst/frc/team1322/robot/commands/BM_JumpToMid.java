package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BM_JumpToMid extends Command {

    public BM_JumpToMid() {
    	//Set the required subsystem
        requires(Robot.kCLAW);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Set Speed to 1
    	Robot.kLIFT.setSpeed(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//If the top sensor is triggered, stop, and go back down to mid
    	if(!Robot.kLIFT.getHighSen()) {
    		Robot.kLIFT.setSpeed(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//When the mid sensor is triggered, STOP
        return !Robot.kLIFT.getMidSen();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Set Motor Speed to 0
    	Robot.kLIFT.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
