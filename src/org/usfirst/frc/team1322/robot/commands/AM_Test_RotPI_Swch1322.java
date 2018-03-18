package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 * Class: AM_RedEnc - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_Test_RotPI_Swch1322 extends CommandGroup {

	
    public AM_Test_RotPI_Swch1322() {
    	
   
    addSequential(new AC_ResetGyro());
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    addSequential(new AC_TurnByGyroPI(true, 90.0, false));     // CW to 90
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false, -90.0, false));   // CCW to 270
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, 0.0, false));      // CW to 0
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false,-180.0, false));   // CCW to 180
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, 90.0, false));     // CW to 90
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false, 0.0, false));     // CCW to 0
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, 0.0, false));     // disable
    
/*    
	if((Robot.kAUTON.getScaleDataCaptured() == true) &&
	   (Robot.kAUTON.getOurSwitchLeftSide() == true)) { // Our Alliance Switch is on Left Side

	}
	else if ((Robot.kAUTON.getScaleDataCaptured() == true) &&
			     (Robot.kAUTON.getOurSwitchLeftSide() == false)) { // Our Alliance Switch is on Right Side

	}
	else {

	} 
*/
    
   }

}
