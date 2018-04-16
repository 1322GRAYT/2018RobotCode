package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 * Command Group Class: AM_StrtCenter_SwRight
 * Autonomous Control Group Pattern: Robot Starts Center 
 * and Robot puts PwrCube in the Right-Side Switch.
 */
public class AM_StrtCenter_SwRightAng extends CommandGroup {	

	
	/**
	 * Command Group Method: AM_StrtCenter_SwRight
	 * Autonomous Control Group Pattern: Robot Starts Center
	 * and Robot puts PwrCube in the Right-Side Switch.
	 */	
    public AM_StrtCenter_SwRightAng() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn());  
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn());  
    	addSequential(new AC_DriveEncdrByDist((float)1.0, (float)0.3, (float)0.25, (float)0.10, (float)0.0, true, true));
    	addSequential(new AC_TurnByGyro(0.5, 38.0, true)); // Turn CW to 38.6 deg
		addParallel(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new BM_LiftHoldPstn());  
    	addParallel(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new BM_LiftHoldPstn());  
    	addSequential(new AC_DriveEncdrByDist((float)8.0, (float)0.8, (float)1.0, (float)0.10, (float)35.0, true, true));
	    addSequential(new AC_TurnByGyro(-0.5, 0.0, true)); // Turn CCW to 0 deg
    	addSequential(new BM_LiftRaiseToMid());
	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.5, true));
	    addSequential(new BM_ClawTiltDown()); 
	    addSequential(new BM_ClawOpen()); 
		addParallel(new AC_TimeDelay((float)0.5));
    	addSequential(new BM_LiftHoldPstn());
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	addSequential(new BM_LiftHoldPstn());        	
    	System.out.println("StrtCenter_SwRightAng");
    }

}

