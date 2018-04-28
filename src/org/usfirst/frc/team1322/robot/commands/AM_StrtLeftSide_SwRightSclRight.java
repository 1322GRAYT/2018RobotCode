package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal; 

/**
 * Command Group Class: AM_StrtLeftSide_SwRightSclRight
 * Autonomous Control Group Pattern: Robot Starts Left-Side
 * and Robot puts PwrCube in the Right-Side Scale.
 */
public class AM_StrtLeftSide_SwRightSclRight extends CommandGroup {
	
	
	/**
	 * Command Group Method: AM_StrtLeftSide_SwRightSclRight
     * Autonomous Control Group Pattern: Robot Starts Left-Side
     * and Robot puts PwrCube in the Right-Side Scale.
	 */	
    public AM_StrtLeftSide_SwRightSclRight() {   	
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
		addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
		addParallel(new AC_DriveEncdrByDist((float)17.0, (float)0.9, (float)1.0, (float)0.10, (float)0.0, true));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
	    addParallel(new AC_TurnByGyro(K_CmndCal.KCMD_r_RotPwr90Deg, 85));  // Turn CW to 90 deg       	
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
		addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
		addParallel(new AC_DriveEncdrByDist((float)15.6, (float)0.9, (float)1.0, (float)0.10, (float)90.0, true));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
		addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_LiftRaiseToHigh());
	    addParallel(new AC_TurnByGyro(-K_CmndCal.KCMD_r_RotPwr90Deg, 0.0));  // Turn CCW to 0 deg       	
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
    	addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrHigh));
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrHigh));
    	addSequential(new BM_LiftRaiseToHigh());
    	addParallel(new AC_DriveEncdrByDist((float)3.0, (float)0.5, (float)0.75, (float)0.10, (float)0.0, true));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrHigh));
    	addSequential(new BM_ShootOutBlock(K_CmndCal.KCMD_t_ShootPrdOut));
		addSequential(new AC_TimeDelay((float)2.0));
		addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrHigh));
		addParallel(new AC_DriveEncdrByDist((float)1.5, (float)0.5, (float)0.5, (float)0.10, (float)0.0, false));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrHigh));
		System.out.println("StrtLeftSide_SwRightSclRight");
    }    

}

