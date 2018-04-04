package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_DriveCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command Class: AC_DriveEncdrByDist - Autonomous Command to Drive Forwards
 * or Backwards by a Desired Distance (Feet) based on the Maximum of the
 * Encoder Counts of two Drive Motors/Wheels.
 */
public class AC_DriveEncdrByDist extends Command {
	private Timer TmOutTmr = new Timer();
	
	// Autonomous Pattern Vars
    private float   DsrdDistFeet, DsrdPriPwr, DsrdDclFeet, DsrdDclPwr;
    private float   DsrdHdngAng;
	private boolean DrctnIsFwd, LftHldEnbl;
	
	private double EncdrActCnt;
	private double EncdrActCntPrev;	
	private double EncdrActCntDelt;	
	private double EncdrInitRefCnt;
	private double EncdrDsrdTrvlCnts;	
	private double EncdrDsrdDclCnts;	
	private double EncdrTgtDclCnts;	
	private double EncdrTgtRefCnt;
	private double DrvPwrCmdLim;
	private double DrvDsrdHdngAng;	
	private double DrvFdbkHdngAng;
	private double DrvHdngAngErrRaw;
	private double DrvHdngAngErr;
 	private final double CorrPwrCmnd = RobotMap.autonDriveCorrectionSpeed;
 	private final double CorrDeadBand = RobotMap.autonDriveLeeway;	
 	
 	
	
    /**
      *  Command Method: AC_DriveEncdrByDist - Autonomous Command to Drive Forwards
      *  or Backwards by a Desired Distance (Feet) based on the Average of
      *  the Encoder Counts of two Drive Motors/Wheels.
      *  @param1: Desired Drive Distance        (float: feet)	
      *  @param2: Desired Drive Power           (float: normalized power)	
      *  @param3: Desired Deceleration Distance (float: feet)
      *  @param4: Desired Deceleration Power    (float: normalized power)
      *  @param5: Is Desired Direction Forward? (boolean)
      *  @param6: Enable Lift Hold Function?    (boolean)
      *   */
    public AC_DriveEncdrByDist(float   DsrdDistFeet,
    		                   float   DsrdPriPwr,
    		                   float   DsrdDclFeet,
    		                   float   DsrdDclPwr,
    		                   float   DsrdHdngAng,    		                   
    		                   boolean DrctnIsFwd,
    		                   boolean LftHldEnbl) {
        requires(Robot.kDRIVE);        
        requires(Robot.kLIFT);        
        this.DrctnIsFwd = DrctnIsFwd;
        this.LftHldEnbl = LftHldEnbl;
        this.DsrdDistFeet = DsrdDistFeet;
        this.DsrdPriPwr = DsrdPriPwr;
        this.DsrdDclFeet = DsrdDclFeet;
        this.DsrdDclPwr = DsrdDclPwr;
        this.DsrdHdngAng = DsrdHdngAng;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	TmOutTmr.reset();
    	
    	EncdrInitRefCnt = Robot.kSENSORS.getRefEncoderCnt();
    	EncdrActCntPrev = EncdrInitRefCnt;
    	
    	EncdrDsrdTrvlCnts = Robot.kSENSORS.getCntsToDrv(DsrdDistFeet);
    	EncdrTgtRefCnt = EncdrInitRefCnt + EncdrDsrdTrvlCnts;
    	
    	EncdrDsrdDclCnts = Robot.kSENSORS.getCntsToDrv(DsrdDclFeet);
    	EncdrTgtDclCnts = EncdrTgtRefCnt - EncdrDsrdDclCnts;    	
    	
    	EncdrActCntDelt = 0.0;
    	DrvPwrCmdLim = 0.0;
    	
        DrvDsrdHdngAng = this.DsrdHdngAng;	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double PriPwr;
    	double DclPwr;
        double DrvPwrCmndSgnd;
        double CorrPwrCmndSgnd;
        double LftPwrCmndSgnd;
        double MinDeltCntThrsh;
        
    	EncdrActCnt = Robot.kSENSORS.getRefEncoderCnt();
    	DrvFdbkHdngAng = Robot.kSENSORS.getGyroAngle(); 	    	
    	
    	DrvHdngAngErrRaw = DrvDsrdHdngAng - DrvFdbkHdngAng;
    	
  	    if (DrvHdngAngErrRaw > CorrDeadBand) {
  	    	DrvHdngAngErr = DrvHdngAngErrRaw - CorrDeadBand;
  	    } else if (DrvHdngAngErrRaw < -CorrDeadBand) {
  	    	DrvHdngAngErr = DrvHdngAngErrRaw + CorrDeadBand;
  	    } else {
  	    	DrvHdngAngErr = DrvHdngAngErrRaw; 
  	    }

    	// Calculate Robot Heading Correction Power
  	    if (DrvHdngAngErr > 0.0)  {
    		CorrPwrCmndSgnd = CorrPwrCmnd;  	    	
  	    } else if (DrvHdngAngErr < 0.0) {
    		CorrPwrCmndSgnd = -CorrPwrCmnd;  	    	
  	    } else {
  	    	CorrPwrCmndSgnd = 0.0;	
  	    }
    	
    	// Calculate Robot Drive Power
    	if (DrctnIsFwd == false) {
    		// Driving Reverse
    		PriPwr = (double)-(DsrdPriPwr);
    	    DclPwr = (double)-(DsrdDclPwr);
    	    if (CorrPwrCmndSgnd != 0.0) {
    	    	// When Driving Reverse, Head Correction Needs to be Reversed
    	    	CorrPwrCmndSgnd = -(CorrPwrCmndSgnd);   
    	    }
    	} else {
    		// Driving Forward
    		PriPwr = (double)DsrdPriPwr;
    	    DclPwr = (double)DsrdDclPwr;    		
    	}
    	
    	if (EncdrActCnt < EncdrTgtDclCnts)
    	  {
          DrvPwrCmndSgnd = PriPwr;
    	  }
    	else // (EncdrActCnt >= EncdrTgtDclCnts)
    	  {
    	  DrvPwrCmndSgnd = DclPwr;
    	  }   	 	
	
    	
    	DrvPwrCmdLim = USERLIB.RateLimOnInc(DrvPwrCmndSgnd,
    			                            DrvPwrCmdLim,
    			                            K_DriveCal.KDRV_r_DrvPwrDeltIncLimMax);
    	
  	    Robot.kDRIVE.mechDrive(0.0, DrvPwrCmdLim, CorrPwrCmndSgnd);

    	EncdrActCntDelt = EncdrActCnt - EncdrActCntPrev;
    	MinDeltCntThrsh = K_CmndCal.KCMD_v_DrvSafetyEncrSpdMin * K_CmndCal.KCMD_t_LoopRt;
  	    if (EncdrActCntDelt >= MinDeltCntThrsh) {
  	    	// Forward Movement Detected, Reset Timer
  	    	TmOutTmr.reset();  	    	
  	    }
  	    else {
  	    	// No Movement Detected, Reset Timer
  	    	TmOutTmr.start();
  	    }

    	EncdrActCntPrev = EncdrActCnt;
    	
    	
  	    // Keep Lift in Elevated Position
  	    if ((this.LftHldEnbl == true) &&
  	    	(Robot.kLIFT.getMidSen() == true) &&
  		    (Robot.kLIFT.getHighSen() == true)) {
  	        // PwrCube not sensed by N/C Sensor
  	    	LftPwrCmndSgnd = (double)K_LiftCal.KLFT_r_LiftMtrHldPwr;	
  	    } else {
  	    	// PwrCube is sensed by N/C Sensor
  	    	LftPwrCmndSgnd = 0.0;	
  	    }
  	    
  	    Robot.kLIFT.setSpeed(LftPwrCmndSgnd);
  	    
  	    
  	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
  	        updateSmartDashData();    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean EndCondMet = false;
    	double CurrEncdrCnt;
    	
    	CurrEncdrCnt = Robot.kSENSORS.getRefEncoderCnt();
    	
    	if ((CurrEncdrCnt >= EncdrTgtRefCnt) ||
    		(TmOutTmr.get() >= K_CmndCal.KCMD_t_DrvSafetyTmOut))
    	{
    		EndCondMet = true;
    	}
    		
    	return (EndCondMet == true);
    }

    // Called once after isFinished returns true
    protected void end() {
    	TmOutTmr.stop();
    	Robot.kAUTON.setMasterTaskCmplt(true);
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

    	SmartDashboard.putNumber("Desired Distance : ", DsrdDistFeet);
        SmartDashboard.putNumber("Encoder Ref Init Count : ", EncdrInitRefCnt);
    	SmartDashboard.putNumber("Encoder Desired Travel Counts : ", EncdrDsrdTrvlCnts);
    	SmartDashboard.putNumber("Encoder Desired Decel Counts : ", EncdrDsrdDclCnts);
    	SmartDashboard.putNumber("Encoder Desired Decel Target : ", EncdrTgtDclCnts);
    	SmartDashboard.putNumber("Encoder Desired End Target : ", EncdrTgtRefCnt);
    	SmartDashboard.putNumber("Encoder Actual Count Avg : ", EncdrActCnt);
    	SmartDashboard.putNumber("Drive Heading Angle Desired : ", DrvDsrdHdngAng);
    	SmartDashboard.putNumber("Drive Heading Angle FeedBack : ", DrvFdbkHdngAng);
    	SmartDashboard.putNumber("Drive Heading Angle Error Raw : ", DrvHdngAngErrRaw);
    	SmartDashboard.putNumber("Drive Heading Angle Error : ", DrvHdngAngErr);
    	
    	//Update SmartDashboard
    	System.out.println("Desired Distance : " + DsrdDistFeet);
    	System.out.println("Encoder Ref Avg Init Count : " + EncdrInitRefCnt);
    	System.out.println("Encoder Desired Travel Counts : " + EncdrDsrdTrvlCnts);
    	System.out.println("Encoder Desired Decel Counts : " + EncdrDsrdDclCnts);
    	System.out.println("Encoder Desired Decel Target : " + EncdrTgtDclCnts);
    	System.out.println("Encoder Desired End Target : " + EncdrTgtRefCnt);
    	System.out.println("Encoder Actual Count Avg : " + EncdrActCnt);
    	System.out.println("Drive Heading Angle Desired : " + DrvDsrdHdngAng);
    	System.out.println("Drive Heading Angle FeedBack : " + DrvFdbkHdngAng);
    	System.out.println("Drive Heading Angle Error Raw : " + DrvHdngAngErrRaw);
    	System.out.println("Drive Heading Angle Error : " + DrvHdngAngErr);
    	
    }    
    
}
