package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Turn A specific Angle at a specific speed
 */
public class BM_TurnByGyro extends Command {
	private Timer TurnTmOut = new Timer();
	private double turnSpeed;
	private double turnAngle;
	
	
    /** KATM_t_RotSafetyTmOut: Amount of Time that must elapse before
     * a rotate command will cancel out due to taking too long to reach
     * the target angle due to some system loss. */
	public static final float KATM_t_RotSafetyTmOut = (float)2.0; // sec 
	
	
	/**
	 * Turn to A specific Angle at a specific speed
	 * @param turnSpeed Speed in which to turn  + = Right, - = Left
	 * @param turnAngle Angle to turn
	 */
    public BM_TurnByGyro(double turnSpeed, double turnAngle) {
        requires(Robot.kDRIVE);
        this.turnSpeed = turnSpeed;
        this.turnAngle = turnAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        TurnTmOut.reset();
        TurnTmOut.start();
    	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	Robot.kDRIVE.mechDrive(0, 0, turnSpeed);
    	if(turnSpeed < .3) {
    		System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		turnSpeed = .3;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double turnAngleAbs;
    	float  turnDirctnScalar = (float)1.0;
    	float  nearTrgtScalar =   (float)1.0;
    	double sevenTenthsOfTotal;
    	double nineTenthsOfTotal;
        double turnNormPwrCmnd;
    	
        /* TurnTmOut is a Free Running Timer */	
    	
    	if (turnSpeed < (double)0.0) {
    		// Turn Counter-ClockWise
    		turnAngleAbs = (double)360 - turnAngle;
    		turnDirctnScalar = (float)-1;
    	}
    	else {
    		// Turn ClockWise 
        	turnAngleAbs = turnAngle;
    		turnDirctnScalar = (float)1;        	
    	}
    	sevenTenthsOfTotal = .7 * turnAngleAbs;
    	nineTenthsOfTotal  = .9 * turnAngleAbs;

    	if(Robot.kSENSORS.getGyroAngle() >= nineTenthsOfTotal) {
    		nearTrgtScalar = (float)0.25;
    	}
    	else if (Robot.kSENSORS.getGyroAngle() >= sevenTenthsOfTotal) {
    		nearTrgtScalar = (float)0.50;    		
    	}
    	 
    	turnNormPwrCmnd = (double)turnDirctnScalar * (double)nearTrgtScalar * turnSpeed;
    	
		Robot.kDRIVE.mechDrive(0, 0, turnNormPwrCmnd);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean exitCond = false;
    	 
	    	if ((turnSpeed >= (double)0.0) &&
	    		(Robot.kSENSORS.getGyroAngle() >= turnAngle)) {
	    		exitCond = true;
    	    }
	    	else if ((turnSpeed < (double)0.0) &&
		    		(Robot.kSENSORS.getGyroAngle() <= turnAngle)) {
		    	exitCond = true;
	        }
	    	else if (TurnTmOut.get() >= KATM_t_RotSafetyTmOut) {
		    	exitCond = true;	    		
	    	}
         
        return Robot.kSENSORS.getGyroAngle() >= turnAngle;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
    	TurnTmOut.stop();	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
