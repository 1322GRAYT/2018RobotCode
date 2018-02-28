package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Class: AM_RedEnc - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_RedEnc extends CommandGroup {

	// Autonomous Pattern Cals
	//Segment 1: Drive 16.14 ft, slow down at 2 ft
	private static final float KADL_l_DrvFwdSeg1 =     (float) 16.14;    // feet was 16.14
	private static final float KADL_l_DrvTrigDclSeg1 = (float) 2.0;      // feet
	private static final float KADL_r_DrvPwrSeg1 =     (float) 0.8;      // Normalized Power	
	private static final float KADL_r_DrvPwrDcl1 =     (float) 0.2;      // Normalized Power	

	//Segment 2: Turn 90 degrees
	private static final float KADL_deg_TurnSeg2 =     (float) 90.0;     // degrees
	private static final float KADL_r_TurnPwrSeg2 =    (float) 0.7;      // Normalized Power
	
	//Segment 3: Drive 5 ft, slow down at 1 ft
	private static final float KADL_l_DrvFwdSeg3 =     (float) 5;        // feet
	private static final float KADL_l_DrvTrigDclSeg3 = (float) 1.0;      // feet
	private static final float KADL_r_DrvPwrSeg3 =     (float) 0.5;      // Normalized Power	
	private static final float KADL_r_DrvPwrDcl3 =     (float) 0.2;      // Normalized Power	

	//Segment 4: Turn 90 degrees
	private static final float KADL_deg_TurnSeg4 =     (float) 90.0;     // degrees
	private static final float KADL_r_TurnPwrSeg4 =    (float) 0.7;      // Normalized Power

	//Segment 5-8 Are For Going Close
	//Segment 5: Drive 3 ft, slow down at .8 ft
	private static final float KADL_l_DrvFwdSeg5 =     (float) 3.0;      // feet
	private static final float KADL_l_DrvTrigDclSeg5 = (float) 0.8;      // feet
	private static final float KADL_r_DrvPwrSeg5 =     (float) 0.5;      // Normalized Power	
	private static final float KADL_r_DrvPwrDcl5 =     (float) 0.2;      // Normalized Power	
	
	//Segment 6: Turn backwards 40 degrees
	private static final float KADL_deg_TurnSeg6 =     (float) 340.0;    // degrees
	private static final float KADL_r_TurnPwrSeg6 =    (float) -0.3;     // Normalized Power

	//Segment 7: Turn 40 Degrees
	private static final float KADL_deg_TurnSeg7 =     (float) 0.0;      // degrees
	private static final float KADL_r_TurnPwrSeg7 =    (float) 0.3;      // Normalized Power
	
	//Segment 8: Drive 12 ft, slow down at 12 ft
	private static final float KADL_l_DrvFwdSeg8 =     (float) 12.5664;  // feet
	private static final float KADL_l_DrvTrigDclSeg8 = (float) 12.5664;  // feet
	private static final float KADL_r_DrvPwrSeg8 =     (float) 0.7;      // Normalized Power	
	private static final float KADL_r_DrvPwrDcl8 =     (float) 0.25;     // Normalized Power
	
	//Segment 9- are for going far
	
	private static String fieldData; //String of Field Data
	private static boolean isClose; //Is Our Color Close
	private static DriverStation.Alliance alliance; //Our Alliance Color
	
    public AM_RedEnc() {
    	
    	//fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	//alliance = DriverStation.getInstance().getAlliance();
    	//isClose = calculateClose();

    	//addSequential(new BM_RaiseToMid());
    	//addParallel
    	addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg1, KADL_r_DrvPwrSeg1,
    			                            KADL_l_DrvTrigDclSeg1, KADL_r_DrvPwrDcl1,
    			                            true));
    	addSequential(new BM_LiftClaw(false));
    	addSequential(new BM_TurnByGyro(KADL_r_TurnPwrSeg2, KADL_deg_TurnSeg2));
    	addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg3, KADL_r_DrvPwrSeg3,
                                              KADL_l_DrvTrigDclSeg3, KADL_r_DrvPwrDcl3,
                                              true));
    	addSequential(new BM_TurnByGyro(KADL_r_TurnPwrSeg4, KADL_deg_TurnSeg4));
    	
    	//if(isClose) {
    		addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg5, KADL_r_DrvPwrSeg5,
                							  KADL_l_DrvTrigDclSeg5, KADL_r_DrvPwrDcl5,
                                              true));
    		addSequential(new BM_TurnByGyro(KADL_r_TurnPwrSeg6, KADL_deg_TurnSeg6));
    		addSequential(new AC_RunClawWheelsInOut(false));
    		addSequential(new BM_TurnByGyro(KADL_r_TurnPwrSeg7, KADL_deg_TurnSeg7));
    		addSequential(new BM_LowerToLow());    
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
