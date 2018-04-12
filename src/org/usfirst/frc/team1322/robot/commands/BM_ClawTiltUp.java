package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *  Command Class: BM_ClawTiltUp
 *  Trigger pneumatic pistons to tilt Claw Up to Vertical
 *  Position for retaining block and keeping claws out of
 *  the way when empty.
 */
public class BM_ClawTiltUp extends Command {
	private Timer DlyTmr = new Timer();

  /**	
	*  Command Method: BM_ClawTiltUp
	*  Trigger pneumatic pistons to tilt Claw Up to Vertical
	*  Position for retaining block and keeping claws out of
	*  the way when empty.
    */	
    public BM_ClawTiltUp() {
        //Set the required subsystem
        requires(Robot.kCLAW);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	DlyTmr.reset();
    	DlyTmr.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.kCLAW.liftClaw();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean finished = false;
    	
    	if (DlyTmr.get() >= K_CmndCal.KCMD_t_ClawPrdTiltUp) {
    		finished = true;
    	}    	
    	
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	DlyTmr.stop();
    	Robot.kAUTON.setMasterTaskCmplt(true);	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
