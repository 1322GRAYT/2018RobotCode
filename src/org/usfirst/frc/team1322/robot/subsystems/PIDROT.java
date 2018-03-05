package org.usfirst.frc.team1322.robot.subsystems;

import org.usfirst.frc.team1322.robot.Robot;
import org.usfirst.frc.team1322.robot.calibrations.K_PIDCal;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class: PIDROT - Performs P-I-D Control of the Rotation of the Robot
 * via Drive System Motors based on the Angular Position Feedback of the
 * Gyro.
 */
public class PIDROT extends Subsystem {
	private Timer TgtCmpltTmr = new Timer();
	
	// Variable Declarations
	boolean PIDRotEnbl;        // (boolean)
	boolean DirtcnIsClckWise;  // (boolean)
	boolean PstnErrWithInDB;   // (boolean)
	boolean PIDRotCondCmplt;   // (boolean)
	double PstnActRaw;         // (degrees)
	double PstnAct;            // (degrees)
    double PstnDsrd;	       // (degrees)
    double PstnErr;            // (degrees)
    double PstnErrAccum;       // (degrees)
    double PropCorr;           // (percent)
    double IntglCorr;          // (percent)
    double PIDCmndPct;         // (percent power)
    double PIDRotCmnd;         // (normalized power)
    
    
    /**********************************************/
    /* Public Interface Definitions        */
    /**********************************************/
    
    /** Method: resetTgtProfTmr - Resets the Drive Speed
     * Target Profile Timer at the beginning of a Drive Segments.  */ 
    public void resetTgtCmpltTmr() {
    	TgtCmpltTmr.reset();
    }

    /** Method: putPIDRotPstnTgt - Interface to Set the Gyro Target
      * Position Angle for the Robot Rotate PID Controller. (degrees)
      * (+ degrees ClockWise, - degrees Counter-Clockwise)
      *  @param1: Drive Rotate PID Enable Select (boolean)	
      *  @param2: Drive Rotate Enable Select (boolean)	
      *  @param3: Drive System Desired Encoder Speed Target (rpm: double) */	
    public void putPIDRotPstnTgt(boolean RotSysEnbl,
    		                     boolean RotClckWise,
    		                     double RotPstnTgt) {
    	PIDRotEnbl = RotSysEnbl;
    	DirtcnIsClckWise = RotClckWise;
    	PstnDsrd = RotPstnTgt;
    }  
    
    /** Method: getPIDRotCmnd - Interface to Get the Drive System Motor 
	  * Normalized Power Command for the Robot Rotate Control (-1 to 1).
      *  @return: Robot Rotate Motor Driver Normalized Power Command (-1 to 1: double) */	
    public double getPIDRotCmnd() {
    	return PIDRotCmnd;
    }

    /** Method: getPIDRotTgtCondMet - Interface to Get the Robot Rotate PI
      * Control System Target Acquired Condition Complete Indication. (boolean)
      *  @return: Robot Rotate Target Condition Complete (boolen) */	
    public boolean getPIDRotTgtCondMet() {
    	return PIDRotCondCmplt;
    }

    
    /** Method: resetPIDRot - Interface to Reset the Drive System
	  * Rotate PID Controller, i.e. initialize PID variables.  */ 
    public void resetPIDRot() {
        resetPIDCntrlr();
    }
	
	
    /******************************************************/
    /* Manage Drive PID Control Subsystem Scheduler Task  */
    /******************************************************/
	
    /** Method: managePIDRotate - Scheduler Function for the Periodic
     * Drive System Rotate Control PI Control System.  */ 
   public void managePIDRotate() {

	calcPstnAct();
	   
   	if (PIDRotEnbl == true)
   	    {
   		calcPstnErr();
   		calcPstnErrAccum();
   		calcPropTerm();
   		calcIntglTerm();
   		calcPIDTotCorr();
   		dtrmnPIDRotTgtMet();
   		
   	    }
   	else  // (PIDEnbl == false)
   	    {
   	    resetPIDCntrlr();  
   	    }
   	
   } 	
    
    
    /**********************************************/
    /* Internal Class Methods                     */
    /**********************************************/
   
   /** Method: calcPstnAct - YadaYada
    *  @param:  input info	(units)
    *  @return: output info (units) */	
   private void calcPstnAct() {
   	double PstnActTemp;
   	
   	PstnActRaw = Robot.kSENSORS.getGyroAngle();    	
   	if (DirtcnIsClckWise = false) {
   		// Turn Counter-ClockWise
   		PstnActTemp = ((float)360) - PstnActRaw;
   	}
   	else /* (DirtcnIsClckWise = true)  */ {
   		// Turn ClockWise
   		PstnActTemp = PstnActRaw;    		
   	}
   	
   	PstnAct = PstnActTemp;
     } 
   
   
    /** Method: calcPstnErr - Calculates Position Error taking
     * into account a symmetrical error dead-band around the
     * zero-error point.  Also gives an indication that
     * the Error Value is within the Dead-Band region.  */
    private void calcPstnErr() {
    	float ErrTemp;
    	boolean ErrInDB = false;
    	
    	ErrTemp = (float)(PstnDsrd - PstnAct);
    	
    	if (ErrTemp >= 0.0) {
    		// PstnErr is Positive
    		if (ErrTemp > K_PIDCal.KROT_Deg_PosErrDB)
    		    {
    		    ErrTemp = ErrTemp - K_PIDCal.KROT_Deg_PosErrDB;    			
    		    }
    		else
    	        {
    			ErrTemp = (float)0.0;
    			ErrInDB = true;
    	        }
    	}
    	else /* (ErrTemp < 0.0) */ {
    		// PstnErr is Negative
    		if (ErrTemp < -(K_PIDCal.KROT_Deg_PosErrDB))        // Less than the -DB
			    {
			    ErrTemp = ErrTemp + K_PIDCal.KROT_Deg_PosErrDB; // -Err - (-DB)   			
			    }
    		else
		        {
				ErrTemp = (float)0.0;
				ErrInDB = true;
		        }    		
    	}
    	
    	PstnErr = (double)ErrTemp;
    	PstnErrWithInDB = ErrInDB;
      }

    
    /** Method: calcPstnErrAccum - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
    private void calcPstnErrAccum() {
    	double ErrAccumTemp;
    	
    	ErrAccumTemp = PstnErrAccum + PstnErr;
    		
     	PstnErrAccum = ErrAccumTemp;  
    }

	
    /** Method: calcPropTerm - YadaYada
     *  @param:  input info	(units)
     *  @return: output info (units) */	
     private void calcPropTerm() {
     	double P_Corr;   // percent power
     	
     	P_Corr = K_PIDCal.KROT_K_PropGx * PstnErr;
     	
     	if (P_Corr < K_PIDCal.KROT_Pct_PropCorrMin) {
     		P_Corr = K_PIDCal.KROT_Pct_PropCorrMin;
     	}
     	else if (P_Corr > K_PIDCal.KROT_Pct_PropCorrMax) {
     		P_Corr = K_PIDCal.KROT_Pct_PropCorrMax;
     	}	
     	PropCorr = P_Corr;
     }

     
     /** Method: calcIntglTerm - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void calcIntglTerm() {
     	double I_Corr;     	
     	
     	I_Corr = K_PIDCal.KROT_K_IntglGx * PstnErrAccum;
     	     	
    	if (I_Corr > K_PIDCal.KROT_Pct_IntglCorrMax) {
    		    I_Corr = K_PIDCal.KROT_Pct_IntglCorrMax;
    	    }
    	else if (I_Corr < -(K_PIDCal.KROT_Pct_IntglCorrMax)) {
                I_Corr = -(K_PIDCal.KROT_Pct_IntglCorrMax);
    	    }
     	IntglCorr = I_Corr;
     }

     
     /** Method: calcPIDTotCorr - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void calcPIDTotCorr() {
     	double CmndPct; // %
     	
     	CmndPct = PropCorr + IntglCorr;
 	    
 	    if (CmndPct > 100) CmndPct = 100;
 	    else if (CmndPct < 0) CmndPct = 0;
 	    
 	    if (DirtcnIsClckWise == false) CmndPct = -CmndPct;  // Desired Target Turn is Counter-ClockWise
 	
 	    PIDCmndPct = CmndPct;
 	    PIDRotCmnd= CmndPct/100;
     }
     

     /** Method: dtrmnPIDRotTgtMet - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void dtrmnPIDRotTgtMet() {
    	
    	 if (PstnErrWithInDB == false) {  // Error outside Target DB
    		 TgtCmpltTmr.reset(); 
    	 } 
    	 else  {
    		 /* Do Nothing - Insided Target DB - Free-Running Timer */
    	 }

    	 if (TgtCmpltTmr.get() >= K_PIDCal.KROT_t_PstnTgtSyncMetThrsh) {
    		 PIDRotCondCmplt = true; 
    	 }
    	 else {
    		 PIDRotCondCmplt = false;  
    	 } 
     }
     
     
     /** Method: resetPIDCntrlr - YadaYada
      *  @param:  input info	(units)
      *  @return: output info (units) */	
     private void resetPIDCntrlr() {
    	 TgtCmpltTmr.reset();
    	 PstnDsrd = 0.0;	 
         PstnErr = 0.0;
         PstnErrWithInDB = false;
         PstnErrAccum = 0.0;
   	     PropCorr = 0.0;
   	     IntglCorr = 0.0;
   	     PIDCmndPct =  0.0;
   	     PIDRotCmnd =  0.0;
   	     PIDRotCondCmplt = false;
     }
     

     
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

