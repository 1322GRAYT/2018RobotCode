package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
  *  Command Class: BM_DisengageJammer
  *  Disengage Pneumatic Lift Jammer to release the mechanically lock
  *  of the lift system so the Lift Motors can raise and lower the lift
  *  and claw of the robot.
 */
public class BM_DisengageJammer extends Command {
	private Timer time = new Timer();
 
	
	/**
	  *  Command Method Constructor: BM_DisengageJammer
      *  Disengage Pneumatic Lift Jammer to release the mechanically lock
      *  of the lift system so the Lift Motors can raise and lower the lift
      *  and claw of the robot.
	  */		
	public BM_DisengageJammer() {
        requires(Robot.kLIFT);        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	time.reset();
    	time.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.kLIFT.disengageJammer();
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
