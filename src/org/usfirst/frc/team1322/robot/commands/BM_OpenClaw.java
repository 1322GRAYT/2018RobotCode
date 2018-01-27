package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BM_OpenClaw extends Command {

	private boolean open;
	private boolean finished = false;
	
    public BM_OpenClaw(boolean open) {
        requires(Robot.kCLAW);
        this.open = open;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(open) {
    		Robot.kCLAW.openClaw();
    	}else {
    		Robot.kCLAW.closeClaw();
    	}
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
