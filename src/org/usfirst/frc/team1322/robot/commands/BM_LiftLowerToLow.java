package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class: BM_LiftLowerToLow Command - Lower the Lift System down to the low
 * position sensor.
 */
public class BM_LiftLowerToLow extends Command {

    public BM_LiftLowerToLow() {
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kLIFT.setSpeed((double)-K_CmndCal.KCMD_r_LiftPwrLowerToLow);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kLIFT.setSpeed((double)-K_CmndCal.KCMD_r_LiftPwrLowerToLow);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean finished = false;
    	
    	if (Robot.kSENSORS.getLiftLowPstnDtctd() == true) {
    		finished = true;
    	}
    	
        return (finished);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kLIFT.setSpeed(0);
    	Robot.kSENSORS.putLiftLowPstnDtctd(false); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
