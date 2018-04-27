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
public class AM_StrtCenter_SwLeft extends CommandGroup {	

	
	/**
	 * Command Group Method: AM_StrtCenter_SwLeft
	 * Autonomous Control Group Pattern: Robot Starts Center
	 * and Robot puts PwrCube in the Left-Side Switch.
	 */	
    public AM_StrtCenter_SwLeft() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.25, (float)0.10, (float)0.0, true, true));
    	addSequential(new AC_TurnByGyro(-K_CmndCal.KCMD_r_RotPwr90Deg, -85.0, true)); // Turn CCW to -90 deg
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.8, (float)1.0, (float)0.10, (float)-90.0, true, true));
    	addSequential(new AC_TurnByGyro(K_CmndCal.KCMD_r_RotPwr90Deg, 0.0, true)); // Turn CW to 0 deg
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)3.5, (float)0.8, (float)0.5, (float)0.3, (float)0.0, true, true));
    	addSequential(new BM_LiftRaiseToMid());
	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
	    addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_ClawOpen());
		addParallel(new AC_TimeDelay((float)0.5));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid)); 
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
    	System.out.println("StrtCenter_SwLeft");
    }

}

