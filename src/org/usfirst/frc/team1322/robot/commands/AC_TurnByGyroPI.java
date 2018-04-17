package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
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
	private Timer RotateTmOut = new Timer();
	private boolean RotClckWise;
	private double  RotPstnDsrd;
	

	//  Autonomous Pattern Constructor
    /** Method: AC_TurnByGyroPI - Autonomous Command to Rotate
     * ClockWise or Counter-ClockWise to a Desired Angular
     * Position Based on Gyro Angular Position Feedback using
     * a PI Control System.
     *  @param1: RotClckWise - Is Desired Rotation ClockWise? (boolean)	
     *  @param2: RotPstnDsrd - Desired Angular Rotation       (double: degrees +/-)
     *           (+ = ClockWise, - = Counter-ClockWise)         
     *   */
    public AC_TurnByGyroPI(boolean RotClckWise,
    		               double  RotPstnDsrd) {
        requires(Robot.kDRIVE);
        requires(Robot.kPID);        
    	this.RotClckWise = RotClckWise;
    	this.RotPstnDsrd = RotPstnDsrd;        
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	RotateTmOut.reset();
    	RotateTmOut.start();
    	Robot.kDRIVE.enable();
    	Robot.kPID.resetPIDRot();
        Robot.kPID.putPIDRotPstnTgt(this.RotClckWise,
        		                    this.RotPstnDsrd);
    	Robot.kPID.putPIDRotSysEnbl(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double NormPwrCmnd;
    	
    	// RotateTmOut updates as free-running timer.
        Robot.kPID.managePIDRotate();
        NormPwrCmnd = Robot.kPID.getPIDRotCmnd();
    	Robot.kDRIVE.mechDrive(0, 0, NormPwrCmnd);    	
	    
  	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
    	    updateSmartDashData();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean exitCond = false;
    	
    	if ((Robot.kPID.getPIDRotTgtCondMet() == true) ||
    		(RotateTmOut.get() >= K_CmndCal.KCMD_t_RotSafetyTmOutPI)) {
    		exitCond = true;
    	}
    	
        return (exitCond == true);
    }

    // Called once after isFinished returns true
    protected void end() {
    	RotateTmOut.stop();   
    	Robot.kPID.putPIDRotSysEnbl(false);
    	Robot.kPID.resetPIDRot();
    	Robot.kAUTON.setMasterTaskCmplt(true);
    	Robot.kDRIVE.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    
	// Called to Update SmartDash Data for Display
    protected void updateSmartDashData() {

    	//Drive Speeds
    	SmartDashboard.putBoolean("Dsrd Rotate Clockwise : ", RotClckWise);
    	SmartDashboard.putNumber("Desired Rotate Position : ", RotPstnDsrd);
    	SmartDashboard.putNumber("Actual Rotate Position : ", Robot.kPID.getPIDRotPstnAct());
    	SmartDashboard.putNumber("Position Error: ", Robot.kPID.getPIDRotPstnErr());
    	SmartDashboard.putNumber("Position Error Accumulator : ", Robot.kPID.getPIDRotErrAccum());
    	SmartDashboard.putNumber("Feed-Forward Term : ", Robot.kPID.getPIDRotFdFwdTerm());
    	SmartDashboard.putNumber("Proportional Term : ", Robot.kPID.getPIDRotPropTerm());
    	SmartDashboard.putNumber("Integral Term : ", Robot.kPID.getPIDRotIntglTerm());
    	SmartDashboard.putNumber("PID Percent Power : ", Robot.kPID.getPIDRotPIDCmndPct());
    	SmartDashboard.putNumber("PID Power Command : ", Robot.kPID.getPIDRotCmnd());
    	SmartDashboard.putBoolean("PID Target Condition Met : ", Robot.kPID.getPIDRotTgtCondMet());    	
    	SmartDashboard.putNumber("PID Time Out : ", RotateTmOut.get());

    	
    	
    	System.out.println("Dsrd Rotate Clockwise : " + RotClckWise);
    	System.out.println("Desired Rotate Position : " + RotPstnDsrd);
    	System.out.println("Actual Rotate Position : " + Robot.kPID.getPIDRotPstnAct());
    	System.out.println("Position Error: " + Robot.kPID.getPIDRotPstnErr());
    	System.out.println("Position Error Accumulator : " + Robot.kPID.getPIDRotErrAccum());
    	System.out.println("Feed-Forward Term : " + Robot.kPID.getPIDRotFdFwdTerm());
    	System.out.println("Proportional Term : " + Robot.kPID.getPIDRotPropTerm());
    	System.out.println("Integral Term : " + Robot.kPID.getPIDRotIntglTerm());
    	System.out.println("PID Percent Power : " + Robot.kPID.getPIDRotPIDCmndPct());
    	System.out.println("PID Power Command : " + Robot.kPID.getPIDRotCmnd());
    	System.out.println("PID Target Condition Met : " + Robot.kPID.getPIDRotTgtCondMet());
    	System.out.println("PID Time Out : " + RotateTmOut.get());
    	
    }    
    
}
