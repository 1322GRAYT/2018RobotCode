package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

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
    	addSequential(new BM_RaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)17.0, (float)0.9, (float)1.0, (float)0.10, (float)0.0, true, true));
     	addSequential(new AC_TurnByGyro(0.9, 90, true));  // Turn CW to 90 deg       	
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
     	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)15.6, (float)0.9, (float)1.0, (float)0.10, (float)90.0, true, true));
     	addSequential(new AC_TurnByGyro(-0.9, 0.0, true));  // Turn CCW to 0 deg       	
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
     	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
	    addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_RaiseToHigh());
	    addSequential(new AC_DriveEncdrByDist((float)3.0, (float)0.5, (float)0.75, (float)0.10, (float)0.0, true, true));
	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.5, true));
	    addSequential(new BM_RaiseToHigh());
	    addSequential(new BM_ClawOpen());
		addSequential(new AC_TimeDelay((float)0.5));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.5, (float)0.10, (float)0.0, false, false));
     	addSequential(new AC_TurnByGyro(-0.9, -180, false)); // Turn CCW to -180 deg
    	System.out.println("StrtLeftSide_SwRightSclRight");
    }    

}

