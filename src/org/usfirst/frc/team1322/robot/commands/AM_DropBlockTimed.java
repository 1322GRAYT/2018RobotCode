package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Command Group Class: AM_DropBlockTimed
 *  Drive Forward for a second
 */
public class AM_DropBlockTimed extends CommandGroup {
	
    public AM_DropBlockTimed() {
    	addSequential(new AC_DriveByGyroTime(0, 0.4, 1, false));
    	addSequential(new BM_LiftRaiseToMid());
    	addSequential(new AC_TurnByGyro(-0.8, -270, true));
    	addSequential(new AC_DriveByGyroTime(0, 0.8, 2, true));
    	addSequential(new AC_TurnByGyro(0.8, 0, true));
    	addSequential(new AC_DriveByGyroTime(0, 0.8, 1, true));
    	addSequential(new BM_ClawTiltDown());
    	addSequential(new BM_ClawOpen());
    	addSequential(new BM_ClawTiltUp());
    }
}
