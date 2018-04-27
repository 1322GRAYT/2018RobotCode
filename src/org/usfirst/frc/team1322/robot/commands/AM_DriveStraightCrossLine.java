package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_DriveStraightCrossLine extends CommandGroup {

    public AM_DriveStraightCrossLine() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
    	addParallel(new AC_DriveEncdrByDistPI((float)12.0, (float)0.8, (float)1.0, (float)0.10, (float)0.0, true));    	
	    addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid));
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	addSequential(new BM_LiftHoldPstn(K_LiftCal.KLFT_r_LiftMtrHldPwrMid)); 
    	System.out.println("drive straight");
    }
}
