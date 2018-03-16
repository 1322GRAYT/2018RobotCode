package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 

/**
 *  Command Group Class: AM_TestEnc
 *  <Description>
 */
public class AM_TestEnc extends CommandGroup {

	private static String fieldData;                 // String of Field Data
	private static boolean ourSwitchLeftSide;        // Is Our Side of Our Alliance Switch on the Left Side of the Field?
	private static boolean ourScaleLeftSide;         // Is Our Side of the Scale on the Left Side of the Field?
	private static DriverStation.Alliance alliance;  // Our Alliance Color
	
		
	
	/**
	 *  Command Group Method: AM_TestEnc
	 *  <Description>
	 */	
    public AM_TestEnc() {
    	
    	fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	alliance = DriverStation.getInstance().getAlliance();
    	ourSwitchLeftSide = dtrmnOurSwitchPstn();
    	ourScaleLeftSide = dtrmnOurScalePstn();

    	
    	addSequential(new AC_ResetGyro());  	
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	
    	if(ourSwitchLeftSide) {
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	} else {
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	}

    	if(ourScaleLeftSide) {
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    		
    	} else {
    		addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	}
    	
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
