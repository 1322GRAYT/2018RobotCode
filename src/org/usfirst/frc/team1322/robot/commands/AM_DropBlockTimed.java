package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_DropBlockTimed extends CommandGroup {

	//private final String fd = DriverStation.getInstance().getGameSpecificMessage(); //Store Game specific data to string
	//private final char[] fieldData = {fd.charAt(0), fd.charAt(1), fd.charAt(2)}; //Make Game Data more accessable
	//private final int dsNum = DriverStation.getInstance().getLocation(); //Store driver station number to string
	
    public AM_DropBlockTimed() {
    	addSequential(new AC_DriveByGyroTime(0,-.4, 1));
    	addSequential(new BM_RaiseToMid());
    	addSequential(new BM_TurnByGyro(-.7, 270));
    	addSequential(new AC_DriveByGyroTime(0, -.8, 2));
    	addSequential(new BM_TurnByGyro(.7, 0));
    	addSequential(new AC_DriveByGyroTime(0, -.8, 1));
    	addSequential(new BM_LiftClaw(true));
    	addSequential(new BM_OpenClaw(true));
    	addSequential(new BM_LiftClaw(false));
    }
}
