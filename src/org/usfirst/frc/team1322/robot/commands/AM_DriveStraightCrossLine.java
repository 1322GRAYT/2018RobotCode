package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_DriveStraightCrossLine extends CommandGroup {

    public AM_DriveStraightCrossLine() {
    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_LiftRaiseToMid());
    	addSequential(new AC_DriveEncdrByDistPI((float)12.0, (float)0.8, (float)1.0, (float)0.10, (float)0.0, true));    	
    	System.out.println("drive straight");
    }
}
