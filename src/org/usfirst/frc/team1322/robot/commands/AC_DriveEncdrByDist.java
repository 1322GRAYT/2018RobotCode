package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class: AC_DriveEncdrByDist - Autonomous Command to Drive Forwards
 * or Backwards by a Desired Distance (Feet) based on the Average of
 * the Encoder Counts of two Drive Motors/Wheels.
 */
public class AC_DriveEncdrByDist extends Command {
	
	// Autonomous Pattern Vars
    private float   DsrdDistFeet, DsrdPriPwr, DsrdDclFeet, DsrdDclPwr;
	private boolean DrctnIsFwd;
	
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
    		                   boolean DrctnIsFwd) {
        requires(Robot.kSENSORS);
        requires(Robot.kPIDDRV);
        requires(Robot.kDRIVE);        
        this.DrctnIsFwd = DrctnIsFwd;
        this.DsrdDistFeet = DsrdDistFeet;
        this.DsrdPriPwr = DsrdPriPwr;
        this.DsrdDclFeet = DsrdDclFeet;
        this.DsrdDclPwr = DsrdDclPwr;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.kDRIVE.resetEncoders();

    	EncdrInitRefCnt = Robot.kSENSORS.getRefEncoderCnt();
    	
    	EncdrDsrdTrvlCnts = Robot.kSENSORS.getCntsToDrv(DsrdDistFeet);
    	EncdrTgtRefCnt = EncdrInitRefCnt + EncdrDsrdTrvlCnts;
    	
    	EncdrDsrdDclCnts = Robot.kSENSORS.getCntsToDrv(DsrdDclFeet);
    	EncdrTgtDclCnts = EncdrTgtRefCnt - EncdrDsrdDclCnts;

    	Robot.kPIDDRV.resetPIDDrv();
    	Robot.kPIDDRV.resetTgtProfTmr();
    	Robot.kPIDDRV.putPIDDrvSpdTgt(true, 0.0);  //Enbl PID and Command Target Spd - Todo  Speed Target Set to 0.0 Until SubSystem Debug is Complete.
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double EncdrCnt[] = new double[4]; 
    	double PriPwr;
    	double DclPwr;

    	EncdrActCnt = Robot.kSENSORS.getRefEncoderCnt();    	

    	Robot.kPIDDRV.managePIDDrive();            // Call the Drive PID Scheduler   	
    	// Cmnds[] = Robot.kPIDDRV.getPIDDrvCmnd();  // Get PID Commands - todo: Not Hooked-Up - Until Subsystem Debug is Complete
    	
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
          Robot.kDRIVE.mechDrive(0.0, PriPwr, 0.0);
    	  }
    	else // (EncdrActCnt >= EncdrTgtDclCnts)
    	  {
    	  Robot.kDRIVE.mechDrive(0.0, DclPwr, 0.0);
    	  }
    
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
  	  Robot.kPIDDRV.resetPIDDrv();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
