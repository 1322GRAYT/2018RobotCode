package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_TestEnc extends CommandGroup {

    public AM_TestEnc() {
    	if(DriverStation.getInstance().getAlliance().equals(Alliance.Red)) {
    		addSequential(new AM_Comp_SwchAlliancePartners());
/*    		if((DriverStation.getInstance().isFMSAttached() == true) &&
    		   (DriverStation.getInstance().getGameSpecificMessage() == Code)) */
    	}else if(DriverStation.getInstance().getAlliance().equals(Alliance.Blue)) {
    		//addSequential(new AM_BlueEnc());
    	}
    }
}
