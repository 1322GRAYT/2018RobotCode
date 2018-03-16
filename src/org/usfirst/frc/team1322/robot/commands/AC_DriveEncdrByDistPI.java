package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_DriveCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
  * Command Class: AC_DriveEncdrByDistPI
  * Autonomous Command to Drive Forwards or Backwards by a 
  * Desired Distance (Feet) based on the Max of the
  * Encoder Counts of two Drive Motors/Wheels and Drive
  * is corrected by PI control based on the Gyro readings.
  */
public class AC_DriveEncdrByDistPI extends Command {
	
	// Autonomous Pattern Vars
    private float   DsrdDistFeet, DsrdPriPwr, DsrdDclFeet, DsrdDclPwr;
	private boolean DrvPIDEnbl;
    private boolean DrctnIsFwd, LftHldEnbl;
	
	private double DrvHdngDsrd;
	private double EncdrActCnt;
	private double EncdrInitRefCnt;
	private double EncdrDsrdTrvlCnts;	
	private double EncdrDsrdDclCnts;	
	private double EncdrTgtDclCnts;	
	private double EncdrTgtRefCnt;
	private double DrvPwrCmdLim;
	
	
	
    /** 
      * Command Method: AC_DriveEncdrByDist
      * Autonomous Command to Drive Forwards or Backwards by
      * a Desired Distance (Feet) based on the Max of the
      * Encoder Counts of two Drive Motors/Wheels.
      *  @param1: Desired Drive Distance             (float: feet)	
      *  @param2: Desired Drive Power                (float: normalized power)	
      *  @param3: Desired Deceleration Distance      (float: feet)
      *  @param4: Desired Deceleration Power         (float: normalized power)
      *  @param5: Desired Drive Course Heading Angle (float: degrees)
      *  @param6: Is Desired Direction Forward?      (boolean)
      *  @param7: Enable Lift Hold Function?         (boolean)
      *   */
    public AC_DriveEncdrByDistPI(float   DsrdDistFeet,
    		                     float   DsrdPriPwr,
    		                     float   DsrdDclFeet,
    		                     float   DsrdDclPwr,
    		                     float   DrvHdngDsrd,
    		                     boolean DrctnIsFwd,
    		                     boolean LftHldEnbl) {
        requires(Robot.kDRIVE);
        requires(Robot.kLIFT);  
        this.DrctnIsFwd = DrctnIsFwd;
        this.LftHldEnbl = LftHldEnbl;
        this.DrvHdngDsrd = DrvHdngDsrd;
        this.DsrdDistFeet = DsrdDistFeet;
        this.DsrdPriPwr = DsrdPriPwr;
        this.DsrdDclFeet = DsrdDclFeet;
        this.DsrdDclPwr = DsrdDclPwr;
    }



	// Called just before this Command runs the first time
    protected void initialize() {
    	
    	EncdrInitRefCnt = Robot.kSENSORS.getRefEncoderCnt();
    	
    	EncdrDsrdTrvlCnts = Robot.kSENSORS.getCntsToDrv(DsrdDistFeet);
    	EncdrTgtRefCnt = EncdrInitRefCnt + EncdrDsrdTrvlCnts;
    	
    	EncdrDsrdDclCnts = Robot.kSENSORS.getCntsToDrv(DsrdDclFeet);
    	EncdrTgtDclCnts = EncdrTgtRefCnt - EncdrDsrdDclCnts;

    	Robot.kDRIVE.enable();    	
    	Robot.kPID.resetPIDRot();
    	DrvPIDEnbl = true;
    	DrvPwrCmdLim = 0.0;
        Robot.kPID.putPIDDrvPstnTgt(DrvPIDEnbl, this.DrvHdngDsrd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double PriPwr;
    	double DclPwr;
        double HdngCorrCmnd;
        double DrvPwrCmnd;
        double LftPwrCmnd;
    	
    	EncdrActCnt = Robot.kSENSORS.getRefEncoderCnt();    	
        Robot.kPID.managePIDDrive();
        HdngCorrCmnd = Robot.kPID.getPIDDrvCorrCmnd();
    	
    	if (DrctnIsFwd == false) {  // Driving In Reverse
    		PriPwr = (double)-(DsrdPriPwr);
    	    DclPwr = (double)-(DsrdDclPwr);
    	    if (HdngCorrCmnd != 0.0) {
    	    	// When Driving Reverse, Head Correction Needs to be Reversed
    	    	HdngCorrCmnd = -(HdngCorrCmnd);   
    	    }
    	} else {
    		PriPwr = (double)DsrdPriPwr;
    	    DclPwr = (double)DsrdDclPwr;    		
    	}
    	    	
    	if (EncdrActCnt < EncdrTgtDclCnts) {
            DrvPwrCmnd = PriPwr;
    	} else { // (EncdrActCnt >= EncdrTgtDclCnts)
    	    DrvPwrCmnd = DclPwr;
    	}

    	DrvPwrCmdLim = USERLIB.RateLimOnInc(DrvPwrCmnd,
                                            DrvPwrCmdLim,
                                            K_DriveCal.KDRV_r_DrvPwrDeltIncLimMax);    	
    	
  	    Robot.kDRIVE.mechDrive(0.0, DrvPwrCmdLim, HdngCorrCmnd);

  	  
	    // Keep Lift in Elevated Position
	    if ((this.LftHldEnbl == true) &&
	    	(Robot.kLIFT.getMidSen() == true) &&
		    (Robot.kLIFT.getHighSen() == true)) {
	        // PwrCube not sensed by N/C Sensor
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
    	double CurrEncdrCnt;
    	
    	CurrEncdrCnt = Robot.kSENSORS.getRefEncoderCnt();
    	return (CurrEncdrCnt >= EncdrTgtRefCnt);
    }

    // Called once after isFinished returns true
    protected void end() {
      DrvPIDEnbl = false;
      Robot.kPID.putPIDDrvPstnTgt(DrvPIDEnbl, this.DrvHdngDsrd);    	
  	  Robot.kDRIVE.mechDrive(0.0, 0.0, 0.0);
	  Robot.kLIFT.setSpeed(0.0);  	  
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

    
    
	// Called to Update SmartDash Data for Display
    protected void updateSmartDashData() {
    	double EncdrCnt[] = new double[4]; 

    	//Ultra Sonic Sensor Values
    	SmartDashboard.putNumber("Rear US : ", Robot.kSENSORS.getRearUSDistance());
    	SmartDashboard.putNumber("Left US : ", Robot.kSENSORS.getLeftUSDistance());
    	SmartDashboard.putNumber("Right US : ", Robot.kSENSORS.getRightUSDistance());
    	//Gyro
    	SmartDashboard.putNumber("Gyro Angle : ", Robot.kSENSORS.getGyroAngle());
    	//Drive Speeds
    	SmartDashboard.putNumberArray("Encoder Velocity : ", Robot.kSENSORS.getEncodersVelRaw());
    	SmartDashboard.putNumberArray("Encoder Counts : ", Robot.kSENSORS.getEncodersCnt());
    	SmartDashboard.putNumberArray("Encoder RPM : ", Robot.kSENSORS.getEncodersRPM());
    	SmartDashboard.putNumberArray("Wheel RPM : ", Robot.kSENSORS.getWhlsRPM());
    	SmartDashboard.putNumber("Desired Distance : ", this.DsrdDistFeet);
        SmartDashboard.putNumber("Encoder Ref Init Count : ", EncdrInitRefCnt);
    	SmartDashboard.putNumber("Encoder Desired Travel Counts : ", EncdrDsrdTrvlCnts);
    	SmartDashboard.putNumber("Encoder Desired Decel Counts : ", EncdrDsrdDclCnts);
    	SmartDashboard.putNumber("Encoder Desired Decel Target : ", EncdrTgtDclCnts);
    	SmartDashboard.putNumber("Encoder Desired End Target : ", EncdrTgtRefCnt);
    	SmartDashboard.putNumber("Encoder Actual Count Avg : ", EncdrActCnt);
    	
    	EncdrCnt = Robot.kSENSORS.getEncodersCnt(); 
    	//Update SmartDashboard
    	System.out.print("Raw Encoder Counts Ref A : " + EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlA_Slct]);
    	System.out.print("Raw Encoder Counts Ref B : " + EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlB_Slct]);
    	System.out.println("Desired Distance : " + this.DsrdDistFeet);
    	System.out.println("Encoder Ref Avg Init Count : " + EncdrInitRefCnt);
    	System.out.println("Encoder Desired Travel Counts : " + EncdrDsrdTrvlCnts);
    	System.out.println("Encoder Desired Decel Counts : " + EncdrDsrdDclCnts);
    	System.out.println("Encoder Desired Decel Target : " + EncdrTgtDclCnts);
    	System.out.println("Encoder Desired End Target : " + EncdrTgtRefCnt);
    	System.out.println("Encoder Actual Count Avg : " + EncdrActCnt);
    
    }
    
}
