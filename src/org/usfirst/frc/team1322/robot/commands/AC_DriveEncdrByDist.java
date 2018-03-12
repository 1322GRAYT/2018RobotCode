package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command Class: AC_DriveEncdrByDist - Autonomous Command to Drive Forwards
 * or Backwards by a Desired Distance (Feet) based on the Maximum of the
 * Encoder Counts of two Drive Motors/Wheels.
 */
public class AC_DriveEncdrByDist extends Command {
	
	// Autonomous Pattern Vars
    private float   DsrdDistFeet, DsrdPriPwr, DsrdDclFeet, DsrdDclPwr;
    private float   DsrdHdngAng;
	private boolean DrctnIsFwd;
	
	private double EncdrActCnt;
	private double EncdrInitRefCnt;
	private double EncdrDsrdTrvlCnts;	
	private double EncdrDsrdDclCnts;	
	private double EncdrTgtDclCnts;	
	private double EncdrTgtRefCnt;
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
      *   */
    public AC_DriveEncdrByDist(float   DsrdDistFeet,
    		                   float   DsrdPriPwr,
    		                   float   DsrdDclFeet,
    		                   float   DsrdDclPwr,
    		                   float   DsrdHdngAng,    		                   
    		                   boolean DrctnIsFwd) {
        requires(Robot.kDRIVE);        
        this.DrctnIsFwd = DrctnIsFwd;
        this.DsrdDistFeet = DsrdDistFeet;
        this.DsrdPriPwr = DsrdPriPwr;
        this.DsrdDclFeet = DsrdDclFeet;
        this.DsrdDclPwr = DsrdDclPwr;
        this.DsrdHdngAng = DsrdHdngAng;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	
    	EncdrInitRefCnt = Robot.kSENSORS.getRefEncoderCnt();
    	
    	EncdrDsrdTrvlCnts = Robot.kSENSORS.getCntsToDrv(DsrdDistFeet);
    	EncdrTgtRefCnt = EncdrInitRefCnt + EncdrDsrdTrvlCnts;
    	
    	EncdrDsrdDclCnts = Robot.kSENSORS.getCntsToDrv(DsrdDclFeet);
    	EncdrTgtDclCnts = EncdrTgtRefCnt - EncdrDsrdDclCnts;    	
    	
        DrvDsrdHdngAng = this.DsrdHdngAng;	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double PriPwr;
    	double DclPwr;
        double DrvPwrCmndSgnd;
        double CorrPwrCmndSgnd;

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
    	
  	    Robot.kDRIVE.mechDrive(0.0, DrvPwrCmndSgnd, CorrPwrCmndSgnd);

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
  	  Robot.kDRIVE.mechDrive(0.0, 0.0, 0.0);
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
    	SmartDashboard.putNumber("Drive Heading Angle Desired : ", DrvDsrdHdngAng);
    	SmartDashboard.putNumber("Drive Heading Angle FeedBack : ", DrvFdbkHdngAng);
    	SmartDashboard.putNumber("Drive Heading Angle Error Raw : ", DrvHdngAngErrRaw);
    	SmartDashboard.putNumber("Drive Heading Angle Error : ", DrvHdngAngErr);
    	
    	
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
    	System.out.println("Drive Heading Angle Desired : " + DrvDsrdHdngAng);
    	System.out.println("Drive Heading Angle FeedBack : " + DrvFdbkHdngAng);
    	System.out.println("Drive Heading Angle Error Raw : " + DrvHdngAngErrRaw);
    	System.out.println("Drive Heading Angle Error : " + DrvHdngAngErr);
    	
    }
    
    
}
