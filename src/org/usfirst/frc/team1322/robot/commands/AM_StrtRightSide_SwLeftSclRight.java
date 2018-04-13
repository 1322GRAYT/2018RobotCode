package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 * Command Group Class: AM_StrtRightSide_SwLeftSclRight
 * Autonomous Control Group Pattern: Robot Starts Right-Side
 * and Robot puts PwrCube in the Left-Side Scale.
 */
public class AM_StrtRightSide_SwLeftSclRight extends CommandGroup {
	

	/**
	 * Command Group Method: AM_StrtRightSide_SwLeftSclRight
     * Autonomous Control Group Pattern: Robot Starts Right-Side
     * and Robot puts PwrCube in the Right-Side Scale.
	 */	
    public AM_StrtRightSide_SwLeftSclRight() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)24.5, (float)0.9, (float)1.0, (float)0.10, (float)1.77, true, true));
     	addSequential(new AC_TurnByGyro(-0.8, -90, true)); // Turn CCW to -90 deg       	
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
     	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
	    addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_LiftRaiseToHigh());
    	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.7, (float)1.0, (float)0.10, (float)-90.0, true, true));
	    addSequential(new BM_ShootOutBlock(K_CmndCal.KCMD_t_ShootPrdOut));
		addParallel(new AC_TimeDelay((float)0.75));
    	addSequential(new BM_LiftHoldPstn()); 
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.5, (float)0.10, (float)-90.0, false, true));    
    	System.out.println("StrtRightSide_SwLeftSclRight");
    }   

}

