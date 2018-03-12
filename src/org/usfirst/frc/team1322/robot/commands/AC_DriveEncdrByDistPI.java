package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class: AC_DriveEncdrByDistPI - Autonomous Command to Drive Forwards
 * or Backwards by a Desired Distance (Feet) based on the Average of
 * the Encoder Counts of two Drive Motors/Wheels and Drive is corrected
 * by PI control based on the Gyro readings.
 */
public class AC_DriveEncdrByDistPI extends Command {
	
	// Autonomous Pattern Vars
    private float   DsrdDistFeet, DsrdPriPwr, DsrdDclFeet, DsrdDclPwr;
	private boolean DrvPIDEnbl;
    private boolean DrctnIsFwd;
	
	private double DrvHdngDsrd;
	private double EncdrActCnt;
	private double EncdrInitRefCnt;
	private double EncdrDsrdTrvlCnts;	
	private double EncdrDsrdDclCnts;	
	private double EncdrTgtDclCnts;	
	private double EncdrTgtRefCnt;

	
	//  Autonomous Pattern Constructor
    /** Method: AC_DriveEncdrByDist - Autonomous Command to Drive Forwards
      * or Backwards by a Desired Distance (Feet) based on the Average of
      * the Encoder Counts of two Drive Motors/Wheels.
      *  @param1: Desired Drive Distance             (float: feet)	
      *  @param2: Desired Drive Power                (float: normalized power)	
      *  @param3: Desired Deceleration Distance      (float: feet)
      *  @param4: Desired Deceleration Power         (float: normalized power)
      *  @param5: Desired Drive Course Heading Angle (float: degrees)
      *  @param6: Is Desired Direction Forward?      (boolean)
      *   */
    public AC_DriveEncdrByDistPI(boolean DrvPIDEnbl,
    		                     float   DsrdDistFeet,
    		                     float   DsrdPriPwr,
    		                     float   DsrdDclFeet,
    		                     float   DsrdDclPwr,
    		                     float   DrvHdngDsrd,
    		                     boolean DrctnIsFwd) {
        requires(Robot.kDRIVE);        
        this.DrvPIDEnbl = DrvPIDEnbl; 
        this.DrctnIsFwd = DrctnIsFwd;
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
        Robot.kPID.putPIDDrvPstnTgt(this.DrvPIDEnbl,
        		                    this.DrvHdngDsrd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double EncdrCnt[] = new double[4]; 
    	double PriPwr;
    	double DclPwr;
        double HdngCorrCmnd;
        double DrvPwrCmnd;
    	
    	EncdrActCnt = Robot.kSENSORS.getRefEncoderCnt();    	
        Robot.kPID.managePIDDrive();
        HdngCorrCmnd = Robot.kPID.getPIDDrvCorrCmnd();
    	
    	if (DrctnIsFwd == false) {
    		PriPwr = (double)-(DsrdPriPwr);
    	    DclPwr = (double)-(DsrdDclPwr);
    	}
    	else {
    		PriPwr = (double)DsrdPriPwr;
    	    DclPwr = (double)DsrdDclPwr;    		
    	}
    	    	
    	if (EncdrActCnt < EncdrTgtDclCnts)
    	  {
          DrvPwrCmnd = PriPwr;
    	  }
    	else // (EncdrActCnt >= EncdrTgtDclCnts)
    	  {
    	  DrvPwrCmnd = DclPwr;
    	  }
    	
  	  Robot.kDRIVE.mechDrive(0.0, DrvPwrCmnd, HdngCorrCmnd);
    	
    	
    
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
}
