package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TC_RunWheelsRotate extends Command {

	boolean side;
	
    public TC_RunWheelsRotate(boolean side) {
        requires(Robot.kCLAW);
        //pass side to global
        this.side = side;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if side, rotate block at power 1, else, rotate block at power -1
    	if(side) {
    		Robot.kCLAW.clawSpeedRotate(1);
    	}else {
    		Robot.kCLAW.clawSpeedRotate(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Set claw speed to 0
    	Robot.kCLAW.clawSpeedInOut(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
