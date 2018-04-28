package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal; 

/**
 * Command Group Class: AM_StrtLeftSide_SwRightSclRightHoldCube
 * Autonomous Control Group Pattern: Robot Starts Left-Side
 * and Robot drives towards Right-Side Scale but stops short
 * to allow team-mate to put multiple blocks in Scale and will
 * put Block in Scale/Switch at beginning of Tele.
 */
public class AM_StrtLeftSide_SwRightSclRightHoldCube extends CommandGroup {
	
	
	/**
	 * Command Group Method: AM_StrtLeftSide_SwRightSclRightHoldCube
     * Autonomous Control Group Pattern: Robot Starts Left-Side
     * and Robot drives towards Right-Side Scale but stops short
     * to allow team-mate to put multiple blocks in Scale and will
     * put Block in Scale/Switch at beginning of Tele.
	 */	
    public AM_StrtLeftSide_SwRightSclRightHoldCube() {   	
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid)); 
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid)); 
    	addParallel(new AC_DriveEncdrByDist((float)17.0, (float)0.9, (float)1.0, (float)0.10, (float)0.0, true));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
    	addSequential(new AC_TurnByGyro(K_CmndCal.KCMD_r_RotPwr90Deg, 85, true));  // Turn CW to 90 deg       	
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid)); 
     	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid)); 
    	addParallel(new AC_DriveEncdrByDist((float)12.0, (float)0.9, (float)1.0, (float)0.10, (float)90.0, true));
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
    	addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_LiftRaiseToHigh());
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrHigh)); 
    	System.out.println("StrtLeftSide_SwRightSclRightHoldCube");
    }    

}

