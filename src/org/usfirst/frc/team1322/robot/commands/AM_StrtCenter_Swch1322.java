package org.usfirst.frc.team1322.robot.commands;

import javax.swing.text.Position;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 * Command Group Class: AM_StrtCenter_Swch1322
 * Autonomous Control Group Pattern: Robot Starts Left Side
 * and Robot puts PwrCube in the Switch.
 */
public class AM_StrtCenter_Swch1322 extends CommandGroup {
	
	private static String fieldData;                 // String of Field Data
	private static boolean ourSwitchLeftSide;        // Is Our Side of Our Alliance Switch on the Left Side of the Field?
	private static boolean ourScaleLeftSide;         // Is Our Side of the Scale on the Left Side of the Field?
	private static DriverStation.Alliance alliance;  // Our Alliance Color
	

	
	/**
	 * Command Group Method: AM_StrtCenter_Swch1322
	 * Autonomous Control Group Pattern: Robot Starts Left Side
	 * and Robot puts PwrCube in the Switch.
	 */	
    public AM_StrtCenter_Swch1322() {
    	
    	fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	alliance = DriverStation.getInstance().getAlliance();
    	ourSwitchLeftSide = dtrmnOurSwitchPstn();
    	ourScaleLeftSide = dtrmnOurScalePstn();


    	addSequential(new AC_ResetGyro());
    	addSequential(new BM_RaiseToMid());
		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_DriveEncdrByDist((float)5.0, (float)0.8, (float)1.0, (float)0.10, (float)0.0, true, true));
    	if(ourSwitchLeftSide) { // Our Alliance Switch is on Left Side
        	addSequential(new AC_TurnByGyro(-0.7, -90.0, true)); // Turn CCW to -90 deg
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
        	addSequential(new AC_DriveEncdrByDist((float)3.0, (float)0.7, (float)1.0, (float)0.10, (float)-90.0, true, true));
        	addSequential(new AC_TurnByGyro(0.7, 0.0, true)); // Turn CW to 0 deg
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
        	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.7, (float)1.0, (float)0.10, (float)0.0, true, true));
        	addSequential(new BM_RaiseToMid());
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
    	    addSequential(new BM_LiftClaw(false));
    	    addSequential(new BM_OpenClaw(true));
    		addSequential(new AC_TimeDelay((float)0.5));
    	    addSequential(new AC_DriveByGyroTime(-0.5, 0.0, 2.5, false));  // Strafe to the Left
    	}
    	else // Our Alliance Switch is on Right Side
    	{
        	addSequential(new AC_TurnByGyro(0.7, 90.0, true)); // Turn CW to 90 deg
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
        	addSequential(new AC_DriveEncdrByDist((float)3.0, (float)0.7, (float)1.0, (float)0.10, (float)90.0, true, true));
    	    addSequential(new AC_TurnByGyro(-0.7, 0.0, true)); // Turn CCW to 0 deg
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
        	addSequential(new AC_DriveEncdrByDist((float)4.0, (float)0.7, (float)1.0, (float)0.10, (float)0.0, true, true));
        	addSequential(new BM_RaiseToMid());
    	    addSequential(new AC_DriveByGyroTime(0.0, 0.5, 0.75, true));
    	    addSequential(new BM_LiftClaw(false));
    	    addSequential(new BM_OpenClaw(true));
    		addSequential(new AC_TimeDelay((float)0.5));
    	    addSequential(new AC_DriveByGyroTime(0.5, 0.0, 2.5, false));  // Strafe to the Right    		
    	}
    	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    	addSequential(new AC_DriveEncdrByDist((float)6.0, (float)0.8, (float)1.0, (float)0.15, (float)0.0, true, false));
    	
    }

    /** Method: dtrmnOurSwitchPstn() -  Calculate if the our color of the Switch
      * on our Alliance Half of the field is on the near-side wrt. our robot
      * starting Position. */
    private boolean dtrmnOurSwitchPstn() {
    	if(fieldData.length() > 0) {
//    		return (fieldData.charAt(0) == getColorFromAlliance(alliance));
    		return (fieldData.charAt(0) == 'L');
    	}else {
    		return false;
    	}
    	
    }

    
    /** Method: dtrmnOurScalePstn() -  Calculate if the our color of the Switch
      * on our Alliance Half of the field is on the near-side wrt. our robot
      * starting Position.  */
    private boolean dtrmnOurScalePstn() {
    	if(fieldData.length() > 0) {
//    		return (fieldData.charAt(1) == getColorFromAlliance(alliance));
    		return (fieldData.charAt(1) == 'L');
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

