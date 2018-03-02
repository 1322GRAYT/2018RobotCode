package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_Comp_DriveStraightCrossLine extends CommandGroup {

    public AM_Comp_DriveStraightCrossLine() {
    	addSequential(new AC_DriveByGyroTime(0,.8, 2.5));
    }
}
