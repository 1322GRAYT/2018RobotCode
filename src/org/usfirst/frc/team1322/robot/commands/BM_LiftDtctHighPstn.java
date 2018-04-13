package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  Command Class: BM_LiftDtctHighPstn
 *  Trigger the Detection of the Lift High Position.
 */
public class BM_LiftDtctHighPstn extends Command {
	private boolean finished = false;

   /**
 *  Command Method: BM_LiftDtctHighPstn
 *  Trigger the Detection of the Lift High Position.
	*/	
    public BM_LiftDtctHighPstn() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kSENSORS.putLiftHighPstnDtctd(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//set global finished to true
    	finished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
