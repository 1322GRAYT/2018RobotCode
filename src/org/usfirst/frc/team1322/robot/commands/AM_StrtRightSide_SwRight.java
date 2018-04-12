package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 * Command Group Class: AM_StrtRightSide_SwRight
 * Autonomous Control Group Pattern: Robot Starts Right-Side
 * and Robot puts PwrCube in the Right-Side Switch.
 */
public class AM_StrtRightSide_SwRight extends CommandGroup {
	

	/**
	 * Command Group Method: AM_StrtRightSide_SwRight
     * Autonomous Control Group Pattern: Robot Starts Right-Side
     * and Robot puts PwrCube in the Right-Side Switch.
	 */	
    public AM_StrtRightSide_SwRight() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)12.0, (float)0.8, (float)1.0, (float)0.1, (float)0.0, true, true));
    	addSequential(new AC_TurnByGyro(-0.9, -90.0, true)); // Turn CCW to -90 deg
    	addSequential(new BM_LiftRaiseToMid());
	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
	    addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_ClawOpen());
		addSequential(new AC_TimeDelay((float)0.5));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.6, (float)0.5, (float)0.20, (float)-90.0, false, false));
    	addSequential(new AC_TurnByGyro(0.9, 0.0, false));  // Turn CW to 90 deg
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)5.0, (float)0.8, (float)1.0, (float)0.10, (float)0.0, true, false));
    	addSequential(new AC_TurnByGyro(-0.9, -90.0, false)); // Turn CCW to -90 deg
 		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.8, (float)1.0, (float)0.10, (float)0.0, true, false));
    	addSequential(new AC_TurnByGyro(-0.9, -180.0, false)); // Turn CCW to -180 deg
    	System.out.println("StrtRightSide_SwRight");
    }   

}

