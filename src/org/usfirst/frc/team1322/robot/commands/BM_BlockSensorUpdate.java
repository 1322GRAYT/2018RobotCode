package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class BM_BlockSensorUpdate extends Command {

    public BM_BlockSensorUpdate() {
        requires(Robot.kCLAW);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	//Set Devailt Value
    	SmartDashboard.putBoolean("Block in Claw: ", false);
    	//Update SmartDashboard
    	SmartDashboard.updateValues();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Set SmartDashboard values
    	SmartDashboard.putBoolean("Block in Claw: ", Robot.kCLAW.getBlock());
    	//Update SmartDashboard
    	SmartDashboard.updateValues();
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
