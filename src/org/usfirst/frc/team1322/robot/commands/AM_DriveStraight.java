package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_DriveStraight extends CommandGroup {

    public AM_DriveStraight() {
    	addSequential(new AC_DriveByGyroTime(0,.8, 2));
    }
}
