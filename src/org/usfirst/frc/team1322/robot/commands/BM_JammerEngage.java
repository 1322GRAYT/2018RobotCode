package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
  *  Command Class: BM_JammerEngage
  *  Engage Pneumatic Lift Jammer to mechanically lock lift
  *  system so the Lift Motors are no longer required to hold
  *  robot in lifted position.
  */
public class BM_JammerEngage extends Command {
	private Timer time = new Timer();
	
	
	/**
	  *  Command Method Constructor: BM_JammerEngage
	  *  Engage Pneumatic Lift Jammer to mechanically lock lift
	  *  system so the Lift Motors are no longer required to hold
	  *  robot in lifted position.
	  */	
    public BM_JammerEngage() {
        requires(Robot.kLIFT);        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time.reset();
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.kLIFT.engageJammer();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return time.get() >= 0.100;
    }

    // Called once after isFinished returns true
    protected void end() {
    	time.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
