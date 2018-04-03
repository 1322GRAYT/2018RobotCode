package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
  * Command Class: BM_LiftHoldPstn
  * Hold the Lift in either the Mid Pstn or High Pstn
  * by running the Lift Motor up at a low power until
  * the position switch detects the lift is in position,
  * then turn motor power off until the position switch
  * indicates it is no longer in position, then turn it
  * back on.  Caution to not over-power the Lift Motor
  * or the lift could overshoot the position switch and
  * continue running up.
 */
public class BM_LiftHoldPstn extends Command {
    private double LiftPwrCmnd;
	
	
	/**
	 * Command Method: BM_LiftHoldPstn
	 * Hold the Lift in either the Mid Pstn or High Pstn
	 * by running the Lift Motor up at a low power until
	 * the position switch detects the lift is in position,
	 * then turn motor power off until the position switch
	 * indicates it is no longer in position, then turn it
	 * back on.  Caution to not over-power the Lift Motor
	 * or the lift could overshoot the position switch and
	 * continue running up.
	 */
    public BM_LiftHoldPstn() {
        requires(Robot.kLIFT);
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
        // None Required
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		
	    // Keep Lift in Elevated Position
	    if ((Robot.kLIFT.getMidSen() == true) &&
			(Robot.kLIFT.getHighSen() == true)) {
	        // PwrCube not sensed by N/C Sensor
	    	LiftPwrCmnd = (double)K_LiftCal.KLFT_r_LiftMtrHldPwr;	
	    } else {
	    	// PwrCube is sensed by N/C Sensor
	    	LiftPwrCmnd = 0.0;	
	    }
	    
	    Robot.kLIFT.setSpeed(LiftPwrCmnd);
	    
  	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
	    	updateSmartDashData();  
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.kAUTON.getMasterTaskCmplt());
    }

    
    // Called once after command is finished.
    protected void end() {
  	    Robot.kLIFT.setSpeed(0.0);
    }

    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    
	// Call to Update SmartDash Data for Display
    protected void updateSmartDashData() {
    	SmartDashboard.putNumber("Lift Hold Power Cmnd : ", LiftPwrCmnd);    	
    	
    	System.out.println("Lift Hold Power Cmnd : " + LiftPwrCmnd);     	
    }
    
}
