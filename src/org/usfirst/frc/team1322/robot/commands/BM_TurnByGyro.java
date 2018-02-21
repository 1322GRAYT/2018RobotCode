package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn A specific Angle at a specific speed
 */
public class BM_TurnByGyro extends Command {
	
	private double turnSpeed;
	private double turnAngle;
	
	/**
	 * Turn to A specific Angle at a specific speed
	 * @param turnSpeed Speed in which to turn  + = Right, - + Left
	 * @param turnAngle Angle to turn
	 */
    public BM_TurnByGyro(double turnSpeed, double turnAngle) {
        requires(Robot.kSENSORS);
        requires(Robot.kDRIVE);
        this.turnSpeed = turnSpeed;
        this.turnAngle = turnAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	Robot.kDRIVE.mechDrive(0, 0, turnSpeed);
    	if(turnSpeed < .3) {
    		System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		turnSpeed = .3;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double sevenTenthsOfTotal = .7 * turnAngle;
    	if(Robot.kSENSORS.getGyroAngle() >= sevenTenthsOfTotal) {
    		Robot.kDRIVE.mechDrive(0, 0, (turnSpeed * .5));
    	}
    	
    	double nineTenthsOfTotal = .9 * turnAngle;
    	if(Robot.kSENSORS.getGyroAngle() >= nineTenthsOfTotal) {
    		Robot.kDRIVE.mechDrive(0, 0, (turnSpeed * .25));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.kSENSORS.getGyroAngle() >= turnAngle;
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
