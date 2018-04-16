package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 * Class: AM_Test_RotPI - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_Test_RotPI extends CommandGroup {

	
    public AM_Test_RotPI() {
    	
    addSequential(new AC_ResetGyro());
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));
    addSequential(new AC_TurnByGyroPI(true, 90.0));     // CW to 90
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false, -90.0));   // CCW to 270
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, 0.0));      // CW to 0
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false,-180.0));   // CCW to 180
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, 90.0));     // CW to 90
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(false, 0.0));     // CCW to 0
	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
    addSequential(new AC_TurnByGyroPI(true, 0.0));     // disable
	System.out.println("Test_RotPI");
    
   }

}
