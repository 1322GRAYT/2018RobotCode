package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_SensorCal;
import org.usfirst.frc.team1322.robot.calibrations.RobotMap;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
    	Robot.kPID.resetPIDRot();
        Robot.kPID.putPIDRotPstnTgt(this.RotPIDEnbl,
        		                    this.RotClckWise,
        		                    this.RotPstnDsrd);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double NormPwrCmnd;
    	
    	// RotateTmOut updates as free-running timer.
        Robot.kPID.managePIDRotate();
        NormPwrCmnd = Robot.kPID.getPIDRotCmnd();
    	Robot.kDRIVE.mechDrive(0, 0, NormPwrCmnd);
    	
    	updateSmartDashData();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean exitCond = false;
    	
    	if ((Robot.kPID.getPIDRotTgtCondMet() == true) ||
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
    
    
	// Called to Update SmartDash Data for Display
    protected void updateSmartDashData() {

    	//Gyro
    	SmartDashboard.putNumber("Gyro Angle : ", Robot.kSENSORS.getGyroAngle());
    	//Drive Speeds
    	SmartDashboard.putNumberArray("Encoder Velocity : ", Robot.kSENSORS.getEncodersVelRaw());
    	SmartDashboard.putNumberArray("Encoder Counts : ", Robot.kSENSORS.getEncodersCnt());
    	SmartDashboard.putNumberArray("Encoder RPM : ", Robot.kSENSORS.getEncodersRPM());
    	SmartDashboard.putBoolean("Dsrd Rotate Clockwise : ", this.RotClckWise);
    	SmartDashboard.putNumber("Desired Rotate Position : ", this.RotPstnDsrd);
    	SmartDashboard.putNumber("Actual Rotate Position : ", Robot.kPID.getPIDRotPstnAct());
    	SmartDashboard.putNumber("Position Error: ", Robot.kPID.getPIDRotPstnErr());
    	SmartDashboard.putNumber("Position Error Accumulator : ", Robot.kPID.getPIDRotErrAccum());
    	SmartDashboard.putNumber("Proportional Term : ", Robot.kPID.getPIDRotPropTerm());
    	SmartDashboard.putNumber("Integral Term : ", Robot.kPID.getPIDRotIntglTerm());
    	SmartDashboard.putNumber("PID Percent Power : ", Robot.kPID.getPIDRotPIDCmndPct());
    	SmartDashboard.putNumber("PID Power Command : ", Robot.kPID.getPIDRotCmnd());
    	SmartDashboard.putBoolean("PID Target Condition Met : ", Robot.kPID.getPIDRotTgtCondMet());    	
    	SmartDashboard.putNumber("PID Time Out : ", RotateTmOut.get());
    	
    	//Update SmartDashboard
    	System.out.println("Dsrd Rotate Clockwise : " + this.RotClckWise);
    	System.out.println("Desired Rotate Position : " + this.RotPstnDsrd);
    	System.out.println("Actual Rotate Position : " + Robot.kPID.getPIDRotPstnAct());
    	System.out.println("Position Error: " + Robot.kPID.getPIDRotPstnErr());
    	System.out.println("Position Error Accumulator : " + Robot.kPID.getPIDRotErrAccum());
    	System.out.println("Proportional Term : " + Robot.kPID.getPIDRotPropTerm());
    	System.out.println("Integral Term : " + Robot.kPID.getPIDRotIntglTerm());
    	System.out.println("PID Percent Power : " + Robot.kPID.getPIDRotPIDCmndPct());
    	System.out.println("PID Power Command : " + Robot.kPID.getPIDRotCmnd());
    	System.out.println("PID Target Condition Met : " + Robot.kPID.getPIDRotTgtCondMet());
    	System.out.println("PID Time Out : " + RotateTmOut.get());
    	
    }    
    
}
