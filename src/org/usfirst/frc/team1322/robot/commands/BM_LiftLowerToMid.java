package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class: BM_LiftLowerToMid Command - Lower the Lift System down to the mid position
 * sensor, if it is already past the mid sensor or misses the mid position
 * sensor and the low position sensor is detected, the lift motor speed will
 * be reversed to send the lift back up to the mid position sensor.
 */
public class BM_LiftLowerToMid extends Command {

    public BM_LiftLowerToMid() {
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLIFT.setSpeed((double)-K_CmndCal.KCMD_r_LiftPwrLowerToMid);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(!Robot.kLIFT.getLowSen()) {
    		Robot.kLIFT.setSpeed(1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.kLIFT.getMidSen();
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
