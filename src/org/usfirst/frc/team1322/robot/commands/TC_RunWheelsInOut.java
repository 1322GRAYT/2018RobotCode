package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_RunWheelsInOut extends Command {

	boolean in;
	
    public TC_RunWheelsInOut(boolean in) {
        requires(Robot.kCLAW);
        this.in = in;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if in, set power to 1, else, set power to -1
    	if(in) {
    		Robot.kCLAW.clawSpeedInOut(1);
    	}else {
    		Robot.kCLAW.clawSpeedInOut(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Set speed to 0
    	Robot.kCLAW.clawSpeedInOut(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
