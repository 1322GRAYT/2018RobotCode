package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal; 

/**
 * Command Group Class: AM_StrtLeftSide_Swch1322
 * Autonomous Control Group Pattern: Robot Starts Left Side
 * and Robot puts PwrCube in the Switch.
 */
public class AM_StrtLeftSide_Swch_PICntrl extends CommandGroup {
	

	/**
	 * Command Group Method: AM_StrtLeftSide_Swch1322
	 * Autonomous Control Group Pattern: Robot Starts Left Side
	 * and Robot puts PwrCube in the Switch.
	 */	
    public AM_StrtLeftSide_Swch_PICntrl() {

    	addSequential(new AC_ResetGyro());    	
    	addSequential(new BM_LiftRaiseToMid());
	  	addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	        		  	
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	        		  	
    	if((Robot.kAUTON.getScaleDataCaptured() == true) &&
    	   (Robot.kAUTON.getOurSwitchLeftSide() == true)) { // Our Alliance Switch is on Left Side
        	addParallel(new AC_DriveEncdrByDistPI((float)12.0, (float)0.8, (float)1.5, (float)0.20, (float)0.0, true));
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	
        	addParallel(new AC_TurnByGyroPI(true, 90.0)); // Turn CW to 90 deg
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	        	
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
    	    addSequential(new BM_ClawTiltDown());
    	    addSequential(new BM_ClawOpen());
    	  	addSequential(new AC_TimeDelay((float)0.5));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
        	addSequential(new AC_DriveEncdrByDistPI((float)2.0, (float)0.6, (float)0.5, (float)0.20, (float)90.0, false));
    	    addSequential(new AC_DriveByGyroTime(-0.5, 0.0, 2.5, false));  // Strafe to the Left
       		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
        	addSequential(new AC_DriveEncdrByDistPI((float)4.0, (float)0.8, (float)1.0, (float)0.20, (float)90.0, true));
        	addSequential(new AC_TurnByGyroPI(true, 180.0)); // Turn CW to 180 deg
    	}
    	else if ((Robot.kAUTON.getScaleDataCaptured() == true) &&
   			     (Robot.kAUTON.getOurSwitchLeftSide() == false)) { // Our Alliance Switch is On Right Side
        	addParallel(new AC_DriveEncdrByDistPI((float)17.0, (float)0.9, (float)2.0, (float)0.10, (float)0.0, true));
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	
        	addParallel(new AC_TurnByGyroPI(true, 90.0)); // Turn CW to 90 deg
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	
        	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
        	addSequential(new AC_DriveEncdrByDistPI((float)16.0, (float)0.9, (float)2.0, (float)0.10, (float)90.0, true));
        	addParallel(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	
        	addParallel(new AC_TurnByGyroPI(true, 180.0)); // Turn CW to 180 deg
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	
        	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
        	addParallel(new AC_DriveEncdrByDistPI((float)4.0, (float)0.8, (float)0.9, (float)0.20, (float)180.0, true));
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	
        	addParallel(new AC_TurnByGyroPI(true, 270.0)); // Turn CW to 270 deg
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	    		
        	addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	    		        	
        	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
        	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));        	    		        	
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
    	    addSequential(new BM_ClawTiltDown());
    	    addSequential(new BM_ClawOpen());
    	} else {
        	addSequential(new AC_DriveEncdrByDist((float)13.0, (float)0.9, (float)1.0, (float)0.10, (float)0.0, true, true));    		
    	}
    }

}

