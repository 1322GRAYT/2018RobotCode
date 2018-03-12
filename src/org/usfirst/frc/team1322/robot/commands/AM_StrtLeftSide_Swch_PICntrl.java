package org.usfirst.frc.team1322.robot.commands;

import javax.swing.text.Position;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Command Group Class: AM_StrtLeftSide_Swch1322
 * Autonomous Control Group Pattern: Robot Starts Left Side
 * and Robot puts PwrCube in the Switch.
 */
public class AM_StrtLeftSide_Swch_PICntrl extends CommandGroup {
	
	private static String fieldData;                 // String of Field Data
	private static boolean ourSwitchLeftSide;        // Is Our Side of Our Alliance Switch on the Left Side of the Field?
	private static boolean ourScaleLeftSide;         // Is Our Side of the Scale on the Left Side of the Field?
	private static DriverStation.Alliance alliance;  // Our Alliance Color


	/** KAMG_t_PostMoveDly: Time Delay Movement prior to Starting another
	 * Drive or Rotate Movement to allow the robot and sensors/encoders
	 * to stabilize. (sec). */
	public static final float KAMG_t_PostMoveDly = (float)0.250; // sec
	
	/** KAMG_t_EncdrRstDly: Time Delay after Encoder Counter Reset. (sec). */
	public static final double KAMG_t_EncdrRstDly = 0.250; // sec
	

	/**
	 * Command Group Method: AM_StrtLeftSide_Swch1322
	 * Autonomous Control Group Pattern: Robot Starts Left Side
	 * and Robot puts PwrCube in the Switch.
	 */	
    public AM_StrtLeftSide_Swch_PICntrl() {
    	
    	fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	alliance = DriverStation.getInstance().getAlliance();
    	ourSwitchLeftSide = dtrmnOurSwitchPstn();
    	ourScaleLeftSide = dtrmnOurScalePstn();


    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_RaiseToMid());
	  	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));    	
    	if(ourSwitchLeftSide) { // Our Alliance Switch is on Left Side
        	addSequential(new AC_DriveEncdrByDistPI((float)12.0, (float)0.8, (float)1.5, (float)0.20, (float)0.0, true));
        	addSequential(new AC_TurnByGyroPI(true, 90.0)); // Turn CW to 90 deg
        	addSequential(new BM_RaiseToMid());
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75));
    	    addSequential(new BM_LiftClaw(false));
    	    addSequential(new BM_OpenClaw(true));
    	  	addSequential(new AC_TimeDelay((float)0.5));
        	addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));    	
        	addSequential(new AC_DriveEncdrByDistPI((float)2.0, (float)0.6, (float)0.5, (float)0.20, (float)90.0, false));
    	    addSequential(new AC_DriveByGyroTime(-0.5, 0.0, 2.5));  // Straffe to the Left
       		addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));
        	addSequential(new AC_DriveEncdrByDistPI((float)4.0, (float)0.8, (float)1.0, (float)0.20, (float)90.0, true));
        	addSequential(new AC_TurnByGyroPI(true, 180.0)); // Turn CW to 180 deg
    	}
    	else // Our Alliance Switch is On Right Side
    	{
        	addSequential(new AC_DriveEncdrByDistPI((float)17.0, (float)1.0, (float)2.0, (float)0.10, (float)0.0, true));
        	addSequential(new AC_TurnByGyroPI(true, 90.0)); // Turn CW to 90 deg
    	  	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));
        	addSequential(new AC_DriveEncdrByDistPI((float)16.0, (float)1.0, (float)2.0, (float)0.10, (float)90.0, true));
    	    addSequential(new AC_TurnByGyroPI(true, 180.0)); // Turn CW to 180 deg
    		addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));
        	addSequential(new AC_DriveEncdrByDistPI((float)4.0, (float)0.8, (float)1.0, (float)0.20, (float)180.0, true));
    	    addSequential(new AC_TurnByGyroPI(true, 270.0)); // Turn CW to 270 deg
    		addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));
        	addSequential(new BM_RaiseToMid());
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75));
    	    addSequential(new BM_LiftClaw(false));
    	    addSequential(new BM_OpenClaw(true));
    	}
    }


    /** Method: dtrmnOurSwitchPstn() -  Calculate if the our color of the Switch
      * on our Alliance Half of the field is on the near-side wrt. our robot
      * starting Position. */
    private boolean dtrmnOurSwitchPstn() {
    	if(fieldData.length() > 0) {
    		return (fieldData.charAt(0) == getColorFromAlliance(alliance));
    	}else {
    		return false;
    	}
    	
    }

    
    /** Method: dtrmnOurScalePstn() -  Calculate if the our color of the Switch
      * on our Alliance Half of the field is on the near-side wrt. our robot
      * starting Position.  */
    private boolean dtrmnOurScalePstn() {
    	if(fieldData.length() > 0) {
    		return fieldData.charAt(1) == getColorFromAlliance(alliance);
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

