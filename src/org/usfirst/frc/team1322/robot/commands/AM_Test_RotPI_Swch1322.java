package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Class: AM_RedEnc - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_Test_RotPI_Swch1322 extends CommandGroup {

	private static String fieldData;                 // String of Field Data
	private static boolean ourSwitchNearSide;        // Is Our Side of Our Alliance Switch on Our Robots Side of the Field?
	private static boolean ourScaleNearSide;         // Is Our Side of the Scale on Our Robots Side of the Field?
	private static DriverStation.Alliance alliance;  //Our Alliance Color


	/** KAMG_t_PostMoveDly: Time Delay Movement prior to Starting another
	 * Drive or Rotate Movement to allow the robot and sensors/encoders
	 * to stabilize. (sec). */
	public static final float KAMG_t_PostMoveDly = (float)1.0; // sec
	
	/** KAMG_t_EncdrRstDly: Time Delay after Encoder Counter Reset. (sec). */
	public static final double KAMG_t_EncdrRstDly = 0.5; // sec
	
	
    public AM_Test_RotPI_Swch1322() {
    	
    //	fieldData = DriverStation.getInstance().getGameSpecificMessage();
    //	alliance = DriverStation.getInstance().getAlliance();
    //	ourSwitchNearSide = dtrmnOurSwitchPstn();
    //	ourScaleNearSide = dtrmnOurScalePstn();

   
    addSequential(new AC_ResetGyro());
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_ResetEncoders(KAMG_t_EncdrRstDly));
    addSequential(new AC_TurnByGyroPI(true, true, 90.0));     // CW to 90
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, false, -90.0));   // CCW to 270
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, true, 0.0));      // CW to 0
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, false,-180.0));   // CCW to 180
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, true, 90.0));     // CW to 90
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, false, 0.0));     // CCW to 0
	addSequential(new AC_TimeDelay(KAMG_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false, true, 0.0));     // disable
	
	
/*	
	if(ourSwitchNearSide) {
        	addSequential(new AC_DriveEncdrByDist((float)2.5, (float)0.4, (float)1.0, (float)0.15, true));
    	}
    	else 
    	{
    		addSequential(new AC_DriveEncdrByDist((float)13.00, (float)0.8, (float)2.0, (float)0.20, true));
    	}	
*/	  		
    		
    }
    
    
    /** Method: dtrmnOurSwitchPstn() -  Calculate if the our color of the Switch
     * on our Alliance Half of the field is on the near-side wrt. our robot
     * starting Position. */
    private boolean dtrmnOurSwitchPstn() {
    	if(fieldData.length() > 0) {
    		return (fieldData.charAt(0) == getColorFromAlliance(alliance));
   			}
    	else {
    		return false;
    	}
    }
   
   /** Method: dtrmnOurScalePstn() -  Calculate if the our color of the Switch
     * on our Alliance Half of the field is on the near-side wrt. our robot
     * starting Position.  */
   private boolean dtrmnOurScalePstn() {
	   	if(fieldData.length() > 0) {
		   return fieldData.charAt(1) == getColorFromAlliance(alliance);
   		}
	   	else {
   			return false;
   		}  
   }
   
   private char getColorFromAlliance(DriverStation.Alliance alliance) {
	    if(alliance.equals(DriverStation.Alliance.Red)) {
   			return "R".charAt(0);
   		}
	    else {
   			return "B".charAt(0);
   		}
   }

}
