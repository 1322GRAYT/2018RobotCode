package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.Timer;

/**
  * Class: AutoSlct_Center_BothSidesSwitch - Starting with
  * the Robot in the Center position, performs the Autonomous
  * Mode Selection based on the Field Data from the FMS Data.
  */
public class AutoSlct_Center_BothSidesSwitch extends Command {
	private Timer TmOutTmr = new Timer();
    private boolean AutoSlctCmplt;

	CommandGroup autoCommandGroup;
	
	public AutoSlct_Center_BothSidesSwitch() {
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
    		if (K_CmndCal.KCMD_b_CenterAnglePtrnEnbl == true) {
    			autoCommandGroup = new AM_StrtCenter_SwLeftAng();    			
    		} else {
    			autoCommandGroup = new AM_StrtCenter_SwLeft();
    		}
    	} else if ((Robot.kAUTON.getFieldDataTimedOut() == false) &&
        		   (Robot.kAUTON.getOurSwitchLeftSide() == false)) {
    		// Our Alliance Switch is On Right Side
    		if (K_CmndCal.KCMD_b_CenterAnglePtrnEnbl == true) {
        		autoCommandGroup = new AM_StrtCenter_SwRightAng();
    		} else {
        		autoCommandGroup = new AM_StrtCenter_SwRight();    			
    		}
    	} else {
    		/* Timed-Out or Interrupted before Valid Data Detected,
    		   Just drive straight across the line. */
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
