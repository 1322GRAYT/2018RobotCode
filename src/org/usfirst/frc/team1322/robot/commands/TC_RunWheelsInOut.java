package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class: TC_RunClawWheelsInOut - For Tele-Op Mode, this Class
 * Controls the Claw Wheel Motors that can Take-In or Eject
 * a Power-Cube depending on the Direction of the Wheel Motors.
 */
public class TC_RunWheelsInOut extends Command {

	boolean in;
	
	
    public TC_RunWheelsInOut(boolean in) {
        requires(Robot.kSHOOTER);
        this.in = in;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if in, set power to 1, else, set power to -1
    	if(in) { // PwrCube Take-In
    		Robot.kSHOOTER.clawSpeedInOut(1);
    	}else {  // PwrCube Eject
    		Robot.kSHOOTER.clawSpeedInOut(-1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Set speed to 0
    	Robot.kSHOOTER.clawSpeedInOut(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
