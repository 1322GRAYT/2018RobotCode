package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 * Command Group Class: AM_StrtRightSide_SwLeftSclLeftHoldCube
 * Autonomous Control Group Pattern: Robot Starts Right-Side
 * and Robot drives towards Left-Side Scale but stops short
 * to allow team-mate to put multiple blocks in Scale and will
 * put Block in Scale/Switch at beginning of Tele.
 */
public class AM_StrtRightSide_SwLeftSclLeftHoldCube extends CommandGroup {
	

	/**
	 * Command Group Method: AM_StrtRightSide_SwLeftSclLeftHoldCube
     * Autonomous Control Group Pattern: Robot Starts Right-Side
     * and Robot drives towards Left-Side Scale but stops short
     * to allow team-mate to put multiple blocks in Scale and will
     * put Block in Scale/Switch at beginning of Tele.
	 */	
    public AM_StrtRightSide_SwLeftSclLeftHoldCube() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn()); 
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn()); 
    	addSequential(new AC_DriveEncdrByDist((float)17.0, (float)0.9, (float)1.0, (float)0.10, (float)0.0, true, true));
     	addSequential(new AC_TurnByGyro(-0.9, -90, true));  // Turn CCW to -90 deg       	
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn()); 
     	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new BM_LiftHoldPstn()); 
    	addSequential(new AC_DriveEncdrByDist((float)12.0, (float)0.9, (float)1.0, (float)0.10, (float)-90.0, true, true));
    	addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_LiftRaiseToHigh());
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	addSequential(new BM_LiftHoldPstn()); 
    	System.out.println("StrtRightSide_SwLeftSclLeftHoldCube");
    }   

}

