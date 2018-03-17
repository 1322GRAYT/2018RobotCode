package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Class: AutoSlct_Center - Starting with the Robot in
 * the Center position, performs the Autonomous Mode
 * Selection based on the Field Data from the FMS Data.
 */
public class AutoSlct_Center extends Command {

	CommandGroup autoCommandGroup;
	
	public AutoSlct_Center() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.updateFieldData();
    	if (Robot.kAUTON.getFieldData().charAt(0) == 'L')    	
    	{
    		autoCommandGroup = new AM_StrtCenter_SwchLeftSide();
    	} else {
    		autoCommandGroup = new AM_StrtCenter_SwchRightSide();    		    		
    	}
    	autoCommandGroup.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    // nothing to do    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
    
}
