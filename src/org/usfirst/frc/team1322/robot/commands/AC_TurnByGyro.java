package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_CmndCal;
import org.usfirst.frc.team1322.robot.calibrations.K_DriveCal;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;
import org.usfirst.frc.team1322.robot.subsystems.USERLIB;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command Class: AM_TurnByGyro
 * Turn To a a specific Angle at a specific Normalized Power.
 * Positive Power is ClockWise, Negative is Counter-ClockWise.
 */
public class AC_TurnByGyro extends Command {
	boolean liftHldEnbl;
	private Timer TurnTmOut = new Timer();
	private double turnPwr;
	private double turnPwrCmnd;	
	private double turnPwrCmndLim;
	private double turnAngle;
	private double RotDsrdAng;	
	private double RotFdbkAng;
	private double RotFdbkAngInit;	
	private double RotAngInitDelt;
    private double RotAng70Pct;	
    private double RotAng90Pct;
    private double LiftPwrCmnd;
	
	
	/**
	 * Command Method: AM_TurnByGyro
	 * Turn to a specific Angle at a specific Normalized Power.
	 * @param1: turnPwr, Normalized Power To Turn  + = ClckWise, - = CntrClckWise
	 * @param2: turnAngle, Desired Turn Target Angle (boolean)
	 * @param3: liftHldEnbl, Enable the Lift Hold Function (boolean)
	 */
    public AC_TurnByGyro(double turnPwr, double turnAngle, boolean liftHldEnbl) {
        requires(Robot.kDRIVE);
        requires(Robot.kLIFT);
        this.turnPwr = turnPwr;
        this.turnAngle = turnAngle;
        this.liftHldEnbl = liftHldEnbl;
    }

    
    // Called just before this Command runs the first time
    protected void initialize() {
        TurnTmOut.reset();
        TurnTmOut.start();
        if (K_CmndCal.KCMD_b_RotRstGyroOnInit == true)
        	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();

        turnPwrCmndLim = 0.0;
    	
    	RotDsrdAng = this.turnAngle;
    	RotFdbkAngInit = Robot.kSENSORS.getGyroAngle(); 	
    	RotAngInitDelt = Math.abs(RotDsrdAng - RotFdbkAngInit);
    	if (turnPwr >= 0.0) {
    		// Turn ClockWise
            RotAng70Pct = RotDsrdAng - (RotAngInitDelt * 0.3);	
            RotAng90Pct = RotDsrdAng - (RotAngInitDelt) * 0.1;	    		
    	} else {
    		// Turn Counter-ClockWise
            RotAng70Pct = RotDsrdAng + (RotAngInitDelt * 0.3);	
            RotAng90Pct = RotDsrdAng + (RotAngInitDelt) * 0.1;	
    	}
        
    	if(Math.abs(turnPwr) < .3) {
    		System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		if (turnPwr > 0.0)  {
    			turnPwr =  0.3;
    		} else {
    			turnPwr = -0.3;    			
    		}
    	}
    	
    	Robot.kDRIVE.mechDrive(0, 0, turnPwr);
    	  	    	
    }

    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	float  nearTrgtScalar = (float)1.0;

    	
        /* TurnTmOut is a Free Running Timer */	

        RotFdbkAng = Robot.kSENSORS.getGyroAngle();
        
    	if (turnPwr >= 0.0) {
    		// Turn ClockWise
	    	if(RotFdbkAng >= RotAng90Pct) {
	    		nearTrgtScalar = (float)0.25;
	    	} else if (RotFdbkAng >= RotAng70Pct) {
	    		nearTrgtScalar = (float)0.75;    		
	    	}
    	} else {
	    	if(RotFdbkAng <= RotAng90Pct) {
	    		nearTrgtScalar = (float)0.25;
	    	} else if (RotFdbkAng <= RotAng70Pct) {
	    		nearTrgtScalar = (float)0.75;    		
	    	}    		
    	}
    	
    	turnPwrCmnd = (double)nearTrgtScalar * turnPwr;
    	
        turnPwrCmndLim = USERLIB.RateLimOnInc(turnPwrCmnd,
        		                              turnPwrCmndLim,
        		                              K_DriveCal.KDRV_r_RotPwrDeltIncLimMax);
    	
		Robot.kDRIVE.mechDrive(0, 0, turnPwrCmndLim);
		
	    // Keep Lift in Elevated Position
	    if ((this.liftHldEnbl == true) &&
	    	(Robot.kLIFT.getMidSen() == true) &&
			(Robot.kLIFT.getHighSen() == true)) {
	        // PwrCube not sensed by N/C Sensor
	    	LiftPwrCmnd = (double)K_LiftCal.KLFT_r_LiftMtrHldPwr;	
	    } else {
	    	// PwrCube is sensed by N/C Sensor
	    	LiftPwrCmnd = 0.0;	
	    }
	    
	    Robot.kLIFT.setSpeed(LiftPwrCmnd);
	    
  	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
	    	updateSmartDashData();  
    }

    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean exitCond = false;
    	 
	    	if ((turnPwr >= (double)0.0) &&
	    		(RotFdbkAng >= turnAngle)) {
	    		exitCond = true;
    	    } else if ((turnPwr < (double)0.0) &&
		    		   (RotFdbkAng <= turnAngle)) {
		    	exitCond = true;
	        } else if (TurnTmOut.get() >= K_CmndCal.KCMD_t_RotSafetyTmOut) {
		    	exitCond = true;	    		
	    	}
         
        return (exitCond == true);
    }

    
    // Called once after isFinished returns true
    protected void end() {
    	Robot.kDRIVE.disable();
  	    Robot.kLIFT.setSpeed(0.0);
    	TurnTmOut.stop();	
    }

    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
    
    
	// Call to Update SmartDash Data for Display
    protected void updateSmartDashData() {
        SmartDashboard.putNumber("Rotate Timeout Timer : ", TurnTmOut.get());
    	SmartDashboard.putNumber("Rotate Desired Angle : ", RotDsrdAng);
    	SmartDashboard.putNumber("Rotate Feedback Angle : ", RotFdbkAng);
    	SmartDashboard.putNumber("Rotate Feedback Angle Init : ", RotFdbkAngInit);
    	SmartDashboard.putNumber("Rotate Angle Init Delta : ", RotAngInitDelt);
    	SmartDashboard.putNumber("Rotate 70 Percent Angle : ", RotAng70Pct);
    	SmartDashboard.putNumber("Rotate 90 Percent Angle : ", RotAng90Pct);
    	SmartDashboard.putNumber("Rotate Power Cmnd : ", turnPwr);
    	SmartDashboard.putNumber("Rotate Power Cmnd Adjusted : ", turnPwrCmnd);
    	SmartDashboard.putBoolean("Lift Hold Enable during Side Arc? : ", liftHldEnbl);
    	SmartDashboard.putNumber("Lift Hold Power Cmnd : ", LiftPwrCmnd);    	
    	
    	System.out.println("Rotate Timeout Timer : " + TurnTmOut.get());
    	System.out.println("Rotate Desired Angle : " + RotDsrdAng);
    	System.out.println("Rotate Feedback Angle : " + RotFdbkAng);
    	System.out.println("Rotate Feedback Angle Init : " + RotFdbkAngInit);
    	System.out.println("Rotate Angle Init Delta : " + RotAngInitDelt);
    	System.out.println("Rotate 70 Percent Angle : " + RotAng70Pct);
    	System.out.println("Rotate 90 Percent Angle : " + RotAng90Pct);
    	System.out.println("Rotate Power Cmnd : " + turnPwr);
    	System.out.println("Rotate Power Cmnd Adjusted : " + turnPwrCmnd);
    	System.out.println("Lift Hold Enable during Side Arc? : " + liftHldEnbl);
    	System.out.println("Lift Hold Power Cmnd : " + LiftPwrCmnd);     	
    }
    
}
