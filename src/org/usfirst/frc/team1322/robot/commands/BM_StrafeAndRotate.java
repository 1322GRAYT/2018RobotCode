package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

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
	    private double  RotAng70Pct;	
	    private double  RotAng90Pct;
	    private double  RotPwrCmndPct;
	    private double  RotPwrCmnd;
	    private double  RotPwrCmndAdj;
	    private double  StrfPwrCmnd;
	    private boolean LftHldEnbl;
        private double  LftPwrCmnd;
 	
	
    /**
      *  Command Method: AC_DriveEncdrByDist - Autonomous Command to Strafe
      *  to the side and rotate to a desired position simultaneously to move
      *  sideways in an arc about point.
      *  @param1: Mode is Autonomous (Not Tele-Op)?      (boolean)	
      *  @param2: Desired Rotation ClockWise?            (boolean)	
      *  @param3: Desired Rotation Position Angle        (float: degrees)	
      *  @param4: Desired Rotate Power (Abs Value)       (float: percent)
      *  @param5: Desired Strafe Power (+=right,-=left)  (float: normalized power)
      *  @param6: Enable Lift Hold Function?             (boolean)
      *   */
    public BM_StrafeAndRotate(boolean ModeIsAuton,
    		                  boolean RotClckWise,
                              float   RotDsrdAng,
                              float   RotPwrCmndPct,
                              float   StrfPwrCmnd,
                              boolean LftHldEnbl) {
        requires(Robot.kDRIVE);        
        // requires(Robot.kLIFT);
        this.ModeIsAuton = ModeIsAuton;
        this.RotClckWise = RotClckWise;
        this.RotDsrdAng = RotDsrdAng;
        this.RotPwrCmndPct = RotPwrCmndPct;
        this.StrfPwrCmnd = StrfPwrCmnd;
        this.LftHldEnbl =  LftHldEnbl;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	double RotPwrTemp;
    	
    	RotTmOut.reset();
    	RotTmOut.start();
        if ((K_CmndCal.KCMD_b_RotRstGyroOnInit == true) ||
        	(ModeIsAuton == false))
        	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	
    	RotFdbkAngInit = Robot.kSENSORS.getGyroAngle(); 	
    	RotAngInitDelt = RotDsrdAng - RotFdbkAngInit;
        RotAng70Pct = RotAngInitDelt * 0.7;	
        RotAng90Pct = RotAngInitDelt * 0.9;	
        
        RotPwrTemp = (double)Math.abs(StrfPwrCmnd * (RotPwrCmndPct/(float)100));
        
    	if(RotPwrTemp < 0.3) {
    		System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		RotPwrTemp = 0.3;
    	}
        if (this.RotClckWise == false) {
        	// Rotation is Counter-ClockWise
    		RotPwrTemp = -(RotPwrTemp);        	
    	}
        RotPwrCmnd = RotPwrTemp;        
        
    	Robot.kDRIVE.mechDrive(StrfPwrCmnd, 0.0, RotPwrCmnd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	float  turnDirctnScalar = (float)1.0;
    	float  nearTrgtScalar =   (float)1.0;
    	
        /* TurnTmOut is a Free Running Timer */	

        RotFdbkAng = Robot.kSENSORS.getGyroAngle();
        
        if (RotClckWise == false) {
    		// Turn Counter-ClockWise
    		turnDirctnScalar = (float)-1;
        } else {
    		// Turn ClockWise 
    		turnDirctnScalar = (float)1;        	        	
        }

    	if(RotFdbkAng >= RotAng90Pct) {
    		nearTrgtScalar = (float)0.30;
    	} else if (RotFdbkAng >= RotAng70Pct) {
    		nearTrgtScalar = (float)0.60;    		
    	}
    	RotPwrCmndAdj = (double)turnDirctnScalar * (double)nearTrgtScalar * RotPwrCmnd;
    	
		Robot.kDRIVE.mechDrive(StrfPwrCmnd, 0, RotPwrCmndAdj);
		
		
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
    	SmartDashboard.putNumber("Rotate 70 Percent Angle : ", RotAng70Pct);
    	SmartDashboard.putNumber("Rotate 90 Percent Angle : ", RotAng90Pct);
    	SmartDashboard.putNumber("Rotate Power Cmnd Percent Of Strafe Power : ", RotPwrCmndPct);
    	SmartDashboard.putNumber("Rotate Power Cmnd : ", RotPwrCmnd);
    	SmartDashboard.putNumber("Rotate Power Cmnd Adjusted : ", RotPwrCmndAdj);
    	SmartDashboard.putNumber("Strafe Power Cmnd : ", StrfPwrCmnd);
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
    	System.out.println("Rotate 70 Percent Angle : " + RotAng70Pct);
    	System.out.println("Rotate 90 Percent Angle : " + RotAng90Pct);
    	System.out.println("Rotate Power Cmnd Percent Of Strafe Power : " + RotPwrCmndPct);
    	System.out.println("Rotate Power Cmnd : " + RotPwrCmnd);
    	System.out.println("Rotate Power Cmnd Adjusted : " + RotPwrCmndAdj);
    	System.out.println("Strafe Power Cmnd : " + StrfPwrCmnd);
    	System.out.println("Lift Hold Enable during Side Arc? : " + LftHldEnbl);
    	System.out.println("Lift Hold Power Cmnd : " + LftPwrCmnd); 
    	
    }
    
    
}
