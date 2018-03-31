package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
  *  Command Class: BM_TiltDownClaw
  *  Trigger pneumatic pistons to tilt Claw Down to Horizontal
  *  Position for dropping block and also to clear hanging
  *  mechanism when raising Claw to high position.
 */
public class BM_ClawTiltDown extends Command {
	private boolean finished = false;

  /**	
	*  Command Method: BM_TiltDownClaw
	*  Trigger pneumatic pistons to tilt Claw Down to Horizontal
	*  Position for dropping block and also to clear hanging
	*  mechanism when raising Claw to high position.
    */
    public BM_ClawTiltDown() {
        //Set the required subsystem
        requires(Robot.kCLAW);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kCLAW.lowerClaw();
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
