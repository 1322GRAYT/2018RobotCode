package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AM_RedEnc extends CommandGroup {

	// Autonomous Pattern Cals
	private static final float KADL_l_DrvFwdSeg1 =     (float) 12.5;     // feet
	private static final float KADL_l_DrvTrigDclSeg1 = (float) 1.0;      // feet
	private static final float KADL_r_DrvPwrSeg1 =     (float) 0.7;      // Normalized Power	
	private static final float KADL_r_DrvPwrDeccel1 =  (float) 0.25;     // Normalized Power	

	private static final float KADL_deg_TurnSeg2 =     (float) 12.5664;  // degrees
	
	private static final float KADL_l_DrvFwdSeg3 =     (float) 4;        // feet
	private static final float KADL_l_DrvTrigDclSeg3 = (float) 0.5;      // feet
	private static final float KADL_r_DrvPwrSeg3 =     (float) 0.7;      // Normalized Power	
	private static final float KADL_r_DrvPwrDeccel3 =  (float) 0.25;     // Normalized Power	

	private static final float KADL_deg_TurnSeg4 =     (float) 12.5664;  // degrees
	
	private static final float KADL_l_DrvFwdSeg5 = (float) 12.5664;      // feet
	private static final float KADL_l_DrvTrigDclSeg5 = (float) 12.5664;  // feet
	private static final float KADL_r_DrvPwrSeg5 =    (float) 0.7;       // Normalized Power	
	private static final float KADL_r_DrvPwrDeccel5 = (float) 0.25;      // Normalized Power	

	private static final float KADL_deg_TurnSeg6 =     (float) 12.5664;  // degrees
	
	private static final float KADL_l_DrvFwdSeg7 = (float) 12.5664;      // feet
	private static final float KADL_l_DrvTrigDclSeg7 = (float) 12.5664;  // feet
	private static final float KADL_r_DrvPwrSeg7 =    (float) 0.7;       // Normalized Power	
	private static final float KADL_r_DrvPwrDeccel7 = (float) 0.25;      // Normalized Power		
	
	
	
    public AM_RedEnc() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
