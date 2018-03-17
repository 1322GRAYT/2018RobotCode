package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;


/**
 * Class: AutoSlct_LeftSide - Starting with the Robot in
 * the Far Left position, performs the Autonomous Mode
 * Selection based on the Field Data from the FMS Data.
 */
public class AutoSlct_LeftSide extends Command {

	CommandGroup autoCommandGroup;
	
	public AutoSlct_LeftSide() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.updateFieldData();
    	if (Robot.kAUTON.getFieldData().charAt(0) == 'L')    	
    	{
    		autoCommandGroup = new AM_StrtLeftSide_SwchLeftSide();
    	} else {
//    		autoCommandGroup = new AM_DriveStraightCrossLine();    		
    		autoCommandGroup = new AM_StrtLeftSide_SwchRightSide();    		    		
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
