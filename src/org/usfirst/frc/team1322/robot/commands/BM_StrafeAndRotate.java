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
     * Command Class: BM_StrafeAndRotate - Autonomous Command to Strafe
     *  to the side and rotate to a desired position simultaneously to move
     *  sideways in an arc about point.
     */
    public class BM_StrafeAndRotate extends Command {
	
	    // Autonomous Pattern Vars
	    private Timer   RotTmOut = new Timer();
	    private boolean ModeIsAuton;
	    private boolean RotClckWise;
	    private double  RotDsrdAng;
		private double  RotFdbkAng;
		private double  RotFdbkAngInit;	
		private double  RotAngInitDelt;
	    private double  RotAng95Pct;
	    private double  RotPwrCmnd;
	    private double  RotPwrCmndAdj;
	    private double  StrfPwrCmnd;
	    private double  StrfPwrCmndLim;
	    private double  DrvPwrCmnd; 	
	
    /**
      *  Command Method: BM_StrafeAndRotate - Autonomous Command to Strafe
      *  to the side and rotate to a desired position simultaneously to move
      *  sideways in an arc about point.
      *  @param1: Mode is Autonomous (Not Tele-Op)?      (boolean)	
      *  @param2: Desired Rotation ClockWise?            (boolean)	
      *  @param3: Desired Rotation Position Angle        (float: degrees)	
      */
    public BM_StrafeAndRotate(boolean ModeIsAuton,
    		                  boolean RotClckWise,
                              float   RotDsrdAng) {
        requires(Robot.kDRIVE);        
        this.ModeIsAuton = ModeIsAuton;
        this.RotClckWise = RotClckWise;
        this.RotDsrdAng = RotDsrdAng;
    }


	// Called just before this Command runs the first time
    protected void initialize() {
    	double RotPwrTemp;
    	
    	Robot.kAUTON.setMasterTaskCmplt(false);
    	
    	StrfPwrCmndLim = 0.0;
    	
    	RotTmOut.reset();
    	RotTmOut.start();
        if ((K_CmndCal.KCMD_b_RotRstGyroOnInit == true) ||
        	(ModeIsAuton == false))
        	Robot.kSENSORS.resetGyro();
    	Robot.kDRIVE.enable();
    	
    	RotFdbkAngInit = Robot.kSENSORS.getGyroAngle(); 	
    	RotAngInitDelt = RotDsrdAng - RotFdbkAngInit;
        RotAng95Pct = RotAngInitDelt * 0.95;	

       if (RotClckWise == false) {
	   // Turn Counter-ClockWise
    	   RotPwrTemp  = (float)K_CmndCal.KCMD_r_SideArcRotPwrLeft;
       	   if(RotPwrTemp < 0.3) {
    	       System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		   RotPwrTemp = 0.3;
    	   }
           RotPwrCmnd = -RotPwrTemp;        
           StrfPwrCmnd = (float)K_CmndCal.KCMD_r_SideArcStrfPwr;
           DrvPwrCmnd = (float)K_CmndCal.KCMD_r_SideArcDrvPwrLeft;     
       } else {
	   // Turn ClockWise 
    	   RotPwrTemp = (float)K_CmndCal.KCMD_r_SideArcRotPwrRight;
       	   if(RotPwrTemp < 0.3) {
    	       System.out.println("TurnByGyro Turn Speed to low, Upping to .3");
    		   RotPwrTemp = 0.3;
       	   }
           RotPwrCmnd = RotPwrTemp;               	   
           StrfPwrCmnd = (float)-(K_CmndCal.KCMD_r_SideArcStrfPwr);
           DrvPwrCmnd = (float)K_CmndCal.KCMD_r_SideArcDrvPwrRight;     
       }
       
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	float  nearTrgtScalar = (float)1.0;
    	
        /* TurnTmOut is a Free Running Timer */	

    	// Rate Limit the Strafing Power
    	StrfPwrCmndLim = USERLIB.RateLimOnInc(StrfPwrCmnd,
    			                              StrfPwrCmndLim,
    			                              K_DriveCal.KDRV_r_StrfPwrDeltIncLimMax);
    	
    	// Determine Rotate Power
        RotFdbkAng = Robot.kSENSORS.getGyroAngle();
        
    	if(RotFdbkAng >= RotAng95Pct) {
    		nearTrgtScalar = (float)0.50;   		
    	}
    	RotPwrCmndAdj = (double)nearTrgtScalar * RotPwrCmnd;
    	
		Robot.kDRIVE.mechDrive(StrfPwrCmndLim, DrvPwrCmnd, RotPwrCmndAdj);
	  	    
	    
  	    // Update Smart Dashboard Data
	    if (K_CmndCal.KCMD_b_DebugEnbl)
  	        updateSmartDashData();    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        boolean exitCond = false;
   	 
    	if ((RotClckWise == true) &&
    		(RotFdbkAng >= RotDsrdAng)) {
    		exitCond = true;
	    } else if ((RotClckWise == false) &&
	    		   (RotFdbkAng <= RotDsrdAng)) {
	    	exitCond = true;
        } else if ((ModeIsAuton == true) &&
        		   (RotTmOut.get() >= K_CmndCal.KCMD_t_SideArcSafetyTmOut)) {
	    	exitCond = true;	    		
    	}
     
    return (exitCond == true);
    }

    // Called once after isFinished returns true
    protected void end() {
  	    RotTmOut.stop();	
    	Robot.kDRIVE.disable();
    	Robot.kAUTON.setMasterTaskCmplt(true);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }

    
	// Call to Update SmartDash Data for Display
    protected void updateSmartDashData() {
    	SmartDashboard.putBoolean("Rotate ClockWise? : ", RotClckWise);
        SmartDashboard.putNumber("Rotate Timeout Timer : ", RotTmOut.get());
    	SmartDashboard.putNumber("Rotate Desired Angle : ", RotDsrdAng);
    	SmartDashboard.putNumber("Rotate Feedback Angle : ", RotFdbkAng);
    	SmartDashboard.putNumber("Rotate Feedback Angle Init : ", RotFdbkAngInit);
    	SmartDashboard.putNumber("Rotate Angle Init Delta : ", RotAngInitDelt);
    	SmartDashboard.putNumber("Rotate 90 Percent Angle : ", RotAng95Pct);
    	SmartDashboard.putNumber("Rotate Power Cmnd : ", RotPwrCmnd);
    	SmartDashboard.putNumber("Rotate Power Cmnd Adjusted : ", RotPwrCmndAdj);
    	SmartDashboard.putNumber("Strafe Power Cmnd : ", StrfPwrCmnd);
    	SmartDashboard.putNumber("Strafe Power Cmnd : ", DrvPwrCmnd);
    	
    	System.out.println("Rotate ClockWise? : " + RotClckWise);
    	System.out.println("Rotate Timeout Timer : " + RotTmOut.get());
    	System.out.println("Rotate Desired Angle : " + RotDsrdAng);
    	System.out.println("Rotate Feedback Angle : " + RotFdbkAng);
    	System.out.println("Rotate Feedback Angle Init : " + RotFdbkAngInit);
    	System.out.println("Rotate Angle Init Delta : " + RotAngInitDelt);
    	System.out.println("Rotate 90 Percent Angle : " + RotAng95Pct);
    	System.out.println("Rotate Power Cmnd : " + RotPwrCmnd);
    	System.out.println("Rotate Power Cmnd Adjusted : " + RotPwrCmndAdj);
    	System.out.println("Strafe Power Cmnd : " + StrfPwrCmnd);
    	System.out.println("Strafe Power Cmnd : " + DrvPwrCmnd);
    	
    }
    
    
}
