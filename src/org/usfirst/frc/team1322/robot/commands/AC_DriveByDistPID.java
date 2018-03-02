package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class: AC_DriveByDistPID - Autonomous Command to Drive Forwards
 * or Backwards by a Desired Distance (Feet) based on the Average of
 * the Encoder Counts of two Drive Motors/Wheels.
 */public class AC_DriveByDistPID extends Command {

		// Autonomous Pattern Vars
	    private float   DsrdDistFeet, DsrdPriSpd, DsrdDclFeet, DsrdDclSpd;

		private double EncdrActCnt;
		private double EncdrInitRefCnt;
		private double EncdrDsrdTrvlCnts;	
		private double EncdrDsrdDclCnts;	
		private double EncdrTgtDclCnts;	
		private double EncdrTgtRefCnt;
		private double DrvPwrCmndFA[] = new double[4];  // Drive Normalized Power Command - Fore-Aft Direction

		
	//  Autonomous Pattern Constructor
    /** Method: AC_DriveByDistPID - Autonomous Command to Drive Forwards
      * or Backwards by a Desired Distance (Feet) based on the Average of
      * the Encoder Counts of two Drive Motors/Wheels.
      *  @param1: Desired Drive Distance        (float: feet)	
      *  @param2: Desired Drive Speed           (float: rpm)	
      *  @param3: Desired Deceleration Distance (float: feet)
      *  @param4: Desired Deceleration Speed    (float: rpm)
      *  @param5: Is Desired Direction Forward? (boolean)
      *   */	 
    public AC_DriveByDistPID(float   DsrdDistFeet,
                             float   DsrdPriSpd,
                             float   DsrdDclFeet,
                             float   DsrdDclSpd,
                             boolean DrctnIsFwd) {
    	requires(Robot.kSENSORS);
    	requires(Robot.kPIDDRV);
    	requires(Robot.kDRIVE);        
    	this.DsrdDistFeet = DsrdDistFeet;
    	this.DsrdPriSpd = DsrdPriSpd;
    	this.DsrdDclFeet = DsrdDclFeet;
    	this.DsrdDclSpd = DsrdDclSpd;
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
		Robot.kPIDDRV.putPIDDrvSpdTgt(true, this.DsrdPriSpd);  //Enbl PID and Command Target Spd
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		double DsrdSpdSlctd;
		
		EncdrActCnt = Robot.kSENSORS.getRefEncoderCnt();    // Get Current Average Encoder Count  	
	    if (EncdrActCnt < EncdrTgtDclCnts)                  // Select the Target Encoder RPM
			{
	    	DsrdSpdSlctd = this.DsrdPriSpd;
			}
		else // (EncdrActCnt >= EncdrTgtDclCnts)
			{
			DsrdSpdSlctd = this.DsrdDclSpd;
			}
		Robot.kPIDDRV.putPIDDrvSpdTgt(true, DsrdSpdSlctd);  // Set PID Enbl PID and Target Encoder RPM		
		Robot.kPIDDRV.managePIDDrive();                     // Call the Drive PID Scheduler   	
		DrvPwrCmndFA = Robot.kPIDDRV.getPIDDrvCmnd();       // Get PID Normalized Power Commands
		Robot.kDRIVE.straightDrive(DrvPwrCmndFA);           // Command Motors to Normalized Powers


		/**********************************************/
		/* Output Display for Data Analysis           */
		/**********************************************/
		//Ultra Sonic Sensor Values
		SmartDashboard.putNumber("Rear US : ", Robot.kSENSORS.getRearUSDistance());
		SmartDashboard.putNumber("Left US : ", Robot.kSENSORS.getLeftUSDistance());
		SmartDashboard.putNumber("Right US : ", Robot.kSENSORS.getRightUSDistance());
		//Gyro
		SmartDashboard.putNumber("Gyro Angle : ", Robot.kSENSORS.getGyroAngle());
		//Drive Speeds
		SmartDashboard.putNumberArray("PID Drv Cmnds : ", DrvPwrCmndFA);		
		SmartDashboard.putNumber("Desired Encoder RPM : ", DsrdSpdSlctd);		
		SmartDashboard.putNumberArray("Encoder RPM : ", Robot.kSENSORS.getEncodersRPM());
		SmartDashboard.putNumberArray("Encoder Velocity : ", Robot.kSENSORS.getEncodersVelRaw());
		SmartDashboard.putNumberArray("Encoder Counts : ", Robot.kSENSORS.getEncodersCnt());
		SmartDashboard.putNumberArray("Wheel RPM : ", Robot.kSENSORS.getWhlsRPM());
		SmartDashboard.putNumber("Desired Distance : ", this.DsrdDistFeet);
		SmartDashboard.putNumber("Encoder Ref Init Count : ", EncdrInitRefCnt);
		SmartDashboard.putNumber("Encoder Desired Travel Counts : ", EncdrDsrdTrvlCnts);
		SmartDashboard.putNumber("Encoder Desired Decel Counts : ", EncdrDsrdDclCnts);
		SmartDashboard.putNumber("Encoder Desired Decel Target : ", EncdrTgtDclCnts);
		SmartDashboard.putNumber("Encoder Desired End Target : ", EncdrTgtRefCnt);
		SmartDashboard.putNumber("Encoder Actual Count Avg : ", EncdrActCnt);
		
		
		//Update SmartDashboard
		double DsplyEncdrCnt[] = new double[4];           //  Only for Data Display
		DsplyEncdrCnt = Robot.kSENSORS.getEncodersCnt();  //  Only for Data Display
		double DsplyEncdrRPM[] = new double[4];           //  Only for Data Display
		DsplyEncdrRPM = Robot.kSENSORS.getEncodersRPM();  //  Only for Data Display
		System.out.print("Encoder Ref A Count : " + DsplyEncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlA_Slct]);  // RF
		System.out.print("Encoder Ref B Count : " + DsplyEncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlB_Slct]);  // LR
		System.out.println("Encoder Ref Count Avg : " + EncdrActCnt);		
		System.out.println("Desired Distance : " + this.DsrdDistFeet);
		System.out.println("Encoder Ref Avg Init Count : " + EncdrInitRefCnt);
		System.out.println("Encoder Desired Travel Counts : " + EncdrDsrdTrvlCnts);
		System.out.println("Encoder Desired Decel Counts : " + EncdrDsrdDclCnts);
		System.out.println("Encoder Desired Decel Target : " + EncdrTgtDclCnts);
		System.out.println("Encoder Desired End Target : " + EncdrTgtRefCnt);
		System.out.println("Cmnd Pwr RR : " + DrvPwrCmndFA[0]);
		System.out.println("Cmnd Pwr RF : " + DrvPwrCmndFA[1]);
		System.out.println("Cmnd Pwr LF : " + DrvPwrCmndFA[2]);
		System.out.println("Cmnd Pwr LR : " + DrvPwrCmndFA[3]);
		System.out.println("Desired Encoder RPM : " + DsrdSpdSlctd);
		System.out.println("Encoder RPM RR : " + DsplyEncdrRPM[0]);
		System.out.println("Encoder RPM RF : " + DsplyEncdrRPM[1]);
		System.out.println("Encoder RPM LF : " + DsplyEncdrRPM[2]);
		System.out.println("Encoder RPM LR : " + DsplyEncdrRPM[3]);
	
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
