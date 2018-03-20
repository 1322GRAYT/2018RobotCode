package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.subsystems.AUTON;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.Timer;

/**
  * Class: AutoSlct_RightSide - Starting with the Robot in
 * the Far Right position, performs the Autonomous Mode
 * Selection based on the Field Data from the FMS Data.
  */
public class NewAutoSlct_RightSide extends Command {
	private Timer TmOutTmr = new Timer();
    private boolean AutoSlctCmplt;

	CommandGroup autoCommandGroup;
	
	public NewAutoSlct_RightSide() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	TmOutTmr.reset();
    	TmOutTmr.start();
    	AutoSlctCmplt = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kAUTON.updateFieldData();
    	
    	if (Robot.kAUTON.dtrmnOurSwitchPstn() == true) {
    		AutoSlctCmplt = true;
    	} else if (TmOutTmr.get() >= K_CmndCal.KCMD_t_FieldDataTmOut) {
    		AutoSlctCmplt = true; 
    		Robot.kAUTON.setFieldDataTimedOut(true);
    	} else {
    		// Keep Testing until Valid Switch Data or Time Out
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return AutoSlctCmplt;
    }

    // Called once after isFinished returns true
    protected void end() {
    	TmOutTmr.stop();    	
    	   	
    	if ((Robot.kAUTON.getFieldDataTimedOut() == false) &&
    		(Robot.kAUTON.getOurSwitchLeftSide() == true)) {
    		// Our Alliance Switch is On Left Side
    		autoCommandGroup = new AM_StrtRightSide_SwchLeftSide();    		    		
    	} else if ((Robot.kAUTON.getFieldDataTimedOut() == false) &&
        		   (Robot.kAUTON.getOurSwitchLeftSide() == false)) {
    		// Our Alliance Switch is On Right Side
    		autoCommandGroup = new AM_StrtRightSide_SwchRightSide();
    	} else {
    		/* Timed-Out or Interrupted before Valid Data Detected,
    		   Just drive straight accross the line. */
    		autoCommandGroup = new AM_DriveStraightCrossLine();    		    		
    	}
    	autoCommandGroup.start();	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
