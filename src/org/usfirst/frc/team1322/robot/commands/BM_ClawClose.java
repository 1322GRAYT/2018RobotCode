package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *  Command Class: BM_ClawClose
 *  Trigger pneumatic pistons to close Claw to grab
 *  PwrCube.
 */
public class BM_ClawClose extends Command {
	private Timer DlyTmr = new Timer();
	
   /**
	*  Command Method: BM_ClawClose
	*  Trigger pneumatic pistons to close Claw to grab
    *  PwrCube.
	*/
    public BM_ClawClose() {
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
        Robot.kCLAW.closeClaw();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean finished = false;
    	
    	if (DlyTmr.get() >= K_CmndCal.KCMD_t_ClawPrdClose) {
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
