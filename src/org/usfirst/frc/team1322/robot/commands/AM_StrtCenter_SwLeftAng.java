package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal; 


/**
 * Command Group Class: AM_StrtCenter_SwLeft
 * Autonomous Control Group Pattern: Robot Starts Center 
 * and Robot puts PwrCube in the Left-Side Switch.
 */
public class AM_StrtCenter_SwLeftAng extends CommandGroup {	

	
	/**
	 * Command Group Method: AM_StrtCenter_SwLeft
	 * Autonomous Control Group Pattern: Robot Starts Center
	 * and Robot puts PwrCube in the Left-Side Switch.
	 */	
    public AM_StrtCenter_SwLeftAng() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
		addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  		
    	addParallel(new AC_DriveEncdrByDistPI((float)1.0, (float)0.3, (float)0.25, (float)0.10, (float)0.0, true));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addParallel(new AC_TurnByGyroPI(false, -35.0)); // Turn CCW to -38.6 deg
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addParallel(new AC_DriveEncdrByDistPI((float)8.0, (float)0.8, (float)1.0, (float)0.10, (float)-35.0, true));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addParallel(new AC_TurnByGyroPI(true, 0.0)); // Turn CW to 0 deg
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addSequential(new BM_LiftRaiseToMid());
    	addParallel(new AC_DriveByGyroTime(0.0, 0.5, 0.5));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));  
    	addSequential(new BM_ClawTiltDown()); 
    	addSequential(new BM_ClawOpen()); 
		addParallel(new AC_TimeDelay((float)1.0));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));    	
    	System.out.println("StrtCenter_SwLeftAng");
    }

}

