package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
  * Class: AC_CaptureFieldData - Capture Field Data from the
  * Field Management System.  
  */
public class AC_CaptureFieldData extends Command {
    float TmOutThrsh;
	private static String fieldData;   // String of Field Data
    
	private Timer TmOutTmr = new Timer();

    public AC_CaptureFieldData(float TmOutThrsh) {
    	this.TmOutThrsh = TmOutThrsh;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	TmOutTmr.reset();
    	TmOutTmr.start();
    	Robot.kAUTON.resetFieldDataCapture();
    	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	// Timer is Free Running.

	    fieldData = Robot.kAUTON.getFieldData();
	
	    if(fieldData.length() > 0)
	    {
	    	// Capture Switch Position 
	    	if (fieldData.charAt(0) == 'L') {
		       Robot.kAUTON.setOurSwitchLeftSide(true);
		       Robot.kAUTON.setSwitchDataCaptured(true);
		    } else if (fieldData.charAt(0) == 'R') {
			       Robot.kAUTON.setOurSwitchLeftSide(false);
			       Robot.kAUTON.setSwitchDataCaptured(true);		    	
		    } else {
			       Robot.kAUTON.setSwitchDataCaptured(false);		    		    	
		    }	    	

	    	// Capture Scale Position 
		    if (fieldData.charAt(1) == 'L') {
		       Robot.kAUTON.setOurScaleLeftSide(true);
		       Robot.kAUTON.setScaleDataCaptured(true);
		    } else if (fieldData.charAt(1) == 'R') {
			       Robot.kAUTON.setOurScaleLeftSide(false);
			       Robot.kAUTON.setScaleDataCaptured(true);		    	
		    } else {
			       Robot.kAUTON.setScaleDataCaptured(false);		    		    	
		    }	    	   
	    } else {
		Robot.kAUTON.setSwitchDataCaptured(false);		    		    	
		Robot.kAUTON.setScaleDataCaptured(false);		    		    		    	
	    }

	    // Update Smart Dashboard Data
//	    if (K_CmndCal.KCMD_b_DebugEnbl)
	        updateSmartDashData(); 	    
	    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean CmndCmplt;
    	
    	if ((Robot.kAUTON.getSwitchDataCaptured() == true) &&
    		(Robot.kAUTON.getScaleDataCaptured() == true)) {
    	    Robot.kAUTON.setFieldDataTimedOut(false);
    	    CmndCmplt = true;
    	} else if (TmOutTmr.get() >= (double)TmOutThrsh) {
    	    Robot.kAUTON.setFieldDataTimedOut(true);
    	    CmndCmplt = true;
        } else {
    	    CmndCmplt = false;        	
        }
    	
        return (CmndCmplt);
    }

    // Called once after isFinished returns true
    protected void end() {
    	TmOutTmr.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

    
    
	// Called to Update SmartDash Data for Display
    protected void updateSmartDashData() {

    	//Gyro
    	SmartDashboard.putNumber("Gyro Angle : ", Robot.kSENSORS.getGyroAngle());
    	//Drive Speeds
/*    	
    	SmartDashboard.putData("FieldData1 : ", fieldData.charAt(0));
        SmartDashboard.putString("FieldData2 : ", fieldData.charAt(1));
        SmartDashboard.putString("FieldData3 : ", fieldData.charAt(2));
*/
        SmartDashboard.putBoolean("Our Switch Left? : ", Robot.kAUTON.getOurSwitchLeftSide());
    	SmartDashboard.putBoolean("Our Scale Left? : ", Robot.kAUTON.getOurScaleLeftSide());
    	SmartDashboard.putBoolean("Our Switch Left? : ", Robot.kAUTON.getSwitchDataCaptured());
    	SmartDashboard.putBoolean("Our Switch Left? : ", Robot.kAUTON.getScaleDataCaptured());
    	SmartDashboard.putBoolean("Our Scale Left? : ", Robot.kAUTON.getFieldDataTimedOut());
    	SmartDashboard.putNumber("Field Data TimeOut Timer : ", TmOutTmr.get());
    	
    	//Update SmartDashboard
    	System.out.println("FieldData1 : " + fieldData.charAt(0));
    	System.out.println("FieldData2 : " + fieldData.charAt(1));
    	System.out.println("FieldData3 : " + fieldData.charAt(2));
    	System.out.println("Our Switch Left? : " + Robot.kAUTON.getOurSwitchLeftSide());
    	System.out.println("Our Scale Left? : " + Robot.kAUTON.getOurScaleLeftSide());
    	System.out.println("Our Switch Left? : " + Robot.kAUTON.getSwitchDataCaptured());
    	System.out.println("Our Switch Left? : " + Robot.kAUTON.getScaleDataCaptured());
    	System.out.println("Our Scale Left? : " + Robot.kAUTON.getFieldDataTimedOut());
    	System.out.println("Field Data TimeOut Timer : " + TmOutTmr.get());
    
    }
    
    
}
