package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_ClawCal;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class: AC_RunClawWheelsInOut - For Autonomous Mode, this Class
 * Controls the Claw Wheel Motors that can Take-In or Eject
 * a Power-Cube depending on the Direction of the Wheel Motors.
 */
public class AC_RunClawWheelsInOut extends Command {
	boolean in;
	private Timer timer = new Timer();

	
    public AC_RunClawWheelsInOut(boolean in) {
            requires(Robot.kCLAW);
            this.in = in;
        }
    	

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	//if in, set power to 1, else, set power to -1
    	if(in) { // PwrCube Take-In
    		Robot.kCLAW.clawSpeedInOut(1);
    	}else {  // PwrCube Eject
    		Robot.kCLAW.clawSpeedInOut(-1);
    	}
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Nothing Required - Motors Running Based on Command in initialize()
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean CmndCmplt = false;
        
    	if(in == true)
    	{ // PwrCube Take-In/Grab
    		if ((Robot.kSENSORS.getBlock() == true) ||
    			(timer.get() >= K_ClawCal.KCLW_t_AutoBlckGrbTmOut))
    		{
    			CmndCmplt = true;    			
    		}
    	}
    	else // (in == false)
    	{  // PwrCube Eject
    		if (((Robot.kSENSORS.getBlock() == false) &&
    			 (timer.get() >= K_ClawCal.KCLW_t_AutoBlckEjctTmMin)) ||
    			(timer.get() >= K_ClawCal.KCLW_t_AutoBlckEjctTmOut))
    		{
    			CmndCmplt = true;    		    			
    		}
    	}
    	
        return CmndCmplt;
    }

    
    // Called once after isFinished returns true
    protected void end() {
    	//Set speed to 0
    	Robot.kCLAW.clawSpeedInOut(0);
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
}
