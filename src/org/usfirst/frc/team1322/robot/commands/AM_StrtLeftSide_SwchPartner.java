package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Class: AM_Comp_SwchAlliancePartners - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_StrtLeftSide_SwchPartner extends CommandGroup {

	private static String fieldData; //String of Field Data
	private static boolean isClose; //Is Our Color Close
	private static DriverStation.Alliance alliance; //Our Alliance Color

	
    public AM_StrtLeftSide_SwchPartner() {
    	
    	//fieldData = DriverStation.getInstance().getGameSpecificMessage(); //Return 3 Character String (RLR) (LRL) etc.
    	//alliance = DriverStation.getInstance().getAlliance();
    	//isClose = calculateClose();

    	addSequential(new BM_RaiseToMid());
    	addSequential(new BM_LiftClaw(false));
    	addSequential(new AC_DriveEncdrByDist((float)18.0, (float)0.8, (float)2.0, (float)0.2, true));        // Drive Forward 18 ft, slow down at 2 ft from end.
    	addSequential(new BM_TurnByGyro(0.7, 90.0));                                                          // Turn CW 90deg
    	addSequential(new AC_DriveEncdrByDist((float)2.5, (float)0.4, (float)1.0, (float)0.15, true));        // Drive Forward 2.5 ft, slow down 1.0 ft from end.
    	addSequential(new BM_TurnByGyro(0.5, 90.0));                                                          // Turn CW 90deg
    	//if(isClose) {
    		addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.4, (float)0.2, true));     // Drive Forward 2.0 ft, slow down 0.4 ft from end.
    		addSequential(new AC_RunClawWheelsInOut(false));
    		addSequential(new AC_DriveEncdrByDist((float)0.3, (float)0.25, (float)0.0, (float)0.1, false));   // Drive Reverse 0.4 ft.
    		addSequential(new BM_OpenClaw(true));
    		addSequential(new BM_LowerToLow());
    		addSequential(new BM_OpenClaw(false));
        	addSequential(new BM_RaiseToMid());    		
    		addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.4, (float) 0.5, (float) 0.2, false));  // Drive Reverse 2.0 ft, slow down 0.5 ft from end.
    		addSequential(new BM_TurnByGyro(0.7, 90.0));                                                      // Turn ClockWise 90 degrees
    		addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.5, (float)0.75, (float)0.2, true));    // Drive Forward 4.0 ft, slow down at 0.75 ft from end.
    		addSequential(new BM_TurnByGyro(0.5, 90.0));                                                      // Turn Clockwise 90 Degrees
    	//}else {
    		//Go Far
    	//}	
    	
    }
    
    //Calculate if the color is close
    private boolean calculateClose() {
    	if(fieldData.charAt(0) == getColorFromAlliance(alliance))  {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    private char getColorFromAlliance(DriverStation.Alliance alliance) {
    	if(alliance.equals(DriverStation.Alliance.Red)) {
    		return "R".charAt(0);
    	}else {
    		return "B".charAt(0);
    	}
    }
}
