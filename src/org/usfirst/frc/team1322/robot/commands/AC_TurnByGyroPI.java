package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;


/**
 * Turn A specific Angle at a specific speed
 */
/**
 * Class: AC_TurnByGyroPI - Autonomous Command to Rotate
 * ClockWise or Counter-ClockWise to a Desired Angular
 * Postion Based on Gyro Angular Position Feedback using
 * a PI Control System. */
public class AC_TurnByGyroPI extends Command {

	// Autonomous Pattern Vars
	private Timer RotateTmOut = new Timer();
	private boolean RotPIDEnbl, RotClckWise;
	private double  RotPstnDsrd;
	

    /** KATM_t_RotSafetyTmOutPI: Amount of Time that must elapse before
     * a P-I Controlled rotate command will cancel out due to taking too
     * long to reach the target angle due to some system loss. */
	public static final float KATM_t_RotSafetyTmOutPI = (float)2.5; // sec 
	
	
	
	//  Autonomous Pattern Constructor
    /** Method: AC_TurnByGyroPI - Autonomous Command to Rotate
     * ClockWise or Counter-ClockWise to a Desired Angular
     * Position Based on Gyro Angular Position Feedback using
     * a PI Control System.
    *  @param1: Rotate PID Control Enable      (boolean)	
    *  @param2: Is Desired Rotation ClockWise? (boolean)	
    *  @param3: Desired Angular Rotation       (double: degrees +/-)
    *           (+ = ClockWise, - = Counter-ClockWise)
    *  @Return: Commanded Motor Driver Power   (double: normalized power)
    *   */
    public AC_TurnByGyroPI(boolean RotPIDEnbl,
    		               boolean RotClckWise,
    		               double  RotPstnDsrd) {
        requires(Robot.kDRIVE);
    	this.RotPIDEnbl = RotPIDEnbl;
    	this.RotClckWise = RotClckWise;
    	this.RotPstnDsrd = RotPstnDsrd;
        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	RotateTmOut.reset();
    	RotateTmOut.start();
    	Robot.kDRIVE.enable();
    	Robot.kPIDROT.resetPIDRot();
        Robot.kPIDROT.putPIDRotPstnTgt(this.RotPIDEnbl,
        		                       this.RotClckWise,
        		                       this.RotPstnDsrd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double NormPwrCmnd;
    	
    	// RotateTmOut updates as free-running timer.
        Robot.kPIDROT.managePIDRotate();
        NormPwrCmnd = Robot.kPIDROT.getPIDRotCmnd();
    	Robot.kDRIVE.mechDrive(0, 0, NormPwrCmnd);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean exitCond = false;
    	
    	if ((Robot.kPIDROT.getPIDRotTgtCondMet() == true) ||
    		(RotateTmOut.get() >= KATM_t_RotSafetyTmOutPI)) {
    		exitCond = true;
    	}
    	
        return (exitCond == true);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
    	RotateTmOut.stop();    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
