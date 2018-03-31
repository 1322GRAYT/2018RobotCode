package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  Command Class: BM_ClawClose
 *  Trigger pneumatic pistons to close Claw to grab
 *  PwrCube.
 */
public class BM_ClawClose extends Command {
	private boolean finished = false;
	
   /**
	*  Command Method: BM_ClawClose
	*  Trigger pneumatic pistons to close Claw to grab
    *  PwrCube.
	*/
    public BM_ClawClose() {
    	//Set the required subsystem
        requires(Robot.kCLAW);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.kCLAW.closeClaw();
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
