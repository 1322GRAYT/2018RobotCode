package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 * Command Group Class: AM_StrtLeftSide_SwLeft
 * Autonomous Control Group Pattern: Robot Starts Left-Side
 * and Robot puts PwrCube in the Left-Side Switch.
 */
public class AM_StrtLeftSide_SwLeft extends CommandGroup {
	
	
	/**
	 * Command Group Method: AM_StrtLeftSide_SwLeft
     * Autonomous Control Group Pattern: Robot Starts Left-Side
     * and Robot puts PwrCube in the Left-Side Switch.
	 */	
    public AM_StrtLeftSide_SwLeft() {   	
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn());
		addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn());
		addSequential(new AC_DriveEncdrByDist((float)12.0, (float)0.8, (float)1.5, (float)0.1, (float)0.0, true, true));
    	addSequential(new AC_TurnByGyro(0.8, 85.0, true));  // Turn CW to 90 deg
    	addSequential(new BM_LiftRaiseToMid());
	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
	    addSequential(new BM_ClawTiltDown());
	    addSequential(new BM_ClawOpen());
		addParallel(new AC_TimeDelay((float)0.250));
    	addSequential(new BM_LiftHoldPstn()); 
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.5, (float)0.10, (float)90.0, false, true));
    	System.out.println("StrtLeftSide_SwLeft");
    }    

}

