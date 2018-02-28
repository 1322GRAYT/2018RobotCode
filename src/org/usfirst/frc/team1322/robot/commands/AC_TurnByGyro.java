package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;

import edu.wpi.first.wpilibj.command.Command;


/**
 * Turn A specific Angle at a specific speed
 */
/**
 * Class: AC_TurnByGyro - Autonomous Command to Rotate
 * ClockWise or Counter-ClockWise to a Desired Angular
 * Postion Based on Gyro Angular Position Feedback using
 * a PI Control System. */
public class AC_TurnByGyro extends Command {
	
	// Autonomous Pattern Vars
	private boolean RotPIDEnbl, RotClckWise;
	private double  RotPstnDsrd;
	

	//  Autonomous Pattern Constructor
    /** Method: AC_TurnByGyro - Autonomous Command to Rotate
     * ClockWise or Counter-ClockWise to a Desired Angular
     * Position Based on Gyro Angular Position Feedback using
     * a PI Control System.
    *  @param1: Rotate PID Control Enable      (boolean)	
    *  @param2: Is Desired Rotation ClockWise? (boolean)	
    *  @param3: Desired Angular Rotation       (double: degrees +/-)
    *           (+ = ClockWise, - = Counter-ClockWise)
    *  @Return: Commanded Motor Driver Power   (double: normalized power)
    *   */
    public AC_TurnByGyro(boolean RotPIDEnbl,
    		             boolean RotClckWise,
    		             double  RotPstnDsrd) {
        requires(Robot.kDRIVE);
    	this.RotPIDEnbl = RotPIDEnbl;
    	this.RotClckWise = RotClckWise;
    	this.RotPstnDsrd = RotPstnDsrd;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	Robot.kPIDROT.resetPIDRot();
        Robot.kPIDROT.putPIDRotPstnTgt(this.RotPIDEnbl,
        		                       this.RotClckWise,
        		                       this.RotPstnDsrd);  
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double NormPwrCmnd;
    	
        Robot.kPIDROT.managePIDRotate();
        NormPwrCmnd = Robot.kPIDROT.getPIDRotCmnd();
    	Robot.kDRIVE.mechDrive(0, 0, NormPwrCmnd);    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.kPIDROT.getPIDRotTgtCondMet() == true);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
