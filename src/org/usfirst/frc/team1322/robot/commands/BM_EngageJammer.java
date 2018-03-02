package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BM_EngageJammer extends Command {

	private boolean finished = false;
	private boolean engage;
    public BM_EngageJammer(boolean engage) {
        requires(Robot.kLIFT);
        this.engage = engage;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(engage) {
    		Robot.kLIFT.engageJammer();
    	}else {
    		Robot.kLIFT.disengageJammer();
    	}
    	
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
