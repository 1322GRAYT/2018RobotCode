package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drive Forward/Sideways using the gyro
 */
public class AC_DriveByGyro extends Command {

	private double strafeSpeed;
	private double forwardSpeed;
	private double startGyroPos;
	private double stopDistance;
	private double usID;
	private final double correctionSpeed = RobotMap.autonDriveCorrectionSpeed;
	private final double leeway = RobotMap.autonDriveLeeway;
	
	
    public AC_DriveByGyro(double forwardSpeed, double strafeSpeed, double stopDistance, double usID) {
    	requires(Robot.kSENSORS);
        requires(Robot.kDRIVE);
        this.forwardSpeed = forwardSpeed;
        this.strafeSpeed = strafeSpeed;
        this.stopDistance = stopDistance;
        this.usID = usID;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Keep Robot Facing Straight at all costs
    	double gAngle = Robot.kSENSORS.getGyroAngle();
    	if((gAngle - leeway) > startGyroPos) {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, correctionSpeed);
    	}else if((gAngle + leeway) < startGyroPos) {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, -correctionSpeed);
    	}else {
    		Robot.kDRIVE.mechDrive(strafeSpeed, forwardSpeed, 0);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return stopDistance <= Robot.kSENSORS.getUSDistanceFromId(usID);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
