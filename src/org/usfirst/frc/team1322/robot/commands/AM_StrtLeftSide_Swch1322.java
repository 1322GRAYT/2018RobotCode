package org.usfirst.frc.team1322.robot.commands;

import javax.swing.text.Position;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Class: AM_Comp_Swch1322 - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_StrtLeftSide_Swch1322 extends CommandGroup {
	
	private static String fieldData;                 // String of Field Data
	private static boolean ourSwitchNearSide;        // Is Our Side of Our Alliance Switch on Our Robots Side of the Field?
	private static boolean ourScaleNearSide;         // Is Our Side of the Scale on Our Robots Side of the Field?
	private static DriverStation.Alliance alliance;  //Our Alliance Color

	
    public AM_StrtLeftSide_Swch1322() {
    	
    	fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	alliance = DriverStation.getInstance().getAlliance();
    	ourSwitchNearSide = dtrmnOurSwitchPstn();
    	ourScaleNearSide = dtrmnOurScalePstn();

    	
    	addSequential(new BM_RaiseToMid());
    	addSequential(new BM_LiftClaw(false));
    	addSequential(new AC_DriveEncdrByDist((float)18.0, (float)0.8, (float)2.0, (float)0.2, true));        // Drive Forward 18 ft, slow down at 2 ft from end.
    	addSequential(new BM_TurnByGyro(0.7, 90.0));                                                          // Turn CW 90deg
    	if(ourSwitchNearSide) {
        	addSequential(new AC_DriveEncdrByDist((float)2.5, (float)0.4, (float)1.0, (float)0.15, true));    // Drive Forward 2.5 ft, slow down 1.0 ft from end.
    	    addSequential(new BM_TurnByGyro(0.5, 90.0)); // Turn CW 90deg
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.4, 0.5)); // 0.4 Power for 0.5 Seconds
    		addSequential(new AC_RunClawWheelsInOut(false));
/*    	    
    		addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.4, (float)0.2, true));     / / Drive Forward 2.0 ft, slow down 0.4 ft from end.
    		addSequential(new AC_RunClawWheelsInOut(false));
    		addSequential(new AC_DriveEncdrByDist((float)0.3, (float)0.25, (float)0.0, (float)0.1, false));   / / Drive Reverse 0.4 ft.
    		addSequential(new BM_OpenClaw(true));
    		addSequential(new BM_LowerToLow());
    		addSequential(new BM_OpenClaw(false));
        	addSequential(new BM_RaiseToMid());    		
    		addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.4, (float) 0.5, (float) 0.2, false));  / / Drive Reverse 2.0 ft, slow down 0.5 ft from end.
    		addSequential(new BM_TurnByGyro(0.7, 90.0));                                                      / / Turn ClockWise 90 degrees
    		addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.5, (float)0.75, (float)0.2, true));    / / Drive Forward 4.0 ft, slow down at 0.75 ft from end.
    		addSequential(new BM_TurnByGyro(0.5, 90.0));                                                      / / Turn Clockwise 90 Degrees
*/
    	}
    	else 
    	{
        	addSequential(new AC_DriveEncdrByDist((float)12.00, (float)0.8, (float)2.0, (float)0.20, true));  // Drive Forward 10.0 ft, slow down 2.0 ft from end.
    		addSequential(new BM_TurnByGyro(0.5, 90.0));                                                      // Turn Clockwise 90 Degrees
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.4, 0.5));                                             // 0.4 Power for 0.5 Seconds
    		addSequential(new AC_RunClawWheelsInOut(false));
/*
    		addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.5, (float)0.4, (float)0.2, true));     / / Drive Forward 2.0 ft, slow down 0.4 ft from end.
    		addSequential(new AC_RunClawWheelsInOut(false));
    		addSequential(new AC_DriveEncdrByDist((float)0.3, (float)0.25, (float)0.0, (float)0.1, false));   / / Drive Reverse 0.4 ft.
    		addSequential(new BM_OpenClaw(true));
    		addSequential(new BM_LowerToLow());
    		addSequential(new BM_OpenClaw(false));
        	addSequential(new BM_RaiseToMid());    		
    		addSequential(new AC_DriveEncdrByDist((float)2.0, (float)0.4, (float) 0.5, (float) 0.2, false));  / / Drive Reverse 2.0 ft, slow down 0.5 ft from end.
    		addSequential(new BM_TurnByGyro(0.7, -90.0));                                                     / / Turn ClockWise 90 degrees
    		addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.5, (float)0.75, (float)0.2, true));    / / Drive Forward 4.0 ft, slow down at 0.75 ft from end.
    		addSequential(new BM_TurnByGyro(0.5, -90.0));                                                     / / Turn Clockwise 90 Degrees
 */
    	}	
    	
    }

    /** Method: dtrmnOurSwitchPstn() -  Calculate if the our color of the Switch
      * on our Alliance Half of the field is on the near-side wrt. our robot
      * starting Position. */
    private boolean dtrmnOurSwitchPstn() {
    	boolean OurSideOfSwitchIsNear = false;
    	
    	if(fieldData.charAt(0) == getColorFromAlliance(alliance))  {
    		OurSideOfSwitchIsNear = true;
    	}
    	return (OurSideOfSwitchIsNear);
    }
    
    /** Method: dtrmnOurScalePstn() -  Calculate if the our color of the Switch
      * on our Alliance Half of the field is on the near-side wrt. our robot
      * starting Position.  */
    private boolean dtrmnOurScalePstn() {
 	    boolean OurSideOfScaleIsNear = false;
 	
 	    if(fieldData.charAt(1) == getColorFromAlliance(alliance))  {
 	    	OurSideOfScaleIsNear = true;
 	    }
 	    return (OurSideOfScaleIsNear);
    }
    
    
 private char getColorFromAlliance(DriverStation.Alliance alliance) {
    	if(alliance.equals(DriverStation.Alliance.Red)) {
    		return "R".charAt(0);
    	}else {
    		return "B".charAt(0);
    	}
    }
}

