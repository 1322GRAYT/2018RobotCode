package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 * Command Group Class: AM_StrtRightSide_SwchLeftSide
 * Autonomous Control Group Pattern: Robot Starts Right-Side
 * and Robot puts PwrCube in the Left-Side Switch.
 */
public class AM_StrtRightSide_SwchLeftSide extends CommandGroup {
	

	/**
	 * Command Group Method: AM_StrtRightSide_SwchLeftSide
     * Autonomous Control Group Pattern: Robot Starts Right-Side
     * and Robot puts PwrCube in the Left-Side Switch.
	 */	
    public AM_StrtRightSide_SwchLeftSide() {
    	addSequential(new AC_CaptureFieldData(K_CmndCal.KCMD_t_FieldDataTmOut));
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_RaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)17.0, (float)0.9, (float)2.0, (float)0.10, (float)0.0, true, true));
     	addSequential(new AC_TurnByGyro(-0.9, -75.0, true)); // Turn CCW to -90 deg       	
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
     	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)16.0, (float)0.9, (float)2.0, (float)0.10, (float)-90.0, true, true));
	    addSequential(new AC_TurnByGyro(-0.9, -190.0, true)); // Turn CCW to -180 deg
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.8, (float)1.0, (float)0.20, (float)-180.0, true, true));
	    addSequential(new AC_TurnByGyro(-0.9, -280.0, true)); // Turn CCW to -270 deg
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new BM_RaiseToMid());
	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
	    addSequential(new BM_LiftClaw(false));
	    addSequential(new BM_OpenClaw(true));
    	System.out.println("StrtRightSide_SwchLeftSide");
    }   

}

