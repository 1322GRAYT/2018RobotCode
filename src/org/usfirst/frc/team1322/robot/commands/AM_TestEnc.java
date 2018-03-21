package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal; 


/**
 *  Command Group Class: AM_TestEnc
 *  <Description>
 */
public class AM_TestEnc extends CommandGroup {

	
	/**
	 *  Command Group Method: AM_TestEnc
	 *  <Description>
	 */	
    public AM_TestEnc() {

    	addSequential(new AC_ResetGyro());  	
    	addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	addSequential(new AC_TimeDelay(K_CmndCal.KCMD_t_PostMoveDly));
        addSequential(new AC_ResetEncoders(K_CmndCal.KCMD_t_EncdrRstDly));    	
    	System.out.println("TestEnc"); 	
    	
    }
        
}
