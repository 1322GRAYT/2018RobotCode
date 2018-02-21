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
    	addParallel(new AC_DriveByGyroTime(1,0, 3));
        addSequential(new BM_RaiseToMid());
    	addSequential(new BM_TurnByGyro(.5, 90));
    	addParallel(new AC_DriveByGyroTime(1, 0, 2));
    	addParallel(new BM_OpenClaw(true));
    }
}
