package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 *  Command Group Class: AM_TestEnc
 *  <Description>
 */
public class AM_TestEnc extends CommandGroup {

	
	/**
	 *  Command Group Method: AM_TestEnc
	 *  <Description>
	 */	
    public AM_TestEnc() {

    	
    	addSequential(new AC_CaptureFieldData(K_CmndCal.KCMD_t_FieldDataTmOut));
    	addSequential(new AC_ResetGyro());  	
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	
    	if((Robot.kAUTON.getScaleDataCaptured() == true) &&
    	   (Robot.kAUTON.getOurSwitchLeftSide() == true)) { // Our Alliance Switch is on Left Side
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	} 
    	else if ((Robot.kAUTON.getScaleDataCaptured() == true) &&
 			     (Robot.kAUTON.getOurSwitchLeftSide() == false)) { // Our Alliance Switch is On Right Side
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	}
    	else {
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	    		
    	}

    	if((Robot.kAUTON.getScaleDataCaptured() == true) &&
    	   (Robot.kAUTON.getOurSwitchLeftSide() == true)) { // Our Alliance Switch is on Left Side
    	    addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	    addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	} 
    	else if ((Robot.kAUTON.getScaleDataCaptured() == true) &&
    	         (Robot.kAUTON.getOurSwitchLeftSide() == false)) { // Our Alliance Switch is On Right Side
    	    addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	    addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	}
    	else {
    	    addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	    addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	    		
    	}
    	
    }
        
}
