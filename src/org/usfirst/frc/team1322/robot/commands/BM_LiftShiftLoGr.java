package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  Command Class: BM_LiftShiftLoGr
 *  Trigger pneumatic pistons to Shift the Lift
 *  Raise/Lower Gearbox to Lo Gear to move slower
 *  but have more torque. 
 */
public class BM_LiftShiftLoGr extends Command {
	private boolean finished = false;

   /**
	*  Command Method: BM_LiftShiftLoGr
	*  Trigger pneumatic pistons to Shift the Lift
    *  Raise/Lower Gearbox to Lo Gear to move slower
    *  but have more torque. 
	*/	
    public BM_LiftShiftLoGr() {
    	//Set the required subsystem
        requires(Robot.kLIFT);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.kLIFT.shiftLiftLow();
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
