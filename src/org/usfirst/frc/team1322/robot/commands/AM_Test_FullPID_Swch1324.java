package org.usfirst.frc.team1322.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Class: AM_RedEnc - Autonomous Control Mode Pattern Lever Left.
 */
public class AM_Test_FullPID_Swch1324 extends CommandGroup {

	// Autonomous Pattern Cals
	//Segment 1: Drive 16.14 ft, slow down at 2 ft
	private static final float KADL_l_DrvFwdSeg1 =     (float) 17.00;    // feet was 16.14
	private static final float KADL_l_DrvTrigDclSeg1 = (float) 2.0;      // feet
	private static final float KADL_n_DrvRPMSeg1 =     (float) 0.8;      // Normalized Power	
	private static final float KADL_n_DrvRPMDcl1 =     (float) 0.2;      // Normalized Power	

	//Segment 2: Turn 90 degrees
	private static final float KADL_deg_TurnSeg2 =     (float) 90.0;     // degrees
	
	//Segment 3: Drive 5 ft, slow down at 1 ft
	private static final float KADL_l_DrvFwdSeg3 =     (float) 5;        // feet
	private static final float KADL_l_DrvTrigDclSeg3 = (float) 1.0;      // feet
	private static final float KADL_n_DrvRPMSeg3 =     (float) 0.5;      // Normalized Power	
	private static final float KADL_n_DrvRPMDcl3 =     (float) 0.2;      // Normalized Power	

	//Segment 4: Turn 90 degrees
	private static final float KADL_deg_TurnSeg4 =     (float) 90.0;     // degrees

	//Segment 5-8 Are For Going Close
	//Segment 5: Drive 3 ft, slow down at .8 ft
	private static final float KADL_l_DrvFwdSeg5 =     (float) 3.0;      // feet
	private static final float KADL_l_DrvTrigDclSeg5 = (float) 0.8;      // feet
	private static final float KADL_n_DrvRPMSeg5 =     (float) 0.3;      // Normalized Power	
	private static final float KADL_n_DrvRPMDcl5 =     (float) 0.2;      // Normalized Power	

	//Segment 6: Drive 3 ft, slow down at .8 ft
	private static final float KADL_l_DrvFwdSeg6 =     (float) 0.3;      // feet
	private static final float KADL_l_DrvTrigDclSeg6 = (float) 0.1;      // feet
	private static final float KADL_n_DrvRPMSeg6 =     (float) 0.3;      // Normalized Power	
	private static final float KADL_n_DrvRPMDcl6 =     (float) 0.1;      // Normalized Power	

	//Segment 7: Drive 3 ft, slow down at .8 ft
	private static final float KADL_l_DrvFwdSeg7 =     (float) 4.0;      // feet
	private static final float KADL_l_DrvTrigDclSeg7 = (float) 0.5;      // feet
	private static final float KADL_n_DrvRPMSeg7 =     (float) 0.4;      // Normalized Power	
	private static final float KADL_n_DrvRPMDcl7 =     (float) 0.20;     // Normalized Power	
	
	//Segment 8: Turn ClockWise 90 degrees
	private static final float KADL_deg_TurnSeg8 =     (float) 90.0;     // degrees

	//Segment 9: Drive 5.0 ft, slow down at 4.0 ft
	private static final float KADL_l_DrvFwdSeg9 =     (float) 5.0;      // feet
	private static final float KADL_l_DrvTrigDclSeg9 = (float) 1.0;      // feet
	private static final float KADL_n_DrvRPMSeg9 =     (float) 0.5;      // Normalized Power	
	private static final float KADL_n_DrvRPMDcl9 =     (float) 0.25;     // Normalized Power

	//Segment 10: Turn Clockwise 90 Degrees
	private static final float KADL_deg_TurnSeg10 =     (float) 9.0;      // degrees
	

	
	//Segment 11- are for going far
	
	
	private static String fieldData; //String of Field Data
	private static boolean isClose; //Is Our Color Close
	private static DriverStation.Alliance alliance; //Our Alliance Color
	
	
    public AM_Test_FullPID_Swch1324() {
    	
    	//fieldData = DriverStation.getInstance().getGameSpecificMessage();
    	//alliance = DriverStation.getInstance().getAlliance();
    	//isClose = calculateClose();

    	addSequential(new BM_RaiseToMid());
    	addSequential(new BM_LiftClaw(false));
    	addParallel(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg1, KADL_n_DrvRPMSeg1,
    			                            KADL_l_DrvTrigDclSeg1, KADL_n_DrvRPMDcl1,
    			                            true));
    	addSequential(new BM_LiftClaw(false));
    	addSequential(new AC_TurnByGyroPI(true, true, KADL_deg_TurnSeg2));
    	addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg3, KADL_n_DrvRPMSeg3,
                                              KADL_l_DrvTrigDclSeg3, KADL_n_DrvRPMDcl3,
                                              true));
    	addSequential(new AC_TurnByGyroPI(true, true, KADL_deg_TurnSeg4));
    	
    	//if(isClose) {
			addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg5, KADL_n_DrvRPMSeg5,
				                                  KADL_l_DrvTrigDclSeg5, KADL_n_DrvRPMDcl5,
	                                              true));
			addSequential(new AC_RunClawWheelsInOut(false));
			addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg6, KADL_n_DrvRPMSeg6,
			                                      KADL_l_DrvTrigDclSeg6, KADL_n_DrvRPMDcl6,
			                                      false));
			
			addSequential(new BM_OpenClaw(true));
			addSequential(new BM_LowerToLow());
			addSequential(new BM_OpenClaw(false));
			addSequential(new BM_RaiseToMid());    		
			addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg7, KADL_n_DrvRPMDcl7,
			                                      KADL_l_DrvTrigDclSeg7, KADL_n_DrvRPMDcl7,
			                                      false));
			addSequential(new AC_TurnByGyroPI(true, true,  KADL_deg_TurnSeg8));
			addSequential(new AC_DriveEncdrByDist(KADL_l_DrvFwdSeg9, KADL_n_DrvRPMDcl9,
			                                      KADL_l_DrvTrigDclSeg9, KADL_n_DrvRPMDcl9,
			                                      true));
			addSequential(new AC_TurnByGyroPI(true, true, KADL_deg_TurnSeg10));
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
