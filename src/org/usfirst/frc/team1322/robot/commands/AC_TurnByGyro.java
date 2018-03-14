package org.usfirst.frc.team1322.robot.commands;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_LiftCal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command Class: AM_TurnByGyro
 * Turn To a a specific Angle at a specific Normalized Power.
 * Positive Power is ClockWise, Negative is Counter-ClockWise.
 */
public class AC_TurnByGyro extends Command {
	boolean liftHldEnbl;
	private Timer TurnTmOut = new Timer();
	private double turnPwr;
	private double turnAngle;
	private double RotDsrdAng;	
	private double RotFdbkAng;
	private double RotFdbkAngInit;	
	private double RotAngInitDelt;
    private double RotAng70Pct;	
    private double RotAng90Pct;	
	
	
    /** KATM_b_RotRstGyroOnInit: Enable the the Reset the Gyro at
      * the initialization at each Turn by Gryo Step. */
	public static final boolean KATM_b_RotRstGyroOnInit = false; 
	
	/** KATM_t_RotSafetyTmOut: Amount of Time that must elapse before
     * a rotate command will cancel out due to taking too long to reach
     * the target angle due to some system loss. */
	public static final float KATM_t_RotSafetyTmOut = (float)1.0; // sec 
	
	
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
        if (KATM_b_RotRstGyroOnInit == true)
        	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	
    	RotDsrdAng = this.turnAngle;
    	RotFdbkAngInit = Robot.kSENSORS.getGyroAngle(); 	
    	RotAngInitDelt = RotDsrdAng - RotFdbkAngInit;
        RotAng70Pct = RotAngInitDelt * 0.7;	
        RotAng90Pct = RotAngInitDelt * 0.9;	
    	
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
    	float  turnDirctnScalar = (float)1.0;
    	float  nearTrgtScalar =   (float)1.0;
        double turnNormPwrCmnd;
        double liftPwrCmnd;
    	
        /* TurnTmOut is a Free Running Timer */	

        RotFdbkAng = Robot.kSENSORS.getGyroAngle();
        
    	if (turnPwr < (double)0.0) {
    		// Turn Counter-ClockWise
    		turnDirctnScalar = (float)-1;
    	} else {
    		// Turn ClockWise 
    		turnDirctnScalar = (float)1;        	
    	}

    	if(RotFdbkAng >= RotAng90Pct) {
    		nearTrgtScalar = (float)0.25;
    	} else if (RotFdbkAng >= RotAng70Pct) {
    		nearTrgtScalar = (float)0.50;    		
    	}
    	 
    	turnNormPwrCmnd = (double)turnDirctnScalar * (double)nearTrgtScalar * turnPwr;
    	
		Robot.kDRIVE.mechDrive(0, 0, turnNormPwrCmnd);
		
		
	    // Keep Lift in Elevated Position
	    if ((this.liftHldEnbl == true) &&
	    	(Robot.kLIFT.getMidSen() == true)) {
	        // PwrCube not sensed by N/C Sensor
	    	liftPwrCmnd = (double)K_LiftCal.KLFT_r_LiftMtrHldPwr;	
	    } else {
	    	// PwrCube is sensed by N/C Sensor
	    	liftPwrCmnd = 0.0;	
	    }
	    
	    Robot.kLIFT.setSpeed(liftPwrCmnd);  
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
	        } else if (TurnTmOut.get() >= KATM_t_RotSafetyTmOut) {
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
    
}
