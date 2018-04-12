package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;


/**
  * Class: AC_TimeDelay - Delays the Robot for the time value supplied
  *        as an argument by the calling Command Group (seconds).  
  * @param: Desired Delay Time (seconds)
  */
public class AC_TimeDelay extends Command {
    float DlyTmThrsh;
	
	private Timer timer = new Timer();

    public AC_TimeDelay(float DlyTmThrsh) {
    	this.DlyTmThrsh = DlyTmThrsh;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    // Just Wait - Timer is Free Running.
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timer.get() >= (double)DlyTmThrsh);
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    	Robot.kAUTON.setMasterTaskCmplt(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
    
}
