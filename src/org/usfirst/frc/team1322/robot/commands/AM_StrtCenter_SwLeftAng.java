package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 * Command Group Class: AM_StrtCenter_SwLeft
 * Autonomous Control Group Pattern: Robot Starts Center 
 * and Robot puts PwrCube in the Left-Side Switch.
 */
public class AM_StrtCenter_SwLeftAng extends CommandGroup {	

	
	/**
	 * Command Group Method: AM_StrtCenter_SwLeft
	 * Autonomous Control Group Pattern: Robot Starts Center
	 * and Robot puts PwrCube in the Left-Side Switch.
	 */	
    public AM_StrtCenter_SwLeftAng() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn());  
		addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new BM_LiftHoldPstn());  		
    	addSequential(new AC_DriveEncdrByDist((float)1.0, (float)0.3, (float)0.25, (float)0.10, (float)0.0, true, true));
    	addSequential(new AC_TurnByGyro(-0.7, -38.6, true)); // Turn CCW to -38.6 deg
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn());  
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn());  
    	addSequential(new AC_DriveEncdrByDist((float)7.35, (float)0.8, (float)1.0, (float)0.10, (float)-38.6, true, true));
    	addSequential(new AC_TurnByGyro(0.7, 0.0, true)); // Turn CW to 0 deg
    	addSequential(new BM_LiftRaiseToMid());
    	addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.5, true));
	    addParallel(new BM_ClawTiltDown());
    	addSequential(new BM_LiftHoldPstn());  
	    addParallel(new BM_ClawOpen());
    	addSequential(new BM_LiftHoldPstn());  
		addParallel(new AC_TimeDelay((float)0.5));
    	addSequential(new BM_LiftHoldPstn());  
	    addSequential(new AC_DriveByGyroTime(-0.5, 0.0, 2.0, false));  // Strafe to the Left
    	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.5, (float)0.5, (float)0.1, (float)0.0, true, false));
    	System.out.println("StrtCenter_SwLeft");
    }

}

