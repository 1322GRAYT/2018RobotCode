package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class: BM_LiftRaiseToHigh Command - Raise the Lift System up to the high
 * position sensor.
 */
public class BM_LiftRaiseToHigh extends Command {

    public BM_LiftRaiseToHigh() {
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLIFT.setSpeed((double)K_CmndCal.KCMD_r_LiftPwrRaiseToHigh);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
      // Continue at Speed 1
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.kLIFT.getHighSen();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kLIFT.setSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
