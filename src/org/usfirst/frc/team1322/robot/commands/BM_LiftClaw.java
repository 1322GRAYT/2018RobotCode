package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BM_LiftClaw extends Command {
	
	private boolean finished = false;
	private boolean lift;

    public BM_LiftClaw(boolean lift) {
    	//Set global this.lift
        this.lift = lift;
        //Set the required subsystem
        requires(Robot.kCLAW);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//If lift, tilt claw up, else, tilt it down
    	if(lift) {
    		Robot.kCLAW.liftClaw();
    	}else {
    		Robot.kCLAW.lowerClaw();
    	}
    	//tell everyone we finished
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
