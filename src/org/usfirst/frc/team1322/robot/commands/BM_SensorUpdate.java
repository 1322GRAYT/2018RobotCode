package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BM_SensorUpdate extends Command {
	double EncdrCnt[] = new double[4]; 
	
	
    public BM_SensorUpdate() {
        requires(Robot.kSENSORS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	
    	//Set SmartDashboard values
    	//PDP Current
    	/*SmartDashboard.putBoolean("Block in Claw: ", Robot.kSENSORS.getBlock());
    	SmartDashboard.putNumber("Lift2 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLift2));
    	SmartDashboard.putNumber("Lift1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLift1));
    	SmartDashboard.putNumber("rRDrive2 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpRRDrive2));
    	SmartDashboard.putNumber("rRDrive1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpRRDrive1));
    	SmartDashboard.putNumber("rFDrive2 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpRFDrive2));
    	SmartDashboard.putNumber("rFDrive1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpRFDrive1));
    	SmartDashboard.putNumber("NA1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpNA1));
    	SmartDashboard.putNumber("NA2 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpNA2));
    	SmartDashboard.putNumber("BlockSensor Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpBlockSensor));
    	SmartDashboard.putNumber("NA3 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpNA3));
    	SmartDashboard.putNumber("lRDrive1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLRDrive1));
    	SmartDashboard.putNumber("ClawR Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpClawR));
    	SmartDashboard.putNumber("ClawL Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpClawL));
    	SmartDashboard.putNumber("lFDrive2 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLFDrive2));
    	SmartDashboard.putNumber("lRDrive2 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLRDrive2));
    	SmartDashboard.putNumber("lFDrive1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLFDrive1)); */
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
    	SmartDashboard.putNumberArray("Wheel Velocity : ", Robot.kSENSORS.getWhlsVel());

    	/*//Field Data
    	SmartDashboard.putString("FieldData : ", Robot.kAUTON.getFieldData());
        SmartDashboard.putBoolean("Our Switch Left? : ", Robot.kAUTON.getOurSwitchLeftSide());
    	SmartDashboard.putBoolean("Our Scale Left? : ", Robot.kAUTON.getOurScaleLeftSide());
    	SmartDashboard.putBoolean("Our Switch Captured? : ", Robot.kAUTON.getSwitchDataCaptured());
    	SmartDashboard.putBoolean("Our Scale Captured? : ", Robot.kAUTON.getScaleDataCaptured());
    	SmartDashboard.putBoolean("c? : ", Robot.kAUTON.getFieldDataTimedOut());
*/
    	SmartDashboard.updateValues();

    	
	    if (K_CmndCal.KCMD_b_DebugEnbl)
	    {
	    	//Update Console   	
	    	System.out.println("Encoder Counts: " + Robot.kSENSORS.getEncodersCnt());  
	
	    	System.out.println("Gyro Angle : " + Robot.kSENSORS.getGyroAngle());
	    	
	    	EncdrCnt = Robot.kSENSORS.getEncodersCnt(); 
	    	System.out.print("Raw Encoder Counts Ref A : " + EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlA_Slct]);
	    	System.out.print("Raw Encoder Counts Ref B : " + EncdrCnt[K_SensorCal.KWSS_e_RefAutonDrvWhlB_Slct]);
	    	
	    	System.out.println("FieldData : " + Robot.kAUTON.getFieldData());
	     	System.out.println("Our Switch Left? : " + Robot.kAUTON.getOurSwitchLeftSide());
	    	System.out.println("Our Scale Left? : " + Robot.kAUTON.getOurScaleLeftSide());
	    	System.out.println("Our Switch Captured? : " + Robot.kAUTON.getSwitchDataCaptured());
	    	System.out.println("Our Scale Captured? : " + Robot.kAUTON.getScaleDataCaptured());
	    	System.out.println("Our Switch Captured? : " + Robot.kAUTON.getFieldDataTimedOut());
	    }
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Never Stops
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
