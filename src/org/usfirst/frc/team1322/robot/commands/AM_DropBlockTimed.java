package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Command Group Class: AM_DropBlockTimed
 *  Drive Forward for a second
 */
public class AM_DropBlockTimed extends CommandGroup {
	
    public AM_DropBlockTimed() {
    	addSequential(new AC_DriveByGyroTime(0, 0.4, 1, false));
    	addSequential(new BM_RaiseToMid());
    	addSequential(new AC_TurnByGyro(-0.8, -270, true));
    	addSequential(new AC_DriveByGyroTime(0, 0.8, 2, true));
    	addSequential(new AC_TurnByGyro(0.8, 0, true));
    	addSequential(new AC_DriveByGyroTime(0, 0.8, 1, true));
    	addSequential(new BM_LiftClaw(true));
    	addSequential(new BM_OpenClaw(true));
    	addSequential(new BM_LiftClaw(false));
    }
}
