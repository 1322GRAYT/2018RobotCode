package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_DriveCal;
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
    private boolean DrctnIsFwd;
	
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
      *   */
    public AC_DriveEncdrByDistPI(float   DsrdDistFeet,
    		                     float   DsrdPriPwr,
    		                     float   DsrdDclFeet,
    		                     float   DsrdDclPwr,
    		                     float   DrvHdngDsrd,
    		                     boolean DrctnIsFwd) {
        requires(Robot.kDRIVE);
        requires(Robot.kPID);  
        this.DrctnIsFwd = DrctnIsFwd;
        this.DrvHdngDsrd = DrvHdngDsrd;
        this.DsrdDistFeet = DsrdDistFeet;
        this.DsrdPriPwr = DsrdPriPwr;
        this.DsrdDclFeet = DsrdDclFeet;
        this.DsrdDclPwr = DsrdDclPwr;
    }



	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	
    	EncdrInitRefCnt = Robot.kSENSORS.getRefEncoderCnt();
    	
    	EncdrDsrdTrvlCnts = Robot.kSENSORS.getCntsToDrv(DsrdDistFeet);
    	EncdrTgtRefCnt = EncdrInitRefCnt + EncdrDsrdTrvlCnts;
    	
    	EncdrDsrdDclCnts = Robot.kSENSORS.getCntsToDrv(DsrdDclFeet);
    	EncdrTgtDclCnts = EncdrTgtRefCnt - EncdrDsrdDclCnts;

    	Robot.kDRIVE.enable();    	
    	DrvPwrCmdLim = 0.0;
    	Robot.kPID.resetPIDRot();
        Robot.kPID.putPIDDrvPstnTgt(this.DrvHdngDsrd);
    	Robot.kPID.putPIDDrvSysEnbl(true);
    	Robot.kPID.startTgtCondTmr();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double PriPwr;
    	double DclPwr;
        double HdngCorrCmnd;
        double DrvPwrCmnd;
    	
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
  	  Robot.kAUTON.setMasterTaskCmplt(true);
  	  Robot.kPID.putPIDDrvSysEnbl(false);
  	  Robot.kPID.stopTgtCondTmr();
  	  Robot.kPID.resetPIDRot();
  	  Robot.kDRIVE.mechDrive(0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

    
	// Called to Update SmartDash Data for Display
    protected void updateSmartDashData() {

    	SmartDashboard.putNumber("Desired Distance : ", DsrdDistFeet);
        SmartDashboard.putNumber("Encoder Ref Init Count : ", EncdrInitRefCnt);
    	SmartDashboard.putNumber("Encoder Desired Travel Counts : ", EncdrDsrdTrvlCnts);
    	SmartDashboard.putNumber("Encoder Desired Decel Counts : ", EncdrDsrdDclCnts);
    	SmartDashboard.putNumber("Encoder Desired Decel Target : ", EncdrTgtDclCnts);
    	SmartDashboard.putNumber("Encoder Desired End Target : ", EncdrTgtRefCnt);
    	SmartDashboard.putNumber("Encoder Actual Count Avg : ", EncdrActCnt);
    	
    	System.out.println("Desired Distance : " + DsrdDistFeet);
    	System.out.println("Encoder Ref Avg Init Count : " + EncdrInitRefCnt);
    	System.out.println("Encoder Desired Travel Counts : " + EncdrDsrdTrvlCnts);
    	System.out.println("Encoder Desired Decel Counts : " + EncdrDsrdDclCnts);
    	System.out.println("Encoder Desired Decel Target : " + EncdrTgtDclCnts);
    	System.out.println("Encoder Desired End Target : " + EncdrTgtRefCnt);
    	System.out.println("Encoder Actual Count Avg : " + EncdrActCnt);
    
    }
    
}
