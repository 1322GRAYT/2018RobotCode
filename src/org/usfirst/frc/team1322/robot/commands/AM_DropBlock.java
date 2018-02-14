package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_DropBlock extends CommandGroup {

	private final String fd = DriverStation.getInstance().getGameSpecificMessage(); //Store Game specific data to string
	private final char[] fieldData = {fd.charAt(0), fd.charAt(1), fd.charAt(2)}; //Make Game Data more accessable
	private final int dsNum = DriverStation.getInstance().getLocation(); //Store driver station number to string
	
    public AM_DropBlock() {
    	
    	
    	
    	
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
