package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BM_LowerToLow extends Command {

    public BM_LowerToLow() {
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLIFT.setSpeed(-1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      // Continue at Speed -1
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.kLIFT.getLowSen();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kLIFT.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
