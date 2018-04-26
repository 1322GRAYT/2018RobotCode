package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 * Command Group Class: AM_StrtRightSide_SwLeftSclLeft
 * Autonomous Control Group Pattern: Robot Starts Right-Side
 * and Robot puts PwrCube in the Left-Side Scale.
 */
public class AM_StrtRightSide_SwLeftSclLeft extends CommandGroup {
	

	/**
	 * Command Group Method: AM_StrtRightSide_SwLeftSclLeft
     * Autonomous Control Group Pattern: Robot Starts Right-Side
     * and Robot puts PwrCube in the Left-Side Scale.
	 */	
    public AM_StrtRightSide_SwLeftSclLeft() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)17.0, (float)0.9, (float)1.0, (float)0.10, (float)0.0, true, true));
     	addSequential(new AC_TurnByGyro(-K_CmndCal.KCMD_r_RotPwr90Deg, -85, true));  // Turn CCW to -90 deg       	
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
     	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)15.6, (float)0.9, (float)1.0, (float)0.10, (float)-90.0, true, true));
	    addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_LiftRaiseToHigh());
     	addSequential(new AC_TurnByGyro(K_CmndCal.KCMD_r_RotPwr90Deg, 0.0, true));  // Turn CW to 0 deg       	
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
     	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
	    addSequential(new BM_LiftRaiseToHigh());
	    addSequential(new AC_DriveEncdrByDist((float)3.0, (float)0.5, (float)0.75, (float)0.10, (float)0.0, true, true));
	    addSequential(new BM_ShootOutBlock(K_CmndCal.KCMD_t_ShootPrdOut));
		addSequential(new AC_TimeDelay((float)0.250));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)1.5, (float)0.5, (float)0.5, (float)0.10, (float)0.0, false, true));   	    	
    	System.out.println("StrtRightSide_SwLeftSclLeft");
    }   

}

