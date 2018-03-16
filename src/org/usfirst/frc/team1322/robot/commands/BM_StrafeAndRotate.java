package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_DriveCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


   /**
     * Command Class: BM_StrafeAndRotate - Autonomous Command to Strafe
     *  to the side and rotate to a desired position simultaneously to move
     *  sideways in an arc about point.
     */
    public class BM_StrafeAndRotate extends Command {
	
	    // Autonomous Pattern Vars
	    private Timer   RotTmOut = new Timer();
	    private boolean ModeIsAuton;
	    private boolean RotClckWise;
	    private double  RotDsrdAng;
		private double  RotFdbkAng;
		private double  RotFdbkAngInit;	
		private double  RotAngInitDelt;
	    private double  RotAng90Pct;
	    private double  RotPwrCmndAbs;
	    private double  RotPwrCmnd;
	    private double  RotPwrCmndAdj;
	    private double  StrfPwrCmnd;
	    private double  StrfPwrCmndLim;
	    private double  DrvPwrCmnd;
	    private boolean LftHldEnbl;
        private double  LftPwrCmnd;
 	
	
    /**
      *  Command Method: AC_DriveEncdrByDist - Autonomous Command to Strafe
      *  to the side and rotate to a desired position simultaneously to move
      *  sideways in an arc about point.
      *  @param1: Mode is Autonomous (Not Tele-Op)?      (boolean)	
      *  @param2: Desired Rotation ClockWise?            (boolean)	
      *  @param3: Desired Rotation Position Angle        (float: degrees)	
      *  @param4: Desired Rotate Power (Abs Value)       (float: normalized power)
      *  @param5: Desired Strafe Power (+=right,-=left)  (float: normalized power)
      *  @param5: Drive Power Correction (+=Fwd,-=Rvrs)  (float: normalized power)
      *  @param6: Enable Lift Hold Function?             (boolean)
      *   */
    public BM_StrafeAndRotate(boolean ModeIsAuton,
    		                  boolean RotClckWise,
                              float   RotDsrdAng,
                              float   RotPwrCmndAbs,
                              float   StrfPwrCmnd,
                              float   DrvPwrCmnd,
                              boolean LftHldEnbl) {
        requires(Robot.kDRIVE);        
        // requires(Robot.kLIFT);
        this.ModeIsAuton = ModeIsAuton;
        this.RotClckWise = RotClckWise;
        this.RotDsrdAng = RotDsrdAng;
        this.RotPwrCmndAbs = RotPwrCmndAbs;
        this.StrfPwrCmnd = StrfPwrCmnd;
        this.DrvPwrCmnd = DrvPwrCmnd;
        this.LftHldEnbl =  LftHldEnbl;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	double RotPwrTemp;
    	
    	StrfPwrCmndLim = 0.0;
    	
    	RotTmOut.reset();
    	RotTmOut.start();
        if ((K_CmndCal.KCMD_b_RotRstGyroOnInit == true) ||
        	(ModeIsAuton == false))
        	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	
    	RotFdbkAngInit = Robot.kSENSORS.getGyroAngle(); 	
    	RotAngInitDelt = RotDsrdAng - RotFdbkAngInit;
        RotAng90Pct = RotAngInitDelt * 0.9;	
        
        RotPwrTemp = (double)Math.abs(RotPwrCmndAbs);
        
    	if(RotPwrTemp < 0.3) {
    		System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		RotPwrTemp = 0.3;
    	}
        RotPwrCmndAbs = RotPwrTemp;        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	float  turnDirctnScalar = (float)1.0;
    	float  nearTrgtScalar =   (float)1.0;
    	double StrfPwrCmndDelt;
    	
        /* TurnTmOut is a Free Running Timer */	

    	// Rate Limit the Strafing Power
    	StrfPwrCmndLim = USERLIB.RateLimOnInc(StrfPwrCmnd,
    			                              StrfPwrCmndLim,
    			                              K_DriveCal.KDRV_r_StrfPwrDeltIncLimMax);
    	
    	// Determine Rotate Power
        RotFdbkAng = Robot.kSENSORS.getGyroAngle();

        if (RotClckWise == false) {
    		// Turn Counter-ClockWise
    		turnDirctnScalar = (float)-1;
        } else {
    		// Turn ClockWise 
    		turnDirctnScalar = (float)1;        	        	
        }
        
    	if(RotFdbkAng >= RotAng90Pct) {
    		nearTrgtScalar = (float)0.50;   		
    	}
    	RotPwrCmndAdj = (double)turnDirctnScalar * (double)nearTrgtScalar * RotPwrCmnd;
    	
		Robot.kDRIVE.mechDrive(StrfPwrCmndLim, DrvPwrCmnd, RotPwrCmndAdj);
		
		
		// Determine Lift Power
	    // Keep Lift in Elevated Position
	    if ((ModeIsAuton == false) &&
	    	(LftHldEnbl == true) &&
	    	(Robot.kLIFT.getMidSen() == true) &&
	    	(Robot.kLIFT.getHighSen() == true)) {
	        // PwrCube not sensed by N/C Sensors
	    	LftPwrCmnd = (double)K_LiftCal.KLFT_r_LiftMtrHldPwr;	
	    } else {
	    	// PwrCube is sensed by N/C Sensor
	    	LftPwrCmnd = 0.0;	
	    }
	    
	    Robot.kLIFT.setSpeed(LftPwrCmnd);  
  	    
	    
  	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
  	        updateSmartDashData();    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean exitCond = false;
   	 
    	if ((RotClckWise == true) &&
    		(RotFdbkAng >= RotDsrdAng)) {
    		exitCond = true;
	    } else if ((RotClckWise == false) &&
	    		   (RotFdbkAng <= RotDsrdAng)) {
	    	exitCond = true;
        } else if ((ModeIsAuton == true) &&
        		   (RotTmOut.get() >= K_CmndCal.KCMD_t_SideArcSafetyTmOut)) {
	    	exitCond = true;	    		
    	}
     
    return (exitCond == true);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
  	    Robot.kLIFT.setSpeed(0.0);
  	    RotTmOut.stop();	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

    
	// Call to Update SmartDash Data for Display
    protected void updateSmartDashData() {
    	double EncdrCnt[] = new double[4]; 

    	//Gyro
    	SmartDashboard.putNumber("Gyro Angle : ", RotFdbkAng);
    	//Drive Speeds
    	SmartDashboard.putNumberArray("Encoder Velocity : ", Robot.kSENSORS.getEncodersVelRaw());
    	SmartDashboard.putNumberArray("Encoder Counts : ", Robot.kSENSORS.getEncodersCnt());
    	SmartDashboard.putNumberArray("Encoder RPM : ", Robot.kSENSORS.getEncodersRPM());
    	SmartDashboard.putNumberArray("Wheel RPM : ", Robot.kSENSORS.getWhlsRPM());
    	SmartDashboard.putBoolean("Rotate ClockWise? : ", RotClckWise);
        SmartDashboard.putNumber("Rotate Timeout Timer : ", RotTmOut.get());
    	SmartDashboard.putNumber("Rotate Desired Angle : ", RotDsrdAng);
    	SmartDashboard.putNumber("Rotate Feedback Angle : ", RotFdbkAng);
    	SmartDashboard.putNumber("Rotate Feedback Angle Init : ", RotFdbkAngInit);
    	SmartDashboard.putNumber("Rotate Angle Init Delta : ", RotAngInitDelt);
    	SmartDashboard.putNumber("Rotate 90 Percent Angle : ", RotAng90Pct);
    	SmartDashboard.putNumber("Rotate Power Cmnd : ", RotPwrCmndAbs);
    	SmartDashboard.putNumber("Rotate Power Cmnd : ", RotPwrCmnd);
    	SmartDashboard.putNumber("Rotate Power Cmnd Adjusted : ", RotPwrCmndAdj);
    	SmartDashboard.putNumber("Strafe Power Cmnd : ", StrfPwrCmnd);
    	SmartDashboard.putNumber("Strafe Power Cmnd : ", DrvPwrCmnd);
    	SmartDashboard.putBoolean("Lift Hold Enable during Side Arc? : ", LftHldEnbl);
    	SmartDashboard.putNumber("Lift Hold Power Cmnd : ", LftPwrCmnd);    	
    	
    	EncdrCnt = Robot.kSENSORS.getEncodersCnt(); 
    	//Update SmartDashboard
    	System.out.print("Raw Encoder Counts Ref A : " + EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlA_Slct]);
    	System.out.print("Raw Encoder Counts Ref B : " + EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlB_Slct]);
    	System.out.println("Rotate ClockWise? : " + RotClckWise);
    	System.out.println("Rotate Timeout Timer : " + RotTmOut.get());
    	System.out.println("Rotate Desired Angle : " + RotDsrdAng);
    	System.out.println("Rotate Feedback Angle : " + RotFdbkAng);
    	System.out.println("Rotate Feedback Angle Init : " + RotFdbkAngInit);
    	System.out.println("Rotate Angle Init Delta : " + RotAngInitDelt);
    	System.out.println("Rotate 90 Percent Angle : " + RotAng90Pct);
    	System.out.println("Rotate Power Cmnd : " + RotPwrCmndAbs);
    	System.out.println("Rotate Power Cmnd : " + RotPwrCmnd);
    	System.out.println("Rotate Power Cmnd Adjusted : " + RotPwrCmndAdj);
    	System.out.println("Strafe Power Cmnd : " + StrfPwrCmnd);
    	System.out.println("Strafe Power Cmnd : " + DrvPwrCmnd);
    	System.out.println("Lift Hold Enable during Side Arc? : " + LftHldEnbl);
    	System.out.println("Lift Hold Power Cmnd : " + LftPwrCmnd); 
    	
    }
    
    
}
