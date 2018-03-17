package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;


/**
  * Class: AC_TimeDelay - Delays the Robot for the time value supplied
  *        as an argument by the calling Command Group (seconds).  
  * @param: Desired Delay Time (seconds)
  */
public class Auto_RightSide extends Command {

	CommandGroup autoCommandGroup;
	
	public Auto_RightSide() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.fieldString.charAt(0) == 'R')
    	{
    		autoCommandGroup = new AM_StrtRightSide_Swch1322();
    	} else {
    		autoCommandGroup = new AM_DriveStraightCrossLine();    		
    	}
    	autoCommandGroup.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    // Just Wait - Timer is Free Running.
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
