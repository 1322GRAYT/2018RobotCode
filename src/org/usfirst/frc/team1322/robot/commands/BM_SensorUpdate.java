package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BM_SensorUpdate extends Command {

    public BM_SensorUpdate() {
        requires(Robot.kSENSORS);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kSENSORS.init();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Set SmartDashboard values
    	//PDP Current
    	SmartDashboard.putBoolean("Block in Claw: ", Robot.kSENSORS.getBlock());
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
    	SmartDashboard.putNumber("lFDrive1 Current: ", Robot.kSENSORS.getPdpCurrent(RobotMap.pdpLFDrive1));
    	//Ultra Sonic Sensor Vaules
    	SmartDashboard.putNumber("Rear US : ", Robot.kSENSORS.getRearUSDistance());
    	SmartDashboard.putNumber("Left US : ", Robot.kSENSORS.getLeftUSDistance());
    	SmartDashboard.putNumber("Right US : ", Robot.kSENSORS.getRightUSDistance());
    	//Gyro
    	SmartDashboard.putNumber("Gyro Angle : ", Robot.kSENSORS.getGyroAngle());
    	//Update SmartDashboard
    	//SmartDashboard.updateValues();
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
