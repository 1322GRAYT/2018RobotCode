package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BM_ShiftLift extends Command {

	private boolean finished = false;
	private boolean low = false;

    public BM_ShiftLift(boolean low) {
        //pass along low to global
    	this.low = low;
    	//Set the required subsystem
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//if low, shift low, else, shift to high gear
    	if(low) {
    		Robot.kLIFT.shiftLiftHigh();
    	}else{
    		Robot.kLIFT.shiftLiftLow();
    	}	
    	//set global finished to true
    	finished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
