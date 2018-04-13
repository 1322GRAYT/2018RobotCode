package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class: BM_LiftRaiseToMid Command - Raise the Lift System up to the mid position
 * sensor, if it is already past the mid sensor or misses the mid position
 * sensor and the high position sensor is detected, the lift motor speed will
 * be reversed to send the lift back down to the mid position sensor.
 */
public class BM_LiftRaiseToMid extends Command {

    public BM_LiftRaiseToMid() {
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLIFT.setSpeed((double)K_CmndCal.KCMD_r_LiftPwrRaiseToMid);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.kSENSORS.getLiftHighPstnDtctd() == true) {
        	Robot.kLIFT.setSpeed((double)-K_CmndCal.KCMD_r_LiftPwrLowerToMid);
        	Robot.kSENSORS.putLiftHighPstnDtctd(false);
    	} else {
        	Robot.kLIFT.setSpeed((double)K_CmndCal.KCMD_r_LiftPwrRaiseToMid);    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean finished = false;
    	
    	if (Robot.kSENSORS.getLiftMidPstnDtctd() == true) {
    		finished = true;
    	}
    	
        return (finished);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kLIFT.setSpeed(0);
    	Robot.kSENSORS.putLiftMidPstnDtctd(false);    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
